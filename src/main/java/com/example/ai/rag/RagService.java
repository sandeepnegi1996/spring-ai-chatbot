package com.example.ai.rag;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RagService {

    private final ChatModel chatModel;              // GPT-4 (OpenAI)
    private final EmbeddingModel embeddingModel;   // Local (Transformers)
    private final VectorStore vectorStore;


    public void runDemo() {
        // Use GPT-4 for chat
        String reply = chatModel.call("Explain hybrid AI setup in simple terms.");
        System.out.println("Chat response: " +  reply);

        // Use Transformers for embeddings
        List<Double> embedding = embeddingModel.embed("hybrid ai setup");
        System.out.println("Embedding vector length: " + embedding.toString());
    }


    public void addDocument(String content) {
        Document doc = new Document(content);
        vectorStore.add(List.of(doc));
    }

    /** Ask a question with RAG */
    public String ask(String question) {

//        List<Document> docs = vectorStore.similaritySearch(SearchRequest.builder().query(question).topK(3).build()); // top-3 docs
        List<Document> docs = vectorStore.similaritySearch(SearchRequest.query(question).withTopK(3)); // top-3 docs

        String context = docs.stream()
                .map(Document::getFormattedContent)
                .reduce("", (a, b) -> a + "\n" + b);

       return   chatModel.call("Answer the question using the context:\n" + context + "\n\nQuestion: " + question);

//        return  chatModel.prompt().user("Answer the question using the context:\n" + context + "\n\nQuestion: " + question).call().chatResponse();

    }

}
