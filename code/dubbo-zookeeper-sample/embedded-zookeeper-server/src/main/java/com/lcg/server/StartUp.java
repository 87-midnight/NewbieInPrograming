package com.lcg.server;

public class StartUp {

    public static void main(String...args){
        EmbeddedZookeeper server = new EmbeddedZookeeper(2181,false);
        server.start();
    }
}
