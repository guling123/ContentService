package cn.people.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.people.controller.vo.ResultVO;
import cn.people.entity.Site;
import cn.people.service.ISiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
* @ClassName: SiteController 
* @Description: 站点信息管理
* @author shidandan
* @date 2019年1月18日 下午7:50:49 
*  
 */
@RestController
@RequestMapping("/site")
@Api(value = "站点信息管理", description = "站点信息管理")
public class SiteController {

    @Autowired
    private ISiteService iSiteService;
    
    /**
     * 
    * @Title: 初始化站点信息 
    * @author shidandan
    * @date 2019年1月18日 下午7:51:33 
    * @Description: 初始化站点信息  
    * @param param 站点信息
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "初始化站点信息", notes = "初始化站点信息")
    @ApiImplicitParam(name = "Site", value = "param", paramType = "query", required = true, dataType = "TbSite")
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public ResultVO<Boolean> createSite(@RequestBody Site param) throws Exception{
        
        return ResultVO.ok(iSiteService.createSite(param));
    }
    
    /**
     * 
    * @Title: 更新站点信息
    * @author shidandan
    * @date 2019年1月18日 下午7:52:30 
    * @Description: 更新站点信息
    * @param param 站点信息
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return ResultVO<Boolean>    返回类型 
    * @throws 
     */
    @ApiOperation(value = "更新站点信息", notes = "初始化站点信息")
    @ApiImplicitParam(name = "Site", value = "param", paramType = "query", required = true, dataType = "TbSite")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVO<Boolean> updateSite(@RequestBody Site param) throws Exception{
        
        return ResultVO.ok(iSiteService.updateSite(param));
    }
}
