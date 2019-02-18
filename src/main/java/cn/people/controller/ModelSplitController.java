package cn.people.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.people.controller.vo.ResultVO;
import cn.people.controller.vo.SplitCreateVO;
import cn.people.controller.vo.ModelSplitDetailVO;
import cn.people.controller.vo.ModelSplitPageVO;
import cn.people.controller.vo.ModelSplitVO;
import cn.people.controller.vo.SplitPageVO;
import cn.people.controller.vo.SplitUpdateVO;
import cn.people.entity.ModelSplit;
import cn.people.service.IModelSplitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
* @ClassName: ModelSplitController 
* @Description: 模板碎片信息管理 
* @author shidandan
* @date 2019年1月17日 下午8:14:39 
*  
 */
@RestController
@RequestMapping("/model/split")
@Api(value = "模板碎片信息管理", description = "模板碎片信息管理")
public class ModelSplitController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelSplitController.class);
    
    @Autowired
    private IModelSplitService iModelSplitService;
    
    /**
     * 
    * @Title: 添加新模板碎片 
    * @author shidandan
    * @date 2019年1月17日 下午8:14:56 
    * @Description: 添加新模板碎片
    * @param split 模板碎片内容
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "添加新模板碎片", notes = "添加新模板碎片")
    @RequestMapping(value ="/add", method = RequestMethod.POST)
    public ResultVO<Boolean> createModelSplit(@RequestBody SplitCreateVO split) throws Exception{
        LOGGER.info("添加新模板碎片,入参",JSON.toJSONString(split));
        Boolean result=iModelSplitService.createModelSplit(split);
        return ResultVO.ok(result);
    }
   
    /**
     * 
    * @Title: 修改模板碎片 
    * @author shidandan
    * @date 2019年1月17日 下午8:15:26 
    * @Description: 修改模板碎片
    * @param split 模板碎片
    * @param id 模板碎片ID
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "修改模板碎片", notes = "修改模板碎片")
    @RequestMapping(value ="/{id}/update", method = RequestMethod.POST)
    public ResultVO<Boolean> updateSplit(@RequestBody SplitUpdateVO split,@PathVariable String id) throws Exception{
        LOGGER.info("修改模板碎片,入参",JSON.toJSONString(split));
        
        return ResultVO.ok(iModelSplitService.updateSplit(id,split));
    }
    
    /**
     * 
    * @Title: 模板碎片列表查询 
    * @author shidandan
    * @date 2019年1月17日 下午8:16:04 
    * @Description: 模板碎片列表查询
    * @param split 模板碎片
    * @param modelid 模板ID
    * @return 模板碎片集合
    * @throws Exception  参数说明 
    * @return ResultVO<IPage<ModelSplit>>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "模板碎片列表查询", notes = "模板碎片列表查询")
    @RequestMapping(value ="/{modelid}/page", method = RequestMethod.POST)
    public ResultVO<ModelSplitPageVO> getSplitPaged(@RequestBody SplitPageVO split,@PathVariable(value="modelid") String modelid) throws Exception{
        LOGGER.info("模板碎片列表查询,入参",JSON.toJSONString(split));
        
        return ResultVO.ok(iModelSplitService.getModelSplitPaged(split, modelid));
    }
    /**
     * 
    * @Title: 查询模板下的所有碎片 
    * @author shidandan
    * @date 2019年1月22日 下午1:38:13 
    * @Description: 查询模板下的所有碎片 
    * @param modelid 模板ID
    * @return
    * @throws Exception  参数说明 
    * @return ResultVO<List<ModelSplit>>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "模板碎片列表查询", notes = "模板碎片列表查询")
    @RequestMapping(value ="/{modelid}/list", method = RequestMethod.GET)
    public ResultVO<List<ModelSplit>> getSplit(@PathVariable(value="modelid") String modelid) throws Exception{

        return ResultVO.ok(iModelSplitService.getModelSplit(modelid));
    }
    
    /**
     * 
    * @Title: 获取模板碎片详细信息 
    * @author shidandan
    * @date 2019年1月17日 下午8:16:31 
    * @Description: 获取模板碎片详细信息 
    * @param id 模板碎片ID
    * @return 模板碎片详情
    * @throws Exception  参数说明 
    * @return ResultVO<ModelSplitDetailVO>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "获取模板碎片详细信息", notes = "获取模板碎片详细信息")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", required = true, dataType = "String")
    @GetMapping(value ="/{id}/detail")
    public ResultVO<ModelSplitDetailVO> getModelSplitDetail(@PathVariable String id) throws Exception{
        LOGGER.info("获取模板碎片详细信息,入参：{}",id);
        
        return ResultVO.ok(iModelSplitService.getModelSplitDetail(id));
    }
    
    /**
     * 
    * @Title: 删除模板碎片 
    * @author shidandan
    * @date 2019年1月17日 下午8:17:06 
    * @Description: 删除模板碎片
    * @param id 模板碎片ID
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "删除模板碎片", notes = "删除模板碎片")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", required = true, dataType = "String")
    @GetMapping(value ="/{id}/del")
    public ResultVO<Boolean> delModelSplit(@PathVariable String id) throws Exception{
        LOGGER.info("删除模板碎片,入参：{}",id);
        return ResultVO.ok(iModelSplitService.delModelSplit(id));
    }
}
