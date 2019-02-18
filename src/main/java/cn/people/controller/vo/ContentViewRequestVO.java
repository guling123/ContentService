package cn.people.controller.vo;

/**
 * 查询自动新闻查询条件入参
* @ClassName: ContentViewRequestVO 
* @Description: 查询自动新闻查询条件入参
* @author zhangchengchun
* @date 2019年1月14日 下午6:00:54 
*  
 */
public class ContentViewRequestVO
{
    private Integer dstatus;
    private String channelLogicIdInStr;//栏目id字符串
    private String startTime;
    private String endTime;
    private String contentIdNotInStr1;
    private String contentIdNotInStr2;//新闻_自动新闻_过滤新闻ID
    private Boolean onlyImageNews;//新闻_自动新闻_是否只显示图片新闻
    private String orderBy;
    private Integer limit;
    public Integer getDstatus()
    {
        return dstatus;
    }
    public void setDstatus(Integer dstatus)
    {
        this.dstatus = dstatus;
    }
    public String getChannelLogicIdInStr()
    {
        return channelLogicIdInStr;
    }
    public void setChannelLogicIdInStr(String channelLogicIdInStr)
    {
        this.channelLogicIdInStr = channelLogicIdInStr;
    }
    public String getStartTime()
    {
        return startTime;
    }
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
    public String getEndTime()
    {
        return endTime;
    }
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    public String getContentIdNotInStr1()
    {
        return contentIdNotInStr1;
    }
    public void setContentIdNotInStr1(String contentIdNotInStr1)
    {
        this.contentIdNotInStr1 = contentIdNotInStr1;
    }
    public String getContentIdNotInStr2()
    {
        return contentIdNotInStr2;
    }
    public void setContentIdNotInStr2(String contentIdNotInStr2)
    {
        this.contentIdNotInStr2 = contentIdNotInStr2;
    }
    public Boolean getOnlyImageNews()
    {
        return onlyImageNews;
    }
    public void setOnlyImageNews(Boolean onlyImageNews)
    {
        this.onlyImageNews = onlyImageNews;
    }
    public String getOrderBy()
    {
        return orderBy;
    }
    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }
    public Integer getLimit()
    {
        return limit;
    }
    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }    
}
