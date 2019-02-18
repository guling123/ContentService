package cn.people.service;

import cn.people.commons.exceptions.ContentBussinessException;
import cn.people.controller.vo.ChannelCreateResultVO;
import cn.people.controller.vo.ChannelCreateVO;
import cn.people.controller.vo.ChannelDetailVO;
import cn.people.controller.vo.ChannelListRequestVO;
import cn.people.controller.vo.ChannelListTreeVO;
import cn.people.controller.vo.ChannelListVO;
import cn.people.controller.vo.PublishChanneRequest;
import cn.people.controller.vo.PublishChannelResponseVO;
import cn.people.entity.Channel;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 
* @ClassName: IChannelService 
* @Description: 栏目接口
* @author shidandan
* @date 2019年1月17日 下午4:51:06 
*  
 */
public interface IChannelService extends IService<Channel> {

    /**
     * 
    * @Title: 新增子栏目 
    * @author shidandan
    * @date 2019年1月17日 下午4:47:16 
    * @Description: 新增子栏目 
    * @param channelCreateVO 栏目信息
    * @return
    * @throws Exception  参数说明 
    * @return ChannelCreateResultVO    返回类型 
    * @throws 
     */
    ChannelCreateResultVO createChannel(ChannelCreateVO channelCreateVO) throws Exception;

    /**
     * 
    * @Title: 获取栏目详情 
    * @author shidandan
    * @date 2019年1月17日 下午4:48:02 
    * @Description: 获取栏目详情 
    * @param id 栏目ID
    * @return 栏目详情
    * @throws Exception  参数说明 
    * @return ChannelDetailVO    返回类型 
    * @throws 
     */
    ChannelDetailVO getChannelById(String id) throws Exception;

    /**
     * 
    * @Title: 删除子栏目 
    * @author shidandan
    * @date 2019年1月17日 下午4:48:34 
    * @Description: 删除子栏目 
    * @param id 栏目ID
    * @return 成功返回true
    * @throws Exception  参数说明 
    * @return Boolean    返回类型 
    * @throws 
     */
    Boolean delChannel(String id ) throws Exception;

    /**
     * 
    * @Title: 获取栏目树 
    * @author shidandan
    * @date 2019年1月17日 下午4:49:07 
    * @Description: 获取栏目树  
    * @param orgid 租户ID
    * @param siteid 站点ID
    * @return 返回栏木树
    * @throws Exception  参数说明 
    * @return ChannelListTreeVO    返回类型 
    * @throws 
     */
    ChannelListVO getChannel(String orgid, String siteid,ChannelListRequestVO requestVO) throws Exception;
    
    /**
     * 
    * @Title: 根据逻辑ID查询栏目信息 
    * @author shidandan
    * @date 2019年1月17日 下午4:49:52 
    * @Description: 根据逻辑ID查询栏目信息 
    * @param logicId 逻辑ID
    * @return  参数说明 
    * @return Channel    返回类型 
    * @throws 
     */
    Channel getByLogicId(Integer logicId);

    /**
     * 
    * @Title: 根据栏目逻辑ID集合查询栏目信息 
    * @author shidandan
    * @date 2019年1月17日 下午4:50:21 
    * @Description: 根据栏目逻辑ID集合查询栏目信息
    * @param channelLogicIds 栏目逻辑ID集合
    * @return  参数说明 
    * @return List<Channel>    返回类型 
    * @throws 
     */
    List<Channel> listByLogicIds(String[] channelLogicIds);
    
    /**
     * 
    * @Title: 查询栏目发布下线资源树接口 
    * @author zhangchengchun
    * @date 2019年1月23日 下午1:11:40 
    * @Description:  查询栏目发布下线资源树接口 
    * @param publishChanneRequestVO 
    * @return List<PublishChannelResponseVO>    返回类型 
    * @throws 
     */
    List<PublishChannelResponseVO> getChannelPublishList(PublishChanneRequest publishChanneRequestVO);

    /**
     * 
    * @Title: 更新栏目发布状态
    * @author zhangchengchun
    * @date 2019年1月29日 下午5:37:43 
    * @Description:  更新栏目发布状态
    * @param @param logincId
    * @param @param releaseStatus
    * @param @return  参数说明 
    * @return Boolean    返回类型 
    * @throws 
     */
    Boolean updateReleaseStatus(Integer logicId, Integer releaseStatus)  throws ContentBussinessException;
}
