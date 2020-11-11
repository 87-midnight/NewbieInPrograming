package com.lcg.utils;

import java.util.*;

/**
 * @author linchuangang
 * @createTime 2020/11/8
 **/
public class CollectionTest {

    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("sss1");
        arrayList.add("sss");
        arrayList.add("xxx");
//        arrayList.remove("sss");

        List<String> list = new ArrayList<>();
        list.add("sss");
        list.remove("sss");
        Set<String> set = new HashSet<>();
        set.add("sss");
        set.add("ssss");
        set.add("sss1s");
//        set.remove("sss");

        //遍历删除指定元素时，以下的遍历方式会报ConcurrentModificationException错误，
        //在遍历删除元素时，需要使用迭代器
//        arrayList.forEach(x->{
//            if (x.equalsIgnoreCase("sss")){
//                arrayList.remove(x);
//            }
//        });
        Iterator<String> iterator = arrayList.iterator();
        while (iterator.hasNext()){
            String s = iterator.next();
            if (s.equalsIgnoreCase("ssss")){
                iterator.remove();
            }
        }
        //或者使用removeIf方法
        arrayList.removeIf(s->s.equalsIgnoreCase("sss"));

    }
}
