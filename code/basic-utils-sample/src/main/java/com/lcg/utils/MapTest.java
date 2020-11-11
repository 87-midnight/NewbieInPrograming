package com.lcg.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author linchuangang
 * @createTime 2020/11/11
 **/
public class MapTest {
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("sss","sss");
        map.put("111","222");
        // map遍历删除时，以下方式也会报错。
//        map.forEach((k,v)->{
//            if (k.equalsIgnoreCase("111")){
//                map.remove(k);
//            }
//        });
        Set<Map.Entry<String,String>> entry = map.entrySet();
        //通过entrySet来调用removeIf删除，正确
        entry.removeIf(entrys->entrys.getKey().equalsIgnoreCase("1234"));
        //通过map获取key的set集合，调用removeIf来删除，正确
        map.keySet().removeIf(x->x.equalsIgnoreCase("sss"));
    }
}
