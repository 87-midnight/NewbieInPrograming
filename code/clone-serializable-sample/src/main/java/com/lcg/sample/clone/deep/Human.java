package com.lcg.sample.clone.deep;

/**
 * @author linchuangang
 * @create 2019-09-06 17:31
 **/
public class Human implements Cloneable {

    private String name;
    private HumanBeing being;


    public HumanBeing getBeing() {
        return being;
    }

    public void setBeing(HumanBeing being) {
        this.being = being;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Human clone = (Human) super.clone();
        //深度拷贝，当human里包含成员对象时，则该对象的类需实现cloneable接口，重写clone方法
        clone.being = (HumanBeing) being.clone();
        return clone;
    }

    public static class HumanBeing implements Cloneable{
        private Integer age;

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
