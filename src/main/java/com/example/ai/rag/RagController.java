package com.example.ai.rag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vector")
@Slf4j
@RequiredArgsConstructor
public class RagController {

    private final RagService ragService;
    private final HRFaqCsvIngestionService hrFaqCsvIngestionService;


    @PostMapping(value = "hybrid/message")
    public ResponseEntity<String> hybrid(
            @RequestBody RequestMessageDto requestMessageDto) {
        ragService.runDemo();
        return ResponseEntity.ok("");
    }


    @GetMapping("/addhrfaq")
    public ResponseEntity<String> addhrfaq() throws Exception {
        return ResponseEntity.ok(hrFaqCsvIngestionService.ingestFAQs("src/main/resources/input/hr_faq.csv"));
    }


    //this method we can add the doc
    @PostMapping("/add")
    public String addDoc(@RequestBody RequestMessageDto requestMessageDto) {
        ragService.addDocument(requestMessageDto.question());
        return "Document added!";
    }


    //use this to ask query based on the doc
    @PostMapping("/ask")
    public ResponseEntity<ResponseMessageDto> ask(@RequestBody RequestMessageDto requestMessageDto) {

        return ResponseEntity.ok(ragService.ask(requestMessageDto.question()));
    }
}
