package com.example.vapp.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName("dk_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonateKidRelation implements Serializable {
    private String did;
    private String kid;
    private int isLiked;//0没关注 1关注
    private int isDonated;//0没捐助 1捐助
}
