package com.example.text;

/**
 * create at 2020/7/4
 * author raotong
 * Description :
 */
public class UserServiceImpl implements UserService {
    @Override
    public int addUser(String name) {
        System.out.println("add user " + name);
        return 1;
    }
}
