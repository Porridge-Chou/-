package com.example.vapp.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vapp.backend.entity.ChatMessage;
import com.example.vapp.backend.mapper.MessageMapper;
import com.example.vapp.backend.service.MessageService;
import com.example.vapp.backend.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageMapper, ChatMessage> implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public String saveMsg(ChatMessage chatMessage) {
        String id=UUIDUtil.getOneUUID();
        chatMessage.setId(id);
        chatMessage.setFlag(0);
        messageMapper.insert(chatMessage);
        return id;
    }

    //签收消息
    @Override
    public void updateMsgSigned(String id) {
        String[] arr=id.split(",");
        messageMapper.updateMsgSigned(arr[0],arr[1]);
    }

    @Override
    public List<ChatMessage> getHistoryMst(String sid,String rid) {
        return messageMapper.getHistoryMst(sid,rid);
    }

    @Override
    public List<ChatMessage> getUnreadMsg(String sId, String rId) {
        return messageMapper.getUnreadMsg(sId,rId);
    }

    @Override
    public Map<String,ChatMessage> getSnapShot(String sId) {
        List<ChatMessage> list= messageMapper.getSnapShot(sId);
        Map<String,ChatMessage> res=new HashMap<>();
        for (ChatMessage msg:list)
            res.put(msg.getFromId().equals(sId)?msg.getToId():msg.getFromId(),msg);
        return res;
    }

    @Override
    public String getMsgName(String sId){
        return messageMapper.getMsgName(sId);
    }

}
