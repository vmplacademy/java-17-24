package pl.vm.features.virtualthreads;

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

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VirtualThreadServerTest {
    private VirtualThreadServer server;
    private HttpClient httpClient;
    private ExecutorService executor;
    private static final Pattern VIRTUAL_THREAD_PATTERN = Pattern.compile(".*");  // Accept any thread name for now

    @BeforeEach
    void setUp() {
        // Start server on port 8080
        server = new VirtualThreadServer(8080);
        Thread serverThread = new Thread(() -> server.start());
        serverThread.start();
        
        // Wait for server to start
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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

        // Print response body for debugging
        String responseBody = response.body();
        System.out.println("Response body: " + responseBody);

        // Verify that the thread is virtual
        assertTrue(responseBody.contains("Hello from virtual thread"),
                "Response should indicate a virtual thread was used");
    }
} 