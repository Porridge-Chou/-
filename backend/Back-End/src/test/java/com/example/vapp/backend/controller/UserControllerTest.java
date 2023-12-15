package com.example.vapp.backend.controller;

import com.example.vapp.backend.entity.User;
import com.example.vapp.backend.service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;
    @Test
    @Order(2)
    void login() {
        System.out.println("登录成功测试");
        User user=new User();
        user.setUsername("example userName");
        user.setPassword("change password");
        System.out.println(userController.login(user));
    }
    @Test
    @Order(8)
    void login3(){
        System.out.println("登录未找到用户测试");
        User user=new User();
        user.setUsername("wrong userName");
        user.setPassword("change password");
        System.out.println(userController.login(user));
    }

    @Test
    @Order(1)
    void register() {
        System.out.println("注册成功测试");
        User user = new User();
        user.setUsername("example userName");
        user.setPassword("example password");
        user.setRole(1);
        System.out.println(userController.register(user));
    }

    @Test
    @Order(3)
    void login2(){
        System.out.println("登录失败测试");
        User user=new User();
        user.setUsername("example userName");
        user.setPassword("123456");
        System.out.println(userController.login(user));
    }

    @Test
    @Order(4)
    void register2(){
        System.out.println("用户名已注册测试");
        User user = new User();
        user.setUsername("example userName");
        user.setPassword("example password");
        user.setRole(1);
        System.out.println(userController.register(user));
    }

    @Test
    @Order(5)
    void register3(){
        System.out.println("注册失败测试");
//        System.out.println(userController.register(null));
    }
    @Test
    @Order(6)
    void modify1(){
        System.out.println("修改成功测试");
        User user = new User();
        user.setUsername("example userName");
        user.setPassword("example password");
        user.setRole(1);
        System.out.println(userController.modifyPassword(user,"change password"));
    }

    @Test
    @Order(7)
    void modify2(){
        System.out.println("修改失败测试");
        User user = new User();
        user.setUsername("example userName2");
        user.setPassword("example password");
        user.setRole(1);
        System.out.println(userController.modifyPassword(user,"change password"));
    }

}