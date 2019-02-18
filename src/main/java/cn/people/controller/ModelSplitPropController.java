package cn.people.controller;


import java.util.ArrayList;
import java.util.List;

import cn.people.commons.constants.CodeConstants;
import cn.people.controller.vo.*;
import cn.people.service.IModelSplitPropService;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
import cn.people.entity.ModelSplitProp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
* @ClassName: ModelSplitPropController 
* @Description: 模板碎片属性信息管理
* @author shidandan
* @date 2019年1月17日 下午8:27:06 
*  
 */
@RestController
@RequestMapping("/model/spilt/prop")
@Api(value = "模板碎片属性信息管理", description = "模板碎片属性信息管理")
public class ModelSplitPropController
{

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelSplitPropController.class);

    @Autowired
    private IModelSplitPropService iModelSpiltPropService;
    
    /**
     * 
    * @Title: 添加新模板碎片属性 
    * @author shidandan
    * @date 2019年1月17日 下午8:27:26 
    * @Description: 添加新模板碎片属性 
    * @param modelSpiltId 模板碎片ID
    * @param createVO 碎片模板属性值
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "添加新模板碎片属性", notes = "添加新模板碎片属性")
    @RequestMapping(value ="/{modelSpiltId}/add", method = RequestMethod.POST)
    public ResultVO<Boolean> createModelSplitProp(@PathVariable(value="modelSpiltId")String modelSpiltId,
                                                  @RequestBody ModelSplitPropCreateVO createVO) throws Exception{

        return ResultVO.ok(iModelSpiltPropService.createModelSplitProp(modelSpiltId, createVO));
    }

    /**
     * 
    * @Title: 修改模板碎片属性 
    * @author shidandan
    * @date 2019年1月17日 下午8:28:26 
    * @Description: 修改模板碎片属性 
    * @param updateVO 碎片模板属性值
    * @param id 碎片模板属性ID
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "修改模板碎片属性", notes = "修改模板碎片")
    @RequestMapping(value ="/{id}/update", method = RequestMethod.POST)
    public ResultVO<Boolean> updateModelSplitProp(@RequestBody ModelSplitPropUpdateVO updateVO,@PathVariable(value="id")String id) throws Exception{
        LOGGER.info("修改模板碎片属性,入参",JSON.toJSONString(updateVO));
        ModelSplitProp entity=new ModelSplitProp();
        entity.setId(id);
        entity.setPropValue(updateVO.getPropValue());
        return ResultVO.ok(iModelSpiltPropService.updateById(entity));
    }

    /**
     * 
    * @Title: 修改某栏目对应模板碎片属性值 
    * @author shidandan
    * @date 2019年1月17日 下午8:29:17 
    * @Description: 修改某栏目对应模板碎片属性值 
    * @param updateVO 模板碎片属性值
    * @param id 模板碎片属性ID
    * @param channelId 栏目ID
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "修改某栏目对应模板碎片属性值", notes = "修改某栏目对应模板碎片属性值")
    @RequestMapping(value ="/updateByChannel", method = RequestMethod.POST)
    public ResultVO<Boolean> updateModelSplitPropByChannel(@RequestBody ModelSplitPropBatchUpdateVO updateVO) throws Exception{
        LOGGER.info("修改某栏目对应模板碎片属性值,入参",JSON.toJSONString(updateVO));
        
        return ResultVO.ok(iModelSpiltPropService.updateModelSplitPropByChannel(updateVO));
    }

    /**
     * 
    * @Title: 根据栏目查询模板碎片属性列表 
    * @author shidandan
    * @date 2019年1月17日 下午8:30:04 
    * @Description: 根据栏目查询模板碎片属性列表
    * @param channelId 栏目ID
    * @param modelSpiltId 模板碎片ID
    * @return 模板碎片属性集合
    * @throws Exception  参数说明 
    * @return ResultVO<List<SplitPropListVO>>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "根据栏目查询模板碎片属性列表", notes = "根据栏目查询模板碎片属性列表")
    @RequestMapping(value ="/{modelSpiltLogicId}/{channelId}/list", method = RequestMethod.GET)
    public ResultVO<List<SplitPropListVO>> getModelSplitPropListByChannel(@PathVariable(value="channelId")String channelId, @PathVariable(value="modelSpiltLogicId")String modelSpiltLogicId) throws Exception{
        if(StringUtils.isEmpty(channelId) && StringUtils.isEmpty(modelSpiltLogicId)) {
            return ResultVO.badRequest(CodeConstants.RESULT_ERR_PARAM, "参数有误，不可为空");
        }

        ArrayList<SplitPropListVO > iModelSpiltPropList = iModelSpiltPropService.getModelSplitPropListByChannel(channelId,modelSpiltLogicId);
        return ResultVO.ok(iModelSpiltPropList);
    }

    /**
     *
     * @Title: 模板碎片属性列表查询 
     * @author shidandan
     * @date 2019年1月17日 下午8:30:41 
     * @Description: 模板碎片属性列表查询 
     * @param modelSpiltId 模板碎片ID
     * @return 模板碎片ID集合
     * @throws Exception  参数说明 
     * @return ResultVO<List<ModelSplitPropListVO>>    返回类型 
     * @throws 
     */
    @ApiOperation(value = "模板碎片属性列表查询", notes = "模板碎片属性列表查询")
    @RequestMapping(value ="/{modelSpiltId}/list", method = RequestMethod.POST)
    public ResultVO<ModelSplitPropListParentVO> getModelSplitPropList(@PathVariable(value="modelSpiltId")String modelSpiltId) throws Exception{

        return ResultVO.ok(iModelSpiltPropService.getModelSplitPropList(modelSpiltId));
    }

    /**
     * 
    * @Title: 删除模板碎片属性 
    * @author shidandan
    * @date 2019年1月17日 下午8:31:08 
    * @Description: 删除模板碎片属性
    * @param id 模板碎片属性ID
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "删除模板碎片属性", notes = "删除模板碎片属性")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", required = true, dataType = "String")
    @GetMapping(value ="/{id}/del")
    public ResultVO<Boolean> delModelSplitProp(@PathVariable(value="id") String id) throws Exception{
        LOGGER.info("删除模板碎片属性,入参：{}",id);
        ModelSplitProp entity=new ModelSplitProp();
        entity.setId(id);
        entity.setState(2);
        return ResultVO.ok(iModelSpiltPropService.updateById(entity));
    }
}
