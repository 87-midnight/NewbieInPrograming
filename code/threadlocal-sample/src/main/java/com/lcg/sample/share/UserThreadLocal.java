package com.lcg.sample.share;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author linchuangang
 * @createTime 2020/11/2
 **/
public class UserThreadLocal {

    private static ThreadLocal<UserInfo> localUsers = new ThreadLocal<UserInfo>(){
        /**
         * <p>方法描述:ThreadLocal没有被当前线程赋值时或当前线程刚调用remove方法后调用get方法，返回此方法值</p>
         * @author linchuangang
         * @create 2020/11/2 2:42
         * @return com.lcg.sample.share.UserInfo
         **/
        @Override
        protected UserInfo initialValue() {
            return null;
        }
    };

    public static class InsertUserService implements Runnable {

        private String name;

        public InsertUserService(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            while (true){
                if (localUsers.get() == null) {
                    UserInfo info = new UserInfo();
                    info.setName(this.name);
                    info.setPassword("123");
                    localUsers.set(info);
                }else {
                    UserInfo info = localUsers.get();
                    info.setAge(info.getAge()+1);
                    localUsers.set(info);
                    System.out.println("线程："+Thread.currentThread().getName()+"-"+this.name+"的用户年龄："+info.getAge());
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
  }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(20);
        service.execute(new InsertUserService("test1"));
        service.submit(new InsertUserService("test2"));
    }
}
