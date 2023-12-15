package com.example.vapp.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vapp.backend.entity.Child;
import com.example.vapp.backend.entity.ChildVo;
import com.example.vapp.backend.entity.DonateKidRelation;
import com.example.vapp.backend.mapper.ChildMapper;
import com.example.vapp.backend.mapper.DonateKidRelationMapper;
import com.example.vapp.backend.service.IChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商品Service实现类

 */
@Service("childService")
public class IChildServiceImpl extends ServiceImpl<ChildMapper, Child> implements IChildService {

    @Autowired
    private ChildMapper childMapper;


    /**
     * 孩子详情（带有组织信息和志愿者信息）
     * @param id
     * @return
     */
    @Override
    public ChildVo childAll(String id) {
        return childMapper.childAll(id);
    }

    /**
     * 孩子详情关注
     * @param donateId
     * @param childId
     * @return
     */
    @Override
    public DonateKidRelation like(String donateId, String childId) {
        return childMapper.like(donateId,childId);
    }

    //    @Override
//    public List<Child> findHot() {
//        return childMapper.findHot();
//    }
//
//    @Override
//    public List<Child> list(Map<String, Object> map) {
//        return childMapper.list(map);
//    }
//
//    @Override
//    public Long getTotal(Map<String, Object> map) {
//        return childMapper.getTotal(map);
//    }
//
//    @Override
//    public Integer add(Child child) {
//        return childMapper.add(child);
//    }
//
//    @Override
//    public Integer update(Child child) {
//        return childMapper.update(child);
//    }
}
