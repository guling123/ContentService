package cn.people.service;

import cn.people.entity.Site;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 站点信息表 服务类
 * </p>
 *
 * @author shidandan
 * @since 2018-12-04
 */
public interface ISiteService extends IService<Site> {
    
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
    Boolean createSite(Site param) throws Exception;
    
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
    Boolean updateSite(Site param) throws Exception;
}
