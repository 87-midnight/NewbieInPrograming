package com.lcg.sample.shallowClone;

public class EchoServiceImpl implements CommonService {

    private String content;

    public EchoServiceImpl(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void print() {
        System.out.println("hello,echo,"+content);
    }

    public Object clone() {
        EchoServiceImpl obj = null;
        try {
            obj = (EchoServiceImpl) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
