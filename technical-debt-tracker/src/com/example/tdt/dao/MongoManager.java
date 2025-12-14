package com.example.tdt.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoManager {
    private static final String DEFAULT_URI = "mongodb://localhost:27017";
    private static final String DEFAULT_DB = "technical_debt_db";

    private static MongoClient client;

    public static MongoDatabase getDatabase() {
        if (client == null) {
            client = MongoClients.create(DEFAULT_URI);
        }
        return client.getDatabase(DEFAULT_DB);
    }

    public static void close() {
        if (client != null) {
            client.close();
            client = null;
        }
    }
}
