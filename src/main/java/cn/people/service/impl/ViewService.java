package cn.people.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.regexp.RESyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.people.commons.exceptions.ContentBussinessException;
import cn.people.controller.vo.ChannelPageVo;
import cn.people.controller.vo.ContentPageVo;
import cn.people.controller.vo.ModelSplitMapResponseVO;
import cn.people.service.IContentService;
import cn.people.service.IViewService;
import cn.people.service.view.TemplateService;
import cn.people.service.view.obj.ChannelObj;
import cn.people.service.view.obj.CommonTool;
import cn.people.service.view.obj.ContentObj;
import cn.people.service.view.obj.ContentUrl;

/**
 * 
* @ClassName: ViewService 
* @Description: 模板预览实现类
* @author zhangchengchun
* @date 2019年1月17日 下午1:47:41 
*  
 */
@Service
public class ViewService implements IViewService
{
    @Autowired
    private TemplateService templateService;
    
    //根据页面中是否包括<!--PageNo=([0-9]+)-->来获取分页数
    public static Pattern CHANNEL_PAGE_PATTERN = Pattern.compile("<!--PageNo=([0-9]+)-->");
        
    /**
     * 
    * Title: 查询栏目的模板原内容  
    * @author zhangchengchun
    * @date 2019年1月21日 上午10:35:35 
    * @Description: 查询栏目的模板原内容
    * @param logicId 栏目逻辑id
    * @return 栏目的模板原内容
    * @see cn.people.service.IViewService#getChannelTemplateContent(java.lang.Integer)
     */
    @Override
    public String getChannelTemplateContent(Integer logicId)
    {
        return templateService.getChannelTemplateContent(logicId);
    }
    
    /**
     * 
    * Title:  查询栏目的模板中的标记集合
    * @author zhangchengchun
    * @date 2019年1月21日 上午10:36:56 
    * @Description: 查询栏目的模板中的标记集合
    * @param logicId 栏目逻辑id
    * @return 栏目的模板中的标记集合
    * @see cn.people.service.IViewService#getChannelTemplateSplits(java.lang.Integer)
     */
    @Override
    public List<ModelSplitMapResponseVO> getChannelTemplateSplits(Integer logicId)
    {
        return templateService.getChannelTemplateSplits(logicId);
    }
    
    /**
     * 
    * Title: 预览栏目的模板 
    * @author zhangchengchun
    * @date 2019年1月21日 上午10:40:12 
    * @Description: 预览栏目的模板 
    * @param logicId 栏目逻辑id
    * @param pageNo 当前页码，可以不传
    * @param info 调试信息，支持值：info|debug
    * @param vtype 预览类型，预览时不传，静态化时传publish
    * @return 预览栏目的模板 
    * @see cn.people.service.IViewService#getChannelPreview(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @Override
    public String getChannelPreview(Integer logicId, Integer pageNo, String info, String vtype)
    {
        return templateService.getChannelPreview(logicId, pageNo, info, vtype);
    }
    
    /**
     * 
    * Title: 预览稿件的模板
    * @author zhangchengchun
    * @date 2019年1月21日 上午10:42:21 
    * @Description: 预览稿件的模板
    * @param contentLogicId 稿件的逻辑id
    * @param pageNo 当前页码，可以不传
    * @param info 调试信息，支持值：info|debug
    * @param vtype 预览类型，预览时不传，静态化时传publish
    * @return 预览稿件的模板
    * @throws ContentBussinessException 
    * @see cn.people.service.IViewService#getNewsPreview(java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @Override
    public String getNewsPreview(Integer contentLogicId, Integer pageNo, String info, String vtype) throws ContentBussinessException
    {
        return templateService.getNewsPreview(contentLogicId, pageNo, info, vtype);
    }

    /**
     * 
    * Title: 查询稿件分页信息
    * @author zhangchengchun
    * @date 2019年1月31日 上午10:00:49 
    * @Description: 查询稿件分页信息
    * @param contentLogicId 稿件逻辑id
    * @return List<ContentPageVo>
    * @throws ContentBussinessException 
     */
    @Override
    public List<ContentPageVo> getContentPages(Integer contentLogicId)
        throws ContentBussinessException
    {
        List<ContentPageVo> result = new ArrayList<ContentPageVo>();
        ContentObj content = new ContentObj(contentLogicId);
        if(content.getPageType()==1||content.getPageType()==2) {
            content.initPage(1);
        }
       
        ContentUrl contentUrl = content.getOldUrl();
        String fristUrl = contentUrl.getUrl();
        ContentPageVo fristPage = new ContentPageVo();
        fristPage.setUrl(fristUrl);
        fristPage.setPageNo(0);
        fristPage.setDomain(content.getDomain());
        result.add(fristPage);

        int count = content.getPageCount();
        for (int i = 2; i <= count; i++ )
        {
            String url = content.getUrl(i);
            ContentPageVo page = new ContentPageVo();
            page.setUrl(url);
            page.setPageNo(i);
            page.setDomain(content.getDomain());
            result.add(page);
        }

        return result;
    }

    /**
     * @Title: 查询栏目分页信息 
     * @author zhangchengchun
     * @date 2019年1月31日 上午10:30:13 
     * @Description: 查询栏目分页信息 
     * @param logicId  栏目逻辑id
     * @param @return
     *             参数说明 
     * @return List<ChannelPageVo>    返回类型  @throws 
     */
    @Override
    public List<ChannelPageVo> getChannelPages(Integer logicId)
    {
        List<ChannelPageVo> pages = new ArrayList<ChannelPageVo>();
        ChannelObj channel = new ChannelObj(logicId);
        String url = channel.getChannelUrl().getUrlPath()+"/"+"index.html";
        Integer pageNo=0;
        ChannelPageVo first = new ChannelPageVo();
        first.setUrl(url);
        first.setPageNo(pageNo);
        pages.add(first);
        
        String content =  templateService.getChannelPreview(logicId, 0, "", "publish");
        int maxPage=getMaxPageOfChannel(content);
        if(maxPage>1){
            for (int i = maxPage; i>0; i--) {
                pageNo=1;
                url = channel.getChannelUrl().getUrlPath()+File.separator+"index"+i+".html";
                ChannelPageVo page = new ChannelPageVo();
                page.setUrl(url);
                page.setPageNo(logicId);
                pages.add(page);
            }
        }
        
        return pages;
    }
    
    protected int getMaxPageOfChannel( String urlContent ) throws RESyntaxException
    {
        Matcher matcher = CHANNEL_PAGE_PATTERN.matcher(urlContent);
        if (matcher.find()) {
            return CommonTool.getInt(matcher.group(1));
        }
        return 0;
    }

}
