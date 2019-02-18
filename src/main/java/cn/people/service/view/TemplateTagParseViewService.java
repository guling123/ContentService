package cn.people.service.view;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.people.entity.Channel;
import cn.people.entity.ModelSplit;
import cn.people.entity.Split;
import cn.people.service.IChannelService;
import cn.people.service.IModelSplitService;
import cn.people.service.ISplitService;
import cn.people.service.view.obj.ContentObj;
import cn.people.service.view.obj.ContentTagValueRedis;
import cn.people.service.view.obj.SharedConstant;
import cn.people.service.view.tag.AbstractBaseTag;
import cn.people.service.view.tag.ContentTag;


@Service
public class TemplateTagParseViewService
{
    private static final Logger log = LoggerFactory.getLogger(TemplateTagParseViewService.class);

    @Autowired
    private IModelSplitService iModelSplitService;

    @Autowired
    private ISplitService isplitService;
    
    @Autowired
    private IChannelService iChannelService;

    public String viewChannelNewsOld(TemplateTagParseModel model,boolean isEdit)
    {
        if (model.getTtId() > 0)
        {
            ModelSplit templateTag = iModelSplitService.getByLogicId(model.getTtId());
            if (templateTag != null)
            {
                try
                {
                    Boolean isEditTag = true;
                        
                    Split tag = isplitService.getById(templateTag.getSplitId());
                    int endIndex = tag.getClassName().indexOf(".");
                    String className = tag.getClassName().substring(0, endIndex);
                    Class tc = Class.forName("cn.people.service.view.tag." + className);
                    AbstractBaseTag baseTag = (AbstractBaseTag)tc.newInstance();
                    if (!StringUtils.isEmpty(templateTag.getChannelId())
                        &&!"0".equals(templateTag.getChannelId()))
                    {
                        //如果存在主控栏目，并且主控栏目并非当前栏目，则不可以编辑改模板碎片    
                        if(model.getNodeId()!=Integer.parseInt(templateTag.getChannelId())) {
                            isEditTag = false;
                        }
                        
                        Channel channel = iChannelService.getById(Integer.parseInt(templateTag.getChannelId()));
                        model.setNodeId(channel.getLogicId());;
                    }
                    baseTag.initTag(model.getTtId(), model.getNodeId(), model.getContentId(),
                        model.getPageNo());

                    HashMap<String, Object> values = baseTag.view();
                    String result = values.get("tagvalues").toString();
                    if(isEdit && !StringUtils.isEmpty(result)) {
                    	// 预览时增加标签
                    	result = "<span ttid='msl"+ templateTag.getLogicId() +"' data-model-enable='"+isEditTag+"' data-model-id='msl' data-model-value='"+templateTag.getSplitName()+"'>" + result + "</span>";
                    }
                    return result;
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    log.error("预览标记出错:ttId=" + model.getTtId() + ";nodeId=" + model.getNodeId()
                              + "\r\n" + ex.getMessage());
                }
            }
        }
        return "";

    }

    public String viewRedisContentNewsOld(ContentObj content, String s)
    {
        int pageCount = content.getPageCount();
        int pageNo = content.getPage();
        ContentTagValueRedis redis = new ContentTagValueRedis();
        String replaceStr = "";
        if (SharedConstant.REDIS)
        {
            if (pageCount > 1)
            {
                if (pageNo <= 1)
                {
                    replaceStr = viewContentNewsOld(content,s);
                    // 设置redis缓存值
                    redis.set(String.valueOf(content.getContentId()), s, replaceStr);
                }
                else
                {
                    // 取redis缓存值
                    replaceStr = redis.get(String.valueOf(content.getContentId()), s);
                    if (replaceStr == null)
                    {
                        replaceStr = viewContentNewsOld(content,s);
                        redis.set(String.valueOf(content.getContentId()), s, replaceStr);
                    }
                }
            }
            else
            {
                replaceStr = viewContentNewsOld(content,s);
            }
        }
        else
        {
            replaceStr = viewContentNewsOld(content,s);
        }
        return replaceStr;
    }

    public String viewContentNewsOld(ContentObj content, String s)
    {
        try
        {
            String content_id = content.getContentId();
            Integer node_id = content.getNodeId();
            AbstractBaseTag baseTag = new ContentTag();
            baseTag.initTag(node_id, content_id, s, content);
            HashMap<String, Object> values = baseTag.view();
            String result = values.get("tagvalues").toString();
            return result;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.error("预览新闻出错:contentId=" + content.getContentId() + ";nodeId=" + content.getNodeId()
                      + "\r\n" + ex.getMessage());
        }
        return "";
    }
    
    //只有多页时候缓存redis值，并且第一页先删除redis值在设置redis值。
    public String viewRedisChannelNewsOld(TemplateTagParseModel model, boolean isEdit){
        int pageCount = model.getObj().getPageCount();
        ContentTagValueRedis redis = new ContentTagValueRedis();
        String replaceStr = "";
        if(SharedConstant.REDIS){
            if(pageCount>1){
                if(model.getPageNo()>1){
                    //取redis缓存值
                    replaceStr = redis.get(String.valueOf(model.getObj().getContentId()), String.valueOf(model.getTtId()));
                    if(replaceStr == null){
                        replaceStr = viewChannelNewsOld(model, isEdit);
                        redis.set(String.valueOf(model.getObj().getContentId()),  String.valueOf(model.getTtId()), replaceStr);
                    }
                }else{
                    replaceStr = viewChannelNewsOld(model, isEdit);
                    //设置redis缓存值
                    redis.set(String.valueOf(model.getObj().getContentId()),  String.valueOf(model.getTtId()), replaceStr);
                }                                                                                                           
            }else{
                replaceStr = viewChannelNewsOld(model, isEdit);
            }
        }else{
            replaceStr = viewChannelNewsOld(model, isEdit);
        }
        return replaceStr;
    }

}
