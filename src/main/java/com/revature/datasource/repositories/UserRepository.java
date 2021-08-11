package com.revature.datasource.repositories;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.revature.datasource.models.AppUser;

import com.revature.datasource.utils.MongoClientFactory;
import com.revature.utils.exceptions.DataSourceException;

import com.revature.utils.exceptions.ResourcePersistenceException;
import org.bson.Document;

public class UserRepository implements CrudRepository<AppUser> {

    @Override
    public AppUser findById(String id) {
        return null;
    }

    /**
     * Takes in non-null AppUser, copy data from AppUser to Document, and attempt to
     * persist the Document to the datasource.
     *
     * @param newUser
     * @return
     */
    @Override
    public AppUser save(AppUser newUser) {
        newUser.setUsername(newUser.getUsername().toLowerCase());

        try {
            MongoClient client = MongoClientFactory.getInstance().getClient();
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> collection = database.getCollection("users");

            if (userExists(newUser.getUsername())) {
                throw new ResourcePersistenceException("That username is already taken!");
            }

            newUser.setUserType("student");

            Document newUserDoc = new Document("firstname", newUser.getFirstname())
                    .append("lastname", newUser.getLastname())
                    .append("username", newUser.getUsername())
                    .append("password", newUser.getPassword())
                    .append("userType", newUser.getUserType());


            collection.insertOne(newUserDoc);
            newUser.setId(newUserDoc.get("_id").toString());

            return newUser;

        } catch (ResourcePersistenceException e) {
            throw new ResourcePersistenceException(e.getMessage());

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

    /**
     * Takes in non-null Strings and uses them to query the database.
     * If a result is returned, get the Document, map its fields to an AppUser,
     * and return the AppUser
     *
     * @param username
     * @param password
     * @return
     */
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
            throw new DataSourceException("An exception occurred while mapping the document", e);

        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    /**
     * Takes a non-null String, searches the datasource for the name,
     * and returns false if the user is not in datasource
     *
     * @param username
     * @return
     */
    public boolean userExists(String username) {
        try {
            MongoClient client = MongoClientFactory.getInstance().getClient();
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> collection = database.getCollection("users");

            Document queryDoc = new Document("username", username);
            Document returnDoc = collection.find(queryDoc).first();

            return returnDoc != null;

        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }
}
