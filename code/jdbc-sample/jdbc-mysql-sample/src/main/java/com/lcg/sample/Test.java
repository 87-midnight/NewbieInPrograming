package com.lcg.sample;

import com.alibaba.fastjson.JSON;
import com.lcg.sample.connect.ConnectionManager;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;

/**
 * @author linchuangang
 * @create 2019-11-12 14:04
 **/
@Slf4j
public class Test {

    public static void main(String...args)throws Exception{
        ConnectionManager manager = new ConnectionManager();
        ResultSet rs = manager.connect("jdbc:mysql://localhost:3306/nacos_devtest?useSSL=false&serverTimezone=Asia/Shanghai","root","123456")
        .execute("select * from users");
        while (rs.next()) {
            log.info("exec result:{}", JSON.toJSONString(rs.getString(1)));
        }
        manager.close();
    }
}
