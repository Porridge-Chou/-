package com.example.vapp.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vapp.backend.entity.Child;
import com.example.vapp.backend.entity.ChildVo;
import com.example.vapp.backend.entity.DonateKidRelation;

import java.util.List;
import java.util.Map;

/**
 * 商品Service接口

 */
public interface IChildService extends IService<Child> {
      /**
       * 孩子详情（带有组织信息和志愿者信息）
       * @param id
       * @return
       */
      ChildVo childAll (String id);

      /**
       * 孩子详情关注
       * @param donateId
       * @param childId
       * @return
       */
      DonateKidRelation like(String donateId,String childId);

//    /**
//     * 查询轮播商品
//     * @return
//     */
//    public List<Child> findSwiper();

//    /**
//     * 查询热卖商品
//     * @return
//     */
//    public List<Child> findHot();
//
//    /**
//     * 根据条件 分页商品
//     * @param map
//     * @return
//     */
//    public List<Child> list(Map<String,Object> map);
//
//    /**
//     * 根据条件，查询商品总记录数
//     * @param map
//     * @return
//     */
//    public Long getTotal(Map<String,Object> map);
//
//    /**
//     * 添加商品
//     * @param child
//     * @return
//     */
//    public Integer add(Child child);
//
//    /**
//     * 修改商品
//     * @param child
//     * @return
//     */
//    public Integer update(Child child);

}
