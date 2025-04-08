package com.ai.aidemo;

import com.google.common.util.concurrent.ListenableFuture;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class AiDemoApplicationTests {

    @Autowired
    VectorStore vectorStore;

    /**
     * Initiate a vector database
     */
    @Test
    void contextLoads() throws ExecutionException, InterruptedException {
        // Integrate gRPC API port
        QdrantClient qdrantClient = new QdrantClient(QdrantGrpcClient.newBuilder("localhost", 6334, false).build());
        ListenableFuture<Collections.CollectionOperationResponse> future = qdrantClient.createCollectionAsync("test", Collections.VectorParams.newBuilder()
                .setSize(3).setDistance(Collections.Distance.Cosine).build());
        System.out.println(future.get());
    }

    /**
     * Load data - must base on the embedding model
     */
    @Test
    void loadData() {
        List<Document> documents = List.of(
                new Document("Red Seagull", Map.of("name", "RS", "age", 12)),
                new Document("Black Mask"),
                new Document("Red Robin", Map.of("name", "RR", "age", 12)),
                new Document("Silver Raven", Map.of("name", "SR", "age", 12)),
                new Document("MagPie", Map.of("name", "MP")));

        // Add the documents to Qdrant
        vectorStore.add(documents);
    }

    /**
     * Search similar data points
     */
    @Test
    void searchSimilarity() {
        // Retrieve documents similar to a query
        var b = new FilterExpressionBuilder();
        var exp = b.and(b.gt("age", 10), b.in("name", List.of("SR", "RS", "SR")));
        List<Document> results = vectorStore.similaritySearch(SearchRequest.builder()
                .query("bird")
                .topK(5)
//                .similarityThreshold(0.5)
//                .filterExpression("name in ['SR', 'RS', 'SR'] && age >= 10")
                .filterExpression(exp.build())
                .build());
        results.forEach(System.out::println);
    }
}
