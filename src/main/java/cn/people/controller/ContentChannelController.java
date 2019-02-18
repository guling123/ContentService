package cn.people.controller;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.people.commons.constants.CodeConstants;
import cn.people.controller.vo.ResultVO;
import cn.people.entity.ContentChannel;
import cn.people.service.IContentChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
* @ClassName: ContentChannelController 
* @Description: 内容栏目信息管理
* @author shidandan
* @date 2019年1月18日 下午7:18:18 
*  
 */
@RestController
@RequestMapping("/content/channel")
@Api(value = "内容栏目信息管理", description = "内容栏目信息管理")
public class ContentChannelController {

    @Autowired
    private IContentChannelService iContentChannelService;
    
    /**
     * 
    * @Title: 内容列表查询 
    * @author shidandan
    * @date 2019年1月18日 下午7:18:56 
    * @Description: 内容列表查询 
    * @param channelid 栏目ID
    * @return 内容列表
    * @return ResultVO<List<ContentChannel>>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "内容列表查询", notes = "内容列表查询")
    @ApiImplicitParam(name = "param", value = "param", paramType = "query", required = true, dataType = "ContentQueryVO")
    @GetMapping("/{channelid}/list")
    public ResultVO<List<ContentChannel>> getContentByChannel(@PathVariable(value="channelid") String channelid) {

        if (StringUtils.isEmpty(channelid)) {
            // 获取失败直接返回
            return ResultVO.badRequest(CodeConstants.RESULT_ERR_PARAM, "站点id不存在，请确认参数是否正确");
        }
        ContentChannel entity=new ContentChannel();
        entity.setChannelid(channelid);
        
        return ResultVO.ok(iContentChannelService.list(new QueryWrapper<ContentChannel>(entity)));
    }
}
