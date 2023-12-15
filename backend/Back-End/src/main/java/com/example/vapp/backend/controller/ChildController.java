package com.example.vapp.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.vapp.backend.entity.*;

import com.example.vapp.backend.entity.vo.DonateRecordVo;
import com.example.vapp.backend.service.IChildService;
import com.example.vapp.backend.service.IChildSwiperImageService;
import com.example.vapp.backend.service.IDonateKidRelationService;
import com.example.vapp.backend.service.VolunteerKidRelationService;
import com.example.vapp.backend.util.ResultMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 孩子控制器

 */
@RestController
@Slf4j
@RequestMapping("/child")
@CrossOrigin(origins = "http://localhost", maxAge = 3600)
public class ChildController {

    @Autowired
    private IChildService childService;
    @Autowired
    private IChildSwiperImageService childSwiperImageService;
    @Autowired
    private VolunteerKidRelationService volunteerKidRelationService;
    @Autowired
    private IDonateKidRelationService iDonateKidRelationService;


    /**
     * 查询孩子是否在首页轮播
     * @return
     */
    @RequestMapping("/findSwiper")
    public ResultMap findSwiper(){
        List<Child> childList = childService.list(new QueryWrapper<Child>().eq("is_swiper",1));;
        Map<String,Object> map=new HashMap<>();
        map.put("message",childList);
        return ResultMap.ok(map);
    }

    /**
     * 根据id查询孩子，显示孩子详情及孩子轮播图
     * @param id
     * @return
     */
    @RequestMapping("/detail")
    public ResultMap detail(String id){
        Child child = childService.getById(id);
        List<ChildSwiperImage> childSwiperImageList =childSwiperImageService.list(new QueryWrapper<ChildSwiperImage>().eq("child_id", child.getId()).orderByAsc("sort"));
        child.setChildSwiperImageList(childSwiperImageList);
        Map<String,Object> map=new HashMap<>();
        map.put("message",child);
        return ResultMap.ok(map);
    }

    /**
     * 搜索框查询孩子名字
     * @param search
     * @return
     */
    @GetMapping("/search")
    public ResultMap search(String search){
        List<Child> childList = childService.list(new QueryWrapper<Child>().like("name", search));
        Map<String,Object> map=new HashMap<>();
        map.put("message",childList);
        return ResultMap.ok(map);
    }
    /**
     * 孩子列表
     * @return
     */
    @RequestMapping("/getChildList")
    public ResultMap getChildList(){
        List<Child> childList = childService.list();
        Map<String,Object> map=new HashMap<>();
        map.put("message",childList);
        return ResultMap.ok(map);
    }

    /**
     * 根据地区和标签筛选首页孩子
     * @param address
     * @param tag
     * @return
     */
    @GetMapping("/sift")
    public ResultMap search(String address,String tag ){
        List<Child> childList = childService.list(new QueryWrapper<Child>().
                and(wrapper -> wrapper.like("address",address).like("tag", tag)));
        Map<String,Object> map=new HashMap<>();
        map.put("message",childList);
        return ResultMap.ok(map);
    }

    /**
     * 孩子详情（带有组织信息和志愿者信息）
     * @param id
     * @return
     */
    @RequestMapping("/childAll")
    public ResultMap childAll(String id){
        ChildVo childVo = childService.childAll(id);
        Map<String,Object> map=new HashMap<>();
        map.put("message",childVo);
        return ResultMap.ok(map);
    }

    /**
     * 孩子详情关注
     * @param donateId
     * @param childId
     * @return
     */
    @RequestMapping("/like")
    public ResultMap like(String donateId,String childId){

        DonateKidRelation donateKidRelation = childService.like(donateId,childId);
        if(donateKidRelation==null){
            return ResultMap.error("该用户或该孩子不存在");
        }
        Map<String,Object> map=new HashMap<>();
        map.put("message",donateKidRelation);
        return ResultMap.ok(map);
    }
    /**
     * 查询孩子的必做任务完成情况
     * @return
     */
    @RequestMapping("/getMustTask")
    public ResultMap getMustTask(String id){
        List<Child> childList = childService.list(new QueryWrapper<Child>().eq("id",id));;
        JSONArray tasks = JSONArray.parseArray(childList.get(0).getMustTask());
        Map<String,Object> map=new HashMap<>();
        map.put("message",tasks);
        return ResultMap.ok(map);
    }
    /**
     * 查询孩子的选做任务完成情况
     * @return
     */
    @RequestMapping("/getOptTask")
    public ResultMap getOptTask(String id){
        List<Child> childList = childService.list(new QueryWrapper<Child>().eq("id",id));;
        JSONArray tasks = JSONArray.parseArray(childList.get(0).getOptTask());
        Map<String,Object> map=new HashMap<>();
        map.put("message",tasks);
        return ResultMap.ok(map);
    }
    /**
     * 查询孩子的总积分
     * @return
     */
    @RequestMapping("/getPoints")
    public ResultMap getPoints(String id){
        List<Child> childList = childService.list(new QueryWrapper<Child>().eq("id",id));;
        Integer points = childList.get(0).getPoints();
        Map<String,Object> map=new HashMap<>();
        map.put("message",points);
        return ResultMap.ok(map);
    }
    /**
     * 查询孩子的各月积分（折线图）
     * @return
     */
    @RequestMapping("/getLineChart")
    public ResultMap getLineChart(String id){
        List<Child> childList = childService.list(new QueryWrapper<Child>().eq("id",id));;
        JSONArray tasks = JSONArray.parseArray(childList.get(0).getLineChart());
        Map<String,Object> map=new HashMap<>();
        map.put("message",tasks);
        return ResultMap.ok(map);
    }
    /**
     * 查询孩子对应的志愿者
     */
    @RequestMapping(value = "/queryVolunteer", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResultMap queryVolunteer(@RequestBody Child child){
        VolunteerKidRelation volunteerKidRelation = volunteerKidRelationService.getOne(new QueryWrapper<VolunteerKidRelation>().eq("kid", child.getId()));
        if(volunteerKidRelation == null){
            return ResultMap.error("该孩子暂未拥有志愿者");
        }
        return ResultMap.ok().put("vid", volunteerKidRelation.getVid());
    }

    /**
     * 孩子详情关注
     * @param donateId
     * @param childId
     * @return
     */
    @RequestMapping("/writeConcernRecord")
    public ResultMap writeConcernRecord(String donateId,String childId){
        //根据捐助者id和孩子id查找记录
        DonateKidRelation donateKidRelation1 =iDonateKidRelationService.getOne(new QueryWrapper<DonateKidRelation>().
                and(wrapper -> wrapper.eq("did",donateId).like("kid", childId)));
        //未查找到记录则在数据库中新增该记录
        if(donateKidRelation1==null){
            DonateKidRelation donatekidRelation = new DonateKidRelation();
            donatekidRelation.setDid(donateId);
            donatekidRelation.setKid(childId);
            donatekidRelation.setIsLiked(1);
            int res=iDonateKidRelationService.insert(donatekidRelation);
            if(res==0){
                return ResultMap.error("捐赠记录写入失败");
            }
            Map<String,Object> map=new HashMap<>();
            map.put("message",donatekidRelation);
            return ResultMap.ok(map);
        }
        //查找到该记录，但未关注，更改为关注
        else if(donateKidRelation1.getIsLiked()==0){
            UpdateWrapper<DonateKidRelation> updateWrapper = Wrappers.update();
            updateWrapper.and(wrapper -> wrapper.eq("did",donateId).like("kid", childId));
            DonateKidRelation donateKidRelation = new DonateKidRelation();
            donateKidRelation.setDid(donateId);
            donateKidRelation.setKid(childId);
            donateKidRelation.setIsLiked(1);
            donateKidRelation.setIsDonated(donateKidRelation1.getIsDonated());
          boolean update = iDonateKidRelationService.update(donateKidRelation,updateWrapper);
            System.out.println(update);
        }
        //查找到记录且已经关注
        else{
            return  ResultMap.error("已关注");
        }
        return ResultMap.ok();
    }
    /**
     * 孩子详情取消关注
     * @param donateId
     * @param childId
     * @return
     */
    @RequestMapping("/unConcernRecord")
    public ResultMap unConcernRecord(String donateId,String childId){
        DonateKidRelation donateKidRelation1 =iDonateKidRelationService.getOne(new QueryWrapper<DonateKidRelation>().
                and(wrapper -> wrapper.eq("did",donateId).like("kid", childId)));
        //查找到该条记录且已关注
        if(donateKidRelation1.getIsLiked()==1){
            UpdateWrapper<DonateKidRelation> updateWrapper = Wrappers.update();
            updateWrapper.and(wrapper -> wrapper.eq("did",donateId).like("kid", childId));
            DonateKidRelation donateKidRelation = new DonateKidRelation();
            donateKidRelation.setDid(donateId);
            donateKidRelation.setKid(childId);
            donateKidRelation.setIsLiked(0);
            donateKidRelation.setIsDonated(donateKidRelation1.getIsDonated());
            boolean update = iDonateKidRelationService.update(donateKidRelation,updateWrapper);
            System.out.println(update);
        }
        //未查找到记录或者未关注
        else{
            return  ResultMap.error("该捐助者未关注该孩子");
        }
        return ResultMap.ok();
    }

    @RequestMapping(value = "/getChild", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResultMap getChild(String childId){
        Child child = childService.getOne(new QueryWrapper<Child>().eq("id",childId));
        if(child == null){
            ResultMap.error("未找到孩子");
        }
        return ResultMap.ok().put("child",child);
    }


}
