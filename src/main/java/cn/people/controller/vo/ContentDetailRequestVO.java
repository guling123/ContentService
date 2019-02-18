package cn.people.controller.vo;

/**
 * 查询ContentDetail数据对象的过滤条件封装
* @ClassName: ContentDetailRequestVO 
* @Description:查询ContentDetail数据对象的过滤条件封装，非空的参数都会传递到查询语句的where条件中
* @author zhangchengchun
* @date 2019年1月15日 上午10:26:09 
*  
 */
public class ContentDetailRequestVO
{
    private Integer dstatus;//content的状态
    private String channelLogicIdInStr;//限定所属的栏目，是一个已英文逗号分隔的栏目逻辑id字符串
    private String createtimeStart;//content的创建时间需要大于这个时间
    private String createtimeStop;//content的创建时间需要小于这个时间
    private Integer limit;//记录查询上线
    private String orderByStr;//排序字段  eg:“ id desc ”
    private Integer mediaImgCount;//媒体图片数量   大于这个值
    private String sendtimeStart;//content的上线时间需要大于这个时间
        
    public String getSendtimeStart()
    {
        return sendtimeStart;
    }
    public void setSendtimeStart(String sendtimeStart)
    {
        this.sendtimeStart = sendtimeStart;
    }
    public Integer getMediaImgCount()
    {
        return mediaImgCount;
    }
    public void setMediaImgCount(Integer mediaImgCount)
    {
        this.mediaImgCount = mediaImgCount;
    }
    public String getOrderByStr()
    {
        return orderByStr;
    }
    public void setOrderByStr(String orderByStr)
    {
        this.orderByStr = orderByStr;
    }
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
    public String getCreatetimeStart()
    {
        return createtimeStart;
    }
    public void setCreatetimeStart(String createtimeStart)
    {
        this.createtimeStart = createtimeStart;
    }
    public String getCreatetimeStop()
    {
        return createtimeStop;
    }
    public void setCreatetimeStop(String createtimeStop)
    {
        this.createtimeStop = createtimeStop;
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
