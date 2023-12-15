package com.example.vapp.backend.netty;

import com.example.vapp.backend.entity.ChatMessage;
import lombok.Data;

import java.io.Serializable;


@Data
public class DataContent implements Serializable {
    private Integer action;     //用户动作
    private ChatMessage chatMessage;    //聊天内容
    private String extand;      //扩展字段
}
