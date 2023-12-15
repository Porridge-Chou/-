package com.example.vapp.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vapp.backend.entity.Donate;
import com.example.vapp.backend.entity.Organization;
import org.apache.ibatis.annotations.Mapper;

/**
 * 捐助者mapper接口
 */
@Mapper
public interface DonateMapper extends BaseMapper<Donate> {

}
