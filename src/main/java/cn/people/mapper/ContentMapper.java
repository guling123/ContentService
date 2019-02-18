package cn.people.mapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.people.controller.vo.*;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.people.entity.Content;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 内容主体表 Mapper 接口
 * </p>
 *
 * @author shidandan
 * @since 2018-12-04
 */
@Mapper
public interface ContentMapper extends BaseMapper<Content> {
    


    /**
     * @author guling
     * @Title   findByNumberHQL
     * @Date    2019/1/16 9:09
     * @param   node_id, inputDate, contentId
     * @return  java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @throws
     * @Description 通过条件查询content
    */
    ArrayList<ContentResultVO> findByNumberHQL(@Param("node_id") Integer node_id, @Param("inputDate") String inputDate);
    /**
     * @author guling
     * @Title   findByThreeParam
     * @Date    2019/1/16 13:29
     * @param   node_id, inputDate, contentId
     * @return  java.util.ArrayList<cn.people.controller.vo.ContentResultVO>
     * @throws
     * @Description 通过三个条件查询content
    */
    ArrayList<ContentResultVO> findByThreeParam(@Param("node_id") Integer node_id, @Param("inputDate") String inputDate, @Param("contentId") String contentId);



    /**
     * @author guling
     * @Title   getlistAutoNews
     * @Date    2019/1/16 13:42
     * @param   searchParam
     * @return  java.util.ArrayList<cn.people.controller.vo.ContentResultVO>
     * @throws
     * @Description //TODO
    */
    ArrayList<ContentResultVO> getlistAutoNews(@Param("searchParam") ContentViewRequestVO searchParam, @Param("contentViewSearchRequestVO")ContentViewSearchRequestVO contentViewSearchRequestVO);

    /**
     * @author guling
     * @Title   listContentDetails
     * @Date    2019/1/16 18:15
     * @param   searchParam, contentViewSearchRequestVO]
     * @return  java.util.ArrayList<cn.people.controller.vo.ContentResultVO>
     * @throws
     * @Description
    */
    ArrayList<ContentResultVO> listContentDetails(ContentDetailRequestVO searchParam, ContentViewSearchRequestVO contentViewSearchRequestVO);

    /**
     * @author guling
     * @Title   getContentPaged
     * @Date    2019/1/24 13:51
     * @param   [page, param]  //TODO (描述参数)
     * @return  java.util.List<cn.people.entity.Content>
     * @throws
     * @Description //TODO(这里用一句话描述这个方法的作用)
    */
    List<ContentListVO> getContentPaged(Page<ContentListVO> page, ContentPageQueryVO param);


    /**
     *
    * @Title: 查询入参栏目下的指定状态的稿件
    * @author zhangchengchun
    * @date 2019年1月24日 下午2:36:37 
    * @Description: 查询入参栏目下的待发布的稿件
    * @param channelId 栏目id
    * @return List<Content>    返回类型 
    * @throws 
     */
    List<Content> listChannelContents(@Param("channelId") String channelId ,@Param("status") Integer ...status);
}