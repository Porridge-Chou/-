package com.example.vapp.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vapp.backend.entity.Donate;
import com.example.vapp.backend.entity.Organization;
import com.example.vapp.backend.mapper.DonateMapper;
import com.example.vapp.backend.mapper.OrganizationMapper;
import com.example.vapp.backend.service.IDonateService;
import com.example.vapp.backend.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//组织Service实现类

@Service("donateService")
public class IDonateServiceImpl extends ServiceImpl<DonateMapper, Donate> implements IDonateService {

    @Autowired
    private DonateMapper donateMapper;

}
