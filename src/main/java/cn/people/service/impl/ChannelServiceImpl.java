package cn.people.service.impl;

import cn.people.commons.constants.CodeConstants;
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
import cn.people.entity.Content;
import cn.people.entity.ContentChannel;
import cn.people.entity.Model;
import cn.people.entity.Site;
import cn.people.mapper.*;
import cn.people.service.IChannelService;
import cn.people.service.IIdGeneraterService;
import cn.people.service.view.obj.ChannelObj;
import java_cup.production;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * 
* @ClassName: ChannelServiceImpl 
* @Description: 栏目接口实现类
* @author shidandan
* @date 2019年1月17日 下午4:51:27 
*  
 */
@Service 
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel>implements IChannelService
{

    @Autowired 
    private SiteMapper siteMapper;

    @Autowired 
    private ContentChannelMapper contentChannelMapper;
    
    @Autowired
    private ChannelMapper channelMapper;
    
    @Autowired
    private IIdGeneraterService iIdGeneraterService;

    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 
    * Title: 创建子栏目
    * @author shidandan
    * @date 2019年1月17日 下午4:22:50 
    * @Description: 创建子栏目
    * @param channelCreateVO 创建子栏目参数
    * @return
    * @throws Exception 
    * @see cn.people.service.IChannelService#createChannel(cn.people.controller.vo.ChannelCreateVO)
     */
    @Override 
    public ChannelCreateResultVO createChannel(ChannelCreateVO channelCreateVO)
        throws Exception
    {
        // 效验站点是否存在
        Site site = siteMapper.selectById(channelCreateVO.getSiteid());

        if (null == site)
        {
            throw new ContentBussinessException(CodeConstants.SITE_NOT_EXIST, "站点不存在");
        }
        Channel entity=new Channel();
        entity.setChannelName(channelCreateVO.getChannelName());
        List<Channel> channelList=this.list(new QueryWrapper<Channel>(entity));
        
        if(!CollectionUtils.isEmpty(channelList)) {
            throw new ContentBussinessException(CodeConstants.CHANNEL_EXIST, "栏目名称已被占用");
        }
        // 创建内容
        Channel channel = new Channel();
        BeanUtils.copyProperties(channelCreateVO, channel);
        channel.setLogicId(iIdGeneraterService.getNextValue(Channel.class.getAnnotation(TableName.class).value()));

        if(!channelCreateVO.getParentId().equals("0")) {
            Channel parentChannel = this.getById(channelCreateVO.getParentId());
            if (parentChannel == null)
            {
                throw new ContentBussinessException(CodeConstants.CONTENT_NOT_EXIST, "不存在父栏目");
            }
            if(parentChannel.getParentString().equals("0")) {
                channel.setDeep(2);
                channel.setParentString(parentChannel.getLogicId()+",");
            }else {
                String[] parent=parentChannel.getParentString().split(",");
                channel.setDeep(parent.length+2);
                channel.setParentString(parentChannel.getParentString()+parentChannel.getLogicId()+",");
            } 
        }else {
            channel.setDeep(1);
            channel.setParentString("0");
            channel.setDisplay(1);
        }
        
        
        channel.setCreateTime(new Date());
        channel.setDstatus(1);
        channel.setStatusRelease(1);
        channel.setImageUrl(channelCreateVO.getImageUrl());
        channel.setDomain(site.getDomain());
        boolean isSuccess = this.save(channel);

        ChannelCreateResultVO result = new ChannelCreateResultVO();
        result.setSuccess(isSuccess);
        result.setId(channel.getId());
        result.setSiteid(channelCreateVO.getSiteid());
        result.setParentId(channel.getParentId());
        return result;
    }

    /**
     * 
    * Title: 获取栏目详情
    * @author shidandan
    * @date 2019年1月17日 下午4:27:27 
    * @Description: 通过ID获取栏目详情
    * @param id 栏目ID
    * @return
    * @throws Exception 
    * @see cn.people.service.IChannelService#getChannelById(java.lang.String)
     */
    @Override 
    public ChannelDetailVO getChannelById(String id)
        throws Exception
    {
        Channel channel = this.getById(id);

        if (null != channel)
        {
            ChannelDetailVO result = new ChannelDetailVO();
            BeanUtils.copyProperties(channel, result);
            if (!StringUtils.isEmpty(channel.getImagesModelId())){
                Model getImageName = modelMapper.selectById(channel.getImagesModelId());
                if(getImageName != null){
                    result.setImagesModelName(getImageName.getModelName());
                }
            }
            if (!StringUtils.isEmpty(channel.getChannelModelId())){
                Model getchannelName = modelMapper.selectById(channel.getChannelModelId());
                if(getchannelName != null){
                    result.setChannelModelName(getchannelName.getModelName());
                }
            }
            if (!StringUtils.isEmpty(channel.getContentModelId())){
                Model getContentName = modelMapper.selectById(channel.getContentModelId());
                if(getContentName != null){
                    result.setContentModelName(getContentName.getModelName());
                }
            }
            ChannelObj channelObj=new ChannelObj(channel.getLogicId());
            result.setChannelUrl(channelObj.getChannelUrl().getUrlPath());
            return result;
        }
        return null;
    }

    /**
     * 
    * Title: 删除栏目
    * @author shidandan
    * @date 2019年1月17日 下午4:25:01 
    * @Description: 删除栏目
    * @param id 栏目ID
    * @return 成功返回true
    * @throws Exception 
    * @see cn.people.service.IChannelService#delChannel(java.lang.String)
     */
    @Override 
    public Boolean delChannel(String id)
        throws Exception
    {
        Channel channel = this.getById(id);

        if (null == channel)
        {
            throw new ContentBussinessException(CodeConstants.CHANNEL_NOT_EXIST, "栏目不存在");
        }

        QueryWrapper<Channel> wrapper=new QueryWrapper<Channel>();
        wrapper.like("parent_string", ","+id+",");
        List<Channel> list =this.list(wrapper);
        if(!CollectionUtils.isEmpty(list)) {
            list.add(channel);
        }else {
            list =new ArrayList<Channel>();
            list.add(channel);
            
        }
        Set<String> childIds=list.stream().map(childId ->{return childId.getId();}).collect(Collectors.toSet());
        QueryWrapper<ContentChannel> queryWrapper = new QueryWrapper<ContentChannel>();
        queryWrapper.in("channelid", childIds);
        List<ContentChannel> contentChannelList = new ArrayList<>();
        contentChannelList = contentChannelMapper.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(contentChannelList)) {
            throw new ContentBussinessException(CodeConstants.CHANNEL_DEL_ERROR_EXIST_CONTENT, "栏目下有内容不能删除");
        }
        list.forEach(c -> {
            c.setDstatus(2);
        });
        return this.updateBatchById(list);

    }

    /**
     * 
    * Title: 获取栏目详情
    * @author shidandan
    * @date 2019年1月17日 下午4:13:48 
    * @Description: 获取栏目详情
    * @param orgid 租户ID
    * @param siteid 站点ID
    * @return　栏目树
    * @throws Exception 
    * @see cn.people.service.IChannelService#getChannel(java.lang.String, java.lang.String)
     */
    @Override 
    public ChannelListVO getChannel(String orgid, String siteid,ChannelListRequestVO requestVO)
        throws Exception
    {
        QueryWrapper<Channel> channel = new QueryWrapper<Channel>();
        channel.eq("siteid", siteid);
        channel.eq("dstatus", 1);
        if(!CollectionUtils.isEmpty(requestVO.getChannelIds())) {
            channel.in("id", requestVO.getChannelIds());
        }
        List<Channel> channelList = this.list(channel);

        if (!CollectionUtils.isEmpty(channelList))
        {
            ChannelListVO result = new ChannelListVO();
            List<ChannelListTreeVO> channelListChild = new ArrayList<>();
            channelList.forEach(cc -> {
                if(cc.getParentId().equals("0")) {
                    ChannelListTreeVO resultChild = new ChannelListTreeVO();
                    BeanUtils.copyProperties(cc, resultChild);
                    resultChild.setId(cc.getId());
                    resultChild.setChannelName(cc.getChannelName());
                    resultChild.setParentId(cc.getParentId());
                    resultChild.setDisplay(cc.getDisplay());
                    resultChild.setLogicId(cc.getLogicId());
                    channelListChild.add(resultChild);  
                }
            });
            result.setChildren(channelListChild);
            getChannelTreeVO(channelList, result.getChildren());
            return result;
        }
        return null;
    }

    /**
     * 
    * @Title: 返回栏目树 
    * @author shidandan
    * @date 2019年1月17日 下午4:25:34 
    * @Description: 返回栏目树 
    * @param channelList
    * @param treeList
    * @return  参数说明 
    * @return ChannelListTreeVO    返回类型 
    * @throws 
     */
    private void getChannelTreeVO(List<Channel> channelList,
                                               List<ChannelListTreeVO> treeList)
    {
        if (CollectionUtils.isEmpty(treeList))
        {
            return;
        }

        //将子类的list放入treemap channelId为key  list为值
        Map<String, List<ChannelListTreeVO>> treeMap = new HashMap<String, List<ChannelListTreeVO>>(
            16);
        treeList.forEach(tree -> {
            List<ChannelListTreeVO> list = new ArrayList<ChannelListTreeVO>();
            treeMap.put(tree.getId(), list);
        });

        channelList.forEach(channel -> {
            if (treeMap.containsKey(channel.getParentId()))
            {
                ChannelListTreeVO vo = new ChannelListTreeVO();
                BeanUtils.copyProperties(channel, vo);
                vo.setId(channel.getId());
                vo.setChannelName(channel.getChannelName());
                vo.setParentId(channel.getParentId());
                vo.setDisplay(channel.getDisplay());
                vo.setLogicId(channel.getLogicId());
                treeMap.get(channel.getParentId()).add(vo);
            }
        });

        treeList.forEach(tree -> {
            if (treeMap.containsKey(tree.getId()))
            {
                tree.setChildren(treeMap.get(tree.getId()));
            }
        });

        treeList.forEach(tree -> {
            getChannelTreeVO(channelList, tree.getChildren());
        });

    }
    /**
     * 
    * Title: 获取栏目详情
    * @author shidandan
    * @date 2019年1月17日 下午4:25:55 
    * @Description: 通过逻辑ID获取栏目详情
    * @param logicId 逻辑ID
    * @return 
    * @see cn.people.service.IChannelService#getByLogicId(java.lang.Integer)
     */
    @Override
    public Channel getByLogicId(Integer logicId)
    {
        QueryWrapper<Channel> queryWrapper = new QueryWrapper<Channel>();
        queryWrapper.eq("logic_id", logicId);
        return getOne(queryWrapper);
    }

    /**
     * 
    * Title: 批量获取栏目详情
    * @author shidandan
    * @date 2019年1月17日 下午4:26:34 
    * @Description: 通过一批逻辑ID获取多个栏目得详情
    * @param channelLogicIds
    * @return 
    * @see cn.people.service.IChannelService#listByLogicIds(java.lang.String[])
     */
    @Override
    public List<Channel> listByLogicIds(String[] channelLogicIds)
    {
        QueryWrapper<Channel> queryWrapper = new QueryWrapper<Channel>();
        queryWrapper.in("logic_id", channelLogicIds);
        return list(queryWrapper);
    }
    
 
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
    @Override
    public List<PublishChannelResponseVO> getChannelPublishList(PublishChanneRequest publishChanneRequestVO)
    {
        List<PublishChannelResponseVO> result = new ArrayList<PublishChannelResponseVO>();
        
        //记录所有需要返回的栏目
        List<Integer> logicIds = new ArrayList<Integer>();
        
        PublishChannelResponseVO mainChannel = getChanneltree(publishChanneRequestVO);
        
        //没有需要处理的任务
        if(mainChannel==null) return result;
        
        //把栏目树种的逻辑id写入logicIds
        buildLogicIds(mainChannel,logicIds);
        
        //添加当前栏目
        result.add(mainChannel);
        
        //添加相关栏目，以当前栏目作为主控栏目的栏目
        List<PublishChannelResponseVO> tempSlaveChannelLogicIds = getSlaveChannels(logicIds,publishChanneRequestVO.getIsSubChannels());
        while(tempSlaveChannelLogicIds.size()>0) {
            List<Integer> slaveLogicIds= new ArrayList<Integer>();          
            for(PublishChannelResponseVO slaveChannel:tempSlaveChannelLogicIds) {
                //把channel栏目树中的逻辑id写入slaveLogicIds,如果已经存在于logicIds，则不写入，并且从栏木树中移除
                buildSlaveLogicIds(slaveChannel,slaveLogicIds,logicIds);
                //从栏目写入logicIds
                for(Integer logicId:slaveLogicIds) {
                    logicIds.add(logicId);
                }
                //从栏目加入结果集
                if(null!=slaveChannel) {
                    result.add(slaveChannel);
                }
            }
            tempSlaveChannelLogicIds = getSlaveChannels(slaveLogicIds,publishChanneRequestVO.getIsSubChannels());            
        }
        
        //处理是否需要包含稿件详情
        if(publishChanneRequestVO.getIsContents()) {
            buildContents(result,publishChanneRequestVO.getType());
        }
        
        return result;
    }

    
    /**
     * 
    * @Title: 添加稿件信息
    * @author zhangchengchun
    * @date 2019年1月24日 上午11:57:24 
    * @Description: 查询已审核的稿件，添加到相应的栏目节点下方
    * @param result  参数说明 
    * @return void    返回类型 
    * @throws 
     */
    private void buildContents(List<PublishChannelResponseVO> result,Integer Type)
    {
        result.forEach(publishChannel->{
            
            List<Content> contents = contentMapper.listChannelContents(publishChannel.getId(),Type==1?Content.DSTATUS_SECOND_PASS:Content.DSTATUS_PUBLISHED);
            
            contents.forEach(content->{
                publishChannel.getContentLogicIds().add(content.getLogicId());
            });
        
            if(publishChannel.getSubChannels().size()>0) {
                buildContents(publishChannel.getSubChannels(),Type);
            }
        });
    }

    /**
     * 
    * @Title: 把mainChannel中的所有栏目逻辑id写入logicIds中
    * @author zhangchengchun
    * @date 2019年1月23日 下午10:11:18 
    * @Description: 把mainChannel中的所有栏目逻辑id写入tempLogicIds中,
    * 如果逻辑id存在于logicIds中，则把这个栏目删除，否则再写入到tempLogicIds
    * @param @param mainChannel
    * @param @param tempLogicIds
    * @param @param logicIds  参数说明 
    * @return void    返回类型 
    * @throws 
     */
    private void buildSlaveLogicIds(PublishChannelResponseVO channelVO,
                                    List<Integer> tempLogicIds, List<Integer> logicIds)
    {
        if(logicIds.contains(channelVO.getLogicId())) {
            channelVO=null;
            return;
        }else {
            tempLogicIds.add(channelVO.getLogicId());
        }
        
        if(channelVO.getSubChannels().size()>0) {
            List<PublishChannelResponseVO> removeList = new ArrayList<PublishChannelResponseVO>();
            
            channelVO.getSubChannels().forEach(subChannel->{
                buildSlaveLogicIds(subChannel,tempLogicIds,logicIds);
                if(null==subChannel) {
                    removeList.add(subChannel);
                }
            });
            
            for(PublishChannelResponseVO removeChannel:removeList){
                channelVO.getSubChannels().remove(removeChannel);
            }
        }
    }

    /**
     * 
    * @Title: 把mainChannel中的所有栏目逻辑id写入logicIds中
    * @author zhangchengchun
    * @date 2019年1月23日 下午10:10:20 
    * @Description: 把mainChannel中的所有栏目逻辑id写入logicIds中
    * @param @param mainChannel
    * @param @param logicIds  参数说明 
    * @return void    返回类型 
    * @throws 
     */
    private void buildLogicIds(PublishChannelResponseVO mainChannel, List<Integer> logicIds)
    {
        logicIds.add(mainChannel.getLogicId());
        
        mainChannel.getSubChannels().forEach(sub->{
            buildLogicIds(sub,logicIds);
        });
    }

    /**
     * 
    * @Title: 查询使用了入参逻辑id作为主控栏目的栏目逻辑id 
    * @author zhangchengchun
    * @date 2019年1月23日 下午9:05:55 
    * @Description: 查询使用了入参逻辑id作为主控栏目的栏目逻辑id，如果入参中要求包含子栏目，则需要子栏目
    * @param logicIds 
    * @param @return  参数说明 
    * @return List<Integer>    返回类型 
    * @throws 
     */
    private List<PublishChannelResponseVO> getSlaveChannels(List<Integer> logicIds,Boolean containsSubChannel)
    {
        // TODO 暂不支持相关栏目
        return new ArrayList<PublishChannelResponseVO>();
    }

    /**
     * 
    * @Title: 查询栏目的栏目树
    * @author zhangchengchun
    * @date 2019年1月23日 下午8:42:17 
    * @Description: 查询栏目的栏目树
    * @param publishChanneRequestVO
    * @param  参数说明 
    * @return PublishChannelResponseVO    返回类型 
    * @throws 
     */
    private PublishChannelResponseVO getChanneltree(PublishChanneRequest publishChanneRequestVO)
    {
        PublishChannelResponseVO result = new PublishChannelResponseVO();
        result.setLogicId(publishChanneRequestVO.getLogicId());        
            
        Channel currentChannel = getByLogicId(publishChanneRequestVO.getLogicId());
        result.setId(currentChannel.getId());    
        
        if((publishChanneRequestVO.getType()==1&&
            (Channel.STATUSRELEASE_RELEASED == currentChannel.getStatusRelease()||
              Channel.STATUSRELEASE_RELEASEING == currentChannel.getStatusRelease()))
            ||(publishChanneRequestVO.getType()==0&&Channel.STATUSRELEASE_UNRELEASE == currentChannel.getStatusRelease())) {
            return null;
        }
        
        if(publishChanneRequestVO.getIsSubChannels()) {            
            List<PublishChannelResponseVO> subChannels = buildSubChannel(currentChannel,publishChanneRequestVO);
            result.setSubChannels(subChannels );
        }
        
        return result;
    }

    //创建栏目资源树
    private List<PublishChannelResponseVO> buildSubChannel(Channel channel,PublishChanneRequest publishChanneRequestVO)
    {
        List<PublishChannelResponseVO> result = new ArrayList<PublishChannelResponseVO>();
        
        QueryWrapper<Channel> queryWrapper = new QueryWrapper<Channel>();
        queryWrapper.eq("dstatus", 2);
        queryWrapper.eq("status_release", 1==publishChanneRequestVO.getType()?Channel.STATUSRELEASE_UNRELEASE:Channel.STATUSRELEASE_RELEASED);
        queryWrapper.eq("parent_id", channel.getId());
        
        List<Channel> channelList = list(queryWrapper);
        
        channelList.forEach(subChannel->{
            //只返回有权限访问的子栏目
            if(publishChanneRequestVO.getAuthorizedChannelIds().contains(subChannel.getId()))
            {
                PublishChannelResponseVO sub = new PublishChannelResponseVO();
                sub.setId(subChannel.getId());
                sub.setLogicId(subChannel.getLogicId());
                sub.setSubChannels(buildSubChannel(subChannel,publishChanneRequestVO));
                result.add(sub);
            }
        }); 
        
        return result;
    }

    @Override
    public Boolean updateReleaseStatus(Integer logicId, Integer releaseStatus) throws ContentBussinessException
    {
        Channel channel = getByLogicId(logicId);
        if(null == channel) {
            throw new ContentBussinessException(CodeConstants.CHANNEL_NOT_EXIST, "栏目信息不存在");
        }
        channel.setStatusRelease(releaseStatus);
        return updateById(channel);
    }

}
