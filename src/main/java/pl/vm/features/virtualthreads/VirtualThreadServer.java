package pl.vm.features.virtualthreads;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.concurrent.Executors;

/**
 * A simple web server that demonstrates the use of virtual threads in Java 24.
 * This server uses Java's built-in Simple Web Server with virtual threads.
 */
public class VirtualThreadServer {
    private final int port;
    private HttpServer server;
    private volatile boolean running;

    public VirtualThreadServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            // Create server with virtual thread executor
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
            
            // Add handler for root path
            server.createContext("/", exchange -> {
                try {
                    // Simulate some I/O-bound work
                    Thread.sleep(Duration.ofMillis(100));
                    
                    Thread currentThread = Thread.currentThread();
                    String threadName = currentThread.getName();
                    boolean isVirtual = currentThread.isVirtual();
                    
                    System.out.println("Handling request in thread: " + threadName + " (isVirtual: " + isVirtual + ")");
                    
                    String content = String.format("Hello from %s thread: %s%nRequest started at: %d",
                            isVirtual ? "virtual" : "platform",
                            threadName,
                            System.currentTimeMillis());
                    
                    // Send response
                    exchange.sendResponseHeaders(200, content.length());
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(content.getBytes());
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    exchange.sendResponseHeaders(500, 0);
                    exchange.close();
                }
            });
            
            server.start();
            running = true;
            System.out.println("Server started on port " + port);
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            running = false;
        }
    }

    public int getPort() {
        return port;
    }

    public static void main(String[] args) {
        VirtualThreadServer server = new VirtualThreadServer(8080);
        server.start();
    }
} 