/**   
* @Title: SplitPropController.java 
* @Package cn.people.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author shidandan
* @date 2019年1月22日 下午7:51:04 
* @version V1.0   
*/
package cn.people.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import cn.people.commons.constants.CodeConstants;
import cn.people.controller.vo.ResultVO;
import cn.people.entity.SplitMod;
import cn.people.entity.SplitProp;
import cn.people.service.ISplitPropService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @ClassName: SplitPropController 
* @Description: 碎片属性 
* @author shidandan
* @date 2019年1月22日 下午7:51:04 
*  
*/
@RestController
@RequestMapping("/split/prop")
@Api(value = "碎片属性管理", description = "碎片属性管理")
public class SplitPropController
{
    @Autowired
    private ISplitPropService iSplitPropService;
    
    /**
     * 
    * @Title: 查询所有模板碎片未添加的碎片属性 
    * @author shidandan
    * @date 2019年1月22日 下午8:07:49 
    * @Description: 查询所有模板碎片未添加的碎片属性
    * @param splitId 碎片ID
    * @param modelSpiltId 模板碎片ID
    * @return
    * @throws Exception  参数说明 
    * @return ResultVO<List<SplitProp>>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "查询所有模板碎片未添加的碎片属性", notes = "查询所有得碎片方案")
    @RequestMapping(value ="/{splitId}/{modelSpiltId}/list", method = RequestMethod.GET)
    public ResultVO<List<SplitProp>> getSplitProp(@PathVariable(value="splitId")String splitId,@PathVariable(value="modelSpiltId")String modelSpiltId) throws Exception{
        if(StringUtils.isEmpty(splitId)||StringUtils.isEmpty(modelSpiltId)) {
            ResultVO.badRequest(CodeConstants.RESULT_ERR_PARAM, "参数错误");
        }
        return ResultVO.ok(iSplitPropService.getSplitProp(splitId, modelSpiltId));
    }

}
