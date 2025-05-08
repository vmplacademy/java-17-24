package pl.vm.features.virtualthreads;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(VirtualThreadServer.class);
    private final int port;
    private HttpServer server;
    private volatile boolean running;

    public VirtualThreadServer(int port) throws IOException {
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }

    public void start() {
        // Add handler for root path
        server.createContext("/", exchange -> {
            try {
                // Simulate some I/O-bound work
                Thread.sleep(Duration.ofMillis(100));
                
                String threadName = Thread.currentThread().getName();
                boolean isVirtual = Thread.currentThread().isVirtual();
                
                logger.info("Handling request in thread: {} (isVirtual: {})", threadName, isVirtual);
                
                String response = String.format("Hello from %s thread: %s%nRequest started at: %d",
                        isVirtual ? "virtual" : "platform",
                        threadName,
                        System.currentTimeMillis());
                
                // Send response
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                } catch (IOException ex) {
                    logger.error("Error sending response", ex);
                    exchange.sendResponseHeaders(500, 0);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                try {
                    exchange.sendResponseHeaders(500, 0);
                } catch (IOException ex) {
                    logger.error("Error sending error response", ex);
                }
                exchange.close();
            } catch (IOException e) {
                logger.error("Error handling request", e);
                try {
                    exchange.sendResponseHeaders(500, 0);
                } catch (IOException ex) {
                    logger.error("Error sending error response", ex);
                }
                exchange.close();
            }
        });
        
        server.start();
        running = true;
        logger.info("Server started on port {}", port);
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
        try {
            VirtualThreadServer server = new VirtualThreadServer(8080);
            server.start();
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
} 