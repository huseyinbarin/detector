package com.barin.domain;

public class Company {
    private final String id;
    private final String name;
    private final String parenthesisName;


    public Company(String id, String name, String parenthesisName) {
        this.id = id;
        this.name = name;
        this.parenthesisName = parenthesisName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParenthesisName() {
        return parenthesisName;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parenthesisName='" + parenthesisName + '\'' +
                '}';
    }
}
