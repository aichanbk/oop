package com.hust.quiz.Models;

public class Category {
    private int id_number;
    private String name;
    private int parent_id = 0;
    private int course_count;
    private String category_info;

    public Category(int id_number, String name, int parent_id, int course_count, String category_info) {
        this.id_number = id_number;
        this.name = name;
        this.parent_id = parent_id;
        this.course_count = course_count;
        this.category_info = category_info;
    }

    public static String getName(String category_name) {
        int openParenIndex = category_name.indexOf(" (");
        if (openParenIndex < 0) { // If we have no " ("
            openParenIndex = category_name.length();
        }
        return category_name.substring(0, openParenIndex);
    }

    @Override
    public String toString() {
        if (course_count == 0) {
            return name;
        } else {
            return name + " (" + course_count + ")";
        }
    }

    public int getId() {
        return id_number;
    }

    public String getName() {
        return name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public String getCategory_info() {
        return category_info;
    }

    public int getCourse_count() {
        return course_count;
    }
}