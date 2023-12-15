package com.example.vapp.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@TableName("o_table")
@Data
public class Organization {
    private String id;  //组织编号
    private String name; //组织名称
    private String phone; //组织联系方式
    private String tag;  //组织标签
    private String address; //组织地址
    private String description; //组织描述
    private String manager; //组织负责人姓名
    private String picture; //组织会徽
    private String certificate; //组织证书
    private String volunteerList ; //志愿者列表
    @TableField(select = false)
    private List<OrganizationSwiperImage> organizationSwiperImageList;  // 组织轮播图片
}
