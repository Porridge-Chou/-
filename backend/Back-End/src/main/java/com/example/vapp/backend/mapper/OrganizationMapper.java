package com.example.vapp.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vapp.backend.entity.Organization;
import com.example.vapp.backend.entity.OrganizationSwiperImage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 组织mapper接口
 */
@Mapper
public interface OrganizationMapper extends BaseMapper<Organization> {
}
