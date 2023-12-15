package com.example.vapp.backend.util;

import lombok.Data;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 用于返回前端结果的类
 */
public class ResultMap extends HashMap<String , Object> {
    private static final long serialVersionUID=1L;

    public ResultMap() {
        put("code",0);
    }

    public static ResultMap error(){return error(500,"未知异常，请联系管理员");}

    public static ResultMap error(String msg){return error(500,msg);}

    public static ResultMap error(int code,String msg){
        ResultMap r=new ResultMap();
        r.put("code",code);
        r.put("msg",msg);
        return r;
    }

    public static ResultMap ok(String msg){
        ResultMap r=new ResultMap();
        r.put("msg",msg);
        return r;
    }

    public static ResultMap ok(Map<String,Object> map){
        ResultMap r=new ResultMap();
        r.putAll(map);
        return r;
    }

    public static ResultMap ok(){return new ResultMap();}

    public ResultMap put(String key,Object value){
        super.put(key,value);
        return this;
    }


}
