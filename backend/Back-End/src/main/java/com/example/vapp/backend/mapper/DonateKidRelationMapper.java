package com.example.vapp.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vapp.backend.entity.DonateKidRelation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DonateKidRelationMapper extends BaseMapper<DonateKidRelation> {
    @Override
     int insert(DonateKidRelation entity);
}
