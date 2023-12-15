package com.example.vapp.backend.entity.vo;

import com.example.vapp.backend.entity.Child;
import com.example.vapp.backend.entity.Donate;
import lombok.Data;

@Data
public class DonateRecordVo {
    private Donate donate;
    private Child child;
    private int money;
    private String item;
}
