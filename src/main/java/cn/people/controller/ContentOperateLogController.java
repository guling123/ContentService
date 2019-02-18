package cn.people.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.people.commons.enumeration.OperateTypeEnum;
import cn.people.controller.vo.ContentOperateLogVO;
import cn.people.controller.vo.ResultVO;
import cn.people.entity.ContentOperateLog;
import cn.people.service.IContentOperateLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
* @ClassName: ContentOperateLogController 
* @Description: 内容操作日志管理
* @author gaoyongjiu
* @date 2019年1月18日 下午7:47:15 
*  
 */
@RestController
@RequestMapping("/content/operateLog")
@Api(value = "内容操作日志管理", description = "内容操作日志管理")
public class ContentOperateLogController {
    @Autowired
    private IContentOperateLogService iContentOperateLogService;
    
    /**
     * 
    * @Title: 获取内容操作日志集合 
    * @author shidandan
    * @date 2019年1月18日 下午7:47:45 
    * @Description: 获取内容操作日志集合 
    * @param id 内容id
    * @return 内容操作日志集合
    * @throws Exception  参数说明 
    * @return ResultVO<List<ContentOperateLogVO>>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "获取内容操作日志集合", notes = "获取内容操作日志集合")
    @ApiImplicitParam(name = "id", value = "Content id", paramType = "path", required = true)
    @GetMapping("/{id}/list")
    public ResultVO<List<ContentOperateLogVO>> getContentById(@PathVariable("id") String id) throws Exception {
        
        ContentOperateLog contentOperateLog=new ContentOperateLog();
        contentOperateLog.setContentId(id);
        List<ContentOperateLog> list=iContentOperateLogService.list(new QueryWrapper<ContentOperateLog>(contentOperateLog));
        if(!CollectionUtils.isEmpty(list)) {
            List<ContentOperateLogVO> result=new ArrayList<ContentOperateLogVO>();
            list.forEach(log ->{
                ContentOperateLogVO vo=new ContentOperateLogVO();
                BeanUtils.copyProperties(log, vo);
                vo.setDtypeDesc(OperateTypeEnum.getDesc(log.getDtype()));
                vo.setDtype(log.getDtype().toString());
                vo.setContentid(log.getContentId());
                vo.setCreatetime(log.getCreateTime());
                vo.setOperatorid(log.getOperatorId());
                result.add(vo);
            });
            return ResultVO.ok(result);
        }
        return ResultVO.ok(null);
    }
}
