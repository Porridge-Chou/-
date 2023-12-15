package com.example.vapp.backend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 组织轮播图片
 */
@TableName("o_swiperimgs")
@Data
public class OrganizationSwiperImage {
    private String id; //组织轮播图id
    private String image; //组织图片；
    private int sort; //组织轮播图顺序
    private String organizationId; //所属组织id
}
