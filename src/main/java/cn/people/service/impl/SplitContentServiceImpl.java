package cn.people.service.impl;

import cn.people.commons.constants.CodeConstants;
import cn.people.commons.exceptions.ContentBussinessException;
import cn.people.controller.vo.SplitContentAddVO;
import cn.people.controller.vo.SplitContentVO;
import cn.people.entity.Channel;
import cn.people.entity.Content;
import cn.people.entity.ModelSplit;
import cn.people.entity.SplitContent;
import cn.people.mapper.ChannelMapper;
import cn.people.mapper.ContentMapper;
import cn.people.mapper.ModelSplitMapper;
import cn.people.mapper.SplitContentMapper;
import cn.people.service.ISplitContentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
* @ClassName: SplitContentServiceImpl 
* @Description: 模板碎片内容管理接口实现类 
* @author shidandan
* @date 2019年1月17日 下午4:42:49 
*  
 */
@Service
public class SplitContentServiceImpl extends ServiceImpl<SplitContentMapper, SplitContent> implements ISplitContentService {

    @Autowired
    private ModelSplitMapper modelSplitMapper;

    @Autowired
    private ContentMapper contentMapper;
    
    @Autowired
    private ChannelMapper channelMapper;
    
    /**
     * 
    * Title: 更新模板碎片内容
    * @author shidandan
    * @date 2019年1月17日 下午4:43:15 
    * @Description: 更新模板碎片内容
    * @param id 模板碎片内容ID
    * @param splitContentVO 模板碎片内容的属性
    * @return 成功返回true
    * @throws Exception 
    * @see cn.people.service.ISplitContentService#updateSplitContents(java.lang.String, cn.people.controller.vo.SplitContentVO)
     */
    @Override
    public Boolean updateSplitContents(String id, SplitContentVO splitContentVO) throws Exception
    {
        if(StringUtils.isEmpty(id)||null==splitContentVO) {
            throw new ContentBussinessException(CodeConstants.RESULT_ERR_PARAM, "参数有误");
        }

        SplitContent splitContent= this.getById(id);

        if(null==splitContent) {
            throw new ContentBussinessException(CodeConstants.SPLIT_CONTENT_NOT_EXIST, "碎片内容不存在");
        }
        BeanUtils.copyProperties(splitContentVO, splitContent);
        splitContent.setId(id);
        return this.updateById(splitContent);
    }

    /**
     * 
    * Title: 查询碎片对应的内容列表
    * @author shidandan
    * @date 2019年1月17日 下午4:36:34 
    * @Description: 查询碎片对应的内容列表
    * @param modelSplitId 模板碎片ID
    * @param channelId 栏目ID
    * @return 模板碎片内容集合
    * @see cn.people.service.ISplitContentService#querySplitContentList(java.lang.String, java.lang.String)
     */
    @Override
    public List<SplitContentVO> querySplitContentList(String modelSplitLogicId,String channelId)
    {
        //查询某碎片下得内容
        SplitContent entity=new SplitContent();
        entity.setModelSplitLogicId(Integer.valueOf(modelSplitLogicId));
        entity.setChannelId(channelId);
        List<SplitContent> spiltContentList=this.list(new QueryWrapper<SplitContent>(entity));


        return querySplitContentListResult(spiltContentList);
    }


    /**
     * 
    * Title: 更新模板碎片内容的状态
    * @author shidandan
    * @date 2019年1月17日 下午4:44:51 
    * @Description: 更新模板碎片内容的状态
    * @param id 模板碎片内容ID
    * @param publishEnable 0显示1隐藏
    * @return 成功返回true
    * @throws Exception 
    * @see cn.people.service.ISplitContentService#updateSplitContentStatus(java.lang.String, java.lang.Integer)
     */
    @Override
    public Boolean updateSplitContentStatus(String id, Integer publishEnable)
        throws Exception
    {
        SplitContent splitContent=this.getById(id);
        if(null==splitContent) {
            throw new ContentBussinessException(CodeConstants.SPLIT_CONTENT_NOT_EXIST, "碎片内容不存在");
        }
        splitContent.setPublishEnable(publishEnable);

        return this.updateById(splitContent);
    }

    /**
     * 
    * Title: 添加模板碎片内容
    * @author shidandan
    * @date 2019年1月17日 下午4:45:37 
    * @Description: 添加模板碎片内容
    * @param modelSpiltId 模板碎片ID
    * @param splitContentVO 模板碎片内容
    * @param channelId 栏目ID
    * @return 成功返回true
    * @throws Exception 
    * @see cn.people.service.ISplitContentService#addSplitContent(java.lang.String, cn.people.controller.vo.SplitContentAddVO, java.lang.String)
     */
    @Override
    public Boolean addSplitContent(SplitContentAddVO splitContentVO)
        throws Exception
    {
        ModelSplit modelSplit=modelSplitMapper.selectById(splitContentVO.getModelSpiltId());

        if(null==modelSplit) {
            throw new ContentBussinessException(CodeConstants.MODEL_SPLIT_NOT_EXIST, "模板碎片不存在");
        }

        //排除已经添加的
        
        List<SplitContentVO> newSplitContentVOList=splitContentVO.getSpiltContentList();
        List<SplitContent> oldSplitContentList=new ArrayList<SplitContent>();

        for(SplitContentVO vo:splitContentVO.getSpiltContentList()) {
           if(StringUtils.isBlank(vo.getId())) {
               newSplitContentVOList.add(vo);
           }else {
               SplitContent splitContent=new SplitContent();
               
               BeanUtils.copyProperties(vo, splitContent);
               oldSplitContentList.add(splitContent);
           }
        }
        Boolean isSuccess=true;
        if(CollectionUtils.isNotEmpty(oldSplitContentList)) {
            isSuccess=this.updateBatchById(oldSplitContentList);
        }
        
        if(CollectionUtils.isNotEmpty(newSplitContentVOList) && isSuccess) {
            List<SplitContent> entityList=new ArrayList<SplitContent>();

            Set<String> contentids=newSplitContentVOList.stream().map(s ->{
                if(!StringUtils.isEmpty(s.getContentid())) {
                    return s.getContentid();
                }
                return null;
            }).collect(Collectors.toSet());
            //准备封装内容栏目信息
            List<Content> contentList=contentMapper.selectBatchIds(contentids);

            Map<String,String> contentMap=new HashMap<String,String>();
            
            Map<String,Integer> contentLogicMap=new HashMap<String,Integer>();
            contentList.forEach(c ->{
                contentMap.put(c.getId(), c.getChannelid());
                contentLogicMap.put(c.getId(), c.getLogicId());
            });
            //准备封装内容栏目得逻辑ID
            Set<String> channelIds=contentList.stream().map(c ->{return c.getChannelid();}).collect(Collectors.toSet());
            
            channelIds.add(splitContentVO.getChannelId());
            List<Channel> channelList=channelMapper.selectBatchIds(channelIds);
            
            Map<String,Integer> channelMap=new HashMap<String,Integer>();
            
            channelList.forEach(c ->{
                channelMap.put(c.getId(), c.getLogicId());
            });
            QueryWrapper<SplitContent> queryWrapper=new QueryWrapper<SplitContent>();
            queryWrapper.eq("model_split_id", splitContentVO.getModelSpiltId());
            queryWrapper.eq("channel_id", splitContentVO.getChannelId());
            queryWrapper.orderByDesc("subindex");
            int subindex=1;
            List<SplitContent> splitContentList=this.list(queryWrapper);
            
            if(!CollectionUtils.isEmpty(splitContentList)) {
                subindex=splitContentList.get(0).getSubindex()+1;
            }
            //封装要添加得信息
            for(SplitContentVO sc:newSplitContentVOList) {

                SplitContent splitContent=new SplitContent();
                BeanUtils.copyProperties(splitContentVO, splitContent);
                splitContent.setChannelId(splitContentVO.getChannelId());
                splitContent.setChannelLogicId(channelMap.get(splitContentVO.getChannelId()));
                splitContent.setCreaterid(sc.getImporter());
                splitContent.setCreateTime(new Date());
                splitContent.setPublishEnable(0);
                splitContent.setModelId(modelSplit.getModelId());
                splitContent.setModelSplitId(splitContentVO.getModelSpiltId());
                splitContent.setModelSplitLogicId(modelSplit.getLogicId());
                splitContent.setShowTitle(sc.getTitle());
                splitContent.setDtype(sc.getDtype());
                splitContent.setSubindex(new Integer(subindex));
                if(!StringUtils.isEmpty(contentMap.get(sc.getContentid()))) {
                   splitContent.setContentChannelId(contentMap.get(sc.getContentid()));
                   splitContent.setContentChannelLogicId(channelMap.get(contentMap.get(sc.getContentid())));
                   splitContent.setContentLogicId(contentLogicMap.get(sc.getContentid()));
                }
                subindex++;
                entityList.add(splitContent);
            
            }

            return this.saveBatch(entityList);
        }
        return true;
    }
    /**
     * 
    * @Title: 查询模板碎片内容集合 
    * @author shidandan
    * @date 2019年1月17日 下午4:44:05 
    * @Description: 查询模板碎片内容集合
    * @param spiltContentList
    * @return  参数说明 
    * @return List<SplitContentVO>    返回类型 
    * @throws 
     */
    private List<SplitContentVO> querySplitContentListResult(List<SplitContent> spiltContentList){
        if(!CollectionUtils.isEmpty(spiltContentList)) {
            List<SplitContentVO> result=new ArrayList<SplitContentVO>();
            //封装返回结果
            spiltContentList.forEach(splitContent ->{
                SplitContentVO vo=new SplitContentVO();
                vo.setDtype(splitContent.getDtype());
                vo.setId(splitContent.getId());
                vo.setImporter(splitContent.getCreaterid());
                vo.setImportTime(splitContent.getCreateTime());
                vo.setTitle(splitContent.getShowTitle());
                vo.setDstatus(splitContent.getPublishEnable());
                vo.setContentid(splitContent.getContentid());
                vo.setId(splitContent.getId());
                result.add(vo);
            });
            return result;

        }
        return null;
    }
}
