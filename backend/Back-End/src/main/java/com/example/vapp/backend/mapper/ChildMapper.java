package com.example.vapp.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vapp.backend.entity.Child;
import com.example.vapp.backend.entity.ChildVo;
import com.example.vapp.backend.entity.DonateKidRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 商品Mapper接口
 */
@Repository
@Mapper
public interface ChildMapper extends BaseMapper<Child> {
        /**
         * 孩子详情（带有组织信息和志愿者信息）
         * @param id
         * @return
         */
        @Select("select k.*,o.id organizationId,o.name organizationName,v.id volunteerId,v.name volunteerName from kid_table k ,o_table o ,v_table v where o.id=k.organization_id and v.id=k.volunteer_id and k.id = #{id}")
        ChildVo childAll(String id);

        /**
         * 孩子详情关注
         * @param donateId
         * @param childId
         * @return
         */
        @Select("select dk.* from kid_table k ,dk_table dk where dk.kid=k.id and dk.did = #{donateId} and dk.kid = #{childId}")
        DonateKidRelation like(String donateId,String childId);
//    /**
//     * 查询轮播商品
//     * @return
//     */
//    public List<Child> findSwiper();

//
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
