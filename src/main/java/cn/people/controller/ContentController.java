package cn.people.controller;

import java.util.List;

import cn.people.controller.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.people.commons.constants.CodeConstants;
import cn.people.entity.Content;
import cn.people.entity.ContentVersion;
import cn.people.service.IContentService;
import cn.people.service.IContentVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
* @ClassName: ContentController 
* @Description: 内容信息管理
* @author shidandan
* @date 2019年1月18日 下午7:20:05 
*  
 */
@RestController
@RequestMapping("/content")
@Api(value = "内容信息管理", description = "内容信息管理")
public class ContentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentController.class);
    @Autowired
    private IContentService contentService;

    @Autowired
    private IContentVersionService contentVersionService;

    /**
     * 
    * @Title: 添加内容 
    * @author shidandan
    * @date 2019年1月18日 下午7:20:34 
    * @Description: 添加内容 
    * @param param 内容详情
    * @return 添加内容结果
    * @throws Exception  参数说明 
    * @return ResultVO<ContentCreateResultVO>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "添加内容", notes = "添加内容")
    @PostMapping("/add")
    public ResultVO<ContentCreateResultVO> createContent(@RequestBody ContentCreateVO param) throws Exception {
        return ResultVO.ok(contentService.createContent(param));
    }

    /**
     * 
    * @Title: 回收站列表查询 
    * @author shidandan
    * @date 2019年1月18日 下午7:21:58 
    * @Description: 回收站列表查询 
    * @param param 回收站内容列表查询参数
    * @return 回收站列表
    * @throws Exception  参数说明 
    * @return ResultVO<Page<ContentRecycledListResultVO>>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "回收站列表查询", notes = "回收站列表查询")
    @ApiImplicitParam(name = "ContentCreateVO", value = "param", paramType = "query", required = true, dataType = "ContentCreateVO")
    @GetMapping("/recycled")
    public ResultVO<Page<ContentRecycledListResultVO>> getContentRecycledPaged(ContentRecycledListVO param) throws Exception {
        return ResultVO.ok(contentService.getContentRecycledPaged(param));
    }

    /**
     * 
    * @Title: 内容列表查询
    * @author gaoyongjiu
    * @date 2019年1月18日 下午7:25:31 
    * @Description: 内容列表查询
    * @param param 内容查询参数
    * @return  内容列表
    * @return ResultVO<List<Content>>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "内容列表查询", notes = "内容列表查询")
    @ApiImplicitParam(name = "param", value = "param", paramType = "query", required = true, dataType = "ContentQueryVO")
    @PostMapping("/list")
    public ResultVO<Page<ContentListVO>> getContentPaged(@RequestBody ContentQueryVO param) {
        LOGGER.info("修改碎片对应的内容列表,入参,param={}",JSON.toJSONString(param));
        if (param.getSiteid() == null || "".equals(param.getSiteid())) {
            // 获取失败直接返回
            return ResultVO.badRequest(CodeConstants.RESULT_ERR_PARAM, "站点id不存在，请确认参数是否正确");
        }
       Content content = new Content();
        // 整理查询条件
        BeanUtils.copyProperties(param, content);

        Page<ContentListVO> page = new Page<>(param.getPageNo(), param.getPageSize());
        // 分页查询
        Page<ContentListVO> rnt = contentService.getContentPaged(page,param);

        return ResultVO.ok(rnt);
    }
    
    /**
     * 
    * @Title: 获取内容详情
    * @author shidandan
    * @date 2019年1月18日 下午7:31:18 
    * @Description: 获取内容详情
    * @param id 内容id
    * @return 内容详情
    * @throws 内容详情 
    * @return ResultVO<ContentDetailVO>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "获取内容详情", notes = "获取内容详情")
    @ApiImplicitParam(name = "id", value = "Content id", paramType = "path", required = true)
    @GetMapping("/{id}/detail")
    public ResultVO<ContentDetailVO> getContentById(@PathVariable("id") String id) throws Exception {
        return ResultVO.ok(contentService.getContentById(id));
    }
    
    /**
     * 
    * @Title: 根据逻辑ID查询内容详情 
    * @author shidandan
    * @date 2019年1月30日 下午6:05:04 
    * @Description: 根据逻辑ID查询内容详情 
    * @param logicId 逻辑ID
    * @return
    * @throws Exception  参数说明 
    * @return ResultVO<ContentDetailVO>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "根据逻辑ID查询内容详情", notes = "根据逻辑ID查询内容详情")
    @ApiImplicitParam(name = "logicId", value = "Content id", paramType = "path", required = true)
    @GetMapping("/{logicId}/detailByLogicId")
    public ResultVO<ContentDetailVO> getContentByLogicId(@PathVariable("logicId") Integer logicId) throws Exception {
        return ResultVO.ok(contentService.getContentByLogicId(logicId));
    }

    /**
     * 
    * @Title: 删除内容
    * @author tianweisong
    * @date 2019年1月18日 下午7:32:40 
    * @Description: 删除内容
    * @param id 内容id
    * @return 删除内容结果
    * @return ResultVO<ContentDelResultVO>    返回类型 
     * @throws Exception 
    * @throws 
     */
    @ApiOperation(value = "删除内容", notes = "删除内容")
    @ApiImplicitParam(name = "id", value = "Content id", paramType = "path", required = true)
    @GetMapping("/{id}/del")
    public ResultVO<Boolean> delContent(@PathVariable("id") String id) throws Exception {
        return ResultVO.ok(contentService.delContent(id));
    }

    /**
     * 
    * @Title: 内容的审核通过
    * @author shidandan
    * @date 2019年1月18日 下午7:34:40 
    * @Description: 内容的审核通过
    * @param id 内容id
    * @param auditVO 文章审核
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "内容的审核通过", notes = "内容的审核通过")
    @ApiImplicitParam(name = "id", value = "Content id", paramType = "path", required = true)
    @PostMapping("/{id}/pass")
    public ResultVO<Boolean> passContent(@PathVariable("id") String id,@RequestBody ContentAuditVO auditVO) throws Exception {

        return ResultVO.ok(contentService.auditContent(id, auditVO,1));
    }

    /**
     *
    * @Title: 内容的审核驳回 
    * @author shidandan
    * @date 2019年1月25日 下午2:00:18 
    * @Description: 内容的审核驳回 
    * @param id 内容ID
    * @param auditVO
    * @return
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "内容的审核驳回", notes = "内容的审核驳回")
    @ApiImplicitParam(name = "id", value = "Content id", paramType = "path", required = true)
    @PostMapping("/{id}/reject")
    public ResultVO<Boolean> rejectContent(@PathVariable("id") String id,@RequestBody ContentAuditVO auditVO) throws Exception {

        return ResultVO.ok(contentService.auditContent(id, auditVO,2));
    }

    /**
     * 
    * @Title: 从回收站还原内容
    * @author tianweisong
    * @date 2019年1月18日 下午7:36:00 
    * @Description: 从回收站还原内容
    * @param id 内容id
    * @return  还原内容结果
    * @return ResultVO<ContentRestoreResultVO>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "从回收站还原内容", notes = "从回收站还原内容")
    @ApiImplicitParam(name = "id", value = "Content id", paramType = "path", required = true)
    @PatchMapping("/{id}/restore")
    public ResultVO<ContentRestoreResultVO> restoreContent(@PathVariable("id") String id) {
        return ResultVO.ok(contentService.restoreContent(id));
    }

    /**
     * 
    * @Title: 更新内容
    * @author shidandan
    * @date 2019年1月18日 下午7:37:04 
    * @Description: 更新内容
    * @param id 内容id
    * @param content 更新内容请求参数
    * @return 更新内容结果
    * @throws Exception  参数说明 
    * @return ResultVO<ContentUpdateResultVO>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "更新内容", notes = "更新内容")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "Content id", paramType = "path", required = true),
        @ApiImplicitParam(name = "content", value = "content", paramType = "body", required = true)
    })
    @PutMapping("/{id}/update")
    public ResultVO<ContentUpdateResultVO> updateContent(@PathVariable("id") String id,
                                                         @RequestBody ContentUpdateVO content) throws Exception {
        return ResultVO.ok(contentService.updateContent(id, content));
    }

    /**
     * 
    * @Title: 获取内容版本列表
    * @author gaoyongjiu
    * @date 2019年1月18日 下午7:38:45 
    * @Description: 获取内容版本列表
    * @param id 内容id
    * @param pageNo 当前页
    * @param pageSize 每页条数
    * @return  获取内容版本列表
    * @return ResultVO<IPage<ContentVersion>>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "内容版本列表", notes = "内容版本列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", paramType = "path", required = true),
        @ApiImplicitParam(name = "page", value = "page", paramType = "query", required = true)
    })
    @GetMapping("/{id}/version")
    public ResultVO<IPage<ContentVersion>> getContentVersionPaged(@PathVariable("id") String id,
                                                                  @ApiParam(value="当前页",required=true)@RequestParam("pageNo")int pageNo,
                                                                  @ApiParam(value="每页条数",required=true)@RequestParam("pageSize")int pageSize) {
        ContentVersion contentVersion = new ContentVersion();
        contentVersion.setContentid(id);

        IPage<ContentVersion> rtn = contentVersionService.page(
            new Page<>(pageNo, pageSize),
            new QueryWrapper<>(contentVersion));

        return ResultVO.ok(rtn);
    }

    /**
     * 
    * @Title: 获取版本详细信息
    * @author  gaoyongjiu
    * @date 2019年1月18日 下午7:41:41 
    * @Description: 获取版本详细信息
    * @param id 内容版本id
    * @param versionid 版本id
    * @return  版本详细信息
    * @return ResultVO<ContentVersionVO>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "获取版本详细信息", notes = "获取版本详细信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", paramType = "path", required = true),
        @ApiImplicitParam(name = "versionid", value = "versionid", paramType = "path", required = true)
    })
    @GetMapping("/{id}/{versionid}/version")
    public ResultVO<ContentVersionVO> getContentVersionInfo(@PathVariable("id") String id,
                                                            @PathVariable("versionid") String versionid) {
        // 先获取版信息
        ContentVersion contentVersion = contentVersionService.getById(versionid);
        if (contentVersion == null) {
            // 获取失败直接返回
            return ResultVO.badRequest(CodeConstants.RESULT_ERR_PARAM, "版本信息不存在，请确认参数是否正确");
        }

        // 装配VO
        ContentVersionVO vo = new ContentVersionVO(contentVersion);

        return ResultVO.ok(vo);
    }
       
    /**
     * 
    * @Title: 更新稿件发布状态 
    * @author zhangchengchun
    * @date 2019年1月29日 下午6:30:33 
    * @Description: 更新稿件发布状态
    * @param logicId 稿件逻辑id
    * @param status 稿件发布状态
    * @param @return
    * @param @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "更新稿件发布状态", notes = "更新稿件发布状态")
    @PostMapping(value = "/{logicId}/release/{status}")
    public ResultVO<Boolean> updateReleaseStatus(@PathVariable("logicId") Integer logicId,@PathVariable("status") Integer status) throws Exception{
        LOGGER.info("已发布稿件入参logicId="+logicId +"状态："+status);
       
        return ResultVO.ok(contentService.updateReleaseStatus(logicId,status));
    }
}
