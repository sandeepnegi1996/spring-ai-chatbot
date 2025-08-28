//package com.example.ai.chatbot;
//
//import org.springframework.http.codec.ServerSentEvent;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.chat.model.Generation;
//import org.springframework.ai.chat.model.ChatResponse;
//
//import java.time.Duration;
//import java.time.Instant;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.atomic.AtomicLong;
//
//@Service
//@RestController
//@RequestMapping("/chat")
//public class StreamingMetricsControllerService {
//
//    private final ChatClient openAiChatClient;
//
//
//    // Metrics
//    private final AtomicInteger totalRequests = new AtomicInteger();
//    private final AtomicLong totalInputTokens = new AtomicLong();
//    private final AtomicLong totalOutputTokens = new AtomicLong();
//    private final AtomicLong totalOutputChars = new AtomicLong();
//    private final AtomicLong totalLatencyMs = new AtomicLong();
//
//    public StreamingMetricsControllerService(ChatClient.Builder chatClientBuilder) {
//        this.openAiChatClient = chatClientBuilder.build();
//    }
//
//    /**
//     * SSE Endpoint for streaming chat responses
//     */
//    @PostMapping(value = "/stream", produces = "text/event-stream")
//    public Flux<ServerSentEvent<String>> streamChat(@RequestBody Map<String, String> request) {
//        String prompt = request.get("message");
//        Instant start = Instant.now();
//        totalRequests.incrementAndGet();
//
//        // Stream chat responses
//        Flux<ChatResponse> responseFlux = openAiChatClient.prompt()
//                .user(prompt)
//                .stream()
//                .chatResponse();
//
//        // Share stream for metrics & SSE
//        Flux<ChatResponse> shared = responseFlux.share();
//
//        // For metrics: capture input/output tokens
//        shared.subscribe(cr -> {
//            if (cr.getMetadata() != null) {
//                totalInputTokens.addAndGet(cr.getMetadata().getUsage().getPromptTokens());
//                totalOutputTokens.addAndGet(cr.getMetadata().getUsage().getCompletionTokens());
//            }
//        });
//
//        // SSE stream
//        return shared.map(cr -> {
//            String text = cr.getResults().stream()
//                    .map(Generation::getOutput)
//                    .filter(o -> o != null && o.getText() != null)
//                    .map(o -> o.getText())
//                    .reduce("", String::concat);
//
//            totalOutputChars.addAndGet(text.length());
//            return ServerSentEvent.builder(text).build();
//        }).doFinally(signal -> {
//            long latency = Duration.between(start, Instant.now()).toMillis();
//            totalLatencyMs.addAndGet(latency);
//        });
//    }
//
//    /**
//     * Metrics Endpoint
//     */
//    @GetMapping("/stream/metrics")
//    public Mono<String> metrics() {
//        long avgLatency = totalRequests.get() > 0
//                ? totalLatencyMs.get() / totalRequests.get()
//                : 0;
//
//        return Mono.just(
//                "Total Requests: " + totalRequests.get() + "\n" +
//                        "Input Tokens: " + totalInputTokens.get() + "\n" +
//                        "Output Tokens: " + totalOutputTokens.get() + "\n" +
//                        "Output Characters: " + totalOutputChars.get() + "\n" +
//                        "Average Latency (ms): " + avgLatency
//        );
//    }
//
//
//}
