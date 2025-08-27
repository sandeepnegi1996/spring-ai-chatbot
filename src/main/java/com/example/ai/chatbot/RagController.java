//package com.example.ai.chatbot;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.ai.chat.model.ChatResponse;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/rag")
//@RequiredArgsConstructor
//public class RagController {
//
//    private final RagService ragService;
//
//
//    @PostMapping("/add")
//    public String addDoc(@RequestBody Map<String, String> request) {
//        String content = request.get("message");
//        ragService.addDocument(content);
//        return "Document added!";
//    }
//
//    @PostMapping("/ask")
//    public ChatResponse ask(@RequestBody Map<String, String> request) {
//        String message = request.get("message");
//        return ragService.ask(message);
//    }
//}
