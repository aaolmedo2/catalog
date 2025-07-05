package com.banquito.core.loan.catalog.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoVerifier implements CommandLineRunner {

    private final MongoTemplate mongo;

    public MongoVerifier(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public void run(String... args) {
        System.out.println("DB conectado: " + mongo.getDb().getName());
        System.out.println("Colecciones: " + mongo.getCollectionNames());
    }
}