package com.example.vapp.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonateChild {
    private String id;
    private String name;
    private Integer age;
    private String category;
    private String image;
    private Integer money;
    private Date data;
    private String item;
}
