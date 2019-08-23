package com.lcg.sample.student;

public class ChineseAdapter implements Foreigner {

    private Chinese chinese;

    public ChineseAdapter(Chinese chinese) {
        this.chinese = chinese;
    }

    public void chatInEnglish() {
        System.out.print("我能让说中文的人在英语课堂上与外国人交流:\t");
        chinese.chatInChinese();
    }
}
