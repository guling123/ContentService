package cn.people.service.impl;

import cn.people.commons.constants.CodeConstants;
import cn.people.commons.exceptions.ContentBussinessException;
import cn.people.entity.Site;
import cn.people.mapper.SiteMapper;
import cn.people.service.ISiteService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 站点信息表 服务实现类
 * </p>
 *
 * @author shidandan
 * @since 2018-12-04
 */
@Service
public class SiteServiceImpl extends ServiceImpl<SiteMapper, Site> implements ISiteService {

    /**
     * 
    * Title: 初始化站点信息 
    * @author shidandan
    * @date 2019年1月18日 下午7:51:33 
    * @Description: 初始化站点信息  
    * @param param 站点信息
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @see cn.people.service.ISiteService#createSite(cn.people.entity.Site)  
     */
    @Override
    public Boolean createSite(Site param) throws Exception
    {

        //效验站点名称是否已经存在
        Site site=new Site();
        
        site.setSitename(param.getSitename());
        int count=this.count(new QueryWrapper<Site>(site));
        
        if(count>0) {
            throw new ContentBussinessException(CodeConstants.SITENAME_EXIST, "站点名称已经存在");
        }
        
        
        param.setCreatetime(new Date());
        return this.save(param);
    
    }

    /**
     * 
    * Title: 更新站点信息
    * @author shidandan
    * @date 2019年1月18日 下午7:52:30 
    * @Description: 更新站点信息
    * @param param 站点信息
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @see cn.people.service.ISiteService#updateSite(cn.people.entity.Site)
     */
    @Override
    public Boolean updateSite(Site param) throws Exception
    {
        //效验站点是否已经存在
        Site site=this.getById(param.getId());
        
        if(null==site) {
            throw new ContentBussinessException(CodeConstants.SITE_NOT_EXIST, "站点不存在");
        }
        
        
        return this.updateById(param);
    }

}
