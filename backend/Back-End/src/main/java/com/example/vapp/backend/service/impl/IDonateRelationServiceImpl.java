package com.example.vapp.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vapp.backend.entity.DonateRelation;
import com.example.vapp.backend.entity.Organization;
import com.example.vapp.backend.mapper.DonateRelationMapper;
import com.example.vapp.backend.mapper.OrganizationMapper;
import com.example.vapp.backend.service.IDonateRelationService;
import com.example.vapp.backend.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("donateRelationService")
public class IDonateRelationServiceImpl extends ServiceImpl<DonateRelationMapper, DonateRelation> implements IDonateRelationService {

    @Autowired
    private DonateRelationMapper donateRelationMapper;
}

