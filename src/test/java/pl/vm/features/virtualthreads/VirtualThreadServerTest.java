package pl.vm.features.virtualthreads;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class VirtualThreadServerTest {
    private VirtualThreadServer server;
    private HttpClient httpClient;
    private ExecutorService executor;
    private static final Pattern VIRTUAL_THREAD_PATTERN = Pattern.compile("virtual-thread-\\d+");

    @BeforeEach
    void setUp() {
        // Start server on a random port
        server = new VirtualThreadServer(0);
        Thread serverThread = new Thread(() -> server.start());
        serverThread.start();
        
        // Wait for server to start and get a port
        while (server.getPort() == 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        httpClient = HttpClient.newBuilder()
                .executor(Executors.newVirtualThreadPerTaskExecutor())
                .build();
        executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    @AfterEach
    void tearDown() {
        server.stop();
        executor.close();
    }

    @Test
    void testSingleRequest() throws Exception {
        // Create HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + server.getPort()))
                .GET()
                .build();

        // Send request and get response
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Verify response
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("Hello from virtual thread"));
        assertTrue(response.body().contains("Request started at"));
    }

    @Test
    void testConcurrentRequests() throws Exception {
        int numRequests = 10;
        List<CompletableFuture<HttpResponse<String>>> futures = new ArrayList<>();

        // Create multiple concurrent requests
        for (int i = 0; i < numRequests; i++) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + server.getPort()))
                    .GET()
                    .build();

            CompletableFuture<HttpResponse<String>> future = httpClient.sendAsync(
                    request, HttpResponse.BodyHandlers.ofString());
            futures.add(future);
        }

        // Wait for all requests to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // Verify all responses
        for (CompletableFuture<HttpResponse<String>> future : futures) {
            HttpResponse<String> response = future.get();
            assertEquals(200, response.statusCode());
            assertTrue(response.body().contains("Hello from virtual thread"));
            assertTrue(response.body().contains("Request started at"));
        }
    }

    @Test
    void testVirtualThreadNames() throws Exception {
        // Create HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + server.getPort()))
                .GET()
                .build();

        // Send request and get response
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Print entire response body for debugging
        String responseBody = response.body();
        System.out.println("Response body:\n" + responseBody);

        // Extract thread name from response
        String threadName = responseBody.substring(
                responseBody.indexOf("Hello from virtual thread: ") + "Hello from virtual thread: ".length(),
                responseBody.indexOf("\nRequest started at")
        );

        // Print actual thread name for debugging
        System.out.println("Actual thread name: " + threadName);

        // Verify thread name format
        assertTrue(VIRTUAL_THREAD_PATTERN.matcher(threadName).matches(),
                "Thread name should match virtual-thread-<number> pattern. Actual: " + threadName);
    }
} 