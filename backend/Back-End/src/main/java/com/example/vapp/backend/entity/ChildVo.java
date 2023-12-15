package com.example.vapp.backend.entity;

import lombok.Data;

@Data
public class ChildVo {
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
//    private String mustTask;
//    private String optTask;
//    private String lineChart;
//    private Integer points;
    private String organizationId;
    private String organizationName;
    private String volunteerId;
    private String volunteerName;
    private int isLike;

}
