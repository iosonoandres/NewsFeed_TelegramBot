package com.example.inferno_fx;

import javafx.beans.property.SimpleStringProperty;

//classe Employee per prova
public class Employee {

    private final SimpleStringProperty name;
    private final SimpleStringProperty department;

    Employee(String name, String department) {
        this.name = new SimpleStringProperty(name);
        this.department = new SimpleStringProperty(department);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String fName) {
        name.set(fName);
    }

    public String getDepartment() {
        return department.get();
    }

    public void setDepartment(String fName) {
        department.set(fName);
    }
}
