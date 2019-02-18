package cn.people.service;

import cn.people.entity.IdGenerater;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 数值 ID 生成器 简易 服务类
 * </p>
 *
 * @author shidandan
 * @since 2019-01-10
 */
public interface IIdGeneraterService extends IService<IdGenerater> {

    /**
     * 
    * @Title: getOrgid 
    * @author shidandan
    * @date 2019年1月2日 上午10:11:09 
    * @Description: 获取下一个
    * @param @return  参数说明 
    * @return Integer    返回类型 
    * @throws 
     */
    Integer getNextValue(String entityName);
    
}
