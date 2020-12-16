package com.lcg.observer;

import java.util.ArrayList;
import java.util.List;

public class MathExamine extends Examine{

    public MathExamine(List<Student> students) {
        super(students);
    }

    @Override
    public void notifyStudent() {
        String command = "不用考试，回宿舍睡觉";
        students.stream().filter(x->x.type() == Student.Subject.math).forEach(y->y.response(command));
    }

    public static void main(String[] args) {
        List<Student> students = new ArrayList<Student>(){{
            add(new MathStudent());
            add(new MathStudent());
            add(new MathStudent());
        }};
        Examine examine = new MathExamine(students);
        examine.notifyStudent();
    }
}
