package com.lcg.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Examine {

    protected List<Student> students = new ArrayList<>();

    public Examine(List<Student> students) {
        this.students = students;
    }

    public abstract void notifyStudent();

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
