package com.example.vapp.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vapp.backend.entity.DonateKidRelation;


public interface IDonateKidRelationService extends IService<DonateKidRelation> {

    int insert(DonateKidRelation entity);

}
