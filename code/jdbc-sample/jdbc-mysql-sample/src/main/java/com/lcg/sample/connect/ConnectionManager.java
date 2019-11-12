package com.lcg.sample.connect;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * 连接管理
 * https://dev.mysql.com/doc/connector-j/8.0/en/
 * @author linchuangang
 * @create 2019-11-12 14:05
 *
 **/
@Slf4j
public class ConnectionManager {

    private Connection connection;
    private PreparedStatement stmt;
    private ResultSet rs;


    public ConnectionManager connect(String url,String username,String password){
        try {
            connection = DriverManager.getConnection(url,username,password);
        }catch (Exception var1){
            log.error("connection init failed",var1);
        }
        return this;
    }

    public ResultSet execute(String sql){
        try {
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            log.error("exec sql failed",e);
        }
        return rs;
    }
    public ConnectionManager close() throws SQLException {
        this.connection.close();
        stmt.close();
        this.rs.close();
        return this;
    }
}
