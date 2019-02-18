package cn.people.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import cn.people.commons.exceptions.ContentBussinessException;
import cn.people.controller.vo.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import cn.people.entity.Content;


/**
 * <p>
 * 内容主体表 服务类
 * </p>
 *
 * @author shidandan
 * @since 2018-12-04
 */
public interface IContentService extends IService<Content> {

    /**
     * 
    * @Title: 添加内容 
    * @author shidandan
    * @date 2019年1月18日 下午7:20:34 
    * @Description: 添加内容 
    * @param param 内容详情
    * @return 添加内容结果
    * @throws Exception  参数说明 
    * @return ResultVO<ContentCreateResultVO>    返回类型 
    * @throws 
     */
    ContentCreateResultVO createContent(ContentCreateVO param) throws Exception;

    /**
     * 
    * @Title: 更新内容 
    * @author shidandan
    * @date 2019年1月18日 下午8:10:41 
    * @Description: 更新内容
    * @param id 内容id
    * @param content 内容
    * @return 更新内容结果
    * @throws Exception  参数说明 
    * @return ContentUpdateResultVO    返回类型 
    * @throws 
     */
    ContentUpdateResultVO updateContent(String id,ContentUpdateVO content) throws Exception;

    /**
     * 
    * @Title: 回收站列表查询 
    * @author shidandan
    * @date 2019年1月18日 下午7:21:58 
    * @Description: 回收站列表查询 
    * @param param 回收站内容列表查询参数
    * @return 回收站列表
    * @throws Exception  参数说明 
    * @return ResultVO<Page<ContentRecycledListResultVO>>    返回类型 
    * @throws 
     */
    Page<ContentRecycledListResultVO> getContentRecycledPaged(ContentRecycledListVO param);

    /**
     * 
    * @Title: 内容列表查询
    * @author gaoyongjiu
    * @date 2019年1月21日 上午9:47:01 
    * @Description: 内容列表查询
    * @param page 内容
    * @param param 内容查询参数
    * @return  内容列表 
    * @return List<Content>    返回类型 
    * @throws 
     */
    Page<ContentListVO> getContentPaged(Page<ContentListVO> page,ContentQueryVO param);


    /**
     * 
    * @Title: 删除内容
    * @author tianweisong
    * @date 2019年1月18日 下午7:32:40 
    * @Description: 删除内容
    * @param contentId 内容id
    * @return 删除内容结果
    * @return ResultVO<ContentDelResultVO>    返回类型 
     * @throws Exception 
    * @throws 
     */
    Boolean delContent(String contentId) throws Exception;

    /**
     * 
    * @Title: 从回收站还原内容
    * @author tianweisong
    * @date 2019年1月18日 下午7:36:00 
    * @Description: 从回收站还原内容
    * @param contentId 内容id
    * @return  还原内容结果
    * @return ResultVO<ContentRestoreResultVO>    返回类型 
    * @throws 
     */
    ContentRestoreResultVO restoreContent(String contentId);

    /**
     * 
    * @Title: 获取回收站内容详情
    * @author tianweisong
    * @date 2019年1月18日 下午8:13:46 
    * @Description: 获取回收站内容详情
    * @param contentId 回收站内容id
    * @return  内容详情
    * @return ContentDetailVO    返回类型 
    * @throws 
     */
    ContentDetailVO getContentForRecycled(String contentId);
    
    /**
     * 
    * @Title: 获取内容详情 
    * @author shidandan
    * @date 2019年1月18日 下午8:14:41 
    * @Description: 获取内容详情 
    * @param id 内容id
    * @return  内容详情 
    * @return ContentDetailVO    返回类型 
    * @throws 
     */
    ContentDetailVO getContentById(String id);
    
    /**
     * 
    * @Title: 文章审核 
    * @author shidandan
    * @date 2019年1月18日 下午8:15:26 
    * @Description: 文章审核
    * @param id 文章id
    * @param auditVO 文章审核
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return Boolean    返回类型 
    * @throws 
     */
    Boolean auditContent(String id,ContentAuditVO auditVO,Integer dstatus) throws Exception;
    
    /**
     * 
    * @Title: 返回当前稿件的前一个已签发的稿件
    * @author zhangchengchun
    * @date 2019年1月18日 下午8:16:17 
    * @Description: 返回当前稿件的前一个已签发的稿件
    * @param node_id 所属栏目的逻辑id
    * @param inputDate 当前稿件创建日期
    * @param contentId 当前稿件id
    * @return  内容详情
    * @return ContentDetailVO    返回类型 
    * @throws 
     */
    ContentDetailVO getPreContent(Integer node_id, String inputDate ,String contentId);
    
    /**
     * 
    * @Title: 返回当前稿件的后一个已签发的稿件
    * @author zhangchengchun
    * @date 2019年1月14日 下午3:09:08 
    * @Description: 返回当前稿件的后一个已签发的稿件
    * @param  node_id 所属栏目的逻辑id
    * @param inputDate 当前稿件创建日期
    * @param contentId 当前稿件id
    * @return  内容详情
    * @return ContentDetailVO    返回类型 
    * @throws 
     */
    ContentDetailVO getNextContent(Integer node_id, String inputDate ,String contentId);
    
    /**
     * 
    * @Title:  查询自动新闻数据
    * @author zhangchengchun
    * @date 2019年1月18日 下午8:18:24 
    * @Description:  查询自动新闻数据
    * @param searchParam 查询自动新闻查询条件
    * @return  自动新闻数据
    * @return List<ContentDetailVO>    返回类型 
    * @throws 
     */
    List<ContentDetailVO> listAutoNews(ContentViewRequestVO searchParam);

    /**
     * 
    * @Title:  根据查询条件组装ContentDetailVO数据
    * @author zhangchengchun
    * @date 2019年1月18日 下午8:19:12 
    * @Description:  根据查询条件组装ContentDetailVO数据
    * @param searchParam 查询自动新闻查询条件
    * @return  内容详情
    * @return List<ContentDetailVO>    返回类型 
    * @throws 
     */
    List<ContentDetailVO> listContentDetails(ContentDetailRequestVO searchParam);
    

    /**
     * 
    * @Title: 更新稿件发布状态
    * @author zhangchengchun
    * @date 2019年1月29日 下午6:32:16 
    * @Description: 更新稿件发布状态 
    * @param logicId 稿件逻辑id
    * @param status 稿件状态
    * @param @return  参数说明 
    * @return Boolean    返回类型 
    * @throws 
     */
    Boolean updateReleaseStatus(Integer logicId, Integer status) throws ContentBussinessException;

    /**
     * 
    * @Title: 根据稿件逻辑id查询稿件信息 
    * @author zhangchengchun
    * @date 2019年1月31日 上午9:46:12 
    * @Description: 根据稿件逻辑id查询稿件信息 
    * @param contentLogicId 稿件逻辑id
    * @param @return  参数说明 
    * @return ContentDetailVO    返回类型 
    * @throws 
     */
    ContentDetailVO getContentByLogicId(Integer contentLogicId);

    
}
