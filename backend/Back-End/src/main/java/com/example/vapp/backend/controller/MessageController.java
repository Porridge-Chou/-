package com.example.vapp.backend.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.vapp.backend.entity.ChatMessage;
import com.example.vapp.backend.service.MessageService;
import com.example.vapp.backend.util.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @RequestMapping("/getUnreadMsg")
    public ResultMap getUnreadMsg(@RequestBody JSONObject jsonObject){
        String sId= (String) jsonObject.get("sId");
        String rId= (String) jsonObject.get("rId");
        List<ChatMessage> list=messageService.getUnreadMsg(sId,rId);
        JSONArray jsonArray=new JSONArray();
        jsonArray.addAll(list);

        return ResultMap.ok(JSONArray.toJSONString(list));
    }

    @RequestMapping("/getSnapShot")
    public ResultMap getSnapShot(@RequestBody String sId){
        Map<String,ChatMessage> map=messageService.getSnapShot(sId);
        JSONArray arr=new JSONArray();
        for(String s: map.keySet()){
            JSONObject object=new JSONObject();
            object.put("key",s);
            object.put("msg",map.get(s));
            arr.add(object);
        }
        System.out.println(JSONArray.toJSONString(arr));
        return ResultMap.ok(JSONArray.toJSONString(arr));
    }

    @RequestMapping("/getMsgName")
    public ResultMap getMsgName(@RequestBody String Id){

        return ResultMap.ok(messageService.getMsgName(Id));
    }
}
