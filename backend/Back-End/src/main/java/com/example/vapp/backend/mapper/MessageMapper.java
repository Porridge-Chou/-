package com.example.vapp.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vapp.backend.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * 此类用于操作聊天信息
 */
@Repository
@Mapper
public interface MessageMapper extends BaseMapper<ChatMessage> {

    @Select("select * from dv_message where (from_id = #{sId} AND to_id = #{rId}) or (from_id = #{rId} AND to_id = #{sId}) ORDER BY time")
    public List<ChatMessage> getHistoryMst(String sId,String rId);


    @Select("select * from dv_message where ((from_id = #{sId} AND to_id = #{rId}) or (from_id = #{rId} AND to_id = #{sId}))and flag = 0 ORDER BY time")
    public List<ChatMessage> getUnreadMsg(String sId,String rId);

    @Select("SELECT a.* ,b.`name` AS rname FROM `dv_message` AS a LEFT JOIN `v_table` AS b on (a.to_id = b.id ) WHERE b.`name` IS NOT NULL AND a.from_id = #{sId} UNION SELECT a.* ,b.`name` AS rname FROM `dv_message` AS a LEFT JOIN `d_table` AS b on (a.to_id = b.id ) WHERE b.`name` IS NOT NULL AND a.to_id = #{sId} ORDER BY `time` ")
    public List<ChatMessage> getSnapShot(String sId);

    @Select("SELECT `name` FROM d_table WHERE id = #{sid} UNION SELECT `name` FROM v_table WHERE id = #{sid}")
    public String getMsgName(String sid);

    @Update("UPDATE `dv_message` SET flag = 1 WHERE from_id = #{rid} AND to_id = #{sid}")
    public void updateMsgSigned(String sid,String rid);

}
