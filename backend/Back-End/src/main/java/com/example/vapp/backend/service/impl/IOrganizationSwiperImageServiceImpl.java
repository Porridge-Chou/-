package com.example.vapp.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vapp.backend.entity.Organization;
import com.example.vapp.backend.entity.OrganizationSwiperImage;
import com.example.vapp.backend.mapper.OrganizationMapper;
import com.example.vapp.backend.mapper.OrganizationSwiperImageMapper;
import com.example.vapp.backend.service.IOrganizationService;
import com.example.vapp.backend.service.IOrganizationSwiperImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 组织轮播图Service实现类
 *
 */

@Service("organizationSwiperImageService")
public class  IOrganizationSwiperImageServiceImpl extends ServiceImpl<OrganizationSwiperImageMapper, OrganizationSwiperImage> implements IOrganizationSwiperImageService {

    @Autowired
    private OrganizationSwiperImageMapper organizationSwiperImageMapper;
}
