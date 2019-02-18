package cn.people.service.view;


import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;

import cn.people.commons.exceptions.ContentBussinessException;
import cn.people.controller.vo.ModelSplitMapResponseVO;
import cn.people.entity.Channel;
import cn.people.entity.Model;
import cn.people.entity.ModelSplit;
import cn.people.entity.ModelSplitProp;
import cn.people.entity.Split;
import cn.people.service.IChannelService;
import cn.people.service.IContentService;
import cn.people.service.IModelService;
import cn.people.service.IModelSplitPropService;
import cn.people.service.IModelSplitService;
import cn.people.service.ISplitService;
import cn.people.service.view.obj.ChannelObj;
import cn.people.service.view.obj.ChannelTagValueRedis;
import cn.people.service.view.obj.ContentObj;
import cn.people.service.view.obj.DateUtil;
import cn.people.service.view.obj.FileTool;
import cn.people.service.view.obj.Regular;
import cn.people.service.view.obj.SharedConstant;
import cn.people.service.view.obj.StringTool;


@Service
public class TemplateService
{
    @Autowired
    private IChannelService iChannelService;

    @Autowired
    private IModelService iModelService;

    @Autowired
    private IModelSplitService iModelSplitService;

    @Autowired
    private ISplitService isplitService;

    @Autowired
    private IModelSplitPropService iModelSpiltPropService;

    @Autowired
    private TemplateTagParseViewService templateTagParseViewService;
    
    @Autowired
    private IContentService iContentService;

    private static final Logger log = LoggerFactory.getLogger(TemplateService.class);

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");

    // 模板目录
    @Value("${model.modelDir}")
    private String templateDir = "/template/";

    // 模板图片目录
    private String templateImgDir = "/img/";

    // 自定义模板目录
    private String customTemplateDir = "/customtemplate/";

    // 模板url前缀
    private String templateUrlStart = "/template/";

    // 自定义模板url前缀
    private String customTemplateUrlStart = "/customtemplate/";

    private static Pattern HEAD_TAG_PATTERN = Pattern.compile("(?is)<head([^>]*?)>");

    private static Pattern PORTAL_COLUMN_PATTERN = Pattern.compile(
        "(?is)<div[^<>]*?id=[\"'](PortalColumn[0-9]+)[\"']([^>]*?)>(.*?)</div>");

    public static Pattern TAG_PATTERN = Pattern.compile("\\$\\{TAG_([0-9]+)_TAG\\}");

    public static Pattern TAG_PRE_PATTERN = Pattern.compile("\\$\\{TAG_PRE([0-9]+)_TAG\\}");// 可视化预处理标记,用来组合html代码和其他标记

    public static Pattern TAGOLD_PATTERN = Pattern.compile("\\{TAG_([0-9]+)_TAG\\}");

    // 样式表格式
    // private static Pattern CSS_PATTERN = Pattern.compile("<link +href=\".+/main.css\"
    // +rel=\"stylesheet\" +type=\"text/css\" +name=\"MAINCSS\" +>");
    private static Pattern HEAD_CONTENT_TYPE_PATTERN = Pattern.compile(
        " *< *meta *(http-equiv *= *\"? *content-type *\"?)?(content *= *\"? *text/html *; *charset *= *utf-8 *\"?)? *(http-equiv *= *\"? *content-type *\"?)?(content *= *\"? *text/html *; *charset *= *utf-8 *\"?)? */? *> *",
        Pattern.CASE_INSENSITIVE);

    /**
     * 查询栏目模板内容
     * 
     * @Title: getChannelTemplateContent 
     * @author zhangchengchun
     * @date 2019年1月16日 上午10:34:59 
     * @Description: 查询栏目模板内容
     * @param channelLogicId
     *            栏目逻辑id
     * @param  参数说明 
     * @return String    返回类型  @throws 
     */
    public String getChannelTemplateContent(Integer channelLogicId)
    {
        Channel channel = iChannelService.getByLogicId(channelLogicId);
        if(null==channel) {
            return null;
        }
        String templateid = channel.getChannelModelId();
        Model template = iModelService.getById(templateid);
        if(null==template) {
            return null;
        }
        String filepath = templateDir + makeURL(template.getCreateTime(), templateid);
        String result = FileTool.ReadFile(filepath, "utf-8");
        return result;
    }

    /**
     * 查询栏目的模板中的标记集合
     * 
     * @Title: getChannelTemplateSplits 
     * @author zhangchengchun
     * @date 2019年1月16日 上午10:36:39 
     * @Description: 查询栏目的模板中的标记集合 
     * @param logicId
     *            栏目逻辑id
     * @param @return
     *             参数说明 
     * @return List<ModelSplitMapResponseVO>    返回类型  @throws 
     */
    public List<ModelSplitMapResponseVO> getChannelTemplateSplits(Integer logicId)
    {
        List<ModelSplitMapResponseVO> result = new ArrayList<>();
        String modelContent = getChannelTemplateContent(logicId);
        if (null!=modelContent && modelContent.length() > 0)
        {
            // 处理标记
            Matcher tagMatcher = TAGOLD_PATTERN.matcher(modelContent);

            List<String> tagIdList = new ArrayList<String>();
            while (tagMatcher.find())
            {
                String tagid = tagMatcher.group(1);
                if (tagid != null && tagid.length() > 0)
                {
                    tagIdList.add(tagid);
                }
            }
            if (tagIdList.size() > 0)
            {
                QueryWrapper<ModelSplit> queryWrapper = new QueryWrapper<ModelSplit>();
                queryWrapper.in("logic_id", tagIdList);
                List<ModelSplit> ttagList = iModelSplitService.list(queryWrapper);

                if (ttagList != null && ttagList.size() > 0)
                {
                    for (ModelSplit tTag : ttagList)
                    {
                        TemplateTagParseModel model = new TemplateTagParseModel(tTag.getLogicId(),
                            logicId, null, 0);
                        String replaceStr = templateTagParseViewService.viewChannelNewsOld(model,false);

                        ModelSplitMapResponseVO msm = new ModelSplitMapResponseVO();
                        msm.setModelSplitContent(replaceStr);
                        msm.setModelSplitId(tTag.getId());
                        msm.setModelSplitKey("${TAG_" + tTag.getLogicId() + "_TAG}");
                        msm.setModelSplitLogicId(tTag.getLogicId());
                        msm.setSplitName(tTag.getSplitName());

                        result.add(msm);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 新闻预览内容
     * 
     * @param content
     *            新闻POJO_Content
     * @return 新闻预览内容
     * @throws ContentBussinessException 
     */
    /**
     * 
    * @Title: 预览稿件的模板
    * @author zhangchengchun
    * @date 2019年1月17日 下午1:55:48 
    * @Description: 预览稿件的模板
    * @param contentId 稿件的id
    * @param pageNo 当前页码，可以不传
    * @param info 调试信息，支持值：info|debug
    * @param vtype 预览类型，预览时不传，静态化时传publish
    * @param @throws ContentBussinessException  参数说明 
    * @return String    返回类型 
    * @throws 
     */
    public String getNewsPreview(Integer contentLogicId, Integer page, String info, String vtype) throws ContentBussinessException
    {
        ContentObj contentObj = new ContentObj(contentLogicId);
        
        int tagInfo = 0;// 是否显示标记处理时间信息
        String tagInfoHtml = "";// 标记处理时间信息HTML
        int tagInfoCount = 0;// 标记处理总数
        long tagInfoTimCount = 0;// 标记处理总消耗时间
        Vector<Object[]> tagInfoVector = new Vector<Object[]>();
        if (info != null)
        {
            if (info.equals("info"))
            {
                tagInfo = 1;
            }
            else if (info.equals("debug"))
            {
                tagInfo = 2;
            }
        }
        try
        {
            if (contentObj != null)
            {
                /*
                 * if (contentObj.getDeleted() != null && contentObj.getDeleted() == 1) { return
                 * ""; }
                 */
                if (contentObj.getPageType() != null && contentObj.getPageType() > 0)
                {
                    contentObj.initPage(page);
                }
                
                String templateid = contentObj.getTemplateId();
                
                String filepath = null;
                Channel channelPojo = iChannelService.getByLogicId(contentObj.getNodeId());
                ChannelObj channelParents[] = null;
                if (channelPojo != null)
                {
                    ChannelObj channel = new ChannelObj(channelPojo);
                    LinkedHashMap<Integer, ChannelObj> parents = channel.getParents();
                    if (parents != null && parents.size() > 0)
                    {
                        channelParents = parents.values().toArray(new ChannelObj[parents.size()]);
                    }
                }
                // 模板id为0时使用栏目设定的默认模板
                if (StringUtils.isEmpty(templateid))
                {
                    if (channelPojo != null)
                    {
                        if (!StringUtils.isEmpty(channelPojo.getContentModelId()))
                        {
                            templateid = channelPojo.getContentModelId();
                        }
                        else
                        {
                            if (channelParents != null && channelParents.length > 0)
                            {
                                for (int i = channelParents.length - 1; i >= 0; i-- )
                                {
                                    ChannelObj tempChannel = channelParents[i];
                                    if (!StringUtils.isEmpty(tempChannel.getContentTemplateId()))
                                    {
                                        templateid = tempChannel.getContentTemplateId();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                if (StringUtils.isEmpty(templateid))
                {
                    return "没有模板";
                }
                Model template = iModelService.getById(templateid);
                if (template != null)
                {
                    filepath = templateDir + makeURL(template.getCreateTime(), templateid);
                    File file = new File(filepath);
                    if (!file.exists())
                    {
                        filepath = templateDir + template.getUrl() + "_" + templateid + ".htm";
                    }
                }

                if (filepath != null)
                {
                    String result = FileTool.ReadFile(filepath, "utf-8");
                    String re = getContentNewsOld(contentObj, result, page, contentObj.getNodeId(),
                        info, !"edit".equals(vtype));
                    if("publish".equals(vtype)) {
                        //TODO 静态化，替换链接。
                    }
                    return re;
                }
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return "没有模板";
    }

    /**
     * 栏目预览内容
     * 
     * @param nodeId
     *            栏目id
     * @param pageNo
     *            页面翻页号
     * @param info
     *            是否显示标记处理时间详细信息
     * @return 栏目预览内容
     */
    public String getChannelPreview(Integer nodeId, Integer pageNo, String info,String vtype)
    {
        int tagInfo = 0;// 是否显示标记处理时间信息
        String tagInfoHtml = "";// 标记处理时间信息HTML
        int tagInfoCount = 0;// 标记处理总数
        long tagInfoTimCount = 0;// 标记处理总消耗时间
        Vector<Object[]> tagInfoVector = new Vector<Object[]>();
        if (info != null)
        {
            if (info.equals("info"))
            {
                tagInfo = 1;
            }
            else if (info.equals("debug"))
            {
                tagInfo = 2;
            }
        }
        if (StringUtils.isEmpty(nodeId))
        {
            return "没有模板";
        }
        try
        {
            Channel channel = iChannelService.getByLogicId(nodeId);
            String templateid = channel.getChannelModelId();
            String filepath = null;

            Model template = iModelService.getById(templateid);
            if (null==template)
            {
                return "没有模板";
            }
            filepath = templateDir + makeURL(template.getCreateTime(), templateid);

            System.out.println("======:"+filepath);
            File filenew = new File(filepath);
            if (!filenew.exists())
            {
                String filepathnew = SharedConstant.SYSTEM_ROOT + "/new_template/"
                                     + template.getUrl() + "_" + templateid + "_" + nodeId
                                     + ".htm";
                File file = new File(filepathnew);
                if (file.exists())
                {
                    filepath = filepathnew;
                }
                else
                {
                    filepath = templateDir + template.getUrl() + "_" + template.getId() + ".htm";
                }
            }
            String re = getChannelNewsOld(nodeId, filepath, pageNo, info, "edit".equals(vtype));
            if("publish".equals(vtype)) {
                //TODO 静态化，替换链接。
            }
            return re;
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return "没有模板";

    }

    private String getChannelNewsOld(Integer nodeId, String filepath, Integer pageNo, String info,boolean isEdit)
    {
        int tagInfo = 0;// 是否显示标记处理时间信息
        String tagInfoHtml = "";// 标记处理时间信息HTML
        int tagInfoCount = 0;// 标记处理总数
        long tagInfoTimCount = 0;// 标记处理总消耗时间
        Vector<Object[]> tagInfoVector = new Vector<Object[]>();
        if (info != null)
        {
            if (info.equals("info"))
            {
                tagInfo = 1;
            }
            else if (info.equals("debug"))
            {
                tagInfo = 2;
            }
        }
        try
        {
            if (filepath != null)
            {
                String result = FileTool.ReadFile(filepath, "utf-8");
                if (result.length() > 0)
                {
                    // 处理标记
                    Matcher tagMatcher = TAGOLD_PATTERN.matcher(result);

                    HashMap<String, Object> values = new HashMap<String, Object>();
                    Channel channel = iChannelService.getByLogicId(nodeId);
                    values.put("channel", channel);

                    // StringBuffer buffer = new StringBuffer();
                    List<String> tagIdList = new ArrayList<String>();
                    HashMap<Integer, Split> tagIdMap = new HashMap<Integer, Split>();
                    HashMap<Integer, ModelSplit> templateTagMap = new HashMap<Integer, ModelSplit>();
                    while (tagMatcher.find())
                    {
                        String tagid = tagMatcher.group(1);
                        if (tagid != null && tagid.length() > 0)
                        {
                            tagIdList.add(tagid);
                            tagIdMap.put(Integer.parseInt(tagid), null);
                        }
                    }
                    if (tagIdMap.size() > 0)
                    {
                        QueryWrapper<ModelSplit> queryWrapper = new QueryWrapper<ModelSplit>();
                        queryWrapper.in("logic_id", tagIdList);
                        List<ModelSplit> ttagList = iModelSplitService.list(queryWrapper);

                        if (ttagList != null && ttagList.size() > 0)
                        {
                            for (ModelSplit tTag : ttagList)
                            {
                                Split split = isplitService.getById(tTag.getSplitId());
                                tagIdMap.put(tTag.getLogicId(), split);
                                templateTagMap.put(tTag.getLogicId(), tTag);
                            }
                        }
                        List<ModelSplitProp> templateTagPropList = iModelSpiltPropService.selectHasPage(
                            tagIdList);
                        boolean isMultipage = false;// 是否有分页
                        List<Integer> multiPageTags = new ArrayList<Integer>(); // 含有分页属性的标记
                        if (templateTagPropList != null && templateTagPropList.size() > 0)
                        {
                            isMultipage = true;
                            for (ModelSplitProp templateTagProp : templateTagPropList)
                            {
                                multiPageTags.add(templateTagProp.getModelSplitLogicId());
                            }
                        }
                        ChannelTagValueRedis redis = new ChannelTagValueRedis();
                        for (Entry<Integer, Split> entry : tagIdMap.entrySet())
                        {
                            int tagid = entry.getKey();
                            Split tag = entry.getValue();
                            if (tag != null)
                            {
                                long startTime = System.currentTimeMillis();
                                String replaceStr = "";
                                TemplateTagParseModel model = new TemplateTagParseModel(tagid,
                                    nodeId, null, pageNo);

                                if (isMultipage && SharedConstant.REDIS)
                                {
                                    String strNodeId = StringTool.getNotNullString(nodeId);
                                    String strTT_ID = StringTool.getNotNullString(tagid);
                                    if (!multiPageTags.contains(tagid))
                                    {
                                        if (pageNo > 1)
                                        {
                                            replaceStr = redis.get(strNodeId, strTT_ID);
                                            if (replaceStr == null || replaceStr.length() <= 0)
                                            {
                                                replaceStr = templateTagParseViewService.viewChannelNewsOld(
                                                    model, isEdit);
                                                redis.set(strNodeId, strTT_ID, replaceStr);
                                            }
                                        }
                                        else
                                        {
                                            replaceStr = templateTagParseViewService.viewChannelNewsOld(
                                                model, isEdit);
                                            redis.set(strNodeId, strTT_ID, replaceStr);
                                        }
                                    }
                                    else
                                    {
                                        replaceStr = templateTagParseViewService.viewChannelNewsOld(
                                            model, isEdit);
                                    }
                                }
                                else
                                {
                                    replaceStr = templateTagParseViewService.viewChannelNewsOld(
                                        model, isEdit);
                                }
                                result = Regular.ReplaceTag(String.valueOf(tagid), replaceStr,
                                    result);

                                ModelSplit tTag = templateTagMap.get(tagid);
                                long tempTagTime = System.currentTimeMillis() - startTime;
                                if (tagInfo > 0)
                                {
                                    tagInfoCount++ ;
                                    tagInfoTimCount += tempTagTime;
                                    if (tagInfo == 2)
                                    {
                                        String timeHtml = tempTagTime + "";
                                        if (tempTagTime > 5000)
                                        {
                                            timeHtml = "<font color='red'>" + timeHtml + "</font>";
                                        }
                                        // tagInfoHtml+="标记名："+tTag.getTagName()+" 标记ID："+tagid+"
                                        // 处理时间(毫秒)："+timeHtml+"<br>";
                                        tagInfoVector.add(new Object[] {tempTagTime,
                                            "  标记ID：" + tagid + "  处理时间(毫秒)：" + timeHtml
                                                                                     + "<br>\n"});
                                    }
                                }
                                else
                                {
                                    long tagLog = SharedConstant.TAGLOG;
                                    if (tagLog != -1 && tempTagTime > tagLog)
                                    {
                                        String templateid = tTag.getModelId();
                                        Date date = DateUtil.getNowDate();
                                        String nowDateStr = DateUtil.Date2Str(date, "yyyy-MM-dd");
                                        String logDate = DateUtil.Date2Str(date,
                                            "yyyy-MM-dd HH:mm:ss");
                                        String log = "NODE_ID=" + nodeId + ",TT_ID=" + tagid
                                                     + ",TIME=" + tempTagTime + ",LOG_DATE="
                                                     + logDate + ",TEMPLATE_ID=" + templateid + ","
                                                     + "\r\n";
                                        String tagLogDirPath = System.getProperty("user.dir")
                                                               + "/logs/logstag";
                                        String logPath = tagLogDirPath + "/" + nowDateStr
                                                         + "_TAG.log";
                                        FileTool.write(logPath, log);
                                    }
                                }
                            }
                        }
                        if (tagInfo > 0)
                        {
                            Collections.sort(tagInfoVector, new Comparator<Object[]>()
                            {
                                public int compare(Object[] o1, Object[] o2)
                                {
                                    return (int)(((Long)o1[0]) - ((Long)o2[0]));
                                }
                            });
                            for (Iterator<Object[]> iterator = tagInfoVector.iterator(); iterator.hasNext();)
                            {
                                tagInfoHtml += iterator.next()[1];
                            }
                            tagInfoHtml += "预览共用时："
                                           + tagInfoTimCount + "(毫秒)   共有标记：" + tagInfoCount
                                           + "  标记平均耗时：" + (tagInfoCount > 0 ? tagInfoTimCount
                                                                               / tagInfoCount : 0)
                                           + "(毫秒)";
                        }

                    }
                }
                return (tagInfo > 0 ? tagInfoHtml : "") + result;
            }

        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return "没有模板";
    }

    private String getContentNewsOld(ContentObj content, String result, int page,
                                     Integer controlNodeId, String info, boolean isEdit)
    {
        int tagInfo = 0;// 是否显示标记处理时间信息
        String tagInfoHtml = "";// 标记处理时间信息HTML
        int tagInfoCount = 0;// 标记处理总数
        long tagInfoTimCount = 0;// 标记处理总消耗时间
        Vector<Object[]> tagInfoVector = new Vector<Object[]>();
        if (info != null)
        {
            if (info.equals("info"))
            {
                tagInfo = 1;
            }
            else if (info.equals("debug"))
            {
                tagInfo = 2;
            }
        }
        try
        {
            if (result.length() > 0)
            {
                Vector v = Regular.MatchNewsTagList(result);
                if (v != null) for (int i = 0; i < v.size(); i++ )
                {
                    String s = (String)v.get(i);
                    long startTime = System.currentTimeMillis();
                    String replaceStr = "";
                    if (SharedConstant.REDIS)
                    {
                        String key = s + ",";
                        if (SharedConstant.CONTENT_TAG_NAME.contains(key))
                        {
                            replaceStr = templateTagParseViewService.viewRedisContentNewsOld(
                                content, s);
                        }
                        else
                        {
                            replaceStr = templateTagParseViewService.viewContentNewsOld(content,
                                s);
                        }
                    }
                    else
                    {
                        replaceStr = templateTagParseViewService.viewContentNewsOld(content, s);
                    }
                    result = Regular.ReplaceNewsTag(s, replaceStr, result);
                    if (tagInfo > 0)
                    {
                        tagInfoCount++ ;
                        long tempTagTime = System.currentTimeMillis() - startTime;
                        tagInfoTimCount += tempTagTime;
                        if (tagInfo == 2)
                        {
                            String timeHtml = tempTagTime + "";
                            if (tempTagTime > 5000)
                            {
                                timeHtml = "<font color='red'>" + timeHtml + "</font>";
                            }
                            tagInfoVector.add(new Object[] {tempTagTime,
                                "${标记名}：" + s + "  处理时间(毫秒)：" + timeHtml + "<br>\n"});
                        }
                    }
                }
                // 处理标记// 替换"{TAG_id_TAG}"类的标记
                Matcher tagMatcher = TAGOLD_PATTERN.matcher(result);
                List<String> tagIdList = new ArrayList<String>();
                HashMap<Integer, Split> tagIdMap = new HashMap<Integer, Split>();
                HashMap<Integer, ModelSplit> templateTagMap = new HashMap<Integer, ModelSplit>();
                while (tagMatcher.find())
                {
                    String tagid = tagMatcher.group(1);
                    if (tagid != null && tagid.length() > 0)
                    {
                        tagIdList.add(tagid);
                        tagIdMap.put(Integer.parseInt(tagid), null);
                    }
                }
                if (tagIdMap.size() > 0)
                {
                    QueryWrapper<ModelSplit> queryWrapper = new QueryWrapper<ModelSplit>();
                    queryWrapper.in("logic_id", tagIdList);
                    List<ModelSplit> ttagList = iModelSplitService.list(queryWrapper);
                    if (ttagList != null && ttagList.size() > 0)
                    {
                        for (ModelSplit tTag : ttagList)
                        {
                            Split split = isplitService.getById(tTag.getSplitId());
                            tagIdMap.put(tTag.getLogicId(), split);
                            templateTagMap.put(tTag.getLogicId(), tTag);
                        }
                    }
                    for (Entry<Integer, Split> entry : tagIdMap.entrySet())
                    {
                        int tagid = entry.getKey();
                        Split tag = entry.getValue();
                        long startTime = System.currentTimeMillis();
                        String replaceStr = "";
                        if (tag == null)
                        {
                            result = Regular.ReplaceTag(String.valueOf(tagid), "", result);
                            continue;
                        }
                        TemplateTagParseModel model = new TemplateTagParseModel(tagid,
                            StringUtils.isEmpty(
                                controlNodeId) ? content.getNodeId() : controlNodeId,
                            content.getContentId(), page, content);

                        if (tag.getClassName().contains("ContentPageTag"))
                        {
                            replaceStr = templateTagParseViewService.viewChannelNewsOld(model, isEdit);
                        }
                        else
                        {
                            replaceStr = templateTagParseViewService.viewRedisChannelNewsOld(
                                model, isEdit);
                        }
                        result = Regular.ReplaceTag(String.valueOf(tagid), replaceStr, result);
                        long tempTagTime = System.currentTimeMillis() - startTime;
                        ModelSplit tTag = templateTagMap.get(tagid);
                        if (tagInfo > 0)
                        {
                            tagInfoCount++ ;
                            tagInfoTimCount += tempTagTime;
                            if (tagInfo == 2)
                            {
                                String timeHtml = tempTagTime + "";
                                if (tempTagTime > 5000)
                                {
                                    timeHtml = "<font color='red'>" + timeHtml + "</font>";
                                }
                                tagInfoVector.add(new Object[] {tempTagTime,
                                    "标记名：" + tag.getSplitName() + "  标记ID：" + tagid + "  处理时间(毫秒)："
                                                                             + timeHtml
                                                                             + "<br>\n"});
                            }
                        }
                        else
                        {
                            long tagLog = SharedConstant.TAGLOG;
                            Integer nodeId = controlNodeId > 0 ? controlNodeId : content.getNodeId();
                            if (tagLog != -1 && tempTagTime > tagLog)
                            {
                                String templateid = tTag.getModelId();

                                Date date = DateUtil.getNowDate();
                                String nowDateStr = DateUtil.Date2Str(date, "yyyy-MM-dd");
                                String logDate = DateUtil.Date2Str(date, "yyyy-MM-dd HH:mm:ss");
                                String log = "NODE_ID=" + nodeId + ",TT_ID=" + tagid + ",TIME="
                                             + tempTagTime + ",LOG_DATE=" + logDate
                                             + ",TEMPLATE_ID=" + templateid + "\r\n";
                                String tagLogDirPath = System.getProperty("user.dir")
                                                       + "/logs/logstag";
                                String logPath = tagLogDirPath + "/" + nowDateStr + "_TAG.log";
                                FileTool.write(logPath, log);
                            }
                        }
                    }
                }
            }
            if (tagInfo > 0)
            {
                Collections.sort(tagInfoVector, new Comparator<Object[]>()
                {
                    public int compare(Object[] o1, Object[] o2)
                    {
                        return (int)(((Long)o1[0]) - ((Long)o2[0]));
                    }
                });
                for (Iterator<Object[]> iterator = tagInfoVector.iterator(); iterator.hasNext();)
                {
                    tagInfoHtml += iterator.next()[1];
                }
                tagInfoHtml += "预览共用时：" + tagInfoTimCount + "(毫秒)   共有标记：" + tagInfoCount
                               + "  标记平均耗时："
                               + (tagInfoCount > 0 ? tagInfoTimCount / tagInfoCount : 0) + "(毫秒)";
            }
            return (tagInfo > 0 ? tagInfoHtml : "") + result;
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    // 根据时间计算模板URL
    private String makeURL(Date date, String templateid)
    {
        if (date == null)
        {
            return "";
        }
        else
        {
            return format.format(date) + "/" + templateid + ".html";
        }
    }
}
