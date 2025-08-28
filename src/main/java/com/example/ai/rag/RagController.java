package com.example.ai.rag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vector")
@Slf4j
@RequiredArgsConstructor
public class RagController {

    private final RagService ragService;


    @PostMapping(value = "hybrid/message")
    public ResponseEntity<String> hybrid(
            @RequestBody RequestMessageDto requestMessageDto
    ) {
        ragService.runDemo();
        return ResponseEntity.ok("");
    }

    //this method we can add the doc
    @PostMapping("/add")
    public String addDoc(@RequestBody RequestMessageDto requestMessageDto) {
        ragService.addDocument(requestMessageDto.message());
        return "Document added!";
    }


    //use this to ask query based on the doc
    @PostMapping("/ask")
    public ResponseEntity<String> ask(@RequestBody RequestMessageDto requestMessageDto) {

        return ResponseEntity.ok(ragService.ask(requestMessageDto.message()));
    }
}
