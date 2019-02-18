package cn.people.service;

import cn.people.entity.SplitProp;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 碎片属性表对应原表TAG_PROP 服务类
 * </p>
 *
 * @author shidandan
 * @since 2019-01-07
 */
public interface ISplitPropService extends IService<SplitProp> {

    /**
     * 
    * @Title: 查询所有模板碎片未添加的碎片属性 
    * @author shidandan
    * @date 2019年1月22日 下午7:57:54 
    * @Description: 查询所有模板碎片未添加的碎片属性
    * @param splitId 碎片ID
    * @param modelSpiltId 模板碎片ID
    * @return  参数说明 
    * @return List<SplitProp>    返回类型 
    * @throws 
     */
    List<SplitProp> getSplitProp(String splitId,String modelSpiltId);
}
