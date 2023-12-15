package com.example.vapp.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@TableName("d_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donate implements Serializable{
    @TableId(type = IdType.INPUT)
    private String id;
    private String name;
    private String gender;
    private Integer age;
    private String address;
    private String description;
    private String head_photo;
}

