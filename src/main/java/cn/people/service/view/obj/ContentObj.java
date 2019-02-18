package cn.people.service.view.obj;


import java.sql.Clob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.people.commons.constants.CodeConstants;
import cn.people.commons.exceptions.ContentBussinessException;
import cn.people.commons.utils.SpringContextUtil;
import cn.people.controller.vo.ContentDetailVO;
import cn.people.entity.Channel;
import cn.people.entity.Content;
import cn.people.entity.ContentMedia;
import cn.people.entity.ContentVersion;
import cn.people.service.IChannelService;
import cn.people.service.IContentMediaService;
import cn.people.service.IContentService;
import cn.people.service.IContentVersionService;


/**
 * Content entity. @author MyEclipse Persistence Tools
 */

public class ContentObj implements java.io.Serializable
{
    public static SimpleDateFormat contentUrlFormat = new SimpleDateFormat("yyyy/MMdd/");

    public static String pageBreak = "{PAGINATION}";// 系统分页符

    public static String pageBreakRegex = "\\{PAGINATION\\}";// 系统分页符

    public static Vector<String> pageSymbols = new Vector<String>();// 分页符

    public static String channelPathRegex = "[a-zA-Z0-9]+";// 栏目静态地址中FILE_PATH部分

    public static String channelPathNoRegex = "[0-9]+";// 栏目静态地址中FILE_PATH部分

    private static int autoPagination = SharedConstant.AutoPagination;

    static
    {
        // pageSymbols.add("\t*<div style=\"page-break-after: always;\">\n\t*<span style=\"display:
        // none;\">&nbsp;</span></div>");// ckeditor的分页符
        pageSymbols.add("(?is)\t*<div style=\"([^<>]*?)page-break-after: always(.*?)</div>");// ckeditor的分页符
        pageSymbols.add(
            "<div style=\"PAGE-BREAK-AFTER: always\"><span style=\"DISPLAY: none\">&nbsp;</span></div>");// fckeditor的分页符
        pageSymbols.add("_ueditor_page_break_tag_");
    }

    // Fields

    private String contentId;
    
    private Integer contentLogicId;

    private String originName;

    private String originUrl;

    private Integer languageId;

    private Integer nodeId;

    private String title;

    private String pretitle;

    private String subtitle;

    private String content;

    private String contentString;

    private String author;

    private String summary;

    private Integer inputBy;

    private Timestamp inputDate;

    private Timestamp modifyDate;

    private Timestamp displayDate;

    private Integer mediaCount;

    private Integer status;

    private Short deleted;

    private String templateId;

    private String editor;

    private Timestamp releaseDate;

    private String contentPrefix;

    private String contentPostfix;

    private Integer pageType;

    private Short contentType;

    private String releaseBy;

    // 域名
    private String domain;

    // 无线渠道
    private String coUrlName;

    // 无线版本 url_name
    private String verUrlName;

    // 新闻分页
    private Vector<String> pages = new Vector<String>();// 所有分页

    private int pageCount = 0;// 分页总数

    private int page = 0;// 当前页

    // 媒体列表
    private List<ContentMedia> mediae;

    private ContentMedia pageMedia;// 当前分页的media

    IContentService iContentService = SpringContextUtil.getBean(IContentService.class);
    IChannelService iChannelService = SpringContextUtil.getBean(IChannelService.class);
    IContentMediaService iContentMediaService = SpringContextUtil.getBean(IContentMediaService.class);
    IContentVersionService iContentVersionService = SpringContextUtil.getBean(IContentVersionService.class);

    public ContentObj(ContentDetailVO contentSource)
    {
        init(contentSource);
    }

    public ContentObj(Integer contentLogicId)
        throws ContentBussinessException
    {
        ContentDetailVO content = iContentService.getContentByLogicId(contentLogicId);
        if (content != null)
        {
            init(content);
        }
        else
        {
            throw new ContentBussinessException(CodeConstants.CONTENT_NOT_EXIST,
                "id为" + contentLogicId + "的稿件不存在");
        }
    }

    private void init(ContentDetailVO contentSource)
    {
        this.contentId = contentSource.getId();
        this.contentLogicId = contentSource.getLogicId();
        this.languageId = 1;// TODO 暂时只支持中文
        this.nodeId = contentSource.getChannelLogicId();
        this.title = contentSource.getTitle();
        this.pretitle = contentSource.getShouldertitle();
        this.subtitle = contentSource.getSubtitle();
        this.content = contentSource.getContent();
        setContentString(contentSource.getContent());
        this.author = contentSource.getAuthor();
        this.summary = contentSource.getDescription();
        this.inputDate = DateUtil.dateToTimestamp(contentSource.getCreatetime());
        this.modifyDate = DateUtil.dateToTimestamp(contentSource.getUpdatetime());
        this.displayDate = DateUtil.dateToTimestamp(contentSource.getSendtime());
        this.mediaCount = contentSource.getMediaCount();
        this.status = contentSource.getDstatus();
        this.templateId = initTemplateId(contentSource.getChannelLogicId());
        this.releaseDate = DateUtil.dateToTimestamp(contentSource.getSendtime());
        this.contentPrefix = "";// 原标题
        this.contentPostfix = "";// 短标题
        this.inputBy = 0; // TODO 创建用户id
        this.editor = "";// TODO 责任编辑
        this.pageType = contentSource.getDtype();// TODO 人民网  分页方式;2||21 媒体分页，其他正文分页   新cms 1 图文   2 图集        未确定如何分页
        this.releaseBy = initReleaseBy(contentSource.getId());
        this.contentType = 0;// TODO 原创 标记 0:非原创 1 原创;
    }

    private String initReleaseBy(String contentId)
    {
        QueryWrapper<ContentVersion> queryWrapper = new QueryWrapper<ContentVersion>();
        queryWrapper.eq("contentid", contentId);
        queryWrapper.eq("dtype", Content.DSTATUS_CREATED);
        queryWrapper.orderByDesc("createtime");
        ContentVersion cv = iContentVersionService.getOne(queryWrapper);
        if(null!=cv) {
            return cv.getOperatorname();
        }
        return "";
    }

    private String initTemplateId(Integer channelLogicId)
    {
        Channel channel = iChannelService.getByLogicId(channelLogicId);
        if (!StringUtils.isEmpty(channel.getContentModelId()))
        {
            return channel.getContentModelId();
        }
        else
        {
            String parentString = channel.getParentString();
            if (!StringUtils.isEmpty(parentString))
            {
                String[] pLogicIds = parentString.split(",");
                for (int i = pLogicIds.length - 1; i >= 0; i-- )
                {
                    Channel pChannel = iChannelService.getByLogicId(
                        Integer.parseInt(pLogicIds[i]));
                    if (pChannel != null && !StringUtils.isEmpty(pChannel.getContentModelId()))
                    {
                        return pChannel.getContentModelId();
                    }
                }
            }
        }
        return null;
    }

    public String getUrl()
    {
        ContentUrl contentUrl = getOldUrl();
        if (contentUrl != null)
            return contentUrl.getUrl();
        else
            return "#";
    }

    // 得到栏目路径中设置了FILE_PATH的路径
    private static ArrayList<String> getPathFile(String channelurl)
    {
        ArrayList<String> result = new ArrayList<String>();
        if (channelurl != null)
        {
            if (channelurl.lastIndexOf("/") > -1)
            {
                String url = channelurl.substring(0, channelurl.lastIndexOf("/") + 1);
                String urlArray[] = url.split("/");
                for (int i = urlArray.length - 1; i >= 0; i-- )
                {
                    if (urlArray[i].matches(channelPathNoRegex) == false
                        && urlArray[i].matches(channelPathRegex))
                    {
                        result.add(urlArray[i]);
                    }
                }

            }
        }
        return result;
    }

    // 获取域名
    public String getDomain()
    {
        if (domain != null)
        {
            return domain;
        }

        ContentUrl contentUrl = getOldUrl();
        domain = contentUrl.getDomain();

        return domain;
    }

    // 获取静态地址
    public String getStaticUrl()
    {
        ContentUrl contentUrl = getOldUrl();
        return "http://" + contentUrl.getDomain() + (getLanguageId() == 1 ? "/GB/" : "/")
               + contentUrl.getUrl();
    }

    public String getUrl(int page)
    {
        if (!StringUtils.isEmpty(contentId))
        {
            // 重新计算新闻静态地址
            Channel channel = iChannelService.getByLogicId(nodeId);
            ChannelObj channelObj = new ChannelObj(channel);
            ChannelUrl channelUrl = channelObj.getChannelUrl();

            ContentUrl newContentUrl = new ContentUrl();
            newContentUrl.setLogicId(contentLogicId);
            newContentUrl.setDomain(channelUrl.getDomain());

            String fileExtName = ".html";
            newContentUrl.setUrl(channelUrl.getUrlPath()+ "/"+ contentLogicId + fileExtName);

            if ((page > 1))
            {
                String url = newContentUrl.getUrl();
                int len = url.lastIndexOf(".");
                if (len >= 0)
                {
                    url = url.substring(0, len) + "-" + page + url.substring(len);
                }
                return url;
            }
            else
            {
                return newContentUrl.getUrl();
            }

        }
        else
        {
            return "#";
        }
    }

    public ContentUrl getOldUrl()
    {
        ContentUrl contentUrl = null;

        // 重新计算新闻静态地址
        Channel channel = iChannelService.getByLogicId(nodeId);
        if (channel != null)
        {
            ChannelObj channelObj = new ChannelObj(channel);
            ChannelUrl channelUrl = channelObj.getChannelUrl();
            contentUrl = new ContentUrl();
            contentUrl.setLogicId(contentLogicId);
            contentUrl.setDomain(channelUrl.getDomain());            
           
            contentUrl.setUrl(channelUrl.getUrlPath()+ "/" + contentLogicId + ".html");
        }
        return contentUrl;

    }

    // 获取媒体列表，类型为通过媒体列表管理上传的图片
    public List<ContentMedia> getMediae()
    {
        if (mediae == null)
        {
            if (!StringUtils.isEmpty(contentId))
            {
                QueryWrapper<ContentMedia> queryWrapper = new QueryWrapper<ContentMedia>();
                queryWrapper.eq("content_id", contentId);
                queryWrapper.ne("uploadtype", 0);
                queryWrapper.orderByAsc("level", "id");
                mediae = iContentMediaService.list(queryWrapper);
            }
        }
        return mediae;
    }

    // Property accessors

    public void initPage(int pageNo)
    {
        if (contentString != null && contentString.length() > 0 && pageType != null
            && pageType > 0)
        {
            if (pageType == 1)
            {
                String temp = contentString;
                for (String symbol : pageSymbols)
                {
                    temp = temp.replaceAll(symbol, pageBreakRegex);
                }
                if (temp.indexOf(pageBreak) >= 0)
                {
                    String contents[] = temp.split(pageBreakRegex);
                    for (int i = 0; i < contents.length; i++ )
                    {
                        if (temp.startsWith("&$"))
                        {
                            if (!contents[i].startsWith("&$"))
                            {
                                contents[i] = "&$" + contents[i];
                            }
                            if (!contents[i].endsWith("&$"))
                            {
                                contents[i] = contents[i] + "&$";
                            }
                            pages.add(contents[i]);
                        }
                        else
                        {
                            pages.add(contents[i]);
                        }
                    }
                    pageCount = pages.size();
                    if (pageNo > 0 && pageNo <= pageCount)
                    {
                        page = pageNo;
                        contentString = pages.get(page - 1);
                    }
                    else if (pageNo == 0)
                    {
                        page = 1;
                        contentString = pages.get(0);
                    }
                }
                else
                {
                    pages.add(contentString);
                    pageCount = 1;
                    page = 1;
                }
            }
            else if (pageType == 2)
            {
                if (getMediae() != null && getMediae().size() > 0)
                {
                    pageCount = mediae.size();
                    if (pageNo > 0 && pageNo <= pageCount)
                    {
                        page = pageNo;
                        pageMedia = mediae.get(page - 1);
                    }
                    else if (pageNo == 0)
                    {
                        page = 1;
                        pageMedia = mediae.get(0);
                    }
                }
            }
            else if (pageType == 21)
            {
                if (getMediae() != null && getMediae().size() > 0)
                {
                    pageCount = mediae.size();
                    if (pageNo > 0 && pageNo <= pageCount)
                    {
                        page = pageNo;
                        pageMedia = mediae.get(page - 1);
                    }
                    else if (pageNo == 0)
                    {
                        page = 1;
                        pageMedia = mediae.get(0);
                    }
                    else
                    {
                        page = 1;
                    }
                    if (page > 1)
                    {
                        contentString = "";
                    }
                }
            }
            else if (pageType == 3)
            {
                String temp = contentString;
                for (String symbol : pageSymbols)
                {
                    temp = temp.replaceAll(symbol, pageBreakRegex);
                }
                if (temp.indexOf(pageBreak) >= 0)
                {
                    String contents[] = temp.split(pageBreakRegex);
                    for (int i = 0; i < contents.length; i++ )
                    {
                        if (temp.startsWith("&$"))
                        {
                            if (!contents[i].startsWith("&$"))
                            {
                                contents[i] = "&$" + contents[i];
                            }
                            if (!contents[i].endsWith("&$"))
                            {
                                contents[i] = contents[i] + "&$";
                            }
                            pages.add(contents[i]);
                        }
                        else
                        {
                            pages.add(contents[i]);
                        }
                    }
                    pageCount = pages.size();
                    if (pageNo > 0 && pageNo <= pageCount)
                    {
                        page = pageNo;
                        contentString = pages.get(page - 1);
                    }
                    else if (pageNo == 0)
                    {
                        page = 1;
                        contentString = pages.get(0);
                    }
                }
                else
                {
                    Vector<String> contentList = getPageContentList(contentString, autoPagination);
                    pageCount = contentList.size();
                    if (pageNo > 0 && pageNo <= pageCount)
                    {
                        page = pageNo;
                        contentString = "&$" + contentList.get(page - 1) + "&$";
                    }
                    else if (pageNo == 0)
                    {
                        page = 1;
                        // contentString = "&$"+contentList.get(0)+"&$";
                        contentString = "&$" + (pageCount > 0 ? contentList.get(0) : contentString)
                                        + "&$";
                    }
                }
            }
            else
            {
                pages.add(contentString);
                pageCount = 1;
                page = 1;
            }
        }
    }

    public Vector<String> getPageContentList(String contentString, int pageCount)
    {
        if (contentString == null)
        {
            return pages;
        }
        else
        {
            contentString = contentString.replace("&$", "");
        }
        String regex = "<p.*?>(\\s.*)|(.*?)</p>";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(contentString);
        int sum = 0;
        HtmlToText HtmlToText = new HtmlToText();
        String lastStr = "";
        while (m.find())
        {
            String mStr = m.group(0);
            String htmStr = HtmlToText.html2Text(mStr);
            sum = sum + htmStr.length();
            if (sum <= pageCount)
            {
                lastStr = lastStr + mStr;
            }
            else
            {
                int end = contentString.indexOf(mStr);
                String subStr = contentString.substring(0, end + mStr.length());
                pages.add(subStr);
                contentString = contentString.substring(end + mStr.length());
                lastStr = "";
                sum = 0;
            }
        }
        if (lastStr.length() > 0)
        {
            if (lastStr.length() < pageCount / 2)
            {
                if (pages.size() > 0)
                {
                    String lastContent = (String)pages.get(pages.size() - 1) + lastStr;
                    pages.remove(pages.size() - 1);
                    pages.add(lastContent);
                }
                else
                {
                    pages.add(lastStr);
                }
            }
            else
            {
                pages.add(lastStr);
            }
        }
        return pages;
    }

    public String getContentId()
    {
        return this.contentId;
    }

    public void setContentId(String contentId)
    {
        this.contentId = contentId;
    }

    public String getOriginName()
    {
        return originName;
    }

    public void setOriginName(String originName)
    {
        this.originName = originName;
    }

    public String getOriginUrl()
    {
        return originUrl;
    }

    public void setOriginUrl(String originUrl)
    {
        this.originUrl = originUrl;
    }

    public Integer getLanguageId()
    {
        return this.languageId;
    }

    public void setLanguageId(Integer languageId)
    {
        this.languageId = languageId;
    }

    public Integer getNodeId()
    {
        return this.nodeId;
    }

    public void setNodeId(Integer nodeId)
    {
        this.nodeId = nodeId;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPretitle()
    {
        return this.pretitle;
    }

    public void setPretitle(String pretitle)
    {
        this.pretitle = pretitle;
    }

    public String getSubtitle()
    {
        return this.subtitle;
    }

    public void setSubtitle(String subtitle)
    {
        this.subtitle = subtitle;
    }

    public String getContent()
    {
        return this.content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContentString()
    {
        return this.contentString;
    }

    public void setContentString(String contentString)
    {
        this.contentString = contentString;
    }

    public String getAuthor()
    {
        return this.author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getSummary()
    {
        return this.summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public Integer getInputBy()
    {
        return this.inputBy;
    }

    public void setInputBy(Integer inputBy)
    {
        this.inputBy = inputBy;
    }

    public Timestamp getInputDate()
    {
        return this.inputDate;
    }

    public void setInputDate(Timestamp inputDate)
    {
        this.inputDate = inputDate;
    }

    public Timestamp getModifyDate()
    {
        return this.modifyDate;
    }

    public void setModifyDate(Timestamp modifyDate)
    {
        this.modifyDate = modifyDate;
    }

    // DisplayDate为空时显示InputDate
    public Timestamp getDisplayDate()
    {
        if (displayDate == null)
        {
            return getInputDate();
        }
        return this.displayDate;
    }

    public void setDisplayDate(Timestamp displayDate)
    {
        this.displayDate = displayDate;
    }

    public Integer getMediaCount()
    {
        return this.mediaCount;
    }

    public void setMediaCount(Integer mediaCount)
    {
        this.mediaCount = mediaCount;
    }

    public Integer getStatus()
    {
        return this.status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Short getDeleted()
    {
        return this.deleted;
    }

    public void setDeleted(Short deleted)
    {
        this.deleted = deleted;
    }

    public String getTemplateId()
    {
        return this.templateId;
    }

    public void setTemplateId(String templateId)
    {
        this.templateId = templateId;
    }

    public String getEditor()
    {
        return this.editor;
    }

    public void setEditor(String editor)
    {
        this.editor = editor;
    }

    public Timestamp getReleaseDate()
    {
        return this.releaseDate;
    }

    public void setReleaseDate(Timestamp releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public String getContentPrefix()
    {
        return this.contentPrefix;
    }

    public void setContentPrefix(String contentPrefix)
    {
        this.contentPrefix = contentPrefix;
    }

    public String getContentPostfix()
    {
        return this.contentPostfix;
    }

    public void setContentPostfix(String contentPostfix)
    {
        this.contentPostfix = contentPostfix;
    }

    public Integer getPageType()
    {
        return this.pageType;
    }

    public void setPageType(Integer pageType)
    {
        this.pageType = pageType;
    }

    public Short getContentType()
    {
        return this.contentType;
    }

    public void setContentType(Short contentType)
    {
        this.contentType = contentType;
    }

    public int getPageCount()
    {
        return pageCount;
    }

    public int getPage()
    {
        return page;
    }

    public String getReleaseBy()
    {
        return releaseBy;
    }

    public void setReleaseBy(String releaseBy)
    {
        this.releaseBy = releaseBy;
    }

    public ContentMedia getPageMedia()
    {
        return pageMedia;
    }

    public String getCoUrlName()
    {
        return coUrlName;
    }

    public void setCoUrlName(String coUrlName)
    {
        this.coUrlName = coUrlName;
    }

    public String getVerUrlName()
    {
        return verUrlName;
    }

    public void setVerUrlName(String verUrlName)
    {
        this.verUrlName = verUrlName;
    }

    public Integer getContentLogicId()
    {
        return contentLogicId;
    }

    public void setContentLogicId(Integer contentLogicId)
    {
        this.contentLogicId = contentLogicId;
    }
}