package cn.people.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.people.controller.vo.ChannelPageVo;
import cn.people.controller.vo.ContentPageVo;
import cn.people.controller.vo.ModelSplitMapResponseVO;
import cn.people.controller.vo.ResultVO;
import cn.people.service.IViewService;
import cn.people.service.view.obj.SharedConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * 模板预览Controller
 * 
 * @ClassName: ViewController 
 * @Description: 模板预览Controller
 * @author zhangchengchun
 * @date 2019年1月15日 下午8:02:57    
 */
@RestController
@RequestMapping("/view")
@Api(value = "模板预览", description = "模板预览")
public class ViewController
{
    @Autowired
    private IViewService iViewService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewController.class);
    /**
     * 
    * @Title: 查询栏目的模板原内容 
    * @author zhangchengchun
    * @date 2019年1月21日 上午10:33:18 
    * @Description: 查询栏目的模板原内容
    * @param logicId 栏目逻辑id
    * @return 栏目的模板原内容
    * @throws Exception  参数说明 
    * @return ResultVO<String>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "查询栏目的模板原内容", notes = "查询栏目的模板原内容")
    @RequestMapping(value = "/channel/{logicId}/mod", method = RequestMethod.GET)
    public ResultVO<String> getChannelTemplateMod(@PathVariable(value = "logicId") Integer logicId)
        throws Exception
    {
        return ResultVO.ok(iViewService.getChannelTemplateContent(logicId));
    }

    /**
     * 
    * @Title: 查询栏目的模板中的标记集合
    * @author zhangchengchun
    * @date 2019年1月21日 上午10:37:42 
    * @Description: 查询栏目的模板中的标记集合
    * @param logicId 栏目逻辑id
    * @return 栏目的模板中的标记集合
    * @throws Exception  参数说明 
    * @return ResultVO<List<ModelSplitMapResponseVO>>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "查询栏目的模板中的标记集合", notes = "查询栏目的模板中的标记集合")
    @RequestMapping(value = "/channel/{logicId}/splits", method = RequestMethod.GET)
    public ResultVO<List<ModelSplitMapResponseVO>> getChannelTemplateSplits(@PathVariable(value = "logicId") Integer logicId)
        throws Exception
    {

        return ResultVO.ok(iViewService.getChannelTemplateSplits(logicId));
    }

    /**
     * 
    * @Title: 预览栏目的模板 
    * @author zhangchengchun
    * @date 2019年1月21日 上午10:39:17 
    * @Description: 预览栏目的模板
    * @param logicId 栏目逻辑id
    * @param pageNo 当前页码，可以不传
    * @param info 调试信息，支持值：info|debug
    * @param vtype 预览类型，预览时不传，静态化时传publish
    * @return 预览栏目的模板 
    * @throws Exception  参数说明 
    * @return ResultVO<String>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "预览栏目的模板", notes = "预览栏目的模板")
    @RequestMapping(value = "/channel/{logicId}", method = RequestMethod.GET)
    public ResultVO<String> getChannelTemplate(@ApiParam(value = "栏目逻辑id", required = true) @PathVariable(value = "logicId") Integer logicId,
                                               @ApiParam(value = "浏览第几页，可不传", required = false) @RequestParam(value="page",defaultValue="0",required=false) Integer pageNo,
                                               @ApiParam(value = "是否返回日志，支持值：info|debug", required = false) @RequestParam(value="info",defaultValue="",required=false) String info,
                                               @ApiParam(value = "是否要静态化 支持值：edit|view|publish 分别对应 可视化编辑|预览|发布", required = false) @RequestParam(value="vtype",defaultValue="",required=false) String vtype)
        throws Exception
    {        
        return ResultVO.ok(iViewService.getChannelPreview(logicId, pageNo, info, vtype));
    }

    /**
     * 
    * @Title: 预览稿件的模板
    * @author zhangchengchun
    * @date 2019年1月21日 上午10:40:55 
    * @Description: 预览稿件的模板
    * @param contentLogicId 稿件的逻辑id
    * @param pageNo 当前页码，可以不传
    * @param info 调试信息，支持值：info|debug
    * @param vtype 预览类型，预览时不传，静态化时传publish
    * @return 预览稿件的模板
    * @throws Exception  参数说明 
    * @return ResultVO<String>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "预览稿件的模板", notes = "预览稿件的模板")
    @RequestMapping(value = "/content/{contentLogicId}", method = RequestMethod.GET)
    public ResultVO<String> getContentTemplate(@ApiParam(value = "稿件id", required = true)@PathVariable(value = "contentLogicId") Integer contentLogicId,
                                               @ApiParam(value = "浏览第几页，可不传", required = false) @RequestParam(value="page",defaultValue="0",required=false) Integer pageNo,
                                               @ApiParam(value = "是否返回日志，支持值：info|debug", required = false) @RequestParam(value="info",defaultValue="",required=false) String info,
                                               @ApiParam(value = "是否要静态化", required = false) @RequestParam(value="vtype",defaultValue="",required=false) String vtype)
        throws Exception
    {
        LOGGER.info("请求预览稿件入参-->contentLogicId："+contentLogicId+"  page:"+pageNo+"  info:"+info+"  vtype:"+vtype);
        return ResultVO.ok(iViewService.getNewsPreview(contentLogicId, pageNo, info, vtype));
    }
    
    /**
     * 
    * @Title: 查询稿件分页信息接口
    * @author zhangchengchun
    * @date 2019年1月31日 上午9:39:35 
    * @Description: 查询稿件分页信息接口
    * @param contentLogicId 稿件逻辑id
    * @param @return
    * @param @throws Exception  参数说明 
    * @return ResultVO<List<ContentPageVo>>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "查询稿件分页信息", notes = "查询稿件分页信息")
    @RequestMapping(value = "/content/{contentLogicId}/pages", method = RequestMethod.GET)
    public ResultVO<List<ContentPageVo>> getContentPages(@ApiParam(value = "稿件逻辑id", required = true)@PathVariable(value = "contentLogicId") Integer contentLogicId)
        throws Exception
    {
        return ResultVO.ok(iViewService.getContentPages(contentLogicId));
    }
        
    /**
     * 
    * @Title: 查询栏目分页信息 
    * @author zhangchengchun
    * @date 2019年1月31日 上午10:29:38 
    * @Description: 查询栏目分页信息
    * @param logicId 栏目逻辑id
    * @param @return
    * @param @throws Exception  参数说明 
    * @return ResultVO<List<ChannelPageVo>>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "查询栏目分页信息", notes = "查询栏目分页信息")
    @RequestMapping(value = "/channel/{logicId}/pages", method = RequestMethod.GET)
    public ResultVO<List<ChannelPageVo>> getChannelPages(@ApiParam(value = "栏目逻辑id", required = true) @PathVariable(value = "logicId") Integer logicId)
        throws Exception
    {        
        return ResultVO.ok(iViewService.getChannelPages(logicId));
    }
}
