package cn.people.service;

import cn.people.controller.vo.ModelSplitDetailVO;
import cn.people.controller.vo.ModelSplitPageVO;
import cn.people.controller.vo.ModelSplitVO;
import cn.people.controller.vo.SplitCreateVO;
import cn.people.controller.vo.SplitPageVO;
import cn.people.controller.vo.SplitUpdateVO;
import cn.people.entity.ModelSplit;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 
* @ClassName: IModelSplitService 
* @Description: 模板碎片接口
* @author shidandan
* @date 2019年1月17日 下午8:26:51 
*  
 */
public interface IModelSplitService extends IService<ModelSplit> {
    
    /**
     * 
    * @Title: 创建模板碎片 
    * @author shidandan
    * @date 2019年1月17日 下午8:18:03 
    * @Description: 创建模板碎片
    * @param split 模板碎片
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return Boolean    返回类型 
    * @throws 
     */
     Boolean createModelSplit(SplitCreateVO split) throws Exception;
    /**
     * 
    * @Title: 通过模板碎片逻辑ID查询模板碎片信息 
    * @author shidandan
    * @date 2019年1月17日 下午8:19:24 
    * @Description: 通过模板碎片逻辑ID查询模板碎片信息
    * @param ttId 模板碎片逻辑ID
    * @return  参数说明 
    * @return ModelSplit    返回类型 
    * @throws 
     */
    ModelSplit getByLogicId(int ttId);

    /**
     * 
    * @Title: 更新模板碎片 
    * @author shidandan
    * @date 2019年1月17日 下午8:18:46 
    * @Description: 更新模板碎片
    * @param id 模板碎片ID
    * @param split 模板碎片信息
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return Boolean    返回类型 
    * @throws 
     */
	Boolean updateSplit(String id, SplitUpdateVO split) throws Exception;
	
	/**
	 * 
	* @Title: 分页查询模板碎片信息 
	* @author shidandan
	* @date 2019年1月17日 下午8:20:04 
	* @Description: 分页查询模板碎片信息 
	* @param split 模板碎片信息
	* @param modelid 模板ID
	* @return  模板碎片集合
	* @return IPage<ModelSplit>    返回类型 
	 * @throws Exception 
	* @throws 
	 */
	ModelSplitPageVO getModelSplitPaged(SplitPageVO split,String modelid) throws Exception;
    /**
     * 
    * @Title: 查询所有模板碎片 
    * @author shidandan
    * @date 2019年1月22日 下午1:35:27 
    * @Description: 查询所有模板碎片
    * @param modelid
    * @return  参数说明 
    * @return List<ModelSplit>    返回类型 
    * @throws 
     */
    List<ModelSplit> getModelSplit(String modelid);
    
    /**
     * 
    * @Title: 获取模板碎片信息详情 
    * @author shidandan
    * @date 2019年1月17日 下午8:20:44 
    * @Description: 获取模板碎片信息详情
    * @param id 模板碎片ID
    * @return 模板碎片详情信息
    * @throws Exception  参数说明 
    * @return ModelSplitDetailVO    返回类型 
    * @throws 
     */
    ModelSplitDetailVO getModelSplitDetail(String id) throws Exception;

    /**
     * 
    * @Title: 删除模板碎片 
    * @author shidandan
    * @date 2019年1月17日 下午8:21:34 
    * @Description: 删除模板碎片 
    * @param id 模板碎片ID
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return Boolean    返回类型 
    * @throws 
     */
   Boolean delModelSplit(String id) throws Exception;

}
