package cn.people.service;

import cn.people.controller.vo.*;
import cn.people.entity.ModelSplitProp;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.ArrayList;
import java.util.List;


/**
 * 
* @ClassName: IModelSplitPropService 
* @Description: 模板碎片属性接口
* @author shidandan
* @date 2019年1月17日 下午8:31:49 
*  
 */
public interface IModelSplitPropService extends IService<ModelSplitProp> {

    /**
     * 
    * @Title: 查询某个栏目下的模板碎片属性 
    * @author shidandan
    * @date 2019年1月17日 下午8:32:05 
    * @Description: 查询某个栏目下的模板碎片属性 
    * @param channelId 栏目ID
    * @param modelSplitId 模板碎片ID
    * @return 模板碎片属性集合
    * @throws Exception  参数说明 
    * @return ArrayList<SplitPropListVO>    返回类型 
    * @throws 
     */
    ArrayList<SplitPropListVO> getModelSplitPropListByChannel(String channelId, String modelSpiltLogicId) throws Exception ;

    /**
     * 
    * @Title: 查询含有分页信息的模板碎片属性 
    * @author shidandan
    * @date 2019年1月17日 下午8:33:20 
    * @Description: 查询含有分页信息的模板碎片属性
    * @param tagIdList 碎片ID集合
    * @return  参数说明 
    * @return List<ModelSplitProp>    返回类型 
    * @throws 
     */
    List<ModelSplitProp> selectHasPage(List<String> tagIdList);

	/**
	 * 
	* @Title: 查询某个模板碎片下的模板碎片属性 
	* @author shidandan
	* @date 2019年1月17日 下午8:33:50 
	* @Description: 查询某个模板碎片下的模板碎片属性 
	* @param modelSpiltId 模板碎片ID
	* @return 模板碎片属性集合
	* @throws Exception  参数说明 
	* @return List<ModelSplitPropListVO>    返回类型 
	* @throws 
	 */
	ModelSplitPropListParentVO getModelSplitPropList(String modelSpiltId) throws Exception;

    /**
     * 
    * @Title: 创建碎片模板属性 
    * @author shidandan
    * @date 2019年1月17日 下午8:34:39 
    * @Description: 创建碎片模板属性 
    * @param modelSplitId 模板碎片ID
    * @param createVO 模板碎片属性值
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return Boolean    返回类型 
    * @throws 
     */
    Boolean createModelSplitProp(String modelSplitId,ModelSplitPropCreateVO createVO) throws Exception;
    /**
     * 
    * @Title: 更新栏目下的模板碎片属性 
    * @author shidandan
    * @date 2019年1月17日 下午8:35:21 
    * @Description: 更新栏目下的模板碎片属性
    * @param updateVO 模板碎片属性值
    * @param id 模板碎片属性ID
    * @param channelId 栏目ID
    * @return  成功返回true
    * @return Boolean    返回类型 
    * @throws 
     */
    Boolean updateModelSplitPropByChannel(ModelSplitPropBatchUpdateVO updateVO);

}
