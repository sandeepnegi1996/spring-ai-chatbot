package com.example.ai.chatbot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class SyncChatController {

    private final SyncMetricsService syncMetricsService;
    private final StreamingMetricsControllerService streamingMetricsService;


    // sync chat controller and it will store the metrics also
    @PostMapping("/chat/sync")
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        ChatResponse response = syncMetricsService.chatWithMetrics(userMessage);

        return Map.of("response", response.getResult().getOutput().getText());
    }



    // metrics generateed by the sync controller is called from here
    @GetMapping("/sync/metrics")
    public SyncMetricsService.MetricsResponse getMetrics() {
        log.info("returnoing the metric service response ");
        return syncMetricsService.getMetrics();
    }
}
