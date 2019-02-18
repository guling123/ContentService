package cn.people.mapper;

import cn.people.entity.ModelSplitProp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 模板碎片属性表,对应原TEMPLATE_TAG_PROP Mapper 接口
 * </p>
 *
 * @author shidandan
 * @since 2019-01-07
 */
public interface ModelSplitPropMapper extends BaseMapper<ModelSplitProp> {

    /**
     * @author guling
     * @Title   getModelSplitPropListByChannel
     * @Date    2019/1/10 16:13
     * @param   modelSpiltId
     * @return  java.util.ArrayList<cn.people.controller.vo.SplitPropListVO>
     * @throws
     * @Description 通过modelSpiltId获取集合
     */
    List<Map<String,String>> getModelSplitPropListByChannel(@Param("modelSpiltLogicId") String modelSpiltLogicId);
    
    /**
     * 
    * @Title: selectHasPage 
    * @author zhangchengchun
    * @date 2019年1月11日 上午10:16:55 
    * @Description: 查询含有分页信息的模板碎片属性
    * @param @param tagIdList 模板碎片逻辑id集合
    * @param @return  参数说明 
    * @return List<ModelSplitProp>    返回类型 
    * @throws 
     */
    List<ModelSplitProp> selectHasPage(@Param("tagIdList")List<String> tagIdList);

}
