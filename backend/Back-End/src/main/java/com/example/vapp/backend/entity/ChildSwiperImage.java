package com.example.vapp.backend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 孩子轮播图片
 */
@TableName("kid_swiperimgs")
@Data
public class ChildSwiperImage {
    private String id; //孩子轮播图id
    private String image; //孩子图片
    private int sort; //孩子轮播图顺序
    private String childId; //所属孩子id
}
