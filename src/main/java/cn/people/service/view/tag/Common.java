package cn.people.service.view.tag;


import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.people.commons.exceptions.ContentBussinessException;
import cn.people.controller.vo.ContentDelResultVO;
import cn.people.controller.vo.ContentDetailRequestVO;
import cn.people.controller.vo.ContentDetailVO;
import cn.people.controller.vo.ContentViewRequestVO;
import cn.people.entity.Channel;
import cn.people.entity.Content;
import cn.people.entity.ContentChannel;
import cn.people.entity.ContentMedia;
import cn.people.entity.ModelSplit;
import cn.people.entity.Split;
import cn.people.entity.SplitContent;
import cn.people.service.ISplitContentService;
import cn.people.service.view.obj.AutoNewsList;
import cn.people.service.view.obj.ChannelObj;
import cn.people.service.view.obj.ContentChannelNameValueRedis;
import cn.people.service.view.obj.ContentObj;
import cn.people.service.view.obj.Converter;
import cn.people.service.view.obj.DateUtil;
import cn.people.service.view.obj.Encode;
import cn.people.service.view.obj.Escape;
import cn.people.service.view.obj.HTML2TextNew;
import cn.people.service.view.obj.HtmlToText;
import cn.people.service.view.obj.TitlePropVO;
import cn.people.service.view.obj.PageModel;
import cn.people.service.view.obj.Regular;
import cn.people.service.view.obj.SharedConstant;
import cn.people.service.view.obj.StringTool;
import cn.people.service.view.obj.SplitContentComparator;


public class Common extends AbstractBaseTag
{

    private static final Logger log = LoggerFactory.getLogger(Common.class);

    public int _maxOutput = 10;

    public int _outputCount = 0;

    public String _queue = "1234567";

    public int _PageCount = 50;

    public int _PageNo = 0;

    public int _itemStart = 0;

    public int _itemEnd = 0;

    public int _itemTotal = 0;

    public String p_name_tail = "…";

    private String _notListNodeSet = "";

    private String _notListContentSet = "";

    private String _ContentFieldList = "CONTENT_ID,TITLE,RELEASE_DATE,image_count,summary,INPUT_DATE";

    private boolean isMongol = false;

    private long oldNodeId = -1;

    private Integer limit = 10;

    private static String SYSTEM_NAME = SharedConstant.SYSTEM_NAME;

    private boolean isAbsolutePath = false;

    public Integer getLimit()
    {
        return limit;
    }

    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }

    private Channel channel = null;

    public Channel getChannel()
    {
        return channel;
    }

    public void setChannel(Channel channel)
    {
        this.channel = channel;
    }

    public HashMap<String, Object> view()
    {
        // 初始化栏目

        this.channel = initChannel(nodeId);

        if (!this.GetParam("综合_输出顺序").equals(""))
        {
            this._queue = this.GetParam("综合_输出顺序");
        }

        if (!GetParam("综合_输出条数").equals(""))
        {
            this._maxOutput = Integer.parseInt(GetParam("综合_输出条数"));
            if (!GetParam("综合_输出条数_模板控制").equals(""))
            {
                this._maxOutput = Integer.parseInt(GetParam("综合_输出条数_模板控制"));
            }
        }

        this._PageNo = Integer.parseInt(String.valueOf(page));
        if (this._PageNo <= 0)
        {
            this._PageNo = 1;
        }

        this._PageCount = this._maxOutput;

        if (this.GetParam("省略符号").length() > 0)
        {
            this.p_name_tail = GetParam("省略符号");
        }
        if (this.GetParam("使用绝对地址").equals("1"))
        {
            isAbsolutePath = true;
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        StringBuffer sBuff = new StringBuffer();
        for (int i = 0; i < this._queue.length(); i++ )
        {
            // 1:块名称、2:手选内容、3:自动栏目、4:自动新闻、5:更多、6:当前节点信息
            String s = _queue.substring(i, i + 1);
            if (s.equals("1"))
            {
                sBuff.append(this.outputHeader());
            }
            else if (s.equals("2"))
            {
                sBuff.append(this.outputManual());
            }
            else if (s.equals("t"))
            {
                sBuff.append(this.outputManual_tv());
            }
            else if (s.equals("3"))
            {
                sBuff.append(this.outputAutoNodes());
            }
            else if (s.equals("4"))
            {
                sBuff.append(this.outputAutoNews());
            }
            else if (s.equals("5"))
            {
                sBuff.append(this.outputMore());
            }
            else if (s.equals("6"))
            {
                sBuff.append(this.outputCurNodeInfo());
            }
            else if (s.equals("7"))
            {
                sBuff.append(this.outputAutoPicture());
            }
            else if (s.equals("8"))
            {
                sBuff.append(this.outputAutoNodeNews());
            }
            else if (s.equals("9"))
            {
                sBuff.append(this.outputAutoPicture2());
                // else if (s.equals("10") ) sBuff.append(this.outputAutosports());
            }
            else if (s.equals("10"))
            {
                sBuff.append(this.outputManual_index());
            }
            else if (s.equals("k"))
            {
                // 生成关键字新闻列表
                sBuff.append(this.outputKeywordNews());
            }
            else if (s.equals("h"))
            {
                // 通过标记属性控制html代码
                sBuff.append(this.outHtml());
            }
            else if (s.equals("s"))
            {
                // 城市网专家专栏文本层手控内容 专家内容+其发表文字数量（根据nodeId统计）
                sBuff.append(this.outputManualAuto());
            }
        }
        if (sBuff.length() > 0)
        {
            sBuff.insert(0, this.GetParam("综合_头html代码"));
            sBuff.append(this.GetParam("综合_尾html代码"));
            /*
             * if(this.GetParam("xml是否转json").equals("1")){ SAXJsonParser sax = new
             * SAXJsonParser(); sBuff = new StringBuffer(sax.xmltoJson(sBuff.toString())); }
             */
        }
        result.put("tagvalues", sBuff.toString());
        return result;
    }

    /**
     * 显示块头
     * 
     * @return 返回块名称的输出内容
     */
    public String outputHeader()
    {
        String res = "";
        if (!GetParam("块_名称").equals(""))
        {
            res += GetParam("块_头html代码");
            res += GetParam("块_名称");
            res += GetParam("块_尾html代码") + "\n";
        }
        return res;
    }

    public String outputManual()
    {
        StringBuffer s = new StringBuffer();
        // 取得标记的内容
        if (!this.GetParam("无效是否显示").equals(""))
        {
            List itemlist = this.getTagValueList_index();
            // 如果有内容或需要设自动新闻或自动显示节点
            if (itemlist != null)
            {
                if (itemlist.size() > 0 && this._maxOutput > 0)
                {
                    // 输出内容
                    int i_count = 0;
                    // 初试化输出列数
                    int i_MultiColumn = 1;
                    if (!this.GetParam("手选内容_多列输出_列数").equals(""))
                    {
                        int i_t1 = Integer.parseInt(GetParam("手选内容_多列输出_列数"));
                        if (i_t1 > 0)
                        {
                            i_MultiColumn = i_t1;
                        }
                    }
                    // 行间隔
                    int inv = 0;
                    if (!GetParam("新闻_输出间隔").equals(""))
                    {
                        inv = Integer.parseInt(GetParam("新闻_输出间隔"));
                    }
                    int count_row = 0;// 输出行数
                    // 显示标记内容
                    for (int i = 0; i < itemlist.size(); i += i_MultiColumn)
                    {
                        // 多列输出中，处理行头
                        if (i_MultiColumn > 1)
                        {
                            s.append(this.GetParam("手选内容_多列输出_行头html代码"));
                        }
                        for (int j = 0; j < i_MultiColumn; j++ )
                        {
                            if (j != 0)
                            {
                                // 多列输出中，处理列间代码
                                s.append(GetParam("手选内容_多列输出_列间html代码"));
                            }
                            if (i_count < itemlist.size())
                            {
                                s.append(
                                    this.showManual((TitlePropVO)itemlist.get(i_count)));
                            }
                            else
                            {
                                // 多列输出中，处理剩余最后一行后面没有内容部分
                                if (j != 0)
                                {
                                    // s.append("&nbsp");
                                    s.append("");
                                }
                            }
                            i_count++ ;
                        }
                        // 多列输出中，处理行尾
                        if (i_MultiColumn > 1)
                        {
                            s.append(this.GetParam("手选内容_多列输出_行尾html代码") + "\n");
                        }
                        // 处理隔行
                        if (inv > 0)
                        {
                            count_row++ ;
                            if (count_row == inv && (itemlist.size() - 1) != (i + i_MultiColumn)
                                && i < (itemlist.size() - 1))
                            {
                                count_row = 0;
                                s.append(GetParam("新闻_输出间隔html代码").equals("") ? "<br>" : GetParam(
                                    "新闻_输出间隔html代码"));
                            }
                        }
                    }
                    this._outputCount = itemlist.size();
                }
            }
            if (s.length() > 0)
            {
                s.insert(0, this.GetParam("手选内容_头html代码"));
                s.append(this.GetParam("手选内容_尾html代码"));
            }
        }
        else
        {
            List itemlist = null;
            String spc = "";
            if (this.GetParam("手选内容_是否显示翻页控制栏").trim().length() > 0)
            {
                itemlist = this.getTagValueListPage();
            }
            else
            {
                itemlist = this.getTagValueList();
            }

            // 如果有内容或需要设自动新闻或自动显示节点
            if (itemlist != null)
            {
                if (itemlist.size() > 0 && this._maxOutput > 0)
                {
                    if (this.GetParam("手选内容_是否显示翻页控制栏").trim().length() > 0)
                    {
                        spc = this.showPageController(nodeId, false);
                    }
                    // 输出内容
                    int i_count = 0;
                    // 初试化输出列数
                    int i_MultiColumn = 1;
                    if (!this.GetParam("手选内容_多列输出_列数").equals(""))
                    {
                        int i_t1 = Integer.parseInt(GetParam("手选内容_多列输出_列数"));
                        if (i_t1 > 0)
                        {
                            i_MultiColumn = i_t1;
                        }
                    }
                    // 行间隔
                    int inv = 0;
                    if (!GetParam("新闻_输出间隔").equals(""))
                    {
                        inv = Integer.parseInt(GetParam("新闻_输出间隔"));
                    }
                    int count_row = 0;// 输出行数
                    // 显示标记内容
                    for (int i = 0; i < itemlist.size(); i += i_MultiColumn)
                    {
                        // 多列输出中，处理行头
                        if (i_MultiColumn > 1)
                        {
                            s.append(this.GetParam("手选内容_多列输出_行头html代码"));
                        }

                        for (int j = 0; j < i_MultiColumn; j++ )
                        {
                            if (j != 0)
                            {
                                // 多列输出中，处理列间代码
                                s.append(GetParam("手选内容_多列输出_列间html代码"));
                            }
                            if (i_count < itemlist.size())
                            {
                                s.append(
                                    this.showManual((TitlePropVO)itemlist.get(i_count)));
                            }
                            else
                            {
                                // 多列输出中，处理剩余最后一行后面没有内容部分
                                if (j != 0)
                                {
                                    // s.append("&nbsp");
                                    s.append("");
                                }
                            }
                            i_count++ ;
                        }
                        // 多列输出中，处理行尾
                        if (i_MultiColumn > 1)
                        {
                            s.append(this.GetParam("手选内容_多列输出_行尾html代码") + "\n");
                        }
                        // 处理隔行
                        if (inv > 0)
                        {
                            count_row++ ;
                            if (count_row == inv && (itemlist.size() - 1) != (i + i_MultiColumn)
                                && i < (itemlist.size() - 1))
                            {
                                count_row = 0;
                                s.append(GetParam("新闻_输出间隔html代码").equals("") ? "<br>" : GetParam(
                                    "新闻_输出间隔html代码"));
                            }
                        }
                    }

                    this._outputCount = itemlist.size();
                }
            }
            if (s.length() > 0)
            {
                s.insert(0, this.GetParam("手选内容_头html代码"));
                s.append(this.GetParam("手选内容_尾html代码"));
                if (spc.length() > 0)
                {
                    if (this.GetParam("手选内容_是否显示翻页控制栏").equals("1")
                        || this.GetParam("手选内容_是否显示翻页控制栏").equals("3"))
                    {
                        // 输出翻页信息
                        s.insert(0, spc);
                    }
                    if (this.GetParam("手选内容_是否显示翻页控制栏").equals("2")
                        || this.GetParam("手选内容_是否显示翻页控制栏").equals("3"))
                    {
                        // 输出翻页信息
                        s.append(spc);
                    }
                }
            }
        }
        if (this.GetParam("手选内容_是否输出编码").equals("1"))
        {
            return Escape.escape(s.toString());
        }
        return s.toString();

    }

    protected String showManual(TitlePropVO curitem)
    {
        String res = "";
        Integer itemType = curitem.getItemType();
        if (itemType.equals(SplitContent.DTYPE_CONTENT))
        {
            // 内容的是新闻
            String itemNews = this.showItemNews(curitem, false);

            if (!this.GetParam("新闻前是否显示节点").equals(""))
            {
                if (this.GetParam("新闻前是否显示节点").equals("2"))
                {
                    res += GetParam("新闻_行头html代码") + this.showItemNewsChannel(curitem) + itemNews
                           + GetParam("新闻_行尾html代码") + "\n";
                }
                else
                {
                    res += GetParam("新闻_行头html代码") + this.showItemNewsNode(curitem) + itemNews
                           + GetParam("新闻_行尾html代码") + "\n";
                }
            }
            else
            {
                res += GetParam("新闻_行头html代码") + itemNews + GetParam("新闻_行尾html代码") + "\n";
            }
            this._maxOutput-- ;
        }
//        else if (itemType.equals("2"))
//        {
//            // 内容是栏目
//            res += GetParam("节点_行头html代码") + this.showItemNode(curitem) + GetParam("节点_行尾html代码")
//                   + "\n";
//            // res += GetParam("节点_行头html代码") + this.showItemNodeLink(curitem) +
//            // GetParam("节点_行尾html代码") + "\n";
//            this._maxOutput-- ;
//        }
        else if (itemType.equals(SplitContent.DTYPE_PHOTO))
        {
            // 内容是图片
            res += this.GetParam("图片_行头html代码") + this.showItemPicture(curitem)
                   + this.GetParam("图片_行尾html代码") + "\n";
            this._maxOutput-- ;
        }
        else if (itemType.equals(SplitContent.DTYPE_WORD))
        {
            // 内容是文字
            res += this.GetParam("文字_行头html代码") + this.showItemText(curitem)
                   + this.GetParam("文字_行尾html代码");
            this._maxOutput-- ;
        }
        return res;
    }

    protected String showItemNews(TitlePropVO curitem)
    {
        return showItemNews(curitem, false);
    }

    /**
     * 显示一条新闻
     * 
     * @param curitem
     *            标记内容的一行 split为false是不处理标题截断
     * @return 返回新闻的结果
     */
    protected String showItemNews(TitlePropVO curitem, boolean split)
    {
        int news_length = 500;
        String p_name_tail = "…";
        int p_name_tail_length = 1;
        if (this.GetParam("新闻_新闻标题长度").trim().length() > 0)
        {
            news_length = Integer.parseInt(this.GetParam("新闻_新闻标题长度").trim());
        }
        String name = null;

        String pname = curitem.getTitle();
        if (this.GetParam("显示标题类型").equals("1") && curitem.getExt1() != null)
        {
            if (!curitem.getExt1().equals(""))
            {
                pname = curitem.getExt1();
            }
        }
        if (this.GetParam("显示标题类型").equals("2") && curitem.getExt2() != null)
        {
            if (!curitem.getExt2().equals(""))
            {
                pname = curitem.getExt2();
            }
        }
        if (this.GetParam("显示标题类型").equals("3") && curitem.getExt3() != null)
        {
            if (!curitem.getExt3().equals(""))
            {
                pname = curitem.getExt3();
            }
        }
        if (this.GetParam("显示标题类型").equals("4") && curitem.getExt4() != null)
        {
            if (!curitem.getExt4().equals(""))
            {
                pname = curitem.getExt4();
            }
        }
        pname = pname.replace("&$", "");

        String pname_temp = curitem.getTitle();
        pname_temp = pname_temp.replace("&$", "");
        HtmlToText html = new HtmlToText();
        pname_temp = html.html2Text(pname_temp);

        String content_id = curitem.getContentId();
        Integer contentLogicId = curitem.getContentLogicId();
        ContentDetailVO content = iContentService.getContentById(content_id);

        if (split == false)
        {
            if (this.GetParam("是否外文版专用").equalsIgnoreCase("meng"))
            {
                if (news_length > 0 && news_length < pname.length())
                {
                    StringBuffer tempresult = new StringBuffer();
                    String temparr[] = pname.split("\n");
                    int i = temparr.length - 1;
                    while (tempresult.length() < news_length && i >= 0)
                    {
                        char tempchar[] = temparr[i].toCharArray();
                        int j = 0;
                        String templine = "";
                        while (tempresult.length() + templine.length() < news_length
                               && j < tempchar.length)
                        {
                            templine += tempchar[j];
                            j++ ;
                            if (tempresult.length() + templine.length() >= news_length)
                                templine += this.p_name_tail;
                        }
                        if (i == temparr.length - 1)
                            tempresult.insert(0, templine);
                        else
                            tempresult.insert(0, templine + "\n");
                        i-- ;
                    }
                    name = tempresult.toString();
                }
                else
                {
                    name = pname;
                }
            }
            else
            {
                if (news_length > 0 && news_length < pname_temp.length())
                {
                    if (news_length > p_name_tail_length)
                    {
                        if (this.GetParam("新闻_新闻标题长度_是否半角特殊处理").equals("1"))
                        {
                            name = subStringName(pname_temp, news_length - p_name_tail_length)
                                   + this.p_name_tail;
                        }
                        else
                        {
                            name = retrieveNoStyleName(pname_temp,
                                news_length - p_name_tail_length) + this.p_name_tail;
                        }
                    }
                    else
                    {
                        name = retrieveNoStyleName(pname_temp, news_length);
                    }
                }
                else
                {
                    name = pname_temp;
                }
                name = pname.replace(pname_temp, name);
            }

        }
        else
        {
            name = pname;
        }
        name = "&$" + name + "&$";
        // retrieveNoStyleName(curitem.getColumn("title").
        // asString(),news_length) +
        // 标题
        StringBuffer sTitle = new StringBuffer();
        String picwen = GetParam("新闻_标题_附图文字");
        String pic = "";
        // 显示标题、附图、日期

        if (!picwen.equals(""))
        {
            Integer mediaCount = curitem.getMediaCount();
            if (mediaCount != null)
            {
                if (mediaCount > 0)
                {
                    pic = picwen;
                }
                else
                {
                    if (content != null)
                    {
                        mediaCount = content.getMediaCount();
                        if (mediaCount > 0)
                        {
                            pic = picwen;
                        }
                    }
                }
            }
        }

        // 标题前加作者
        String authorhtml = this.GetParam("新闻_标题_作者");
        int authorLimit = Converter.str2Int(GetParam("新闻_标题_作者_字数限制"));
        if (authorhtml.length() > 0)
        {
            if (content != null)
            {
                String author = content.getAuthor();
                if (author != null && author.length() > 0)
                {
                    if (authorLimit > 1)
                    {
                        if (author.length() > authorLimit)
                        {
                            author = author.substring(0, authorLimit - 1) + "...";
                        }
                    }
                    if (authorhtml.indexOf("${STARTAUTHOR}") >= 0)
                    {
                        authorhtml = authorhtml.replaceAll("\\$\\{STARTAUTHOR\\}", author);
                        name = authorhtml + name;
                    }
                    else if (authorhtml.indexOf("${ENDAUTHOR}") >= 0)
                    {
                        authorhtml = authorhtml.replaceAll("\\$\\{ENDAUTHOR\\}", author);
                        name = name + authorhtml;
                    }
                    else
                    {
                        name = author + authorhtml + name;
                    }
                }
            }

        }

        if (this.GetParam("是否外文版专用").equals("Foreign"))
        {
            if (this.GetParam("新闻_标题_链接鼠标提示").equals("1"))
            {
                sTitle.append(this.GetContentLink_Foreign(contentLogicId, name + pic,
                    GetParam("新闻_标题_链接属性"), pname));
            }
            else
            {
                sTitle.append(this.GetContentLink_Foreign(contentLogicId, name + pic,
                    GetParam("新闻_标题_链接属性")));
            }
        }
        else if (isMongol)
        {
            if (this.GetParam("新闻_标题_链接鼠标提示").equals("1"))
            {
                sTitle.append(this.GetContentMongolLink(contentLogicId, name + pic,
                    GetParam("新闻_标题_链接属性"), pname));
            }
            else
            {
                sTitle.append(this.GetContentLink_Foreign(contentLogicId, name + pic,
                    GetParam("新闻_标题_链接属性")));
            }
        }
        else
        {
            // 新闻_标题_链接鼠标提示

            if (this.GetParam("新闻_标题_链接鼠标提示").equals("1"))
            {
                sTitle.append(this.GetContentLink(contentLogicId, name + pic,
                    GetParam("新闻_标题_链接属性"), pname));
            }
            else
            {
                sTitle.append(this.GetContentLink(contentLogicId, name + pic,
                    GetParam("新闻_标题_链接属性")));
            }
        }

        // 新闻_标题_日期格式
        if (!this.GetParam("新闻_标题_日期格式").equals(""))
        {
            // 新闻_标题日期格式非空表示需要显示日期
            if (this.GetParam("新闻_发布时间_是否显示").equals("1"))
            {
                sTitle.append(" " + GetDateStr(GetParam("新闻_标题_日期格式"),
                    DateUtil.asDate(DateUtil.dateToTimestamp(content.getSendtime()))));
            }
            else
            {
                sTitle.append(" " + GetDateStr(GetParam("新闻_标题_日期格式"),
                    DateUtil.asDate(curitem.getInputDate())));
            }
        }

        // 新闻_标题_附加代码
        if (!this.GetParam("新闻_标题_附加代码").equals(""))
        {
            String headhtml = GetParam("新闻_标题_附加代码");
            if (headhtml.indexOf("${STARS}") >= 0)
            {
                long clickCount = getViewCount(String.valueOf(content_id));
                int stars = 0;
                if (clickCount > 500)
                {
                    stars = 5;
                }
                else if (clickCount > 200)
                {
                    stars = 4;
                }
                else if (clickCount > 100)
                {
                    stars = 3;
                }
                else if (clickCount > 50)
                {
                    stars = 2;
                }
                else
                {
                    stars = 1;
                }
                String starhtml = "";
                for (int i = 0; i < 5; i++ )
                {
                    if (i < stars)
                    {
                        starhtml += "<img src=\"/img/zhymt/star1.gif\" width=\"14\" height=\"13\" />";
                    }
                    else
                    {
                        starhtml += "<img src=\"/img/zhymt/star2.gif\" width=\"14\" height=\"13\" />";
                    }
                }
                headhtml = headhtml.replaceAll("\\$\\{STARS\\}", starhtml);
                sTitle.append(headhtml);
            }
            else if (headhtml.indexOf("${CLICK}") >= 0)
            {
                long clickCount = getViewCount(String.valueOf(content_id));
                headhtml = headhtml.replaceAll("\\$\\{CLICK\\}", clickCount + "");
                sTitle.append(headhtml);
            }
            else
            {
                sTitle.append(GetParam("新闻_标题_附加代码"));
            }
        }
        // 标题
        StringBuffer sSummary = new StringBuffer();
        StringBuffer authorStr = new StringBuffer();
        StringBuffer originStr = new StringBuffer();
        StringBuffer inputDateStr = new StringBuffer();

        // 处理摘要
        if (!this.GetParam("新闻_摘要_是否显示").equals(""))
        {
            String itemType = StringTool.getString(curitem.getItemType());
            String summary = curitem.getSummary();
            if (summary != null)
            {
                summary = summary.replace("&$&$", "");
            }

            int summarycount = -1;
            try
            {
                summarycount = Integer.parseInt(this.GetParam("新闻_摘要_字数"));
                if (summarycount < 0)
                {
                    summarycount = 50;
                }
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
            if (!StringUtils.isEmpty(content_id) && (summary == null || summary.length() == 0))
            {

                // 手动新闻不从正文取摘要
                if (itemType == null || itemType.length() == 0)
                {
                    summary = content.getDescription();
                    summary = HTML2TextNew.convert(Encode.htmlsp(summary)).trim();
                    if (summarycount > 0)
                    {
                        summary = summary.substring(0,
                            summarycount < summary.length() ? summarycount : summary.length());
                    }
                }
            }

            if (summary != null && summary.length() > 0)
            {
                if (GetParam("显示省略符").equals("1"))
                {
                    summary = summary + p_name_tail;
                }
                if (!this.GetParam("新闻_摘要_不添加空格").equals("1"))
                {
                    if (!summary.substring(0, 1).equals(" "))
                    {
                        summary = "    " + summary;
                    }
                }
                sSummary.append(GetParam("新闻_摘要_行头html代码"));
                // 显示摘要
                if (!this.GetParam("新闻_摘要_是否有链接").equals(""))
                {
                    sSummary.append(this.GetContentLink(contentLogicId, summary,
                        GetParam("新闻_摘要_链接属性")));
                }
                else if (this.GetParam("是否外文版专用").equals("Foreign"))
                { // 外文版专用
                  // 对外文版摘要的特殊处理,去掉对空格&nbsp;的替换处理 htmlsp_nospace()
                    sSummary.append(Encode.htmlsp_nospace(summary));
                }
                else
                {
                    sSummary.append(Encode.htmlsp(summary));
                }
                // 显示摘要的“详细内容”
                if (!this.GetParam("新闻_摘要_详细内容").equals(""))
                {
                    sSummary.append(this.GetContentLink(contentLogicId,
                        this.GetParam("新闻_摘要_详细内容"), GetParam("新闻_摘要_链接属性")));
                }
                sSummary.append(GetParam("新闻_摘要_行尾html代码"));
            }
            else if (this.GetParam("新闻_摘要_是否显示").equals("1")
                     && this.GetParam("新闻_摘要_显示正文内容").equals("1"))
            {
                if (content != null)
                {
                    summary = content.getContent();
                    String contentTemplate = this.GetParam("文献网书籍HTML代码");
                    if (contentTemplate.length() > 0)
                    {
                        //
                    }
                    else if (summary != null && summary.length() > 0)
                    {
                        if (GetParam("是否外文版专用").equals("Foreign"))
                        { // 外文版专用
                          // 对外文版摘要的特殊处理,去掉对空格&nbsp;的替换处理 htmlsp_nospace()
                            summary = HTML2TextNew.convert(Encode.htmlsp_nospace(summary)).trim();
                        }
                        else
                        {
                            summary = HTML2TextNew.convert(Encode.htmlsp(summary)).trim();
                        }
                        if (!"".equals(this.GetParam("新闻_摘要_提取正文前n句")))
                        {
                            int n = Integer.parseInt(this.GetParam("新闻_摘要_提取正文前n句"));
                            String regex = "。";
                            if (!summary.contains(regex))
                            {
                                regex = "\\.";
                            }
                            String[] contentArr = summary.split(regex);
                            summary = "";
                            if (contentArr != null)
                            {
                                for (int i = 0; i < n && i < contentArr.length; i++ )
                                {
                                    if (StringTool.isEmptyString(contentArr[i]) == false)
                                    {
                                        summary += contentArr[i] + ("。".equals(regex) ? "。" : ".");
                                    }
                                }
                            }
                        }
                        else if (summarycount > 0)
                        {
                            summary = summary.substring(0,
                                summarycount < summary.length() ? summarycount : summary.length());
                        }
                    }
                    sSummary.append(GetParam("新闻_摘要_行头html代码"));
                    // 显示摘要
                    if (!this.GetParam("新闻_摘要_是否有链接").equals(""))
                    {
                        if (GetParam("是否外文版专用").equals("Foreign"))
                        {
                            sSummary.append(this.GetContentLink_Foreign(contentLogicId,
                                summary + (GetParam("显示省略符").equals("1") ? p_name_tail : ""),
                                GetParam("新闻_摘要_链接属性")));
                        }
                        else
                        {
                            sSummary.append(this.GetContentLink(contentLogicId,
                                summary + (GetParam("显示省略符").equals("1") ? p_name_tail : ""),
                                GetParam("新闻_摘要_链接属性")));
                        }
                    }
                    else
                    {
                        sSummary.append(
                            summary + (GetParam("显示省略符").equals("1") ? p_name_tail : ""));
                    }
                    // 显示摘要的“详细内容”
                    if (!this.GetParam("新闻_摘要_详细内容").equals(""))
                    {
                        sSummary.append(this.GetContentLink(contentLogicId,
                            this.GetParam("新闻_摘要_详细内容"), GetParam("新闻_摘要_链接属性")));
                    }
                    sSummary.append(GetParam("新闻_摘要_行尾html代码"));
                }
            }
        }
        // 图片
        StringBuffer pString = new StringBuffer();
        boolean isPic = true;
        String nodeIdStr = this.GetParam("新闻_图片_过滤图片节点");
        String nodeIdTemp = "";
        if (!nodeIdStr.equals(""))
        {
            List<ContentChannel> ccs = iContentChannelService.getByContentId(content_id);
            for (ContentChannel cc : ccs)
            {
                Channel channel = iChannelService.getById(cc.getChannelid());
                if (channel.getLogicId() == Integer.parseInt(nodeIdStr))
                {
                    isPic = false;
                    break;
                }
            }
        }
        if (this.GetParam("新闻_图片_是否显示").equals("1") && isPic)
        {
            QueryWrapper<ContentMedia> queryWrapper = new QueryWrapper<ContentMedia>();
            queryWrapper.eq("content_id", content_id);
            queryWrapper.ge("level", 0);

            if (this.GetParam("新闻_图片_上传类型").equals("2"))
            {

            }
            else if (this.GetParam("新闻_图片_上传类型").equals("3"))
            {
                queryWrapper.eq("head_pic", 1);
            }
            else
            {
                queryWrapper.ne("uploadtype", 1);
            }
            queryWrapper.orderByAsc("level", "id");
            List<ContentMedia> listimg = iContentMediaService.list(queryWrapper);

            if (listimg != null)
            {
                if (listimg.size() > 0)
                {
                    if (this.GetParam("新闻_图片_是否显示多张图").equals("1"))
                    {
                        for (int i = 0; i < listimg.size(); i++ )
                        {
                            ContentMedia contentMedia = (ContentMedia)listimg.get(i);
                            curitem.setTextBox(contentMedia.getMediaDesc());
                            curitem.setTextUrl(contentMedia.getPath());
                            pString.append(showItemPicture(curitem));
                        }
                    }
                    else
                    {
                        ContentMedia contentMedia = (ContentMedia)listimg.get(0);
                        curitem.setTextBox(contentMedia.getMediaDesc());
                        curitem.setTextUrl(contentMedia.getPath());
                        pString.append(showItemPicture(curitem));
                    }
                }
            }
        }

        // 新闻_标题_是否显示节点名称
        StringBuffer nodeName = new StringBuffer();
        StringBuffer nodeId = new StringBuffer();
        StringBuffer keyWord = new StringBuffer();
        StringBuffer nodeNames = new StringBuffer();
        if (this.GetParam("新闻_标题_是否显示节点名称").equals("1") || this.GetParam("新闻_作者_是否显示").equals("1")
            || this.GetParam("新闻_发布时间_是否显示").equals("1"))
        {
            if (content != null && content.getChannelids().size() > 0)
            {
                Channel channel = iChannelService.getById(content.getChannelids().get(0));
                String channelName = channel == null ? "" : channel.getChannelName();
                nodeName.append(channelName);
                nodeId.append(channel.getLogicId());
            }
            String author = content.getAuthor();
            if (author != null)
            {
                authorStr.append(author);
            }
            Date releaseDate = content.getSendtime();
            if (releaseDate == null)
            {
                releaseDate = new Date(System.currentTimeMillis());
            }
            if (!this.GetParam("新闻_新样式_新闻标题_日期格式").equals(""))
            {
                inputDateStr.append(
                    DateUtil.Date2Str(releaseDate, this.GetParam("新闻_新样式_新闻标题_日期格式")));
            }
            else
            {
                inputDateStr.append(DateUtil.Date2Str(releaseDate, "yyyy-MM-dd HH:mm"));
            }

            String origin_id = content.getSourceid();
            if (!StringUtils.isEmpty(origin_id))
            {
                String originName = getOrigin(content.getSourcename(), content.getSourceurl());
                originStr.append(originName);
            }

        }
        if (this.GetParam("新闻_关键字_是否显示").equals("1"))
        {
            keyWord.append("暂不支持关键字");
            /*
             * List list = getOwnKeyWord(String.valueOf(content_id)); if(list!=null){
             * keyWord.append(GetParam("新闻_关键字_综合头")); for(int i=0;i<list.size();i++){ Keyword kw =
             * (Keyword)list.get(i); keyWord.append(GetParam("新闻_关键字_行头")); if(i!=list.size()-1){
             * keyWord.append(kw.getKeyword()+GetParam("新闻_关键字_输出间隔html代码")); }else{
             * keyWord.append(kw.getKeyword()); } keyWord.append(GetParam("新闻_关键字_行尾")); } }
             * keyWord.append(GetParam("新闻_关键字_综合尾"));
             */
        }
        if (this.GetParam("新闻_所属节点_是否显示").equals("1"))
        {
            ContentChannelNameValueRedis redis = new ContentChannelNameValueRedis();
            String strContentId = StringTool.getNotNullString(content_id);
            String strTT_ID = StringTool.getNotNullString(ttId);
            if (SharedConstant.REDIS && this._PageNo > 1)
            {//
                String node_Name = redis.get(strContentId, strTT_ID);
                if (node_Name != null)
                {
                    nodeNames.append(node_Name);
                }
            }
            if (nodeNames == null || "".equals(nodeNames.toString())
                || nodeNames.toString().equals("null"))
            {// 没启用redis or 第一页 or redis没取到值的情况 都去数据库查询
                List<Channel> list = getOwnContentChannel(String.valueOf(content_id));
                if (list != null)
                {
                    nodeNames.append(GetParam("新闻_所属节点_综合头"));
                    for (int i = 0; i < list.size(); i++ )
                    {
                        Channel cch = list.get(i);
                        String channelName = GetChannelLink(cch.getLogicId(),
                            cch.getChannelName(), GetParam("新闻_所属节点_链接属性"));
                        nodeNames.append(GetParam("新闻_所属节点_行头"));
                        if (i != list.size() - 1)
                        {
                            nodeNames.append(channelName + GetParam("新闻_所属节点_输出间隔html代码"));
                        }
                        else
                        {
                            nodeNames.append(channelName);
                        }
                        nodeNames.append(GetParam("新闻_所属节点_行尾"));
                    }
                }
                nodeNames.append(GetParam("新闻_所属节点_综合尾"));

                if (SharedConstant.REDIS)
                {
                    redis.set(strContentId, strTT_ID, nodeNames.toString());
                }
            }
        }
        String newhtml = GetParam("新闻_新样式HTML代码");
        if (newhtml.length() > 0)
        {
            if (newhtml.indexOf("${NODENAME}") >= 0)
            {
                newhtml = newhtml.replaceAll("\\$\\{NODENAME\\}", nodeName.toString());
            }
            if (newhtml.indexOf("${TITLE}") >= 0)
            {
                String titleStr = sTitle.toString().replaceAll("\\$", "\\\\\\$");
                newhtml = newhtml.replaceAll("\\$\\{TITLE\\}", titleStr);
            }
            if (newhtml.indexOf("${TITLE_NOLINK}") >= 0)
            {
                newhtml = newhtml.replaceAll("\\$\\{TITLE_NOLINK\\}", pname);
            }
            if (newhtml.indexOf("${AUTHOR}") >= 0)
            {
                newhtml = newhtml.replaceAll("\\$\\{AUTHOR\\}", authorStr.toString());
            }
            if (newhtml.indexOf("${ORIGIN}") >= 0)
            {
                newhtml = newhtml.replaceAll("\\$\\{ORIGIN\\}", originStr.toString());
            }
            if (newhtml.indexOf("${PUBLISHTIME}") >= 0)
            {
                newhtml = newhtml.replaceAll("\\$\\{PUBLISHTIME\\}", inputDateStr.toString());
            }
            if (newhtml.indexOf("${SUMMARY}") >= 0)
            {
                String strSummary = StringTool.replaceAll(sSummary.toString(), "<br><br>", "");
                strSummary = StringTool.replaceAll(sSummary.toString(), "<br>", "");
                strSummary = StringTool.replaceAll(strSummary, "　　", "");
                strSummary = StringTool.replaceAll(strSummary, "&nbsp;&nbsp;&nbsp;&nbsp;", "");
                if (!GetParam("是否外文版专用").equals("Foreign"))
                {
                    strSummary = "　　" + strSummary;
                }
                strSummary = strSummary.toString().replaceAll("\\$", "\\\\\\$");;
                newhtml = newhtml.replaceAll("\\$\\{SUMMARY\\}", strSummary);
            }
            if (newhtml.indexOf("${SUMMARY_NOSPACE}") >= 0)
            {
                String strSummary = StringTool.replaceAll(sSummary.toString(), "<br><br>", "");
                strSummary = StringTool.replaceAll(strSummary, "　　", "");
                strSummary = StringTool.replaceAll(strSummary, "&nbsp;&nbsp;&nbsp;&nbsp;", "");
                newhtml = newhtml.replaceAll("\\$\\{SUMMARY_NOSPACE\\}", strSummary);
            }
            if (newhtml.indexOf("${PIC}") >= 0)
            {
                newhtml = newhtml.replaceAll("\\$\\{PIC\\}", pString.toString());
            }
            if (newhtml.indexOf("${PICALL}") >= 0)
            {
                newhtml = newhtml.replaceAll("\\$\\{PICALL\\}", pString.toString());
            }
            if (newhtml.indexOf("${MORE}") >= 0)
            {
                String more = this.GetParam("更多_文字");
                String linkMore = this.GetContentLink(contentLogicId, more,
                    GetParam("新闻_标题_链接属性"));
                newhtml = newhtml.replaceAll("\\$\\{MORE\\}", linkMore);
            }
            if (newhtml.indexOf("${KEYWORD}") >= 0)
            {
                newhtml = newhtml.replaceAll("\\$\\{KEYWORD\\}", keyWord.toString());
            }
            if (newhtml.indexOf("${TITLE_SOURCE}") >= 0)
            {
                newhtml = StringTool.replaceAll(newhtml, "${TITLE_SOURCE}", Encode.htmlsp(name));
            }
            if (newhtml.indexOf("${URL}") >= 0)
            {
                newhtml = StringTool.replaceAll(newhtml, "${URL}", getContentURL(contentLogicId));
            }
            if (newhtml.indexOf("${NODEID}") >= 0)
            {
                newhtml = StringTool.replaceAll(newhtml, "${NODEID}", nodeId.toString());
            }
            if (newhtml.indexOf("${NODENAMES}") >= 0)
            {
                newhtml = newhtml.replaceAll("\\$\\{NODENAMES\\}", nodeNames.toString());
            }
            if (newhtml.indexOf("${CONTENTID}") >= 0)
            {
                newhtml = newhtml.replaceAll("\\$\\{CONTENTID\\}", String.valueOf(content_id));
            }
            return newhtml;
        }
        else
        {
            return sTitle.toString() + sSummary.toString();
        }
    }

    // 当具有风格定义的链接名名称被截断时，可能造成显示的不正常！
    private String retrieveNoStyleName(String name, int len_limit)
    {
        if (name == null || name.length() == 0)
        {
            return "";
        }
        char[] charArray = name.toCharArray();
        StringBuffer strBuff = new StringBuffer();
        boolean meetThenFlag = false;
        for (int i = 0; i < charArray.length; i++ )
        {
            if (charArray[i] == '<' || charArray[i] == '>')
            {
                // meetThenFlag = !meetThenFlag;
            }
            if (!meetThenFlag)
            {
                strBuff.append(charArray[i]);
            }
        }
        if (strBuff.length() <= len_limit)
        {
            return strBuff.toString();
        }
        else
        {
            // 把标题中的html标记过滤
//			return HTML2TextNew.convert(Encode.htmlsp(strBuff.substring(0, len_limit)));
            return strBuff.substring(0, len_limit);
        }
    }

    // 截取特定长度字符串
    private String subStringName(String name, int len_limit)
    {
        if (name == null || name.length() == 0)
        {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        char[] charArray = name.toCharArray();
        double len = 0;
        for (int i = 0; i < charArray.length; i++ )
        {
            char a = charArray[i];
            boolean isChinese = StringTool.isChinese(a);
            if (isChinese)
            {
                len += 1;
            }
            else
            {
                len += 0.5;
            }
            if (len <= len_limit)
            {
                sb.append(a);
            }
        }
        return sb.toString();
    }

    // 获取点击数
    private long getViewCount(String content_id)
    {
        long result = 0;
        /*
         * try { ReadDBTool paiHangRead = (ReadDBTool)
         * SpringContextUtil.getBean("ReadDBToolPaiHangRead"); String sql =
         * "from ContentCount where contentId=" + content_id; List<ContentCount> subList =
         * paiHangRead.find(sql); ContentCount contentCount = subList.get(0);
         * if(contentCount!=null){ result =contentCount.getCountSum(); } } catch (Exception e) { //
         * TODO: handle exception }
         */
        return result;
    }

    /**
     * 显示一张图片
     * 
     * @param curitem
     *            标记内容的一行
     * @return 处理该图片的结果
     */
    protected String showItemPicture(ContentMedia contentMedia, TitlePropVO curitem)
    {
        String res = "";
        String content_id = contentMedia.getContentId();
        Content content = iContentService.getById(content_id);
        // 处理链接
        String link = "";
        String link_url;
        String pic_desc = contentMedia.getMediaDesc();
        String pic_alt = pic_desc;
        int picDescLen = 0;
        if (!StringUtils.isEmpty(content_id))
        {
            // 图片是从新闻中选取过来的
            link = this.getContentURL(content.getLogicId());
            if (GetParam("图片说明_新闻标题").equals("1"))
            {
                pic_desc = curitem.getTitle();
                pic_alt = pic_desc;
                if (GetParam("图片说明_新闻标题_字数").length() > 0)
                {
                    try
                    {
                        picDescLen = Integer.parseInt(GetParam("图片说明_新闻标题_字数"));
                        pic_desc = HTML2TextNew.convert(pic_desc);
                        if (pic_desc.length() > picDescLen)
                        {
                            pic_desc = pic_desc.substring(0, picDescLen) + "...";
                        }
                    }
                    catch (Exception e)
                    {
                        // TODO: handle exception
                    }
                }
            }
        }
        else
        {
            link_url = curitem.getLinkUrl();
            if (link_url != null && link_url.length() > 0)
            {
                // 图片是自己上传的，并有内容
                link = link_url;
            }

        }

        String imgsrc = "";
        String imgpath = contentMedia.getPath();
        // 处理图片src
        if (imgpath != null && imgpath.toLowerCase().endsWith(".swf"))
        {
            imgsrc = "<embed src=\"" + imgpath
                     + "\" type=\"application/x-shockwave-flash\" scale=\"\" play=\"true\" loop=\"true\" menu=\"true\" "
                     + this.GetParam("图片_图片属性") + "></embed>";
        }
        else
        {
            if (pic_desc != null && pic_desc.length() > 0
                && !this.GetParam("图片说明_是否作为图片的ALT").equals("0"))
            {
                if (isMongol)
                {
                    imgsrc = "<img src=\"" + imgpath + "\" " + this.GetParam("图片_图片属性") + " alt=\""
                             + Encode.htmlMongolTitle(pic_alt) + "\">";
                }
                else
                {
                    imgsrc = "<img src=\"" + imgpath + "\" " + this.GetParam("图片_图片属性") + " alt=\""
                             + HTML2TextNew.convertAlt(Encode.htmlsp(pic_alt)) + "\">";

                }
            }
            else
            {
                imgsrc = "<img src=\"" + imgpath + "\" " + this.GetParam("图片_图片属性") + ">";
            }
        }
        // 处理图片标题
        String pic_title = (curitem.getTitle() == null) ? "" : curitem.getTitle();
        if (this.GetParam("图片_标题_是否显示").equals("1"))
        {
            res += this.GetParam("图片_标题_行头html代码") + pic_title + this.GetParam("图片_标题_行尾html代码");
        }
        if (!link.equals(""))
        {
            // 图片有链接
            if (this.GetParam("图片_链接唯一").equals("1"))
            {
                res += "<a href=\"" + link + "\" " + this.GetParam("图片_链接属性") + ">" + imgsrc;
                if (!this.GetParam("图片_是否显示图片说明").equals(""))
                {
                    if (this.GetParam("是否外文版专用").equals("Foreign") || isMongol) // 外文版专用
                    {
                        res += this.GetParam("图片说明_行头html代码");
                        res += (pic_desc != null && pic_desc.length() > 0) ? Encode.htmlsp_nospace(
                            pic_desc) : this.GetParam("图片说明_默认内容");
                        res += this.GetParam("图片说明_行尾html代码");
                    }
                    else
                    {
                        res += this.GetParam("图片说明_行头html代码");
                        res += (pic_desc != null && pic_desc.length() > 0) ? Encode.htmlsp(
                            pic_desc) : this.GetParam("图片说明_默认内容");
                        res += this.GetParam("图片说明_行尾html代码");
                    }
                }
                res += "</a>";
            }
            else
            {
                // 图片有链接
                res += "<a href=\"" + link + "\" " + this.GetParam("图片_链接属性") + ">" + imgsrc
                       + "</a>";
                if (!this.GetParam("图片_是否显示图片说明").equals(""))
                {
                    if (this.GetParam("是否外文版专用").equals("Foreign") || isMongol) // 外文版专用
                    {
                        res += this.GetParam("图片说明_行头html代码");
                        if (this.GetParam("图片说明_是否带链接").equals("0") || pic_desc == null
                            || pic_desc.length() == 0)
                        {
                            res += (pic_desc != null
                                    && pic_desc.length() > 0) ? Encode.htmlsp_nospace(
                                        pic_desc) : this.GetParam("图片说明_默认内容");
                        }
                        else
                        {
                            res += "<a href=\"" + link + "\" " + this.GetParam("图片_链接属性") + ">"
                                   + Encode.htmlsp_nospace(pic_desc) + "</a>";
                        }
                        res += this.GetParam("图片说明_行尾html代码");
                    }
                    else
                    {
                        res += this.GetParam("图片说明_行头html代码");
                        if (this.GetParam("图片说明_是否带链接").equals("0") || pic_desc == null
                            || pic_desc.length() == 0)
                        {
                            res += (pic_desc != null && pic_desc.length() > 0) ? Encode.htmlsp(
                                pic_desc) : this.GetParam("图片说明_默认内容");
                        }
                        else
                        {
                            res += "<a href=\"" + link + "\" " + this.GetParam("图片_链接属性") + ">"
                                   + Encode.htmlsp(pic_desc) + "</a>";
                        }
                        res += this.GetParam("图片说明_行尾html代码");
                    }
                }
            }
        }
        else
        {
            // 图片无链接
            if (imgpath != null && this.GetParam("图片_只显示图片地址").equals("1"))
            {
                res += imgpath;
            }
            else
            {
                res += imgsrc;
            }
            if (!this.GetParam("图片_是否显示图片说明").equals(""))
            {
                if (this.GetParam("是否外文版专用").equals("Foreign") || isMongol) // 外文版专用
                {
                    res += this.GetParam("图片说明_行头html代码");
                    res += (pic_desc != null && pic_desc.length() > 0) ? Encode.htmlsp_nospace(
                        pic_desc) : this.GetParam("图片说明_默认内容");
                    res += this.GetParam("图片说明_行尾html代码");
                }
                else
                {
                    res += this.GetParam("图片说明_行头html代码");
                    res += (pic_desc != null && pic_desc.length() > 0) ? Encode.htmlsp(
                        pic_desc) : this.GetParam("图片说明_默认内容");
                    res += this.GetParam("图片说明_行尾html代码");
                }
            }
        }

        // 处理图片标题
        if (this.GetParam("图片_标题_是否显示").equals("2"))
        {
            res += this.GetParam("图片_标题_行头html代码") + pic_title + this.GetParam("图片_标题_行尾html代码");
        }
        if (!this.GetParam("新闻_标题_日期格式").equals(""))
        {
            // 新闻_标题日期格式非空表示需要显示日期
            res += (" <center>"
                    + GetDateStr(GetParam("新闻_标题_日期格式"), DateUtil.asDate(curitem.getPublishTime()))
                    + "</center>");
        }
        return res;
    }

    /**
     * 显示新闻前节点,新闻所属栏目
     * 
     * @param curitem
     *            标记内容的一行
     */
    protected String showItemNewsChannel(TitlePropVO curitem)
    {
        Integer node_id = curitem.getNodeId();
        Integer cNode_id = curitem.getCnodeId();
        if (this.GetParam("新闻前是否显示节点_原始栏目链接").equals("1") && cNode_id != null)
        {
            if (cNode_id> 0)
            {
                node_id = cNode_id;
            }
        }
        Channel channel = iChannelService.getByLogicId(node_id);
        String channel_name = channel.getChannelName();
        StringBuffer t = new StringBuffer();
        if (this.GetParam("新闻前节点_行头html代码").length() > 0)
        {
            t.append(this.GetParam("新闻前节点_行头html代码"));
        }
        if (this.GetParam("新闻前节点是否不显示方括号").equals("1"))
        {
            t.append(GetChannelLink(node_id, channel_name, GetParam("新闻前节点链接属性")));
        }
        else
        {
            t.append("[");
            t.append(GetChannelLink(node_id, channel_name, GetParam("新闻前节点链接属性")));
            t.append("]");
        }
        if (this.GetParam("新闻前节点_行尾html代码").length() > 0)
        {
            t.append(this.GetParam("新闻前节点_行尾html代码"));
        }
        return t.toString();
    }

    /**
     * 获新闻链接
     * 
     * @param id
     *            新闻id
     * @param name
     *            链接名称
     * @param hrefprop
     *            链接属性
     * @return 新闻的连接
     */

    public String GetContentLink(Integer contentLogicId, String name, String hrefprop, String title)
    {
        return "<a href='" + sharedConstant.getContentViewUrl(contentLogicId) + "' " + hrefprop + " title=\""
               + HTML2TextNew.convertAlt(title) + "\">" + Encode.htmlsp(name) + "</a>";
    }

    public String GetContentLink(Integer contentLogicId, String name, String hrefprop)
    {

        return "<a href='" + sharedConstant.getContentViewUrl(contentLogicId) + "' " + hrefprop + ">" + Encode.htmlsp(name)
               + "</a>";
    }

    public String GetContentMongolLink(Integer contentLogicId, String name, String hrefprop, String title)
    {
        return "<a href='" + sharedConstant.getContentViewUrl(contentLogicId) + "' " + hrefprop + " title=\""
               + Encode.htmlMongolTitle(title) + "\">" + Encode.htmlsp_nospace(name) + "</a>";
    }

    // 外文版专用
    public String GetContentLink_Foreign(Integer contentLogicId, String name, String hrefprop)
    {
        return "<a href='" + sharedConstant.getContentViewUrl(contentLogicId) + "' " + hrefprop + ">"
               + Encode.htmlsp_nospace(name) + "</a>";
    }

    public String GetContentLink_Foreign(Integer contentLogicId, String name, String hrefprop, String title)
    {
        return "<a href='" + sharedConstant.getContentViewUrl(contentLogicId) + "' " + hrefprop + " title=\""
               + HTML2TextNew.convertAlt(title) + "\">" + Encode.htmlsp_nospace(name) + "</a>";
    }

    /**
     * 获得节点链接
     * 
     * @param id
     *            节点id
     * @param name
     *            链接名称
     * @param hrefprop
     *            链接属性
     * @return 节点的连接
     */
    public String GetChannelLink(Integer id, String name, String hrefprop)
    {
        return "<a href='" + sharedConstant.getChannelViewUrl(id) + "' " + hrefprop + ">" + Encode.htmlsp(name)
               + "</a>";
    }

    public String Foreign_GetChannelLink(Integer id, String name, String hrefprop)
    {
        return "<a href='" + sharedConstant.getChannelViewUrl(id) + "' " + hrefprop + ">"
               + Encode.htmlsp_nospace(name) + "</a>";
    }

    /**
     * 显示新闻前节点
     * 
     * @param curitem
     */
    protected String showItemNewsNode(TitlePropVO curitem)
    {
        // 待定

//		SQL db2sql = this.getDB2SQL();
//		String sqlStr = "with RPL (node_id, parent_id, channel_name) AS (select node_id, parent_id,channel_name from v_channel_2 where node_id in (select node_id from content where content_id=?) and node_id>1 union all select parent.node_id, parent.parent_id, parent.channel_name from RPL child, v_channel_2 parent where child.parent_id = parent.node_id and parent.node_id>3) select DISTINCT RPL.NODE_ID, RPL.PARENT_ID,RPL.CHANNEL_NAME from RPL , v_channel_2 ct where RPL.node_id = ct.node_id and RPL.parent_id not in (select node_id from RPL)";
//		db2sql.setSQL(sqlStr);
//		db2sql.ClearParam();
//		db2sql.setParam(1, new Value(curitem.getColumn("content_id").asInt()));
//		DataSet ds = db2sql.execQuery();
//		SQL db2sql1 = this.getDB2SQL();
//		String sqlStr1 = "with RPL (node_id, parent_id, channel_name) AS (select node_id, parent_id,channel_name from v_channel_2 where node_id in (select node_id from content_channel where content_id=? ) and node_id>1 union all select parent.node_id, parent.parent_id, parent.channel_name from RPL child, v_channel_2 parent where child.parent_id = parent.node_id and parent.node_id>3) select DISTINCT RPL.NODE_ID, RPL.PARENT_ID,RPL.CHANNEL_NAME from RPL , v_channel_2 ct where RPL.node_id = ct.node_id and RPL.parent_id not in (select node_id from RPL)";
//		db2sql1.setSQL(sqlStr1);
//		db2sql1.ClearParam();
//		db2sql1.setParam(1, new Value(curitem.getColumn("CONTENT_ID").asInt()));
//		DataSet ds1 = db2sql1.execQuery();
        StringBuffer t = new StringBuffer();
        t.append("[");
//		if (ds.getRowCount() > 0) {
//			t.append(this.GetChannelLink(ds.getRecord(0).getColumn("NODE_ID").asString(), ds.getRecord(0).getColumn("CHANNEL_NAME").asString(), GetParam("新闻前节点链接属性")));
//		} else {
//			t.append(this.GetChannelLink(ds1.getRecord(0).getColumn("NODE_ID").asString(), ds1.getRecord(0).getColumn("CHANNEL_NAME").asString(), GetParam("新闻前节点链接属性")));
//		}
        t.append("]");
        return t.toString();
    }

    /**
     * 显示一条节点,根据"节点_显示新闻的数量"设置，显示节点下的新闻，根据"节点_继承子节点标记ID"，显示子节点的标记
     * 
     * @param curitem
     *            标记内容的一行
     * @return 处理该节点的结果
     */
    protected String showItemNode(TitlePropVO curitem)
    {
        String res = "";
        Integer node_id = curitem.getCnodeId();
        // 每输出一条内容，最大输出条数减1
        if (!this.GetParam("节点_是否显示节点名称").equals(""))
        {

            if (this.GetParam("是否外文版专用").equals("Foreign"))
            {
                res += this.GetParam("节点_节点名称_头html代码") + this.Foreign_GetChannelLink(node_id,
                    curitem.getTitle(), GetParam("节点_链接属性")) + this.GetParam("节点_节点名称_尾html代码");
            }
            else
            {
                res += this.GetParam("节点_节点名称_头html代码");
                res += this.GetChannelLink(node_id, curitem.getTitle(), GetParam("节点_链接属性"));
                if (this.GetParam("节点_是否显示“更多”").equals("2"))
                {
                    res += this.showMore("" + node_id);
                }
                res += this.GetParam("节点_节点名称_尾html代码");
            }

        }

        // 处理节点的自动新闻
        if (!this.GetParam("节点_显示新闻的数量").equals(""))
        {

            int max = Integer.parseInt(this.GetParam("节点_显示新闻的数量"));

            if (max > 0)
            {
                // 如果该节点有子节点，自动新闻时连同该节点下第一层子节点
                String nodeSet = "" + node_id;
                String nolistnode = this.GetParam("节点_自动节点_不输出子栏目");

                nolistnode = nolistnode == null ? "" : nolistnode.trim().toLowerCase();
                if (!nolistnode.equals("true"))
                {
                    Channel parentChannel = iChannelService.getByLogicId(node_id);
                    String[] ids = nolistnode.split(",");

                    QueryWrapper<Channel> queryWrapper = new QueryWrapper<Channel>();
                    queryWrapper.eq("parentid", parentChannel.getId());
                    queryWrapper.eq("dstatus", 2);
                    queryWrapper.eq("status_release", 1);
                    queryWrapper.notIn("logic_id", ids);

                    List<Channel> chanels = iChannelService.list(queryWrapper);

                    for (int i = 0; i < chanels.size(); i++ )
                    {
                        Channel channel = chanels.get(i);
                        nodeSet += "," + channel.getLogicId();
                    }
                }
                String str = this.showNewsList(nodeSet, max);
                if (!str.equals(""))
                {
                    res += this.GetParam("新闻_自动新闻_头html代码") + str
                           + this.GetParam("新闻_自动新闻_尾html代码");
                }
            }
        }
        // 继承子节点标记
        String rtStr = this.GetParam("节点_引用其他标记模板");
        if (!rtStr.equals(""))
        {
            // 从模板中获得标记列表
            String tagIDList = this.getTagListFromStr(rtStr);
            if (tagIDList.length() > 0)
            {
                String[] tagids = tagIDList.split(",");

                QueryWrapper<ModelSplit> queryWrapper = new QueryWrapper<ModelSplit>();
                queryWrapper.in("logic_id", tagids);
                List<ModelSplit> list = iModelSplitService.list(queryWrapper);

                // 解释每个标记，并替换到模板中
                if (list.size() > 0)
                {
                    // 如果找的到对应的标记，则替换
                    for (int i = 0; i < list.size(); i++ )
                    {
                        ModelSplit templateTag = list.get(i);

                        Split split = iSplitService.getById(templateTag.getSplitId());

                        String className = split.getClassName();
                        int tag_id = templateTag.getLogicId();
                        String replaceStr = "";
                        // 初试化参数
                        className = className.substring(0, className.indexOf("."));

                        // 获得标记的内容
                        try
                        {
                            // 调用对应的类
                            Class tc = Class.forName("cn.com.people.cms.server.tag." + className);
                            AbstractBaseTag tv = (AbstractBaseTag)tc.newInstance();
                            // tv.setNodeId(node_id);
                            tv.initTag(tag_id, node_id, "-1");
                            // 获得结果
                            HashMap iMap = tv.view();
                            replaceStr = iMap.get("tagvalues").toString();
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                            log.error("节点'" + this.nodeId + "'所设定的继承子节点标记'" + tag_id + "'在子节点'"
                                      + node_id + "'中，生成出错。");
                            replaceStr = "标记生成出错，请与管理员联系！";
                        }
                        // 替换到模板中
                        rtStr = Regular.ReplaceTag("" + tag_id, replaceStr, rtStr);
                    }
                }
                // 将找不到的标记替换为空。
                Vector v = Regular.MatchTagList(rtStr);
                for (int i = 0; i < v.size(); i++ )
                {
                    String t_tagID = v.get(i).toString();
                    rtStr = Regular.ReplaceTag(t_tagID, "", rtStr);
                }
            }
            // 从数据库中获取标记的信息
            res += rtStr;
        }
        if (!this.GetParam("节点_是否显示“更多”").equals("") && !this.GetParam("节点_是否显示“更多”").equals("2"))
        {
            res += this.showMore("" + node_id);
        }
        return res;
    }

    /**
     * 显示一条节点,根据"节点_显示新闻的数量"设置，显示节点下的新闻，根据"节点_继承子节点标记ID"，显示子节点的标记
     * 
     * @param curitem
     *            标记内容的一行
     * @return 处理该节点的结果
     */
    protected String showItemNodeLink(TitlePropVO curitem)
    {
        String res = "";
        Integer node_id = curitem.getCnodeId();
        // 每输出一条内容，最大输出条数减1
        if (!this.GetParam("节点_是否显示节点名称").equals(""))
        {
            if (this.GetParam("是否外文版专用").equals("Foreign"))
            {
                res += this.GetParam("节点_节点名称_头html代码") + this.Foreign_GetChannelLink(node_id,
                    curitem.getTitle(), GetParam("节点_链接属性")) + this.GetParam("节点_节点名称_尾html代码");
            }
            else
            {
                res += this.GetParam("节点_节点名称_头html代码")
                       + this.GetChannelLink(node_id, curitem.getTitle(), GetParam("节点_链接属性"))
                       + this.GetParam("节点_节点名称_尾html代码");
            }
        }
        return res;
    }

    /**
     * 显示自动新闻
     * 
     * @param NodeSet
     *            栏目所属的节点
     * @param max
     *            输出的最大条数
     * @return 返回自动新闻的结果
     */
    protected String showNewsList(String NodeSet, int pageSize)
    {
        StringBuffer sBuff = new StringBuffer();
        AutoNewsList autoNewsList = new AutoNewsList();
        // String strRedisInputDate=null;
        // boolean bMD5Equal=false;
        // boolean isMultipage = false;
        boolean isRedisNewsList = true;
        Integer limit = pageSize;
        String strTTID = StringTool.getNotNullString(ttId);
        if (this.GetParam("新闻_自动新闻_是否显示翻页控制栏").length() > 0)
        {
            {
                if (this.GetParam("新闻_自动新闻_最大新闻条数").length() > 0)
                {
                    limit = Integer.parseInt(GetParam("新闻_自动新闻_最大新闻条数"));
                    if (limit == 0)
                    {
                        limit = 200;
                    }
                }
                else
                {
                    limit = 100;
                }
            }
            // isMultipage = true;
        }
        if (GetParam("新闻_新闻列表_是否使用redis缓存").equals("1"))
        {
            isRedisNewsList = false;
        }
        // 如果有设置自动新闻属性，表示需要显示自动新闻；
        if (NodeSet.trim().length() > 0 && pageSize > 0)
        {
            if (NodeSet.equals("0"))
            {
                // 如果值为0，表示当前节点
                NodeSet = "" + nodeId;
            }

            // 判断是否启用Redis自动新闻列表功能并获得上次查询时的最近input_date值

            int iAutoNewsTimeLimit = Converter.str2Int(GetParam("新闻_自动新闻_时间限制"));
            if (iAutoNewsTimeLimit <= 0) iAutoNewsTimeLimit = 60;// 默认60天
            // iAutoNewsTimeLimit = 1500;
            // isRedisNewsList = false;
            String strAutoNewsTimeLimit = StringTool.getNotNullString(iAutoNewsTimeLimit);
            // 去掉"是否不限制时间"属性
            // if(GetParam("是否不限制时间").equals("1"))iAutoNewsTimeLimit=60;
            // 处理 inputDate字段条件
            Calendar cal = Calendar.getInstance();
            Date nowTime = cal.getTime();
            String endTime = DateUtil.Date2Str(nowTime, "yyyy-MM-dd") + " 23:59:59";
            String startTime = "";
            List<ContentDetailVO> list = null;

            if (SharedConstant.REDIS && isRedisNewsList)
            {
                list = autoNewsList.getRedisAutoNewsList(NodeSet, strTTID, strAutoNewsTimeLimit);
                if (list == null || list.size() == 0)
                {
                    Calendar cal1 = Calendar.getInstance();
                    cal1.add(cal1.DATE, -iAutoNewsTimeLimit + 1);
                    startTime = DateUtil.Date2Str(cal1.getTime(), "yyyy-MM-dd") + " 00:00:00";
                }
                else
                {
                    ContentDetailVO temp1 = (ContentDetailVO)list.get(0);
                    ContentDetailVO temp2 = (ContentDetailVO)list.get(list.size() - 1);
                    Date tempId1 = temp1.getSendtime();
                    Date tempId2 = temp2.getSendtime();
                    // 上一次最新查询日期在往前新增冗余天数处理，默认设置
                    int ext = 7;
                    if (tempId1.getTime() > tempId2.getTime())
                    {
                        Date d = new Date(
                            temp1.getSendtime().getTime() - (long)ext * 24 * 60 * 60 * 1000);
                        startTime = DateUtil.Date2Str(d, "yyyy-MM-dd") + " 00:00:00";
                    }
                    else
                    {
                        Date d = new Date(
                            temp2.getSendtime().getTime() - (long)ext * 24 * 60 * 60 * 1000);
                        startTime = DateUtil.Date2Str(d, "yyyy-MM-dd") + " 00:00:00";
                    }
                }
            }
            else
            {
                Calendar cal1 = Calendar.getInstance();
                cal1.add(cal1.DATE, -iAutoNewsTimeLimit + 1);
                startTime = DateUtil.Date2Str(cal1.getTime(), "yyyy-MM-dd") + " 00:00:00";
            }

            ContentViewRequestVO searchParam = new ContentViewRequestVO();
            searchParam.setChannelLogicIdInStr(NodeSet);
            searchParam.setDstatus(Content.DSTATUS_PUBLISHED);
            searchParam.setEndTime(endTime);
            searchParam.setStartTime(startTime);

            if (!this._notListContentSet.equals(""))
            {
                searchParam.setContentIdNotInStr1(_notListContentSet);
            }
            String content_IdSet = GetParam("新闻_自动新闻_过滤新闻ID");
            if (!content_IdSet.equals(""))
            {
                searchParam.setContentIdNotInStr2(content_IdSet);
            }
            if (GetParam("新闻_自动新闻_是否只显示图片新闻").equals("1"))
            {
                searchParam.setOnlyImageNews(true);
            }
            if (GetParam("新闻_自动新闻_是否按显示时间排序").equals("1"))
            {
                searchParam.setOrderBy(" sendtime desc ");
            }
            else
            {
                searchParam.setOrderBy(" createtime desc ");
            }
            searchParam.setLimit(limit);

            if (SharedConstant.REDIS && isRedisNewsList)
            {
                if (this._PageNo <= 1)
                {
                    list = autoNewsList.getRedisAutoNewsList(NodeSet, strTTID,
                        strAutoNewsTimeLimit);// 取redis值
                    if (list == null || list.size() == 0)
                    {// 未取到值得情况
                        list = iContentService.listAutoNews(searchParam);
                        autoNewsList.setRedisAutoNewsList(NodeSet, strTTID, strAutoNewsTimeLimit,
                            list);// 保存存redis值
                    }
                    else
                    {
                        List<ContentDetailVO> newList = list = iContentService.listAutoNews(
                            searchParam);;// 查询距离上一次查询日期之后最新的结果集
                        if (newList != null)
                        {
                            newList.addAll(list);
                            List<ContentDetailVO> itemlist = new ArrayList();
                            Map contentSelected = new HashMap();
                            int j = 1;
                            for (int i = 0; i < newList.size(); i++ )
                            {
                                ContentDetailVO temp = newList.get(i);
                                String content_id = StringTool.getNotNullString(temp.getId());
                                if (!contentSelected.containsKey(content_id))
                                {
                                    contentSelected.put(content_id, "Exist");
                                    itemlist.add(temp);
                                    j++ ;
                                    if (j > limit)
                                    {
                                        break;
                                    }
                                }
                            }
                            list = itemlist;
                        }
                        autoNewsList.setRedisAutoNewsList(NodeSet, strTTID, strAutoNewsTimeLimit,
                            list);// 保存存redis值.
                    }
                }
                else
                {
                    list = autoNewsList.getRedisAutoNewsList(NodeSet, strTTID,
                        strAutoNewsTimeLimit);// 取redis值
                    if (list == null || list.size() == 0)
                    {// 未取到值得情况
                        list = iContentService.listAutoNews(searchParam);
                        autoNewsList.setRedisAutoNewsList(NodeSet, strTTID, strAutoNewsTimeLimit,
                            list);// 保存存redis值
                    }
                }
            }
            else
            {
                list = iContentService.listAutoNews(searchParam);
            }
            List<ContentDetailVO> sublist = null;
            if (list != null)
            {
                List itemlist = new ArrayList();
                if (GetParam("新闻_自动新闻_是否过滤重复新闻").equals("1"))
                {
                    Map contentSelected = new HashMap();;
                    for (int j = 0; j < list.size(); j++ )
                    {
                        ContentDetailVO pojo_ContentChannel = list.get(j);
                        String content_id = String.valueOf(pojo_ContentChannel.getId());
                        if (!contentSelected.containsKey(content_id))
                        {
                            contentSelected.put(content_id, "Exist");
                            itemlist.add(pojo_ContentChannel);
                        }
                    }
                    list = itemlist;
                }
                PageModel pm = new PageModel(list, pageSize);
                sublist = pm.getPageList(this._PageNo);// 显示第几页
                this._itemStart = pm.getPageStartRow() + 1;
                this._itemEnd = pm.getPageEndRow();
                this._itemTotal = pm.getTotalRows();
            }

            String spc = this.showPageController();
            String spc1 = this.showPageController1();
            if (this.GetParam("新闻_自动新闻_是否显示翻页控制栏").equals("1")
                || this.GetParam("新闻_自动新闻_是否显示翻页控制栏").equals("3")
                || this.GetParam("新闻_自动新闻_是否显示翻页控制栏").equals("4"))
            {
                // 输出翻页信息
                sBuff.append(spc);
            }
            int inv = 0;
            if (!GetParam("新闻_输出间隔").equals(""))
            {
                inv = Integer.parseInt(GetParam("新闻_输出间隔"));
            }
            String pic;
            // 初试化输出列数
            int i_MultiColumn = 1;
            if (!this.GetParam("手选内容_多列输出_列数").equals(""))
            {
                int i_t1 = Integer.parseInt(GetParam("手选内容_多列输出_列数"));
                if (i_t1 > 0)
                {
                    i_MultiColumn = i_t1;
                }
            }
            sBuff.append(GetParam("新闻_列表头"));
            int count = 0;
            if (sublist != null)
            {
                SimpleDateFormat TimestampDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                for (int i = 0; i < sublist.size(); i++ )
                {

                    ContentDetailVO pojo_ContentChannel = sublist.get(i);
                    String content_id = pojo_ContentChannel.getId();
                    Integer contentLogicId = pojo_ContentChannel.getLogicId();
                    String title = pojo_ContentChannel.getTitle();

                    Timestamp inputDate = DateUtil.dateToTimestamp(
                        pojo_ContentChannel.getCreatetime());
                    Timestamp displayDate = DateUtil.dateToTimestamp(
                        pojo_ContentChannel.getSendtime());
                    int cNodeId = pojo_ContentChannel.getChannelLogicId();

                    if (displayDate != null)
                    {
                        Date date = DateUtil.asDate(displayDate);
                        String display_date = TimestampDateFormat.format(date);
                        if (display_date != null)
                        {
                            if (!display_date.equals("2011-06-24"))
                            {
                                inputDate = displayDate;
                            }
                        }
                    }

                    int mediaCount = pojo_ContentChannel.getMediaCount();
                    /*
                     * String ext1 = pojo_ContentChannel.getExt1(); String ext2 =
                     * pojo_ContentChannel.getExt2(); String ext3 = pojo_ContentChannel.getExt3();
                     * String ext4 = pojo_ContentChannel.getExt4();
                     */

                    TitlePropVO pojo_VH_Title_Prop = new TitlePropVO();
                    pojo_VH_Title_Prop.setContentId(content_id);
                    pojo_VH_Title_Prop.setNodeId(nodeId);
                    pojo_VH_Title_Prop.setCnodeId(cNodeId);
                    pojo_VH_Title_Prop.setTitle(title);
                    pojo_VH_Title_Prop.setInputDate(inputDate);
                    pojo_VH_Title_Prop.setMediaCount(mediaCount);
                    pojo_VH_Title_Prop.setContentLogicId(contentLogicId);
                    /*
                     * pojo_VH_Title_Prop.setExt1(ext1); pojo_VH_Title_Prop.setExt2(ext2);
                     * pojo_VH_Title_Prop.setExt3(ext3); pojo_VH_Title_Prop.setExt4(ext4);
                     */
                    if (i % i_MultiColumn == 0) sBuff.append(GetParam("新闻_行头html代码"));

                    String itemNews = this.showItemNews(pojo_VH_Title_Prop);
                    /*
                     * if (!this.GetParam("新闻前是否显示节点").equals("")) { if
                     * (this.GetParam("新闻前是否显示节点").equals("2")) { ReadDBTool readDao = (ReadDBTool)
                     * SpringContextUtil.getBean("ReadDBTool"); Channel
                     * channel=(Channel)readDao.getById(Channel.class, Integer.valueOf(cNodeId));
                     * String channelName = channel==null?"":channel.getChannelName();
                     * pojo_VH_Title_Prop.setChannelName(channelName); itemNews =
                     * this.showItemNewsChannel(pojo_VH_Title_Prop) + itemNews + "\n"; } }
                     */
                    sBuff.append(itemNews);

                    if (i_MultiColumn > 1 && (i + 1) % i_MultiColumn != 0)
                        sBuff.append(GetParam("手选内容_多列输出_列间html代码"));
                    if ((i + 1) % i_MultiColumn == 0) sBuff.append(GetParam("新闻_行尾html代码") + "\n");
                    // 处理隔行
                    if (inv > 0)
                    {
                        count++ ;
                        if (count == inv && (sublist.size() - 1) != i && i > 0)
                        {
                            count = 0;
                            sBuff.append(GetParam("新闻_输出间隔html代码").equals("") ? "<br>" : GetParam(
                                "新闻_输出间隔html代码"));
                        }
                    }
                }
            }

            sBuff.append(GetParam("新闻_列表尾"));
            if (this.GetParam("新闻_自动新闻_是否显示翻页控制栏").equals("2")
                || this.GetParam("新闻_自动新闻_是否显示翻页控制栏").equals("3"))
            {
                // 输出翻页信息
                sBuff.append(spc);
            }
            if (this.GetParam("新闻_自动新闻_是否显示翻页控制栏").equals("4"))
            {
                sBuff.append(spc1);
            }
        }
        return sBuff.toString();
    }

    protected String showPageController()
    {
        return showPageController(-1, true);
//		return showPageController(-1,false);
    }

    protected String showPageController(int node_id, boolean desc)
    {
        Channel tempchannel = this.getChannel();
        if (node_id > 0)
        {
            tempchannel = getChannel(node_id);
        }

        if (this.GetParam("翻页_新样式").equals("1"))
        {
            String result = this.GetParam("翻页_HTML代码");
            int totalPage = (int)Math.ceil(((double)this._itemTotal) / this._PageCount);
//			if (totalPage > 1 && this._itemTotal % this._PageCount == 0) {
//				totalPage -= 1;
//			}
            if (totalPage <= 0) totalPage = 1;
            if (this._PageNo > totalPage)
            {
                this._PageNo = totalPage;
            }
            // 记录总页数
            result += "<!--all page-->";
            result += "<!--PageNo=" + totalPage + "-->";

            String nodeName = Encode.htmlsp(tempchannel.getChannelName());
            result = result.replaceAll("\\$\\{NODENAME\\}", nodeName);
            // 显示节点名称
            result = result.replaceAll("\\$\\{ITEMSTART\\}",
                (this._itemTotal - this._itemStart + 1) + "");
            result = result.replaceAll("\\$\\{ITEMEND\\}",
                ((this._itemTotal == 0) ? 0 : this._itemTotal - this._itemEnd + 1) + "");
            if (SYSTEM_NAME.equals("WIRELESS"))
            {
                result = result.replaceAll("\\$\\{TOTALPAGE\\}", totalPage + "");
            }
            else
            {
                result = result.replaceAll("\\$\\{TOTALPAGE\\}", (totalPage) + "");
            }
            result = result.replaceAll("\\$\\{CURRENTPAGE\\}", this._PageNo + "");

            // 显示上一页
            String last_page = "上一页";
            String next_page = "下一页";
            if (!this.GetParam("上一页文字").equals(""))
            {
                last_page = this.GetParam("上一页文字");
            }
            if (!this.GetParam("下一页文字").equals(""))
            {
                next_page = this.GetParam("下一页文字");
            }
            if (this._PageNo > 1 && this._PageNo <= totalPage)
            {
                // int prevPage = (this._PageNo+1 == totalPage) ? 0 : this._PageNo + 1;
                int prevPage = this._PageNo - 1;
                /*
                 * if(desc==false){ prevPage = this._PageNo + 1; }
                 */
                result = result.replaceAll(
                    "\\$\\{PREVPAGE\\}",
                    "<a href='" + sharedConstant.getChannelViewUrl(tempchannel.getLogicId()) + "?page="
                                         + prevPage + "" + this.GetParam("翻页控制_链接锚点") + "' "
                                         + this.GetParam("翻页控制_链接样式") + " >" + last_page + "</a>");
            }
            else
            {
                result = result.replaceAll("\\$\\{PREVPAGE\\}", "");
            }
            if (desc)
            {
                if (this.GetParam("翻页控制_显示5页链接").equals("1"))
                {
                    String fivepage = "";
                    int startnum = _PageNo;
                    int endnum = startnum + 4;
                    if (endnum > totalPage)
                    {
                        endnum = totalPage;
                        startnum = endnum - 4;
                        if (startnum <= 0)
                        {
                            startnum = 1;
                        }
                    }
                    for (int i = startnum; i <= endnum; i++ )
                    {
                        if (i == _PageNo)
                        {
                            fivepage += "<a class=\"common_current_page\">" + i + "</a>";
                        }
                        else
                        {
                            fivepage += ("<a href='"
                                         + sharedConstant.getChannelViewUrl(tempchannel.getLogicId()) + "?page=" + i + ""
                                         + this.GetParam("翻页控制_链接锚点") + "' "
                                         + this.GetParam("翻页控制_链接样式") + " >" + (i) + "</a>");
                        }
                        fivepage += ("&nbsp;");
                    }
                    result = result.replaceAll("\\$\\{FIVEPAGE\\}", fivepage);
                }
                else if (this.GetParam("翻页控制_显示自定义页链接").equals("1"))
                {
                    String fivepage = "";
                    int startnum = _PageNo - 2;
                    if (startnum <= 0) startnum = 1;
                    int pageNum = 4;
                    String pageStr = this.GetParam("翻页控制_显示自定义页数量");
                    if (pageStr != null && pageStr.length() > 0)
                    {
                        pageNum = Integer.parseInt(pageStr);
                    }
                    int endnum = startnum + pageNum;
                    if (endnum > totalPage - 1)
                    {
                        endnum = totalPage - 1;
                        startnum = (totalPage - pageNum) <= 0 ? 1 : (totalPage - pageNum);
                    }

                    for (int i = startnum; i <= endnum + 1; i++ )
                    {
                        if (i == _PageNo)
                        {
                            fivepage += "<a class=\"common_current_page\">" + i + "</a>";
                        }
                        else
                        {
                            fivepage += ("<a href='"
                                         + sharedConstant.getChannelViewUrl(tempchannel.getLogicId()) + "?page=" + i + ""
                                         + this.GetParam("翻页控制_链接锚点") + "' "
                                         + this.GetParam("翻页控制_链接样式") + " >" + (i) + "</a>");
                        }
                        fivepage += ("&nbsp;");
                    }
                    result = result.replaceAll("\\$\\{CUSTOMPAGE\\}", fivepage);
                }
                else
                {
                    result = result.replaceAll("\\$\\{FIVEPAGE\\}", "");
                }
            }
            else
            {
                if (this.GetParam("翻页控制_显示5页链接").equals("1"))
                {
                    String fivepage = "";
                    int startnum = _PageNo - 2;
                    if (startnum <= 0) startnum = 0;
                    int endnum = startnum + 4;
                    if (endnum > totalPage - 1)
                    {
                        endnum = totalPage - 1;
                    }
                    for (int i = startnum + 1; i <= endnum + 1; i++ )
                    {
                        if (i == _PageNo)
                        {
                            fivepage += "<a class=\"common_current_page\">" + i + "</a>";
                        }
                        else
                        {
                            fivepage += ("<a href='"
                                         + sharedConstant.getChannelViewUrl(tempchannel.getLogicId()) + "?page=" + i + "' "
                                         + this.GetParam("翻页控制_链接样式") + " >" + (i) + "</a>");
                        }
                        fivepage += ("&nbsp;");
                    }
                    result = result.replaceAll("\\$\\{FIVEPAGE\\}", fivepage);
                }
                else if (this.GetParam("翻页控制_显示自定义页链接").equals("1"))
                {
                    String fivepage = "";
                    int startnum = _PageNo - 2;
                    if (startnum <= 0) startnum = 1;
                    int pageNum = 4;
                    String pageStr = this.GetParam("翻页控制_显示自定义页数量");
                    if (pageStr != null && pageStr.length() > 0)
                    {
                        pageNum = Integer.parseInt(pageStr);
                    }
                    int endnum = startnum + pageNum;
                    if (endnum > totalPage - 1)
                    {
                        endnum = totalPage - 1;
                        startnum = (totalPage - pageNum) <= 0 ? 1 : (totalPage - pageNum);
                    }

                    for (int i = startnum; i <= endnum + 1; i++ )
                    {
                        if (i == _PageNo)
                        {
                            fivepage += "<a class=\"common_current_page\">" + i + "</a>";
                        }
                        else
                        {
                            fivepage += ("<a href='"
                                         + sharedConstant.getChannelViewUrl(tempchannel.getLogicId()) + "?page=" + i + ""
                                         + this.GetParam("翻页控制_链接锚点") + "' "
                                         + this.GetParam("翻页控制_链接样式") + " >" + (i) + "</a>");
                        }
                        fivepage += ("&nbsp;");
                    }
                    result = result.replaceAll("\\$\\{CUSTOMPAGE\\}", fivepage);
                }
                else
                {
                    result = result.replaceAll("\\$\\{FIVEPAGE\\}", "");
                }
            }
            // 显示下一页

            // 默认倒序翻页
            if (desc)
            {
                // int nextPage = (this._PageNo == 0) ? totalPage-1 : this._PageNo - 1;
                int nextPage = this._PageNo + 1;
                if (nextPage > 1 && nextPage <= totalPage)
                {
                    result = result.replaceAll(
                        "\\$\\{NEXTPAGE\\}",
                        "<a href='" + sharedConstant.getChannelViewUrl(tempchannel.getLogicId()) + "?page="
                                             + nextPage + "" + this.GetParam("翻页控制_链接锚点") + "' "
                                             + this.GetParam("翻页控制_链接样式") + " >" + next_page
                                             + "</a>");
                }
                else
                {
                    result = result.replaceAll("\\$\\{NEXTPAGE\\}", "");
                }
            }
            else
            {
                // int nextPage = (this._PageNo <=0) ? 1 : this._PageNo + 1;
                int nextPage = this._PageNo + 1;
                if (nextPage > 1 && nextPage <= totalPage)
                {
                    result = result.replaceAll(
                        "\\$\\{NEXTPAGE\\}",
                        "<a href='" + sharedConstant.getChannelViewUrl(tempchannel.getLogicId()) + "?page="
                                             + nextPage + "" + this.GetParam("翻页控制_链接锚点") + "' "
                                             + this.GetParam("翻页控制_链接样式") + " >" + next_page
                                             + "</a>");
                }
                else
                {
                    result = result.replaceAll("\\$\\{NEXTPAGE\\}", "");
                }
            }

            // 首页、末页
            String first_page = "首页";
            String end_page = "尾页";
            if (!this.GetParam("首页文字").equals(""))
            {
                first_page = this.GetParam("首页文字");
            }
            if (!this.GetParam("尾页文字").equals(""))
            {
                end_page = this.GetParam("尾页文字");
            }
            if (desc)
            {
                result = result.replaceAll(
                    "\\$\\{ENDPAGE\\}",
                    "<a href='/cms/template/ChannelView.jsp?id=" + tempchannel.getLogicId()
                                        + "&pageNo=" + totalPage + "' "
                                        + this.GetParam("翻页控制_链接样式") + " >" + end_page + "</a>");
                result = result.replaceAll(
                    "\\$\\{FIRSTPAGE\\}",
                    "<a href='/cms/template/ChannelView.jsp?id=" + tempchannel.getLogicId()
                                          + "&pageNo=1' " + this.GetParam("翻页控制_链接样式") + " >"
                                          + first_page + "</a>");
            }
            else
            {
                result = result.replaceAll(
                    "\\$\\{FIRSTPAGE\\}",
                    "<a href='/cms/template/ChannelView.jsp?id=" + tempchannel.getLogicId()
                                          + "&pageNo=0' " + this.GetParam("翻页控制_链接样式") + " >"
                                          + first_page + "</a>");
                result = result.replaceAll(
                    "\\$\\{ENDPAGE\\}",
                    "<a href='/cms/template/ChannelView.jsp?id=" + tempchannel.getLogicId()
                                        + "&pageNo=" + (totalPage < 0 ? 1 : totalPage) + "' "
                                        + this.GetParam("翻页控制_链接样式") + " >" + end_page + "</a>");
            }

            result = result.replaceAll("\\$\\{NODEID\\}", "" + tempchannel.getLogicId());

            return result;
        }
        else
        {
            StringBuffer s = new StringBuffer();
            int totalPage = (int)Math.ceil(((double)this._itemTotal) / this._PageCount);
//			if (totalPage > 1 && this._itemTotal % this._PageCount == 0) {
//				totalPage -= 1;
//			}
            if (totalPage <= 0) totalPage = 1;
            if (this._PageNo > totalPage)
            {
                this._PageNo = totalPage;
            }
            s.append(this.GetParam("翻页控制_头"));
            s.append("<table width='100%' border='0'><tr>");
            // 显示节点名称
            if (!this.GetParam("翻页控制_是否显示节点名称").equals("0"))
            {
                s.append("<td class='t14l12redb'>" + Encode.htmlsp(tempchannel.getChannelName())
                         + "</td>");
            }
            if (this.GetParam("翻页控制_图片").length() > 0)
            {
                s.append("<td>" + this.GetParam("翻页控制_图片") + "</td>");
                // 显示“最新xx条”
            }
            /**
             * yangsong by 2006-06-13 增加新闻列表翻页的显示
             */
            if (Converter.str2Int(this.GetParam("新闻_自动新闻_最大新闻条数")) > 0
                && this.GetParam("新闻_是否不显示最大条数").equals(""))
            {
                s.append("<td>最新 " + this.GetParam("新闻_自动新闻_最大新闻条数") + " 条</td>");
            }
            // 记录总页数
            s.append("<!--all page-->");
            s.append("<!--PageNo=" + totalPage + "-->");
            s.append("<td align='right' nowrap " + this.GetParam("翻页控制_列样式") + " >");
            // s.append("共" + this._itemTotal + "条 第" + this._itemStart + "条 － 第" + this._itemEnd +
            // "条&nbsp;");
            if (this.GetParam("新闻_是否不显示多少条").equals(""))
            {
                s.append(" 第" + (this._itemTotal - this._itemStart + 1) + "条 － 第"
                         + ((this._itemTotal == 0) ? 0 : this._itemTotal - this._itemEnd + 1)
                         + "条&nbsp;");
            }
            // 显示上一页
            String last_page = "上一页";
            String next_page = "下一页";
            if (!this.GetParam("上一页文字").equals(""))
            {
                last_page = this.GetParam("上一页文字");
            }
            if (!this.GetParam("下一页文字").equals(""))
            {
                next_page = this.GetParam("下一页文字");
            }
            // 默认倒序翻页

            if (desc)
            {
                if (this._PageNo > 1 && this._PageNo <= totalPage)
                {
                    int prevPage = this._PageNo - 1;
                    s.append("<a href='" + sharedConstant.getChannelViewUrl(tempchannel.getLogicId())
                             + "?page=" + prevPage + "' " + this.GetParam("翻页控制_链接样式") + " >"
                             + last_page + "</a>");
                }
                else
                {
                    s.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                }
            }
            else
            {
                s.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            }
            s.append("&nbsp;");
            if (desc)
            {
                if (this.GetParam("翻页控制_显示5页链接").equals("1"))
                {
                    int startnum = totalPage;
                    if (_PageNo > 0) startnum = _PageNo + 1;
                    if (startnum < 5) startnum = 5;
                    if (startnum >= totalPage)
                    {
                        startnum = totalPage;
                    }
                    int endnum = (startnum - 5) >= 0 ? startnum - 5 : 0;
                    for (int i = startnum; i > endnum; i-- )
                    {
                        s.append("<a href='" + sharedConstant.getChannelViewUrl(tempchannel.getLogicId())
                                 + "？page=" + i + "' " + this.GetParam("翻页控制_链接样式") + " >"
                                 + (totalPage - i + 1) + "</a>");
                        s.append("&nbsp;");
                    }
                }
            }
            else
            {
                if (this.GetParam("翻页控制_显示5页链接").equals("1"))
                {
                    int startnum = _PageNo - 2;
                    if (startnum < 0) startnum = 0;
                    int endnum = startnum + 4;
                    if (endnum > totalPage)
                    {
                        endnum = totalPage;
                    }
                    for (int i = startnum; i <= endnum; i++ )
                    {
                        s.append("<a href='" + sharedConstant.getChannelViewUrl(tempchannel.getLogicId())
                                 + "?page=" + i + "' " + this.GetParam("翻页控制_链接样式") + " >"
                                 + (i + 1) + "</a>");
                        s.append("&nbsp;");
                    }
                }
            }
            // 显示下一页
            // 默认倒序翻页

            if (desc)
            {
                // int nextPage = (this._PageNo == 0) ? totalPage-1 : this._PageNo - 1;
                int nextPage = this._PageNo + 1;
                if (nextPage > 1 && nextPage <= totalPage)
                {
                    s.append("<a href='" + sharedConstant.getChannelViewUrl(tempchannel.getLogicId())
                             + "?page=" + nextPage + "' " + this.GetParam("翻页控制_链接样式") + " >"
                             + next_page + "</a>");
                }
                else
                {
                    s.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                }
            }
            else
            {
                // int nextPage = (this._PageNo <=0) ? 1 : this._PageNo + 1;
                int nextPage = this._PageNo + 1;
                if (nextPage > 1 && nextPage <= totalPage)
                {
                    s.append("<a href='" + sharedConstant.getChannelViewUrl(tempchannel.getLogicId())
                             + "?page=" + nextPage + "' " + this.GetParam("翻页控制_链接样式") + " >"
                             + next_page + "</a>");
                }
                else
                {
                    s.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                }
            }

            /*
             * if (desc) { if (this._PageNo > 1 || this._PageNo == 0 && this._itemTotal >
             * this._PageCount) { int nextPage = (this._PageNo == 0) ? totalPage : this._PageNo -
             * 1; s.append("<a href='ChannelView.shtml?id=" + tempchannel.getNodeId() + "&pageNo="
             * + nextPage + "' " + this.GetParam("翻页控制_链接样式") + " >" + next_page + "</a>"); } else
             * { s.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"); } } else { if (totalPage >
             * this._PageNo) { int nextPage = this._PageNo + 1;
             * s.append("<a href='ChannelView.shtml?id=" + tempchannel.getNodeId() + "&pageNo=" +
             * nextPage + "' " + this.GetParam("翻页控制_链接样式") + " >" + next_page + "</a>"); } else {
             * s.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"); } }
             */
            String pagedefault = "<img src=\"/img/3line.gif\" width=\"100%\" height=\"15\"><br>";
            if (this.GetParam("翻页控制_分隔html代码").length() > 0)
            {
                pagedefault = this.GetParam("翻页控制_分隔html代码");
            }
            s.append("</td></tr></table>" + pagedefault);
            return s.toString().replaceAll("\\$\\{NODEID\\}", "" + tempchannel.getLogicId())
                   + this.GetParam("翻页控制_尾");
        }
    }

    protected String showPageController1()
    {
        return showPageController1(-1, true);
    }

    protected String showPageController1(int node_id, boolean desc)
    {
        Channel tempchannel = this.getChannel();
        if (node_id > 0)
        {
            tempchannel = getChannel(node_id);
        }

        if (this.GetParam("翻页_新样式").equals("1"))
        {
            String result = this.GetParam("翻页_HTML代码1");
            int totalPage = (int)Math.ceil(((double)this._itemTotal) / this._PageCount);
//			if (totalPage > 1 && this._itemTotal % this._PageCount == 0) {
//				totalPage -= 1;
//			}
            if (totalPage <= 0) totalPage = 1;
            if (this._PageNo > totalPage)
            {
                this._PageNo = totalPage;
            }
            // 记录总页数
            result += "<!--all page-->";
            result += "<!--PageNo=" + totalPage + "-->";

            String nodeName = Encode.htmlsp(tempchannel.getChannelName());
            result = result.replaceAll("\\$\\{NODENAME\\}", nodeName);
            // 显示节点名称
            result = result.replaceAll("\\$\\{ITEMSTART\\}",
                (this._itemTotal - this._itemStart + 1) + "");
            result = result.replaceAll("\\$\\{ITEMEND\\}",
                ((this._itemTotal == 0) ? 0 : this._itemTotal - this._itemEnd + 1) + "");

            if (SYSTEM_NAME.equals("WIRELESS"))
            {
                result = result.replaceAll("\\$\\{TOTALPAGE\\}", totalPage + "");
            }
            else
            {
                result = result.replaceAll("\\$\\{TOTALPAGE\\}", (totalPage) + "");
            }
            result = result.replaceAll("\\$\\{CURRENTPAGE\\}", this._PageNo + "");

            // 显示上一页
            String last_page = "上一页";
            String next_page = "下一页";
            if (!this.GetParam("上一页文字").equals(""))
            {
                last_page = this.GetParam("上一页文字");
            }
            if (!this.GetParam("下一页文字").equals(""))
            {
                next_page = this.GetParam("下一页文字");
            }
            if (this._PageNo > 1 && this._PageNo <= totalPage)
            {
                // int prevPage = (this._PageNo+1 == totalPage) ? 0 : this._PageNo + 1;
                int prevPage = this._PageNo - 1;
                /*
                 * if(desc==false){ prevPage = this._PageNo + 1; }
                 */
                result = result.replaceAll(
                    "\\$\\{PREVPAGE\\}",
                    "<a href='" + sharedConstant.getChannelViewUrl(tempchannel.getLogicId()) + "?page="
                                         + prevPage + "' " + this.GetParam("翻页控制_链接样式") + " >"
                                         + last_page + "</a>");
            }
            else
            {
                result = result.replaceAll("\\$\\{PREVPAGE\\}", "");
            }
            if (desc)
            {
                if (this.GetParam("翻页控制_显示5页链接").equals("1"))
                {
                    String fivepage = "";
                    int startnum = totalPage;
                    if (_PageNo > 0) startnum = _PageNo + 1;
                    if (startnum < 5) startnum = 5;
                    if (startnum >= totalPage)
                    {
                        startnum = totalPage;
                    }
                    int endnum = (startnum - 5) >= 0 ? startnum - 5 : 0;
                    for (int i = startnum; i > endnum; i-- )
                    {
                        if (i == _PageNo)
                        {
                            fivepage += (totalPage + 1 - i);
                        }
                        else if (i == totalPage)
                        {
                            fivepage += ("<a href='"
                                         + sharedConstant.getChannelViewUrl(tempchannel.getLogicId()) + "' "
                                         + this.GetParam("翻页控制_链接样式") + " >" + (totalPage - i + 1)
                                         + "</a>");
                        }
                        else
                        {
                            fivepage += ("<a href='"
                                         + sharedConstant.getChannelViewUrl(tempchannel.getLogicId()) + "?page=" + i + "' "
                                         + this.GetParam("翻页控制_链接样式") + " >" + (totalPage - i + 1)
                                         + "</a>");
                        }
                        fivepage += ("&nbsp;");
                    }
                    result = result.replaceAll("\\$\\{FIVEPAGE\\}", fivepage);
                }
                else if (this.GetParam("翻页控制_显示自定义页链接").equals("1"))
                {
                    String fivepage = "";
                    int startnum = _PageNo - 2;
                    if (startnum <= 0) startnum = 1;
                    int pageNum = 4;
                    String pageStr = this.GetParam("翻页控制_显示自定义页数量");
                    if (pageStr != null && pageStr.length() > 0)
                    {
                        pageNum = Integer.parseInt(pageStr);
                    }
                    int endnum = startnum + pageNum;
                    if (endnum > totalPage - 1)
                    {
                        endnum = totalPage - 1;
                        startnum = (totalPage - pageNum) <= 0 ? 1 : (totalPage - pageNum);
                    }

                    for (int i = startnum; i <= endnum + 1; i++ )
                    {
                        if (i == _PageNo)
                        {
                            fivepage += "<a class=\"common_current_page\">" + i + "</a>";
                        }
                        else
                        {
                            fivepage += ("<a href='"
                                         + sharedConstant.getChannelViewUrl(tempchannel.getLogicId()) + "?page=" + i + ""
                                         + this.GetParam("翻页控制_链接锚点") + "' "
                                         + this.GetParam("翻页控制_链接样式") + " >" + (i) + "</a>");
                        }
                        fivepage += ("&nbsp;");
                    }
                    result = result.replaceAll("\\$\\{CUSTOMPAGE\\}", fivepage);
                }
                else
                {
                    result = result.replaceAll("\\$\\{FIVEPAGE\\}", "");
                }
            }
            else
            {
                if (this.GetParam("翻页控制_显示5页链接").equals("1"))
                {
                    String fivepage = "";
                    int startnum = _PageNo - 2;
                    if (startnum <= 0) startnum = 0;
                    int endnum = startnum + 4;
                    if (endnum > totalPage - 1)
                    {
                        endnum = totalPage - 1;
                    }
                    for (int i = startnum + 1; i <= endnum + 1; i++ )
                    {
                        if (i == _PageNo)
                        {
                            fivepage += (i);
                        }
                        else
                        {
                            fivepage += ("<a href='"
                                         + sharedConstant.getChannelViewUrl(tempchannel.getLogicId()) + "?page=" + i + "' "
                                         + this.GetParam("翻页控制_链接样式") + " >" + (i) + "</a>");
                        }
                        fivepage += ("&nbsp;");
                    }
                    result = result.replaceAll("\\$\\{FIVEPAGE\\}", fivepage);
                }
                else if (this.GetParam("翻页控制_显示自定义页链接").equals("1"))
                {
                    String fivepage = "";
                    int startnum = _PageNo - 2;
                    if (startnum <= 0) startnum = 1;
                    int pageNum = 4;
                    String pageStr = this.GetParam("翻页控制_显示自定义页数量");
                    if (pageStr != null && pageStr.length() > 0)
                    {
                        pageNum = Integer.parseInt(pageStr);
                    }
                    int endnum = startnum + pageNum;
                    if (endnum > totalPage - 1)
                    {
                        endnum = totalPage - 1;
                        startnum = (totalPage - pageNum) <= 0 ? 1 : (totalPage - pageNum);
                    }

                    for (int i = startnum; i <= endnum + 1; i++ )
                    {
                        if (i == _PageNo)
                        {
                            fivepage += "<a class=\"common_current_page\">" + i + "</a>";
                        }
                        else
                        {
                            fivepage += ("<a href='"
                                         + sharedConstant.getChannelViewUrl(tempchannel.getLogicId()) + "?page=" + i + ""
                                         + this.GetParam("翻页控制_链接锚点") + "' "
                                         + this.GetParam("翻页控制_链接样式") + " >" + (i) + "</a>");
                        }
                        fivepage += ("&nbsp;");
                    }
                    result = result.replaceAll("\\$\\{CUSTOMPAGE\\}", fivepage);
                }
                else
                {
                    result = result.replaceAll("\\$\\{FIVEPAGE\\}", "");
                }
            }
            // 显示下一页

            // 默认倒序翻页
            if (desc)
            {
                // int nextPage = (this._PageNo == 0) ? totalPage-1 : this._PageNo - 1;
                int nextPage = this._PageNo + 1;
                if (nextPage > 1 && nextPage <= totalPage)
                {
                    result = result.replaceAll(
                        "\\$\\{NEXTPAGE\\}",
                        "<a href='" + sharedConstant.getChannelViewUrl(tempchannel.getLogicId()) + "?page="
                                             + nextPage + "' " + this.GetParam("翻页控制_链接样式") + " >"
                                             + next_page + "</a>");
                }
                else
                {
                    result = result.replaceAll("\\$\\{NEXTPAGE\\}", "");
                }
            }
            else
            {
                // int nextPage = (this._PageNo <=0) ? 1 : this._PageNo + 1;
                int nextPage = this._PageNo + 1;
                if (nextPage > 1 && nextPage <= totalPage)
                {
                    result = result.replaceAll(
                        "\\$\\{NEXTPAGE\\}",
                        "<a href='" + sharedConstant.getChannelViewUrl(tempchannel.getLogicId()) + "?page="
                                             + nextPage + "' " + this.GetParam("翻页控制_链接样式") + " >"
                                             + next_page + "</a>");
                }
                else
                {
                    result = result.replaceAll("\\$\\{NEXTPAGE\\}", "");
                }
            }

            // 首页、末页
            String first_page = "首页";
            String end_page = "尾页";
            if (!this.GetParam("首页文字").equals(""))
            {
                first_page = this.GetParam("首页文字");
            }
            if (!this.GetParam("尾页文字").equals(""))
            {
                end_page = this.GetParam("尾页文字");
            }
            if (desc)
            {
                result = result.replaceAll(
                    "\\$\\{ENDPAGE\\}",
                    "<a href='" + sharedConstant.getChannelViewUrl(tempchannel.getLogicId())
                                        + "?page=" + totalPage + "' "
                                        + this.GetParam("翻页控制_链接样式") + " >" + end_page + "</a>");
                result = result.replaceAll(
                    "\\$\\{FIRSTPAGE\\}",
                    "<a href='" + sharedConstant.getChannelViewUrl(tempchannel.getLogicId())
                                          + "?page=1' " + this.GetParam("翻页控制_链接样式") + " >"
                                          + first_page + "</a>");
            }
            else
            {
                result = result.replaceAll(
                    "\\$\\{FIRSTPAGE\\}",
                    "<a href='" + sharedConstant.getChannelViewUrl(tempchannel.getLogicId())
                                          + "?page=0' " + this.GetParam("翻页控制_链接样式") + " >"
                                          + first_page + "</a>");
                result = result.replaceAll(
                    "\\$\\{ENDPAGE\\}",
                    "<a href='" + sharedConstant.getChannelViewUrl(tempchannel.getLogicId())
                                        + "?page=" + ((totalPage - 1) < 0 ? 0 : (totalPage - 1))
                                        + "' " + this.GetParam("翻页控制_链接样式") + " >" + end_page
                                        + "</a>");
            }

            return result;
        }
        return "";
    }

    /**
     * 显示一张图片
     * 
     * @param curitem
     *            标记内容的一行
     * @return 处理该图片的结果
     */
    protected String showItemPicture(TitlePropVO curitem)
    {
        String res = "";
        String content_id = curitem.getContentId();
        // 处理链接
        String link = "";
        String link_url;
        String pic_desc = curitem.getTextBox();
        String pic_alt = pic_desc;
        int picDescLen = 0;

        if (!StringUtils.isEmpty(content_id))
        {
            // 图片是从新闻中选取过来的
            link = this.getContentURL(curitem.getContentLogicId());
            if (GetParam("图片说明_新闻标题").equals("1"))
            {
                pic_desc = curitem.getTitle();
                pic_alt = pic_desc;
            }
            if (GetParam("图片说明_新闻标题_字数").length() > 0)
            {
                try
                {
                    picDescLen = Integer.parseInt(GetParam("图片说明_新闻标题_字数"));
                    pic_desc = HTML2TextNew.convert(pic_desc);
                    if (pic_desc.length() > picDescLen)
                    {
                        pic_desc = pic_desc.substring(0, picDescLen) + "...";
                    }
                }
                catch (Exception e)
                {
                    // TODO: handle exception
                }
            }
        }
        else
        {
            link_url = curitem.getLinkUrl();
            if (link_url != null && link_url.length() > 0)
            {
                // 图片是自己上传的，并有内容
                link = link_url;
            }

        }

        String imgsrc = "";
        String imgpath = curitem.getTextUrl();
        String imgWith = GetParam("图片_图片属性");
        if (!GetParam("图片_无线图片属性").equals(""))
        {
//            if (SYSTEM_NAME.equals("WIRELESS"))
//            {
//                if (imgpath.contains("http://www.people.com.cn"))
//                {
//                    imgpath = imgpath.replace("http://www.people.com.cn", "");
//                }
//                String imgWith_WX = GetParam("图片_无线图片属性");
//                String imgType_WX = GetParam("无线图片裁剪方式") == null ? "" : GetParam("无线图片裁剪方式");
//                if (imgType_WX.equals(""))
//                {
//                    imgType_WX = "thumbs";
//                }
//                imgpath = PICSEREVER + "/" + imgType_WX + "/" + imgWith_WX + "/data/cms" + imgpath;
//            }
        }
        if (isAbsolutePath)
        {
            ChannelObj tempChannel = new ChannelObj(this.channel);
            imgpath = "http://" + tempChannel.getChannelUrl().getDomain() + imgpath;
        }
        // 处理图片src
        if (imgpath != null && imgpath.toLowerCase().endsWith(".swf"))
        {
            imgsrc = "<embed src=\"" + imgpath
                     + "\" type=\"application/x-shockwave-flash\" scale=\"\" play=\"true\" loop=\"true\" menu=\"true\" "
                     + this.GetParam("图片_图片属性") + "></embed>";
        }
        else
        {
            String floatCss = "";
            if (curitem != null && curitem.getImgFloatType() != null)
            {
                if (curitem.getImgFloatType() == 1)
                {
                    floatCss = "style=\"float: left;\"";
                }
                else if (curitem.getImgFloatType() == 2)
                {
                    floatCss = "style=\"float: right;\"";
                }
            }
            if (pic_desc != null && pic_desc.length() > 0
                && !this.GetParam("图片说明_是否作为图片的ALT").equals("0"))
            {
                if (isMongol)
                {
                    imgsrc = "<img " + floatCss + " src=\"" + imgpath + "\" " + imgWith + " alt=\""
                             + Encode.htmlMongolTitle(pic_alt) + "\"/>";
                }
                else
                {
                    imgsrc = "<img " + floatCss + " src=\"" + imgpath + "\" " + imgWith + " alt=\""
                             + HTML2TextNew.convertAlt(Encode.htmlsp(pic_alt)) + "\"/>";

                }
            }
            else
            {
                imgsrc = "<img " + floatCss + " src=\"" + imgpath + "\" " + imgWith + "/>";
            }
        }
        // 处理图片标题
        String pic_title = (curitem.getTitle() == null) ? "" : curitem.getTitle();
        if (this.GetParam("图片_标题_是否显示").equals("1"))
        {
            if (this.GetParam("是否外文版专用").equals("Foreign"))
            {
                res += this.GetParam("图片_标题_行头html代码") + Encode.htmlsp_nospace(pic_title)
                       + this.GetParam("图片_标题_行尾html代码");
            }
            else
            {
                if (this.GetParam("图片_标题_是否带链接").equals("1"))
                {
                    if (!link.equals(""))
                    {
                        res += this.GetParam("图片_标题_行头html代码") + "<a href=\"" + link + "\" "
                               + this.GetParam("图片_链接属性") + ">" + Encode.htmlsp(pic_title) + "</a>"
                               + this.GetParam("图片_标题_行尾html代码");
                    }
                    else
                    {
                        res += this.GetParam("图片_标题_行头html代码") + Encode.htmlsp(pic_title)
                               + this.GetParam("图片_标题_行尾html代码");
                    }
                }
                else
                {
                    res += this.GetParam("图片_标题_行头html代码") + Encode.htmlsp(pic_title)
                           + this.GetParam("图片_标题_行尾html代码");
                }
            }
        }

        if (!link.equals(""))
        {
            // 图片有链接
            if (this.GetParam("图片_链接唯一").equals("1"))
            {
                res += "<a href=\"" + link + "\" " + this.GetParam("图片_链接属性") + ">" + imgsrc;
                if (!this.GetParam("图片_是否显示图片说明").equals(""))
                {
                    if (this.GetParam("是否外文版专用").equals("Foreign") || isMongol) // 外文版专用
                    {
                        res += this.GetParam("图片说明_行头html代码");
                        res += (pic_desc != null && pic_desc.length() > 0) ? Encode.htmlsp_nospace(
                            pic_desc) : this.GetParam("图片说明_默认内容");
                        res += this.GetParam("图片说明_行尾html代码");
                    }
                    else
                    {
                        res += this.GetParam("图片说明_行头html代码");
                        res += (pic_desc != null && pic_desc.length() > 0) ? Encode.htmlsp(
                            pic_desc) : this.GetParam("图片说明_默认内容");
                        res += this.GetParam("图片说明_行尾html代码");
                    }
                }
                res += "</a>";
            }
            else
            {
                res += "<a href=\"" + link + "\" " + this.GetParam("图片_链接属性") + ">" + imgsrc
                       + "</a>";
                if (!this.GetParam("图片_是否显示图片说明").equals(""))
                {
                    if (this.GetParam("是否外文版专用").equals("Foreign") || isMongol) // 外文版专用
                    {
                        res += this.GetParam("图片说明_行头html代码");
                        if (this.GetParam("图片说明_是否带链接").equals("0") || pic_desc == null
                            || pic_desc.length() == 0)
                        {
                            res += (pic_desc != null
                                    && pic_desc.length() > 0) ? Encode.htmlsp_nospace(
                                        pic_desc) : this.GetParam("图片说明_默认内容");
                        }
                        else
                        {
                            res += "<a href=\"" + link + "\" " + this.GetParam("图片_链接属性") + ">"
                                   + Encode.htmlsp_nospace(pic_desc) + "</a>";
                        }
                        res += this.GetParam("图片说明_行尾html代码");
                    }
                    else
                    {
                        res += this.GetParam("图片说明_行头html代码");
                        if (this.GetParam("图片说明_是否带链接").equals("0") || pic_desc == null
                            || pic_desc.length() == 0)
                        {
                            res += (pic_desc != null && pic_desc.length() > 0) ? Encode.htmlsp(
                                pic_desc) : this.GetParam("图片说明_默认内容");
                        }
                        else
                        {
                            if (this.GetParam("新闻_标题_链接鼠标提示").equals("1"))
                            {
                                res += "<a href=\"" + link + "\" " + this.GetParam("图片_链接属性")
                                       + " title=\""
                                       + HTML2TextNew.convertAlt(Encode.htmlsp(pic_desc)) + "\">"
                                       + Encode.htmlsp(pic_desc) + "</a>";
                            }
                            else
                            {
                                res += "<a href=\"" + link + "\" " + this.GetParam("图片_链接属性") + ">"
                                       + Encode.htmlsp(pic_desc) + "</a>";
                            }
                        }
                        res += this.GetParam("图片说明_行尾html代码");
                    }
                }
            }
        }
        else
        {
            // 图片无链接
            if (imgpath != null && this.GetParam("图片_只显示图片地址").equals("1"))
            {
                res += imgpath;
            }
            else
            {
                res += imgsrc;
            }
            if (!this.GetParam("图片_是否显示图片说明").equals(""))
            {
                if (this.GetParam("是否外文版专用").equals("Foreign") || isMongol) // 外文版专用
                {
                    res += this.GetParam("图片说明_行头html代码");
                    res += (pic_desc != null && pic_desc.length() > 0) ? Encode.htmlsp_nospace(
                        pic_desc) : this.GetParam("图片说明_默认内容");
                    res += this.GetParam("图片说明_行尾html代码");
                }
                else
                {
                    res += this.GetParam("图片说明_行头html代码");
                    res += (pic_desc != null && pic_desc.length() > 0) ? Encode.htmlsp(
                        pic_desc) : this.GetParam("图片说明_默认内容");
                    res += this.GetParam("图片说明_行尾html代码");
                }
            }
        }

        // 处理图片标题
        if (this.GetParam("图片_标题_是否显示").equals("2"))
        {
            res += this.GetParam("图片_标题_行头html代码") + Encode.htmlsp(pic_title)
                   + this.GetParam("图片_标题_行尾html代码");
        }
        if (!this.GetParam("新闻_标题_日期格式").equals("") && this.GetParam("图片_标题_不显示日期").equals(""))
        {
            // 新闻_标题日期格式非空表示需要显示日期
            res += (" <center>"
                    + GetDateStr(GetParam("新闻_标题_日期格式"), DateUtil.asDate(curitem.getPublishTime()))
                    + "</center>");
        }

        String newhtml = GetParam("图片_新样式HTML代码");
        if (newhtml.length() > 0)
        {
            if (this.GetParam("是否外文版专用").equals("Foreign") || isMongol) // 外文版专用
            {
                pic_desc = Encode.htmlsp_nospace(pic_desc);
                pic_title = Encode.htmlsp_nospace(pic_title);
            }
            else
            {
                pic_desc = Encode.htmlsp(pic_desc);
                pic_title = Encode.htmlsp(pic_title);
            }
            if (isMongol)
            {
                pic_alt = Encode.htmlMongolTitle(pic_alt);
            }
            else
            {
                pic_alt = HTML2TextNew.convertAlt(Encode.htmlsp(pic_alt));

            }
            if (newhtml.indexOf("${IMG}") >= 0)
            {
                newhtml = newhtml.replaceAll("\\$\\{IMG\\}", imgpath);
            }
            if (newhtml.indexOf("${TITLE}") >= 0)
            {
                String titleStr = pic_title.toString().replaceAll("\\$", "\\\\\\$");
                newhtml = newhtml.replaceAll("\\$\\{TITLE\\}", titleStr);
            }
            if (newhtml.indexOf("${LINK}") >= 0)
            {
                newhtml = newhtml.replaceAll("\\$\\{LINK\\}", link);
            }
            if (newhtml.indexOf("${DESC}") >= 0)
            {
                newhtml = newhtml.replaceAll("\\$\\{DESC\\}", pic_desc);
            }
            if (newhtml.indexOf("${ALT}") >= 0)
            {
                newhtml = newhtml.replaceAll("\\$\\{ALT\\}", pic_alt);
            }
            return newhtml;
        }
        else
        {
            return res;
        }
    }

    /**
     * 显示一行文字
     * 
     * @param curitem
     *            标记内容的一行
     * @return 处理文字的结果
     */
    protected String showItemText(TitlePropVO curitem)
    {
        String res = "";
        // 处理文字标题
        if (!this.GetParam("文字_是否输出标题").equals(""))
        {
            res += this.GetParam("文字_标题头html代码");
            // res += Encode.htmlsp(curitem.getColumn("TITLE").asString());
            res += Encode.htmlsp(curitem.getTitle());
            res += this.GetParam("文字_标题尾html代码");
        }
        // 处理文字内容
        String textbox = curitem.getTextBox();
        if (textbox != null)
        {
            if (textbox.indexOf("&$") >= 0)
            {
                res += Encode.htmlsp(textbox);
            }
            else
            {
                res += textbox;
            }
        }

        if (!this.GetParam("新闻_标题_日期格式").equals(""))
        {
            // 新闻_标题日期格式非空表示需要显示日期
            res += (" " + GetDateStr(GetParam("新闻_标题_日期格式"),
                DateUtil.asDate(curitem.getPublishTime())));
        }
        return res;
    }

    /**
     * 获得该标记的所有数据
     * 
     * @return 获得标记中手工选择的内容
     */
    protected List getTagValueList()
    {
        QueryWrapper<SplitContent> queryWrapper = new QueryWrapper<SplitContent>();
        queryWrapper.eq("publish_enable", 1);// 0默认，1激活
        queryWrapper.eq("channel_logic_id", nodeId);// 数据所属栏目id
        queryWrapper.eq("model_split_logic_id", ttId);
        queryWrapper.orderByDesc("subindex", "modify_time", "id");
        queryWrapper.last(" limit " + (this._maxOutput + 30));

        List<SplitContent> list = iSplitContentService.list(queryWrapper);

        String cnode_id = "";
        ArrayList itemlist = new ArrayList();
        if (list != null)
        {
            if (list.size() > 0)
            {
                ArrayList<SplitContent> list1 = new ArrayList<SplitContent>();
                SplitContentComparator comp = new SplitContentComparator();
                Collections.sort(list, comp);
                int j = 0;
                for (int i = 0; i < list.size(); i++ )
                {
                    SplitContent titlePropBox = list.get(i);
                   
                    if (j < this._maxOutput)
                    {
                        list1.add(titlePropBox);
                        j++ ;
                    }
/*                    String tempcnodeId = String.valueOf(titlePropBox.getContentChannelId()).trim();
                    Channel cChannel = iChannelService.getById(tempcnodeId);
                    String temp_cnodeId = "" + cChannel.getLogicId();
                    String temp_contentId = String.valueOf(titlePropBox.getContentid()).trim();
                    if (!temp_cnodeId.equals("") && !temp_cnodeId.equals("0")
                        && !temp_cnodeId.equals("null"))
                    {
                        if (i == 0)
                        {
                            cnode_id = String.valueOf(cChannel.getLogicId());
                        }
                        else
                        {
                            if (cnode_id.equals(""))
                            {
                                cnode_id = temp_cnodeId;
                            }
                            else
                            {
                                if (cnode_id.indexOf(temp_cnodeId) == -1)
                                {
                                    cnode_id = cnode_id + "," + temp_cnodeId;
                                }
                            }
                        }
                    }*/
                }
/*                List<Channel> channelList = new ArrayList<Channel>();
                if (!cnode_id.equals(""))
                {
                    String[] channelLogicIds = cnode_id.split(",");
                    channelList = iChannelService.listByLogicIds(channelLogicIds);
                }*/
                for (int i = 0; i < list1.size(); i++ )
                {
                    SplitContent titlePropBox = list1.get(i);
                    String titlePropId = titlePropBox.getId();
                    String templateId = titlePropBox.getModelId();
                    Integer itemType = titlePropBox.getDtype();
                    String title = titlePropBox.getShowTitle();
                    String textBox = titlePropBox.getTextBox();
                    String textUrl = titlePropBox.getTextUrl();
                    Timestamp inputDate = DateUtil.dateToTimestamp(titlePropBox.getCreateTime());
                    Timestamp publishTime = DateUtil.dateToTimestamp(titlePropBox.getCreateTime());
                    Integer sort = titlePropBox.getSubindex();
                    String contentId = titlePropBox.getContentid();
                    Integer contentLogicId = titlePropBox.getContentLogicId();
                    Integer ttId = titlePropBox.getModelSplitLogicId();
                    Integer nodeId = titlePropBox.getChannelLogicId();
                    Integer cnodeId = titlePropBox.getContentChannelLogicId();
                    String linkUrl = titlePropBox.getLinkUrl();
                    // Short br = titlePropBox.getBr();
                    Timestamp createDate = DateUtil.dateToTimestamp(titlePropBox.getCreateTime());
                    String summary = titlePropBox.getTextBox();
                    String channelName = "";
                    int mediaCount = 0;
/*                    if (cnodeId != null)
                    {
                        if (!StringUtils.isEmpty(cnodeId))
                        {
                            for (int m = 0; m < channelList.size(); m++ )
                            {
                                Channel channel = (Channel)channelList.get(m);
                                Integer channelNodeId = channel.getLogicId();
                                if (cnodeId.equals(channelNodeId))
                                {
                                    channelName = channel.getChannelName();
                                    // break;
                                }
                            }
                        }
                        else
                        {
                            channelName = "栏目根节点";
                        }
                    }*/
                    TitlePropVO pojo_VH_Title_Prop = new TitlePropVO();
                    // pojo_VH_Title_Prop.setBr(br);
                    pojo_VH_Title_Prop.setChannelName(channelName);
                    pojo_VH_Title_Prop.setCnodeId(cnodeId);
                    pojo_VH_Title_Prop.setContentId(contentId);
                    pojo_VH_Title_Prop.setContentLogicId(contentLogicId);
                    pojo_VH_Title_Prop.setCreateDate(createDate);
                    pojo_VH_Title_Prop.setInputDate(inputDate);
                    pojo_VH_Title_Prop.setItemType(itemType);
                    pojo_VH_Title_Prop.setLinkUrl(linkUrl);
                    pojo_VH_Title_Prop.setMediaCount(mediaCount);
                    pojo_VH_Title_Prop.setNodeId(nodeId);
                    pojo_VH_Title_Prop.setPublishTime(publishTime);
                    pojo_VH_Title_Prop.setSort(sort);
                    pojo_VH_Title_Prop.setSummary(summary);
                    pojo_VH_Title_Prop.setTextBox(textBox);
                    pojo_VH_Title_Prop.setTextUrl(textUrl);
                    pojo_VH_Title_Prop.setTitle(title);
                    pojo_VH_Title_Prop.setTitlePropId(titlePropId);
                    pojo_VH_Title_Prop.setTtId(ttId);
                    pojo_VH_Title_Prop.setImgFloatType(titlePropBox.getImgFloatType());
                    itemlist.add(pojo_VH_Title_Prop);
                }
                if (itemlist != null)
                {
                    if (itemlist.size() > 0)
                    {
                        // 初始化不自动获取新闻的节点或新闻
                        String res1 = "";
                        String res2 = "";
                        for (int i = 0; i < itemlist.size(); i++ )
                        {
                            TitlePropVO curitem = (TitlePropVO)itemlist.get(i);

                            if (curitem.getItemType() == SplitContent.DTYPE_CONTENT)
                            {
                                if (!res1.equals(""))
                                {
                                    res1 += ",";
                                }
                                res1 += curitem.getContentId();
                            }
//                            if (curitem.getItemType() == 2) //暂不支持栏目
//                            {
//                                if (!res2.equals(""))
//                                {
//                                    res2 += ",";
//                                }
//                                res2 += curitem.getCnodeId();
//                            }
                        }
                        this._notListContentSet = res1;
                        this._notListNodeSet = res2;
                    }
                }
            }
        }
        return itemlist;
    }

    protected List getTagValueList_index()
    {

        SimpleDateFormat TimestampDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        java.util.Date time = cal.getTime();
        String endTime = TimestampDateFormat.format(time) + " 00:00:00";

        QueryWrapper<SplitContent> queryWrapper = new QueryWrapper<SplitContent>();
        queryWrapper.ge("modify_time", endTime);
        queryWrapper.eq("channel_logic_id", nodeId);// 数据所属栏目id
        queryWrapper.eq("model_split_logic_id", ttId);
        queryWrapper.orderByDesc("subindex", "modify_time", "id");
        queryWrapper.last(" limit " + (this._maxOutput + 30));

        List<SplitContent> itemlist = iSplitContentService.list(queryWrapper);

        List itemList1 = new ArrayList();
        if (itemlist != null)
        {
            for (int i = 0; i < itemlist.size(); i++ )
            {
                SplitContent titlePropBox = itemlist.get(i);
                String titlePropId = titlePropBox.getId();
                Integer ttId = titlePropBox.getModelSplitLogicId();
                Integer itemType = titlePropBox.getDtype();
                String title = titlePropBox.getShowTitle();
                String textBox = titlePropBox.getTextBox();
                String textUrl = titlePropBox.getTextUrl();
                // int publishEnable = titlePropBox.getPublishEnable();
                Timestamp inputDate = DateUtil.dateToTimestamp(titlePropBox.getCreateTime());
                Timestamp publishTime = DateUtil.dateToTimestamp(titlePropBox.getCreateTime());
                Integer sort = titlePropBox.getSubindex();
                String contentId = titlePropBox.getContentid();
                Integer contentLogicId= titlePropBox.getContentLogicId();
                Integer nodeId = titlePropBox.getChannelLogicId();
                Integer cnodeId = titlePropBox.getContentChannelLogicId();
                String linkUrl = titlePropBox.getLinkUrl();
                Timestamp createDate = DateUtil.dateToTimestamp(titlePropBox.getCreateTime());
                String summary = titlePropBox.getTextBox();

                TitlePropVO pojo_VH_Title_Prop = new TitlePropVO();
                pojo_VH_Title_Prop.setCnodeId(cnodeId);
                pojo_VH_Title_Prop.setContentId(contentId);
                pojo_VH_Title_Prop.setContentLogicId(contentLogicId);
                pojo_VH_Title_Prop.setCreateDate(createDate);
                pojo_VH_Title_Prop.setInputDate(inputDate);
                pojo_VH_Title_Prop.setItemType(itemType);
                pojo_VH_Title_Prop.setLinkUrl(linkUrl);
                pojo_VH_Title_Prop.setNodeId(nodeId);
                pojo_VH_Title_Prop.setPublishTime(publishTime);
                pojo_VH_Title_Prop.setSort(sort);
                pojo_VH_Title_Prop.setSummary(summary);
                pojo_VH_Title_Prop.setTextBox(textBox);
                pojo_VH_Title_Prop.setTextUrl(textUrl);
                pojo_VH_Title_Prop.setTitle(title);
                pojo_VH_Title_Prop.setTitlePropId(titlePropId);
                pojo_VH_Title_Prop.setTtId(ttId);
                pojo_VH_Title_Prop.setImgFloatType(titlePropBox.getImgFloatType());
                itemList1.add(pojo_VH_Title_Prop);
            }
        }
        return itemList1;
    }

    /**
     * 获得该标记的所有数据
     * 
     * @return 获得标记中手工选择的内容,专门为首页回顾用 yangsong 2006－02－06
     */

    // 分页显示手动插入内容
    protected List getTagValueListPage()
    {

        QueryWrapper<SplitContent> queryWrapper = new QueryWrapper<SplitContent>();
        queryWrapper.ge("publish_enable", 1);
        queryWrapper.eq("channel_logic_id", nodeId);// 数据所属栏目id
        queryWrapper.eq("model_split_logic_id", ttId);
        queryWrapper.orderByDesc("subindex", "modify_time", "id");

        List<SplitContent> itemlist = iSplitContentService.list(queryWrapper);

        List itemList1 = new ArrayList();
        if (itemlist != null)
        {
            if (itemlist.size() > 0)
            {
                SplitContentComparator comp = new SplitContentComparator();
                Collections.sort(itemlist, comp);
                for (int i = 0; i < itemlist.size(); i++ )
                {
                    SplitContent titlePropBox = (SplitContent)itemlist.get(i);
                    String titlePropId = titlePropBox.getId();
                    Integer ttId = titlePropBox.getModelSplitLogicId();
                    Integer itemType = titlePropBox.getDtype();
                    String title = titlePropBox.getShowTitle();
                    String textBox = titlePropBox.getTextBox();
                    String textUrl = titlePropBox.getTextUrl();
                    // int publishEnable = titlePropBox.getPublishEnable();
                    Timestamp inputDate = DateUtil.dateToTimestamp(titlePropBox.getCreateTime());
                    Timestamp publishTime = DateUtil.dateToTimestamp(titlePropBox.getCreateTime());
                    Integer sort = titlePropBox.getSubindex();
                    String contentId = titlePropBox.getContentid();
                    Integer nodeId = titlePropBox.getChannelLogicId();
                    Integer cnodeId = titlePropBox.getContentChannelLogicId();
                    String linkUrl = titlePropBox.getLinkUrl();
                    Timestamp createDate = DateUtil.dateToTimestamp(titlePropBox.getCreateTime());
                    String summary = titlePropBox.getTextBox();

                    TitlePropVO pojo_VH_Title_Prop = new TitlePropVO();
                    pojo_VH_Title_Prop.setCnodeId(cnodeId);
                    pojo_VH_Title_Prop.setContentId(contentId);
                    pojo_VH_Title_Prop.setCreateDate(createDate);
                    pojo_VH_Title_Prop.setInputDate(inputDate);
                    pojo_VH_Title_Prop.setItemType(itemType);
                    pojo_VH_Title_Prop.setLinkUrl(linkUrl);
                    pojo_VH_Title_Prop.setNodeId(nodeId);
                    pojo_VH_Title_Prop.setPublishTime(publishTime);
                    pojo_VH_Title_Prop.setSort(sort);
                    pojo_VH_Title_Prop.setSummary(summary);
                    pojo_VH_Title_Prop.setTextBox(textBox);
                    pojo_VH_Title_Prop.setTextUrl(textUrl);
                    pojo_VH_Title_Prop.setTitle(title);
                    pojo_VH_Title_Prop.setTitlePropId(titlePropId);
                    pojo_VH_Title_Prop.setTtId(ttId);
                    itemList1.add(pojo_VH_Title_Prop);
                }
            }

        }
        List sublist = null;
        if (itemList1 != null & itemList1.size() > 0)
        {
            PageModel pm = new PageModel(itemList1, this._PageCount);
            sublist = pm.getPageList(this._PageNo);// 显示第几页
            this._itemStart = pm.getPageStartRow() + 1;
            this._itemEnd = pm.getPageEndRow();
            this._itemTotal = pm.getTotalRows();
        }
        return sublist;
    }

    /**
     * 从字符串中获得tag列表
     * 
     * @param str
     *            输入字符串
     * @return 输出用逗号分隔的taglist
     */
    protected static String getTagListFromStr(String str)
    {
        String res = "";
        Vector v = Regular.MatchTagList(str);
        for (int i = 0; i < v.size(); i++ )
        {
            // 如果不是第一个，需要先加逗号
            if (!res.equals(""))
            {
                res += ",";
            }
            res += v.get(i);
        }
        return res;
    }

    protected String showMore(String id)
    {
        String res = "";
        if (id.length() > 0)
        {
            res += GetParam("更多_头html代码");
            res += "<a href='" + sharedConstant.getChannelViewUrl(Integer.parseInt(id)) + "' " + GetParam("更多_链接属性") + ">"
                   + GetParam("更多_文字") + "</a>";
            res += GetParam("更多_尾html代码") + "\n";
        }
        return res;
    }

    public String outputManual_tv()
    {
        StringBuffer s = new StringBuffer();
        // 取得标记的内容
        List itemlist = this.getTagValueList();
        // 如果有内容或需要设自动新闻或自动显示节点
        if (this._maxOutput > 0)
        {
            // 输出内容
            int inv = 0;
            if (!GetParam("新闻_输出间隔").equals(""))
            {
                inv = Integer.parseInt(GetParam("新闻_输出间隔"));
            }

            // 显示标记内容
            for (int i = 0; i < itemlist.size(); i++ )
            {
                s.append(this.showManual_tv((TitlePropVO)itemlist.get(i)));
            }

            this._outputCount = itemlist.size();
        }

        return s.toString();
    }

    protected String showManual_tv(TitlePropVO curitem)
    {
        String res = "";
        Integer itemType = curitem.getItemType();
        if (itemType.equals(SplitContent.DTYPE_CONTENT))
        {
            // 内容的是新闻
            // res +="<news>";
            // res +="<resource title=" + curitem.getColumn("title").asString() + " url=" +
            // this.getContent().getFullURL() + "></resource>" + GetParam("新闻_行尾html代码") + "\n";
            res += "<newsresource title=\"" + curitem.getTitle() + "\" url=\""
                   + this.getContentURL(curitem.getContentLogicId())
                   + "\"></newsresource>" + GetParam("新闻_行尾html代码") + "\n";
            // res += GetParam("新闻_行头html代码") + this.showItemNews(curitem) +
            // GetParam("新闻_行尾html代码") + "\n";

            this._maxOutput-- ;
            // res +="</news>";

        }
        else if (itemType.equals(SplitContent.DTYPE_PHOTO))
        {
            // 内容是图片
            // res +="<pic>";
            res += "<picresource title=\"" + curitem.getTitle()
                   + "\" picurl=\"http://tv.people.com.cn" + curitem.getTextUrl() + "\" piclink=\""
                   + curitem.getLinkUrl() + "\"></picresource>" + "\n";;
            // res += this.GetParam("图片_行头html代码") + this.showItemPicture(curitem) +
            // this.GetParam("图片_行尾html代码") + "\n";
            this._maxOutput-- ;
            // res +="</pic>";
        }
        return res;
    }

    // 图片_多列输出_行头html代码
    /**
     * 显示自动节点
     * 
     * @return 返回自动节点的结果
     */
    public String outputAutoNodes()
    {
        String res = "";
        String NodeSet = this.GetParam("节点_自动节点_所属节点ID");
        if (NodeSet != null)
        {
            NodeSet = NodeSet.trim();
        }

        String islevel = this.GetParam("排序顺序");

        String colHtml = this.GetParam("节点_多列输出_列间html代码");
        // 如果有设置自动新闻属性，表示需要显示自动新闻；
        if (!NodeSet.equals("") && this._maxOutput > 0)
        {

            QueryWrapper<Channel> queryWrapper = new QueryWrapper<Channel>();

            if (NodeSet.equals("0"))
            {
                // 如果值为0，表示当前节点
                NodeSet = "" + this.nodeId;
            }
            String[] nodeIds = NodeSet.split(",");
            queryWrapper.in("logic_id", nodeIds);
            queryWrapper.eq("dstatus", 2);
            queryWrapper.eq("status_release", 1);

            if (islevel.equals("1"))
            {
                if (this.GetParam("节点_排序_栏目id顺序").equals("1"))
                {
                    queryWrapper.orderByAsc("logic_id");
                }
                else
                {
                    queryWrapper.orderByDesc("logic_id");
                }

            }
            else
            {
                if (this._notListNodeSet.equals(""))
                {
                    if (this.GetParam("节点_排序_栏目id顺序").equals("1"))
                    {
                        queryWrapper.orderByAsc("logic_id");
                    }
                    else
                    {
                        queryWrapper.orderByDesc("logic_id");
                    }
                }
                else
                {
                    String[] notInNodeIds = this._notListNodeSet.split(",");
                    queryWrapper.notIn("logic_id", notInNodeIds);
                    if (this.GetParam("节点_排序_栏目id顺序").equals("1"))
                    {
                        queryWrapper.orderByAsc("logic_id");
                    }
                    else
                    {
                        queryWrapper.orderByDesc("logic_id");
                    }
                }
            }

            List<Channel> itemlist = iChannelService.list(queryWrapper);

            List list = null;
            if (itemlist != null)
            {
                PageModel pm = new PageModel(itemlist, _maxOutput);
                list = pm.getPageList(this._PageNo);// 显示第几页
                this._itemStart = pm.getPageStartRow() + 1;
                this._itemEnd = pm.getPageEndRow();
                this._itemTotal = pm.getTotalRows();
            }

            String spc = this.showPageController();
            int minOut = 0;
            if (!this.GetParam("节点_自动节点_最少输出数量").equals(""))
            {
                minOut = Integer.parseInt(this.GetParam("节点_自动节点_最少输出数量"));
            }
            // 如果选取的数据 > 最少输出数量， 才输出内容，否则不输出
            if (list != null)
            {
                if (list.size() > minOut)
                {
                    res += this.GetParam("节点_自动节点_头html代码");
                    int i_MultiColumn = 1;
                    if (!this.GetParam("节点_多列输出_列数").equals(""))
                    {
                        int i_t1 = Converter.str2Int(GetParam("节点_多列输出_列数"));
                        if (i_t1 > 0)
                        {
                            i_MultiColumn = i_t1;
                        }
                    }
                    // 显示标记内容
                    for (int i = 0; i < list.size(); i += i_MultiColumn)
                    {
                        // 多列输出中，处理行头
                        if (i_MultiColumn > 1)
                        {
                            res += "<tr>";
                        }
                        for (int j = 0; j < i_MultiColumn; j++ )
                        {
                            if (i + j < list.size())
                            {

                                Channel channel = (Channel)list.get(i + j);
                                TitlePropVO vh_Title_Prop = new TitlePropVO();
                                int cnodeId = channel.getLogicId();
                                String title = channel.getChannelName();
                                vh_Title_Prop.setCnodeId(cnodeId);
                                vh_Title_Prop.setTitle(title);

                                res += GetParam("节点_行头html代码") + this.showItemNode(vh_Title_Prop)
                                       + GetParam("节点_行尾html代码") + "\n";
                            }
                            else
                            {
                                // 多列输出中，处理剩余最后一行后面没有内容部分
                                if (j != 0)
                                {
                                    // res += "<td>&nbsp</td>";
                                    res += "<td></td>";
                                }
                            }
                        }
                        // 多列输出中，处理行尾
                        if (i_MultiColumn > 1)
                        {
                            res += "</tr>";
                        }
                    }
                    res += this.GetParam("节点_自动节点_尾html代码");
                    if (this.GetParam("节点_自动节点_是否显示翻页控制栏").equals("1"))
                    {
                        res += spc;
                    }
                }
            }

        }
        return res;
    }

    /**
     * 根据"新闻_自动新闻_所属栏目ID"的属性输出自动新闻
     * 
     * @return 返回自动新闻处理的结果
     */
    public String outputAutoNews()
    {
        StringBuffer sBuff = new StringBuffer();
        String NodeSet = this.GetParam("新闻_自动新闻_所属栏目ID");

        if (!NodeSet.equals("") && this._maxOutput > 0)
        {
            sBuff.append(this.showNewsList(NodeSet, this._maxOutput));
            if (sBuff.length() > 0)
            {
                sBuff.insert(0, this.GetParam("新闻_自动新闻_头html代码"));
                sBuff.append(this.GetParam("新闻_自动新闻_尾html代码") + "\n");
            }
        }
        return sBuff.toString();
    }

    /**
     * 显示更多
     * 
     * @return 返回“更多”的输出内容
     */
    public String outputMore()
    {
        String res = "";
        if (!this.GetParam("更多_文字").equals("") && !this.GetParam("更多_栏目ID").equals(""))
        {
            String id = GetParam("更多_栏目ID");
            if (id.equals("0"))
            {
                id = "" + this.getChannel().getLogicId();
            }
            res += this.showMore(id);
        }
        return res;
    }

    /**
     * 根据"当前节点_是否显示名称"的属性输出当前节点信息
     * 
     * @return 当前节点的处理结果
     */
    public String outputCurNodeInfo()
    {
        StringBuffer res = new StringBuffer();
        Channel channel = this.getChannel();
        if (!this.GetParam("当前节点_是否显示名称").equals("") && this._maxOutput > 0 && channel != null)
        {
            res.append(this.GetParam("节点_节点名称_头html代码"));
            if (this.GetParam("当前节点_是否按URL编码").length() == 0)
            {
                res.append(Encode.htmlsp(this.getChannel().getChannelName()));
            }
            else
            {
                res.append(URLEncoder.encode(Encode.htmlsp(this.getChannel().getChannelName())));
            }
            res.append(this.GetParam("节点_节点名称_尾html代码") + "\n");
        }
        return res.toString();
    }

    /**
     * 根据"图片_自动图片_所属栏目ID"的属性输出自动图片
     * 
     * @return 返回自动新闻处理的结果
     */
    public String outputAutoPicture()
    {
        String res = "";
        String NodeSet = this.GetParam("图片_自动图片_所属栏目ID");
        String NewsPicNum = this.GetParam("图片_自动新闻图片_是否只显示一张");// 非空只显示1张
        if (!NodeSet.equals("") && this._maxOutput > 0 && NewsPicNum.equals(""))
        {
            res = this.showPictureList5(NodeSet, this._maxOutput);
            if (!res.equals(""))
            {
                res = this.GetParam("图片_自动图片_头html代码") + res + this.GetParam("图片_自动图片_尾html代码")
                      + "\n";
                if (this.GetParam("图片_自动图片_是否显示翻页控制栏").equals("1"))
                {
                    res = this.showPageController() + res;
                }
                else if (this.GetParam("图片_自动图片_是否显示翻页控制栏").equals("2"))
                {
                    res = res + this.showPageController();
                }
            }
        }
        if (!NodeSet.equals("") && this._maxOutput > 0 && !NewsPicNum.equals(""))
        {
            res = this.showPictureList4(NodeSet, this._maxOutput);
            if (!res.equals(""))
            {
                res = this.GetParam("图片_自动图片_头html代码") + res + this.GetParam("图片_自动图片_尾html代码")
                      + "\n";
                if (this.GetParam("图片_自动图片_是否显示翻页控制栏").equals("1"))
                {
                    res = this.showPageController() + res;
                }
                else if (this.GetParam("图片_自动图片_是否显示翻页控制栏").equals("2"))
                {
                    res = res + this.showPageController();
                }
            }
        }
        return res;
    }

    /**
     * 判断当前节点是否有子节点，如果有，则显示自动节点，否则显示自动新闻
     * 
     * @return 输出结果
     */
    public String outputAutoNodeNews()
    {
        String res = "";
        QueryWrapper<Channel> queryWrapper = new QueryWrapper<Channel>();

        queryWrapper.eq("dstatus", Channel.DSTATUS_ENABLED);
        queryWrapper.eq("status_release", Channel.STATUSRELEASE_RELEASED);

        Channel pchannel = iChannelService.getByLogicId(this.nodeId);
        queryWrapper.eq("parentid", pchannel.getLogicId());

        List<Channel> list = iChannelService.list(queryWrapper);
        if (list.size() > 1)
        {
            // 如果有多个子节点，调用自动节点，自动从每个节点下取新闻并显示
            this.addParamValue("新闻_自动新闻_是否显示翻页控制栏", "");
            res += this.outputAutoNodes();
        }
        else if (list.size() == 1)
        {
            // 如果只有一个子节点，调用自动新闻，显示子节点下的新闻
            Channel channel = (Channel)list.get(0);
            String nodeId = String.valueOf(channel.getLogicId());
            this.addParamValue("新闻_自动新闻_所属栏目ID", nodeId);
            this.addParamValue("新闻_自动新闻_是否显示翻页控制栏", "1");
            res += this.outputAutoNews();
        }
        else
        {
            // 如果没有子节点，调用自动新闻，显示当前节点下的新闻
            this.addParamValue("新闻_自动新闻_是否显示翻页控制栏", "1");
            res += this.outputAutoNews();
        }

        return res;
    }

    /**
     * 根据"图片_自动图片_所属栏目ID"的属性输出自动图片，每条新闻只输出一幅图片，输出时连同新闻，并自动提取新闻摘要
     * 
     * @return 返回自动新闻处理的结果
     */
    public String outputAutoPicture2()
    {
        String res = "";
        String NodeSet = this.GetParam("图片_自动图片_所属栏目ID");
        if (!NodeSet.equals("") && this._maxOutput > 0)
        {
            res = this.showPictureList2(NodeSet, this._maxOutput);
            if (!res.equals(""))
            {
                res = this.GetParam("图片_自动图片_头html代码") + res + this.GetParam("图片_自动图片_尾html代码")
                      + "\n";
            }
        }
        return res;
    }

    /**
     * 按图片模板显示图片
     * 
     * @param curitem
     * @return
     */
    protected String showItemPicture2(TitlePropVO curitem)
    {
        String res = this.GetParam("图片_显示模板");
        if (res.length() > 0)
        {
            String content_id = curitem.getContentId();
            // 处理链接
            String link = "";
            if (!StringUtils.isEmpty(content_id))
            {
                // 图片是从新闻中选取过来的
                link = this.getContentURL(curitem.getContentLogicId());
            }
            else
            {
                String link_url = curitem.getLinkUrl();
                if (link_url != null && link_url.length() > 0)
                {
                    // 图片是自己上传的，并有内容
                    link = link_url;
                }
            }
            String pic_desc_source = curitem.getTextBox();

            String pic_desc = "";
            if (this.GetParam("是否外文版专用").equals("Foreign")) // 外文版专用
            {
                pic_desc = Encode.htmlsp_nospace(pic_desc_source);
            }
            else
            {
                pic_desc = Encode.htmlsp(pic_desc_source);
            }

            if (!this.GetParam("图片说明_是否带链接").equals("0") && link.length() > 0
                && pic_desc.length() > 0)
            {
                pic_desc = "<a href=\"" + link + "\" " + this.GetParam("图片_链接属性") + ">" + pic_desc
                           + "</a>";
            }
            String imgsrc = "";

            String imgpath = curitem.getTextUrl();
            String imgWith = GetParam("图片_图片属性");
            if (!GetParam("图片_无线图片属性").equals(""))
            {
//                if (SYSTEM_NAME.equals("WIRELESS"))
//                {
//                    if (imgpath.contains("http://www.people.com.cn"))
//                    {
//                        imgpath = imgpath.replace("http://www.people.com.cn", "");
//                    }
//                    String imgWith_WX = GetParam("图片_无线图片属性");
//                    String imgType_WX = GetParam("无线图片裁剪方式") == null ? "" : GetParam("无线图片裁剪方式");
//                    if (imgType_WX.equals(""))
//                    {
//                        imgType_WX = "thumbs";
//                    }
//                    imgpath = PICSEREVER + "/" + imgType_WX + "/" + imgWith_WX + "/data/cms"
//                              + imgpath;
//                }
            }

            // 处理图片src
            if (pic_desc_source != null && pic_desc_source.length() > 0
                && !this.GetParam("图片说明_是否作为图片的ALT").equals("0"))
            {
                imgsrc = "<img src=\"" + imgpath + "\" " + imgWith + " alt=\""
                         + Encode.javascript(pic_desc_source) + "\">";
            }
            else
            {
                imgsrc = "<img src=\"" + imgpath + "\" " + imgWith + ">";
            }
            if (link.length() > 0)
            {
                imgsrc = "<a href=\"" + link + "\" " + this.GetParam("图片_链接属性") + ">" + imgsrc
                         + "</a>";
            }
            String input_date = "";
            if (!this.GetParam("新闻_标题_日期格式").equals(""))
            {
                // 新闻_标题日期格式非空表示需要显示日期
                input_date = GetDateStr(GetParam("新闻_标题_日期格式"),
                    DateUtil.asDate(curitem.getInputDate()));
            }
            // 处理图片标题
            String pic_title = Encode.htmlsp(curitem.getTitle());
            Vector v = this.MatchNewsTagList(res);
            for (int i = 0; i < v.size(); i++ )
            {
                String s = (String)v.get(i);
                if ("pic_title".equals(s))
                {
                    res = this.ReplaceNewsTag(s, pic_title, res);
                }
                else if ("pic_desc".equals(s))
                {
                    res = this.ReplaceNewsTag(s, pic_desc, res);
                }
                else if ("pic_image".equals(s))
                {
                    res = this.ReplaceNewsTag(s, imgsrc, res);
                }
                else if ("input_date".equals(s))
                {
                    res = this.ReplaceNewsTag(s, input_date, res);
                }
            }
        }
        return res;
    }

    private static Vector MatchNewsTagList(String templateContent)
    {
        Vector v = new Vector();
        try
        {
            RE r = new RE("\\$\\{(.*?)\\}");
            int startIndex = 0;
            while (r.match(templateContent, startIndex))
            {
                v.add(r.getParen(1));
                startIndex = r.getParenEnd(0);
            }
        }
        catch (Exception ex)
        {
            return null;
        }
        return v;
    }

    private static String ReplaceNewsTag(String s, String replaceStr, String templateContent)
    {
        try
        {
            RE r = new RE("\\$\\{" + s + "\\}");
            return r.subst(templateContent, replaceStr);
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    protected String showPictureList2(String NodeSet, int pageSize)
    {
        StringBuffer sBuff = new StringBuffer();
        if (!NodeSet.equals(""))
        {
            if (NodeSet.equals("0"))
            {
                // 如果值为0，表示当前节点
                NodeSet = "" + this.nodeId;
            }
            ContentDetailRequestVO requestParam = new ContentDetailRequestVO();
            requestParam.setDstatus(Content.DSTATUS_PUBLISHED);
            requestParam.setChannelLogicIdInStr(NodeSet);

            int iAutoNewsTimeLimit = Converter.str2Int(GetParam("新闻_自动新闻_时间限制"));
            if (iAutoNewsTimeLimit <= 0) iAutoNewsTimeLimit = 365;
            if (GetParam("是否不限制时间").equals("1")) iAutoNewsTimeLimit = 0;
            if (iAutoNewsTimeLimit > 0)
            {
                SimpleDateFormat TimestampDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DATE, 1);
                cal.roll(Calendar.DATE, -1);
                Date endTime = cal.getTime();
                String endTime1 = TimestampDateFormat.format(endTime) + " 23:59:59";

                cal.add(cal.DATE, -iAutoNewsTimeLimit);
                Date startTime = cal.getTime();
                String startTime1 = TimestampDateFormat.format(startTime) + " 00:00:00";

                requestParam.setCreatetimeStart(startTime1);
                requestParam.setCreatetimeStop(endTime1);
            }
            else
            {
                SimpleDateFormat TimestampDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DATE, 1);
                cal.roll(Calendar.DATE, -1);
                Date endTime = cal.getTime();
                String endTime1 = TimestampDateFormat.format(endTime) + " 23:59:59";

                cal.add(cal.DATE, -60);
                Date startTime = cal.getTime();
                String startTime1 = TimestampDateFormat.format(startTime) + " 00:00:00";

                requestParam.setCreatetimeStart(startTime1);
                requestParam.setCreatetimeStop(endTime1);
            }
            requestParam.setOrderByStr(" createtime desc ");
            if (this.GetParam("新闻_自动新闻_最大新闻条数").length() > 0)
            {
                setLimit(Integer.parseInt(GetParam("新闻_自动新闻_最大新闻条数")));
            }
            else
            {
                setLimit(200);
            }
            Integer limit = getLimit();

            requestParam.setLimit(limit);

            List<ContentDetailVO> itemlist = iContentService.listContentDetails(requestParam);

            if (itemlist != null)
            {
                PageModel pm = new PageModel(itemlist, pageSize);
                List sublist = pm.getPageList(this._PageNo);// 显示第几页

                this._itemStart = pm.getPageStartRow() + 1;
                this._itemEnd = pm.getPageEndRow();
                this._itemTotal = pm.getTotalRows();
                String spc = this.showPageController();

                if (this.GetParam("图片_自动图片_是否显示翻页控制栏").equals("1")
                    || this.GetParam("图片_自动图片_是否显示翻页控制栏").equals("3"))
                {
                    // 输出翻页信息
                    sBuff.append(spc);
                }

                for (int i = 0; i < sublist.size(); i++ )
                {
                    ContentDetailVO pojo_ContentChannel = (ContentDetailVO)sublist.get(i);
                    String content_id = pojo_ContentChannel.getId();
                    Integer contentLogicId = pojo_ContentChannel.getLogicId();

                    QueryWrapper<ContentMedia> queryWrapper = new QueryWrapper<ContentMedia>();
                    queryWrapper.eq("content_id", content_id);
                    queryWrapper.orderByAsc("level", "id");
                    List<ContentMedia> listpic = iContentMediaService.list(queryWrapper);

                    if (listpic.size() > 0)
                    {
                        TitlePropVO curitem = new TitlePropVO();
                        String summary = "";
                        String path = ((ContentMedia)listpic.get(0)).getPath();
                        String title = pojo_ContentChannel.getTitle();
                        Timestamp input_date = DateUtil.dateToTimestamp(
                            pojo_ContentChannel.getCreatetime());
                        curitem.setTextBox(summary);
                        curitem.setTextUrl(path);
                        curitem.setTitle(title);
                        curitem.setContentId(content_id);
                        curitem.setContentLogicId(contentLogicId);
                        curitem.setInputDate(input_date);
                        sBuff.append(this.showItemPicture2(curitem) + "\n");
                    }
                    else
                    {
                        log.error(
                            "content id=" + content_id + " image_count>0 but can't find image");
                    }

                }
                if (this.GetParam("图片_自动图片_是否显示翻页控制栏").equals("2")
                    || this.GetParam("图片_自动图片_是否显示翻页控制栏").equals("3"))
                {
                    // 输出翻页信息
                    sBuff.append(spc);
                }
            }
        }
        return sBuff.toString();
    }

    /**
     * 显示自动节点的图片，每篇文章只显示1张
     * 
     * @param NodeSet
     *            栏目所属的节点
     * @param pageSize
     *            每页输出的条数
     * @return 返回自动图片集
     */

    protected String showPictureList4(String NodeSet, int pageSize)
    {
        String res = "";
        if (!NodeSet.equals(""))
        {
            // 初试化输出列数
            int i_MultiColumn = 1;
            if (!this.GetParam("图片_多列输出_列数").equals(""))
            {
                int i_t1 = Integer.parseInt(GetParam("图片_多列输出_列数"));
                if (i_t1 > 0)
                {
                    i_MultiColumn = i_t1;
                }
            }

            List itemlist = null;
            AutoNewsList autoNewsList = new AutoNewsList();
            if (SharedConstant.REDIS)
            {
                if (this._PageNo > 1)
                {
                    itemlist = autoNewsList.getRedisAutoNewsList(NodeSet, String.valueOf(ttId));// 取redis值
                    if (itemlist == null || itemlist.size() == 0)
                    {// 未取到值得情况
                        itemlist = getSinglePictureList(NodeSet);
                        autoNewsList.setRedisAutoNewsList(NodeSet, String.valueOf(ttId), itemlist);// 保存存redis值
                    }
                }
                else
                {
                    itemlist = getSinglePictureList(NodeSet);
                    autoNewsList.setRedisAutoNewsList(NodeSet, String.valueOf(ttId), itemlist);// 保存存redis值
                }
            }
            else
            {
                itemlist = getSinglePictureList(NodeSet);
            }

            if (itemlist != null)
            {
                PageModel pm = new PageModel(itemlist, pageSize);
                List sublist = pm.getPageList(this._PageNo);// 显示第几页
                this._itemStart = pm.getPageStartRow() + 1;
                this._itemEnd = pm.getPageEndRow();
                this._itemTotal = pm.getTotalRows();
                // 输出内容
                int i_count = 0;
                if (sublist != null)
                {
                    if (sublist.size() > 0)
                    {
                        // 多列输出中，处理行头
                        // if (i_MultiColumn > 1) {
                        // res += this.GetParam("图片_多列输出_行头html代码");
                        // }
                        for (int i = 0; i < sublist.size(); i += i_MultiColumn)
                        {
                            // 多列输出中，处理行头
                            if (i_MultiColumn > 1)
                            {
                                res += this.GetParam("图片_多列输出_行头html代码");
                            }
                            for (int j = 0; j < i_MultiColumn; j++ )
                            {
                                if (j != 0)
                                {
                                    // 多列输出中，处理列间代码
                                    res += this.GetParam("图片_多列输出_列间html代码");
                                }
                                if (i_count < sublist.size())
                                {
                                    TitlePropVO pojo_VH_Title_Prop = (TitlePropVO)sublist.get(
                                        i_count);
                                    res += this.GetParam("图片_行头html代码")
                                           + this.showItemPicture(pojo_VH_Title_Prop)
                                           + this.GetParam("图片_行尾html代码") + "\n";
                                }
                                else
                                {
                                    // 多列输出中，处理剩余最后一行后面没有内容部分
                                    if (j != 0)
                                    {
                                        // res += "&nbsp";
                                        res += "";
                                    }
                                }
                                i_count++ ;
                            }
                            // 多列输出中，处理行尾
                            if (i_MultiColumn > 1)
                            {
                                res += this.GetParam("图片_多列输出_行尾html代码") + "\n";
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    /**
     * 显示自动图片，新闻多张图片全显示
     * 
     * @param NodeSet
     *            栏目所属的节点
     * @param pageSize
     *            每页输出的条数
     * @return 返回自动图片的结果
     */
    protected String showPictureList5(String NodeSet, int pageSize)
    {
        String res = "";
        if (!NodeSet.trim().equals(""))
        {
            // 初试化输出列数
            int i_MultiColumn = 1;
            if (!this.GetParam("图片_多列输出_列数").equals(""))
            {
                int i_t1 = Integer.parseInt(GetParam("图片_多列输出_列数"));
                if (i_t1 > 0)
                {
                    i_MultiColumn = i_t1;
                }
            }
            // 增加redis
            AutoNewsList autoNewsList = new AutoNewsList();
            List itemlist = null;
            if (SharedConstant.REDIS)
            {
                if (this._PageNo > 1)
                {
                    itemlist = autoNewsList.getRedisAutoNewsList(NodeSet, String.valueOf(ttId));// 取redis值
                    if (itemlist == null || itemlist.size() == 0)
                    {// 未取到值得情况
                        itemlist = getEveryPictureList(NodeSet);
                        autoNewsList.setRedisAutoNewsList(NodeSet, String.valueOf(ttId), itemlist);// 保存存redis值
                    }
                }
                else
                {
                    itemlist = getEveryPictureList(NodeSet);
                    autoNewsList.setRedisAutoNewsList(NodeSet, String.valueOf(ttId), itemlist);// 保存存redis值
                }
            }
            else
            {
                itemlist = getEveryPictureList(NodeSet);
            }

            if (itemlist != null)
            {
                PageModel pm = new PageModel(itemlist, pageSize);
                List sublist = pm.getPageList(this._PageNo);// 显示第几页
                this._itemStart = pm.getPageStartRow() + 1;
                this._itemEnd = pm.getPageEndRow();
                this._itemTotal = pm.getTotalRows();

                // 输出内容
                int i_count = 0;
                if (sublist != null)
                {
                    if (sublist.size() > 0)
                    {
                        // 多列输出中，处理行头
                        // if (i_MultiColumn > 1) {
                        // res += this.GetParam("图片_多列输出_行头html代码");
                        // }
                        for (int i = 0; i < sublist.size(); i += i_MultiColumn)
                        {
                            // 多列输出中，处理行头
                            if (i_MultiColumn > 1)
                            {
                                res += this.GetParam("图片_多列输出_行头html代码");
                            }
                            for (int j = 0; j < i_MultiColumn; j++ )
                            {
                                if (j != 0)
                                {
                                    // 多列输出中，处理列间代码
                                    res += this.GetParam("图片_多列输出_列间html代码");
                                }
                                if (i_count < sublist.size())
                                {
                                    TitlePropVO pojo_VH_Title_Prop = (TitlePropVO)sublist.get(
                                        i_count);
                                    res += this.GetParam("图片_行头html代码")
                                           + this.showItemPicture(pojo_VH_Title_Prop)
                                           + this.GetParam("图片_行尾html代码") + "\n";
                                }
                                else
                                {
                                    // 多列输出中，处理剩余最后一行后面没有内容部分
                                    if (j != 0)
                                    {
                                        // res += "&nbsp";
                                        res += "";
                                    }
                                }
                                i_count++ ;
                            }
                            // 多列输出中，处理行尾
                            if (i_MultiColumn > 1)
                            {
                                res += this.GetParam("图片_多列输出_行尾html代码") + "\n";
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    /**
     * 显示自动节点的图片，每篇文章只显示1张
     * 
     * @param NodeSet
     *            栏目所属的节点
     * @return 返回自动图片集
     */

    public List getSinglePictureList(String NodeSet)
    {
        List list = new ArrayList();
        if (!NodeSet.equals(""))
        {
            if (NodeSet.equals("0"))
            {
                // 如果值为0，表示当前节点
                NodeSet = "" + this.nodeId;
            }
            ContentDetailRequestVO searchParam = new ContentDetailRequestVO();
            searchParam.setDstatus(Content.DSTATUS_PUBLISHED);
            searchParam.setChannelLogicIdInStr(NodeSet);

            int iAutoNewsTimeLimit = Converter.str2Int(GetParam("图片_自动图片_时间限制"));
            if (iAutoNewsTimeLimit <= 0) iAutoNewsTimeLimit = 30;
            if (iAutoNewsTimeLimit > 0)
            {
                SimpleDateFormat TimestampDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DATE, 1);
                cal.roll(Calendar.DATE, -1);
                Date endTime = cal.getTime();
                String endTime1 = TimestampDateFormat.format(endTime) + " 23:59:59";

                Calendar cal1 = Calendar.getInstance();
                cal1.add(cal1.DATE, -iAutoNewsTimeLimit);
                Date startTime = cal1.getTime();
                String startTime1 = TimestampDateFormat.format(startTime) + " 00:00:00";
                searchParam.setCreatetimeStart(startTime1);
                searchParam.setCreatetimeStop(endTime1);
                searchParam.setMediaImgCount(0);
                searchParam.setOrderByStr(" createtime desc ");
            }

            long limit = 10;
            if (!GetParam("图片_输出上限").equals(""))
            {
                limit = Integer.parseInt(GetParam("图片_输出上限"));
            }

            searchParam.setLimit((int)limit * 5);;
            List<ContentDetailVO> contentChannelList = iContentService.listContentDetails(
                searchParam);

            if (contentChannelList != null && contentChannelList.size() > 0)
            {
                for (int i = 0; i < contentChannelList.size(); i++ )
                {
                    ContentDetailVO pojo_ContentChannel = contentChannelList.get(i);
                    String title = pojo_ContentChannel.getTitle();
                    String contentId = pojo_ContentChannel.getId();
                    Integer contentLogicId = pojo_ContentChannel.getLogicId();
                    int nodeId = pojo_ContentChannel.getChannelLogicId();
                    Timestamp input_date = DateUtil.dateToTimestamp(
                        pojo_ContentChannel.getCreatetime());

                    QueryWrapper<ContentMedia> queryWrapper = new QueryWrapper<ContentMedia>();
                    queryWrapper.eq("content_id", contentId);
                    if (GetParam("新闻_图片_上传类型").equals("0"))
                    {
                        queryWrapper.eq("uploadtype", 0);
                    }
                    if (GetParam("新闻_图片_上传类型").equals("1"))
                    {
                        queryWrapper.eq("uploadtype", 1);
                    }
                    queryWrapper.orderByAsc("level", "id");
                    List<ContentMedia> contentMedialist = iContentMediaService.list(queryWrapper);
                    if (contentMedialist != null && contentMedialist.size() > 0)
                    {
                        TitlePropVO vh_Title_Prop = new TitlePropVO();
                        ContentMedia pojo_ContentMedia = (ContentMedia)contentMedialist.get(0);
                        String desc = pojo_ContentMedia.getMediaDesc();
                        String path = pojo_ContentMedia.getPath();
                        vh_Title_Prop.setContentId(contentId);
                        vh_Title_Prop.setContentLogicId(contentLogicId);
                        vh_Title_Prop.setNodeId(nodeId);
                        vh_Title_Prop.setInputDate(input_date);
                        vh_Title_Prop.setTitle(title);
                        vh_Title_Prop.setTextUrl(path);
                        vh_Title_Prop.setTextBox(desc);
                        vh_Title_Prop.setPublishTime(input_date);
                        if (list.size() < limit)
                        {
                            list.add(vh_Title_Prop);
                        }
                        else
                        {
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 显示自动节点的图片，每篇文章显示所有的图片
     * 
     * @param NodeSet
     *            栏目所属的节点
     * @return 返回自动图片集
     */

    public List getEveryPictureList(String NodeSet)
    {
        List list = new ArrayList();
        if (!NodeSet.equals(""))
        {
            if (NodeSet.equals("0"))
            {
                // 如果值为0，表示当前节点
                NodeSet = "" + this.nodeId;
            }
            ContentDetailRequestVO searchParam = new ContentDetailRequestVO();
            searchParam.setDstatus(Content.DSTATUS_PUBLISHED);
            searchParam.setChannelLogicIdInStr(NodeSet);

            int iAutoNewsTimeLimit = Converter.str2Int(GetParam("图片_自动图片_时间限制"));
            if (iAutoNewsTimeLimit <= 0) iAutoNewsTimeLimit = 30;
            if (iAutoNewsTimeLimit > 0)
            {
                SimpleDateFormat TimestampDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DATE, 1);
                cal.roll(Calendar.DATE, -1);
                Date endTime = cal.getTime();
                String endTime1 = TimestampDateFormat.format(endTime) + " 23:59:59";

                Calendar cal1 = Calendar.getInstance();
                cal1.add(cal1.DATE, -iAutoNewsTimeLimit);
                Date startTime = cal1.getTime();
                String startTime1 = TimestampDateFormat.format(startTime) + " 00:00:00";

                searchParam.setCreatetimeStart(startTime1);
                searchParam.setCreatetimeStop(endTime1);
                searchParam.setMediaImgCount(0);
                searchParam.setOrderByStr(" createtime desc ");
            }

            long limit = 10;
            if (!GetParam("图片_输出上限").equals(""))
            {
                limit = Integer.parseInt(GetParam("图片_输出上限"));
            }
            List<ContentDetailVO> contentChannelList = iContentService.listContentDetails(
                searchParam);

            if (contentChannelList != null && contentChannelList.size() > 0)
            {
                for (int i = 0; i < contentChannelList.size(); i++ )
                {
                    if (list.size() > limit)
                    {
                        break;
                    }
                    ContentDetailVO pojo_ContentChannel = contentChannelList.get(i);
                    String title = pojo_ContentChannel.getTitle();
                    String contentId = pojo_ContentChannel.getId();
                    Integer contentLogicId = pojo_ContentChannel.getLogicId();
                    int nodeId = pojo_ContentChannel.getChannelLogicId();
                    Timestamp input_date = DateUtil.dateToTimestamp(
                        pojo_ContentChannel.getCreatetime());

                    QueryWrapper<ContentMedia> queryWrapper = new QueryWrapper<ContentMedia>();
                    queryWrapper.eq("content_id", contentId);
                    if (GetParam("新闻_图片_上传类型").equals("0"))
                    {
                        queryWrapper.eq("uploadtype", 0);
                    }
                    if (GetParam("新闻_图片_上传类型").equals("1"))
                    {
                        queryWrapper.eq("uploadtype", 1);
                    }
                    queryWrapper.orderByAsc("level", "id");
                    List<ContentMedia> contentMedialist = iContentMediaService.list(queryWrapper);

                    if (contentMedialist != null && contentMedialist.size() > 0)
                    {
                        for (int j = 0; j < contentMedialist.size(); j++ )
                        {
                            TitlePropVO vh_Title_Prop = new TitlePropVO();
                            ContentMedia pojo_ContentMedia = (ContentMedia)contentMedialist.get(j);
                            String desc = pojo_ContentMedia.getMediaDesc();
                            String path = pojo_ContentMedia.getPath();
                            vh_Title_Prop.setContentId(contentId);
                            vh_Title_Prop.setContentLogicId(contentLogicId);
                            vh_Title_Prop.setNodeId(nodeId);
                            vh_Title_Prop.setInputDate(input_date);
                            vh_Title_Prop.setTitle(title);
                            vh_Title_Prop.setTextUrl(path);
                            vh_Title_Prop.setTextBox(desc);
                            vh_Title_Prop.setPublishTime(input_date);
                            if (list.size() < limit)
                            {
                                list.add(vh_Title_Prop);
                            }
                            else
                            {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 专门为首页的回顾 yangsong 2006-02-06
     * 
     * @return String
     */
    public String outputManual_index()
    {
        StringBuffer s = new StringBuffer();
        // 取得标记的内容
        List itemlist = this.getTagValueList();
        // 如果有内容或需要设自动新闻或自动显示节点
        if (itemlist.size() > 0 && this._maxOutput > 0)
        {
            // 输出内容
            int i_count = 0;
            // 初试化输出列数
            int i_MultiColumn = 1;
            if (!this.GetParam("手选内容_多列输出_列数").equals(""))
            {
                int i_t1 = Integer.parseInt(GetParam("手选内容_多列输出_列数"));
                if (i_t1 > 0)
                {
                    i_MultiColumn = i_t1;
                }
            }
            // 显示标记内容
            for (int i = 0; i < itemlist.size(); i += i_MultiColumn)
            {
                // 多列输出中，处理行头
                if (i_MultiColumn > 1)
                {
                    s.append(this.GetParam("手选内容_多列输出_行头html代码"));
                }
                for (int j = 0; j < i_MultiColumn; j++ )
                {
                    if (j != 0)
                    {
                        // 多列输出中，处理列间代码
                        s.append(GetParam("手选内容_多列输出_列间html代码"));
                    }
                    if (i_count < itemlist.size())
                    {
                        s.append(this.showManual((TitlePropVO)itemlist.get(i_count)));
                    }
                    else
                    {
                        // 多列输出中，处理剩余最后一行后面没有内容部分
                        if (j != 0)
                        {
                            // s.append("&nbsp");
                            s.append("");
                        }
                    }
                    i_count++ ;
                }
                // 多列输出中，处理行尾
                if (i_MultiColumn > 1)
                {
                    s.append(this.GetParam("手选内容_多列输出_行尾html代码") + "\n");
                }
            }
            this._outputCount = itemlist.size();
        }
        if (s.length() > 0)
        {
            s.insert(0, this.GetParam("手选内容_头html代码"));
            s.append(this.GetParam("手选内容_尾html代码"));
        }
        return s.toString();
    }

    // 根据用户插入文字，做为关键字来生成相关新闻列表
    private String outputKeywordNews()
    {
        return "暂不支持关键词新闻列表";
        /*
         * StringBuffer result = new StringBuffer(); StringBuffer sBuff = new StringBuffer();
         * AutoNewsList autoNewsList=new AutoNewsList(); boolean isMultipage = false; if
         * (this.GetParam("新闻_关键字相关新闻_是否显示翻页控制栏").length() > 0) { isMultipage = true; } List
         * keynewslist = null; if(!isMultipage){ keynewslist = keyWordNewsList(); }else{
         * if(SharedConstant.REDIS){ if(this._PageNo == 1){ keynewslist = keyWordNewsList();
         * autoNewsList.setRedisAutoKeywordNewsList(String.valueOf(nodeId), String.valueOf(ttId),
         * keynewslist);//保存存redis值 }else{ keynewslist =
         * autoNewsList.getRedisAutoKeywordNewsList(String.valueOf(nodeId),
         * String.valueOf(ttId));//取redis值 if(keynewslist == null ||
         * keynewslist.size()==0){//未取到值得情况 keynewslist = keyWordNewsList();
         * autoNewsList.setRedisAutoKeywordNewsList(String.valueOf(nodeId), String.valueOf(ttId),
         * keynewslist);//保存存redis值 } } }else{ keynewslist = keyWordNewsList(); } } List sublist =
         * null; if(keynewslist!=null){ PageModel pm = new PageModel(keynewslist, _maxOutput);
         * sublist = pm.getPageList(this._PageNo);//显示第几页 this._itemStart = pm.getPageStartRow()+1;
         * this._itemEnd = pm.getPageEndRow(); this._itemTotal = pm.getTotalRows(); } if (sublist
         * != null && sublist.size() > 0) { // 初试化输出列数 int i_MultiColumn = 1; if
         * (!this.GetParam("手选内容_多列输出_列数").equals("")) { int i_t1 =
         * Integer.parseInt(GetParam("手选内容_多列输出_列数")); if (i_t1 > 0) { i_MultiColumn = i_t1; } }
         * for (int i = 0; i < sublist.size(); i++) { if (i % i_MultiColumn == 0){
         * result.append(GetParam("新闻_行头html代码")); } POJO_Content pojo_content =
         * (POJO_Content)sublist.get(i); Integer id = pojo_content.getContentId(); String title =
         * pojo_content.getTitle(); Timestamp input_date = pojo_content.getInputDate();
         * POJO_VH_Title_Prop curitem = new POJO_VH_Title_Prop(); curitem.setContentId(id);
         * curitem.setTitle(title); curitem.setInputDate(input_date);
         * result.append(this.showItemNews(curitem)); if (i_MultiColumn > 1 && (i + 1) %
         * i_MultiColumn != 0) result.append(GetParam("手选内容_多列输出_列间html代码")); if ((i + 1) %
         * i_MultiColumn == 0) result.append(GetParam("新闻_行尾html代码") + "\n"); } } if
         * (result.length() > 0) { result.insert(0, this.GetParam("新闻_关键字相关新闻_头html代码"));
         * result.append(this.GetParam("新闻_关键字相关新闻_尾html代码") + "\n"); } String spc =
         * this.showPageController(); String spc1 = this.showPageController1(); if
         * (this.GetParam("新闻_关键字相关新闻_是否显示翻页控制栏").equals("1") ||
         * this.GetParam("新闻_关键字相关新闻_是否显示翻页控制栏").equals("3") ||
         * this.GetParam("新闻_关键字相关新闻_是否显示翻页控制栏").equals("4")) { // 输出翻页信息 sBuff.append(spc); }
         * sBuff.append(result.toString()); if (this.GetParam("新闻_关键字相关新闻_是否显示翻页控制栏").equals("2")
         * || this.GetParam("新闻_关键字相关新闻_是否显示翻页控制栏").equals("3")) { // 输出翻页信息 sBuff.append(spc); }
         * if(this.GetParam("新闻_关键字相关新闻_是否显示翻页控制栏").equals("4")){ sBuff.append(spc1); } return
         * sBuff.toString();
         */
    }

    /*
     * private List keyWordNewsList(){ String strLanguageID=this.GetParam("语言标识"); if
     * (strLanguageID.equals("")) strLanguageID="45"; //default to show English Language int
     * istrLanguageID=Converter.str2Int(strLanguageID); List itemlist = this.getTagValueList(); //
     * 生成sql查询条件 String sqlwhere = ""; for (int i = 0; i < itemlist.size(); i++) {
     * POJO_VH_Title_Prop curitem = (POJO_VH_Title_Prop)itemlist.get(i); String itemType =
     * String.valueOf(curitem.getItemType()); if (itemType.equals("4")) { // 处理文字内容 String textbox
     * = makesql(curitem.getTextBox()); if (textbox.length() > 0) { if (sqlwhere.length() > 0)
     * sqlwhere += ",'" + textbox + "'"; else sqlwhere += "'" + textbox + "'"; } } } String
     * queryString = "FROM Keyword where keyword in("+sqlwhere+") and keywordDeleted=0"; ReadDBTool
     * readdao = (ReadDBTool) SpringContextUtil.getBean("ReadDBTool"); List list =
     * readdao.find(queryString); String keyword_id = ""; for(int i=0;i<list.size();i++){ Keyword
     * keyword = (Keyword)list.get(i); if(i==0){ keyword_id =
     * String.valueOf(keyword.getKeywordId()); }else{ keyword_id +=
     * ","+String.valueOf(keyword.getKeywordId()); } } String queryStringkey =
     * "FROM POJO_ContentKw where keywordId in("+keyword_id+")"; DBDao dao=(DBDao )
     * SpringContextUtil.getBean("DBDao"); long limit = _maxOutput; if
     * (this.GetParam("新闻_关键字相关新闻_最大新闻条数").length() > 0){ limit =
     * Integer.parseInt(GetParam("新闻_关键字相关新闻_最大新闻条数")); if(limit==0){ limit=200; } }else{ limit =
     * 100; } List listkw = dao.findByNumberHQL(queryStringkey,(int)limit); String content_id = "";
     * for(int i=0;i<listkw.size();i++){ POJO_ContentKw pojo_ContentKw =
     * (POJO_ContentKw)listkw.get(i); if(i==0){ content_id =
     * String.valueOf(pojo_ContentKw.getContentId()); }else{ content_id +=","+
     * String.valueOf(pojo_ContentKw.getContentId()); } } String queryStringcontent =
     * "FROM POJO_Content where status=40 and languageId="+strLanguageID+" and contentId in("
     * +content_id+") order by inputDate desc"; List keynewslist =
     * dao.findByNumberHQL(queryStringcontent,(int)limit); return keynewslist; }
     */

    // 将用户插入的字符串转换为sql形式
    private String makesql(String sql)
    {
        String result = Encode.htmlsp(sql);
        result = result.replaceAll("\n", "");
        result = result.replaceAll("&nbsp;", " ");
        result = result.replaceAll("&amp;", "&");
        result = result.replaceAll("&lt;", "<");
        result = result.replaceAll("&gt;", ">");
        result = result.replaceAll("&quot;", "\"");
        result = result.replaceAll("<.*[b|B]{1}[r|R]{1}.*>", "");
        result = result.replaceAll("'", "''");
        return result;
    }

    public String getContentURL(Integer contentLogicId)
    {
        if (isAbsolutePath)
        {
            try
            {
                ContentObj content = new ContentObj(contentLogicId);
                String url = "http://" + content.getDomain()
                             + (content.getLanguageId() == 1 ? "/GB/" : "/") + content.getUrl();
                return url;
            }
            catch (Exception e)
            {
            }
        }
        return sharedConstant.getContentViewUrl(contentLogicId);
    }

    // 根据s来格式化日期date,s的第1个字符值分别表示0:不处理日期返回"" 1:将日期项间插入指定的间隔符
    // s的第1个字符值大于2时表示以特定的语言处理日期，s的第1个字符后面的字符串是要格式化日期的形式，语法为java.text.SimpleDateFormat中定义
    // 2:英文 3:法文 4:俄文 5:西班牙文 6:阿拉伯文 7:日文 8:朝鲜语 默认为中文
    public String GetDateStr(String s, java.sql.Date date)
    {
        if (s == null || s.length() == 0 || s.substring(0, 1).equals("0")) return "";
        String rt = "";
        String command = s.substring(0, 1);
        if (command.equals("1"))
        {
            s = s.substring(1);
            // 11111 : Year|Month|Day|Hour|Min
            // 值为０时不加间隔字符，为１时加入默认的中文日期间隔字符，为其它值时则将此值做为间隔符直接插入
            for (int i = 0; i < s.length(); i++ )
            {
                String symbol = s.substring(i, i + 1);
                symbol = (symbol == null ? "0" : symbol);
                if (symbol.equals("0"))
                {}
                else
                {
                    if (i == 0)
                    {
                        if (symbol.equals("1"))
                        {
                            rt += DateUtil.getYear(date) + "年";
                        }
                        else
                        {
                            rt += DateUtil.getYear(date) + symbol;
                        }

                    }
                    if (i == 1)
                    {
                        if (symbol.equals("1"))
                        {
                            rt += DateUtil.getMonth(date) + "月";
                        }
                        else
                        {
                            rt += DateUtil.getMonth(date) + symbol;
                        }

                    }
                    if (i == 2)
                    {
                        if (symbol.equals("1"))
                        {
                            rt += DateUtil.getDay(date) + "日";
                        }
                        else
                        {
                            rt += DateUtil.getDay(date) + symbol;
                        }

                    }
                    if (i == 3)
                    {
                        if (symbol.equals("1"))
                        {
                            rt += DateUtil.getHour(date) + ":";
                        }
                        else
                        {
                            rt += DateUtil.getHour(date) + symbol;
                        }

                    }
                    if (i == 4)
                    {
                        if (symbol.equals("1"))
                        {
                            rt += DateUtil.getMinute(date) + "";
                        }
                        else
                        {
                            rt += DateUtil.getMinute(date) + symbol;
                        }

                    }
                }
            }
        }
        else
        {
            Locale lang = Locale.CHINESE;
            if (command.equals("2"))
            {// '英文'
                lang = Locale.ENGLISH;
            }
            else if (command.equals("3"))
            {// '法文'
                lang = Locale.FRENCH;
            }
            else if (command.equals("4"))
            {// '俄文'
                lang = new Locale("ru");
            }
            else if (command.equals("5"))
            {// '西班牙文'
                lang = new Locale("es");
            }
            else if (command.equals("6"))
            {// '阿拉伯文'
                lang = new Locale("ar");
            }
            else if (command.equals("7"))
            {// '日文'
                lang = Locale.JAPANESE;
            }
            else if (command.equals("8"))
            {// '朝鲜语'
                lang = Locale.KOREAN;
            }
            SimpleDateFormat dateformat = new SimpleDateFormat(s.substring(1), lang);
            rt += dateformat.format(date);
        }
        // if (rt.length() > 0) rt = "[" + rt + "]";
        if (rt.length() > 0)
        {
            if (GetParam("时间参数").equals(""))
                rt = "[" + rt + "]";
            else
                rt = rt;
        }
        return rt;
    }

    public Channel getChannel(int node_id)
    {
        return iChannelService.getByLogicId(nodeId);
    }

    // 初始化栏目
    public Channel initChannel(int node_id)
    {
        return getChannel(node_id);
    }

    public int getItemStart(int pageSize)
    {
        int start;
        if (page == 0)
            start = 0;
        else
            start = page * pageSize;
        return start;
    }

    public int getItemEnd(int pageSize)
    {
        int end;
        end = this._itemStart + pageSize;
        return end;
    }

    public int getItemTotal(List list)
    {
        return list.size();
    }

    /**
     * 根据 代码_是否显示 属性来控制 代码_内容 属性中的内容是否显示
     */
    protected String outHtml()
    {
        if (this.GetParam("代码_是否显示").equals("1"))
        {
            return this.GetParam("代码_内容1");
        }
        else if (this.GetParam("代码_是否显示").equals("2"))
        {
            return this.GetParam("代码_内容2");
        }
        return "";
    }

    public String getOrigin(String name, String url)
    {
        String originName = GetParam("新闻_新闻来源_名称");
        if (originName == null || originName.equals(""))
        {
            originName = "来源：";
        }

        return originName + "<a href=\"" + url + "\" target=\"_blank\">" + name + "</a>";
    }

    public String outputManualAuto()
    {

        StringBuffer s = new StringBuffer();

        List itemlist = null;
        String spc = "";

        itemlist = this.getTagValueList();

        ContentDetailVO contentDetail = iContentService.getContentById(this.contentId);
        Integer conNodeID = 0;
        if (contentDetail != null)
        {
            conNodeID = contentDetail.getChannelLogicId();
        }

        if (itemlist != null && itemlist.size() > 0 && conNodeID > 0)
        {
            for (int i = 0; i < itemlist.size(); i++ )
            {
                TitlePropVO pvt = (TitlePropVO)itemlist.get(i);
                if (pvt.getTitle().contains(String.valueOf(conNodeID)))
                {
                    s.append(this.showManual(pvt));
                    // 统计专家新闻数量

                    ContentDetailRequestVO searchParam = new ContentDetailRequestVO();
                    searchParam.setDstatus(Content.DSTATUS_PUBLISHED);
                    searchParam.setChannelLogicIdInStr("" + conNodeID);

                    int iAutoNewsTimeLimit = Converter.str2Int(GetParam("新闻_列表统计_时间限制"));
                    if (iAutoNewsTimeLimit <= 0) iAutoNewsTimeLimit = 365;

                    SimpleDateFormat TimestampDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    java.util.Date endTime = cal.getTime();
                    String endTime1 = TimestampDateFormat.format(endTime) + " 23:59:59";

                    Calendar cal1 = Calendar.getInstance();
                    cal1.add(cal1.DATE, -iAutoNewsTimeLimit + 1);
                    java.util.Date startTime = cal1.getTime();
                    String startTime1 = TimestampDateFormat.format(startTime) + " 00:00:00";

                    searchParam.setCreatetimeStart(startTime1);
                    searchParam.setCreatetimeStop(endTime1);

                    List list = iContentService.listContentDetails(searchParam);
                    int sum = 0;
                    if (list != null) sum = list.size();
                    s.append("<b class=\"mt10\">文章" + sum + "篇</b>");

                    break;
                }
            }
        }

        if (s.length() > 0)
        {
            s.insert(0, this.GetParam("手选内容_头html代码"));
            s.append(this.GetParam("手选内容_尾html代码"));
        }

        return s.toString();

    }

    /*
     * public List getOwnKeyWord(String content_id){ DBDao dao=(DBDao )
     * SpringContextUtil.getBean("DBDao"); ReadDBTool readDao = (ReadDBTool)
     * SpringContextUtil.getBean("ReadDBTool"); String queryString =
     * "FROM POJO_ContentKw WHERE contentId="+content_id; List list = dao.findHQL(queryString,
     * Long.parseLong(content_id)); List re = new ArrayList(); for(int i=0;i<list.size();i++){
     * POJO_ContentKw pojo_ContentKw = (POJO_ContentKw)list.get(i); int keywordid =
     * pojo_ContentKw.getKeywordId(); queryString =
     * "from Keyword where keywordId="+keywordid+" and keywordDeleted=0"; List list1 =
     * readDao.find(queryString); for(int j=0;j<list1.size();j++){ Keyword keyword =
     * (Keyword)list1.get(j); re.add(keyword); } } return re; }
     */

    public List<Channel> getOwnContentChannel(String content_id)
    {

        List<Channel> result = new ArrayList<Channel>();
        List<ContentChannel> temp = new ArrayList<ContentChannel>();

        List<ContentChannel> dbData = iContentChannelService.getByContentId(content_id);

        String node_IdSet = GetParam("新闻_所属节点_过滤节点ID");
        if (node_IdSet != null && !node_IdSet.equals(""))
        {
            String[] nodeIds = node_IdSet.split(",");
            for (ContentChannel cc : dbData)
            {
                Channel channel = iChannelService.getById(cc.getChannelid());
                if (!inNodeIds(channel.getLogicId(), nodeIds))
                {
                    temp.add(cc);
                }
            }
            dbData = temp;
        }

        int limitSize = dbData.size();
        if (!"".equals(this.GetParam("新闻_所属节点_限制输出数量"))
            && limitSize > Integer.parseInt(this.GetParam("新闻_所属节点_限制输出数量")))
        {
            limitSize = Integer.parseInt(this.GetParam("新闻_所属节点_限制输出数量"));
        }
        for (int i = 0; i < limitSize; i++ )
        {
            result.add(iChannelService.getById(dbData.get(i).getChannelid()));
        }
        return result;
    }

    private boolean inNodeIds(Integer logicId, String[] nodeIds)
    {
        for (String id : nodeIds)
        {
            if (logicId == Integer.parseInt(id))
            {
                return true;
            }
        }
        return false;
    }
}
