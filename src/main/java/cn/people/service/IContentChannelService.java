package cn.people.service;

import cn.people.entity.ContentChannel;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 内容栏目表 服务类
 * </p>
 *
 * @author shidandan
 * @since 2018-12-19
 */
public interface IContentChannelService extends IService<ContentChannel> {

    /**
     * 根据稿件id查询稿件栏目关系数据
    * @Title: getByContentId 
    * @author zhangchengchun
    * @date 2019年1月14日 上午10:50:34 
    * @Description: 根据稿件id查询稿件栏目关系数据
    * @param @param contentId
    * @param @return  参数说明 
    * @return List<ContentChannel>    返回类型 
    * @throws 
     */
    List<ContentChannel> getByContentId(String contentId);

}
