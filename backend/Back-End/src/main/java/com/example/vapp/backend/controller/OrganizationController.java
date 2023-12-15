package com.example.vapp.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.vapp.backend.entity.Organization;
import com.example.vapp.backend.entity.OrganizationSwiperImage;
import com.example.vapp.backend.service.IOrganizationService;
import com.example.vapp.backend.service.IOrganizationSwiperImageService;
import com.example.vapp.backend.util.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    private IOrganizationService organizationService;
    @Autowired
    private IOrganizationSwiperImageService organizationSwiperImageService;

    /**
     * 根据id查询组织信息
     * @param id
     * @return
     */
    @GetMapping("/Odetail")
    public ResultMap detail(String id){
        Organization organization =organizationService.getById(id);
        List<OrganizationSwiperImage> organizationSwiperImageList = organizationSwiperImageService.list(new QueryWrapper<OrganizationSwiperImage>().eq("organization_id", organization.getId()).orderByAsc("sort"));
        organization.setOrganizationSwiperImageList(organizationSwiperImageList);
        Map<String,Object> map=new HashMap<>();
        map.put("message",organization);
        return ResultMap.ok(map);
    }
}
