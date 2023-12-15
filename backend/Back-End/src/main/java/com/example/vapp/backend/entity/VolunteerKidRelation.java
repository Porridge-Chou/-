package com.example.vapp.backend.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName("vk_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerKidRelation implements Serializable {
    private String vid;
    private String kid;
    private String start_date;
    private String end_date;
}
