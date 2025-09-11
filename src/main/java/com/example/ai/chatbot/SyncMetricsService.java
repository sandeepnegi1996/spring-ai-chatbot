//package com.example.ai.chatbot;
//
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.chat.model.ChatResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.time.Instant;
//import java.util.concurrent.atomic.AtomicLong;
//
//@Service
//@Slf4j
//public class SyncMetricsService {
//
//    @Autowired
//    private ChatClient openAiChatClient;
//
//    // store cumulative metrics
//    private final AtomicLong totalTokens = new AtomicLong();
//    private final AtomicLong totalRequests = new AtomicLong();
//    private final AtomicLong totalLatencyMs = new AtomicLong();
//
//
//    // using for chat metric for synchronous
//    public ChatResponse chatWithMetrics(String message) {
//        Instant start = Instant.now();
//
//        ChatResponse response =  openAiChatClient.prompt().user(message).call().chatResponse();
//
//        Instant end = Instant.now();
//
//        long latency = Duration.between(start, end).toMillis();
//
//        totalLatencyMs.addAndGet(latency);
//
//        totalRequests.incrementAndGet();
//
//
//        // OpenAI usage metadata (if provider supports it)
//        if (response.getMetadata() != null && response.getMetadata().getUsage() != null) {
//            totalTokens.addAndGet(response.getMetadata().getUsage().getTotalTokens());
//        }
//
//        return response;
//    }
//
//
//
//
//    public MetricsResponse getMetrics() {
//        long reqs = totalRequests.get();
//        log.info("total number of requests : {} ",reqs);
//        double avgLatency = reqs == 0 ? 0 : (double) totalLatencyMs.get() / reqs;
//        return new MetricsResponse(
//                totalTokens.get(),
//                totalRequests.get(),
//                avgLatency
//        );
//    }
//
//    public record MetricsResponse(long totalTokens, long totalRequests, double avgLatencyMs) {}
//}
