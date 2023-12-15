package com.example.vapp.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vapp.backend.entity.ChatMessage;
import com.example.vapp.backend.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface MessageService extends IService<ChatMessage> {

    public String saveMsg(ChatMessage chatMessage);

    public void updateMsgSigned(String id);

    public List<ChatMessage> getHistoryMst(String sid,String rid);

    public List<ChatMessage> getUnreadMsg(String sId,String rId);

    public Map<String,ChatMessage> getSnapShot(String sId);

    public String getMsgName(String sId);

}
