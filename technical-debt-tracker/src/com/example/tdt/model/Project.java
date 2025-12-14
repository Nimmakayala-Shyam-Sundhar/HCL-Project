package com.example.tdt.model;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String id;
    private String name;
    private String description;
    private List<Dependency> dependencies = new ArrayList<>();

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setId(String id) { this.id = id; }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    public List<Dependency> getDependencies() { return dependencies; }
    public void addDependency(Dependency d) { dependencies.add(d); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Project[id=").append(id)
          .append(", name=").append(name)
          .append(", description=").append(description)
          .append(", dependencies=\n");
        for (Dependency d : dependencies) {
            sb.append("    ").append(d).append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
