package cn.people.service;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import cn.people.controller.vo.ModelDetailVO;
import cn.people.controller.vo.ModelListRequestVO;
import cn.people.controller.vo.ModelPageVO;
import cn.people.controller.vo.ModelVO;
import cn.people.entity.Model;

/**
 * <p>
 * 模板表 服务类
 * </p>
 *
 * @author shidandan
 * @since 2018-12-19
 */
public interface IModelService extends IService<Model> {

    /**
     * 
    * @Title: 查询模板列表
    * @author shidandan
    * @date 2019年1月18日 下午2:10:39 
    * @Description: 查询模板列表
    * @param modelListRequestVo 模板信息
    * @return  成功返回模板列表
    * @return IPage<Model>    返回类型 
    * @throws 
     */
    IPage<ModelPageVO> getModelPaged(ModelListRequestVO modelListRequestVo,String siteid);
    
    /**
     * 
    * @Title: 查询某个站点下某种类型的模板  
    * @author shidandan
    * @date 2019年1月22日 下午7:13:45 
    * @Description: 查询某个站点下某种类型的模板 
    * @param siteid 站点ID
    * @param typeid 类型ID
    * @return  参数说明 
    * @return List<Model>    返回类型 
    * @throws 
     */
    List<Model> getModel(String siteid,String typeid);
    /**
     * 
    * @Title: 创建模板
    * @author shidandan
    * @date 2019年1月18日 下午2:11:41 
    * @Description: 创建模板
    * @param modelVo 模板信息
    * @return 成功返回模板id
    * @throws Exception  参数说明 
    * @return String    返回类型 
    * @throws 
     */
    String createModel(ModelVO modelVo) throws Exception;
    
    /**
     * 
    * @Title: 查询模板详情
    * @author shidandan
    * @date 2019年1月15日 下午6:02:32 
    * @Description: 查询模板详情
    * @param @param id
    * @param @return  参数说明 
    * @return ModelDetailVO    返回类型 
    * @throws 
     */
    ModelDetailVO getModel(String id);
    
    /**
     * 
    * @Title: 修改模板详细信息
    * @author shidandan
    * @date 2019年1月18日 下午1:33:19 
    * @Description: 修改模板详细信息
    * @param model 模板详细信息
    * @param id 模板id
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return Boolean    返回类型 
    * @throws 
     */
    Boolean modelUpdate(ModelVO modelVo, String id) throws Exception;
    
    /**
     * 
    * @Title: 启用模板
    * @author shidandan
    * @date 2019年1月18日 下午2:17:30 
    * @Description: 启用模板
    * @param id 模板id
    * @return 成功返回true
    * @return Boolean    返回类型 
    * @throws 
     */
    Boolean enabledModel(String id);

    /**
     * 
    * @Title: 禁用模板 
    * @author shidandan
    * @date 2019年1月18日 下午2:18:35 
    * @Description: 禁用模板 
    * @param id 模板id
    * @return 成功返回true
    * @return Boolean    返回类型 
    * @throws 
     */
    Boolean disableModel(String id);


    
}
