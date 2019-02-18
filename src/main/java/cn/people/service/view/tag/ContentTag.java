package cn.people.service.view.tag;


/**
 * <p>Title: NetaBeans </p> <p>Description: Java Application Framework</p> <p>Copyright: Copyright
 * (c) 2002</p> <p> </p>
 * 
 * @author
 * @version 1.0
 */
import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.people.controller.vo.ContentDetailRequestVO;
import cn.people.controller.vo.ContentDetailVO;
import cn.people.entity.Channel;
import cn.people.entity.Content;
import cn.people.entity.ContentChannel;
import cn.people.entity.ContentMedia;
import cn.people.service.view.obj.ChannelObj;
import cn.people.service.view.obj.ChannelUrl;
import cn.people.service.view.obj.ContentMediaRedis;
import cn.people.service.view.obj.ContentObj;
import cn.people.service.view.obj.ContentTool;
import cn.people.service.view.obj.DateUtil;
import cn.people.service.view.obj.Encode;
import cn.people.service.view.obj.Escape;
import cn.people.service.view.obj.HTML2TextNew;
import cn.people.service.view.obj.HtmlToText;
import cn.people.service.view.obj.SharedConstant;
import cn.people.service.view.obj.StringTool;
import cn.people.service.view.obj.XmlTool;


/**
 * <p>Title: NetaBeans </p> <p>Description: Java Application Framework</p> <p>Copyright: Copyright
 * (c) 2002</p> <p> </p>
 * 
 * @author alex liang
 * @version 2.0
 */

public class ContentTag extends AbstractBaseTag
{
    static private SimpleDateFormat english_datetime_format = new SimpleDateFormat(
        "HH:mm, MMMMM dd, yyyy", Locale.ENGLISH);

    public static final int DEFAULT_NEWS_TITLE_LEN_LIMIT = 20 + 31;

    private static String SYSTEM_NAME = SharedConstant.SYSTEM_NAME;

    private static int JIANBAN_WITH = 128;

    private static int CAIBAN_WITH = 220;

    private static int CHUPING_WITH = 320;

    private static int WIRELESSBIGPIC_WITH = 640;

    // 60周年稿源id
    private static Vector<Long> originId60Year = new Vector<Long>();
    static
    {
        long allOriginID[] = {106140, 106300, 106340, 106400, 105860, 105960, 110256, 110281,
            105780, 105920, 105940, 105980, 106020, 106080, 106120, 106320, 106420, 106660, 105760,
            110518, 110519, 110724, 110645, 110725, 110423, 110424, 105660, 105680, 105700, 105720,
            105740, 105800, 105820, 105840, 105880, 105900, 106000, 106060, 106100, 106160, 106180,
            106220, 106260, 110026, 100649, 100650, 110063, 106380, 106480, 106481, 106620};
        for (int i = 0; i < allOriginID.length; i++ )
        {
            originId60Year.add(allOriginID[i]);
        }
    }

    private String COUNTER_ADDRESS_MAIN = "http://counter.people.cn:8000/c.gif?id=";

    private String COUNTER_ADDRESS_LOCAL = "http://counter.people.cn:8000/f.gif?id=";

    private String COUNTER_ADDRESS_WAIWEN = "http://counter.people.cn:8000/d.gif?id=";

    private String COUNTER_ADDRESS_HAIWAI = "http://paihang.haiwainet.cn:8000/f.gif?id=";

    private String PHPServer_out = "comments.people.com.cn";

    private static int longContentLen = 1140;

    private static int longContentSection = 11;

    public ContentTag()
    {}

    @Override
    public HashMap<String, Object> view()
    {
        // TODO Auto-generated method stub
        HashMap iMap = view(s);
        return iMap;
    }

    public HashMap<String, Object> view(String s)
    {

        String re = "";
        if (s.equals("TITLE"))
        {
            re = getTitle();
        }
        else if (s.equals("TITLE_"))
        {
            re = getTitle_();
        }
        else if (s.equals("TITLE_WAIWEN"))
        {
            re = getTitle_WAIWEN();
        }
        else if (s.equals("TITLE_PREFIX"))
        {
            re = getTITLE_Prefix();
        }
        else if (s.equals("TITLE_POSTFIX"))
        {
            re = getTITLE_Postfix();
        }
        else if (s.equals("NAVIGATOR"))
        {
            re = getNavigator();
        }
        else if (s.startsWith("NAVIGATOR_NOPEOPLE_") && s.length() > 19)
        {
            re = getNavigatorNoPeople(s.substring(19) + ",");
        }
        else if (s.equals("GCD_NAVIGATOR"))
        {
            re = getGCD_Navigator();
        }
        else if (s.equals("BFGY_NAVIGATOR"))
        {
            re = getBFGY_Navigator();
        }
        else if (s.equals("NEW_NAVIGATOR"))
        {
            re = getNEW_Navigator();
        }
        else if (s.equals("TITLE_NOENCODE"))
        {
            re = getTitle_noencode();
        }
        else if (s.equals("TITLE_NOENCODE_ARABIC"))
        {
            re = getTitleArabic_noencode();
        }
        else if (s.equals("CONTENT"))
        {
            re = getContentText();
        }
        else if (s.equals("CONTENT_NOSTYLE"))
        {
            re = getContentNoStyleText();
        }
        else if (s.equals("CONTENT_NOSTYLE_XIONGAN"))
        {
            re = getContentNoStyleText_XiongAn();
        }
        else if (s.equals("CONTENT_IMGNEXT"))
        {
            re = getContentImgNextText();
        }
        else if (s.equals("CONTENT_PHOTO"))
        {
            re = getContentPhotoText();
        }
        else if (s.equals("CONTENT_GQPIC"))
        {
            re = getContentGqPic();
        }
        else if (s.equals("CONTENT_NOENCODE1"))
        {
            re = getContentText_noencodekuohao();
        }
        else if (s.equals("CONTENT_NOENCODE1_NEW"))
        {
            re = getContentText_noencodekuohao_new();
        }
        else if (s.equals("CONTENT_NOENCODE"))
        {
            re = getContentText_noencode();
        }
        else if (s.equals("CONTENT_NOENCODE_NEW"))
        {
            re = getContentText_noencode_new();
        }
        else if (s.equals("CONTENT_NOENCODE_FRENCH"))
        {
            re = getContentText_noencode_french();
        }
        else if (s.equals("CONTENT_GROUP"))
        {
            re = getContentText_Group();
        }
        else if (s.equals("CONTENT_CPC"))
        {
            re = getContentText_CPC();
        }
        else if (s.equals("CONTENT_AD"))
        {
            re = getContentText_Adstr(0);
        }
        else if (s.equals("CONTENT_AD1"))
        {
            re = getContentText_Adstr(1);
        }
        else if (s.equals("CONTENT_PIC_NOT_NEXT"))
        {
            re = getContentText_PicNotNext();
        }
        else if (s.equals("BOARDID"))
        {
            re = getFirstboardID();
        }
        else if (s.equals("CONTENTID"))
        {
            if (SYSTEM_NAME.equals("LOCAL"))
            {
                re = "L_" + getFirstID();
            }
            else
            {
                re = getFirstID();
            }
        }
        else if (s.equals("CONTENTID_HAIWAI"))
        {
            re = getFirstID();
        }
        else if (s.equals("ORIGINAL_CONTENT_TYPE"))
        {
            re = getOriginalContentType();
        }
        else if (s.equals("ORIGINAL_CONTENT_AUTHOR"))
        {
            re = getOriginalContentAuthor();
        }
        else if (s.equals("FULLURLE"))
        {
            re = getFullURLe();
        }
        else if (s.equals("RELATENEWS"))
        {
            re = getRelateNews();
        }
        else if (s.equals("RELATENEWSNEW"))
        {
            re = getRelateNewsNew();
        }
        else if (s.equals("RELATENEWS_2007TXT"))
        {
            re = getRelateNews_2007txt();
        }
        else if (s.equals("RELATENEWS_CPC"))
        {
            re = getRelateNews_cpc();
        }
        else if (s.equals("RELATENEWSGCD"))
        {
            re = getRelateNewsGcd();
        }
        else if (s.equals("RELATENEWSNODATE"))
        {
            re = getRelateNewsNodate();
        }
        else if (s.equals("RELATECHANNELS"))
        {
            re = getRelateChannels();
        }
        else if (s.equals("RELATECHANNELSNEW"))
        {
            re = getRelateChannelsNew();
        }
        else if (s.equals("RELATECHANNELS_2007TXT"))
        {
            re = getRelateChannels_2007txt();
        }
        else if (s.equals("RELATECHANNELS_CPC"))
        {
            re = getRelateChannels_cpc();
        }
        else if (s.equals("RELATECHANNELSGCD"))
        {
            re = getRelateChannelsGcd();
        }
        else if (s.equals("RELATENEWS_FOREIGN"))
        {
            re = getRelateNews_Foreign();
        }
        else if (s.equals("RELATECHANNELS_RUSSIA"))
        {
            re = getRussia_getRelateChannels();
        }
        else if (s.equals("QGLT_RELATECHANNELS"))
        {
            re = qglt_getRelateChannels();
        }
        else if (s.equals("FUNCLIST"))
        {
            re = getFuncList();
        }
        else if (s.equals("PRETITLE"))
        {
            re = getPreTitle();
        }
        else if (s.equals("PRETITLE_NOENCODE"))
        {
            re = getPreTitle_nospace();
        }
        else if (s.equals("SUBTITLE"))
        {
            re = Encode.htmlsp(contentObj.getSubtitle());
        }
        else if (s.equals("SUBTITLE_NEW"))
        {
            re = "<span class=\"subtitle_new\">" + Encode.htmlsp_nospace(contentObj.getSubtitle())
                 + "</span>";
        }
        else if (s.equals("SUBTITLE_NOENCODE"))
        {
            re = Encode.htmlsp_nospace(contentObj.getSubtitle());
        }
        else if (s.equals("SUMMARY"))
        {
            re = getSummary();
        }
        else if (s.equals("SUMMARY_HAIWAI"))
        {
            re = getSummaryHaiWai();
        }
        else if (s.equals("SUMMARYGOSO"))
        {
            re = this.getSummaryNew();
        }
        else if (s.equals("SUMMARYGOSO_EN"))
        {
            re = this.getSummaryNewEn();
        }
        else if (s.equals("SUMMARY1"))
        {
            re = getSummary1();
        }
        else if (s.equals("VIDEO_URL"))
        {
            re = getVideoUrl();
        }
        else if (s.equals("VIDEO_TIME"))
        {
            re = getVideoTime();
        }
        else if (s.equals("PUBLISHTIME"))
        {
            re = getPublishTime();
        }
        else if (s.equals("PUBLISHTIMETEMP"))
        {
            re = getPublishTime_temp();
        }
        else if (s.equals("PUBLISHTIME_SIMPLE"))
        {
            re = getPublishTime_simple();
        }
        else if (s.equals("ENGLISH_PUBLISHTIME"))
        {
            re = English_getPublishTime();
            // 法文
        }
        else if (s.equals("FRENCH_PUBLISHTIME"))
        {
            re = French_getPublishTime();
            // 俄文
        }
        else if (s.equals("RUSSIA_PUBLISHTIME"))
        {
            re = Russia_getPublishTime();
            // 西文
        }
        else if (s.equals("SPANISH_PUBLISHTIME"))
        {
            re = Spanish_getPublishTime();
        }
        else if (s.equals("GERMAN_PUBLISHTIME"))
        {
            re = German_getPublishTime();
        }
        else if (s.startsWith("CUSTOM_PUBLISHTIME"))
        {
            re = Custom_getPublishTime(s);
        }
        else if (s.equals("AUTHOR"))
        {
            re = this.getAuthor();
        }
        else if (s.equals("AUTHOR_ENGLISH"))
        {
            re = this.getAuthorEnglish();
        }
        else if (s.equals("AUTHOR_NEW"))
        {
            re = this.getAuthorNew();
        }
        else if (s.equals("AUTHOR_ZN"))
        {
            re = this.getAuthor_ZN();
        }
        else if (s.equals("AUTHOR_CODE"))
        {
            re = this.getAuthor_CODE();
        }
        else if (s.equals("EDITOR"))
        {
            re = this.getEditor();
        }
        else if (s.equals("EDITOR_ONE"))
        {
            re = this.getEditor_one();
        }
        else if (s.equals("EDITOR_EN"))
        {
            re = this.getEditor_EN();
        }
        else if (s.equals("EDITOR_RUSSIA"))
        {
            re = this.getEditor_RUSSIA();
        }
        else if (s.equals("EDITOR_FRENCH"))
        {
            re = this.getEditor_FRENCH();
        }
        else if (s.equals("EDITOR_NORMAL"))
        {
            re = (contentObj.getEditor() != null
                  && contentObj.getEditor().trim().length() > 0) ? Encode.htmlsp(
                      contentObj.getEditor().trim()) : "";
        }
        else if (s.equals("ORIGIN"))
        {
            re = getOrigin1();
        }
        else if (s.equals("ORIGIN_NAME"))
        {
            re = getOriginName1();
        }
        else if (s.equals("ORIGIN_60YEAR"))
        {
            re = getOrigin60Year();
        }
        else if (s.equals("ORIGIN_RUSSIA"))
        {
            re = Russia_getOrigin(contentObj.getOriginName(), contentObj.getOriginUrl());
        }
        else if (s.equals("ORIGIN_FRENCH"))
        {
            re = French_getOrigin(contentObj.getOriginName(), contentObj.getOriginUrl());
        }
        else if (s.equals("ORIGIN_KOREA"))
        {
            re = Korea_getOrigin(contentObj.getOriginName(), contentObj.getOriginUrl());
        }
        else if (s.equals("ORIGIN_NORMAL"))
        {
            re = NORMAL_getOrigin(contentObj.getOriginName(), contentObj.getOriginUrl());
        }
        else if (s.equals("ORIGIN_HAIWAI"))
        {
            re = getOriginHaiWai();
        }
        else if (s.equals("ORIGIN_PORTUGUESE"))
        {
            re = Portuguese_getOrigin(contentObj.getOriginName(), contentObj.getOriginUrl());
        }
        else if (s.equals("BBSLINK"))
        {
            re = this.getBBSLink();
        }
        else if (s.equals("BBSLINK_list"))
        {
            re = this.getBBSLink_list();
        }
        else if (s.equals("COUNTER"))
        {
            re = this.getCounter();
        }
        else if (s.equals("COUNTERHAIWAI"))
        {
            re = this.getCounterHaiWai();
        }
        else if (s.equals("COUNTERWAIWEN"))
        {
            re = this.getCounterWaiwen();
        }
        else if (s.equals("lastnews"))
        {
            re = this.getContentlast();
        }
        else if (s.equals("nextnews"))
        {
            re = this.getContentnext();
        }
        else if (s.equals("FIRST_PIC"))
        {
            re = this.getFirstPicture();
        }
        else if (s.equals("FIRST_PICNEW"))
        {
            re = this.getFirstPictureNew();
        }
        else if (s.equals("PIC_NEW"))
        {
            re = this.getPicture_Page();
        }
        else if (s.equals("FIRST_PIC_NOTEXT"))
        {
            re = this.getFirstPictureNotext();
        }
        else if (s.equals("FIRST_PIC_NEXT"))
        {
            re = this.getFirstPictureNext();
        }
        else if (s.equals("FIRST_PIC_NEW"))
        {// 响应式文本层用
            re = this.getFirstPicture_New();
        }
        else if (s.equals("QGLT_FIRST_PIC"))
        {
            // 强国论坛使用
            re = this.qglt_getFirstPicture();
        }
        else if (s.equals("OTHER_PIC"))
        {
            re = this.getOtherPicture();
        }
        else if (s.equals("OTHER_PIC_NEW"))
        {// 响应式文本层用
            re = this.getOtherPicture_New();
        }
        else if (s.equals("PIC.ALL"))
        {
            re = this.getAllPicture();
        }
        else if (s.equals("OTHER_PIC_NOSPACE"))
        {
            re = this.Foreign_getOtherPicture();
        }
        else if (s.equals("FIRST_PIC_NOSPACE"))
        {
            re = this.Foreign_getFirstPicture();
        }
        else if (s.equals("PIC_NOSPACE_NEW"))
        {// 外文新版本支持媒体翻页
            re = this.Foreign_getPicture_Page();
        }
        else if (s.equals("FIRST_PIC_NOSPACE2007"))
        {
            re = this.Foreign2007_getFirstPicture();
        }
        else if (s.equals("PIC_NOSPACE2007_NEW"))
        {// 外文新版本支持媒体翻页
            re = this.Foreign2007_getPicture_Page();
        }
        else if (s.equals("FIRST_PIC_JAPAN_NOSPACE"))
        {
            re = this.Foreign_japan_getFirstPicture();
        }
        else if (s.equals("PIC_JAPAN_NOSPACE_NEW"))
        {// 外文新版本支持媒体翻页
            re = this.Foreign_japan_getPicture_Page();
        }
        else if (s.equals("RELATENEWS.BODY"))
        {
            re = this.getRelateNews_body();
        }
        else if (s.equals("BOARD.ACTION"))
        {
            re = this.getBoardAction();
        }
        else if (s.equals("SNODEID"))
        {
            List<Channel> list = getSecondChannels();
            if (list.size() > 0)
            {
                re = "" + list.get(0).getLogicId();
            }
        }
        else if (s.equals("SNODENAME"))
        {
            List list = getSecondChannels();
            if (list.size() > 0)
            {
                re = String.valueOf(((Channel)list.get(0)).getChannelName());
            }
        }
        else if (s.equals("QGLTLINK"))
        {
            re = this.getQGLTLink();
        }
        else if (s.equals("KEYWORD"))
        {
            re = this.getKeyword();
        }
        else if (s.equals("KEYWORD_EN"))
        {
            re = this.getKeywordEn();
        }
        else if (s.equals("LTLINK"))
        {
            re = this.getLTLink();
        }
        else if (s.equals("LTLINK_new"))
        {
            re = this.getLTLinkNew();
        }
        else if (s.equals("getchannel_right"))
        {
            re = this.getchannel_right();
        }
        else if (s.equals("getchannel_down"))
        {
            re = this.getchannel_right();
        }
        else if (s.equals("NEWTITLE"))
        {
            re = this.getnewtitle();
        }
        else if (s.equals("NEWTITLE_HAIWAI"))
        {
            re = this.getnewtitleHaiWai();
        }
        else if (s.equals("GCDTITLE"))
        {
            re = this.getgcdtitle();
        }
        else if (s.equals("FULLURL"))
        {
            re = this.getFullURL();
        }
//        //正文XML相关方法
        else if (s.startsWith("CONTENTXML_"))
        {// 处理正文中的XML，只取单节点下的数据
            re = this.getContentXml(s);
        }
        else if (s.startsWith("CONTENTXMLMAPLIST_"))
        {// 处理正文中的XML，取节点下的多个子节点数据
            re = this.getContentXmlMapList(s);
        }
        else if (s.startsWith("CONTENTXMLMAPID_"))
        {// 处理正文中的XML，从节点下的多个子节点数据中取指定编号的数据
            re = this.getContentXmlMapID(s);
        }
        // 文本信息保护
        else if (s.equals("ESCAPETITLE"))
        {// 标题
            re = this.getEscapeTitle();
        }
        else if (s.equals("ESCAPEPRETITLE"))
        {// 肩标题
            re = this.getEscapePreTitle();
        }
        else if (s.equals("ESCAPESUBTITLE"))
        {// 副标题
            re = this.getEscapeSubTitle();
        }
        else if (s.equals("ESCAPEAUTHOR"))
        {// 作者
            re = this.getEscapeAuthor();
        }
        else if (s.equals("ESCAPECONTENT"))
        {// 内容
            re = this.getEscapeContent();
        }
        else if (s.equals("ESCAPEEDITOR"))
        {// 编辑
            re = this.getEscapeEditor();
        }
        else if (s.equals("VIEWCOUNT"))
        {
            re = getViewCount(String.valueOf(contentId));
        } // 外文版专用西班牙文相关新闻
        else if (s.startsWith("RELATENEWS_SPAIN"))
        {
            re = getRelateNews_Spain();
        }

        else if (s.equals("FIRST1_PIC"))
        {
            re = this.getFirstPicture1();
        }
        // 正文里头加图片
        else if (s.equals("INSERTIMGCONTENT"))
        {
            re = this.getInsertIMGCONTENT();
        }
        else if (s.equals("NODEID"))
        {
            if (SYSTEM_NAME.equals("LOCAL"))
            {
                re = "L_" + contentObj.getNodeId() + "";
            }
            else
            {
                re = contentObj.getNodeId() + "";
            }
        }
        else if (s.equals("INPUTBY"))
        {
            if (SYSTEM_NAME.equals("LOCAL"))
            {
                re = "L_" + contentObj.getInputBy() + "";
            }
            else
            {
                re = contentObj.getInputBy() + "";
            }
        }
        else if (s.equals("RMDSCONTENT"))
        {
            re = this.getRmdsContent();
        }
        else if (s.equals("GQPIC"))
        {
            re = this.getAllPicture_GQ();
        }
        else if (s.equals("GQPICUP"))
        {
            re = this.getPicUp_GQ();
        }
        else if (s.equals("GQPICUPNEW"))
        {
            re = this.getPicUpNew_GQ();
        }
        else if (s.equals("GQPICDOWN"))
        {
            re = this.getPicDown_GQ();
        }
        else if (s.equals("GQPICDOWNNEW"))
        {
            re = this.getPicDownNew_GQ();
        }
        else if (s.equals("GQPICDESC"))
        {
            re = this.getAllPictureDesc_GQ();
        }
        else if (s.equals("NEXTPAGE"))
        {
            re = this.getPageUpAndDown();
        }
        else if (s.equals("ALLPAGEURL"))
        {
            re = this.getAllPageUrl();
        }
        else if (s.equals("PAGEURL"))
        {
            re = this.getPageUrl();
        }
        else if (s.equals("MSNPIC"))
        {
            re = this.getMsnPic();
        }
        else if (s.equals("GQPICNEW"))
        {
            re = this.getGQPicNew();
        }
        else if (s.equals("GQPICNEWFCMS"))
        {
            re = this.getGQPicNewFcms();
        }
        else if (s.equals("GQPICCONTENT"))
        {
            re = this.getGQPicNewContent();
        }
        else if (s.equals("GQPICNEWPAGE"))
        {
            re = this.getGQPicNewPage();
        }
        else if (s.equals("GQPICNEWPAGE1"))
        {
            re = this.getGQPicNewPage1();
        }
        else if (s.equals("GQPICNEWPAGE2"))
        {
            re = this.getGQPicNewPage2();
        }
        else if (s.equals("GQPICNEWPAGE3"))
        {
            re = this.getGQPicNewPage3();
        }
        else if (s.equals("GQPICCity"))
        {
            re = this.getAllPicture_GQ_City();
        }
        else if (s.equals("GQPIC1"))
        {
            re = this.getAllPicture_GQ1();
        }
        else if (s.equals("GQPICSX1"))
        {
            re = this.getAllPicture_GQSX1();
        }
        else if (s.equals("GQPICSX2"))
        {
            re = this.getAllPicture_GQSX2();
        }
        else if (s.equals("GROUPPIC"))
        {
            re = this.getGroupPic();
        }
        else if (s.equals("GROUPPIC_NEW"))
        {
            re = this.getGroupPicNew();
        }
        else if (s.equals("BBSLIST"))
        {
            re = this.getBbsList();
        }
        else if (s.equals("TITLE_ORIGIN"))
        {
            re = this.getContentPrefix();
        }
        else if (s.equals("WIRELESS_PAGEVERSION"))
        {// 版本
            re = contentObj.getVerUrlName();
        }
        else if (s.equals("WIRELESS_PARTNER"))
        {// 渠道
            re = contentObj.getCoUrlName();
        }

        // 不再考虑wap站
        /*
         * else if (s.equals("WIRELESS_TO_MAIN")) {// 无线留言对应关系 re = this.getWIRELESS_TO_MAIN(); }
         * else if (s.equals("WIRELESS_JIANBAN_FIRST_PIC")) {// 简版第一张图片 re =
         * this.getWIRELESS_JianBanFirstPic(); } else if (s.equals("WIRELESS_JIANBAN_OTHER_PIC"))
         * {// 简版其他图片 re = this.getWIRELESS_JianBanOtherPic(); } else if
         * (s.equals("WIRELESS_JIANBAN_PAGE_PIC")) {// 简版媒体分页图片 re =
         * this.getWIRELESS_JianBanPagePic(); } else if (s.equals("WIRELESS_CAIBAN_FIRST_PIC")) {//
         * 彩板第一张图片 re = this.getWIRELESS_CaiBanFirstPic(); } else if
         * (s.equals("WIRELESS_CAIBAN_OTHER_PIC")) {// 彩板其他图片 re =
         * this.getWIRELESS_CaiBanOtherPic(); } else if (s.equals("WIRELESS_CAIBAN_PAGE_PIC")) {//
         * 彩版媒体分页图片 re = this.getWIRELESS_CaiBanPagePic(); } else if
         * (s.equals("WIRELESS_CHUPING_FIRST_PIC")) {// 触屏第一张图片 re =
         * this.getWIRELESS_ChuPingFirstPic(); } else if (s.equals("WIRELESS_CHUPING_OTHER_PIC"))
         * {// 触屏其他图片 re = this.getWIRELESS_ChuPingOtherPic(); } else if
         * (s.equals("WIRELESS_CHUPING_PAGE_PIC")) {// 触屏媒体分页图片 re =
         * this.getWIRELESS_ChuPingPagePic(); } else if (s.equals("WIRELESS_CHUPING_PIC")) {//
         * 触屏媒体分页图片 re = this.getWIRELESS_ChuPingPic(); } else if
         * (s.equals("WIRELESS_PAGE_BIGPIC")) {// 大图分页图片 re = this.getWIRELESS_PageBigPic(); } else
         * if (s.equals("WIRELESS_OTHER_BIGPIC")) {// 大图其他图片 re = this.getWIRELESS_OtherBigPic(); }
         * else if (s.equals("WIRELESS_NAVIGATOR")) {// 无线面包屑 re = getWIRELESS_Navigator(); } else
         * if (s.equals("WIRELESS_JIANBAN_CONTENT")) {// 简版正文 re = getWIRELESS_JianBanContent(); }
         * else if (s.equals("WIRELESS_CAIBAN_CONTENT")) {// 彩板正文 re = getWIRELESS_CaiBanContent();
         * } else if (s.equals("WIRELESS_CHUPING_CONTENT")) {// 触屏正文 re =
         * getWIRELESS_ChuPingContent(); } else if (s.equals("WIRELESS_BIGPIC_CONTENT")) {// 触屏正文
         * re = getWIRELESS_BigPicContent(); } else if (s.equals("WIRELESS_NEW_CONTENT")) {// 触屏正文
         * re = getWIRELESS_NewContent(); } else if (s.equals("WIRELESS_NOPAGE_CONTENT")) {// 触屏正文
         * re = getWIRELESS_NoPageContent(); } else if (s.equals("WIRELESS_PUBLISHTIME")) {//
         * 无线发布时间 re = getWIRELESS_PublishTime(); } else if (s.equals("WIRELESS_ORIGIN_NAME")) {//
         * 无线稿源 re = getWIRELESS_OriginName(); } else if (s.equals("WIRELESS_ESCAPE_INPUTBY")) { re
         * = Escape.escape(Encode.htmlsp(String.valueOf(contentObj.getInputBy()))); } else if
         * (s.equals("WIRELESS_ESCAPE_ORIGIN_NAME")) { re =
         * Escape.escape(getWIRELESS_OriginName()); } else if
         * (s.equals("WIRELESS_ESCAPE_NEWTITLE")) { re =
         * Escape.escape(getnewtitle().trim()).trim(); } else if (s.equals("WIRELESS_FULLURLE")) {
         * re = Escape.escapeto(getFullURLe().trim()).trim(); } else if
         * (s.equals("WIRELESS_WECHAT_PIC")) { re = getWIRELESS_WeChatPic(); } else if
         * (s.equals("WIRELESS_GROUP_PIC")) { re = getWIRELESS_GroupPic(); }
         */
        else if (s.equals("INPUT_DATE"))
        {
            re = DateUtil.Date2Str(this.contentObj.getInputDate(), "yyyy-MM-dd");
        }
        else if (s.equals("NODE_NAME"))
        {
            re = getNodeName();
        }
        else if (s.equals("PIC_URL"))
        {
            re = getPicUrl();
        }
        else if (s.equals("PIC_SCROLL"))
        {
            re = getPicScroll();
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("tagvalues", re);
        return result;
    }

    private String getPageUrl()
    {
        String pageStr = "";
        int pageCount = contentObj.getPageCount();
        if (pageCount != 1)
        {
            for (int i = 1; i <= pageCount; i++ )
            {
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page=" + i
                          + "\">【" + i + "】</a>";
            }
        }
        return pageStr;
    }

    private String getContentPrefix()
    {
        String rt = "";
        String contentPrefix = contentObj.getContentPrefix();
        if (contentPrefix != null)
        {
            rt = contentPrefix.replace("&$", "");
        }
        if (rt.length() > 0)
        {
            HtmlToText html = new HtmlToText();
            rt = html.html2Text(rt);
            rt = "<div class=\"otitle\">原标题：" + rt + "</div>";
        }
        return rt;
    }

    private String getAllPageUrl()
    {
        String urlStr = "";
        int pageCount = contentObj.getPageCount();
        for (int i = 1; i <= pageCount; i++ )
        {
            if (i == 1)
            {
                urlStr = contentObj.getUrl(i);
            }
            else
            {
                urlStr = urlStr + ";" + contentObj.getUrl(i);
            }
        }
        return urlStr;
    }

    private String getContentText()
    {
//    	String rt=StringTool.clob2Str(contentObj.getContent());
//        rt=Encode.htmlsp(rt);

        // String contentPrefix = contentObj.getContentPrefix();
        // String contentPostfix = contentObj.getContentPostfix();
        String rt = contentObj.getContentString();
        /*
         * if(contentPrefix!=null){ rt=contentPrefix+contentObj.getContentString(); }
         */
        Integer language_id = contentObj.getLanguageId();
        Integer pageType = contentObj.getPageType();

        int page = contentObj.getPage();

        int pageCount = contentObj.getPageCount();

        if (pageCount != 1 && page < pageCount)
        {
            int nextPage = contentObj.getPage() + 1;
            String regex = "<\\s*img\\s+([^>]*)\\s*>";
            final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
            final Matcher ma = pa.matcher(rt);
            /*
             * while (ma.find()){ String img = ma.group(); String imghref =
             * "<a href=\"NewsView.shtml?id="+contentId+"&page="+nextPage+"\">"+img+"</a>"; rt =
             * rt.replace(img, imghref); }
             */
            while (ma.find())
            {
                String img = ma.group();
                String imgRegx = img.replace("(", "\\(");
                imgRegx = imgRegx.replace(")", "\\)");
                String regex1 = "<a\\s.*?href=\"([^\"]+)\"[^>]*>\\s*" + imgRegx + "\\s*</a>";
                final Pattern pa1 = Pattern.compile(regex1, Pattern.DOTALL);
                final Matcher ma1 = pa1.matcher(rt);
                if (!ma1.find())
                {
                    String imghref = "<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page="
                                     + nextPage + "\">" + img + "</a>";
                    rt = rt.replace(img, imghref);
                }
            }
        }

        Integer contentId = contentObj.getContentLogicId();
        rt = Encode.htmlsp(rt);

        String pageStr = "";

        if (pageType != null)
        {
            if (language_id == 1 || language_id == 31 || language_id == 45)
            {
                if (pageCount > 1)
                {
                    pageStr = "<div class=\"zdfy clearfix\">";
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\">【" + i + "】</a>";
                    }

                    pageStr = pageStr + "</div>";
                    pageStr = pageStr
                              + "<center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";

                    if (page > 1)
                    {
                        int prePage = contentObj.getPage() - 1;
                        pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + prePage
                                  + "\"><img src=\"/img/prev_page.jpg\" border=\"0\"></a>";
                        pageStr = pageStr + "</td>";
                    }
                    if (page < pageCount)
                    {
                        int nextPage = contentObj.getPage() + 1;
                        pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + nextPage
                                  + "\"><img src=\"/img/next_page.jpg\" border=\"0\"></a>";
                        pageStr = pageStr + "</td>";
                    }
                    pageStr = pageStr + "</tr></table></center>";
                }
            }
            else if (language_id == 3 || language_id == 5 || language_id == 6 || language_id == 29)
            {
                pageStr = "<br><center>";
                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\" class=\"abl2\">【" + i + "】</a>";
                    }
                }

                pageStr = pageStr + "</center>";
                pageStr = pageStr
                          + "<br><center><table border=\"0\" align=\"center\"><tr><td><a href=\""
                          + sharedConstant.getContentViewUrl(contentId) + "\"></a></td></tr></table></center>";

            }
            else if (language_id == 4)
            {
                pageStr = "<br><center>";
                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\" class=\"abl2\">[" + i + "]</a>";
                    }
                }
                pageStr = pageStr + "</center>";
                pageStr = pageStr
                          + "<br><center><table border=\"0\" align=\"center\"><tr><td><a href=\""
                          + sharedConstant.getContentViewUrl(contentId) + "\"></a></td></tr></table></center>";

            }
            else
            {
                pageStr = "<br><center>";
                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\" class=\"abl2\">【" + i + "】</a>";
                    }
                }
                pageStr = pageStr + "</center>";
            }
        }

        // 处理要闻精编前面插入分页代码
        String regexjb = "<div\\s*class=\\s*\"\\s*jingbian2012\\s*\"\\s*>";
        final Pattern pajb = Pattern.compile(regexjb, Pattern.DOTALL);
        final Matcher majb = pajb.matcher(rt);
        if (majb.find())
        {
            String jb = majb.group();
            int intjb = rt.indexOf(jb);
            rt = "&$" + rt.substring(0, intjb) + pageStr + rt.substring(intjb) + "&$";
        }
        else
        {
            rt = "&$" + rt + pageStr + "&$";
        }

        // rt = "&$"+rt+pageStr +"&$";

        rt = Encode.htmlsp(rt);

        return rt;
    }

    private String getContentText_CPC()
    {
        String rt = contentObj.getContentString();
        Integer language_id = contentObj.getLanguageId();
        Integer pageType = contentObj.getPageType();
        int page = contentObj.getPage();
        int pageCount = contentObj.getPageCount();
        ContentTool contentTool = new ContentTool();
        if (pageCount != 1 && page < pageCount)
        {
            int nextPage = contentObj.getPage() + 1;
            String regex = "<\\s*img\\s+([^>]*)\\s*>";
            final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
            final Matcher ma = pa.matcher(rt);
            while (ma.find())
            {
                String img = ma.group();
                String imgRegx = img.replace("(", "\\(");
                imgRegx = imgRegx.replace(")", "\\)");
                String regex1 = "<a\\s.*?href=\"([^\"]+)\"[^>]*>\\s*" + imgRegx + "\\s*</a>";
                final Pattern pa1 = Pattern.compile(regex1, Pattern.DOTALL);
                final Matcher ma1 = pa1.matcher(rt);
                if (!ma1.find())
                {
                    String imghref = "<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page="
                                     + nextPage + "\">" + img + "</a>";
                    rt = rt.replace(img, imghref);
                }
            }
        }

        rt = Encode.htmlsp(rt);

        String pageStr = "";

        if (pageType != null)
        {
            if (pageCount > 1)
            {
                pageStr = "<div class=\"zdfy clearfix\">";
                for (int i = 1; i <= pageCount; i++ )
                {
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page=" + i
                              + "\">【" + i + "】</a>";
                }

                pageStr = pageStr + "</div>";
                pageStr = pageStr
                          + "<center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";

                if (page > 1)
                {
                    int prePage = contentObj.getPage() - 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page="
                              + prePage + "\">上一页</a>";
                    pageStr = pageStr + "</td>";
                }
                if (page < pageCount)
                {
                    int nextPage = contentObj.getPage() + 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page="
                              + nextPage + "\">下一页</a>";
                    pageStr = pageStr + "</td>";
                }
                pageStr = pageStr + "</tr></table></center>";
            }
        }
        // 处理图片视频缩进
        rt = contentTool.getPicNoIndent(rt);
        // 处理要闻精编前面插入分页代码
        String regexjb = "<div\\s*class=\\s*\"\\s*jingbian2012\\s*\"\\s*>";
        final Pattern pajb = Pattern.compile(regexjb, Pattern.DOTALL);
        final Matcher majb = pajb.matcher(rt);
        if (majb.find())
        {
            String jb = majb.group();
            int intjb = rt.indexOf(jb);
            rt = "&$" + rt.substring(0, intjb) + pageStr + rt.substring(intjb) + "&$";
        }
        else
        {
            rt = "&$" + rt + pageStr + "&$";
        }
        rt = Encode.htmlsp(rt);

        return rt;
    }

    private String getContentNoStyleText()
    {

        // String contentPrefix = contentObj.getContentPrefix();
        // String contentPostfix = contentObj.getContentPostfix();
        String rt = contentObj.getContentString();
        /*
         * if(contentPrefix!=null){ rt=contentPrefix+contentObj.getContentString(); }
         */
        Integer language_id = contentObj.getLanguageId();
        Integer pageType = contentObj.getPageType();
        int page = contentObj.getPage();
        int pageCount = contentObj.getPageCount();

        Integer contentId = contentObj.getContentLogicId();
        rt = Encode.htmlsp(rt);

        String pageStr = "";

        if (pageType != null)
        {
            if (language_id == 1 || language_id == 31 || language_id == 45 || language_id == 30)
            {
                pageStr = "<div class=\"zdfy clearfix\">";
                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\">【" + i + "】</a>";
                    }
                }
                pageStr = pageStr + "</div>";
                pageStr = pageStr
                          + "<center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";

                if (page > 1)
                {
                    int prePage = contentObj.getPage() - 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                              + prePage + "\"><img src=\"/img/prev_page.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                if (page < pageCount)
                {
                    int nextPage = contentObj.getPage() + 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                              + nextPage + "\"><img src=\"/img/next_page.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                pageStr = pageStr + "</tr></table></center>";

            }
            else if (language_id == 3 || language_id == 5 || language_id == 6 || language_id == 29)
            {
                pageStr = "<br><center>";
                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\" class=\"abl2\">【" + i + "】</a>";
                    }
                }

                pageStr = pageStr + "</center>";
                pageStr = pageStr
                          + "<br><center><table border=\"0\" align=\"center\"><tr><td><a href=\""
                          + sharedConstant.getContentViewUrl(contentId) + "\"></a></td></tr></table></center>";

            }
            else if (language_id == 4)
            {
                pageStr = "<br><center>";
                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\" class=\"abl2\">[" + i + "]</a>";
                    }
                }
                pageStr = pageStr + "</center>";
                pageStr = pageStr
                          + "<br><center><table border=\"0\" align=\"center\"><tr><td><a href=\""
                          + sharedConstant.getContentViewUrl(contentId) + "\"></a></td></tr></table></center>";

            }
            else
            {
                pageStr = "<br><center>";
                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\" class=\"abl2\">【" + i + "】</a>";
                    }
                }

                pageStr = pageStr + "</center>";

                pageStr = pageStr
                          + "<br><center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";
                if (page > 1)
                {
                    int prePage = contentObj.getPage() - 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                              + prePage
                              + "\"><img src=\"/img/2007english/Previous.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                if (page < pageCount)
                {
                    int nextPage = contentObj.getPage() + 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                              + nextPage
                              + "\"><img src=\"/img/2007english/Next.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                pageStr = pageStr + "</tr></table></center>";
            }
        }
        // 处理要闻精编前面插入分页代码
        String regexjb = "<div\\s*class=\\s*\"\\s*jingbian2012\\s*\"([^>]*)\\s*>";
        final Pattern pajb = Pattern.compile(regexjb, Pattern.DOTALL);
        final Matcher majb = pajb.matcher(rt);
        if (majb.find())
        {
            String jb = majb.group();
            int intjb = rt.indexOf(jb);
            rt = "&$" + rt.substring(0, intjb) + pageStr + rt.substring(intjb) + "&$";
        }
        else
        {
            rt = "&$" + rt + pageStr + "&$";
        }
        // 处理要闻精编前面插入分页代码

        String regex = "<\\s*img\\s+([^>]*)\\s*>";
        // String s = "<p style=\"text-align: center; \"><img alt=\"\"
        // src=\"/NMediaFile/2012/0929/MAIN201209291016000268058587667.jpg\" style=\"width: 480px;
        // height: 360px; \" /></p><p style=\"text-align: center; \">";
        final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(rt);
        while (ma.find())
        {
            String img = ma.group();
            if (pageCount != 1 && page < pageCount)
            {
                int nextPage = contentObj.getPage() + 1;
                String imgRegx = img.replace("(", "\\(");
                imgRegx = imgRegx.replace(")", "\\)");
                String regex1 = "<a\\s.*?href=\"([^\"]+)\"[^>]*>\\s*" + imgRegx + "\\s*</a>";
                final Pattern pa1 = Pattern.compile(regex1, Pattern.DOTALL);
                final Matcher ma1 = pa1.matcher(rt);
                if (!ma1.find())
                {
                    String imghref = "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                     + nextPage + "\">" + img + "</a>";
                    rt = rt.replace(img, imghref);
                }
            }
            String regex1 = "style=\"[^>]*?\"";
            Pattern pa1 = Pattern.compile(regex1, Pattern.DOTALL);
            Matcher ma1 = pa1.matcher(img);
            String newImg = "";
            while (ma1.find())
            {
                String style = ma1.group();
                String regex2 = "(width|height):\\s*[0-9]{1,4}";
                Pattern pa2 = Pattern.compile(regex2, Pattern.DOTALL);
                Matcher ma2 = pa2.matcher(style);
                String newStyle = "";
                while (ma2.find())
                {
                    String s = ma2.group().replace(": ", "=\"") + "\" ";
                    newStyle = newStyle + s;
                }
                newImg = img.replace(style, newStyle);
            }
            if (!newImg.equals(""))
            {
                rt = rt.replace(img, newImg);
            }
        }
        // 处理视频
        rt = StringTool.replaceEmbed(rt);
        rt = Encode.htmlsp(rt);
        return rt;
    }

    private String getContentNoStyleText_XiongAn()
    {
        String rt = contentObj.getContentString();
        Integer pageType = contentObj.getPageType();
        int page = contentObj.getPage();

        int pageCount = contentObj.getPageCount();

        Integer contentId = contentObj.getContentLogicId();

        if (pageCount != 1 && page < pageCount)
        {
            int nextPage = contentObj.getPage() + 1;
            String regex = "<\\s*img\\s+([^>]*)\\s*>";
            final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
            final Matcher ma = pa.matcher(rt);
            while (ma.find())
            {
                String img = ma.group();
                String imgRegx = img.replace("(", "\\(");
                imgRegx = imgRegx.replace(")", "\\)");
                String regex1 = "<a\\s.*?href=\"([^\"]+)\"[^>]*>\\s*" + imgRegx + "\\s*</a>";
                final Pattern pa1 = Pattern.compile(regex1, Pattern.DOTALL);
                final Matcher ma1 = pa1.matcher(rt);
                if (!ma1.find())
                {
                    String imghref = "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                     + nextPage + "\">" + img + "</a>";
                    rt = rt.replace(img, imghref);
                }
            }
        }

        rt = Encode.htmlsp(rt);

        String pageStr = "";

        if (pageType != null)
        {
            if (pageCount > 1)
            {
                for (int i = 1; i <= pageCount; i++ )
                {
                    if (i == page)
                    {
                        pageStr = pageStr + "<a class=\"page_current\" href=\""
                                  + sharedConstant.getContentViewUrl(contentId) + "?page=" + i + "\">" + i + "</a>";
                    }
                    else
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\">" + i + "</a>";
                    }

                }
                if (page > 1)
                {
                    int prePage = contentObj.getPage() - 1;
                    pageStr = "<a class=\"page_left\" href=\"" + sharedConstant.getContentViewUrl(contentId)
                              + "?page=" + prePage
                              + "\"><img src=\"/img/xiongan_prev_page.png\" border=\"0\"></a>"
                              + pageStr;
                }
                if (page < pageCount)
                {
                    int nextPage = contentObj.getPage() + 1;
                    pageStr = pageStr + "<a class=\"page_right\" href=\""
                              + sharedConstant.getContentViewUrl(contentId) + "?page=" + nextPage
                              + "\"><img src=\"/img/xiongan_next_page.png\" border=\"0\"></a>";
                }
                pageStr = "<span class=\"xionganpage\">" + pageStr + "</span>";
            }
        }
        // 处理要闻精编前面插入分页代码
        String regexjb = "<div\\s*class=\\s*\"\\s*jingbian2012\\s*\"([^>]*)\\s*>";
        final Pattern pajb = Pattern.compile(regexjb, Pattern.DOTALL);
        final Matcher majb = pajb.matcher(rt);
        if (majb.find())
        {
            String jb = majb.group();
            int intjb = rt.indexOf(jb);
            rt = "&$" + rt.substring(0, intjb) + pageStr + rt.substring(intjb) + "&$";
        }
        else
        {
            rt = "&$" + rt + pageStr + "&$";
        }
        // 处理要闻精编前面插入分页代码

        String regex = "<\\s*img\\s+([^>]*)\\s*>";
        final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(rt);
        String re = "";
        String x = "";
        while (ma.find())
        {
            x = ma.group();
            re = x.replaceAll("(<img.*?)style=\".*?\"", "$1");
            rt = rt.replace(x, re);
        }
        // 处理视频
        rt = StringTool.replaceEmbed(rt);
        rt = Encode.htmlsp(rt);
        return rt;
    }

    private String getContentImgNextText()
    {

        // String contentPrefix = contentObj.getContentPrefix();
        // String contentPostfix = contentObj.getContentPostfix();
        String rt = contentObj.getContentString();
        /*
         * if(contentPrefix!=null){ rt=contentPrefix+contentObj.getContentString(); }
         */
        Integer language_id = contentObj.getLanguageId();
        Integer pageType = contentObj.getPageType();
        Integer contentId = contentObj.getContentLogicId();

        int page = contentObj.getPage();

        int pageCount = contentObj.getPageCount();

        if (pageCount != 1 && page < pageCount)
        {
            int nextPage = contentObj.getPage() + 1;
            String regex = "<\\s*img\\s+([^>]*)\\s*>";
            final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
            final Matcher ma = pa.matcher(rt);
            while (ma.find())
            {
                String img = ma.group();
                String imghref = "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page=" + nextPage
                                 + "\">" + img + "</a>";
                rt = rt.replace(img, imghref);
            }
        }

        rt = Encode.htmlsp(rt);

        String pageStr = "";

        if (pageType != null)
        {
            if (language_id == 1 || language_id == 31 || language_id == 45)
            {
                pageStr = "<div class=\"zdfy clearfix\">";
                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page="
                                  + i + "\">【" + i + "】</a>";
                    }
                }
                pageStr = pageStr + "</div>";
                pageStr = pageStr
                          + "<center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";

                if (page > 1)
                {
                    int prePage = contentObj.getPage() - 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page="
                              + prePage + "\"><img src=\"/img/prev_page.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                if (page < pageCount)
                {
                    int nextPage = contentObj.getPage() + 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page="
                              + nextPage + "\"><img src=\"/img/next_page.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                pageStr = pageStr + "</tr></table></center>";

            }
            else if (language_id == 3 || language_id == 5 || language_id == 6 || language_id == 29)
            {
                pageStr = "<br><center>";
                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page="
                                  + i + "\" class=\"abl2\">【" + i + "】</a>";
                    }
                }

                pageStr = pageStr + "</center>";
                pageStr = pageStr
                          + "<br><center><table border=\"0\" align=\"center\"><tr><td><a href=\""
                          + sharedConstant.getContentViewUrl(contentId) + "\"></a></td></tr></table></center>";

            }
            else if (language_id == 4)
            {
                pageStr = "<br><center>";
                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page="
                                  + i + "\" class=\"abl2\">[" + i + "]</a>";
                    }
                }
                pageStr = pageStr + "</center>";
                pageStr = pageStr
                          + "<br><center><table border=\"0\" align=\"center\"><tr><td><a href=\""
                          +  sharedConstant.getContentViewUrl(contentId)  + "\"></a></td></tr></table></center>";

            }
            else
            {
                pageStr = "<br><center>";
                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page="
                                  + i + "\" class=\"abl2\">【" + i + "】</a>";
                    }
                }

                pageStr = pageStr + "</center>";

                pageStr = pageStr
                          + "<br><center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";
                if (page > 1)
                {
                    int prePage = contentObj.getPage() - 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page="
                              + prePage
                              + "\"><img src=\"/img/2007english/Previous.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                if (page < pageCount)
                {
                    int nextPage = contentObj.getPage() + 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page="
                              + nextPage
                              + "\"><img src=\"/img/2007english/Next.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                pageStr = pageStr + "</tr></table></center>";
            }
        }

        // 处理要闻精编前面插入分页代码
        String regexjb = "<div\\s*class=\\s*\"\\s*jingbian2012\\s*\"\\s*>";
        final Pattern pajb = Pattern.compile(regexjb, Pattern.DOTALL);
        final Matcher majb = pajb.matcher(rt);
        if (majb.find())
        {
            String jb = majb.group();
            int intjb = rt.indexOf(jb);
            rt = "&$" + rt.substring(0, intjb) + pageStr + rt.substring(intjb) + "&$";
        }
        else
        {
            rt = "&$" + rt + pageStr + "&$";
        }

        String regex = "<\\s*img\\s+([^>]*)\\s*>";
        // String s = "<p style=\"text-align: center; \"><img alt=\"\"
        // src=\"/NMediaFile/2012/0929/MAIN201209291016000268058587667.jpg\" style=\"width: 480px;
        // height: 360px; \" /></p><p style=\"text-align: center; \">";
        final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(rt);
        String re = "";
        String x = "";
        while (ma.find())
        {
            x = ma.group();
            if (page < pageCount)
            {
                int nextPage = contentObj.getPage() + 1;
                x = "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page=" + nextPage + "\">" + x
                    + "</a>";
            }
            String regex1 = "style=\"width:\\s*[0-9]{1,3}px;\\s*height:\\s*[0-9]{1,3}px[;]{0,1}\\s*\"";
            Pattern pa1 = Pattern.compile(regex1, Pattern.DOTALL);
            Matcher ma1 = pa1.matcher(x);
            String xx = "";
            String newStr = "";
            while (ma1.find())
            {
                xx = ma1.group();
                String regex2 = "[a-z]*:\\s*[0-9]{1,3}";
                Pattern pa2 = Pattern.compile(regex2, Pattern.DOTALL);
                Matcher ma2 = pa2.matcher(xx);
                while (ma2.find())
                {
                    String xxx = ma2.group().replace(":", "=") + " ";
                    newStr = newStr + xxx;
                }
                re = x.replace(xx, newStr);
                rt = rt.replace(x, re);
            }
        }
        rt = Encode.htmlsp(rt);
        return rt;
    }

    private String getContentPhotoText()
    {

        // String contentPrefix = contentObj.getContentPrefix();
        // String contentPostfix = contentObj.getContentPostfix();
        String rt = contentObj.getContentString();
        /*
         * if(contentPrefix!=null){ rt=contentPrefix+contentObj.getContentString(); }
         */
        Integer language_id = contentObj.getLanguageId();
        Integer pageType = contentObj.getPageType();

        int page = contentObj.getPage();

        int pageCount = contentObj.getPageCount();
        
        Integer contentId = contentObj.getContentLogicId();

        if (pageCount != 1 && page < pageCount)
        {
            int nextPage = contentObj.getPage() + 1;
            String regex = "<\\s*img\\s+([^>]*)\\s*>";
            final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
            final Matcher ma = pa.matcher(rt);
            while (ma.find())
            {
                String img = ma.group();
                String imghref = "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page=" + nextPage
                                 + "\">" + img + "</a>";
                rt = rt.replace(img, imghref);
            }
        }

        rt = Encode.htmlsp(rt);

        String pageStr = "";

        if (pageType != null)
        {
            if (language_id == 1 || language_id == 31 || language_id == 45)
            {
                pageStr = pageStr
                          + "<center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";

                if (page > 1)
                {
                    int prePage = contentObj.getPage() - 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page="
                              + prePage + "\"><img src=\"/img/prev_page.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                if (page < pageCount)
                {
                    int nextPage = contentObj.getPage() + 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page="
                              + nextPage + "\"><img src=\"/img/next_page.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                pageStr = pageStr + "</tr></table></center>";

            }
            else if (language_id == 3 || language_id == 5 || language_id == 6 || language_id == 29)
            {
                pageStr = "<br><center>";
                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page="
                                  + i + "\" class=\"abl2\">【" + i + "】</a>";
                    }
                }

                pageStr = pageStr + "</center>";
                pageStr = pageStr
                          + "<br><center><table border=\"0\" align=\"center\"><tr><td><a href=\""
                          +  sharedConstant.getContentViewUrl(contentId)  + "\"></a></td></tr></table></center>";

            }
            else if (language_id == 4)
            {
                pageStr = "<br><center>";
                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page="
                                  + i + "\" class=\"abl2\">[" + i + "]</a>";
                    }
                }
                pageStr = pageStr + "</center>";
                pageStr = pageStr
                          + "<br><center><table border=\"0\" align=\"center\"><tr><td><a href=\""
                          +  sharedConstant.getContentViewUrl(contentId)  + "\"></a></td></tr></table></center>";

            }
            else
            {
                pageStr = "<br><center>";
                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page="
                                  + i + "\" class=\"abl2\">【" + i + "】</a>";
                    }
                }

                pageStr = pageStr + "</center>";

                pageStr = pageStr
                          + "<br><center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";
                if (page > 1)
                {
                    int prePage = contentObj.getPage() - 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page="
                              + prePage
                              + "\"><img src=\"/img/2007english/Previous.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                if (page < pageCount)
                {
                    int nextPage = contentObj.getPage() + 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page="
                              + nextPage
                              + "\"><img src=\"/img/2007english/Next.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                pageStr = pageStr + "</tr></table></center>";
            }
        }
//		rt = rt+pageStr;
        rt = "&$" + rt + pageStr + "&$";

        rt = Encode.htmlsp(rt);

        return rt;
    }

    private String getContentGqPic()
    {
        String rt = contentObj.getContentString();
        int page = contentObj.getPage();
        int pageCount = contentObj.getPageCount();
        Integer contentId = contentObj.getContentLogicId();
        if (pageCount != 1 && page < pageCount)
        {
            int nextPage = contentObj.getPage() + 1;
            String regex = "<\\s*img\\s+([^>]*)\\s*>";
            final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
            final Matcher ma = pa.matcher(rt);
            while (ma.find())
            {
                String img = ma.group();
                String imghref = "<a href=\"" +  sharedConstant.getContentViewUrl(contentId)  + "?page=" + nextPage
                                 + "\">" + img + "</a>";
                rt = rt.replace(img, imghref);
            }
        }
        rt = Encode.htmlsp(rt);
        String pageStr = "";
        if (pageCount != 1)
        {
            pageStr = "<div class=\"page_n\">";
            int startnum = page;
            if (startnum <= 0)
            {
                startnum = 1;
            }
            int endnum = startnum + 4;

            if (endnum > pageCount)
            {
                endnum = pageCount;
            }
            for (int i = 1; i <= pageCount; i++ )
            {
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page=" + i
                          + "\">" + i + "</a>";
            }
            pageStr = pageStr + "</div>";
            pageStr = pageStr + "<center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";
            if (page > 1)
            {
                int prePage = contentObj.getPage() - 1;
                pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page=" + prePage
                          + "\"><img src=\"/img/prev_page.jpg\" border=\"0\"></a>";
                pageStr = pageStr + "</td>";
            }
            if (page < pageCount)
            {
                int nextPage = contentObj.getPage() + 1;
                pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                          + nextPage + "\"><img src=\"/img/next_page.jpg\" border=\"0\"></a>";
                pageStr = pageStr + "</td>";
            }
            pageStr = pageStr + "</tr></table></center>";
        }
        rt = "&$" + rt + pageStr + "&$";
        rt = Encode.htmlsp(rt);
        return rt;
    }

    private String getContentText_noencode()
    {
        String rt = contentObj.getContentString();
        Integer language_id = contentObj.getLanguageId();
        Integer pageType = contentObj.getPageType();
        int page = contentObj.getPage();
        int pageCount = contentObj.getPageCount();
        Integer contentId = contentObj.getContentLogicId();
        if (pageCount != 1 && page < pageCount)
        {
            int nextPage = contentObj.getPage() + 1;
            String regex = "<\\s*img\\s+([^>]*)\\s*>";
            final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
            final Matcher ma = pa.matcher(rt);
            while (ma.find())
            {
                String img = ma.group();
                String imghref = "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page=" + nextPage
                                 + "\">" + img + "</a>";
                rt = rt.replace(img, imghref);
            }
        }
        rt = Encode.htmlsp_nospace(rt);
        String pageStr = "";
        if (pageType != null && pageCount > 1)
        {
            pageStr = "<br><center>";
            if (pageCount != 1)
            {
                for (int i = 1; i <= pageCount; i++ )
                {
                    if (page == i)
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\" class=\"common_current_page\">【" + i + "】</a>";
                    }
                    else
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\" class=\"abl2\">【" + i + "】</a>";
                    }
                }
            }
            pageStr = pageStr + "</center>";
            pageStr = pageStr
                      + "<br><center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";
            String pre = "/img/2007english/Previous.jpg";
            String next = "/img/2007english/Next.jpg";
            if (language_id == 29)
            {
                pre = "/img/japanese/pre.jpg";
                next = "/img/japanese/next.jpg";
            }
            if (language_id == 3)
            {
                pre = "/img/page/pre_fr.jpg";
                next = "/img/page/next_fr.jpg";
            }
            if (language_id == 4)
            {
                pre = "/img/page/pre_ru.jpg";
                next = "/img/page/next_ru.jpg";
            }
            if (language_id == 5)
            {
                pre = "/img/page/pre_sp.jpg";
                next = "/img/page/next_sp.jpg";
            }
            if (language_id == 6)
            {
                pre = "/img/page/pre_ar.jpg";
                next = "/img/page/next_ar.jpg";
            }
            if (language_id == 61)
            {
                pre = "/img/page/pre_kr.jpg";
                next = "/img/page/next_kr.jpg";
            }
            if (language_id == 80)
            {
                pre = "/img/page/pre_ger.jpg";
                next = "/img/page/next_ger.jpg";
            }
            if (language_id == 100)
            {
                pre = "/img/page/pre_pt.png";
                next = "/img/page/next_pt.png";
            }
            if (page > 1)
            {
                int prePage = contentObj.getPage() - 1;
                pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page=" + prePage
                          + "\"><img src=\"" + pre + "\" border=\"0\"></a>";
                pageStr = pageStr + "</td>";
            }
            if (page < pageCount)
            {
                int nextPage = contentObj.getPage() + 1;
                pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                          + nextPage + "\"><img src=\"" + next + "\" border=\"0\"></a>";
                pageStr = pageStr + "</td>";
            }
            pageStr = pageStr + "</tr></table></center>";
        }
        rt = "&$" + rt + pageStr + "&$";
        return Encode.htmlsp_nospace(rt);
    }

    private String getContentText_noencode_new()
    {
        String rt = contentObj.getContentString();
        Integer language_id = contentObj.getLanguageId();
        Integer pageType = contentObj.getPageType();
        int page = contentObj.getPage();
        int pageCount = contentObj.getPageCount();
        Integer contentId = contentObj.getContentLogicId();
        rt = rt.replace("&$", "");
        String pageStr = "";

        if (pageType != null)
        {
            if (language_id == 1)
            {

                pageStr = "<br><center>";

                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\">【" + i + "】</a>";
                    }
                }

                pageStr = pageStr + "</center>";
                pageStr = pageStr
                          + "<br><center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";

                if (page > 1)
                {
                    int prePage = contentObj.getPage() - 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                              + prePage + "\"><img src=\"/img/prev_page.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                if (page < pageCount)
                {
                    int nextPage = contentObj.getPage() + 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                              + nextPage + "\"><img src=\"/img/next_page.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                pageStr = pageStr + "</tr></table></center>";
            }
            else
            {
                pageStr = "<br><center>";
                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\" class=\"abl2\">【" + i + "】</a>";
                    }
                }

                pageStr = pageStr + "</center>";

                pageStr = pageStr
                          + "<br><center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";
                if (page > 1)
                {
                    int prePage = contentObj.getPage() - 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                              + prePage
                              + "\"><img src=\"/img/2007english/Previous.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                if (page < pageCount)
                {
                    int nextPage = contentObj.getPage() + 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                              + nextPage
                              + "\"><img src=\"/img/2007english/Next.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                pageStr = pageStr + "</tr></table></center>";
            }
        }

        rt = "&$" + rt + pageStr + "&$";

        return Encode.htmlsp_nospace(rt);
    }

    private String getContentText_noencode_french()
    {
        String rt = contentObj.getContentString();
        Integer pageType = contentObj.getPageType();
        int page = contentObj.getPage();
        int pageCount = contentObj.getPageCount();
        Integer contentId = contentObj.getContentLogicId();
        rt = rt.replace("&$", "");
        String pageStr = "";
        if (pageType != null)
        {
            if (pageCount != 1)
            {
                pageStr = "<div class=\"d2_3\">";
                pageStr += "<span>" + page + "/" + pageCount + "</span>";
                if (page > 1)
                {
                    int prePage = contentObj.getPage() - 1;
                    pageStr = pageStr + "<em><a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                              + prePage + "\">Précédente</a></em>";
                }
                for (int i = 1; i <= pageCount; i++ )
                {
                    if (i == page)
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\" class=\"on\">" + i + "</a>";
                    }
                    else
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\">" + i + "</a>";
                    }
                }
                if (page < pageCount)
                {
                    int nextPage = contentObj.getPage() + 1;
                    pageStr = pageStr + "<em><a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                              + nextPage + "\">Suivante</a></em>";
                }
                pageStr = pageStr + "</div>";
            }

        }
        rt = "&$" + rt + pageStr + "&$";
        return Encode.htmlsp_nospace(rt);
    }

    private String getContentText_Group()
    {
        String rt = contentObj.getContentString();
        Integer pageType = contentObj.getPageType();
        int page = contentObj.getPage();
        int pageCount = contentObj.getPageCount();
        Integer contentId = contentObj.getContentLogicId();
        if (pageCount != 1 && page < pageCount)
        {
            int nextPage = contentObj.getPage() + 1;
            String regex = "<\\s*img\\s+([^>]*)\\s*>";
            final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
            final Matcher ma = pa.matcher(rt);
            while (ma.find())
            {
                String img = ma.group();
                String imgRegx = img.replace("(", "\\(");
                imgRegx = imgRegx.replace(")", "\\)");
                String regex1 = "<a\\s.*?href=\"([^\"]+)\"[^>]*>\\s*" + imgRegx + "\\s*</a>";
                final Pattern pa1 = Pattern.compile(regex1, Pattern.DOTALL);
                final Matcher ma1 = pa1.matcher(rt);
                if (!ma1.find())
                {
                    String imghref = "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                     + nextPage + "\">" + img + "</a>";
                    rt = rt.replace(img, imghref);
                }
            }
        }
        rt = Encode.htmlsp(rt);

        String pageStr = "";
        if (pageType != null && pageType == 1)
        {
            pageStr = "<div class=\"zdfy clearfix\">";
            if (pageCount != 1)
            {
                for (int i = 1; i <= pageCount; i++ )
                {
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page=" + i
                              + "\">【" + i + "】</a>";
                }
            }
            pageStr = pageStr + "</div>";
            pageStr = pageStr + "<center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";

            if (page > 1)
            {
                int prePage = contentObj.getPage() - 1;
                pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page=" + prePage
                          + "\"><img src=\"/img/prev_page.jpg\" border=\"0\"></a>";
                pageStr = pageStr + "</td>";
            }
            if (page < pageCount)
            {
                int nextPage = contentObj.getPage() + 1;
                pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                          + nextPage + "\"><img src=\"/img/next_page.jpg\" border=\"0\"></a>";
                pageStr = pageStr + "</td>";
            }
            pageStr = pageStr + "</tr></table></center>";
        }
        // 处理要闻精编前面插入分页代码
        String regexjb = "<div\\s*class=\\s*\"\\s*jingbian2012\\s*\"([^>]*)\\s*>";
        final Pattern pajb = Pattern.compile(regexjb, Pattern.DOTALL);
        final Matcher majb = pajb.matcher(rt);
        if (majb.find())
        {
            String jb = majb.group();
            int intjb = rt.indexOf(jb);
            rt = "&$" + rt.substring(0, intjb) + pageStr + rt.substring(intjb) + "&$";
        }
        else
        {
            rt = "&$" + rt + pageStr + "&$";
        }
        // 处理要闻精编前面插入分页代码

        String regex = "<\\s*img\\s+([^>]*)\\s*>";
        // String s = "<p style=\"text-align: center; \"><img alt=\"\"
        // src=\"/NMediaFile/2012/0929/MAIN201209291016000268058587667.jpg\" style=\"width: 480px;
        // height: 360px; \" /></p><p style=\"text-align: center; \">";
        final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(rt);
        String re = "";
        String x = "";
        while (ma.find())
        {
            x = ma.group();
            String regex1 = "style=\"([^>]*)(width|height):\\s*[0-9]{1,4}px;\\s*(width|height):\\s*[0-9]{1,4}px;\\s*\"";
            Pattern pa1 = Pattern.compile(regex1, Pattern.DOTALL);
            Matcher ma1 = pa1.matcher(x);
            String xx = "";
            String newStr = "";
            while (ma1.find())
            {
                xx = ma1.group();
                String regex2 = "(width|height):\\s*[0-9]{1,4}";
                Pattern pa2 = Pattern.compile(regex2, Pattern.DOTALL);
                Matcher ma2 = pa2.matcher(xx);
                while (ma2.find())
                {
                    String xxx = ma2.group().replace(":", "=") + " ";
                    newStr = newStr + xxx;
                }
            }
            re = x.replace(xx, newStr);
        }
        rt = rt.replace(x, re);
        // 处理视频
        rt = StringTool.replaceEmbed(rt);
        rt = Encode.htmlsp(rt);
        return rt;
    }

    // 增加参数表示不同的广告地址
    private String getContentText_Ad(int adNo)
    {
        String rt = contentObj.getContentString();
        Integer language_id = contentObj.getLanguageId();
        Integer pageType = contentObj.getPageType();
        int page = contentObj.getPage();
        int pageCount = contentObj.getPageCount();
        String contentId = contentObj.getContentId();
        ContentTool contentTool = new ContentTool();

        // rt=Encode.htmlsp(rt);
        rt = rt.replace("&$", "");
        // 处理要闻精编前加入分页代码
        String pageStr = getPageStr(pageType, pageCount, page, contentObj.getContentLogicId());
        String regexjb = "<div\\s*class=\\s*\"\\s*jingbian2012\\s*\"([^>]*)\\s*>";
        final Pattern pajb = Pattern.compile(regexjb, Pattern.DOTALL);
        final Matcher majb = pajb.matcher(rt);
        if (majb.find())
        {
            String jb = majb.group();
            int intjb = rt.indexOf(jb);
            pageStr = pageStr + rt.substring(intjb);
            rt = rt.substring(0, intjb);
        }
        // 正文图片翻页功能
        rt = getImgNextPage(page, pageCount, contentObj.getContentLogicId(), rt);
        // 处理正文图片样式
        rt = contentTool.getImgStyle(rt);
        // 处理视频
        rt = contentTool.replaceEmbed(rt);
        // 处理图片视频缩进
        rt = contentTool.getPicNoIndent(rt);
        // 处理长文章广告代码
        rt = getLongContentAd(rt, adNo);
        // 添加分页代码
        rt = rt + pageStr;

        // 处理要闻精编前面插入分页代码
        /*
         * String regexjb = "<div\\s*class=\\s*\"\\s*jingbian2012\\s*\"([^>]*)\\s*>"; final Pattern
         * pajb = Pattern.compile(regexjb, Pattern.DOTALL); final Matcher majb = pajb.matcher(rt);
         * if (majb.find()){ String jb = majb.group(); int intjb = rt.indexOf(jb); rt =
         * "&$"+rt.substring(0, intjb)+pageStr+rt.substring(intjb)+"&$"; }else{ rt =
         * "&$"+rt+pageStr +"&$"; }
         */
        // rt=Encode.htmlsp(rt);

        return rt;
    }

    // 增加参数表示不同的广告地址
    private String getContentText_Adstr(int adNo)
    {
        String rt = contentObj.getContentString();
        // String regex = "<table.*?>(.*?)</table>";
        /*
         * String regex = "<table.*?>\\s.*</table>"; final Pattern ptab = Pattern.compile(regex,
         * Pattern.DOTALL); final Matcher mtab = ptab.matcher(rt);
         */
        if (rt.contains("table") || rt.contains("TABLE") || rt.contains("div")
            || rt.contains("DIV") || rt.contains("<br>") || rt.contains("<br />"))
        {
            rt = getContentNoStyleText();
        }
        else
        {
            rt = getContentText_Ad(adNo);
        }
        return rt;
    }

    // 正文图片不添加下一页功能
    private String getContentText_PicNotNext()
    {
        String rt = contentObj.getContentString();
        Integer pageType = contentObj.getPageType();
        int page = contentObj.getPage();
        int pageCount = contentObj.getPageCount();
        String contentId = contentObj.getContentId();
        ContentTool contentTool = new ContentTool();
        rt = rt.replace("&$", "");
        // 处理要闻精编前加入分页代码
        String pageStr = getPageStr(pageType, pageCount, page, contentObj.getContentLogicId());
        String regexjb = "<div\\s*class=\\s*\"\\s*jingbian2012\\s*\"([^>]*)\\s*>";
        final Pattern pajb = Pattern.compile(regexjb, Pattern.DOTALL);
        final Matcher majb = pajb.matcher(rt);
        if (majb.find())
        {
            String jb = majb.group();
            int intjb = rt.indexOf(jb);
            pageStr = pageStr + rt.substring(intjb);
            rt = rt.substring(0, intjb);
        }
        // 处理正文图片样式
        rt = contentTool.getImgStyle(rt);
        // 处理视频
        rt = contentTool.replaceEmbed(rt);
        // 处理图片视频缩进
        rt = contentTool.getPicNoIndent(rt);
        // 添加分页代码
        rt = rt + pageStr;
        return rt;
    }

    private String getContentText_noencodekuohao()
    {

        String rt = contentObj.getContentString();
        Integer language_id = contentObj.getLanguageId();
        Integer pageType = contentObj.getPageType();
        int page = contentObj.getPage();
        int pageCount = contentObj.getPageCount();
        Integer contentId = contentObj.getContentLogicId();
        if (pageCount != 1 && page < pageCount)
        {
            int nextPage = contentObj.getPage() + 1;
            String regex = "<\\s*img\\s+([^>]*)\\s*>";
            final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
            final Matcher ma = pa.matcher(rt);
            while (ma.find())
            {
                String img = ma.group();
                String imghref = "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page=" + nextPage
                                 + "\">" + img + "</a>";
                rt = rt.replace(img, imghref);
            }
        }
        rt = Encode.htmlsp_nospacekuohao(rt);
        // 处理正文图片样式
        ContentTool contentTool = new ContentTool();
        rt = contentTool.getImgStyle(rt);
        String pageStr = "";
        if (pageType != null && pageCount > 1)
        {
            pageStr = "<br><center>";
            if (pageCount != 1)
            {
                for (int i = 1; i <= pageCount; i++ )
                {
                    if (page == i)
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\" class=\"common_current_page\">【" + i + "】</a>";
                    }
                    else
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\" class=\"abl2\">【" + i + "】</a>";
                    }
                }
            }
            pageStr = pageStr + "</center>";
            pageStr = pageStr
                      + "<br><center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";
            String pre = "/img/2007english/Previous.jpg";
            String next = "/img/2007english/Next.jpg";
            if (language_id == 29)
            {
                pre = "/img/japanese/pre.jpg";
                next = "/img/japanese/next.jpg";
            }
            if (language_id == 3)
            {
                pre = "/img/page/pre_fr.jpg";
                next = "/img/page/next_fr.jpg";
            }
            if (language_id == 4)
            {
                pre = "/img/page/pre_ru.jpg";
                next = "/img/page/next_ru.jpg";
            }
            if (language_id == 5)
            {
                pre = "/img/page/pre_sp.jpg";
                next = "/img/page/next_sp.jpg";
            }
            if (language_id == 6)
            {
                pre = "/img/page/pre_ar.jpg";
                next = "/img/page/next_ar.jpg";
            }
            if (language_id == 61)
            {
                pre = "/img/page/pre_kr.jpg";
                next = "/img/page/next_kr.jpg";
            }
            if (language_id == 80)
            {
                pre = "/img/page/pre_ger.jpg";
                next = "/img/page/next_ger.jpg";
            }
            if (page > 1)
            {
                int prePage = contentObj.getPage() - 1;
                pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page=" + prePage
                          + "\"><img src=\"" + pre + "\" border=\"0\"></a>";
                pageStr = pageStr + "</td>";
            }
            if (page < pageCount)
            {
                int nextPage = contentObj.getPage() + 1;
                pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                          + nextPage + "\"><img src=\"" + next + "\" border=\"0\"></a>";
                pageStr = pageStr + "</td>";
            }
            pageStr = pageStr + "</tr></table></center>";
        }
        rt = "&$" + rt + pageStr + "&$";
        return Encode.htmlsp_nospacekuohao(rt);

    }

    private String getContentText_noencodekuohao_new()
    {

        // String contentPrefix = contentObj.getContentPrefix();
        // String contentPostfix = contentObj.getContentPostfix();
        String rt = contentObj.getContentString();
        /*
         * if(contentPrefix!=null){ rt=contentPrefix+contentObj.getContentString(); }
         */
        Integer language_id = contentObj.getLanguageId();
        Integer pageType = contentObj.getPageType();

        int page = contentObj.getPage();

        int pageCount = contentObj.getPageCount();

        Integer contentId = contentObj.getContentLogicId();
        rt = rt.replace("&$", "");

        String pageStr = "";

        if (pageType != null)
        {
            if (language_id == 1)
            {
                pageStr = "<br><center>";

                if (pageCount != 1)
                {
                    for (int i = 1; i <= pageCount; i++ )
                    {
                        pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                                  + i + "\">【" + i + "】</a>";
                    }
                }

                pageStr = pageStr + "</center>";
                pageStr = pageStr
                          + "<br><center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";

                if (page > 1)
                {
                    int prePage = contentObj.getPage() - 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                              + prePage + "\"><img src=\"/img/prev_page.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                if (page < pageCount)
                {
                    int nextPage = contentObj.getPage() + 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                              + nextPage + "\"><img src=\"/img/next_page.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                pageStr = pageStr + "</tr></table></center>";
            }

            if (language_id == 5)
            {
                pageStr = "<br><center>";
                for (int i = 1; i <= pageCount; i++ )
                {
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page=" + i
                              + "\" class=\"abl2\">【" + i + "】</a>";
                }
                pageStr = pageStr + "</center>";

                pageStr = pageStr
                          + "<br><center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";
                if (page > 1)
                {
                    int prePage = contentObj.getPage() - 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                              + prePage
                              + "\"><img src=\"/img/2007english/Previous.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                if (page < pageCount)
                {
                    int nextPage = contentObj.getPage() + 1;
                    pageStr = pageStr + "<td width=\"50%\" align=\"center\">";
                    pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page="
                              + nextPage
                              + "\"><img src=\"/img/2007english/Next.jpg\" border=\"0\"></a>";
                    pageStr = pageStr + "</td>";
                }
                pageStr = pageStr + "</tr></table></center>";
            }
        }
        rt = "&$" + rt + pageStr + "&$";
        return Encode.htmlsp_nospacekuohao(rt);
    }

    // TODO 原创类型？？？？？
    private String getOriginalContentType()
    {
        /*
         * ReadDBTool readDao = (ReadDBTool) SpringContextUtil.getBean("ReadDBTool"); String
         * queryString = "from OriginalContent where contentId ="+contentId; List list =
         * readDao.find(queryString); String res = ""; if(list != null&& list.size()>0){
         * OriginalContent originalContent = (OriginalContent)list.get(0); res =
         * String.valueOf(originalContent.getType()); } return res;
         */
        return "";
    }

    // TODO 原创用户
    private String getOriginalContentAuthor()
    {
        /*
         * ReadDBTool readDao = (ReadDBTool) SpringContextUtil.getBean("ReadDBTool"); String
         * queryString = "from OriginalContent where contentId ="+contentId+" order by id"; List
         * list = readDao.find(queryString); String res = ""; String userId = ""; if(list != null&&
         * list.size()>0){ for(int j = 0;j<list.size();j++){ OriginalContent originalContent =
         * (OriginalContent)list.get(j); res = String.valueOf(originalContent.getAuthor()); String
         * sql = "from User where user_Deleted=0 and user_Name='" + res+"'"; List list1 =
         * readDao.find(sql); if(list1!=null && list1.size()>0){ User user = (User)list1.get(0);
         * if(j==0){ userId = String.valueOf(user.getUser_Id()); }else{ userId = userId +
         * ";"+String.valueOf(user.getUser_Id()); } }else{ if(j ==0 ){ userId = "1000000000";
         * }else{ userId = userId + ";1000000000"; } } } if(SYSTEM_NAME.equals("LOCAL")){ userId =
         * "L_"+userId; }else if(SYSTEM_NAME.equals("FOREIGN")){ userId = "F_"+userId; } } return
         * userId;
         */
        return "";
    }

    public String getTitle()
    {
        String title = Encode.htmlsp(contentObj.getTitle());
        Integer language_id = contentObj.getLanguageId();
        Integer pageType = contentObj.getPageType();
        if (pageType != null)
        {
            if (language_id == 1)
            {
                int pageNo = contentObj.getPage();
                if (pageNo > 1)
                {
                    title = title + "【" + pageNo + "】";
                }
            }
        }
        return title;
    }

    public String getTITLE_Prefix()
    {
        String title = Encode.htmlsp(contentObj.getContentPrefix());
        Integer language_id = contentObj.getLanguageId();
        Integer pageType = contentObj.getPageType();
        if (pageType != null)
        {
            if (language_id == 1)
            {
                int pageNo = contentObj.getPage();
                if (pageNo > 1)
                {
                    title = title + "【" + pageNo + "】";
                }
            }
        }
        return title;
    }

    public String getTITLE_Postfix()
    {
        String title = Encode.htmlsp(contentObj.getContentPostfix());
        Integer language_id = contentObj.getLanguageId();
        Integer pageType = contentObj.getPageType();
        if (pageType != null)
        {
            if (language_id == 1)
            {
                int pageNo = contentObj.getPage();
                if (pageNo > 1)
                {
                    title = title + "【" + pageNo + "】";
                }
            }
        }
        return title;
    }

    public String getTitle_()
    {
        String title = contentObj.getTitle().replace("&$", "");
        HtmlToText html = new HtmlToText();
        title = html.html2Text(title);

        Integer language_id = contentObj.getLanguageId();
        Integer pageType = contentObj.getPageType();
        if (pageType != null)
        {
            if (language_id == 1)
            {
                int pageNo = contentObj.getPage();
                if (pageNo > 1)
                {
                    title = title + "【" + pageNo + "】";
                }
            }
        }
        return title;
    }

    public String getSummary()
    {
        StringBuffer res = new StringBuffer();
        if (contentObj.getSummary() != null && contentObj.getSummary().length() > 0)
        {
            res.append(
                "<table class=\"summary\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td valign=\"top\" align=\"left\">"
                       + Encode.htmlsp(contentObj.getSummary()) + "</td></tr></table>");
        }
        return res.toString();
    }

    public String getSummary1()
    {
        return "已失效";
    }

    public String getSummaryHaiWai()
    {
        StringBuffer res = new StringBuffer();
        if (contentObj.getSummary() != null && contentObj.getSummary().length() > 0)
        {
            res.append(
                "<table class=\"summary\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td valign=\"top\" align=\"left\">摘要："
                       + Encode.htmlsp(contentObj.getSummary()) + "</td></tr></table>");
        }
        return res.toString();
    }

    public String getnewtitle()
    {
        List ds = getSecondChannels();
        String channel_name = ((Channel)ds.get(0)).getChannelName();
        HtmlToText html = new HtmlToText();
        return html.html2Text(this.getTitle()) + "--" + channel_name + "--人民网 ";
    }

    public String getnewtitleHaiWai()
    {
        List ds = getSecondChannels();
        String channel_name = ((Channel)ds.get(0)).getChannelName();

        HtmlToText html = new HtmlToText();
        return html.html2Text(this.getTitle()) + "--" + channel_name + "--海外网";
    }

    public String getgcdtitle()
    {
        List ds = getSecondChannels();
        String channel_name = ((Channel)ds.get(0)).getChannelName();

        return this.getTitle() + "--" + channel_name;

    }

    public String getTitle_noencode()
    {
        Integer pageType = contentObj.getPageType();
        String title = contentObj.getTitle();
        if (pageType != null)
        {
            int pageNo = contentObj.getPage();
            if (pageNo > 1)
            {
                title = title + " (" + pageNo + ")";
            }
        }
        return Encode.htmlsp_nospace(title);
    }

    public String getTitleArabic_noencode()
    {
        Integer pageType = contentObj.getPageType();
        String title = contentObj.getTitle();
        Short contentType = contentObj.getContentType();
        if (contentType == 1)
        {
            title = title + "&$<img src=\"/img/2011arabic/images/icon05.gif\" alt=\"\" />&$";
        }
        if (pageType != null)
        {
            int pageNo = contentObj.getPage();
            if (pageNo > 1)
            {
                title = title + " (" + pageNo + ")";
            }
        }
        return Encode.htmlsp_nospace(title);
    }

    public String getEditor()
    {
        String re = "";
        String editor = contentObj.getEditor() == null ? "" : contentObj.getEditor().trim();
        String userName = contentObj.getReleaseBy()== null ? "" : contentObj.getReleaseBy();

        if (editor.equals("") && userName.equals(""))
        {
            re = "";
        }
        else if (editor.equals("") && !userName.equals(""))
        {
            re = "(责编：" + Encode.htmlsp(userName) + ")";
        }
        else if (!editor.equals("") && userName.equals(""))
        {
            re = "(责编：" + Encode.htmlsp(editor) + ")";
        }
        else if (!editor.equals("") && !userName.equals(""))
        {
            if (!editor.equals(userName))
            {
                re = "(责编：" + Encode.htmlsp(editor) + "、" + Encode.htmlsp(userName) + ")";
            }
            else
            {
                re = "(责编：" + Encode.htmlsp(editor) + ")";
            }
        }
        return re;

    }

    // 责编和签发人只显示一个
    public String getEditor_one()
    {
        String re = "";
        String editor = contentObj.getEditor() == null ? "" : contentObj.getEditor().trim();
        String userName = contentObj.getReleaseBy();

        if (editor.equals("") && userName.equals(""))
        {
            re = "";
        }
        else if (editor.equals("") && !userName.equals(""))
        {
            re = "(责任编辑：" + Encode.htmlsp(userName) + ")";
        }
        else if (!editor.equals("") && userName.equals(""))
        {
            re = "(责任编辑：" + Encode.htmlsp(editor) + ")";
        }
        else if (!editor.equals("") && !userName.equals(""))
        {
            re = "(责任编辑：" + Encode.htmlsp(editor) + ")";
        }
        return re;

    }

    public String getEditor_EN()
    {
        String re = "";
        String editor = contentObj.getEditor() == null ? "" : contentObj.getEditor().trim();
        String userName = contentObj.getReleaseBy();

        if (editor.equals("") && userName.equals(""))
        {
            re = "";
        }
        else if (editor.equals("") && !userName.equals(""))
        {
            re = "(Web editor: " + Encode.htmlsp(userName) + ")";
        }
        else if (!editor.equals("") && userName.equals(""))
        {
            re = "(Web editor: " + Encode.htmlsp(editor) + ")";
        }
        else if (!editor.equals("") && !userName.equals(""))
        {
            if (!editor.equals(userName))
            {
                re = "(Web editor: " + Encode.htmlsp(editor) + ", " + Encode.htmlsp(userName)
                     + ")";
            }
            else
            {
                re = "(Web editor: " + Encode.htmlsp(editor) + ")";
            }
        }
        return re;

    }

    public String getEditor_RUSSIA()
    {
        String re = "";
        String editor = contentObj.getEditor() == null ? "" : contentObj.getEditor().trim();
        String userName = contentObj.getReleaseBy();

        if (editor.equals("") && userName.equals(""))
        {
            re = "";
        }
        else if (editor.equals("") && !userName.equals(""))
        {
            re = "(Редактор:" + Encode.htmlsp(userName) + ")";
        }
        else if (!editor.equals("") && userName.equals(""))
        {
            re = "(Редактор:" + Encode.htmlsp(editor) + ")";
        }
        else if (!editor.equals("") && !userName.equals(""))
        {
            if (!editor.equals(userName))
            {
                re = "(Редактор:" + Encode.htmlsp(editor) + "、" + Encode.htmlsp(userName) + ")";
            }
            else
            {
                re = "(Редактор:" + Encode.htmlsp(editor) + ")";
            }
        }
        return re;

    }

    public String getEditor_FRENCH()
    {
        String re = "";
        String editor = contentObj.getEditor() == null ? "" : contentObj.getEditor().trim();
        String userName = contentObj.getReleaseBy();

        if (editor.equals("") && userName.equals(""))
        {
            re = "";
        }
        else if (editor.equals("") && !userName.equals(""))
        {
            re = "(Rédacteurs :" + Encode.htmlsp(userName) + ")";
        }
        else if (!editor.equals("") && userName.equals(""))
        {
            re = "(Rédacteurs :" + Encode.htmlsp(editor) + ")";
        }
        else if (!editor.equals("") && !userName.equals(""))
        {
            if (!editor.equals(userName))
            {
                re = "(Rédacteurs :" + Encode.htmlsp(editor) + ", " + Encode.htmlsp(userName)
                     + ")";
            }
            else
            {
                re = "(Rédacteurs :" + Encode.htmlsp(editor) + ")";
            }
        }
        return re;

    }

    public List<Channel> getSecondChannels()
    {
        Content content = iContentService.getById(contentId);
        Channel channel = iChannelService.getById(content.getChannelid());

        String parentString = channel.getParentString();
        String[] parent_arr = parentString.split(",");
        Integer secondNode = 1;
        if (parent_arr.length >= 2)
        {
            secondNode = Integer.parseInt(parent_arr[1]);
        }
        else
        {
            secondNode = channel.getLogicId();
        }
        Channel channelSecond = iChannelService.getByLogicId(secondNode);
        List<Channel> list = new ArrayList<Channel>();
        list.add(channelSecond);
        return list;
    }

    public String getNavigator()
    {
        String rt = "";
        List ds = getParents_Navigator();
        for (int i = ds.size() - 1; i >= 0; i-- )
        {
            Channel rec = (Channel)ds.get(i);
            if (rec.getStatusRelease() == 0)
            {
                rt += "";
            }
            else
            {
                rt += GetChannelStaticLink(String.valueOf(rec.getLogicId()), rec.getChannelName(),
                    "clink");
            }
            if (i > 0)
            {
                rt += "&gt;&gt;";
            }
        }
        return rt;
    }

    public String getNavigatorNoPeople(String nodelist)
    {
        // 栏目导航，不包括nodelist里的节点，nodelist是节点号+逗号组成，111,222,333,
        String rt = "";
        List ds = getParents_Navigator();;
        for (int i = ds.size() - 1; i >= 0; i-- )
        {
            Channel rec = (Channel)ds.get(i);
            if (nodelist.indexOf(rec.getLogicId() + ",") < 0)
            {
                if (rec.getStatusRelease() == 0)
                    rt += "";
                else
                    rt += GetChannelStaticLink(String.valueOf(rec.getLogicId()),
                        rec.getChannelName(), "clink");
                if (i > 0) rt += "&gt;&gt;";
            }
        }

        return rt;
    }

    public String getGCD_Navigator()
    {
        String rt = "";
        List ds = getParents_Navigator();
        for (int i = ds.size() - 1; i >= 0; i-- )
        {
            Channel rec = (Channel)ds.get(i);
            if (rec.getLogicId() != 64036)
            {
                if (rec.getStatusRelease() == 0)
                    rt += "";
                else
                    rt += GetChannelStaticLink(String.valueOf(rec.getLogicId()),
                        rec.getChannelName(), "clink");
                if (i > 0) rt += "&gt;&gt;";
            }
        }
        return rt;
    }

    public String getBFGY_Navigator()
    {

        String rt = "";
        List ds = getParents_Navigator();
        for (int i = ds.size() - 1; i >= 0; i-- )
        {
            Channel rec = (Channel)ds.get(i);
            if (rec.getStatusRelease() == 0)
                rt += "";
            else if (i == ds.size() - 1)
            {
                rt += GetChannelStaticLink(String.valueOf(rec.getLogicId()), rec.getChannelName(),
                    "nav-title");
            }
            else
            {
                rt += GetChannelStaticLink(String.valueOf(rec.getLogicId()), rec.getChannelName(),
                    "");
            }
            if (i > 0) rt += "&gt;";
        }
        return rt;
    }

    public String getNEW_Navigator()
    {

        String rt = "";
        List ds = getSecondChannels();
        int secondid = ((Channel)ds.get(0)).getLogicId();
        String channelName = ((Channel)ds.get(0)).getChannelName();
        rt += GetChannelStaticLink("1", "人民网", "");
        rt += "&gt;&gt;";
        rt += GetChannelStaticLink(String.valueOf(secondid), channelName, "");
        rt += "&gt;&gt;";
        rt += "<a>正文</a>";
        return rt;
    }

    public String getWIRELESS_Navigator()
    {

        String rt = "";
        List ds = getParents_Navigator();
        for (int i = ds.size() - 1; i >= 0; i-- )
        {
            Channel rec = (Channel)ds.get(i);
            if (rec.getStatusRelease() == 0)
                rt += "";
            else if (i == ds.size() - 1)
            {
                rt += GetChannelLink(rec.getLogicId(), rec.getChannelName(),
                    "nav-title");
            }
            else
            {
                rt += GetChannelLink(rec.getLogicId(), rec.getChannelName(), "");
            }
            if (i > 0) rt += "&gt;";
        }
        return rt;
    }

    public List getParents_Navigator()
    {
        Channel channel = iChannelService.getByLogicId(nodeId);
        List re = new ArrayList();
        if (null != channel)
        {
            String parentnode = channel.getParentString() + nodeId;
            String[] parentnodearr = parentnode.split(",");
            for (int i = parentnodearr.length - 1; i >= 0; i-- )
            {
                String node_id = parentnodearr[i];
                Channel pChannel = iChannelService.getByLogicId(Integer.parseInt(node_id));
                if (pChannel != null && pChannel.getStatusRelease() == 1)
                {
                    re.add(pChannel);
                }
            }
        }
        return re;
    }

    private int getIntValueFromStr(String strVal, int defaultVal)
    {
        try
        {
            return Integer.parseInt(strVal);
        }
        catch (NumberFormatException e)
        {
            return defaultVal;
        }
    }

    public String getAuthor()
    {
        String res = Encode.htmlsp(contentObj.getAuthor());
        return res;
    }

    public String getAuthor_ZN()
    {
        String res = Encode.htmlsp(contentObj.getAuthor());
        if (res != null)
        {
            if (!res.equals(""))
            {
                res = "作者：" + res;
            }
        }
        return res;
    }

    public String getAuthor_CODE()
    {
        return "暂无跨系统查询方案";

        /*
         * String res = contentObj.getAuthor(); String re = ""; if (res != null) { ReadDBTool
         * readDao = (ReadDBTool)SpringContextUtil.getBean("ReadDBTool"); String query =
         * "from User where user_Name = '" + res.trim() + "'"; List list = readDao.find(query); if
         * (list != null && list.size() > 0) { User user = (User)list.get(0); re =
         * String.valueOf(user.getUser_Id()); } } return re;
         */
    }

    public String getAuthorEnglish()
    {
        String res = "";

        if (contentObj.getAuthor() != null && contentObj.getAuthor().length() > 0)
        {
            res = "By " + Encode.htmlsp(contentObj.getAuthor());
        }
        return res;
    }

    // 60周年来源说明
    public String getOrigin60Year()
    {
        String rt = "";
        /*
         * if (originId60Year.contains(contentObj.getOrigin())) { rt =
         * "郑重声明：以上内容为本网“新中国成立60周年”专稿，非“新中国成立60周年”合作伙伴，禁止转载、使用（包括已取得常规新闻授权的网站），违者必究。如需使用，请与010-65368446联系。";
         * }
         */
        return rt;
    }

    // 文本信息保护
    public String getEscapeTitle()
    {
        String re = Escape.escape(Encode.htmlsp(contentObj.getTitle()));
        StringBuffer sb = new StringBuffer();
        sb.append("<script language=\"Javascript\">");
        sb.append("document.write(unescape(\"" + re + "\"))");
        sb.append("</script>");
        return sb.toString();
    }

    public String getEscapePreTitle()
    {
        String re = Escape.escape(Encode.htmlsp(contentObj.getPretitle()));
        StringBuffer sb = new StringBuffer();
        sb.append("<script language=\"Javascript\">");
        sb.append("document.write(unescape(\"" + re + "\"))");
        sb.append("</script>");
        return sb.toString();
    }

    public String getEscapeSubTitle()
    {
        String re = Escape.escape(Encode.htmlsp(contentObj.getSubtitle()));
        StringBuffer sb = new StringBuffer();
        sb.append("<script language=\"Javascript\">");
        sb.append("document.write(unescape(\"" + re + "\"))");
        sb.append("</script>");
        return sb.toString();
    }

    public String getEscapeAuthor()
    {
        String re = Escape.escape(Encode.htmlsp(contentObj.getAuthor()));
        StringBuffer sb = new StringBuffer();
        sb.append("<script language=\"Javascript\">");
        sb.append("document.write(unescape(\"" + re + "\"))");
        sb.append("</script>");
        return sb.toString();
    }

    public String getEscapeContent()
    {
        String re = Escape.escape(Encode.htmlsp(contentObj.getContent()));
        StringBuffer sb = new StringBuffer();
        sb.append("<script language=\"Javascript\">");
        sb.append("document.write(unescape(\"" + re + "\"))");
        sb.append("</script>");
        return sb.toString();
    }

    public String getEscapeEditor()
    {
        String re = Escape.escape(Encode.htmlsp(contentObj.getEditor().trim()));
        StringBuffer sb = new StringBuffer();
        sb.append("<script language=\"Javascript\">");
        sb.append("document.write(unescape(\"" + re + "\"))");
        sb.append("</script>");
        return sb.toString();
    }

    /**
     * 取得完整URL
     * 
     * @return
     */
    public String getFullURL()
    {
        String url = this.contentObj.getStaticUrl();
        return url;
    }

    // 获取点击数
    private String getViewCount(String content_id)
    {
        return "暂不支持";
    }

    public String getRelateNews()
    {
        return "暂不支持";

        /*
         * List ds = getRelateContents(String.valueOf(contentId)); if( ds.size() == 0 ) { return
         * ""; } StringBuffer sBuff = new StringBuffer(); sBuff.append(
         * "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" );
         * sBuff.append(
         * "<tr><td height=\"30\" background=\"/img/newcontent2005/dotline_bg.gif\" class=\"t14l14bl\"><strong>相关新闻：</td></tr></table>"
         * ); sBuff.append(
         * "<table width=\"100%\" board=\"0\" cellspacing=\"2\" cellpadding=\"0\">"); for( int i =
         * 0; i < ds.size(); i++ ) { ContentRelationDto rec = (ContentRelationDto)ds.get(i);
         * sBuff.append( "<tr><td  class=\"t14l14\">· " ); sBuff.append( GetPageLink(
         * String.valueOf(rec.getRelateId()), Encode.htmlsp( rec.getTitle() ), null ) ); if( i <
         * ds.size() -1 ) sBuff.append( "<br>" ); sBuff.append( " "+"<span class=\"t12l14bl\">" );
         * sBuff.append(rec.getInputDate()); sBuff.append(
         * "</span></td></tr><tr><td background=\"/img/newcontent2005/dotline.gif\" class=\"t14l14\"><img src=\"/img/newcontent2005/dotline.gif\" width=\"3\" height=\"3\"></td></tr>"
         * ); } sBuff.append( "</table>" ); return sBuff.toString();
         */
    }

    public String getRelateNewsNew()
    {
        return "暂不支持";
        /*
         * List ds = getRelateContents(String.valueOf(contentId)); if( ds.size() == 0 ){ return "";
         * } StringBuffer sBuff = new StringBuffer(); sBuff.append(
         * "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" );
         * sBuff.append(
         * "<tr><td height=\"30\" background=\"/img/newcontent2005/dotline_bg.gif\" class=\"t14l14bl\"><strong>相关新闻：</td></tr></table>"
         * ); sBuff.append(
         * "<table width=\"100%\" board=\"0\" cellspacing=\"2\" cellpadding=\"0\">"); ReadDBTool
         * readDao = (ReadDBTool) SpringContextUtil.getBean("ReadDBTool"); for( int i = 0; i <
         * ds.size(); i++ ) { ContentRelationDto contentRelationDto =
         * (ContentRelationDto)ds.get(i); sBuff.append( "<tr><td  class=\"t14l14\">· " );
         * sBuff.append(GetPageLink(String.valueOf(contentRelationDto.getRelateId()),Encode.htmlsp(
         * contentRelationDto.getTitle()), "abl")); sBuff.append( " "+"<span class=\"t12l14bl\">"
         * ); sBuff.append( contentRelationDto.getInputDate()); sBuff.append(
         * "</span></td></tr><tr><td background=\"/img/newcontent2005/dotline.gif\" class=\"t14l14\"><img src=\"/img/newcontent2005/dotline.gif\" width=\"3\" height=\"3\"></td></tr>"
         * ); } sBuff.append( "</table>" ); return sBuff.toString();
         */
    }

    public String getRelateNews_2007txt()
    {
        return "暂不支持";
        /*
         * List ds1 = getOwnKeyWord(String.valueOf(contentId)); List ds =
         * getRelateContents_limit(String.valueOf(contentId)); if( ds.size() == 0 ){ return ""; }
         * //if( ds1.getRowCount() >= 0 ){ StringBuffer sBuff = new StringBuffer(); sBuff.
         * append("<table class=\"more_new\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
         * if(ds1!=null&& ds1.size()> 0 ){
         * sBuff.append("<tr><td class=\"more_title\"><span class=\"more_s\">更多关于</span>"); for(
         * int i = 0; i < ds1.size(); i++ ) { Keyword rec = (Keyword)ds1.get(i); sBuff.append(
         * "&nbsp;<a href=\"http://search.people.com.cn/rmw/GB/rmwsearch/gj_search_lj.jsp?keyword="
         * ); sBuff.append( Encode.htmlsp( rec.getKeyword()));
         * sBuff.append("\" target=_blank><em>"); sBuff.append( Encode.htmlsp( rec.getKeyword()));
         * sBuff.append("</em></a>&nbsp;"); }
         * sBuff.append("的新闻</td></tr><tr><td style=\"padding:10px 0;\">"); }else{ sBuff.
         * append("<tr><td class=\"more_title\"><span class=\"more_s\">相关新闻</span></td></tr><tr><td style=\"padding:10px 0;\">"
         * ); } for( int i = 0; i < ds.size(); i++ ) { sBuff.append( "· " ); ContentRelationDto rec
         * = (ContentRelationDto)ds.get(i);
         * sBuff.append(GetPageLink(String.valueOf(rec.getRelateId()),Encode.htmlsp(rec.getTitle())
         * , "null")); if( i < ds.size() -1 ){ sBuff.append( "<br>" ); } } sBuff.append(
         * "</td></tr></table>" ); return sBuff.toString();
         */

    }

    public String getRelateNews_cpc()
    {
        return "暂不支持";
        /*
         * List ds1 = getOwnKeyWord(String.valueOf(contentId)); List ds =
         * getRelateContents_limit(String.valueOf(contentId)); if( ds.size() == 0 ){ return ""; }
         * StringBuffer sBuff = new StringBuffer();
         * sBuff.append("<table width=\"94%\" border=\"0\" cellspacing=\"4\" cellpadding=\"0\">");
         * if(ds1!=null&& ds1.size() > 0 ){
         * sBuff.append("<tr><td class=\"title01\"><span class=\"more_s\">更多关于</span>"); for( int i
         * = 0; i < ds1.size(); i++ ) { Keyword rec = (Keyword)ds1.get(i); sBuff.append(
         * "&nbsp;<a href=\"http://search.people.com.cn/rmw/GB/rmwsearch/gj_search_lj.jsp?keyword="
         * ); sBuff.append( Encode.htmlsp( rec.getKeyword()));
         * sBuff.append("\" target=_blank><span class=acpc>"); sBuff.append( Encode.htmlsp(
         * rec.getKeyword())); sBuff.append("</span></a>&nbsp;"); }
         * sBuff.append("的新闻</td></tr><tr><td class=\"list_01\">"); }else{ sBuff.
         * append("<tr><td class=\"title01\"><span class=\"more_s\">相关新闻</span></td></tr><tr><td class=\"list_01\">"
         * ); } for( int i = 0; i < ds.size(); i++ ) { ContentRelationDto rec =
         * (ContentRelationDto)ds.get(i); sBuff.append( "· " );
         * sBuff.append(GetPageLink(String.valueOf(rec.getRelateId()),Encode.htmlsp(rec.getTitle())
         * , "null")); if( i < ds.size() -1 ) sBuff.append( "<br>" ); } sBuff.append(
         * "</td></tr></table>" ); return sBuff.toString();
         */

    }

    public String getRelateNewsGcd()
    {
        return "暂不支持";
        /*
         * List ds = getRelateContents(String.valueOf(contentId)); if( ds.size() == 0 ) return "";
         * StringBuffer sBuff = new StringBuffer(); sBuff.append(
         * "<table width=\"740\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"); sBuff.append(
         * "<tr><td width=\"23\"><img src=\"/img/2006cpc/2j/zz_a_09.jpg\" width=\"23\"  height=\"23\"></td><td background=\"/img/2006cpc/2j/zz_a_10.jpg\">&nbsp;<strong>相关新闻</strong></td><td width=\"9\"><img src=\"/img/2006cpc/2j/zz_a_11.jpg\"  width=\"8\"  height=\"23\"></td></tr></table>"
         * ); sBuff.append(
         * "<table width=\"738\" board=\"0\" cellspacing=\"0\" cellpadding=\"1\" bgcolor=\"#E0E0E0\"><tr><td align=\"center\" bgcolor=\"#FFFFFF\"><table width=\"99%\" board=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td>"
         * ); for( int i = 0; i < ds.size(); i++ ) { ContentRelationDto rec =
         * (ContentRelationDto)ds.get(i); sBuff.append( "· " );
         * sBuff.append(GetPageLink(String.valueOf(rec.getRelateId()),Encode.htmlsp(rec.getTitle())
         * , "a14blk")); sBuff.append( " "+"<span class=\"t2jy1\">[" ); sBuff.append(
         * rec.getInputDate()); sBuff.append( "]</span><br>" ); } sBuff.append(
         * "</td></tr></table></td></tr></table>" ); return sBuff.toString();
         */
    }

    public String getRelateNewsNodate()
    {
        return "暂不支持";
        /*
         * List ds = getRelateContents(String.valueOf(contentId)); if( ds.size() == 0 ) return "";
         * StringBuffer sBuff = new StringBuffer(); sBuff.append(
         * "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" );
         * sBuff.append(
         * "<tr><td height=\"30\" background=\"/img/newcontent2005/dotline_bg.gif\" class=\"t14l14bl\"><strong>相关新闻：</td></tr></table>"
         * ); sBuff.append(
         * "<table width=\"100%\" board=\"0\" cellspacing=\"2\" cellpadding=\"0\">"); for( int i =
         * 0; i < ds.size(); i++ ) { ContentRelationDto rec = (ContentRelationDto)ds.get(i);
         * sBuff.append( "<tr><td  class=\"t14l14\">· " );
         * sBuff.append(GetPageLink(String.valueOf(rec.getRelateId()),Encode.htmlsp(rec.getTitle())
         * , "abl")); sBuff.append(
         * "</td></tr><tr><td background=\"/img/newcontent2005/dotline.gif\" class=\"t14l14\"><img src=\"/img/newcontent2005/dotline.gif\" width=\"3\" height=\"3\"></td></tr>"
         * ); } sBuff.append( "</table>" ); return sBuff.toString();
         */
    }

    public String getRelateChannels()
    {
        String rt = "";
        // 待定

        List ds = getRelateChannelsContent();

        if (ds.size() > 0)
        {
            rt += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" background=\"/img/3line2n.gif\">"
                  + "<tr><td><img src=\"/img/3line2n.gif\" width=\"6\" height=\"9\"></td></tr></table>"
                  + "<table width=\"90%\" border=\"0\" cellspacing=\"0\" align=\"center\">"
                  + "<tr><td height=\"30\"><img src=\"/img/3featuren.gif\" width=\"60\" height=\"16\"></td></tr><tr><td>";
            for (int i = 0; i < ds.size(); i++ )
            {
                Channel rec = (Channel)ds.get(i);
                rt += "<img src='/img/dot_navy.gif' width='3' height='3' align='absmiddle' vspace='7'> ";
                rt += GetChannelLink(rec.getLogicId(), rec.getChannelName(), null);
                if (i < ds.size() - 1) rt += "<br>";
            }
            rt += "</td></tr></table>";

        }
        return rt;
    }

    public String getRelateChannelsNew()
    {
        String rt = "";

        List ds = getRelateChannelsContent();

        if (ds.size() > 0)
        {
            // rt += "<table width=\"95%\" border=\"0\" cellpadding=\"5\" cellspacing=\"1\"
            // bgcolor=\"#B5B5B5\">" +
            rt += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
                  + "<tr><td height=\"30\" background=\"/img/newcontent2005/dotline_bg.gif\" class=\"t14l14bl\"><strong>相关专题</strong></td></tr></table>"
                  + "<table width=\"100%\" board=\"0\" cellspacing=\"2\" cellpadding=\"0\">";
            for (int i = 0; i < ds.size(); i++ )
            {
                Channel rec = (Channel)ds.get(i);
                rt += "<tr><td class=\"t14l14\">· ";
                rt += GetChannelLink(rec.getLogicId(), rec.getChannelName(),
                    "abl");
                rt += "</td></tr><tr><td background=\"/img/newcontent2005/dotline.gif\" class=\"t14l14\"><img src=\"/img/newcontent2005/dotline.gif\" width=\"3\" height=\"3\"></td></tr>";
            }
            rt += "</table>";

        }

        return rt;
    }

    public String getRelateChannels_2007txt()
    {
        String rt = "";

        List ds = getRelateChannelsContent();

        if (ds.size() > 0)
        {

            rt += "<table class=\"more_new\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">"
                  + "<tr><td class=\"more_title\"><span class=\"more_s\">相关专题</span></td></tr><tr><td style=\"padding:10px 0;\">";

            for (int i = 0; i < ds.size(); i++ )
            {
                Channel rec = (Channel)ds.get(i);
                rt += "· ";
                rt += GetChannelLink(rec.getLogicId(), rec.getChannelName(), null);
                if (i < ds.size() - 1) rt += "<br>";
            }
            rt += "</td></tr></table>";

        }

        return rt;
    }

    public String getRelateChannels_cpc()
    {
        String rt = "";

        List ds = getRelateChannelsContent();

        if (ds.size() > 0)
        {

            rt += "<table width=\"94%\" border=\"0\" cellspacing=\"4\" cellpadding=\"0\">"
                  + "<tr><td class=\"title01\">相关专题</td></tr><tr><td class=\"list_01\">";

            for (int i = 0; i < ds.size(); i++ )
            {
                Channel rec = (Channel)ds.get(i);
                rt += "· ";
                rt += GetChannelLink(rec.getLogicId(), rec.getChannelName(), null);
                if (i < ds.size() - 1) rt += "<br>";
            }
            rt += "</td></tr></table>";

        }
        return rt;
    }

    public String getRelateChannelsGcd()
    {
        String rt = "";
        List ds = getRelateChannelsContent();

        if (ds.size() > 0)
        {
            // rt += "<table width=\"95%\" border=\"0\" cellpadding=\"5\" cellspacing=\"1\"
            // bgcolor=\"#B5B5B5\">" +
            rt += "<table width=\"740\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
                  + "<tr><td width=\"23\"><img src=\"/img/2006cpc/2j/zz_a_09.jpg\" width=\"23\"  height=\"23\"></td><td background=\"/img/2006cpc/2j/zz_a_10.jpg\">&nbsp;<strong>相关专题</strong></td><td width=\"9\"><img src=\"/img/2006cpc/2j/zz_a_11.jpg\"  width=\"8\"  height=\"23\"></td></tr></table>"
                  + "<table width=\"738\" board=\"0\" cellspacing=\"0\" cellpadding=\"1\" bgcolor=\"#E0E0E0\"><tr><td align=\"center\" bgcolor=\"#FFFFFF\"><table width=\"99%\" board=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td>";
            for (int i = 0; i < ds.size(); i++ )
            {
                Channel rec = (Channel)ds.get(i);
                rt += "· ";
                rt += GetChannelLink(rec.getLogicId(), rec.getChannelName(),
                    "a14blk");
                rt += "<br>";

            }
            rt += "</td></tr></table></td></tr></table>";

        }
        return rt;
    }

    // 外文版使用
    public String getRelateNews_Foreign()
    {
        return "暂不支持";
        /*
         * List ds = getRelateContents(String.valueOf(contentId)); StringBuffer sBuff = new
         * StringBuffer(); if(ds!=null){ if( ds.size() == 0 ){ return ""; } sBuff.append(
         * "<table width=\"90%\" border=\"0\" cellspacing=\"0\" align=\"center\">" ); sBuff.append(
         * "<tr><td><ul style='list-style-image:url(/img/dot_navy3.gif);margin-left:8px; padding-left:8px;'>"
         * ); for( int i = 0; i < ds.size(); i++ ) { ContentRelationDto rec =
         * (ContentRelationDto)ds.get(i); sBuff.append( "<li>"+GetPageLink(
         * String.valueOf(rec.getRelateId()), Encode.htmlsp_nospace( rec.getTitle() ), "anavyul"
         * )+"</li>\n" ); } sBuff.append( "</ul></td></tr></table>" ); } return sBuff.toString();
         */
    }

    // 俄文相关专题
    public String getRussia_getRelateChannels()
    {
        String rt = "";
        List ds = getRelateChannelsContent();
        if (ds.size() > 0)
        {
            rt += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" align=\"left\">"
                  + "<tr><td>Спецтема:</td></tr><tr><td>";
            for (int i = 0; i < ds.size(); i++ )
            {
                Channel rec = (Channel)ds.get(i);
                rt += "<img src='/img/dot_navy.gif' width='3' height='3' align='absmiddle' vspace='7'> ";
                rt += GetChannelLink(rec.getLogicId(), rec.getChannelName(), null);
                if (i < ds.size() - 1) rt += "<br>";
            }
            rt += "</td></tr></table>";
        }
        return rt;
    }

    public String qglt_getRelateChannels()
    {
        String rt = "";
        List ds = getRelateChannelsContent();

        if (ds.size() > 0)
        {
            rt += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" background=\"/img/3line2n.gif\">"
                  + "<tr><td><img src=\"/img/3line2n.gif\" width=\"6\" height=\"9\"></td></tr></table>"
                  + "<table width=\"90%\" border=\"0\" cellspacing=\"0\" align=\"center\">"
                  + "<tr><td>";
            for (int i = 0; i < ds.size(); i++ )
            {
                Channel rec = (Channel)ds.get(i);
                rt += "<img src='/img/dot_navy.gif' width='3' height='3' align='absmiddle' vspace='7'> ";
                rt += GetChannelLink(rec.getLogicId(), rec.getChannelName(), null);
                if (i < ds.size() - 1) rt += "<br>";
            }
            rt += "</td></tr></table>";

        }
        return rt;
    }

    /*
     * public List getRelateChannelsContent(){ DBDao dao=(DBDao )
     * SpringContextUtil.getBean("DBDao"); ReadDBTool readDao = (ReadDBTool)
     * SpringContextUtil.getBean("ReadDBTool"); String queryString =
     * "from POJO_ContentChannel where contentId="+contentId; List list = dao.findHQL(queryString,
     * contentId); String parentnodestr = ""; for(int i=0;i<list.size();i++){ POJO_ContentChannel
     * pojo_ContentChannel = (POJO_ContentChannel)list.get(i); int node_id =
     * pojo_ContentChannel.getNodeId(); // queryString = "from Channel where nodeId="+node_id; //
     * List list1 = readDao.find(queryString); //使用getById利用二级缓存-王弘鹏 Channel
     * channel=(Channel)readDao.getById(Channel.class, Integer.valueOf(node_id));
     * //if(list1.size()>0){ if(null!=channel){ if(i==0){ //parentnodestr =
     * ((Channel)list1.get(0)).getParentString(); parentnodestr=channel.getParentString(); }else{
     * //parentnodestr = parentnodestr+","+((Channel)list1.get(0)).getParentString(); parentnodestr
     * = parentnodestr+channel.getParentString(); } } }
     * if(parentnodestr!=null&&parentnodestr.endsWith(",")){
     * parentnodestr=parentnodestr.substring(0, parentnodestr.length()-1); } queryString =
     * "from Channel where nodeId in("+parentnodestr+")"; List re = readDao.find(queryString);
     * return re; }
     */
    public List getRelateChannelsContent()
    {

        List<ContentChannel> list = iContentChannelService.getByContentId(contentId);

        String parentnodestr = "";
        List re = new ArrayList();
        HashMap hs = new HashMap();
        for (int i = 0; i < list.size(); i++ )
        {
            ContentChannel pojo_ContentChannel = list.get(i);
            String channelId = pojo_ContentChannel.getChannelid();
            Channel channel = iChannelService.getById(channelId);
            if (null != channel)
            {
                String parent_string = channel.getParentString() + channel.getLogicId();
                String[] parentnodearr = parent_string.split(",");
                if (parentnodearr.length > 0)
                {
                    for (int j = 0; j < parentnodearr.length; j++ )
                    {
                        String node_id = parentnodearr[j];
                        Channel pChannel = iChannelService.getByLogicId(Integer.parseInt(node_id));
                        // 栏目类型是专题并且有模板
                        if (pChannel != null && pChannel.getDtype() == 2
                            && !StringUtils.isEmpty(pChannel.getChannelModelId()))
                        {
                            if (!hs.containsKey(pChannel.getLogicId()))
                            {
                                hs.put(pChannel.getLogicId(), pChannel.getParentId());
                            }
                        }
                    }
                }
            }
        }

        Iterator iter = hs.entrySet().iterator();
        while (iter.hasNext())
        {
            Map.Entry entry = (Map.Entry)iter.next();
            Integer key = (Integer)entry.getKey();
            Object val = entry.getValue();
            if (!hs.containsKey(val))
            {
                Channel channel = iChannelService.getByLogicId(key);
                re.add(channel);
            }
        }

        return re;
    }

    public String getFuncList()
    {
        String rt = "";
        rt += "<table width='90%' border='0' cellspacing='0' cellpadding='0' align='center'><tr>"
              + "<td><a href='#' onClick='if (window.print) window.print();return false'><img src='/img/3print.gif' border='0'></a></td>"
              + "<td><a href='" + this.getBBSLink()
              + "'><img src='/img/3sent.gif' width='96' height='25' border='0'></a></td>"
              + "<!--td><a href='#'><img src='/img/3email.gif' width='150' height='25' border='0'></a></td-->"
              + "<!--td><img src='/img/3fore.gif' width='61' height='25'></td-->"
              + "<!--td><select name='select'><option selected>选择语言</option><option>英语</option></select--></td-->"
              + "</tr></table>";
        // 计数器图片

        if (SYSTEM_NAME.equals("MAIN"))
        {
            rt += "<img src=\"" + COUNTER_ADDRESS_MAIN + contentId
                  + "\" width=0 height=0 style=\"display:none;\">";
        }
        else if (SYSTEM_NAME.equals("LOCAL"))
        {
            rt += "<img src=\"" + COUNTER_ADDRESS_LOCAL + contentId
                  + "\" width=0 height=0 style=\"display:none;\">";
        }
        else
        {
            rt += "<img src=\"" + COUNTER_ADDRESS_MAIN + contentId
                  + "\" width=0 height=0 style=\"display:none;\">";
        }
        return rt;
    }

    public String getBBSLink()
    {
        // 待定
        String res = "http://" + PHPServer_out + "/bbs_new/app/src/main/?action=list&uid="
                     + contentObj.getNodeId();

        return res;
    }

    public String getPublishTime()
    {
        String rt = "";
        // 普通新闻

        Date releaseDate = contentObj.getDisplayDate();
        if (releaseDate == null)
        {
            releaseDate = contentObj.getInputDate();
        }
        if (releaseDate == null) releaseDate = new Date(System.currentTimeMillis());
        rt = DateUtil.Date2Str(releaseDate, "yyyy年MM月dd日HH:mm");

        return rt;
    }

    public String getPublishTime_temp()
    {
        String rt = "";
        // 普通新闻
        Date releaseDate = contentObj.getDisplayDate();
        if (releaseDate == null)
        {
            releaseDate = contentObj.getInputDate();
        }
        if (releaseDate == null) releaseDate = new Date(System.currentTimeMillis());
        rt = DateUtil.Date2Str(releaseDate, "yyyy年MM月dd日");
        return rt;
    }

    public String getPublishTime_simple()
    {
        String rt = "";
        Date releaseDate = contentObj.getDisplayDate();
        if (releaseDate == null)
        {
            releaseDate = contentObj.getInputDate();
            if (releaseDate == null)
            {
                releaseDate = new Date(System.currentTimeMillis());
            }
        }
        rt = DateUtil.Date2Str(releaseDate, "yyyy-MM-dd");
        return rt;
    }

    public String getAuthorNew()
    {
        String res = "<span class=\"author_new\">" + Encode.htmlsp_nospace(contentObj.getAuthor())
                     + "</span>";
        return res;
    }

    public String getOrigin1()
    {
        String rt = "";;
        String name = contentObj.getOriginName();
        String url = contentObj.getOriginUrl();

        if (url.contains("haiwainet.cn"))
        {
            rt = "来源：" + "<a href=\"" + url + "\" target=\"_blank\">" + name + "</a>";
        }
        else
        {
            rt = "来源：" + "<a href=\"" + url + "\" target=\"_blank\" rel=\"nofollow\">" + name
                 + "</a>";
        }
        return rt;
    }

    private HashMap getOriginDataSet1(String content_id)
    {
        ContentDetailVO content = iContentService.getContentById(content_id);
        HashMap hs = new HashMap();
        if (content != null)
        {
            hs.put("name", content.getSourcename() == null ? "" : content.getSourcename());
            hs.put("url", content.getSourceurl() == null ? "" : content.getSourceurl());
        }
        return hs;
    }

    public String getOriginName1()
    {
        String rt = "";
        String name = contentObj.getOriginName();
        if (name != null && name.length() > 0)
        {
            rt = "来源：" + name;
        }
        // TODO 原创稿件
        /*
         * ReadDBTool readDao = (ReadDBTool)SpringContextUtil.getBean("ReadDBTool"); String
         * queryString = "from OriginalContent where contentId =" + contentId; List list =
         * readDao.find(queryString); if (list != null && list.size() > 0 && rt.length() > 0) { rt
         * = rt + " 原创稿"; }
         */

        Integer language_id = contentObj.getLanguageId();

        if (language_id == 2)
        {
            rt = rt.replace("来源：", "source：");
            rt = rt.replace("原创稿", "Original");
        }
        if (language_id == 3)
        {
            rt = rt.replace("来源：", "Source:");
            rt = rt.replace("原创稿", "Original");
        }
        if (language_id == 4)
        {
            rt = rt.replace("来源：", "Источник：");
            rt = rt.replace("原创稿", "Эксклюзив");
        }
        if (language_id == 5)
        {
            rt = rt.replace("来源：", "Fuente：");
            rt = rt.replace("原创稿", "Originales");
        }
        if (language_id == 6)
        {
            rt = rt.replace("来源：", "مصدر：");
            rt = rt.replace("原创稿", "خاص");
        }
        if (language_id == 100)
        {
            rt = rt.replace("来源：", "Fonte：");
            rt = rt.replace("原创稿", "Originais");
        }
        if (language_id == 29)
        {
            rt = rt.replace("来源：", "ソース：");
            rt = rt.replace("原创稿", "オリジナル");
        }
        if (language_id == 80)
        {
            rt = rt.replace("来源：", "Quelle：");
            rt = rt.replace("原创稿", "Original");
        }
        if (language_id == 61)
        {
            rt = rt.replace("来源：", "Quelle：");
            rt = rt.replace("原创稿", "직접취재 뉴스");
        }

        rt = rt.replace("<可靠>", "");
        return rt;

    }

    public String getOriginName()
    {
        return contentObj.getOriginName();
    }

    public String getBBSLink_list()
    {
        String res = "";

        return res;
    }

    public String getCounter()
    {
        if (SYSTEM_NAME.equals("MAIN"))
        {
            return "<img src=\"" + COUNTER_ADDRESS_MAIN + contentObj.getContentId()
                   + "\" width=0 height=0 style=\"display:none;\">";
        }
        else if (SYSTEM_NAME.equals("LOCAL"))
        {
            return "<img src=\"" + COUNTER_ADDRESS_LOCAL + contentObj.getContentId()
                   + "\" width=0 height=0 style=\"display:none;\">";
        }
        return "<img src=\"" + COUNTER_ADDRESS_MAIN + contentObj.getContentId()
               + "\" width=0 height=0 style=\"display:none;\">";
    }

    public String getCounterHaiWai()
    {
        if (SYSTEM_NAME.equals("LOCAL"))
        {
            return "<img src=\"" + COUNTER_ADDRESS_HAIWAI + contentObj.getContentId()
                   + "\" width=0 height=0 style=\"display:none;\">";
        }
        return "<img src=\"" + COUNTER_ADDRESS_MAIN + contentObj.getContentId()
               + "\" width=0 height=0 style=\"display:none;\">";
    }

    /**
     * 获得上一条新闻 * * @return 内容
     */
    public String getContentlast()
    {

        // 待定
        StringBuffer res = new StringBuffer();
//	       DataSet lastnews = this.getContent().getContentlast();
//	       if( lastnews.getRowCount() > 0 ) {	   
//	           res.append( GetPageLink(lastnews.getRecord(0).getColumn("content_id").asString(), Encode.htmlsp( lastnews.getRecord(0).getColumn("title").asString() ),null) );
//	       }
        return res.toString();
    }

    /****
     * 获得下一条新闻
     * 
     * @return 内容
     ****/
    public String getContentnext()
    {
        // 待定
        StringBuffer res = new StringBuffer();
//        DataSet nextnews = this.getContent().getContentnext();
//        if( nextnews.getRowCount() > 0 ) {
//            //res.append("<img src=\"" + lastnews.getRecord(0).getColumn("PATH").asString() + "\">");
//            //res.append("上一条新闻:<a href=\""+ lastnews.getRecord(0).getColumn("PATH").asString() + "\">");
//            res.append( GetPageLink(nextnews.getRecord(0).getColumn("content_id").asString(), Encode.htmlsp( nextnews.getRecord(0).getColumn("title").asString() ),null) );
//        }
        return res.toString();
    }

    public String getFirstPictureNew()
    {
        StringBuffer res = new StringBuffer();
        Integer page_type = contentObj.getPageType();
        if (page_type != null)
        {
            int pageType = contentObj.getPageType();
            int page = contentObj.getPage();
            int pageCount = contentObj.getPageCount();
            Integer contentId = contentObj.getContentLogicId();
            if (pageType == 2 || pageType == 21)
            {
                ContentMedia contentMedia = contentObj.getPageMedia();
                String desc = HTML2TextNew.convertAlt(Encode.htmlsp(contentMedia.getMediaDesc()));
                if (desc.length() == 0)
                {
                    desc = HTML2TextNew.convertAlt(getTitle());
                }
                String path = contentMedia.getPath();
                if (contentMedia != null)
                {
                    res.append("<div class=\"page_pic\"><div class=\"pic\">");
                    res.append("<img src=\"" + contentMedia.getPath() + "\" border=\"0\" alt=\""
                               + desc + "\" ></a>");
                    res.append("<div class=\"pic_txt\"></div></div>");
                    if (page > 1)
                    {
                        int prePage = contentObj.getPage() - 1;
                        res.append("<div class=\"left_btn\"><a href=\""
                                   + sharedConstant.getContentViewUrl(contentId) + "?page=" + prePage + "\"></a></div>");
                    }
                    else
                    {
                        res.append("<div class=\"left_btn\"><a href=\"#\"></a></div>");
                    }
                    if (page < pageCount)
                    {
                        int nextPage = contentObj.getPage() + 1;
                        res.append(" <div class=\"right_btn\"><a href=\""
                                   + sharedConstant.getContentViewUrl(contentId) + "?page=" + nextPage + "\"></a></div></div>");
                    }
                    else
                    {
                        res.append(" <div class=\"right_btn\"><a href=\"#\"></a></div></div>");
                    }
                }
            }
            else
            {
                List newsImages = getContentImages(String.valueOf(contentId));
                if (newsImages.size() > 0)
                {
                    String desc = HTML2TextNew.convertAlt(
                        Encode.htmlsp(((ContentMedia)newsImages.get(0)).getMediaDesc()));
                    if (desc.length() == 0)
                    {
                        desc = HTML2TextNew.convertAlt(getTitle());
                    }
                    res.append("<div class=\"page_pic\"><div class=\"pic\">");
                    res.append("<img src=\"" + ((ContentMedia)(newsImages.get(0))).getPath()
                               + "\" border=\"0\" alt=\"" + desc + "\" ></a>");
                    res.append("<div class=\"pic_txt\"></div></div>");
                    res.append("<div class=\"left_btn\"><a href=\"#1\"></a></div>");
                    res.append(" <div class=\"right_btn\"><a href=\"#2\"></a></div></div>");
                }
            }
        }
        else
        {
            List newsImages = getContentImages(String.valueOf(contentId));
            if (newsImages.size() > 0)
            {
                String desc = HTML2TextNew.convertAlt(
                    Encode.htmlsp(((ContentMedia)newsImages.get(0)).getMediaDesc()));
                if (desc.length() == 0)
                {
                    desc = HTML2TextNew.convertAlt(getTitle());
                }
                res.append("<div class=\"page_pic\"><div class=\"pic\">");
                res.append("<img src=\"" + ((ContentMedia)(newsImages.get(0))).getPath()
                           + "\" border=\"0\" alt=\"" + desc + "\" ></a>");
                res.append("<div class=\"pic_txt\"></div></div>");
                res.append("<div class=\"left_btn\"><a href=\"#1\"></a></div>");
                res.append(" <div class=\"right_btn\"><a href=\"#2\"></a></div></div>");
            }
        }

        return res.toString();

    }

    public String getFirstPicture()
    {
        StringBuffer res = new StringBuffer();
        Integer page_type = contentObj.getPageType();
        if (page_type != null)
        {
            int pageType = contentObj.getPageType();
            int page = contentObj.getPage();
            int pageCount = contentObj.getPageCount();
            Integer contentId = contentObj.getContentLogicId();
            if (pageType == 2 || pageType == 21)
            {
                ContentMedia contentMedia = contentObj.getPageMedia();
                String desc = HTML2TextNew.convertAlt(Encode.htmlsp(contentMedia.getMediaDesc()));
                if (desc.length() == 0)
                {
                    desc = HTML2TextNew.convertAlt(getTitle());
                }
                String path = contentMedia.getPath();
                if (contentMedia != null)
                {
                    res.append(
                        "<table width='450' cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                    if (page < pageCount)
                    {
                        int nextPage = contentObj.getPage() + 1;
                        res.append("<a href=\"=" + sharedConstant.getContentViewUrl(contentId) + "?page=" + nextPage
                                   + "\"><img src=\"" + contentMedia.getPath() + "\" alt=\"" + desc
                                   + "\" ></a>");
                    }
                    else
                    {
                        res.append("<img src=\"" + path + "\" alt=\"" + desc + "\" >");
                    }

                    res.append("</td></tr><tr><td align=center><font color='#000000'>"
                               + Encode.htmlsp(contentMedia.getMediaDesc())
                               + "</font></td></tr></table>");
                }
            }
            else
            {
                List newsImages = getContentImages(String.valueOf(contentId));
                if (newsImages.size() > 0)
                {
                    String desc = HTML2TextNew.convertAlt(
                        Encode.htmlsp(((ContentMedia)newsImages.get(0)).getMediaDesc()));
                    if (desc.length() == 0)
                    {
                        desc = HTML2TextNew.convertAlt(getTitle());
                    }
                    res.append(
                        "<table width='450' cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                    if (page < pageCount)
                    {
                        int nextPage = contentObj.getPage() + 1;
                        res.append("<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page=" + nextPage
                                   + "\"><img src=\""
                                   + ((ContentMedia)(newsImages.get(0))).getPath() + "\" alt=\""
                                   + desc + "\" ></a>");
                    }
                    else
                    {
                        res.append("<img src=\"" + ((ContentMedia)(newsImages.get(0))).getPath()
                                   + "\" alt=\"" + desc + "\" >");
                    }

                    res.append("</td></tr><tr><td align=center><font color='#000000'>"
                               + Encode.htmlsp(((ContentMedia)(newsImages.get(0))).getMediaDesc())
                               + "</font></td></tr></table>");
                }
            }
        }
        else
        {
            List newsImages = getContentImages(String.valueOf(contentId));
            if (newsImages.size() > 0)
            {
                String desc = HTML2TextNew.convertAlt(
                    Encode.htmlsp(((ContentMedia)newsImages.get(0)).getMediaDesc()));
                if (desc.length() == 0)
                {
                    desc = HTML2TextNew.convertAlt(getTitle());
                }
                res.append(
                    "<table width='450' cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");

                res.append("<img src=\"" + ((ContentMedia)(newsImages.get(0))).getPath()
                           + "\" alt=\"" + desc + "\" >");

                res.append("</td></tr><tr><td align=center><font color='#000000'>"
                           + Encode.htmlsp(((ContentMedia)(newsImages.get(0))).getMediaDesc())
                           + "</font></td></tr></table>");
            }
        }

        return res.toString();

    }

    public String getFirstPicture_New()
    {
        StringBuffer res = new StringBuffer();
        Integer page_type = contentObj.getPageType();
        if (page_type != null)
        {
            int pageType = contentObj.getPageType();
            int page = contentObj.getPage();
            int pageCount = contentObj.getPageCount();
            Integer contentId = contentObj.getContentLogicId();
            if (pageType == 2 || pageType == 21)
            {
                ContentMedia contentMedia = contentObj.getPageMedia();
                String desc = HTML2TextNew.convertAlt(Encode.htmlsp(contentMedia.getMediaDesc()));
                if (desc.length() == 0)
                {
                    desc = HTML2TextNew.convertAlt(getTitle());
                }
                String path = contentMedia.getPath();
                if (contentMedia != null)
                {
                    res.append(
                        "<table cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                    if (page < pageCount)
                    {
                        int nextPage = contentObj.getPage() + 1;
                        res.append("<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page=" + nextPage
                                   + "\"><img src=\"" + contentMedia.getPath() + "\" alt=\"" + desc
                                   + "\" ></a>");
                    }
                    else
                    {
                        res.append("<img src=\"" + path + "\" alt=\"" + desc + "\" >");
                    }

                    res.append("</td></tr><tr><td align=center><font color='#000000'>"
                               + Encode.htmlsp(contentMedia.getMediaDesc())
                               + "</font></td></tr></table>");
                }
            }
            else
            {
                List newsImages = getContentImages(String.valueOf(contentId));
                if (newsImages.size() > 0)
                {
                    String desc = HTML2TextNew.convertAlt(
                        Encode.htmlsp(((ContentMedia)newsImages.get(0)).getMediaDesc()));
                    if (desc.length() == 0)
                    {
                        desc = HTML2TextNew.convertAlt(getTitle());
                    }
                    res.append(
                        "<table cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                    if (page < pageCount)
                    {
                        int nextPage = contentObj.getPage() + 1;
                        res.append("<a href=\"" + sharedConstant.getContentViewUrl(contentId) + "?page=" + nextPage
                                   + "\"><img src=\""
                                   + ((ContentMedia)(newsImages.get(0))).getPath() + "\" alt=\""
                                   + desc + "\" ></a>");
                    }
                    else
                    {
                        res.append("<img src=\"" + ((ContentMedia)(newsImages.get(0))).getPath()
                                   + "\" alt=\"" + desc + "\" >");
                    }

                    res.append("</td></tr><tr><td align=center><font color='#000000'>"
                               + Encode.htmlsp(((ContentMedia)(newsImages.get(0))).getMediaDesc())
                               + "</font></td></tr></table>");
                }
            }
        }
        else
        {
            List newsImages = getContentImages(String.valueOf(contentId));
            if (newsImages.size() > 0)
            {
                String desc = HTML2TextNew.convertAlt(
                    Encode.htmlsp(((ContentMedia)newsImages.get(0)).getMediaDesc()));
                if (desc.length() == 0)
                {
                    desc = HTML2TextNew.convertAlt(getTitle());
                }
                res.append(
                    "<table cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");

                res.append("<img src=\"" + ((ContentMedia)(newsImages.get(0))).getPath()
                           + "\" alt=\"" + desc + "\" >");

                res.append("</td></tr><tr><td align=center><font color='#000000'>"
                           + Encode.htmlsp(((ContentMedia)(newsImages.get(0))).getMediaDesc())
                           + "</font></td></tr></table>");
            }
        }

        return res.toString();

    }

    public String getPicture_Page()
    {
        StringBuffer res = new StringBuffer();
        Integer page_type = contentObj.getPageType();
        if (page_type != null)
        {
            int pageType = contentObj.getPageType();
            if (pageType == 2 || pageType == 21)
            {
                ContentMedia contentMedia = contentObj.getPageMedia();
                String desc = HTML2TextNew.convertAlt(Encode.htmlsp(contentMedia.getMediaDesc()));
                if (desc.length() == 0)
                {
                    desc = HTML2TextNew.convertAlt(getTitle());
                }
                String path = contentMedia.getPath();
                if (contentMedia != null)
                {
                    res.append(
                        "<table width='450' cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");

                    res.append("<img src=\"" + path + "\" alt=\"" + desc + "\" >");

                    res.append("</td></tr><tr><td align=center><font color='#000000'>"
                               + Encode.htmlsp(desc) + "</font></td></tr></table>");
                }
            }
        }
        return res.toString();
    }

    public String getFirstPictureNotext()
    {
        StringBuffer res = new StringBuffer();
        List newsImages = getContentImages(String.valueOf(contentId));
        Integer pageType = contentObj.getPageType();
        int pageCount = contentObj.getPageCount();
        int page = contentObj.getPage();
        if (newsImages.size() > 0)
        {

            ContentMedia pojo_ContentMedia = (ContentMedia)newsImages.get(0);
            if (pageType != null)
            {
                if (pageType == 21 || pageType == 2)
                {
                    pojo_ContentMedia = (ContentMedia)newsImages.get(page - 1);
                }
            }
            String desc1 = pojo_ContentMedia.getMediaDesc() == null ? "" : pojo_ContentMedia.getMediaDesc();
            String path = pojo_ContentMedia.getPath();
            String desc = HTML2TextNew.convertAlt(Encode.htmlsp(desc1));
            if (desc.length() == 0)
            {
                desc = HTML2TextNew.convertAlt(getTitle());
            }
            if (page < pageCount)
            {
                int nextPage = contentObj.getPage() + 1;
                res.append("<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page=" + nextPage
                           + "\"><img src=\"" + path + "\" alt=\"" + desc + "\" ></a>");
            }
            else
            {
                res.append("<img src=\"" + path + "\" alt=\"" + desc + "\" >");
            }
        }
        return res.toString();

    }

    // 分页新闻图片点击下一页
    public String getFirstPictureNext()
    {
        StringBuffer res = new StringBuffer();
        List newsImages = getContentImages(String.valueOf(contentId));
        if (newsImages.size() > 0)
        {
            ContentMedia pojo_ContentMedia = (ContentMedia)newsImages.get(0);
            String desc1 = pojo_ContentMedia.getMediaDesc();
            String path = pojo_ContentMedia.getPath();

            String desc = HTML2TextNew.convertAlt(Encode.htmlsp(desc1));
            if (desc.length() == 0)
            {
                desc = HTML2TextNew.convertAlt(getTitle());
            }
            res.append("<img src=\"" + path + "\" alt=\"" + desc + "\" >");
            res.append(
                "<br><center><font color='#000000'>" + Encode.htmlsp(desc) + "</font></center>");
        }
        return res.toString();
    }

    public List<ContentMedia> getContentImages(String content_id)
    {
        int pageNo = contentObj.getPage();
        List<ContentMedia> contentMedialist = new ArrayList<ContentMedia>();

        ContentMediaRedis redis = new ContentMediaRedis();
        QueryWrapper<ContentMedia> queryWrapper = new QueryWrapper<ContentMedia>();
        queryWrapper.eq("content_id", content_id);
        queryWrapper.ne("uploadtype", 1);
        queryWrapper.orderByAsc("level", "id");

        if (SharedConstant.REDIS)
        {
            if (pageNo > 1)
            {
                contentMedialist = redis.getRedisContentMediaList(content_id);
                if (contentMedialist == null)
                {
                    contentMedialist = iContentMediaService.list(queryWrapper);
                    redis.setRedisContentMediaList(content_id, contentMedialist);
                }
            }
            else
            {
                contentMedialist = iContentMediaService.list(queryWrapper);
                redis.setRedisContentMediaList(content_id, contentMedialist);
            }
        }
        else
        {
            contentMedialist = iContentMediaService.list(queryWrapper);
        }

        return contentMedialist;
    }

    public List getContentImagesNew(String content_id)
    {
        String queryString = "from POJO_ContentMedia where contentId=" + content_id
                             + " and level>=0 ORDER BY level,ID";
        int pageNo = contentObj.getPage();
        List<ContentMedia> list = new ArrayList<ContentMedia>();

        QueryWrapper<ContentMedia> queryWrapper = new QueryWrapper<ContentMedia>();
        queryWrapper.eq("content_id", content_id);
        queryWrapper.ge("level", 0);
        queryWrapper.orderByAsc("level", "id");

        ContentMediaRedis redis = new ContentMediaRedis();
        String strContentId = "M_" + content_id;
        if (SharedConstant.REDIS)
        {
            if (pageNo > 1)
            {
                list = redis.getRedisContentMediaList(strContentId);
                if (list == null)
                {
                    list = iContentMediaService.list(queryWrapper);
                    redis.setRedisContentMediaList(strContentId, list);
                }
            }
            else
            {
                list = iContentMediaService.list(queryWrapper);
                redis.setRedisContentMediaList(strContentId, list);
            }
        }
        else
        {
            list = iContentMediaService.list(queryWrapper);
        }

        return list;
    }

    // 强国论坛使用
    public String qglt_getFirstPicture()
    {
        StringBuffer res = new StringBuffer();
        List newsImages = getContentImages(String.valueOf(contentId));
        if (newsImages.size() > 0)
        {
            ContentMedia pojo_ContentMedia = (ContentMedia)newsImages.get(0);
            String desc1 = pojo_ContentMedia.getMediaDesc();
            String path = pojo_ContentMedia.getPath();
            res.append("<img src=\"" + path + "\">");
        }
        return res.toString();
    }

    /**
     * 获得其他图片
     * 
     * @return 图片
     */
    public String getOtherPicture()
    {
        StringBuffer res = new StringBuffer();

        Integer page_type = contentObj.getPageType();
        if (page_type != null)
        {
            int pageType = contentObj.getPageType();
            int page = contentObj.getPage();
            int pageCount = contentObj.getPageCount();

            if (pageType == 2 || pageType == 21)
            {
                res.append("");
            }
            else
            {
                List newsImages = getContentImages(String.valueOf(contentId));
                if (newsImages.size() > 1)
                {
                    for (int i = 1; i < newsImages.size(); i++ )
                    {

                        ContentMedia pojo_ContentMedia = (ContentMedia)newsImages.get(i);
                        String desc1 = pojo_ContentMedia.getMediaDesc();
                        String path = pojo_ContentMedia.getPath();

                        String desc = HTML2TextNew.convertAlt(Encode.htmlsp(desc1));
                        if (desc.length() == 0)
                        {
                            // desc=HTML2TextNew.convertAlt(getTitle());
                        }
                        res.append(
                            "<table width='450' cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                        if (page < pageCount)
                        {
                            int nextPage = contentObj.getPage() + 1;
                            res.append("<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page="
                                       + nextPage + "\"><img src=\"" + path + "\" alt=\"" + desc
                                       + "\" ></a>");
                        }
                        else
                        {
                            res.append("<img src=\"" + path + "\" alt=\"" + desc + "\" >");
                        }

                        res.append("</td></tr><tr><td align=center><font color='#000000'>"
                                   + Encode.htmlsp(desc) + "</font></td></tr></table>");
                    }
                }
            }
        }
        else
        {
            List newsImages = getContentImages(String.valueOf(contentId));
            if (newsImages.size() > 1)
            {

                for (int i = 1; i < newsImages.size(); i++ )
                {

                    ContentMedia pojo_ContentMedia = (ContentMedia)newsImages.get(i);
                    String desc1 = pojo_ContentMedia.getMediaDesc();
                    String path = pojo_ContentMedia.getPath();

                    String desc = HTML2TextNew.convertAlt(Encode.htmlsp(desc1));
                    if (desc.length() == 0)
                    {
                        // desc=HTML2TextNew.convertAlt(getTitle());
                    }
                    res.append(
                        "<table width='450' cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                    res.append("<img src=\"" + path + "\" alt=\"" + desc + "\" >");
                    res.append("</td></tr><tr><td align=center><font color='#000000'>"
                               + Encode.htmlsp(desc) + "</font></td></tr></table>");
                }
            }
        }

        return res.toString();
    }

    /**
     * 获得其他图片
     * 
     * @return 图片
     */
    public String getOtherPicture_New()
    {
        StringBuffer res = new StringBuffer();

        Integer page_type = contentObj.getPageType();
        if (page_type != null)
        {
            int pageType = contentObj.getPageType();
            int page = contentObj.getPage();
            int pageCount = contentObj.getPageCount();

            if (pageType == 2 || pageType == 21)
            {
                res.append("");
            }
            else
            {
                List newsImages = getContentImages(String.valueOf(contentId));
                if (newsImages.size() > 1)
                {
                    for (int i = 1; i < newsImages.size(); i++ )
                    {

                        ContentMedia pojo_ContentMedia = (ContentMedia)newsImages.get(i);
                        String desc1 = pojo_ContentMedia.getMediaDesc();
                        String path = pojo_ContentMedia.getPath();

                        String desc = HTML2TextNew.convertAlt(Encode.htmlsp(desc1));
                        if (desc.length() == 0)
                        {
                            // desc=HTML2TextNew.convertAlt(getTitle());
                        }
                        res.append(
                            "<table cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                        if (page < pageCount)
                        {
                            int nextPage = contentObj.getPage() + 1;
                            res.append("<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page="
                                       + nextPage + "\"><img src=\"" + path + "\" alt=\"" + desc
                                       + "\" ></a>");
                        }
                        else
                        {
                            res.append("<img src=\"" + path + "\" alt=\"" + desc + "\" >");
                        }

                        res.append("</td></tr><tr><td align=center><font color='#000000'>"
                                   + Encode.htmlsp(desc) + "</font></td></tr></table>");
                    }
                }
            }
        }
        else
        {
            List newsImages = getContentImages(String.valueOf(contentId));
            if (newsImages.size() > 1)
            {

                for (int i = 1; i < newsImages.size(); i++ )
                {

                    ContentMedia pojo_ContentMedia = (ContentMedia)newsImages.get(i);
                    String desc1 = pojo_ContentMedia.getMediaDesc();
                    String path = pojo_ContentMedia.getPath();

                    String desc = HTML2TextNew.convertAlt(Encode.htmlsp(desc1));
                    if (desc.length() == 0)
                    {
                        // desc=HTML2TextNew.convertAlt(getTitle());
                    }
                    res.append(
                        "<table cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                    res.append("<img src=\"" + path + "\" alt=\"" + desc + "\" >");
                    res.append("</td></tr><tr><td align=center><font color='#000000'>"
                               + Encode.htmlsp(desc) + "</font></td></tr></table>");
                }
            }
        }

        return res.toString();
    }

    public String getAllPicture()
    {
        StringBuffer res = new StringBuffer();
        List newsImages = getContentImages(String.valueOf(contentId));
        for (int i = 0; i < newsImages.size(); i++ )
        {
            ContentMedia pojo_ContentMedia = (ContentMedia)newsImages.get(i);
            String desc1 = pojo_ContentMedia.getMediaDesc();
            String path = pojo_ContentMedia.getPath();

            String desc = HTML2TextNew.convertAlt(Encode.htmlsp(desc1));
            if (desc.length() == 0)
            {
                desc = HTML2TextNew.convertAlt(getTitle());
            }
            res.append("<img src=\"" + path + "\" width=150 alt=\"" + desc + "\" ><br><br>"
                       + Encode.htmlsp(desc) + "<br><br>");
        }
        return res.toString();
    }

    /**
     * 获得相关新闻的内容，目前主要是为视点服务
     * 
     * @return 内容
     */
    public String getRelateNews_body()
    {
        return "暂不支持";
        /*
         * List ds = getRelateContents(String.valueOf(contentId)); if( ds.size() == 0 ) return "";
         * StringBuffer sBuff = new StringBuffer(); for( int i = 0; i < ds.size(); i++ ) {
         * ContentRelationDto rec = (ContentRelationDto)ds.get(i); int relateid =
         * rec.getRelateId(); String title = rec.getTitle(); sBuff.append(
         * "<img src='/img/dot_navy.gif' width='3' height='3' align='absmiddle' vspace='7'> " );
         * sBuff.append( GetPageLink(String.valueOf(relateid), Encode.htmlsp(title),null) ); if( i
         * < ds.size() -1 ) sBuff.append( "<br>" ); } return sBuff.toString();
         */
    }

    public String getBoardAction()
    {
        // 待定
        return "";
        // return "http://" + Config.getParam("PHPServer_out") + "/bbs_add.php";
    }

    public String getQGLTLink()
    {
        String res = "";
        List ds = getSecondChannels();
        if (ds.size() > 0)
        {
            int channelid = ((Channel)ds.get(0)).getLogicId();
            String forumURL = "";
            switch (channelid)
            {
                case 14717:
                    // 地方14717 -> 83
                    forumURL = "mlbrd?to=83";
                    break;
                case 14738:
                    // 书画 14738 -> 13
                    forumURL = "chbrd?to=13";
                    break;
                case 14739:
                    // 健康 14739 -> 60
                    forumURL = "mlbrd?to=60";
                    break;
                case 14657:
                    // 海峡两岸 14657 -> 92
                    forumURL = "chbrd?to=92";
                    break;
                case 1001:
                    // 时政、观点、社会 -> 47
                    forumURL = "chbrd?to=14";
                    break;
                case 1003:
                    forumURL = "chbrd?to=14";
                    break;
                case 1008:
                    forumURL = "chbrd?to=14";
                    break;
                case 22179:
                    // 奥运 ->66
                    forumURL = "chbrd?to=66";
                    break;
                case 41390:
                    // 家电 ->66
                    forumURL = "chbrd?to=109";
                    break;

            }
            if (forumURL.length() > 0)
            {
                res = "<td><a target='_blank' href='http://bbs.people.com.cn/boardList.do?action=postList&boardId=/"
                      + forumURL
                      + "'><img src='/img/3bbs.gif' width='77' height='16' border='0'></a></td>";
            }
        }
        return res;
    }

    public String getLTLink()
    {
        String res = "";
        List ds = getSecondChannels();
        int secondid = ((Channel)ds.get(0)).getLogicId();
        String forumURL = "";
        switch (secondid)
        {
            case 14717:
                // 地方
                forumURL = "15";
                break;
            case 1004:
                // 经济
                forumURL = "11";
                break;
            case 1006:
                // 教育
                forumURL = "18";
                break;
            case 1007:
                // 科技
                forumURL = "19";
                break;
            case 1013:
                // 文化
                forumURL = "17";
                break;
            case 14739:
                // 健康
                forumURL = "34";
                break;
            case 1002:
                // 国际
                forumURL = "6";
                break;
            case 14657:
                // 台湾
                forumURL = "4";
                break;
            case 1011:
                // 军事
                forumURL = "7";
                break;
            case 14641:
                // 视听
                forumURL = "23";
                break;
            case 1005:
                // 汽车
                forumURL = "33";
                break;
            case 41390:
                // 家电
                forumURL = "21";
                break;
            case 14677:
                // 传媒
                forumURL = "16";
                break;
            case 68880:
                // 读书
                forumURL = "31";
                break;
            case 40531:
                // 理论
                forumURL = "14";
                break;
            case 1016:
                // 图片
                forumURL = "29";
                break;
            case 1010:
                // 环保
                forumURL = "20";
                break;
            case 1009:
                // IT
                forumURL = "22";
                break;
            case 14820:
                // 体育
                forumURL = "30";
                break;
            case 41570:
                // 旅游
                forumURL = "32";
                break;
            case 40130:
                // 游戏
                forumURL = "35";
                break;

            default:
                // 默认
                forumURL = "1";

        }
        if (forumURL.length() > 0)
        {
            res = "【<a target='_blank' href='http://bbs.people.com.cn/boardList.do?action=postList&boardId="
                  + forumURL + "'>论坛</a>】";
        }

        return res;
    }

    public String getLTLinkNew()
    {
        String res = "";
        List ds = getSecondChannels();
        int secondid = ((Channel)ds.get(0)).getLogicId();
        String forumURL = "";
        switch (secondid)
        {
            case 14717:
                // 地方
                forumURL = "15";
                break;
            case 1004:
                // 经济
                forumURL = "11";
                break;
            case 1006:
                // 教育
                forumURL = "18";
                break;
            case 1007:
                // 科技
                forumURL = "19";
                break;
            case 1013:
                // 文化
                forumURL = "17";
                break;
            case 14739:
                // 健康
                forumURL = "34";
                break;
            case 1002:
                // 国际
                forumURL = "6";
                break;
            case 14657:
                // 台湾
                forumURL = "4";
                break;
            case 1011:
                // 军事
                forumURL = "7";
                break;
            case 14641:
                // 视听
                forumURL = "23";
                break;
            case 1005:
                // 汽车
                forumURL = "33";
                break;
            case 41390:
                // 家电
                forumURL = "21";
                break;
            case 14677:
                // 传媒
                forumURL = "16";
                break;
            case 68880:
                // 读书
                forumURL = "31";
                break;
            case 40531:
                // 理论
                forumURL = "14";
                break;
            case 1016:
                // 图片
                forumURL = "29";
                break;
            case 1010:
                // 环保
                forumURL = "20";
                break;
            case 1009:
                // IT
                forumURL = "22";
                break;
            case 14820:
                // 体育
                forumURL = "30";
                break;
            case 41570:
                // 旅游
                forumURL = "32";
                break;
            case 40130:
                // 游戏
                forumURL = "35";
                break;

            default:
                // 默认
                forumURL = "1";

        }
        if (forumURL.length() > 0)
        {
            res = "<a target='_blank' href='http://bbs.people.com.cn/boardList.do?action=postList&boardId="
                  + forumURL + "'>论坛</a>";
        }

        return res;
    }

    // 不同栏目文本右边的不同控制
    public String getchannel_right()
    {
        String res = "";
        List ds = getSecondChannels();
        int secondid = ((Channel)ds.get(0)).getLogicId();

        switch (secondid)
        {
            case 1009:
                // IT
                res = "{TAG_12914_TAG}";
                break;
            case 41390:
                // 家电
                res = "{TAG_12917_TAG}";
                break;
            case 14677:
                // 传媒
                res = "{TAG_13725_TAG}";
                break;
            case 14641:
                // 视听天下
                res = "{TAG_12558_TAG}";
                break;
            case 1007:
                // 科技
                res = "{TAG_12921_TAG}";
                break;
            case 1006:
                // 教育
                res = "{TAG_12918_TAG}";
                break;
            case 1010:
                // 环保
                res = "{TAG_12919_TAG}";
                break;
            case 1011:
                // 军事
                res = "{TAG_12915_TAG}";
                break;
            case 1012:
                // 娱乐
                res = "{TAG_12916_TAG}";
                break;
            case 1014:
                // 生活
                res = "{TAG_12922_TAG}";
                break;
            default:
                // 总编室
                res = "{TAG_12312_TAG}";
        }
        return res;
    }

    private String getContentXml(String command)
    {
        String result = "";
        String prefix = "CONTENTXML_";
        if (command != null && command.startsWith(prefix))
        {
            String content = contentObj.getContent();
            try
            {
                String nodeName = command.substring(prefix.length());
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setValidating(false);
                DocumentBuilder doc_builder = dbf.newDocumentBuilder();
                Document doc = doc_builder.parse(
                    new ByteArrayInputStream(content.getBytes("UTF-8")));
                Element root = doc.getDocumentElement();
                if (root != null)
                {
                    String nodevalue = XmlTool.getNodeValue(root, nodeName);
                    result = nodevalue == null ? "" : nodevalue;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }

    private String getContentXmlMapList(String command)
    {
        // CONTENTXMLMAPLIST_后面跟的是要生成列表的样式文本，其中的NAME会被替换为map中的key值，VALUE会被替换为map中的value值
        String result = "";
        String prefix = "CONTENTXMLMAPLIST_";
        if (command != null && command.startsWith(prefix))
        {
            String content = contentObj.getContent();
            try
            {
                command = command.substring(prefix.length());
                String nodeName = "";
                String attribName = "";
                String style = "";
                if (command.indexOf(",") > 0)
                {
                    nodeName = command.substring(0, command.indexOf(","));
                    command = command.substring(command.indexOf(",") + 1);
                    if (command.indexOf(",") > 0)
                    {
                        attribName = command.substring(0, command.indexOf(","));
                        style = command.substring(command.indexOf(",") + 1);
                    }
                    else
                        attribName = command;
                }
                else
                    nodeName = command;
                if (nodeName != null && nodeName.length() > 0 && attribName != null
                    && attribName.length() > 0)
                {
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    dbf.setValidating(false);
                    DocumentBuilder doc_builder = dbf.newDocumentBuilder();
                    Document doc = doc_builder.parse(
                        new ByteArrayInputStream(content.getBytes("UTF-8")));
                    Element root = doc.getDocumentElement();
                    if (root != null)
                    {
                        LinkedHashMap map = XmlTool.getNodeValueMap(root, nodeName, attribName);
                        for (Iterator iter = map.keySet().iterator(); iter.hasNext();)
                        {
                            String key = (String)iter.next();
                            String value = (String)map.get(key);
                            if (style != null && style.length() > 0)
                            {
                                String temp = style.replaceAll("KEY", key);
                                temp = temp.replaceAll("VALUE", value);
                                result += temp;
                            }
                            else
                            {
                                result += value + "<br>";
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }

    private String getContentXmlMapID(String command)
    {
        // CONTENTXMLMAPLIST_后面跟的是要生成列表的样式文本，其中的NAME会被替换为map中的key值，VALUE会被替换为map中的value值
        String result = "";
        String prefix = "CONTENTXMLMAPID_";
        if (command != null && command.startsWith(prefix))
        {
            String content = contentObj.getContent();
            try
            {
                command = command.substring(prefix.length());
                String nodeName = "";
                int id = 0;
                if (command.indexOf(",") > 0)
                {
                    nodeName = command.substring(0, command.indexOf(","));
                    id = Integer.parseInt(command.substring(command.indexOf(",") + 1));
                }
                else
                    nodeName = command;
                if (nodeName != null && nodeName.length() > 0)
                {
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    dbf.setValidating(false);
                    DocumentBuilder doc_builder = dbf.newDocumentBuilder();
                    Document doc = doc_builder.parse(
                        new ByteArrayInputStream(content.getBytes("UTF-8")));
                    Element root = doc.getDocumentElement();
                    if (root != null)
                    {
                        Vector values = XmlTool.getNodeValues(root, nodeName);
                        if (values.size() > id) result = (String)values.get(id);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获得页面关键字
     * 
     * @return 关键字字符串
     */
    public String getKeyword()
    {
        return "暂不支持";
        /*
         * StringBuffer res = new StringBuffer(); ReadDBTool readDao = (ReadDBTool)
         * SpringContextUtil.getBean("ReadDBTool"); String queryString =
         * "from ContentKWHL WHERE contentId="+contentId; List list = readDao.find(queryString);
         * if(list!=null&&list.size()>0) { if(res.length()>0){ res.append( "," ); } ContentKWHL
         * contentKWHL = (ContentKWHL)list.get(0); res.append(contentKWHL.getKeyword()); }else{
         * List ds = getOwnKeyWord(String.valueOf(contentId)); if( ds.size() > 0 ) { for( int i =
         * 0; i < ds.size(); i++ ) { Keyword keyword = (Keyword)ds.get(i);
         * res.append(Encode.htmlsp( keyword.getKeyword())); if( i < ds.size() -1 ) res.append( ","
         * ); } } } return res.toString();
         */
    }

    public String getKeywordEn()
    {
        return "暂不支持";
        /*
         * StringBuffer res = new StringBuffer(); ReadDBTool readDao = (ReadDBTool)
         * SpringContextUtil.getBean("ReadDBTool"); String queryString =
         * "from ContentKWHL WHERE contentId="+contentId; List list = readDao.find(queryString);
         * if(list!=null&&list.size()>0) { if(res.length()>0){ res.append( "," ); } ContentKWHL
         * contentKWHL = (ContentKWHL)list.get(0); res.append(contentKWHL.getKeyword()); }else{
         * List ds = getOwnKeyWord(String.valueOf(contentId)); if( ds.size() > 0 ) { for( int i =
         * 0; i < ds.size(); i++ ) { Keyword keyword = (Keyword)ds.get(i); res.append(
         * keyword.getKeyword()); if( i < ds.size() -1 ) res.append( "," ); } } } return
         * Encode.htmlsp_nospace(res.toString());
         */
    }

    public List getRelateContents_limit(String content_id)
    {
        return new ArrayList();
        /*
         * DBDao dao=(DBDao ) SpringContextUtil.getBean("DBDao"); String queryString =
         * "from POJO_ContentRelation where contentId="+content_id; List list =
         * dao.findByNumberHQL(queryString, 10); List re = new ArrayList();
         * if(list!=null&&list.size()>0){ for(int i=0;i<list.size();i++){ ContentRelationDto
         * contentRelationDto = new ContentRelationDto(); POJO_ContentRelation pojo_ContentRelation
         * = (POJO_ContentRelation)list.get(i); int relateId = pojo_ContentRelation.getRelateId();
         * String queryString1 = "from POJO_Content where contentId="+relateId; List list1 =
         * dao.findHQL(queryString1, relateId); for(int j=0;j<list1.size();j++){ POJO_Content
         * pojo_Content = (POJO_Content)list1.get(j); String title = pojo_Content.getTitle();
         * Timestamp input_date = pojo_Content.getDisplayDate(); if(input_date==null){ input_date =
         * pojo_Content.getInputDate(); } Integer node_id = pojo_Content.getNodeId();
         * contentRelationDto.setRelateId(relateId); contentRelationDto.setTitle(title);
         * contentRelationDto.setInputDate(input_date); contentRelationDto.setNodeId(node_id);
         * re.add(contentRelationDto); } } } return re;
         */
    }

    /*
     * public List getRelateContents(String content_id){ DBDao dao=(DBDao )
     * SpringContextUtil.getBean("DBDao"); String queryString =
     * "from POJO_ContentRelation where contentId="+content_id; List list =
     * dao.findHQL(queryString, Integer.parseInt(content_id)); List re = new ArrayList();
     * if(list!=null&&list.size()>0){ for(int i=0;i<list.size();i++){ ContentRelationDto
     * contentRelationDto = new ContentRelationDto(); POJO_ContentRelation pojo_ContentRelation =
     * (POJO_ContentRelation)list.get(i); int relateId = pojo_ContentRelation.getRelateId(); String
     * queryString1 = "from POJO_Content where deleted=0 and status>0 and contentId="+relateId
     * +" order by inputDate desc"; List list1 = dao.findHQL(queryString1, relateId); for(int
     * j=0;j<list1.size();j++){ POJO_Content pojo_Content = (POJO_Content)list1.get(j); String
     * title = pojo_Content.getTitle(); Timestamp input_date = pojo_Content.getDisplayDate();
     * if(input_date==null){ input_date = pojo_Content.getInputDate(); } Integer node_id =
     * pojo_Content.getNodeId(); contentRelationDto.setRelateId(relateId);
     * contentRelationDto.setTitle(title); contentRelationDto.setInputDate(input_date);
     * contentRelationDto.setNodeId(node_id); re.add(contentRelationDto); } } } return re; }
     */
    public List getOwnKeyWord(String content_id)
    {
        return new ArrayList();
        /*
         * DBDao dao=(DBDao ) SpringContextUtil.getBean("DBDao"); ReadDBTool readDao = (ReadDBTool)
         * SpringContextUtil.getBean("ReadDBTool"); String queryString =
         * "FROM POJO_ContentKw WHERE contentId="+content_id; List list = dao.findHQL(queryString,
         * Long.parseLong(content_id)); List re = new ArrayList(); for(int i=0;i<list.size();i++){
         * POJO_ContentKw pojo_ContentKw = (POJO_ContentKw)list.get(i); int keywordid =
         * pojo_ContentKw.getKeywordId(); queryString =
         * "from Keyword where keywordId="+keywordid+" and keywordDeleted=0"; List list1 =
         * readDao.find(queryString); for(int j=0;j<list1.size();j++){ Keyword keyword =
         * (Keyword)list1.get(j); re.add(keyword); } } return re;
         */
    }

    public String GetPageLink(Integer contentID, String name, String css)
    {
        if (css != null && css.length() > 0)
            return "<a href=\"" + sharedConstant.getContentViewUrl(contentID) + "\" class=\"" + css
                   + "\" target=_blank>" + name + "</a>";
        else
            return "<a href=\"" + sharedConstant.getContentViewUrl(contentID) + "\" target=_blank>" + name + "</a>";
    }

    public String GetChannelLink(Integer nodeID, String name, String css)
    {
        if (css != null && css.length() > 0)
            return "<a href=\"" + sharedConstant.getChannelViewUrl(nodeID) + "\" class=\"" + css + "\">" + name
                   + "</a>";
        else
            return "<a href=\"" + sharedConstant.getChannelViewUrl(nodeID) + "\">" + name + "</a>";
    }

    public String GetChannelStaticLink(String nodeID, String name, String css)
    {
        if (css != null && css.length() > 0)
            return "<a href=\"" + getChannelStaticURL(nodeID) + "\" class=\"" + css + "\">" + name
                   + "</a>";
        else
            return "<a href=\"" + getChannelStaticURL(nodeID) + "\">" + name + "</a>";
    }

    public String GetChannelLink_blank(Integer nodeID, String name, String css)
    {
        if (css != null && css.length() > 0)
            return "<a target='_blank' href=\"" + sharedConstant.getChannelViewUrl(nodeID) + "\" class=\"" + css
                   + "\">" + name + "</a>";
        else
            return "<a target='_blank' href=\"" + sharedConstant.getChannelViewUrl(nodeID) + "\">" + name + "</a>";
    }

    public String getChannelStaticURL(String nodeID)
    {

        Channel channel = iChannelService.getByLogicId(Integer.parseInt(nodeID));
        ChannelObj channelObj = new ChannelObj(channel);
        ChannelUrl channelUrl = channelObj.getChannelUrl();
        String domain = channelUrl.getDomain();
        String url = channelUrl.getUrlPath();
        // int language_id = channel.getSiteid();
        String urlStatic = "http://" + domain + "/GB/" + url;
        /*
         * if(language_id!=1){ urlStatic = "http://" + domain + "/" + url; }
         */
        urlStatic = urlStatic.replace("GB/index.html", "");
        urlStatic = urlStatic.replace("index.html", "");
        return urlStatic;
    }

    public String getTitle_WAIWEN()
    {
        return Encode.htmlsp_nospace(contentObj.getTitle());
    }

    // 英文
    public String English_getPublishTime()
    {
        String rt = "";
        Date releaseDate = contentObj.getDisplayDate();
        if (releaseDate == null)
        {
            releaseDate = contentObj.getInputDate();
        }
        if (releaseDate == null) releaseDate = new Date(System.currentTimeMillis());

        // 2016-10-20 韩文版的稿件发布时间（时区）与韩国统一，有利于naver对最新新闻抓取。韩国比中国时间晚一小时。
        if (contentObj.getLanguageId() == 61)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(releaseDate);// date 换成已经已知的Date对象
            cal.add(Calendar.HOUR_OF_DAY, 1);// after 1 hour
            releaseDate = cal.getTime();
        }
        rt = english_datetime_format.format(releaseDate);
        return rt;
    }

    // 法文
    public String French_getPublishTime()
    {
        String rt = "";
        Date releaseDate = contentObj.getDisplayDate();
        if (releaseDate == null)
        {
            contentObj.getInputDate();
        }
        if (releaseDate == null) releaseDate = new Date(System.currentTimeMillis());
        rt = DateUtil.Date2Str(releaseDate, "dd.MM.yyyy HH") + "h"
             + DateUtil.Date2Str(releaseDate, "mm");
        return rt;
    }

    // 俄文
    public String Russia_getPublishTime()
    {
        String rt = "";
        Date releaseDate = contentObj.getDisplayDate();
        if (releaseDate == null)
        {
            contentObj.getInputDate();
        }
        if (releaseDate == null) releaseDate = new Date(System.currentTimeMillis());
        rt = DateUtil.Date2Str(releaseDate, "HH:mm.dd/MM/yyyy");
        return rt;
    }

    // 西文
    public String Spanish_getPublishTime()
    {
        String rt = "";
        Date releaseDate = contentObj.getDisplayDate();
        if (releaseDate == null)
        {
            contentObj.getInputDate();
        }
        if (releaseDate == null) releaseDate = new Date(System.currentTimeMillis());
        rt = DateUtil.Date2Str(releaseDate, "yyyy:MM:dd.HH:mm");
        return rt;
    }

    // 德文
    public String German_getPublishTime()
    {
        String rt = "";
        Date releaseDate = contentObj.getDisplayDate();
        if (releaseDate == null) releaseDate = new Date(System.currentTimeMillis());
        SimpleDateFormat datetime_format = new SimpleDateFormat("EEEE, dd. MMMMM yyyy",
            Locale.GERMANY);
        rt = datetime_format.format(releaseDate);
        return rt;
    }

    // 自定义
    public String Custom_getPublishTime(String s)
    {
        int start = s.lastIndexOf("_");
        String css = s.substring(start + 1);
        String rt = "";
        Date releaseDate = contentObj.getDisplayDate();
        if (releaseDate == null)
        {
            releaseDate = contentObj.getInputDate();
        }
        if (releaseDate == null) releaseDate = new Date(System.currentTimeMillis());
        rt = DateUtil.Date2Str(releaseDate, css);
        return rt;
    }

    // 外文版(俄文)使用
    public String Russia_getOrigin(String name, String url)
    {
        String rt = "";
        if (name != null && name.length() > 0)
        {
            if (url != null && url.length() > 0)
            {
                rt = "Источник:" + "<a href=\"" + url + "\">" + name + "</a>";
            }
            else
            {
                rt = "Источник:" + name;
            }
        }
        return rt;
    }

    // 外文版(法文)使用
    public String French_getOrigin(String name, String url)
    {
        String rt = "";

        if (name != null && name.length() > 0)
        {
            if (url != null && url.length() > 0)
            {
                rt = " Source: " + "<a href=\"" + url + "\">" + name + "</a>";
            }
            else
            {
                rt = " Source: " + name;
            }
        }

        return rt;
    }

    // 外文版(韩文)使用
    public String Korea_getOrigin(String name, String url)
    {
        String rt = "";
        if (name != null && name.length() > 0)
        {
            if (url != null && url.length() > 0)
            {
                rt = " 출처: " + "<a href=\"" + url + "\">" + name + "</a>";
            }
            else
            {
                rt = " 출처: " + name;
            }
        }

        return rt;
    }

    public String getOriginHaiWai()
    {
        String rt = "";
        String content_id = String.valueOf(contentId);
        HashMap hs = getOriginDataSet1(content_id);
        String name = hs.get("name") == null ? "" : hs.get("name").toString().trim();
        if (name.equals("null"))
        {
            name = "";
        }
        name = name.replace("<可靠>", "");
        String url = hs.get("url") == null ? "" : hs.get("url").toString().trim();
        if (url.equals("null"))
        {
            url = "";
        }
        if (name != null && name.length() > 0)
        {
            if (url.contains("haiwainet.cn"))
            {
                rt = "来源：" + "<a href=\"" + url + "\" target=\"_blank\">" + name + "</a>";
            }
            else
            {
                rt = "来源：" + "<a href=\"" + url + "\" target=\"_blank\" rel=\"nofollow\">" + name
                     + "</a>";
            }
        }
        else
        {
            name = contentObj.getOriginName();
            url = contentObj.getOriginUrl();
            if (url.equals("null"))
            {
                url = "";
            }
            if (name != null && name.length() > 0)
            {
                if (url.contains("haiwainet.cn"))
                {
                    rt = "来源：" + "<a href=\"" + url + "\" target=\"_blank\">" + name + "</a>";
                }
                else
                {
                    rt = "来源：" + "<a href=\"" + url + "\" target=\"_blank\" rel=\"nofollow\">"
                         + name + "</a>";
                }
            }
        }
        return rt;
    }

    public String Portuguese_getOrigin(String name, String url)
    {
        String rt = "";
        if (name != null && name.length() > 0)
        {
            if (url != null && url.length() > 0)
            {
                rt = " Fonte: " + "<a href=\"" + url + "\">" + name + "</a>";
            }
            else
            {
                rt = " Fonte: " + name;
            }
        }

        return rt;
    }

    public String NORMAL_getOrigin(String name, String url)
    {
        String rt = "";

        if (name != null && name.length() > 0)
        {
            if (url != null && url.length() > 0)
            {
                rt = "<a href=\"" + url + "\">" + name + "</a>";
            }
            else
            {
                rt = name;
            }
        }

        return rt;
    }

    public String getCounterWaiwen()
    {
        return "<img src=\"" + COUNTER_ADDRESS_WAIWEN + contentObj.getContentId()
               + "\" width=0 height=0 style=\"display:none;\">";
    }

    // 外文版使用
    public String Foreign_getOtherPicture()
    {
        StringBuffer res = new StringBuffer();
        Integer page_type = contentObj.getPageType();
        if (page_type != null)
        {
            int pageType = contentObj.getPageType();
            int page = contentObj.getPage();
            int pageCount = contentObj.getPageCount();
            

            if (pageType == 2 || pageType == 21)
            {
                res.append("");
            }
            else
            {
                List newsImages = getContentImages(String.valueOf(contentId));
                if (newsImages != null)
                {
                    if (newsImages.size() > 1)
                    {
                        for (int i = 1; i < newsImages.size(); i++ )
                        {
                            ContentMedia contentMedia = (ContentMedia)newsImages.get(i);
                            res.append(
                                "<table width='450' cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                            if (page < pageCount)
                            {
                                int nextPage = contentObj.getPage() + 1;
                                res.append("<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page="
                                           + nextPage + "\"><img src=\"" + contentMedia.getPath()
                                           + "\"></a>");
                            }
                            else
                            {
                                res.append("<img src=\"" + contentMedia.getPath() + "\">");
                            }
                            res.append(
                                "</td></tr><tr><td class=\"picdesc\" align=center><font color='#000000'>"
                                       + Encode.htmlsp_nospace(contentMedia.getMediaDesc())
                                       + "</font></td></tr></table>");
                        }
                    }
                }
            }
        }
        else
        {
            List newsImages = getContentImages(String.valueOf(contentId));
            if (newsImages != null)
            {
                if (newsImages.size() > 1)
                {
                    for (int i = 1; i < newsImages.size(); i++ )
                    {
                        ContentMedia contentMedia = (ContentMedia)newsImages.get(i);
                        res.append(
                            "<table width='450' cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                        res.append("<img src=\"" + contentMedia.getPath() + "\">");
                        res.append(
                            "</td></tr><tr><td class=\"picdesc\" align=center><font color='#000000'>"
                                   + Encode.htmlsp_nospace(contentMedia.getMediaDesc())
                                   + "</font></td></tr></table>");
                    }
                }
            }
        }
        return res.toString();
    }

    // 外文版使用
    public String Foreign_getFirstPicture()
    {
        StringBuffer res = new StringBuffer();
        Integer page_type = contentObj.getPageType();
        String title = contentObj.getTitle();
        if (page_type != null)
        {
            int pageType = contentObj.getPageType();
            int page = contentObj.getPage();
            int pageCount = contentObj.getPageCount();

            if (pageType == 2 || pageType == 21)
            {// 按媒体分页
                ContentMedia contentMedia = contentObj.getPageMedia();
                if (contentMedia != null)
                {
                    res.append(
                        "<table width='450' cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                    if (page < pageCount)
                    {
                        int nextPage = contentObj.getPage() + 1;
                        res.append("<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page=" + nextPage
                                   + "\"><img src=\"" + contentMedia.getPath() + "\" alt=\""
                                   + title + "\"></a>");
                    }
                    else
                    {
                        res.append(
                            "<img src=\"" + contentMedia.getPath() + "\" alt=\"" + title + "\">");
                    }
                    res.append(
                        "</td></tr><tr><td class=\"picdesc\" align=center><font color='#000000'>"
                               + Encode.htmlsp_nospace(contentMedia.getMediaDesc())
                               + "</font></td></tr></table>");
                }
            }
            else
            {// 按正文分页
                List newsImages = getContentImages(String.valueOf(contentId));
                if (newsImages != null)
                {
                    if (newsImages.size() > 0)
                    {
                        ContentMedia contentMedia = (ContentMedia)newsImages.get(0);
                        res.append(
                            "<table width='450' cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                        if (page < pageCount)
                        {
                            int nextPage = contentObj.getPage() + 1;
                            res.append("<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page="
                                       + nextPage + "\"><img src=\"" + contentMedia.getPath()
                                       + "\" alt=\"" + title + "\"></a>");
                        }
                        else
                        {
                            res.append("<img src=\"" + contentMedia.getPath() + "\" alt=\"" + title
                                       + "\">");
                        }
                        res.append(
                            "</td></tr><tr><td class=\"picdesc\" align=center><font color='#000000'>"
                                   + Encode.htmlsp_nospace(contentMedia.getMediaDesc())
                                   + "</font></td></tr></table>");
                    }

                }
            }
        }
        else
        {
            List newsImages = getContentImages(String.valueOf(contentId));
            if (newsImages != null)
            {
                if (newsImages.size() > 0)
                {
                    ContentMedia contentMedia = (ContentMedia)newsImages.get(0);
                    res.append(
                        "<table width='450' cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                    res.append(
                        "<img src=\"" + contentMedia.getPath() + "\" alt=\"" + title + "\">");
                    res.append(
                        "</td></tr><tr><td class=\"picdesc\" align=center><font color='#000000'>"
                               + Encode.htmlsp_nospace(contentMedia.getMediaDesc())
                               + "</font></td></tr></table>");
                }
            }
        }

        return res.toString();
    }

    // 外文版使用
    public String Foreign_getPicture_Page()
    {
        StringBuffer res = new StringBuffer();
        Integer page_type = contentObj.getPageType();
        if (page_type != null)
        {
            int pageType = contentObj.getPageType();
            if (pageType == 2 || pageType == 21)
            {
                ContentMedia contentMedia = contentObj.getPageMedia();
                if (contentMedia != null)
                {
                    res.append(
                        "<table width='450' cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                    res.append("<img src=\"" + contentMedia.getPath() + "\">");
                    res.append("</td></tr><tr><td align=center><font color='#000000'>"
                               + Encode.htmlsp_nospace(contentMedia.getMediaDesc())
                               + "</font></td></tr></table>");
                }
            }
            else
            {
                List newsImages = getContentImages(String.valueOf(contentId));
                if (newsImages != null)
                {
                    if (newsImages.size() > 0)
                    {
                        ContentMedia contentMedia = (ContentMedia)newsImages.get(0);
                        res.append(
                            "<table width='450' cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                        res.append("<img src=\"" + contentMedia.getPath() + "\">");
                        res.append("</td></tr><tr><td align=center><font color='#000000'>"
                                   + Encode.htmlsp_nospace(contentMedia.getMediaDesc())
                                   + "</font></td></tr></table>");
                    }
                }
            }

        }
        return res.toString();
    }

    public String Foreign2007_getFirstPicture()
    {
        StringBuffer res = new StringBuffer();
        Integer pageType = contentObj.getPageType();
        List newsImages = getContentImages(String.valueOf(contentId));
        if (newsImages != null)
        {
            if (newsImages.size() > 0)
            {
                ContentMedia contentMedia = (ContentMedia)newsImages.get(0);
                res.append(
                    "<table width=\"206\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"206\">");
                res.append(
                    "<img src=\"" + contentMedia.getPath() + "\" width=\"206\" height=\"168\">");
                res.append(
                    "</td></tr><tr><td align=\"center\" bgcolor=\"EEEEEE\"><table width=\"206\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\"><tr><td width=\"196\"><strong>"
                           + Encode.htmlsp_nospace(contentMedia.getMediaDesc())
                           + "</strong></td></tr></table></td></tr></table>");
            }
        }
        return res.toString();
    }

    public String Foreign2007_getPicture_Page()
    {
        StringBuffer res = new StringBuffer();
        Integer page_type = contentObj.getPageType();
        if (page_type != null)
        {
            Integer pageType = contentObj.getPageType();
            if (pageType != null)
            {
                if (pageType == 2 || pageType == 21)
                {
                    ContentMedia contentMedia = contentObj.getPageMedia();
                    if (contentMedia != null)
                    {
                        res.append(
                            "<table width=\"206\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"206\">");
                        res.append("<img src=\"" + contentMedia.getPath()
                                   + "\" width=\"206\" height=\"168\">");
                        res.append(
                            "</td></tr><tr><td align=\"center\" bgcolor=\"EEEEEE\"><table width=\"206\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\"><tr><td width=\"196\"><strong>"
                                   + Encode.htmlsp_nospace(contentMedia.getMediaDesc())
                                   + "</strong></td></tr></table></td></tr></table>");
                    }
                }
            }
        }

        return res.toString();
    }

    // 日文版使用
    public String Foreign_japan_getFirstPicture()
    {
        StringBuffer res = new StringBuffer();
        int pageType = contentObj.getPageType();
        List newsImages = getContentImages(String.valueOf(contentId));
        if (newsImages != null)
        {
            if (newsImages.size() > 0)
            {
                ContentMedia contentMedia = (ContentMedia)newsImages.get(0);
                res.append(
                    "<table width='220' cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                res.append("<img src=\"" + contentMedia.getPath() + "\">");
                res.append("</td></tr><tr><td align=center><font color='#000000'>"
                           + Encode.htmlsp_nospace(contentMedia.getMediaDesc())
                           + "</font></td></tr></table>");
            }
        }

        return res.toString();
    }

    public String Foreign_japan_getPicture_Page()
    {
        StringBuffer res = new StringBuffer();
        int pageType = contentObj.getPageType();
        Integer page_type = contentObj.getPageType();
        if (page_type != null)
        {
            if (pageType == 2 || pageType == 21)
            {
                ContentMedia contentMedia = contentObj.getPageMedia();
                if (contentMedia != null)
                {
                    res.append(
                        "<table width='220' cellspacing=0 cellpadding=3 align=center><tr><td align='center'>");
                    res.append("<img src=\"" + contentMedia.getPath() + "\">");
                    res.append("</td></tr><tr><td align=center><font color='#000000'>"
                               + Encode.htmlsp_nospace(contentMedia.getMediaDesc())
                               + "</font></td></tr></table>");
                }
            }
        }
        return res.toString();
    }

    /**
     * 外文版专用相关新闻,使用模板参数中指定的文字做标题
     */

    public String getRelateNews_Spain()
    {
        return "暂不支持";
        /*
         * List ds = getRelateContents(String.valueOf(contentId)); if(ds==null){ return ""; }
         * StringBuffer sBuff = new StringBuffer(); sBuff.
         * append("<table class=\"p3_text_b\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">"
         * ); sBuff.
         * append("<tr><td style=\"padding:0 0 5px 0;\"><strong><a href=\"\">Noticias relacionadas</a></strong></td></tr>"
         * ); sBuff.append( "<tr><td>" ); for( int i = 0; i < (ds.size()>5?5:ds.size()); i++ ) {
         * ContentRelationDto rec = (ContentRelationDto)ds.get(i); sBuff.append( "·" );
         * sBuff.append( GetPageLink( String.valueOf(rec.getRelateId()), Encode.htmlsp_nospace(
         * rec.getTitle()), null ) ); if( i < ds.size() -1 ) sBuff.append( "<br>" ); }
         * sBuff.append( "</td></tr></table>" ); return sBuff.toString();
         */
    }

    /**
     * 根据第一篇文章的id取得留言的 board_id号 yangsong 2006－02－09
     * 
     * @return Long
     */
    public String getFirstboardID()
    {
        /*
         * try { //ReadDBTool readdao = (ReadDBTool)
         * SpringContextUtil.getBean("ReadDBToolWebCommon"); CommentDAO readdao = (CommentDAO)
         * SpringContextUtil.getBean("comment"); String firstContentId = getFirstID(); String
         * queryString = " from Board where newsId="+firstContentId; List list =
         * readdao.find(queryString); if (list!=null) { if(list.size()>0){ Board board =
         * (Board)list.get(0); return String.valueOf(board.getBoardId()); } } } catch (Exception
         * ex) { ex.printStackTrace(); }
         */
        return "暂不支持";
    }

    /**
     * 取得分页文章中第一篇文章content_id yangsong 2006－01－05
     * 
     * @return String
     */
    public String getFirstID()
    {
        return "暂不支持";
        /*
         * String queryString = "from ContentFirst where contentId="+contentId; ReadDBTool readDao
         * = (ReadDBTool) SpringContextUtil.getBean("ReadDBTool"); List list =
         * readDao.find(queryString); if(list!=null){ if(list.size()>0){ ContentFirst contentFirst
         * = (ContentFirst)list.get(0); String firstContentId =
         * String.valueOf(contentFirst.getFirstContentId()); return firstContentId; } return
         * String.valueOf(contentId); }else{ return String.valueOf(contentId); }
         */
    }

    /**
     * 获得第一幅图片
     * 
     * @return 内容
     */
    public String getFirstPicture1()
    {
        StringBuffer res = new StringBuffer();
        Integer page_type = contentObj.getPageType();
        if (page_type != null)
        {
            int pageType = contentObj.getPageType();
            int page = contentObj.getPage();
            int pageCount = contentObj.getPageCount();
            if (pageType == 2 || pageType == 21)
            {
                ContentMedia contentMedia = contentObj.getPageMedia();
                String desc = HTML2TextNew.convertAlt(Encode.htmlsp(contentMedia.getMediaDesc()));
                if (desc.length() == 0)
                {
                    desc = HTML2TextNew.convertAlt(getTitle());
                }
                String path = contentMedia.getPath();
                if (contentMedia != null)
                {
                    if (page < pageCount)
                    {
                        int nextPage = contentObj.getPage() + 1;
                        res.append("<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page=" + nextPage
                                   + "\"><img src=\"" + contentMedia.getPath() + "\" alt=\"" + desc
                                   + "\" ></a>");
                    }
                    else
                    {
                        res.append("<img src=\"" + path + "\" alt=\"" + desc + "\" >");
                    }

                    res.append("<br><center><font color='#000000'>"
                               + Encode.htmlsp(contentMedia.getMediaDesc()) + "</font></center>");
                }
            }
            else
            {
                List newsImages = getContentImages(String.valueOf(contentId));
                if (newsImages.size() > 0)
                {
                    String desc = HTML2TextNew.convertAlt(
                        Encode.htmlsp(((ContentMedia)newsImages.get(0)).getMediaDesc()));
                    if (desc.length() == 0)
                    {
                        desc = HTML2TextNew.convertAlt(getTitle());
                    }
                    if (page < pageCount)
                    {
                        int nextPage = contentObj.getPage() + 1;
                        res.append("<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page=" + nextPage
                                   + "\"><img src=\""
                                   + ((ContentMedia)(newsImages.get(0))).getPath() + "\" alt=\""
                                   + desc + "\" ></a>");
                    }
                    else
                    {
                        res.append("<img src=\"" + ((ContentMedia)(newsImages.get(0))).getPath()
                                   + "\" alt=\"" + desc + "\" >");
                    }

                    res.append("<br><center><font color='#000000'>"
                               + Encode.htmlsp(((ContentMedia)(newsImages.get(0))).getMediaDesc())
                               + "</font></center>");
                }
            }
        }
        else
        {
            List newsImages = getContentImages(String.valueOf(contentId));
            if (newsImages.size() > 0)
            {
                String desc = HTML2TextNew.convertAlt(
                    Encode.htmlsp(((ContentMedia)newsImages.get(0)).getMediaDesc()));
                if (desc.length() == 0)
                {
                    desc = HTML2TextNew.convertAlt(getTitle());
                }
                res.append("<img src=\"" + ((ContentMedia)(newsImages.get(0))).getPath()
                           + "\" alt=\"" + desc + "\" >");
                res.append("<br><center><font color='#000000'>"
                           + Encode.htmlsp(((ContentMedia)(newsImages.get(0))).getMediaDesc())
                           + "</font></center>");
            }
        }
        return res.toString();
    }

    public String getFullURLe()
    {
        String url = contentObj.getUrl();
        return url;
    }

    public String getSummaryNew()
    {
        String re = "";
        String summary = contentObj.getSummary();
        String content = contentObj.getContent();;
        if (summary != null && !summary.equals(""))
        {
            int len = summary.length();
            if (len > 100)
            {
                re = summary.substring(0, 100);
            }
            else
            {
                re = summary;
            }
        }
        else
        {
            if (content != null)
            {
                HtmlToText ht = new HtmlToText();
                content = ht.html2Text(content);
                content = content.replace("&$", "");
                content = StringTool.deleteBlank(content);
                int len = content.length();
                if (len > 100)
                {
                    re = content.substring(0, 100);
                }
                else
                {
                    re = content;
                }
            }
        }
        re = StringTool.deleteBlank(re);
        return re;
    }

    public String getSummaryNewEn()
    {
        String re = "";
        String summary = contentObj.getSummary();
        String content = contentObj.getContent();
        if (summary != null && !summary.equals(""))
        {
            re = summary;
        }
        else
        {
            if (content != null)
            {
                HtmlToText ht = new HtmlToText();
                content = ht.html2Text(content);
                content = content.replace("&$", "");
                // content = StringTool.deleteBlank(content);
                int len = content.length();
                if (len > 100)
                {
                    re = content.substring(0, 100);
                }
                else
                {
                    re = content;
                }
            }
        }
        // re = StringTool.deleteBlank(re);
        return re.trim();
    }

    private String getInsertIMGCONTENT()
    {
        return "暂不支持";
        /*
         * String content = Encode.htmlsp(StringTool.clob2Str(contentObj.getContent())); String
         * content_id = String.valueOf(contentObj.getContentId()); ReadDBTool readDao =
         * (ReadDBTool) SpringContextUtil.getBean("ReadDBTool"); ContentPaginAtion
         * contentPaginAtion=(ContentPaginAtion)readDao.getById(ContentPaginAtion.class,
         * Integer.valueOf(content_id)); String relatedContentId = ""; if(null!=contentPaginAtion){
         * relatedContentId = contentPaginAtion.getRelatedContentId(); } String contentReportImg =
         * getContentReportImg(relatedContentId); String re = (content.replace("[image]",
         * contentReportImg)); return re;
         */
    }

    // 人民日报记者图片获取
    private String getContentReportImg(String content_id)
    {
        return "暂不支持";
        /*
         * StringBuffer imageString = new StringBuffer(); try { DBDao dao=(DBDao )
         * SpringContextUtil.getBean("DBDao"); ReadDBTool readDao = (ReadDBTool)
         * SpringContextUtil.getBean("ReadDBTool"); String queryString =
         * "from POJO_ContentChannel where contentId in("+content_id+")"; List list =
         * dao.findByNumberHQL(queryString,10); String node_id= ""; if(list!=null){ for(int
         * i=0;i<list.size();i++){ POJO_ContentChannel pOJO_ContentChannel =
         * (POJO_ContentChannel)list.get(i); if(i==0){ node_id=
         * String.valueOf(pOJO_ContentChannel.getNodeId()); }else{ node_id =
         * node_id+","+String.valueOf(pOJO_ContentChannel.getNodeId()); } } } String queryString1 =
         * "from Reporter where reporterNodeId in("+node_id+")"; List list1 =
         * readDao.findPage(queryString1, 0, 3, null); if(list1!=null){
         * imageString.append("<div align='center'>");
         * imageString.append("<div class=\"r_title\">联系本文记者</div>");
         * imageString.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">");
         * imageString.append("<tr>"); for(int i=0;i<list1.size();i++){ Reporter reporter =
         * (Reporter)list1.get(i); String REPORTER_IMAGE = reporter.getReporterImage(); String
         * REPORTER_NAME = reporter.getReporterName();; String REPORTER_NODE_ID =
         * String.valueOf(reporter.getReporterNodeId()); String REPORTER_BLOG =
         * reporter.getReporterBlog(); String REPORTER_MICROBLOG = reporter.getReporterMicroblog();
         * imageString.append("<td>"); imageString.append("<a href=\"ChannelView.shtml?id="
         * +REPORTER_NODE_ID+"\" target=\"_blank\"><img src=\""
         * +REPORTER_IMAGE+"\" alt=\"\" width=\"118\" height=\"134\" border=\"0\" /></a><br />");
         * imageString.append("<div align=\"center\"><a href=\"ChannelView.shtml?id="
         * +REPORTER_NODE_ID+"\" target=\"_blank\">"+REPORTER_NAME+"</a><br /></div>");
         * imageString.append("<span>"); imageString.
         * append("<a href=\"http://comments.people.com.cn/bbs_new/app/src/main/?action=list&nid="
         * +REPORTER_NODE_ID+"\" target=\"_blank\">[留言]</a>");
         * imageString.append("<a href=\""+REPORTER_BLOG+"\" target=\"_blank\">[博客]</a>");
         * imageString.append("<a href=\""+REPORTER_MICROBLOG+"\" target=\"_blank\">[微博]</a>");
         * imageString.append("</span>"); imageString.append("</td>"); }
         * imageString.append("</tr>"); imageString.append("</table>");
         * imageString.append("</div>"); } } catch (Exception e) { e.printStackTrace(); } return
         * imageString.toString();
         */
    }

    private String getAllPicture_GQ()
    { // 高清图片
        StringBuffer res = new StringBuffer();
        List newsImages = getContentImages(String.valueOf(contentId));
        int j = 0;
        if (newsImages != null)
        {
            for (int i = 0; i < newsImages.size(); i++ )
            {
                ContentMedia contentMedia = (ContentMedia)newsImages.get(i);
                String path = contentMedia.getPath();
                int urlpage = j;
                res.append("<li><a href=\"?urlpage=" + urlpage + "\"><img src=\"" + path
                           + "\" width=\"100\" height=\"60\" /></a></li>");
                j++ ;
            }
        }

        return res.toString();
    }

    private String getAllPicture_GQ_City()
    { // 城市网高清图片切换
        StringBuffer res = new StringBuffer();
        List newsImages = getContentImages(String.valueOf(contentId));
        int j = 0;
        if (newsImages != null)
        {
            for (int i = 0; i < newsImages.size(); i++ )
            {
                ContentMedia contentMedia = (ContentMedia)newsImages.get(i);
                String path = contentMedia.getPath();
                String desc = contentMedia.getMediaDesc();
                int urlpage = j;
                res.append("<li><img src=\"" + path
                           + "\" width=\"440\" height=\"310\" alt=\"\"/><p>" + desc + "</p></li>");
                j++ ;
            }
        }

        return res.toString();
    }

    private String getAllPicture_GQ1()
    {
        StringBuffer res = new StringBuffer();
        List newsImages = getContentImages(String.valueOf(contentId));
        if (newsImages != null)
        {
            for (int i = 0; i < newsImages.size(); i++ )
            {
                ContentMedia contentMedia = (ContentMedia)newsImages.get(i);
                String path = contentMedia.getPath();
                String desc = contentMedia.getMediaDesc();
                if (desc == null)
                {
                    desc = "";
                }
                res.append("<li><img src=\"" + path + "\" border=\"0\"  alt=\"\"/>");
                res.append("<p class=\"group_show\"><em><b id=\"imgNo\">" + (i + 1) + "</b>/"
                           + newsImages.size() + "</em>" + desc + "</p></li>");
            }
        }

        return res.toString();
    }

    private String getAllPicture_GQSX1()
    {
        StringBuffer res = new StringBuffer();
        List newsImages = getContentImages(String.valueOf(contentId));
        if (newsImages != null)
        {
            Integer pageSize = newsImages.size();
            for (int i = 0; i < newsImages.size(); i++ )
            {
                ContentMedia contentMedia = (ContentMedia)newsImages.get(i);
                String path = contentMedia.getPath();
                String desc = contentMedia.getMediaDesc();
                String title = contentMedia.getTitle();
                String alt = title + "     " + desc;
                Integer pageNo = i;
                if (desc == null)
                {
                    desc = "";
                }
                if (title == null)
                {
                    title = "";
                }
                if (i == 0)
                {
                    res.append("<li class=\"flex-active-slide\"><div class=\"pic\">");
                }
                else
                {
                    res.append("<li data-thumb-alt=\"\" class=\"m10\"><div class=\"pic\">");
                }
                res.append("<img src=\"" + path + "\" alt=\"" + alt + "\" draggable=\"false\" >");
                res.append(
                    "<div class=\"pic_info\"><p class=\"title\"><span class=\"spage\"></span>"
                           + title + "</p><p class=\"pic_info_con\">" + desc
                           + "</p><p class=\"pic_copy\">版权作品，请勿转载</p></div>");
                res.append("</div></li>");
            }
        }
        return res.toString();
    }

    private String getAllPicture_GQSX2()
    {
        StringBuffer res = new StringBuffer();
        List newsImages = getContentImages(String.valueOf(contentId));
        if (newsImages != null)
        {
            for (int i = 0; i < newsImages.size(); i++ )
            {
                ContentMedia contentMedia = (ContentMedia)newsImages.get(i);
                String path = contentMedia.getPath();
                String desc = contentMedia.getMediaDesc();
                String title = contentMedia.getTitle();
                String alt = title + "     " + desc;
                if (desc == null)
                {
                    desc = "";
                }
                if (title == null)
                {
                    title = "";
                }
                res.append("<li class=\"slides_img\">");
                res.append(
                    "<img info src=\"" + path + "\" alt=\"" + title + "\" usemap=\"#Map\">");
                res.append(
                    "<span class=\"title\"><div class=\"t_text\">" + desc + "</div></span>");
                res.append("</li>");
            }
        }

        return res.toString();
    }

    private String getGroupPic()
    {
        StringBuffer res = new StringBuffer();
        List newsImages = getContentImages(String.valueOf(contentId));
        String title = contentObj.getTitle();
        if (newsImages != null && newsImages.size() > 2)
        {
            String sb = "";
            for (int i = 0; i < newsImages.size(); i++ )
            {
                ContentMedia contentMedia = (ContentMedia)newsImages.get(i);
                String path = contentMedia.getPath();
                String desc = contentMedia.getMediaDesc();
                if (desc == null)
                {
                    desc = "";
                }
                sb += "<li><img src=\"" + path + "\" border=\"0\"  alt=\"" + title + "\"/>";
                sb += "<p class=\"group_show\"><em><b id=\"imgNo\">" + (i + 1) + "</b>/"
                      + newsImages.size() + "</em>" + desc + "</p></li>";
            }
            res.append("<div class=\"pic_group clearfix clear\">");
            res.append("<div class=\"pic_big\">");
            res.append("<div class=\"big_focus\" id=\"picG\">");
            res.append("</div>");
            res.append("<span class=\"group_left_b prev\"></span>");
            res.append("<span class=\"group_right_b next\"></span>");
            res.append("</div>");
            res.append("<div class=\"pic_small\">");
            res.append(
                "<img src=\"/img/MAIN/2013/12/113933/images/left_b.jpg\" class=\"prev fl\" title=\"前一张\" alt=\"\" border=\"0\" />");
            res.append("<div class=\"small_show\">");
            res.append("<ul>");
            res.append(sb);
            res.append("</ul>");
            res.append("</div>");
            res.append(
                "<img src=\"/img/MAIN/2013/12/113933/images/right_b.jpg\" class=\"next fr\" nextBtn title=\"后一张\" alt=\"\" border=\"0\" />");
            res.append("</div>");
            res.append("</div>");
            res.append("<div class=\"about_news_c\" style=\"display:none;\">");
            res.append("<div class=\"about_news\">");
            res.append("<h2>" + title + "</h2>");
            res.append("<p><span id=\"myrefresh\" style=\"color:#fff;\">重新浏览</span></p>");
            res.append("<ul id=\"tuijian\">");
            res.append("</ul>");
            res.append("</div>");
            res.append("</div>");
        }
        return res.toString();
    }

    private String getGroupPicNew()
    {
        StringBuffer res = new StringBuffer();
        List newsImages = getContentImages(String.valueOf(contentId));
        String title = contentObj.getTitle();
        if (newsImages != null && newsImages.size() > 2)
        {
            String sb = "";
            for (int i = 0; i < newsImages.size(); i++ )
            {
                ContentMedia contentMedia = (ContentMedia)newsImages.get(i);
                String path = contentMedia.getPath();
                String desc = contentMedia.getMediaDesc();
                if (desc == null)
                {
                    desc = "";
                }
                sb += "picArray[" + i + "] =\"" + path + "\";";
                sb += "picText[" + i + "]  = \"" + desc + "\";";
                sb += "picPage[" + i + "]  = \"" + (i + 1) + "\";";
            }
            res.append("<script type=\"text/javascript\">");
            res.append("var picArray = new Array();");
            res.append("var picText = new Array();");
            res.append("var picPage =  new Array();");
            res.append(sb);
            res.append("</script>");
        }
        return res.toString();
    }

    private String getGQPicNew()
    { // 高清图片
        StringBuffer res = new StringBuffer();
        Integer page_type = contentObj.getPageType();
        if (page_type != null)
        {
            int pageType = contentObj.getPageType();
            if (pageType == 2 || pageType == 21)
            {
                ContentMedia contentMedia = contentObj.getPageMedia();
                String desc = contentMedia.getMediaDesc() == null ? "" : contentMedia.getMediaDesc();
                if (desc != null)
                {
                    desc = HTML2TextNew.convertAlt(Encode.htmlsp(desc));
                }
                if (desc.length() == 0)
                {
                    desc = HTML2TextNew.convertAlt(getTitle());
                }
                String path = contentMedia.getPath();
                if (contentMedia != null)
                {
                    res.append("<img src=\"" + path + "\" border=\"0\" alt=\"" + desc + "\" >");
                    res.append("<div class=\"text_show_img\">" + Encode.htmlsp(desc) + "</div>");
                }
            }
            if (pageType == 0)
            {
                List newsImages = getContentImages(String.valueOf(contentId));
                if (newsImages.size() > 0)
                {
                    String desc = HTML2TextNew.convertAlt(
                        Encode.htmlsp(((ContentMedia)newsImages.get(0)).getMediaDesc()));
                    String path = ((ContentMedia)newsImages.get(0)).getPath();
                    if (desc.length() == 0)
                    {
                        desc = HTML2TextNew.convertAlt(getTitle());
                    }
                    res.append("<img src=\"" + path + "\" border=\"0\" alt=\"" + desc + "\" >");
                    res.append("<div class=\"text_show_img\">" + Encode.htmlsp(desc) + "</div>");
                }
            }
        }
        else
        {
            List newsImages = getContentImages(String.valueOf(contentId));
            if (newsImages.size() > 0)
            {
                String desc = HTML2TextNew.convertAlt(
                    Encode.htmlsp(((ContentMedia)newsImages.get(0)).getMediaDesc()));
                String path = ((ContentMedia)newsImages.get(0)).getPath();
                if (desc.length() == 0)
                {
                    desc = HTML2TextNew.convertAlt(getTitle());
                }
                res.append("<img src=\"" + path + "\" border=\"0\" alt=\"" + desc + "\" >");
                res.append("<div class=\"text_show_img\">" + Encode.htmlsp(desc) + "</div>");
            }
        }
        return res.toString();
    }

    private String getGQPicNewFcms()
    { // 高清图片
        StringBuffer res = new StringBuffer();
        Integer page_type = contentObj.getPageType();
        if (page_type != null)
        {
            int pageType = contentObj.getPageType();
            if (pageType == 2 || pageType == 21)
            {
                ContentMedia contentMedia = contentObj.getPageMedia();
                String desc = contentMedia.getMediaDesc() == null ? "" : contentMedia.getMediaDesc();
                if (desc != null)
                {
                    desc = HTML2TextNew.convertAlt(desc);
                }
                if (desc.length() == 0)
                {
                    desc = HTML2TextNew.convertAlt(getTitle());
                }
                String path = contentMedia.getPath();
                if (contentMedia != null)
                {
                    res.append("<img src=\"" + path + "\" border=\"0\" alt=\"" + desc + "\" >");
                    res.append("<div class=\"text_show_img\">" + desc + "</div>");
                }
            }
            if (pageType == 0)
            {
                List newsImages = getContentImages(String.valueOf(contentId));
                if (newsImages.size() > 0)
                {
                    String desc = HTML2TextNew.convertAlt(
                        ((ContentMedia)newsImages.get(0)).getMediaDesc());
                    String path = ((ContentMedia)newsImages.get(0)).getPath();
                    if (desc.length() == 0)
                    {
                        desc = HTML2TextNew.convertAlt(getTitle());
                    }
                    res.append("<img src=\"" + path + "\" border=\"0\" alt=\"" + desc + "\" >");
                    res.append("<div class=\"text_show_img\">" + desc + "</div>");
                }
            }
        }
        else
        {
            List newsImages = getContentImages(String.valueOf(contentId));
            if (newsImages.size() > 0)
            {
                String desc = HTML2TextNew.convertAlt(((ContentMedia)newsImages.get(0)).getMediaDesc());
                String path = ((ContentMedia)newsImages.get(0)).getPath();
                if (desc.length() == 0)
                {
                    desc = HTML2TextNew.convertAlt(getTitle());
                }
                res.append("<img src=\"" + path + "\" border=\"0\" alt=\"" + desc + "\" >");
                res.append("<div class=\"text_show_img\">" + desc + "</div>");
            }
        }
        return res.toString();
    }

    private String getGQPicNewContent()
    {
        String contentPrefix = contentObj.getContentPrefix();
        // String contentPostfix = contentObj.getContentPostfix();
        String rt = contentObj.getContentString();

        if (contentPrefix != null)
        {
            rt = contentPrefix + contentObj.getContentString();
        }
        if (SYSTEM_NAME.equals("FOREIGN"))
        {
            rt = Encode.htmlsp_nospace(rt);
        }
        else
        {
            rt = Encode.htmlsp(rt);
        }
        return rt;
    }

    private String getGQPicNewPage()
    {
        String pageStr = "<div class=\"page_n\">";
        int pageCount = contentObj.getPageCount();
        int page = contentObj.getPage();
        if (pageCount != 1)
        {
            int startnum = page;
            if (startnum <= 0)
            {
                startnum = 1;
            }
            int endnum = startnum + 4;

            if (endnum > pageCount)
            {
                endnum = pageCount;
            }
            /*
             * for(int i=1;i<=pageCount;i++){ pageStr =
             * pageStr+"<a href=\"NewsView.shtml?id="+contentId+"&page="+i+"\">【"+i+"】</a>"; }
             */
            for (int i = startnum; i <= endnum; i++ )
            {
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page=" + i
                          + "\">【" + i + "】</a>";
            }
            if (page > 1)
            {
                int prePage = contentObj.getPage() - 1;
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page=" + prePage
                          + "\" id=\"prev\">上一页</a>";
            }
            if (page < pageCount)
            {
                int nextPage = contentObj.getPage() + 1;
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page="
                          + nextPage + "\" id=\"next\">下一页</a>";
            }
        }
        pageStr = pageStr + "</div>";
        return pageStr;
    }

    private String getGQPicNewPage1()
    {
        String pageStr = "<div class=\"page_n\">";
        int pageCount = contentObj.getPageCount();
        int page = contentObj.getPage();
        if (pageCount != 1)
        {
            int startnum = page;
            if (startnum <= 0)
            {
                startnum = 1;
            }
            int endnum = startnum + 4;

            if (endnum > pageCount)
            {
                endnum = pageCount;
            }
            /*
             * for(int i=1;i<=pageCount;i++){ pageStr =
             * pageStr+"<a href=\"NewsView.shtml?id="+contentId+"&page="+i+"\">【"+i+"】</a>"; }
             */
            for (int i = 1; i <= pageCount; i++ )
            {
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page=" + i
                          + "\">" + i + "</a>";
            }
            if (page > 1)
            {
                int prePage = contentObj.getPage() - 1;
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page=" + prePage
                          + "\" id=\"prev\">上一页</a>";
            }
            if (page < pageCount)
            {
                int nextPage = contentObj.getPage() + 1;
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page="
                          + nextPage + "\" id=\"next\">下一页</a>";
            }
        }
        pageStr = pageStr + "</div>";
        return pageStr;
    }

    private String getGQPicNewPage2()
    {
        String pageStr = "<div class=\"page_n\">";
        int pageCount = contentObj.getPageCount();
        int page = contentObj.getPage();
        if (pageCount != 1)
        {
            int startnum = page;
            if (startnum <= 0)
            {
                startnum = 1;
            }
            int endnum = startnum + 4;

            if (endnum > pageCount)
            {
                endnum = pageCount;
            }
            /*
             * for(int i=1;i<=pageCount;i++){ pageStr =
             * pageStr+"<a href=\"NewsView.shtml?id="+contentId+"&page="+i+"\">【"+i+"】</a>"; }
             */
            for (int i = 1; i <= pageCount; i++ )
            {
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page=" + i
                          + "\">" + i + "</a>";
            }
            if (page > 1)
            {
                int prePage = contentObj.getPage() - 1;
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page=" + prePage
                          + "\" id=\"prev\">上一页</a>";
            }
            if (page < pageCount)
            {
                int nextPage = contentObj.getPage() + 1;
                pageStr = pageStr + "<a href=\"" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page="
                          + nextPage + "\" id=\"next\">下一页</a>";
            }
            if (page > 1 && page == pageCount)
            {
                int nextPage = contentObj.getPage() + 1;
                ContentMedia POJO_ContentMedia = getNextContentMedia(contentObj);
                if (POJO_ContentMedia != null)
                {
                    String nextPageImgUrl = POJO_ContentMedia.getPath();
                    String content_id = String.valueOf(POJO_ContentMedia.getContentId());
                    String mediaName = POJO_ContentMedia.getMediaName();
                    Content nextContent = iContentService.getById(content_id);
                    String nextPageUrl =  sharedConstant.getContentViewUrl(nextContent.getLogicId());
                    String firstImgUrl =  sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page=1";
                    if (!nextPageImgUrl.contains("http://"))
                    {
                        nextPageImgUrl = "http://www.people.com.cn" + nextPageImgUrl;
                    }
                    String next = "javascript:showAd('" + nextPageImgUrl + "', '" + nextPageUrl
                                  + "', '" + firstImgUrl + "', '" + mediaName
                                  + "','http://wow.people.com.cn/');";
                    pageStr = pageStr + "<a href=\"" + next + "\" id=\"next\">下一页</a>";
                }
            }
        }
        pageStr = pageStr + "</div>";
        return pageStr;
    }

    private String getGQPicNewPage3()
    {
        String pageStr = "";
        ContentMedia POJO_ContentMedia = getNextContentMedia(contentObj);
        if (POJO_ContentMedia != null)
        {
            String nextPageImgUrl = POJO_ContentMedia.getPath();
            String content_id = String.valueOf(POJO_ContentMedia.getContentId());
            String mediaName = POJO_ContentMedia.getMediaName();
            Content nextContent = iContentService.getById(content_id);
            String nextPageUrl = sharedConstant.getContentViewUrl(nextContent.getLogicId());
            String firstImgUrl = "" + sharedConstant.getContentViewUrl(contentObj.getContentLogicId()) + "?page=1";
            if (!nextPageImgUrl.contains("http://"))
            {
                nextPageImgUrl = "http://www.people.com.cn" + nextPageImgUrl;
            }
            pageStr = "<script type=\"text/javascript\">function after_ad(){ showAd('"
                      + nextPageImgUrl + "', '" + nextPageUrl + "', '" + firstImgUrl + "', '"
                      + mediaName + "','http://wow.people.com.cn/');}</script>";
        }
        return pageStr;
    }

    private String getMsnPic()
    {
        StringBuffer res = new StringBuffer();
        List newsImages = getContentImages(String.valueOf(contentId));
        int j = 0;
        if (newsImages != null)
        {
            for (int i = 0; i < newsImages.size(); i++ )
            {
                ContentMedia contentMedia = (ContentMedia)newsImages.get(i);
                String path = contentMedia.getPath();
                String desc = contentMedia.getMediaDesc() == null ? "" : contentMedia.getMediaDesc();
                String url = sharedConstant.getContentViewUrl(contentObj.getContentLogicId());
                res.append("<li><a href=\"" + url + "\" target=\"_blank\"><img alt=\"" + desc
                           + "\" src=\"" + path + "\" bigpic=\"" + path + "\" desc=\"" + desc
                           + "\"/></a></li>");
                j++ ;
            }
        }

        return res.toString();
    }

    private String getAllPictureDesc_GQ()
    { // 高清图片
        StringBuffer res = new StringBuffer();
        List newsImages = getContentImages(String.valueOf(contentId));
        for (int i = 0; i < newsImages.size(); i++ )
        {
            ContentMedia contentMedia = (ContentMedia)newsImages.get(i);
            String desc = contentMedia.getMediaDesc();
            if (desc != null)
            {
                res.append("<b>" + desc + "</b>");
            }
        }
        return res.toString();
    }

    private String getPicUp_GQ()
    {
        return "暂时不支持图集";
        /*
         * SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); long node_id
         * =contentObj.getNodeId(); Date input_date = contentObj.getInputDate(); String inputDate =
         * f.format(input_date); Date beforedate = new Date(); Date afterdate = new Date(); long
         * beforeTime=(input_date.getTime()/1000)-60*60*24*30; long
         * afterTime=(input_date.getTime()/1000)+60*60*24*30; beforedate.setTime(beforeTime*1000);
         * afterdate.setTime(afterTime*1000); String before= f.format(beforedate); String after
         * =f.format(afterdate); DBDao dao=(DBDao ) SpringContextUtil.getBean("DBDao"); //String
         * queryString =
         * "from POJO_ContentChannel where status=40 and nodeId="+node_id+" and inputDate>'"
         * +before+"' and inputDate<'"+inputDate+"' order by inputDate desc"; String queryString =
         * "from POJO_ContentChannel where status=40 and nodeId="+node_id+" and contentId>"
         * +contentId+" and mediaCount>=1 order by contentId"; List list =
         * dao.findByNumberHQL(queryString, 1); StringBuffer res = new StringBuffer(); long
         * language_id = contentObj.getLanguageId(); String tuji = "上一图集"; if(language_id==5){ tuji
         * = "Álbum anterior"; } if(language_id==4){ tuji = "Предыдущая фотолента"; }
         * if(language_id==29){ tuji = "前の写真集"; } if(language_id==31){ tuji = "上一图集"; }
         * if(language_id==3){ tuji = "Série précédente"; } if(language_id==6){ tuji =
         * "المجموعة السابقة"; } if(list!=null){ for(int i=0;i<list.size();i++){
         * POJO_ContentChannel pOJO_ContentChannel = (POJO_ContentChannel)list.get(i); long
         * content_id = pOJO_ContentChannel.getContentId(); POJO_Content contentPojo =
         * (POJO_Content) dao.getById(POJO_Content.class, (long) content_id, (long) content_id);
         * ContentObj content=new ContentObj(contentPojo); String url = content.getStaticUrl();
         * List newsImages = getContentImages(String.valueOf(content_id)); if(newsImages!=null){
         * ContentMedia contentMedia = (ContentMedia)newsImages.get(0); String path =
         * contentMedia.getPath(); res.append("<a href=\""+url+"\"><img src=\""
         * +path+"\" width=\"112\" height=\"77\" alt=\" \" /></a><br /><a href=\""+url+
         * "\">&lt;&lt;"+tuji+"</a>"); } } } return res.toString();
         */
    }

    private String getPicUpNew_GQ()
    {
        return "暂不支持图集";
        /*
         * SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); long node_id
         * =contentObj.getNodeId(); Date input_date = contentObj.getInputDate(); String inputDate =
         * f.format(input_date); Date beforedate = new Date(); Date afterdate = new Date(); long
         * beforeTime=(input_date.getTime()/1000)-60*60*24*30; long
         * afterTime=(input_date.getTime()/1000)+60*60*24*30; beforedate.setTime(beforeTime*1000);
         * afterdate.setTime(afterTime*1000); String before= f.format(beforedate); String after
         * =f.format(afterdate); DBDao dao=(DBDao ) SpringContextUtil.getBean("DBDao"); String
         * queryString =
         * "from POJO_ContentChannel where status=40 and nodeId="+node_id+" and inputDate>'"
         * +before+"' and inputDate<'"+inputDate+"' order by inputDate desc"; List list =
         * dao.findByNumberHQL(queryString, 1); StringBuffer res = new StringBuffer(); long
         * language_id = contentObj.getLanguageId(); String tuji = "上一图集"; if(language_id==5){ tuji
         * = "Álbum anterior"; } if(language_id==4){ tuji = "Предыдущая фотолента"; }
         * if(language_id==29){ tuji = "前の写真集"; } if(language_id==31){ tuji = "上一图集"; }
         * if(language_id==3){ tuji = "Série précédente"; } if(language_id==6){ tuji =
         * "المجموعة السابقة"; } if(list!=null){ for(int i=0;i<list.size();i++){
         * POJO_ContentChannel pOJO_ContentChannel = (POJO_ContentChannel)list.get(i); long
         * content_id = pOJO_ContentChannel.getContentId(); POJO_Content contentPojo =
         * (POJO_Content) dao.getById(POJO_Content.class, (long) content_id, (long) content_id);
         * ContentObj content=new ContentObj(contentPojo); String url = content.getUrl(); List
         * newsImages = getContentImagesNew(String.valueOf(content_id)); if(newsImages!=null){
         * ContentMedia contentMedia = (ContentMedia)newsImages.get(0); String path =
         * contentMedia.getPath();
         * res.append("<a href=\"NewsView.shtml?id="+content_id+"\" target=\"_blank\"><img src=\""
         * +path+"\" width=\"94\" height=\"56\" border=\"0\" alt=\"\" /></a><br /><a href=\"NewsView.shtml?id="
         * +content_id+"\">&lt;&lt;"+tuji+"</a>"); } } }else{ List ds = getSecondChannels(); int
         * nodeId = ((Channel)ds.get(0)).getNodeId(); res.append("<a href=\"ChannelView.shtml?id="
         * +nodeId+"\" target=\"_blank\"><img src=\"http://www.people.com.cn/img/2012wb/images/morepic.jpg\" width=\"94\" height=\"56\" border=\"0\" alt=\"\" /></a><br /><a href=\"ChannelView.shtml?id="
         * +nodeId+"\">&lt;&lt;"+tuji+"</a>"); } return res.toString();
         */
    }

    private String getPicDown_GQ()
    {
        return "暂不支持图集";
        /*
         * SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); long node_id
         * =contentObj.getNodeId(); Date input_date = contentObj.getInputDate(); String inputDate =
         * f.format(input_date); Date beforedate = new Date(); Date afterdate = new Date(); long
         * beforeTime=(input_date.getTime()/1000)-3600*24*30; long
         * afterTime=(input_date.getTime()/1000)+3600*24*30; beforedate.setTime(beforeTime*1000);
         * afterdate.setTime(afterTime*1000); String before= f.format(beforedate); String after
         * =f.format(afterdate); DBDao dao=(DBDao ) SpringContextUtil.getBean("DBDao"); //String
         * queryString =
         * "from POJO_ContentChannel where status=40 and nodeId="+node_id+" and inputDate>'"
         * +inputDate+"' and inputDate<'"+after+"' order by inputDate desc"; String queryString =
         * "from POJO_ContentChannel where status=40 and nodeId="+node_id+" and contentId<"
         * +contentId+" and mediaCount>=1 order by contentId desc"; List list =
         * dao.findByNumberHQL(queryString, 1); StringBuffer res = new StringBuffer(); long
         * language_id = contentObj.getLanguageId(); String tuji = "下一图集"; if(language_id==5){ tuji
         * = "Próximo álbum"; } if(language_id==4){ tuji = "Следующая фотолента"; }
         * if(language_id==29){ tuji = "次の写真集"; } if(language_id==31){ tuji = "下一图集"; }
         * if(language_id==3){ tuji = "Série suivante"; } if(language_id==6){ tuji =
         * " المجموعة التالية"; } if(list!=null){ POJO_ContentChannel pOJO_ContentChannel =
         * (POJO_ContentChannel)list.get(0); long content_id = pOJO_ContentChannel.getContentId();
         * POJO_Content contentPojo = (POJO_Content) dao.getById(POJO_Content.class, (long)
         * content_id, (long) content_id); ContentObj content=new ContentObj(contentPojo); String
         * url = content.getStaticUrl(); List newsImages =
         * getContentImages(String.valueOf(content_id)); if(newsImages!=null &&
         * newsImages.size()>0){ ContentMedia contentMedia = (ContentMedia)newsImages.get(0);
         * String path = contentMedia.getPath(); res.append("<a href=\""+url+"\"><img src=\""
         * +path+"\" width=\"112\" height=\"77\" alt=\" \" /></a><br /><a href=\""+url+
         * "\">&lt;&lt;"+tuji+"</a>"); } } return res.toString();
         */
    }

    private String getPicDownNew_GQ()
    {
        return "暂不支持图集";
        /*
         * SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); long node_id
         * =contentObj.getNodeId(); Date input_date = contentObj.getInputDate(); String inputDate =
         * f.format(input_date); Date beforedate = new Date(); Date afterdate = new Date(); long
         * beforeTime=(input_date.getTime()/1000)-3600*24*30; long
         * afterTime=(input_date.getTime()/1000)+3600*24*30; beforedate.setTime(beforeTime*1000);
         * afterdate.setTime(afterTime*1000); String before= f.format(beforedate); String after
         * =f.format(afterdate); DBDao dao=(DBDao ) SpringContextUtil.getBean("DBDao"); String
         * queryString =
         * "from POJO_ContentChannel where status=40 and nodeId="+node_id+" and inputDate>'"
         * +inputDate+"' and inputDate<'"+after+"' order by inputDate asc"; List list =
         * dao.findByNumberHQL(queryString, 2); StringBuffer res = new StringBuffer(); long
         * language_id = contentObj.getLanguageId(); String tuji = "下一图集"; if(language_id==5){ tuji
         * = "Próximo álbum"; } if(language_id==4){ tuji = "Следующая фотолента"; }
         * if(language_id==29){ tuji = "次の写真集"; } if(language_id==31){ tuji = "下一图集"; }
         * if(language_id==3){ tuji = "Série suivante"; } if(language_id==6){ tuji =
         * " المجموعة التالية"; } if(list!=null){ if(list.size()==2){ POJO_ContentChannel
         * pOJO_ContentChannel = (POJO_ContentChannel)list.get(1); long content_id =
         * pOJO_ContentChannel.getContentId(); POJO_Content contentPojo = (POJO_Content)
         * dao.getById(POJO_Content.class, (long) content_id, (long) content_id); ContentObj
         * content=new ContentObj(contentPojo); String url = content.getUrl(); List newsImages =
         * getContentImagesNew(String.valueOf(content_id)); if(newsImages!=null){ ContentMedia
         * contentMedia = (ContentMedia)newsImages.get(0); String path = contentMedia.getPath();
         * res.append("<a href=\"NewsView.shtml?id="+content_id+"\" target=\"_blank\"><img src=\""
         * +path+"\" width=\"94\" height=\"56\" border=\"0\" alt=\"\" /></a><br /><a href=\"NewsView.shtml?id="
         * +content_id+"\">"+tuji+"&gt;&gt;</a>"); } }else{ List ds = getSecondChannels(); int
         * nodeId = ((Channel)ds.get(0)).getNodeId(); res.append("<a href=\"ChannelView.shtml?id="
         * +nodeId+"\" target=\"_blank\"><img src=\"http://www.people.com.cn/img/2012wb/images/morepic.jpg\" width=\"94\" height=\"56\" border=\"0\" alt=\"\" /></a><br /><a href=\"ChannelView.shtml?id="
         * +nodeId+"\">"+tuji+"&gt;&gt;</a>"); } }else{ List ds = getSecondChannels(); int nodeId =
         * ((Channel)ds.get(0)).getNodeId(); res.append("<a href=\"ChannelView.shtml?id="
         * +nodeId+"\" target=\"_blank\"><img src=\"http://www.people.com.cn/img/2012wb/images/morepic.jpg\" width=\"94\" height=\"56\" border=\"0\" alt=\"\" /></a><br /><a href=\"ChannelView.shtml?id="
         * +nodeId+"\">"+tuji+"&gt;&gt;</a>"); } return res.toString();
         */
    }

    public String getPageUpAndDown()
    {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Integer node_id = contentObj.getNodeId();
        Date input_date = contentObj.getInputDate();
        String contentId = contentObj.getContentId();
        String inputDate = f.format(input_date);
        int languageId = contentObj.getLanguageId();

        ContentDetailVO preContent = iContentService.getPreContent(node_id, inputDate, contentId);

        StringBuffer res = new StringBuffer();
        if (preContent != null)
        {
            String title = preContent.getTitle();

            ContentObj content = new ContentObj(preContent);
            String url = content.getUrl();
            String word = "";
            if (languageId == 30)
            {
                word = "上一章";
            }
            else if (languageId == 6)
            {
                word = "المقالة السابقة";
            }
            else
            {
                word = "上一篇";
            }
            res.append("<div class=\"lianjie\"><a href=\""
                       + sharedConstant.getContentViewUrl(content.getContentLogicId()) + "\"><span>" + word + "</span>" + title + "</a>");

        }
        else
        {
            res.append("<div class=\"lianjie\">");
        }
        if (languageId == 30)
        {
            res.append(
                "<a href=\"" + sharedConstant.getChannelViewUrl(node_id) + "\">目录</a>");
        }

        ContentDetailVO nextContent = iContentService.getNextContent(node_id, inputDate,
            contentId);

        if (nextContent != null)
        {

            String title = nextContent.getTitle();
            ContentObj content = new ContentObj(nextContent);
            String url = content.getUrl();
            String word = "";
            if (languageId == 30)
            {
                word = "下一章";
            }
            else if (languageId == 6)
            {
                word = "المقالة التالية";
            }
            else
            {
                word = "下一篇";
            }
            res.append("</br><a href=\"" + sharedConstant.getContentViewUrl(content.getContentLogicId()) + "\"><span>"
                       + word + "</span>" + title + "</a></div>");
        }
        else
        {
            res.append("</div>");
        }
        return res.toString();
    }

    // add by dengjun begin 20100818
    private String getRmdsContent()
    {
        StringBuffer buff = new StringBuffer();
        String content = Encode.htmlsp(contentObj.getContent());
        List ds = getRelateChannelsContent();
        String rt = "";
        if (ds != null)
        {
            if (ds.size() > 0)
            {
                Channel channel = (Channel)ds.get(0);
                rt = GetChannelLink_blank(channel.getLogicId(),channel.getChannelName(), null);
            }
        }
        if (!content.equals(""))
        {
            if (!rt.equals(""))
            {
                buff.append("<h2><em>视频介绍</em><i>进入《" + rt + "》专题</i></h2>");
            }
            else
            {
                buff.append("<h2><em>视频介绍</em></h2>");
            }
            buff.append("<p>");
            buff.append(getContentText());
            buff.append("</p>");
        }
        return buff.toString();
    }

    private String getBbsList()
    {
        StringBuffer result = new StringBuffer();
        result.append("<!--留言板块-->");
        result.append("<div class=\"note_list clearfix\">");
        result.append("<div class=\"note_t clearfix\">");
        result.append("<ul>");
        result.append("<li id=\"news_pinglun\" class=\"focus\">最新评论</li>");
        result.append("<li id=\"hot_pinglun\">热门评论</li>");
        result.append("</ul>");

        // result.append("<span>热词:<a
        // href=\"http://www.people.com.cn/GB/32306/166587/362774/index.html\"
        // target=\"_blank\">祈福雅安</a><a
        // href=\"http://www.people.com.cn/32306/245336/362822/index.html\"
        // target=\"_blank\">芦山挺住</a><a
        // href=\"http://liaoba.people.com.cn/topic.html?method=query&tb_code=2039\"
        // target=\"_blank\">寻亲互助</a><a
        // href=\"http://bbs1.people.com.cn/post/1/1/2/129149312.html\"
        // target=\"_blank\">再信一次红十字会</a></span>");
        result.append("<span id=\"hot_key\"></span>");

        result.append("<a href=\"\" id=\"all_link\" target=\"_blank\">查看全部留言</a>");
        result.append("</div>");
        result.append("<div class=\"note_list_c clearfix\" id=\"note_list_c\">");

        result.append("</div>");
        result.append("</div>");
        if (SharedConstant.SYSTEM_NAME.equals("LOCAL"))
        {
            result.append(
                "<script type=\"text/javascript\" src=\"/img/2012wb/jquery.note.local.js\"></script>");
        }
        else
        {
            result.append(
                "<script type=\"text/javascript\" src=\"/img/2012wb/jquery.note.min.js\"></script>");
        }
        result.append("<script type=\"text/javascript\">");
        result.append("$(document).ready(function() {");
        result.append("$.show_note_list('" + contentId + "','" + contentId + "');");
        result.append("});");
        result.append("</script>");
        result.append("<!--结束留言板块-->");
        return result.toString();
    }

    public ContentMedia getNextContentMedia(ContentObj contentObj)
    {
        ContentMedia pojo_ContentMedia = null;
        Date d = contentObj.getInputDate();
        Date bfDate = getDateBefore(d, 30);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String before = df.format(bfDate);

        ContentDetailRequestVO searchParam = new ContentDetailRequestVO();
        searchParam.setChannelLogicIdInStr("" + contentObj.getNodeId());
        searchParam.setDstatus(Content.DSTATUS_PUBLISHED);
        searchParam.setMediaImgCount(1);
        searchParam.setCreatetimeStart(df.format(contentObj.getInputDate()));
        searchParam.setSendtimeStart(before);
        searchParam.setOrderByStr(" createtime desc ");
        searchParam.setLimit(1);

        List<ContentDetailVO> list = iContentService.listContentDetails(searchParam);
        if (list != null)
        {
            if (list.size() > 0)
            {
                ContentDetailVO pojo_Content = list.get(0);
                QueryWrapper<ContentMedia> queryWrapper = new QueryWrapper<ContentMedia>();
                queryWrapper.eq("content_id", pojo_Content.getId());
                queryWrapper.orderByAsc("level", "id");

                List<ContentMedia> list1 = iContentMediaService.list(queryWrapper);
                if (list1 != null)
                {
                    if (list1.size() > 0)
                    {
                        pojo_ContentMedia = (ContentMedia)list1.get(0);
                        pojo_ContentMedia.setMediaName(pojo_Content.getTitle());
                    }
                }
            }
        }
        return pojo_ContentMedia;
    }

    public static Date getDateBefore(Date d, int day)
    {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    // 增加参数表示不同的广告地址
    public String getAdCode(int adNo)
    {
        return "暂不支持广告地址";

        /*
         * ReadDBTool dao = (ReadDBTool)SpringContextUtil.getBean("ReadDBTool"); int nodeId =
         * contentObj.getNodeId(); Channel channel = (Channel)dao.getById(Channel.class, nodeId);
         * ContentTool ct = new ContentTool(); String adv = ""; if (channel != null) { String
         * parent_string = channel.getParentString() + nodeId; String[] parent_arr =
         * parent_string.split(","); String first = ""; String second = ""; String third = "";
         * String fourth = ""; String local = "homepage"; for (int i = 0; i < parent_arr.length;
         * i++ ) { if (i == 0) { first = parent_arr[i]; } if (i == 1) { second = parent_arr[i]; }
         * if (i == 2) { third = parent_arr[i]; } if (i == 3) { fourth = parent_arr[i]; } } if
         * (!second.equals("1001")) { local = ct.getAdKey(second, third); String result =
         * "&kv=people|" + local + ";third|" + third + ";fourth|" + fourth + ";newsid|" +
         * contentId; String src =
         * "http://pmm.people.com.cn/main/s?user=people|nonechannel|pip03&db=people&border=0&local=yes&js=ie";
         * if (adNo == 1) { src =
         * "http://pmm.people.com.cn/main/s?user=people|2016passage|B7&db=people&border=0&local=yes&js=ie";
         * } adv = "<div class=\"ad_hh\"><script type='text/javascript' src='" + src + result +
         * "' charset='gbk'></script></div>"; } } return adv;
         */

    }

    // 增加参数表示不同的广告地址
    public String getLongContentAd(String rt, int adNo)
    {
        HtmlToText ht = new HtmlToText();
        String txt = ht.html2Text(rt);
        int len = txt.length();
        if (len < longContentLen)
        {
            return rt;
        }
        else
        {
            String news = "";
            // String regex = "<p.*?>(.*?)</p>";
            String regex = "<p.*?>\\s.*</p>";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(rt);
            ArrayList<String> pages = new ArrayList<String>();
            int totalPic = 0;
            while (m.find())
            {
                String mStr = m.group(0);
                if (mStr.contains("img"))
                {
                    totalPic++ ;
                }
                pages.add(mStr);
            }
            if (pages.size() > 0)
            {
                if (pages.size() >= longContentSection && pages.size() > 3 && totalPic < 3)
                {
                    String t1 = pages.get(pages.size() - 1);
                    String t2 = pages.get(pages.size() - 2);
                    String t3 = pages.get(pages.size() - 3);
                    if (!t1.contains("img") && !t2.contains("img") && !t3.contains("img"))
                    {
                        String adv = getAdCode(adNo);
                        pages.add(pages.size() - 3, adv);
                    }
                }
                for (int i = 0; i < pages.size(); i++ )
                {
                    String temp = pages.get(i);
                    news += temp;
                }
            }
            else
            {
                news = rt;
            }
            return news;
        }
    }

    public String getVideoUrl()
    {
        return "暂不支持视频";
        /*
         * String re = contentObj.getSummary(); String contentMax = contentObj.getContentMark(); if
         * (contentMax.startsWith("1")) { DBDao dao = (DBDao)SpringContextUtil.getBean("DBDao");
         * String queryString = "from POJO_ContentExtend where contentId=" +
         * contentObj.getContentId(); List list = dao.findHQL(queryString,
         * contentObj.getContentId()); if (list != null) { if (list.size() > 0) {
         * POJO_ContentExtend contentExtend = (POJO_ContentExtend)list.get(0); re =
         * contentExtend.getVideoUrl(); } } } return re;
         */
    }

    public String getVideoTime()
    {
        return "暂不支持视频";
        /*
         * String re = ""; String contentMax = contentObj.getContentMark(); if
         * (contentMax.startsWith("1")) { DBDao dao = (DBDao)SpringContextUtil.getBean("DBDao");
         * String queryString = "from POJO_ContentExtend where contentId=" +
         * contentObj.getContentId(); List list = dao.findHQL(queryString,
         * contentObj.getContentId()); if (list != null) { if (list.size() > 0) {
         * POJO_ContentExtend contentExtend = (POJO_ContentExtend)list.get(0); re =
         * StringTool.getNotNullString(contentExtend.getVideoTime()); } } } return re;
         */
    }

    public String getPreTitle()
    {
        String preTitle = contentObj.getPretitle();
        String re = "";
        if (preTitle == null)
        {
            return re;
        }
        else
        {
            re = Encode.htmlsp(preTitle.trim());
        }
        return re;
    }

    public String getPreTitle_nospace()
    {
        String preTitle = contentObj.getPretitle();
        String re = "";
        if (preTitle == null)
        {
            return re;
        }
        else
        {
            re = Encode.htmlsp_nospace(preTitle.trim());
        }
        return re;
    }

    public String getNodeName()
    {
        Integer nodeId = contentObj.getNodeId();
        Channel channel = iChannelService.getByLogicId(nodeId);
        String re = channel.getChannelName();
        return re;
    }

    public String getPicUrl()
    {
        List newsImages = getContentImages(String.valueOf(contentId));
        String url = "";
        if (newsImages != null && newsImages.size() > 0)
        {
            ContentMedia POJO_ContentMedia = (ContentMedia)newsImages.get(0);
            url = POJO_ContentMedia.getPath();
            if (!url.contains("http"))
            {
                url = "http://www.people.com.cn" + url;
            }
        }
        return url;
    }

    public String getPicScroll()
    {
        List newsImages = getContentImages(String.valueOf(contentId));
        StringBuffer sb = new StringBuffer();
        if (newsImages != null && newsImages.size() > 0)
        {
            for (int i = 0; i < newsImages.size(); i++ )
            {
                ContentMedia POJO_ContentMedia = (ContentMedia)newsImages.get(i);
                String path = POJO_ContentMedia.getPath();
                String desc = POJO_ContentMedia.getMediaDesc();
                if (desc == null || desc.equals(""))
                {
                    desc = contentObj.getTitle();
                }
                sb.append("<li>");
                sb.append("<a href=\"\" target=_blank><img src=\"" + path
                          + "\" width=\"700\" border=\"0\" height=\"460\" alt=\"\"/>");
                sb.append("<div>" + desc + "</div>");
                sb.append("</a>");
                sb.append("</li>");
            }
        }
        return sb.toString();
    }
    
    
    public  String getImgNextPage(int page,int pageCount, Integer contentId, String rt){ 
        if(pageCount!=1&&page<pageCount){
            int nextPage = page+1;
            String regex = "<\\s*img\\s+([^>]*)\\s*>";
            final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
            final Matcher ma = pa.matcher(rt); 
            while (ma.find()){
                String img = ma.group(); 
                String imgRegx = img.replace("(","\\(");
                imgRegx = imgRegx.replace(")", "\\)");
                imgRegx = imgRegx.replace("{", "\\{");
                imgRegx = imgRegx.replace("}", "\\}");
                String regex1 = "<a\\s.*?href=\"([^\"]+)\"[^>]*>\\s*"+imgRegx+"\\s*</a>";
                final Pattern pa1 = Pattern.compile(regex1, Pattern.DOTALL);
                final Matcher ma1 = pa1.matcher(rt);
                if(!ma1.find()){
                    String imghref = "<a href=\""+sharedConstant.getContentViewUrl(contentId)+"?page="+nextPage+"\">"+img+"</a>";
                    rt = rt.replace(img, imghref);
                }           
            }
        }   
        return rt;
    }
    
    public String getPageStr(Integer pageType,int pageCount,int page,Integer contentId){
        String pageStr = "";
        if(pageType!=null&&pageCount>1){
            pageStr = "<div class=\"zdfy clearfix\">";
            for(int i=1;i<=pageCount;i++){
                pageStr = pageStr+"<a href=\""+sharedConstant.getContentViewUrl(contentId)+"?page="+i+"\">【"+i+"】</a>";
            }
            pageStr = pageStr+"</div>";         
            pageStr = pageStr+"<center><table border=\"0\" align=\"center\" width=\"40%\"><tr>";                
            if(page>1){
                int prePage = page-1;
                pageStr = pageStr+"<td width=\"50%\" align=\"center\">";
                pageStr = pageStr+"<a href=\""+sharedConstant.getContentViewUrl(contentId)+"?page="+prePage+"\">上一页</a>"; 
                pageStr = pageStr+"</td>";
            }               
            if(page<pageCount){
                int nextPage = page+1;
                pageStr = pageStr+"<td width=\"50%\" align=\"center\">";
                pageStr = pageStr+"<a href=\""+sharedConstant.getContentViewUrl(contentId)+"?page="+nextPage+"\">下一页</a>";
                pageStr = pageStr+"</td>";
            }
            pageStr = pageStr+"</tr></table></center>";         
        }   
        return pageStr;
    }
}
