package com.example.vapp.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;



@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("dv_message")
public class ChatMessage implements Serializable {
    @TableId(type = IdType.INPUT)
    private String id;//消息本身的id
    private String fromId;//发送者id
    private String name;//发送者姓名
    private String rname;//接收者姓名
    private String toId;//接收者id
    private Date time;//消息发送时间
    private String content;//聊天内容
    private int flag;//用于消息的接收
}
