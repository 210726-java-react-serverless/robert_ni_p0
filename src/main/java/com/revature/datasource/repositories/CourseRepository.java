package com.revature.datasource.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.revature.datasource.models.Course;
import com.revature.datasource.utils.MongoClientFactory;
import com.revature.utils.exceptions.DataSourceException;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class CourseRepository implements CrudRepository<Course> {

    /**
     * Takes a non-null String id to query the datasource. If the query returns a Document, map it
     * to a Course object and return it.
     *
     * @param id
     * @return
     */
    @Override
    public Course findById(String id) {
        try {
            MongoClient client = MongoClientFactory.getInstance().getClient();
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> collection = database.getCollection("courses");

            Document queryDoc = new Document("courseId", id);
            Document returnDoc = collection.find(queryDoc).first();

            if (returnDoc == null) {
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();
            Course course = mapper.readValue(returnDoc.toJson(), Course.class);
            course.setId(returnDoc.get("_id").toString());

            return course;

        } catch (JsonMappingException e) {
            throw new DataSourceException("An exception occurred while mapping the document", e);

        } catch (JsonProcessingException e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    /**
     * Takes a non-null Course object, puts its data into a Document, and attempts to persist it
     * to the database.
     *
     * @param newCourse
     * @return
     */
    @Override
    public Course save(Course newCourse) {
        try {
            MongoClient client = MongoClientFactory.getInstance().getClient();
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> collection = database.getCollection("courses");

            Document newCourseDoc = new Document("courseId", newCourse.getCourseId())
                    .append("courseName", newCourse.getCourseName())
                    .append("courseDesc", newCourse.getCourseDesc())
                    .append("registerOpen", newCourse.getRegisterOpen());

            Document returnDoc = collection.find(eq("courseId", newCourse.getCourseId())).first();

            if (returnDoc != null) {
                return null;
            }

            collection.insertOne(newCourseDoc);

            newCourse.setId(newCourseDoc.get("_id").toString());

            return newCourse;

        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    @Override
    public Course update(Course updateResource) {
        return null;
    }

    /**
     * Takes a non-null String id, searches for it in the database, and attempts to delete
     * it from the database.
     *
     * @param courseId
     * @return
     */
    @Override
    public boolean delete(String courseId) {
        try {
            MongoClient client = MongoClientFactory.getInstance().getClient();
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> collection = database.getCollection("courses");

            Document queryDoc = new Document("courseId", courseId);
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

    /**
     * Takes multiple non-null Strings and attempts to find it in the database.
     * If the query returns no results, then there is no course to update. If the
     * query returns a Course, then update it with new information, and attempt to
     * persist it to the database.
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
            MongoCollection<Document> collection = database.getCollection("courses");

            Document queryDoc = new Document("courseId", courseId);
            Document returnDoc = collection.find(queryDoc).first(); // returns the new doc

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
     * Takes a non-null course id and uses it to query the database. If query returns
     * a result, then switches the registerOpen field from 'Yes' to 'No' and vice versa before
     * attempting to persist it to the database.
     *
     * @param courseId
     * @param context
     * @return
     */
    public boolean updateCourse(String courseId, String context) {
        try {
            MongoClient client = MongoClientFactory.getInstance().getClient();
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> collection = database.getCollection("courses");

            Document queryDoc = new Document("courseId", courseId);
            Document returnDoc = collection.find(queryDoc).first(); // returns the new doc

            if (returnDoc == null) {
                return false;
            }

            String currentContext = returnDoc.get("registerOpen").toString();

            if (currentContext.equals("Yes")) {
                Document newDoc = new Document(context, "No");
                Document updateDoc = new Document("$set", newDoc);
                collection.updateOne(returnDoc, updateDoc);
            } else {
                Document newDoc = new Document(context, "Yes");
                Document updateDoc = new Document("$set", newDoc);
                collection.updateOne(returnDoc, updateDoc);
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    /**
     * Used to get all courses from the datasource and adds it to an ArrayList.
     * After all courses are added, return the ArrayList
     *
     * @return
     */
    public List<Course> findAllCourses() {
        List<Course> courses = new ArrayList<>();

        try {
            MongoClient client = MongoClientFactory.getInstance().getClient();
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> collection = database.getCollection("courses");

            MongoCursor cursor = collection.find().iterator();
            ObjectMapper mapper = new ObjectMapper();

            while (cursor.hasNext()) {
                Document doc = (Document) cursor.next();
                Course course = mapper.readValue(doc.toJson(), Course.class);
                course.setId(doc.get("_id").toString());
                courses.add(course);
            }

            return courses;

        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new DataSourceException("An exception occurred while mapping the document", e);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }
}
