package cn.people.service;

import cn.people.controller.vo.SplitContentAddVO;
import cn.people.controller.vo.SplitContentVO;
import cn.people.entity.SplitContent;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 
* @ClassName: ISplitContentService 
* @Description: 模板碎片内容管理接口
* @author shidandan
* @date 2019年1月17日 下午4:41:41 
*  
 */
public interface ISplitContentService extends IService<SplitContent> {
    /**
     * 
    * @Title: 更新模板碎片内容 
    * @author shidandan
    * @date 2019年1月17日 下午4:40:51 
    * @Description: 更新模板碎片内容
    * @param id 模板碎片内容ID
    * @param splitContentVO 模板碎片内容的属性
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return Boolean    返回类型 
    * @throws 
     */
    Boolean updateSplitContents(String id,SplitContentVO splitContentVO) throws Exception;
    
    /**
     * 
    * @Title: 添加模板碎片内容 
    * @author shidandan
    * @date 2019年1月17日 下午4:39:50 
    * @Description: 添加模板碎片内容
    * @param modelSpiltId 模板碎片ID
    * @param splitContentVO 模板碎片内容属性
    * @param channelId 栏目ID
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return Boolean    返回类型 
    * @throws 
     */
    Boolean addSplitContent(SplitContentAddVO splitContentVO) throws Exception;
    /**
     * 
    * @Title: 更新模板碎片内容的状态 
    * @author shidandan
    * @date 2019年1月17日 下午4:37:53 
    * @Description: 更新模板碎片内容的状态 
    * @param id 模板碎片内容的ID
    * @param publishEnable 0显示1隐藏
    * @return
    * @throws Exception  参数说明 
    * @return Boolean    返回类型 
    * @throws 
     */
    Boolean updateSplitContentStatus(String id,Integer publishEnable) throws Exception;
    /**
     * 
    * @Title: 查询模板碎片内容列表 
    * @author shidandan
    * @date 2019年1月17日 下午4:39:11 
    * @Description: 查询模板碎片内容列表 
    * @param modelSplitId 模板碎片ID
    * @param channelId 栏目ID
    * @return  参数说明 
    * @return List<SplitContentVO>    返回类型 
    * @throws 
     */
    List<SplitContentVO> querySplitContentList(String modelSplitId,String channelId);

}
