package com.example.vapp.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vapp.backend.entity.ChildSwiperImage;
import com.example.vapp.backend.entity.OrganizationSwiperImage;
import com.example.vapp.backend.mapper.ChildSwiperImageMapper;
import com.example.vapp.backend.mapper.OrganizationSwiperImageMapper;
import com.example.vapp.backend.service.IChildSwiperImageService;
import com.example.vapp.backend.service.IOrganizationSwiperImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 孩子组织轮播图Service实现类
 *
 */

@Service("childSwiperImageService")
public class IChildSwiperImageServiceImpl extends ServiceImpl<ChildSwiperImageMapper, ChildSwiperImage> implements IChildSwiperImageService {

    @Autowired
    private ChildSwiperImageMapper childSwiperImageMapper;
}
