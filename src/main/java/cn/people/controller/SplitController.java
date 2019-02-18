 package cn.people.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.people.controller.vo.ResultVO;
import cn.people.entity.Split;
import cn.people.service.ISplitService;
import io.swagger.annotations.Api;

/**
 * 
* @ClassName: SplitController 
* @Description: 碎片管理
* @author fanchengkui
* @date 2019年1月18日 下午7:53:43 
*  
 */
@RestController
@RequestMapping("/split")
@Api(value = "碎片管理", description = "碎片管理")
public class SplitController {
 
    @Autowired
    private ISplitService iSplitService;
    /**
     * 
    * @Title: 查询未被某个模板关联得碎片 
    * @author shidandan
    * @date 2019年1月22日 下午2:07:09 
    * @Description: 查询未被某个模板关联得碎片 
    * @param modelid 模板ID
    * @return  参数说明 
    * @return ResultVO<List<Split>>    返回类型 
    * @throws 
     */
    @RequestMapping(value ="/list", method = RequestMethod.GET)
    ResultVO<List<Split>> getSplit(){
        
        return ResultVO.ok(iSplitService.getSplit());
    }
}
