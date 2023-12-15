package com.example.vapp.backend.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@TableName("d_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonateRelation implements Serializable{
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String did;
    private String kid;
    @TableField(fill = FieldFill.INSERT)
    private Date time;//(datetime)
    private Integer money;
    private Integer times;
    private String zhengshu = "default.jpg";
    private String img ;
    private String item;
}

