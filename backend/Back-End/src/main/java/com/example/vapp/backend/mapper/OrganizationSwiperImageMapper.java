package com.example.vapp.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vapp.backend.entity.Organization;
import com.example.vapp.backend.entity.OrganizationSwiperImage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 组织轮播图mapper接口
 */
@Mapper
public interface OrganizationSwiperImageMapper extends BaseMapper<OrganizationSwiperImage> {
}
