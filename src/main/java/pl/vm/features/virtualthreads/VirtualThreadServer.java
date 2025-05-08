package pl.vm.features.virtualthreads;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.lang.ScopedValue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple web server that demonstrates the use of virtual threads in Java 24.
 * This server uses modern Java features like ScopedValue and StructuredTaskScope.
 */
public class VirtualThreadServer {
    private final int requestedPort;
    private final ExecutorService executor;
    private volatile boolean running;
    private ServerSocketChannel serverSocket;
    private volatile int actualPort;
    private final AtomicInteger threadCounter = new AtomicInteger();
    
    // ScopedValue for request context
    private static final ScopedValue<RequestContext> REQUEST_CONTEXT = ScopedValue.newInstance();

    public VirtualThreadServer(int port) {
        this.requestedPort = port;
        // Create a virtual thread executor with custom thread factory
        ThreadFactory factory = r -> {
            Thread thread = Thread.ofVirtual().unstarted(r);
            thread.setName("virtual-thread-" + threadCounter.getAndIncrement());
            return thread;
        };
        this.executor = Executors.newThreadPerTaskExecutor(factory);
    }

    public void start() {
        running = true;
        try {
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(requestedPort));
            actualPort = ((InetSocketAddress) serverSocket.getLocalAddress()).getPort();
            System.out.println("Server started on port " + actualPort);

            while (running) {
                SocketChannel clientSocket = serverSocket.accept();
                // Handle each client connection in a new virtual thread
                executor.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            executor.close();
        }
    }

    private void handleClient(SocketChannel clientSocket) {
        try (clientSocket) {
            // Create request context with timestamp
            var context = new RequestContext(System.currentTimeMillis());
            
            // Use ScopedValue to bind context to the current virtual thread
            ScopedValue.where(REQUEST_CONTEXT, context).run(() -> {
                try {
                    // Simulate some I/O-bound work
                    Thread.sleep(Duration.ofMillis(100));
                    
                    // Read the request
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    clientSocket.read(buffer);
                    buffer.flip();
                    String request = StandardCharsets.UTF_8.decode(buffer).toString();
                    
                    // Process the request and generate a response
                    String response = processRequest(request);
                    
                    // Send the response
                    buffer.clear();
                    buffer.put(StandardCharsets.UTF_8.encode(response));
                    buffer.flip();
                    clientSocket.write(buffer);
                } catch (IOException | InterruptedException e) {
                    System.err.println("Error handling client: " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }

    private String processRequest(String request) {
        // Get the request context from ScopedValue
        var context = REQUEST_CONTEXT.get();
        
        // Simulate some processing time
        try {
            Thread.sleep(Duration.ofMillis(50));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Print current thread name for debugging
        String threadName = Thread.currentThread().getName();
        System.out.println("Processing request in thread: " + threadName);
        
        String content = "Hello from virtual thread: " + threadName + "\nRequest started at: " + context.timestamp();
        return """
            HTTP/1.1 200 OK
            Content-Type: text/plain
            Content-Length: %d
            
            %s""".formatted(content.length(), content);
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error stopping server: " + e.getMessage());
        }
    }

    public int getPort() {
        return actualPort;
    }

    public static void main(String[] args) {
        VirtualThreadServer server = new VirtualThreadServer(8080);
        server.start();
    }
    
    // Record for request context
    private record RequestContext(long timestamp) {}
} 