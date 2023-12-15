package com.example.vapp.backend.controller;

import com.example.vapp.backend.entity.Child;
import com.example.vapp.backend.entity.Donate;
import com.example.vapp.backend.entity.User;
import com.example.vapp.backend.entity.vo.DonateRecordVo;
import com.example.vapp.backend.netty.DataContent;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DonateControllerTest {

    @Autowired
    private DonateController donateController;
    @Autowired
    private UserController userController;

    @Test
    @Order(1)
    void registerKid(){
        System.out.println("注册孩子");
        User user = new User();
        user.setUsername("Test Kid1");
        user.setPassword("Test Password");
        user.setRole(2);
        userController.register(user);
    }
    @Test
    @Order(2)
    void queryTotalKid(){
        System.out.println("查询所有孩子成功测试");
        Donate donate = new Donate();
        donate.setId("56d0b7d81ba845e7a46c057a71895511");
        System.out.println(donateController.queryTotalKid(donate));
    }
    @Test
    @Order(3)
    void queryDonatedKid(){
        System.out.println("查询捐助过孩子成功测试");
        Donate donate = new Donate();
        donate.setId("56d0b7d81ba845e7a46c057a71895511");
        System.out.println(donateController.queryDonatedKid(donate));
    }
    @Test
    @Order(4)
    void queryNoDonatedKid(){
        System.out.println("查询未捐助孩子成功测试");
        Donate donate = new Donate();
        donate.setId("56d0b7d81ba845e7a46c057a71895511");
        System.out.println(donateController.queryNoDonatedKid(donate));
    }
    @Test
    @Order(5)
    void saveDonateRecord(){
        Donate donate = new Donate();
        donate.setId("56d0b7d81ba845e7a46c057a71895511");
        Child child = new Child();
        child.setId("14897bd46c4d4e868067a413698c0c4e");
        DonateRecordVo donateRecordVo = new DonateRecordVo();
        donateRecordVo.setDonate(donate);
        donateRecordVo.setChild(child);
        donateRecordVo.setMoney(100);
        donateRecordVo.setItem("香蕉牛奶");
        System.out.println(donateController.writeDonateRecord(donateRecordVo));
    }
    @Test
    @Order(6)
    void getDonate(){
        Donate donate = new Donate();
        donate.setId("56d0b7d81ba845e7a46c057a71895511");
        System.out.println(donateController.getDonateName(donate));
    }
}
