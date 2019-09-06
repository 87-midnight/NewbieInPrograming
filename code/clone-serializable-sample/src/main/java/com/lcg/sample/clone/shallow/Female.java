package com.lcg.sample.clone.shallow;

/**
 * 浅拷贝
 * @author linchuangang
 * @create 2019-09-06 18:00
 **/
public class Female implements Cloneable {

    private String id;

    private Girl girl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Girl getGirl() {
        return girl;
    }

    public void setGirl(Girl girl) {
        this.girl = girl;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static class Girl{
        private String feature;

        public String getFeature() {
            return feature;
        }

        public void setFeature(String feature) {
            this.feature = feature;
        }
    }
}
