package com.example.ai.rag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class HRFaqCsvIngestionService {

    private final VectorStore vectorStore;


    public String ingestFAQs(String filePath) throws Exception {
        try (FileReader reader = new FileReader(filePath, StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim())) {

            List<Document> documents = new ArrayList<>();

            for (CSVRecord record : csvParser) {
                String id = record.get("id");
                String question = record.get("question");
                String answer = record.get("answer");

                Document doc = new Document(
                        question + " -> " + answer,
                        Map.of("id", id, "question", question, "answer", answer)
                );

                documents.add(doc);
            }

            vectorStore.add(documents);
            log.info("Ingested " + documents.size() + " FAQs from CSV into pgvector.");

            return "documents added successfully";
        }
    }
}

