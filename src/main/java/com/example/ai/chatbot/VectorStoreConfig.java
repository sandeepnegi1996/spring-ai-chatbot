//package com.example.ai.chatbot;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import org.springframework.ai.embedding.EmbeddingModel;
//import org.springframework.ai.document.MetadataMode;
//import org.springframework.ai.openai.api.OpenAiApi;
//import org.springframework.ai.openai.OpenAiEmbeddingModel;
//import org.springframework.ai.openai.OpenAiEmbeddingOptions;
//import org.springframework.ai.vectorstore.VectorStore;
//import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
//
//@Configuration
//public class VectorStoreConfig {
//
//    /**
//     * OpenAiApi client (use builder). Reads API key + optional base URL from properties.
//     * Uses OpenAiApi.builder().apiKey(...) which is the supported construction method.
//     */
//    @Bean
//    public OpenAiApi openAiApi(
//            @Value("${spring.ai.openai.api-key}") String apiKey,
//            @Value("${spring.ai.openai.base-url:https://api.openai.com/v1}") String baseUrl) {
//
//        return OpenAiApi.builder()
//                .apiKey(apiKey)     // convenience overload accepts String
//                .baseUrl(baseUrl)
//                .build();
//    }
//
//    /**
//     * EmbeddingModel backed by OpenAI.
//     * - You can pass MetadataMode (e.g., EMBED) and OpenAiEmbeddingOptions (model + dims).
//     * - If you don't want to set options, you can also call `new OpenAiEmbeddingModel(openAiApi)`.
//     */
//    @Bean
//    public EmbeddingModel embeddingModel(
//            OpenAiApi openAiApi,
//            @Value("${app.embeddings.model:text-embedding-3-small}") String model,
//            @Value("${app.embeddings.metadata-mode:EMBED}") String metadataModeStr,
//            @Value("${app.embeddings.dimensions:1536}") Integer dimensions) {
//
//        MetadataMode metadataMode = MetadataMode.valueOf(metadataModeStr.toUpperCase());
//
//        OpenAiEmbeddingOptions options = OpenAiEmbeddingOptions.builder()
//                .model(model)
//                .dimensions(dimensions)      // optional; PgVectorStore uses this to create vector column
//                .build();
//
//        // Use constructor that accepts API client, metadata mode and options.
//        return new OpenAiEmbeddingModel(openAiApi, metadataMode, options);
//    }
//
//    /**
//     * PgVectorStore bean (VectorStore). Uses the builder API (correct approach).
//     * - initializeSchema toggles automatic table/extension creation (requires DB privileges).
//     * - dimensions: if <= 0, PgVectorStore will use embeddingModel.dimensions() or default.
//     */
//    @Bean
//    public VectorStore vectorStore(
//            JdbcTemplate jdbcTemplate,
//            EmbeddingModel embeddingModel,
//            @Value("${app.pgvector.schemaName:public}") String schemaName,
//            @Value("${app.pgvector.tableName:vector_store}") String tableName,
//            @Value("${app.pgvector.dimensions:0}") int dimensions,
//            @Value("${app.pgvector.distanceType:COSINE_DISTANCE}") String distanceTypeStr,
//            @Value("${app.pgvector.indexType:HNSW}") String indexTypeStr,
//            @Value("${app.pgvector.initializeSchema:true}") boolean initializeSchema
//    ) {
//
//        PgVectorStore.PgVectorStoreBuilder builder = PgVectorStore.builder(jdbcTemplate, embeddingModel)
//                .schemaName(schemaName)
//                .vectorTableName(tableName)
//                .initializeSchema(initializeSchema);
//
//        // optional dimensions override
//        if (dimensions > 0) {
//            builder = builder.dimensions(dimensions);
//        }
//
//        // distance type (COSINE_DISTANCE | EUCLIDEAN_DISTANCE | NEGATIVE_INNER_PRODUCT)
//        builder = builder.distanceType(PgVectorStore.PgDistanceType.valueOf(distanceTypeStr));
//
//        // index type (HNSW | IVFFLAT | NONE)
//        builder = builder.indexType(PgVectorStore.PgIndexType.valueOf(indexTypeStr));
//
//        return builder.build();
//    }
//
//    // Optional: If you don't have a DataSource auto-configured, you can uncomment & configure:
//    //
//    // @Bean
//    // public DataSource dataSource(@Value("${spring.datasource.url}") String url,
//    //                              @Value("${spring.datasource.username}") String user,
//    //                              @Value("${spring.datasource.password}") String pass) {
//    //     return DataSourceBuilder.create().url(url).username(user).password(pass).build();
//    // }
//}
//
