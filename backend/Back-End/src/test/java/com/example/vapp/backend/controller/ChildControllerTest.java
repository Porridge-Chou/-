package com.example.vapp.backend.controller;

import com.example.vapp.backend.entity.Child;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChildControllerTest {
    @Autowired
    private ChildController childController;

    @Test
    @Order(1)
    void queryVolunteerId(){
        //测试孩子查询志愿者成功
        Child child = new Child();
        child.setId("14897bd46c4d4e868067a413698c0c4e");
        System.out.println(childController.queryVolunteer(child));
    }

    @Test
    @Order(2)
    void queryWrongVolunteerId(){
        //测试孩子查询志愿者失败
        Child child = new Child();
        child.setId("wrong id");
        System.out.println(childController.queryVolunteer(child));
    }
    @Test
    @Order(3)
    void queryChild(){
        //测试孩子id返回孩子实例
        System.out.println(childController.getChild("23ac78860b4149f3b48cd53da601e766"));
    }
}
