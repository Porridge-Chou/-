package com.example.vapp.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@TableName("kid_table")
@Data

public class Child implements Serializable {
    @TableId(type = IdType.INPUT)
    private String id;
    private String name;
    private String gender;
    private Integer age;
    private String address;
    private String tag;
    private String school ;
    private Integer grade;
    private String description;
    private String headPhoto;
    private Integer isSwiper;
    private Integer swiperSort;
    private String swiperPic ;
    private String organizationId;
    private String mustTask;
    private String optTask;
    private String lineChart;
    private Integer points;


    @TableField(select = false)
    private List<ChildSwiperImage> childSwiperImageList;  // 孩子轮播图片
}
