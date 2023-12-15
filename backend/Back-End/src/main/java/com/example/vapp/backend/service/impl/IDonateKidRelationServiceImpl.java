package com.example.vapp.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vapp.backend.entity.ChildVo;
import com.example.vapp.backend.entity.DonateKidRelation;
import com.example.vapp.backend.mapper.DonateKidRelationMapper;
import com.example.vapp.backend.service.IDonateKidRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("donateKidRelationService")
public class IDonateKidRelationServiceImpl extends ServiceImpl<DonateKidRelationMapper, DonateKidRelation> implements IDonateKidRelationService {
    @Autowired
    private DonateKidRelationMapper donateKidRelationMapper;
    @Override
    public int insert(DonateKidRelation entity) {
        return donateKidRelationMapper.insert(entity);
    }
}
