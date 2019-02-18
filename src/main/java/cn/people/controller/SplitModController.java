package cn.people.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.people.controller.vo.ResultVO;
import cn.people.controller.vo.SplitModCreateVO;
import cn.people.entity.SplitMod;
import cn.people.service.ISplitModService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
* @ClassName: SplitModController 
* @Description: 碎片方案管理表对应原表TAG_MOD
* @author shidandan
* @date 2019年1月18日 下午7:54:17 
*  
 */
@RestController
@RequestMapping("/split/mod")
@Api(value = "碎片方案管理", description = "碎片方案管理")
public class SplitModController {

    @Autowired
    private ISplitModService iSplitModService;

    /**
     * 
    * @Title: 查询所有得碎片方案 
    * @author shidandan
    * @date 2019年1月18日 下午7:54:50 
    * @Description: 查询所有得碎片方案
    * @param splitId 碎片方案id
    * @return 所有得碎片方案
    * @throws Exception  参数说明 
    * @return ResultVO<List<SplitMod>>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "查询所有得碎片方案", notes = "查询所有得碎片方案")
    @RequestMapping(value ="/{splitId}/list", method = RequestMethod.GET)
    public ResultVO<List<SplitMod>> getSplitMod(@PathVariable(value="splitId")String splitId) throws Exception{
        SplitMod splitMod=new SplitMod();
        splitMod.setSplitId(splitId);
        return ResultVO.ok(iSplitModService.list(new QueryWrapper<SplitMod>(splitMod)));
    }
   
    /**
     * 
    * @Title: 新增碎片方案
    * @author shidandan
    * @date 2019年1月18日 下午7:56:08 
    * @Description: 新增碎片方案 
    * @param splitId 碎片方案id
    * @param createVO 模板碎片
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "新增碎片方案", notes = "新增碎片方案")
    @RequestMapping(value ="/{splitId}/add", method = RequestMethod.POST)
    public ResultVO<Boolean> createSplitMod(@PathVariable(value="splitId")String splitId,@RequestBody SplitModCreateVO createVO) throws Exception{
        
        return ResultVO.ok(iSplitModService.createSplitMod(createVO, splitId));
    }
}
