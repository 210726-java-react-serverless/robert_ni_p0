package com.revature.datasource.repositories;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.revature.datasource.models.AppUser;

import com.revature.datasource.utils.MongoClientFactory;
import com.revature.utils.exceptions.DataSourceException;

import org.bson.Document;

public class UserRepository implements CrudRepository<AppUser> {

    @Override
    public AppUser findById(int id) {
        return null;
    }

    @Override
    public AppUser save(AppUser newUser) {

        try {
            MongoClient client = MongoClientFactory.getInstance().getClient();
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> collection = database.getCollection("users");

            newUser.setUserType("student");

            Document newUserDoc = new Document("firstname", newUser.getFirstname())
                    .append("lastname", newUser.getLastname())
                    .append("email", newUser.getEmail())
                    .append("username", newUser.getUsername())
                    .append("password", newUser.getPassword())
                    .append("userType", newUser.getUserType());

            collection.insertOne(newUserDoc);
            newUser.setId(newUserDoc.get("_id").toString());

            return newUser;

        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    @Override
    public AppUser update(AppUser updateResource) {
        return null;
    }

    @Override
    public boolean delete(String username) {
        return false;
    }

    public AppUser findUserByCredentials(String username, String password) {

        try {
            MongoClient client = MongoClientFactory.getInstance().getClient();
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> collection = database.getCollection("users");

            Document queryDoc = new Document("username", username).append("password", password);
            Document authUserDoc = collection.find(queryDoc).first();

            if (authUserDoc == null) {
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();
            AppUser authUser = mapper.readValue(authUserDoc.toJson(), AppUser.class);
            authUser.setId(authUserDoc.get("_id").toString());

            return authUser;

        } catch (JsonMappingException e) {
            System.out.println(e.getMessage()); // TODO logger
            throw new DataSourceException("An exception occurred while mapping the document", e);

        } catch (Exception e) {
            System.out.println(e.getMessage()); // TODO logger
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }
}
