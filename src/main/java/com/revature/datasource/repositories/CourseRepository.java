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

public class CourseRepository implements CrudRepository<Course> {

    @Override
    public Course findById(int id) {
        return null;
    }

    @Override
    public Course save(Course newCourse) {

        try {
            MongoClient client = MongoClientFactory.getInstance().getClient();
            MongoDatabase database = client.getDatabase("p0");
            MongoCollection<Document> collection = database.getCollection("courses");

            Document newCourseDoc = new Document("courseId", newCourse.getCourseId())
                    .append("courseName", newCourse.getCourseName())
                    .append("courseDesc", newCourse.getCourseDesc())
                    .append("regOpen", newCourse.getRegOpen());

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

    public List<Course> findOpenCourses() {
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