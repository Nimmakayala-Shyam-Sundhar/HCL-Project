package com.example.tdt;

import com.example.tdt.dao.MongoManager;
import com.example.tdt.dao.ProjectDAO;
import com.example.tdt.model.Dependency;
import com.example.tdt.model.Project;
import com.example.tdt.model.VulnerabilityReport;
import com.example.tdt.service.VulnerabilityService;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("== Technical Debt Tracker Demo ==");

        ProjectDAO dao = new ProjectDAO();
        VulnerabilityService vulnService = new VulnerabilityService();

        Project project = new Project("Example App", "A demo project to track dependencies and vulnerabilities");
        project.addDependency(new Dependency("org.apache.logging.log4j", "log4j-core", "2.8.0"));
        project.addDependency(new Dependency("commons-io", "commons-io", "2.4"));
        project.addDependency(new Dependency("com.google.guava", "guava", "30.1.1-jre"));

        String id = dao.insertProject(project);
        System.out.println("Created project with id: " + id);

        List<Project> list = dao.listProjects();
        System.out.println("\nProjects in DB:");
        for (Project pr : list) System.out.println(pr);

        VulnerabilityReport report = vulnService.checkVulnerabilities(project);
        System.out.println("\n" + report);

        Project recommended = vulnService.recommendDependencyUpdates(project);
        System.out.println("\nRecommended dependency updates (applied to copy):");
        System.out.println(recommended);

        System.out.println("Updating project in DB with recommended versions...");
        recommended.setId(id);
        boolean updated = dao.updateProject(id, recommended);
        System.out.println("Update success: " + updated);

        Project fetched = dao.getProjectById(id);
        System.out.println("\nFetched after update:");
        System.out.println(fetched);

        MongoManager.close();
        System.out.println("\nDemo complete.");
    }
}
