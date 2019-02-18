package cn.people.controller;

import java.util.List;

import javax.validation.Valid;

import cn.people.controller.vo.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import cn.people.commons.constants.CodeConstants;
import cn.people.entity.Channel;
import cn.people.service.IChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
* @ClassName: ChannelController 
* @Description: 栏目信息管理
* @author shidandan
* @date 2019年1月17日 下午4:47:03 
*  
 */
@RestController
@RequestMapping("/channel")
@Api(value = "栏目信息管理", description = "栏目信息管理")
public class ChannelController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelController.class);
    
    @Autowired
    private IChannelService iChannelService;
    
    /**
     * 
    * @Title: 查询某站点下得栏目信息 
    * @author shidandan
    * @date 2019年1月17日 下午4:12:01 
    * @Description: 查询某站点下得栏目信息
    * @param siteid 站点ID
    * @param orgid  租户ID
    * @return
    * @throws Exception  参数说明 
    * @return ResultVO<ChannelListTreeVO>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "查询某站点下得栏目信息", notes = "查询某站点下得栏目信息")
    @RequestMapping(value = "/{siteid}/{orgid}/list",method = RequestMethod.POST)
    public ResultVO<ChannelListVO> getChannelBySite(@PathVariable(value="siteid")String siteid,
                                                        @PathVariable(value="orgid")String orgid,@RequestBody ChannelListRequestVO requestVO) throws Exception {
        if(StringUtils.isEmpty(siteid)) {
            return ResultVO.badRequest(CodeConstants.RESULT_ERR_PARAM, "站点ID不能为空");
        }
        return ResultVO.ok(iChannelService.getChannel(orgid, siteid,requestVO));
    }
    
    /**
     * 
    * @Title: 条件查询栏目信息 
    * @author shidandan
    * @date 2019年2月1日 下午6:52:59 
    * @Description: 条件查询栏目信息 
    * @param requestVO
    * @return
    * @throws Exception  参数说明 
    * @return ResultVO<List<Channel>>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "条件查询栏目信息", notes = "条件查询栏目信息")
    @RequestMapping(value = "/listAll",method = RequestMethod.POST)
    public ResultVO<List<Channel>> getChannelList(@RequestBody ChannelListRequestVO requestVO) throws Exception {
        QueryWrapper<Channel> channel = new QueryWrapper<Channel>();
        channel.eq("dstatus", 1);
        if(!CollectionUtils.isEmpty(requestVO.getChannelIds())) {
            channel.in("id", requestVO.getChannelIds());
        }
        return ResultVO.ok(iChannelService.list(channel));
    }
    

    /**
     * 
    * @Title: 新增子栏目 
    * @author shidandan
    * @date 2019年1月17日 下午4:13:33 
    * @Description: 新增子栏目 
    * @param channelCreateVO　创建子栏目的参数
    * @return　创建栏目得成功返回参数
    * @throws Exception  参数说明 
    * @return ResultVO<ChannelCreateResultVO>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "新增子栏目", notes = "新增子栏目")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultVO<ChannelCreateResultVO> createChannel(@Valid @RequestBody ChannelCreateVO channelCreateVO) throws Exception {
      return ResultVO.ok(iChannelService.createChannel(channelCreateVO));
    }
    
    /**
     * 
    * @Title: 通过id获取栏目详情 
    * @author shidandan
    * @date 2019年1月17日 下午4:23:31 
    * @Description: 通过id获取栏目详情 
    * @param id 栏目ID
    * @return 栏目详情
    * @throws Exception  参数说明 
    * @return ResultVO<ChannelDetailVO>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "通过id获取栏目详情", notes = "通过id获取栏目详情")
    @ApiImplicitParam(name = "id", value = "id", paramType = "query", required = true, dataType = "String")
    @RequestMapping(value = "/{id}/detail",method = RequestMethod.GET)
    public ResultVO<ChannelDetailVO> getChannel(@PathVariable(value="id") String id) throws Exception {
        if(StringUtils.isEmpty(id)) {
            return ResultVO.badRequest(CodeConstants.RESULT_ERR_PARAM, "参数有误，不可为空");
        }
        return ResultVO.ok(iChannelService.getChannelById(id));
    }
    
   /**
    * 
   * @Title: 更新栏目信息 
   * @author shidandan
   * @date 2019年1月17日 下午4:23:58 
   * @Description: 更新栏目信息
   * @param channel 栏目信息
   * @param id 栏目ID
   * @return
   * @throws Exception  参数说明 
   * @return ResultVO<Boolean>    返回类型 
   * @throws 
    */
    @ApiOperation(value = "更新栏目信息", notes = "更新栏目信息")
    @RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
    public ResultVO<Boolean> updateChannel(@Valid @RequestBody ChannelCreateVO channel,@PathVariable(value="id")String id) throws Exception {
        if(StringUtils.isEmpty(id)) {
            return ResultVO.badRequest(CodeConstants.RESULT_ERR_PARAM, "参数有误，不可为空");
        }
      Channel entity=new Channel();
      entity.setId(id);
      BeanUtils.copyProperties(channel, entity);
      return ResultVO.ok(iChannelService.updateById(entity));  
    }

    /**
     * 
    * @Title: 删除栏目 
    * @author shidandan
    * @date 2019年1月17日 下午4:24:38 
    * @Description: 删除栏目
    * @param id 栏目ID
    * @return
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "删除栏目", notes = "删除栏目")
    @ApiImplicitParam(name = "id", value = "id", paramType = "query", required = true, dataType = "String")
    @RequestMapping(value = "/{id}/del",method = RequestMethod.GET )
    public ResultVO<Boolean> delChannel(@PathVariable(value="id") String id) throws Exception {
        if(StringUtils.isEmpty(id)) {
            return ResultVO.badRequest(CodeConstants.RESULT_ERR_PARAM, "参数有误，不可为空");
        }
        return ResultVO.ok(iChannelService.delChannel(id));
    }
    
    /**
     * 
    * @Title: 查询栏目发布下线资源树接口 
    * @author zhangchengchun
    * @date 2019年1月23日 下午1:12:42 
    * @Description: 查询栏目发布下线资源树接口 
    * @param publishChanneRequestVO
    * @param @throws Exception  参数说明 
    * @return ResultVO List<ChannelCreateResultVO>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "获取栏目发布下线资源树", notes = "获取栏目发布下线资源树")
    @RequestMapping(value = "/publish/tree",method = RequestMethod.POST)
    public ResultVO<List<PublishChannelResponseVO>> getChannelPublist(@RequestBody PublishChanneRequest publishChanneRequestVO) throws Exception {
      return ResultVO.ok(iChannelService.getChannelPublishList(publishChanneRequestVO));
    }
    
    /**
     * 
    * @Title: 更新栏目状态接口 
    * @author zhangchengchun
    * @date 2019年1月29日 下午5:31:38 
    * @Description: 更新栏目状态接口 
    * @param logincId 栏目逻辑id
    * @param releaseStatus 栏目发布状态
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "更改栏目的发布状态", notes = "更改栏目的发布状态")
    @PostMapping(value = "/{logicId}/release/{status}")
    public ResultVO<Boolean> updateReleaseStatus(@PathVariable(value="logicId") Integer logicId,@PathVariable(value="status") Integer releaseStatus ) throws Exception{
       return ResultVO.ok(iChannelService.updateReleaseStatus(logicId,releaseStatus));
    }
}
