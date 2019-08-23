package com.lcg.sample;

import com.lcg.sample.student.Chinese;
import com.lcg.sample.student.ChineseAdapter;
import com.lcg.sample.student.ChineseStudent;
import com.lcg.sample.student.Foreigner;
import com.lcg.sample.student.ForeignerStudent;

public class EnglishClassroom {

    private Foreigner foreigner;

    public void setForeigner(Foreigner foreigner) {
        this.foreigner = foreigner;
    }

    public void communicateInEnglish(){
        foreigner.chatInEnglish();
    }

    public static void main(String...args){
        //英语课开始上课
        EnglishClassroom classroom = new EnglishClassroom();
        //一位外国人准备好了发言
        Foreigner A = new ForeignerStudent();
        classroom.setForeigner(A);
        //A在课堂上使用了英语发言
        classroom.communicateInEnglish();

        //这时来了一位中国学生，并且想好了要说的话
        Chinese B = new ChineseStudent();
        //把原话交给适配器
        ChineseAdapter chineseAdapter = new ChineseAdapter(B);
        //课堂容纳适配器
        classroom.setForeigner(chineseAdapter);
        //在适配器的作用下B同学能无障碍发言了
        classroom.communicateInEnglish();
    }
}
