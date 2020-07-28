package com.example.text;

/**
 * create at 2020/7/4
 * author raotong
 * Description : 接口添加日志功能
 */
public interface UserService {
    @Log
    int addUser(String name);
}
