package com.revature.utils;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.revature.utils.exceptions.ResourcePersistenceException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class MongoClientFactory {

    private static final MongoClientFactory MONGO_CLIENT_FACTORY = new MongoClientFactory();
    private MongoClient client = null;

    private MongoClientFactory() {
        Properties props = new Properties();
        try {
            props.load(new FileReader("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResourcePersistenceException("Unable to load properties file.");
        }

        String ipAddress = props.getProperty("ipAddress");
        int port = Integer.parseInt(props.getProperty("port"));
        String username = props.getProperty("username");
        char[] password = props.getProperty("password").toCharArray();
        String dbName = props.getProperty("dbName");

        List<ServerAddress> hosts = Collections.singletonList(new ServerAddress(ipAddress, port));
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(username, dbName, password);
        MongoClientSettings settings = MongoClientSettings
                .builder()
                .applyToClusterSettings(builder -> builder.hosts(hosts))
                .credential(mongoCredential)
                .build();

        try {
            client = MongoClients.create(settings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MongoClientFactory getInstance() {
        return (MONGO_CLIENT_FACTORY == null ? new MongoClientFactory() : MONGO_CLIENT_FACTORY);
    }

    public MongoClient getClient() {
        return client;
    }

    public void cleanUp() {
        client.close();
    }
}
