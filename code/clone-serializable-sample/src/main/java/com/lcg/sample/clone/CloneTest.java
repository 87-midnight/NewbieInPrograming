package com.lcg.sample.clone;

import com.lcg.sample.clone.deep.Human;
import com.lcg.sample.clone.deep.People;
import com.lcg.sample.clone.deep.SerializeClone;
import com.lcg.sample.clone.shallow.Female;

/**
 * @author linchuangang
 * @create 2019-09-06 17:19
 **/
public class CloneTest {

    public static void main(String[]args) throws Exception {
        //使用clone，深度拷贝方式
        Human human = new Human();
        human.setName("human");
        Human.HumanBeing being = new Human.HumanBeing();
        being.setAge(12);
        human.setBeing(being);
        Human clone = (Human) human.clone();
        //两个对象的属性值完全相同，结果为true
        System.out.println(human.getName().equals(clone.getName()));
        //默认object里的equal是比较reference，两个不是同个对象，故为false
        System.out.println(human.equals(clone));
        //使用clone，深度拷贝方式

        //序列化深度克隆
        People people = new People();
        people.setName("Tom");
        People peopleClone = SerializeClone.deepClone(people);
        //两个对象的属性值完全相同，结果为true
        System.out.println(people.getName().equals(peopleClone.getName()));
        //对象引用不同，结果为false
        System.out.println(people.equals(peopleClone));
        //序列化深度克隆

        //浅拷贝
        Female female = new Female();
        Female.Girl girl = new Female.Girl();
        female.setId("123");
        girl.setFeature("tall");
        female.setGirl(girl);
        Female femaleClone = (Female) female.clone();
        //对象引用不同，结果为false
        System.out.println(female.equals(femaleClone));
        //由于是浅拷贝，成员对象只是引用同一个，结果为true
        System.out.println(female.getGirl().equals(femaleClone.getGirl()));
        //浅拷贝
    }
}
