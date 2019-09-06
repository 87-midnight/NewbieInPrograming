package com.lcg.sample.clazz;

/**
 * @author linchuangang
 * @create 2019-09-06 19:37
 **/
public class InnerClass {

    private Long id;
    private Student student;
    private Teacher teacher;

    public Student newStudent(long userId){
        Student student = new Student();
        student.setUserId(userId);
        return student;
    }

    protected class Student{
        private Long userId;

        public Student(){

        }
        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }

    public static class Teacher{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
