package com.example.vapp.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vapp.backend.entity.Child;
import com.example.vapp.backend.entity.DonateRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 捐助关系mapper接口
 */
@Mapper
public interface DonateRelationMapper extends BaseMapper<DonateRelation> {

}
