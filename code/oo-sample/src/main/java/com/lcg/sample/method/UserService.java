package com.lcg.sample.method;

/**
 * @author linchuangang
 * @create 2019-09-06 20:25
 **/
public class UserService extends BaseService {

    /**
     * 重写方法
     */
    @Override
    public void save() {
        System.out.println("userService invoke save method");
    }

    /**
     * 重载方法，返回类型不能作为重载的判断依据。
     * 参数列表个数不一致作为判断重载与重写的依据。
     * @param a
     * @param b
     */
    public void print(int a,int b){
        System.out.println("userService print method:{"+a+","+b+"}");
    }
}
