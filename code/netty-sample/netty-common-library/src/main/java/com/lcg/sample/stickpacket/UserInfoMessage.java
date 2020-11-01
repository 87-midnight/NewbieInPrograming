package com.lcg.sample.stickpacket;

import lombok.Data;

import java.io.Serializable;

/**
 * @author linchuangang
 * @createTime 2020/11/2
 **/
@Data
public class UserInfoMessage implements Serializable {

    private int length;
    private byte[] content;
    private long timestamp;
}
