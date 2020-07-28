package com.example.text;

/**
 * create at 2020/7/4
 * author raotong
 * Description : 用于测试的类
 */
public class ProxyTest {

    public static void main(String[] args) {
        //特点： 最先执行最后返回的代理对象的代理方法。如下：先执行Log最后执行业务方法。
        UserService service = new UserServiceImpl();
        service = (UserService)LogProxy.factory(service);
        service.addUser("abel");
    }

}
