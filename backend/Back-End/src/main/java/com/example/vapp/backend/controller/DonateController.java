package com.example.vapp.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.vapp.backend.entity.Child;
import com.example.vapp.backend.entity.Donate;
import com.example.vapp.backend.entity.DonateKidRelation;
import com.example.vapp.backend.entity.DonateRelation;
import com.example.vapp.backend.entity.vo.DonateRecordVo;
import com.example.vapp.backend.mapper.ChildMapper;
import com.example.vapp.backend.mapper.DonateKidRelationMapper;
import com.example.vapp.backend.service.IChildService;
import com.example.vapp.backend.service.IDonateKidRelationService;
import com.example.vapp.backend.service.IDonateRelationService;
import com.example.vapp.backend.service.IDonateService;
import com.example.vapp.backend.util.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/donate")
public class DonateController {

    @Autowired
    private IDonateService iDonateService;
    @Autowired
    private IDonateKidRelationService iDonateKidRelationService;
    @Autowired
    private DonateKidRelationMapper donateKidRelationMapper;
    @Autowired
    private IChildService iChildService;
    @Autowired
    private ChildMapper childMapper;
    @Autowired
    private IDonateRelationService iDonateRelationService;

    @RequestMapping(value = "/queryTotalKid", method = RequestMethod.POST, headers="Accept=application/json")
    public ResultMap queryTotalKid(@RequestBody Donate donate){
        //查询关注的全部孩子
        List<DonateKidRelation> donateKidRelationList = donateKidRelationMapper.selectList(
                new QueryWrapper<DonateKidRelation>()
                        .eq("did",donate.getId())
                        .eq("is_liked",1)
        );
        List<String> kidIds = new ArrayList<>();
        for (DonateKidRelation donateKidRelation : donateKidRelationList) {
            kidIds.add(donateKidRelation.getKid());
        }
        List<Child> kidlist = childMapper.selectBatchIds(kidIds);
        if(kidlist == null){
            return ResultMap.error("查询所有孩子失败");
        }
        return ResultMap.ok().put("kids",kidlist);
    }

    @RequestMapping(value = "/queryDonatedKid", method = RequestMethod.POST, headers="Accept=application/json")
    public ResultMap queryDonatedKid(@RequestBody Donate donate){
        //查询捐助过的孩子
        List<DonateKidRelation> donateKidRelationList = donateKidRelationMapper.selectList(
                new QueryWrapper<DonateKidRelation>()
                        .eq("did",donate.getId())
                        .eq("is_liked",1)
                        .eq("is_donated",1)
        );
        List<String> kidIds = new ArrayList<>();
        for (DonateKidRelation donateKidRelation : donateKidRelationList) {
            kidIds.add(donateKidRelation.getKid());
        }
        List<Child> kidlist = childMapper.selectBatchIds(kidIds);
        if(kidlist == null){
            return ResultMap.error("查询捐助过的孩子失败");
        }
        return ResultMap.ok().put("kids",kidlist);
    }

    @RequestMapping(value = "/queryNoDonatedKid", method = RequestMethod.POST, headers="Accept=application/json")
    public ResultMap queryNoDonatedKid(@RequestBody Donate donate){
        //查询未捐助的孩子
        List<DonateKidRelation> donateKidRelationList = donateKidRelationMapper.selectList(
                new QueryWrapper<DonateKidRelation>()
                        .eq("did",donate.getId())
                        .eq("is_liked",1)
                        .eq("is_donated",0)
        );
        List<String> kidIds = new ArrayList<>();
        for (DonateKidRelation donateKidRelation : donateKidRelationList) {
            kidIds.add(donateKidRelation.getKid());
        }
        List<Child> kidlist = childMapper.selectBatchIds(kidIds);
        if(kidlist == null){
            return ResultMap.error("查询捐助过的孩子失败");
        }
        return ResultMap.ok().put("kids",kidlist);
    }

    @RequestMapping(value="/writeDonateRecord", method = RequestMethod.POST)
    public ResultMap writeDonateRecord(@RequestBody DonateRecordVo donateRecordVo){
        //写入捐助记录

        DonateRelation donateRelation = new DonateRelation();
        String donateId = donateRecordVo.getDonate().getId();
        String childId = donateRecordVo.getChild().getId();
        donateRelation.setDid(donateId);
        donateRelation.setKid(childId);
        donateRelation.setMoney(donateRecordVo.getMoney());
        donateRelation.setItem(donateRecordVo.getItem());
        //查询往前捐赠的次数
        int count = (int)iDonateRelationService.count(
                new QueryWrapper<DonateRelation>()
                        .eq("did",donateId)
                        .eq("kid",childId)
        );
        donateRelation.setTimes(count+1);
        if(!iDonateRelationService.save(donateRelation)){
            return ResultMap.error("捐赠记录写入失败");
        }
        return ResultMap.ok();

    }

    @RequestMapping(value = "/getDonateName", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResultMap getDonateName(@RequestBody Donate donate){
        Donate newDonate = iDonateService.getOne(new QueryWrapper<Donate>().eq("id",donate.getId()));
        if(newDonate == null){
            return ResultMap.error("查询错误!请检查id是否正确!");
        }
        return ResultMap.ok().put("donate",newDonate);
    }



}
