package cn.people.service;

import java.util.List;

import cn.people.commons.exceptions.ContentBussinessException;
import cn.people.controller.vo.ChannelPageVo;
import cn.people.controller.vo.ContentPageVo;
import cn.people.controller.vo.ModelSplitMapResponseVO;

/**
 * 
* @ClassName: IViewService 
* @Description: 提供模板预览服务
* @author zhangchengchun
* @date 2019年1月17日 下午1:30:08 
*  
 */
public interface IViewService
{

    /**
     * 
    * @Title: 查询栏目的模板原内容  
    * @author zhangchengchun
    * @date 2019年1月17日 下午1:38:26 
    * @Description: 查询栏目的模板原内容
    * @param logicId 栏目逻辑id
    * @return 栏目的模板原内容
    * @return String 栏目内容
    * @throws 
     */
    String getChannelTemplateContent(Integer logicId);

    /**
     * 
    * @Title: 查询栏目的模板中的标记集合
    * @author zhangchengchun
    * @date 2019年1月17日 下午1:39:35 
    * @Description: 查询栏目的模板中的标记集合
    * @param logicId 栏目逻辑id
    * @return  栏目的模板中的标记集合
    * @return List<ModelSplitMapResponseVO>    返回类型 
    * @throws 
     */
    List<ModelSplitMapResponseVO> getChannelTemplateSplits(Integer logicId);

    /**
     * 
    * @Title: 预览栏目的模板
    * @author zhangchengchun
    * @date 2019年1月17日 下午1:40:20 
    * @Description: 预览栏目的模板
    * @param logicId 栏目逻辑id
    * @param pageNo 当前页码，可以不传
    * @param info 调试信息，支持值：info|debug
    * @param vtype 预览类型，预览时不传，静态化时传publish
    * @param  参数说明 
    * @return String    返回类型 
    * @throws 
     */
    String getChannelPreview(Integer logicId, Integer pageNo, String info, String vtype);

    /**
     * 
    * @Title: 预览稿件的模板 
    * @author zhangchengchun
    * @date 2019年1月17日 下午1:43:03 
    * @Description: 预览稿件的模板 
    * @param contentId 稿件的id
    * @param pageNo 当前页码，可以不传
    * @param info 调试信息，支持值：info|debug
    * @param vtype 预览类型，预览时不传，静态化时传publish
    * @return 预览稿件的模板 
    * @throws 
     */
    String getNewsPreview(Integer contentLogicId, Integer pageNo, String info, String vtype)  throws ContentBussinessException;

    /**
     * 
    * @Title: 查询稿件分页信息
    * @author zhangchengchun
    * @date 2019年1月31日 上午9:41:20 
    * @Description: 查询稿件分页信息
    * @param contentLogicId 稿件逻辑id
    * @param @return  参数说明 
    * @return List<ContentPageVo>    返回类型 
    * @throws 
     */
    List<ContentPageVo> getContentPages(Integer contentLogicId)  throws ContentBussinessException;

    /**
     * 
    * @Title: 查询栏目分页信息 
    * @author zhangchengchun
    * @date 2019年1月31日 上午10:30:13 
    * @Description: 查询栏目分页信息 
    * @param logicId 栏目逻辑id
    * @param @return  参数说明 
    * @return List<ChannelPageVo>    返回类型 
    * @throws 
     */
    List<ChannelPageVo> getChannelPages(Integer logicId);

}
