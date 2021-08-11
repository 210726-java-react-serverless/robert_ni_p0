package com.revature.datasource.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.revature.datasource.models.Schedule;
import com.revature.datasource.utils.MongoClientFactory;
import com.revature.utils.exceptions.DataSourceException;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ScheduleRepository implements CrudRepository<Schedule> {

    @Override
    public Schedule findById(String id) {
        return null;
    }

    /**
     * Takes a non-null Schedule, copies its data to a Document, and attempts to persist it
     * to the datasource
     *
     * @param newSchedule
     * @return
     */
    @Override
    public Schedule save(Schedule newSchedule) {
        try {
            MongoClient client = MongoClientFactory.getInstance().getClient();
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> collection = database.getCollection("schedules");

            Document newScheduleDoc = new Document("username", newSchedule.getUsername())
                    .append("courseId", newSchedule.getCourseId())
                    .append("courseName", newSchedule.getCourseName())
                    .append("courseDesc", newSchedule.getCourseDesc());

            collection.insertOne(newScheduleDoc);
            newSchedule.setId(newScheduleDoc.get("_id").toString());

            return newSchedule;

        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    @Override
    public Schedule update(Schedule updateSchedule) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        try {
            MongoClient client = MongoClientFactory.getInstance().getClient();
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> collection = database.getCollection("schedules");

            Document queryDoc = new Document("courseId", id);

            MongoCursor cursor = collection.find(queryDoc).iterator();
            while (cursor.hasNext()) {
                Document removeDoc = (Document) cursor.next();
                collection.deleteOne(removeDoc);
            }

            return true;

        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    /**
     * Takes in non-null Strings, copies them to a Document, and attempts to query the datasource
     * for the Document. If the query returns a Document, map its data to a Schedule and return it
     *
     * @param username
     * @param id
     * @return
     */
    public Schedule findByUser(String username, String id) {
        try {
            MongoClient client = MongoClientFactory.getInstance().getClient();
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> collection = database.getCollection("schedules");

            Document queryDoc = new Document("username", username)
                    .append("courseId", id);
            Document returnDoc = collection.find(queryDoc).first();

            if (returnDoc == null) {
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();

            Schedule schedule = mapper.readValue(returnDoc.toJson(), Schedule.class);
            schedule.setId(returnDoc.get("_id").toString());

            return schedule;

        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    /**
     * When a course is updated in the database, the course will also be modified inside
     * the schedules
     *
     * @param courseId
     * @param context
     * @param newInfo
     * @return
     */
    public boolean updateCourse(String courseId, String context, String newInfo) {
        try {
            MongoClient client = MongoClientFactory.getInstance().getClient();
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> collection = database.getCollection("schedules1");

            Document queryDoc = new Document("courseId", courseId);
            Document returnDoc = collection.find(queryDoc).first();

            if (returnDoc == null) {
                return false;
            }

            Document newDoc = new Document(context, newInfo);
            Document updateDoc = new Document("$set", newDoc);
            collection.updateOne(returnDoc, updateDoc);

            return true;

        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    /**
     * Used to get all schedules from the datasource and adds it to an ArrayList.
     * After all schedules are added, return the ArrayList
     *
     * @return
     */
    public List<Schedule> getAllSchedules() {
        List<Schedule> schedules = new ArrayList<>();

        try {
            MongoClient client = MongoClientFactory.getInstance().getClient();
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> collection = database.getCollection("schedules");

            MongoCursor cursor = collection.find().iterator();
            ObjectMapper mapper = new ObjectMapper();

            while (cursor.hasNext()) {
                Document doc = (Document) cursor.next();
                Schedule schedule = mapper.readValue(doc.toJson(), Schedule.class);
                schedule.setId(doc.get("_id").toString());
                schedules.add(schedule);
            }

            return schedules;

        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new DataSourceException("An unexpected error occurred", e);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    /**
     * Takes in non-null Strings, uses them to search the datasource, and
     * if a result is returned, attempt to remove it from the datasource
     *
     * @param username
     * @param courseId
     * @return
     */
    public boolean deleteSchedule(String username, String courseId) {
        try {
            MongoClient client = MongoClientFactory.getInstance().getClient();
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> collection = database.getCollection("schedules");

            Document queryDoc = new Document("username", username)
                    .append("courseId", courseId);
            Document removeDoc = collection.find(queryDoc).first();

            if (removeDoc == null) {
                return false;
            }

            collection.deleteOne(removeDoc);

            return true;
            
        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }
}
