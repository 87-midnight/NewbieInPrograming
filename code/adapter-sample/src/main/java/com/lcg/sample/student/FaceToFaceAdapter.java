package com.lcg.sample.student;

public class FaceToFaceAdapter implements Chinese,Foreigner {

    private Chinese chinese;
    private Foreigner foreigner;

    public void setChinese(Chinese chinese) {
        this.chinese = chinese;
    }

    public void setForeigner(Foreigner foreigner) {
        this.foreigner = foreigner;
    }

    public void chatInChinese() {
        foreigner.chatInEnglish();
    }

    public void chatInEnglish() {
        chinese.chatInChinese();
    }
}
