package cn.people.controller;


import java.util.ArrayList;
import java.util.List;

import cn.people.controller.vo.SplitContentDelVO;
import javax.validation.Valid;

import cn.people.controller.vo.SplitContentDelVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;

import cn.people.commons.constants.CodeConstants;
import cn.people.controller.vo.ResultVO;
import cn.people.controller.vo.SplitContentAddVO;
import cn.people.controller.vo.SplitContentVO;
import cn.people.entity.SplitContent;
import cn.people.service.ISplitContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;


/**
 * 
* @ClassName: SplitContentController 
* @Description: 碎片内容管理
* @author shidandan
* @date 2019年1月17日 下午4:42:31 
*  
 */
@RestController
@RequestMapping("/split/content")
@Api(value = "碎片内容管理", description = "碎片内容管理")
public class SplitContentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SplitContentController.class);
    
    @Autowired
    ISplitContentService splitContentService;
    
    /**
     * 
    * @Title: 查询碎片对应的内容列表 
    * @author shidandan
    * @date 2019年1月17日 下午4:29:06 
    * @Description: 查询碎片对应的内容列表
    * @param modelSplitId 模板碎片ID
    * @param channelId 栏目ID
    * @return 模板碎片内容列表
    * @throws Exception  参数说明 
    * @return ResultVO<List<SplitContentVO>>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "查询碎片对应的内容列表", notes = "查询碎片对应的内容列表")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value ="/{modelSplitLogicId}/{channelId}/list", method = RequestMethod.GET)
    public ResultVO<List<SplitContentVO>> querySplitContentList(@PathVariable("modelSplitLogicId") String modelSplitLogicId,@PathVariable("channelId") String channelId) throws Exception{
        LOGGER.info("查询碎片对应的内容列表,入参id={}",modelSplitLogicId);
        
        return ResultVO.ok(splitContentService.querySplitContentList(modelSplitLogicId,channelId));
    }
   
    /**
     * 
    * @Title: 查询碎片内容详情 
    * @author shidandan
    * @date 2019年1月17日 下午4:29:54 
    * @Description: 查询碎片内容详情
    * @param id 模板碎片内容ID
    * @return  模板碎片详情
    * @throws Exception  参数说明 
    * @return ResultVO<SplitContentVO>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "查询碎片内容详情", notes = "查询碎片内容详情")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value ="/{id}/detail", method = RequestMethod.GET)
    public ResultVO<SplitContentVO> getSplitContent(@PathVariable("id") String id) throws Exception{
        LOGGER.info("查询碎片对应的内容列表,入参id={}",id);
        
        SplitContent splitContent=splitContentService.getById(id);
        if(null!=splitContent) {
            SplitContentVO result=new SplitContentVO(); 
            BeanUtils.copyProperties(splitContent, result);
            result.setTitle(splitContent.getShowTitle());
            return ResultVO.ok(result);
        }  
        
        return ResultVO.ok(null);
    }
    
    /**
     * 
    * @Title: 删除碎片内容 
    * @author shidandan
    * @date 2019年1月17日 下午4:30:40 
    * @Description: 删除碎片内容
    * @param id 模板碎片内容ID
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "删除碎片内容", notes = "删除碎片内容")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value ="/{id}/del", method = RequestMethod.GET)
    public ResultVO<Boolean> delSplitContent(@PathVariable("id") String id) throws Exception{
        LOGGER.info("查询碎片对应的内容列表,入参id={}",id);
        if(StringUtils.isEmpty(id)) {
            ResultVO.badRequest(CodeConstants.RESULT_ERR_PARAM, "参数有误");
        }
        return ResultVO.ok(splitContentService.removeById(id));
    }

    /**
     * @author guling
     * @Title   批量删除碎片内容
     * @Date    2019/1/29 9:36
     * @param   [ids]  要删除的id集合
     * @return  cn.people.controller.vo.ResultVO<java.lang.Boolean>
     * @throws
     * @Description 批量删除碎片内容
    */
    @ApiOperation(value = "批量删除碎片内容", notes = "批量删除碎片内容")
    @RequestMapping(value ="ids/del", method = RequestMethod.POST)
   public ResultVO<Boolean> delSplitContentByIds(@RequestBody SplitContentDelVO splitContentDelVO) throws Exception{

        LOGGER.info("查询碎片对应的内容列表,入参splitContentDelVO={}",splitContentDelVO);
        if(splitContentDelVO == null) {
            ResultVO.badRequest(CodeConstants.RESULT_ERR_PARAM, "参数有误");
        }

        return ResultVO.ok(splitContentService.removeByIds(splitContentDelVO.getIds()));
    }

    /**
     * 
    * @Title: 显示碎片内容 
    * @author shidandan
    * @date 2019年1月17日 下午4:31:10 
    * @Description: 显示碎片内容 
    * @param id 模板碎片内容ID
    * @return　成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "显示碎片内容", notes = "显示碎片内容")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value ="/{id}/show", method = RequestMethod.GET)
    public ResultVO<Boolean> showSplitContent(@PathVariable("id") String id) throws Exception{
        LOGGER.info("显示碎片内容,入参id={}",id);
        
        return ResultVO.ok(splitContentService.updateSplitContentStatus(id, 1));
    }
    
    /**
     * 
    * @Title: 隐藏碎片内容 
    * @author shidandan
    * @date 2019年1月17日 下午4:34:05 
    * @Description: 隐藏碎片内容 
    * @param id 模板碎片内容ID
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "隐藏碎片内容", notes = "隐藏碎片内容")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value ="/{id}/hide", method = RequestMethod.GET)
    public ResultVO<Boolean> hideSplitContent(@PathVariable("id") String id) throws Exception{
        LOGGER.info("隐藏碎片内容,入参id={}",id);
        
        return ResultVO.ok(splitContentService.updateSplitContentStatus(id, 0));
    }
   
    /**
     * 
    * @Title: 修改碎片内容 
    * @author shidandan
    * @date 2019年1月17日 下午4:34:33 
    * @Description: 修改碎片内容 
    * @param id 模板碎片内容ID
    * @param splitContentVO 模板碎片内容得属性
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "修改碎片内容", notes = "修改碎片内容")
    @RequestMapping(value ="/{id}/update", method = RequestMethod.POST)
    public ResultVO<Boolean> updateSplitContent(@PathVariable(value="id") String id,@RequestBody SplitContentVO splitContentVO) throws Exception{
        LOGGER.info("修改碎片内容,入参id="+id+",model={}",JSON.toJSONString(splitContentVO));
        
        return ResultVO.ok(splitContentService.updateSplitContents(id, splitContentVO));
    }
    
    /**
     * 
    * @Title: 增加碎片内容 
    * @author shidandan
    * @date 2019年1月17日 下午4:35:08 
    * @Description: 增加碎片内容
    * @param modelSpiltId 模板碎片ID
    * @param channelId　栏目ID
    * @param splitContentVO　模板碎片内容属性
    * @return　成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "增加碎片内容", notes = "增加碎片内容")
    @RequestMapping(value ="/add", method = RequestMethod.POST)
    public ResultVO<Boolean> addSplitContent(@Valid @RequestBody SplitContentAddVO splitContentVO) throws Exception{
        LOGGER.info("增加碎片内容,入参+",JSON.toJSONString(splitContentVO));
        
        return ResultVO.ok(splitContentService.addSplitContent(splitContentVO));
    }
    
}
