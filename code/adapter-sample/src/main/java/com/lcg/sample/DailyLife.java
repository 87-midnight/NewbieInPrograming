package com.lcg.sample;

import com.lcg.sample.student.*;

/**
 * 日常生活中，外国人和中国人见面后，想要互相交流无障碍，需要双向适配器的支撑
 * @see FaceToFaceAdapter
 */
public class DailyLife {

    public static void main(String...args){
        Foreigner A = new ForeignerStudent();
        Chinese B = new ChineseStudent();

        FaceToFaceAdapter faceToFace = new FaceToFaceAdapter();
        faceToFace.setChinese(B);
        faceToFace.chatInEnglish();

        faceToFace.setForeigner(A);
        faceToFace.chatInChinese();
    }
}
