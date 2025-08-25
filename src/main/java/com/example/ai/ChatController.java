package com.example.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    @Autowired
    private  ChatClient chat;

    @PostMapping("/chat")
    public Mono<String> chat(@RequestBody Map<String, String> body) {
        String user = body.getOrDefault("message", "");
        log.info("inside the chat sync method ");
        return Mono.fromSupplier(() -> chat.prompt().user(user).call().content());
    }

    @PostMapping(path = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> stream(@RequestBody Map<String, String> body) {

        String message = body.getOrDefault("message", "");

        log.info("inside the chat sse  method ");

        return chat.prompt()
                   .user(message)
                   .stream()
                   .content()
                   .map(chunk -> ServerSentEvent.builder(chunk).build());
    }
}
