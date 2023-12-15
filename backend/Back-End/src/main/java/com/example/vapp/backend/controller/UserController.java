package com.example.vapp.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.vapp.backend.entity.*;
import com.example.vapp.backend.service.IChildService;
import com.example.vapp.backend.service.IDonateRelationService;
import com.example.vapp.backend.service.IDonateService;
import com.example.vapp.backend.service.UserService;
import com.example.vapp.backend.util.ResultMap;
import com.example.vapp.backend.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private IDonateRelationService donateRelationService;
    @Autowired
    private IDonateService donateService;
    @Autowired
    private IChildService childService;
    /**
     *
     * 查询用户获取信息
     * @return
     */
    @RequestMapping(value="/getUserInfo")
    public ResultMap getUserInfo(String id){
        User user = userService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("message",user);
        return ResultMap.ok(map);
    }
    /**
     *
     * 查询捐助者获取信息
     * @return
     */
    @RequestMapping(value="/getDonateInfo")
    public ResultMap getDonateInfo(String id){
        Donate donate = donateService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("message",donate);
        return ResultMap.ok(map);
    }
    /**
     *
     * 获取捐助者帮助过的孩子
     * @return
     */
    @RequestMapping(value="/getUserHelpChild")
    public ResultMap getUserHelpChild(String id){
        List<DonateRelation> donateRelationList = donateRelationService.list(new QueryWrapper<DonateRelation>().eq("did", id));
        Map<String,Object> map=new HashMap<>();
        map.put("message",donateRelationList);
        return ResultMap.ok(map);
    }

    /**
     *
     * 合并数据返回给个人中心页所需数据
     * @return
     */
    @RequestMapping(value="/getPersonCenter")
    public ResultMap getPersonCenter(String id){
        Donate donate = donateService.getById(id);
        PersonCenter personCenter = new PersonCenter();
        personCenter.setId(String.valueOf(id));
        personCenter.setName(donate.getName());
        List<DonateRelation> donateRelationList = donateRelationService.list(new QueryWrapper<DonateRelation>().eq("did", id));
        Integer help = donateRelationList.size();
        Integer sum = 0;
        List<DonateChild> donateChildList = new ArrayList<>();
        for(DonateRelation donateRelation : donateRelationList){
            sum = sum + donateRelation.getMoney();
            String iid = donateRelation.getKid();
            Child child = childService.getById(iid);
            if (child != null) {
                DonateChild donateChild = new DonateChild();
                donateChild.setId(iid);
                donateChild.setName(child.getName());
                donateChild.setAge(child.getAge());
                donateChild.setCategory(child.getAddress());
                donateChild.setImage(donateRelation.getImg());
                donateChild.setMoney(donateRelation.getMoney());
                donateChild.setData(donateRelation.getTime());
                donateChild.setItem(donateRelation.getItem());
                donateChildList.add(donateChild);

            } else {
                // 处理 child 为空的情况
                System.out.println("null!");
            }
        }
        personCenter.setHelp_num(help);
        personCenter.setSumtotal(sum);
        personCenter.setChildren(donateChildList);

        Map<String,Object> map=new HashMap<>();
        map.put("message",personCenter);
        return ResultMap.ok(map);
    }

    /**
     *
     * 登录时查询用户
     */
    @RequestMapping(value="/login",method= RequestMethod.POST,headers="Accept=application/json")
    public ResultMap login(@RequestBody User user){
        User result=userService.getOne(new QueryWrapper<User>().eq("username",user.getUsername()));
        if(result == null){
            return ResultMap.error(600,"没找到用户");
        }
        Map map=new HashMap();
        if(!result.getPassword().equals(user.getPassword()))
            return ResultMap.error("密码失败");
        map.put("role",result.getRole());
        map.put("id",result.getId());
        return ResultMap.ok(map);
    }

    /**
     *
     * 注册用户
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST,headers="Accept=application/json")
    public ResultMap register(@RequestBody User user){
        User tempUser = new User();
        tempUser = user;
        String UUID = UUIDUtil.getOneUUID();
        tempUser.setId(UUID);
        if(userService.getOne(new QueryWrapper<User>().eq("username",tempUser.getUsername())) != null){
            return ResultMap.error("该用户名已被使用！");
        }else{
            if(!userService.save(tempUser)){
                return ResultMap.error("注册失败");
            }
            //将id写入不同role表中
            switch (tempUser.getRole()){

                case 1://捐助者
                    Donate donate = new Donate();
                    donate.setId(UUID);
                    if(!donateService.save(donate)){
                        return ResultMap.error("捐助者写入失败");
                    }
                    break;

                case 2://孩子
                    Child child = new Child();
                    child.setId(UUID);
                    if(!childService.save(child)){
                        return ResultMap.error("儿童写入失败");
                    }
                    break;
                case 3://志愿者
                    break;

            }
        }
        return ResultMap.ok();
    }
    /**
     * 捐助者
     */

    /**
     *
     * 修改密码
     */
    @PostMapping("/modifyPassword")
    public ResultMap modifyPassword(@RequestBody User user,@RequestParam String cPassword){
        //传入一个user实例和修改的密码
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username",user.getUsername()).set("password",cPassword);
        if(!userService.update(null,updateWrapper)){
            return ResultMap.error("修改失败");
        }
        return ResultMap.ok();
    }

    @GetMapping("/test")
    public ResultMap test(){
        return ResultMap.ok();
    }

}
