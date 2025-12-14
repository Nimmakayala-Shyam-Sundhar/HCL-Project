package com.example.tdt.dao;

import com.example.tdt.model.Dependency;
import com.example.tdt.model.Project;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ProjectDAO {

    private final MongoCollection<Document> collection;

    public ProjectDAO() {
        MongoDatabase db = MongoManager.getDatabase();
        collection = db.getCollection("projects");
    }

    public String insertProject(Project project) {
        Document doc = new Document("name", project.getName())
                .append("description", project.getDescription());

        List<Document> deps = new ArrayList<>();
        for (Dependency d : project.getDependencies()) {
            deps.add(new Document("groupId", d.getGroupId())
                    .append("artifactId", d.getArtifactId())
                    .append("version", d.getVersion()));
        }
        doc.append("dependencies", deps);

        collection.insertOne(doc);
        return doc.getObjectId("_id").toHexString();
    }

    public List<Project> listProjects() {
        List<Project> projects = new ArrayList<>();
        for (Document d : collection.find()) {
            projects.add(documentToProject(d));
        }
        return projects;
    }

    public Project getProjectById(String id) {
        Document d = collection.find(eq("_id", new ObjectId(id))).first();
        return d != null ? documentToProject(d) : null;
    }

    public boolean updateProject(String id, Project project) {
        Document updateDoc = new Document("name", project.getName())
                .append("description", project.getDescription());

        List<Document> deps = new ArrayList<>();
        for (Dependency d : project.getDependencies()) {
            deps.add(new Document("groupId", d.getGroupId())
                    .append("artifactId", d.getArtifactId())
                    .append("version", d.getVersion()));
        }
        updateDoc.append("dependencies", deps);

        return collection.replaceOne(eq("_id", new ObjectId(id)), updateDoc).getModifiedCount() > 0;
    }

    public boolean deleteProject(String id) {
        return collection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount() > 0;
    }

    // Convert Document -> Project safely (no warnings)
    private Project documentToProject(Document d) {
        Project project = new Project(d.getString("name"), d.getString("description"));
        project.setId(d.getObjectId("_id").toHexString());

        List<Document> deps = d.getList("dependencies", Document.class, new ArrayList<>());
        for (Document depDoc : deps) {
            project.addDependency(new Dependency(
                    depDoc.getString("groupId"),
                    depDoc.getString("artifactId"),
                    depDoc.getString("version")
            ));
        }
        return project;
    }
}
