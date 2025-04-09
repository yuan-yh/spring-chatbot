package com.ai.aidemo;

import com.ai.aidemo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;

@SpringBootTest
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testMongo() {
        this.mongoTemplate.createCollection("test");
    }

    @Test
    void testInsert() {
        this.mongoTemplate.insert(new User(1L, "Robin", 12, "drake"));
        this.mongoTemplate.insert(Arrays.asList(new User(2L, "Robin", 15, "nwing"),
                new User(3L, "Black", 14, "ccass"),
                new User(4L, "Robin", 13, "rhood"),
                new User(5L, "Robin", 11, "spoiler")), User.class);
    }

    @Test
    void testFind() {
        System.out.println(this.mongoTemplate.findById(1L, User.class));
        this.mongoTemplate.findAll(User.class).forEach(System.out::println);

        Query query = new Query(Criteria.where("age").gt(12));
        System.out.println(this.mongoTemplate.findOne(query, User.class));

        this.mongoTemplate.find(Query.query(Criteria.where("age").gt(10).lt(15).and("name").regex("Robin")), User.class).forEach(System.out::println);

        // Remove Duplicates
        this.mongoTemplate.findDistinct(("name"), User.class, String.class).forEach(System.out::println);

        // Sort
        this.mongoTemplate.find(Query.query(Criteria.where("age").gt(10).lt(15).and("name").regex("Robin")).with(Sort.by(Sort.Order.desc("age"))), User.class).forEach(System.out::println);

        // Pagination
        this.mongoTemplate.find(Query.query(Criteria.where("name").regex("Robin")).with(Sort.by(Sort.Order.desc("age"))).limit(1).skip(2), User.class).forEach(System.out::println);

        this.mongoTemplate.find(Query.query(Criteria.where("name").regex("Robin")).with(PageRequest.of(0, 1, Sort.by(Sort.Order.desc("age")))), User.class).forEach(System.out::println);
    }

    @Test
    void testUpdate() {
        this.mongoTemplate.updateFirst(Query.query(Criteria.where("name").is("Robin")), new org.springframework.data.mongodb.core.query.Update().set("age", 16), User.class);
        this.mongoTemplate.updateMulti(Query.query(Criteria.where("name").regex("bin")), Update.update("age", 18), User.class);
    }

    @Test
    void testDelete() {
        this.mongoTemplate.remove(Query.query(Criteria.where("name").is("Robin")), User.class);
    }
}
