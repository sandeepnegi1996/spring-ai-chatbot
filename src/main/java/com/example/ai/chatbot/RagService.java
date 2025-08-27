//package com.example.ai.chatbot;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.chat.model.ChatResponse;
//import org.springframework.ai.document.Document;
//
//import org.springframework.ai.vectorstore.SearchRequest;
//import org.springframework.ai.vectorstore.VectorStore;
//
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class RagService {
//
//    private final VectorStore vectorStore;
//    private final ChatClient openAiChatClient;
//
//    /** Store a document into pgvector */
//    public void addDocument(String content) {
//        Document doc = new Document(content);
//        vectorStore.add(List.of(doc));
//    }
//
//    /** Ask a question with RAG */
//    public ChatResponse ask(String question) {
//
//        List<Document> docs = vectorStore.similaritySearch(SearchRequest.builder().query(question).topK(3).build()); // top-3 docs
//
//        String context = docs.stream()
//                .map(Document::getFormattedContent)
//                .reduce("", (a, b) -> a + "\n" + b);
//
//        return  openAiChatClient.prompt().user("Answer the question using the context:\n" + context + "\n\nQuestion: " + question).call().chatResponse();
//
//    }
//}
//
//
