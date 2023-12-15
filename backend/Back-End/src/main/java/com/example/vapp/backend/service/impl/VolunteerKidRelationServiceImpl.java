package com.example.vapp.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vapp.backend.entity.VolunteerKidRelation;
import com.example.vapp.backend.mapper.VolunteerKidRelationMapper;
import com.example.vapp.backend.service.VolunteerKidRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("volunteerKidRelationService")
public class VolunteerKidRelationServiceImpl  extends ServiceImpl<VolunteerKidRelationMapper, VolunteerKidRelation> implements VolunteerKidRelationService {
    @Autowired
    private VolunteerKidRelationMapper volunteerKidRelationMapper;

}
