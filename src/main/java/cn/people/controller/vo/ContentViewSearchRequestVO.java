package cn.people.controller.vo;

import java.io.Serializable;
import java.util.List;


/**
 * @author guling
 * @ClassName: ContentViewSearchRequestVO
 * @Description: 查询入参@(这里用一句话描述这个类的作用)
 * @date 2019/1/16 13:58
 */
public class ContentViewSearchRequestVO implements Serializable
{
    List<String> NodeList;

    List<String> contentIdList;

    //新闻_自动新闻_过滤新闻ID
    List<String> getContentIdList1;

    public List<String> getNodeList()
    {
        return NodeList;
    }

    public void setNodeList(List<String> nodeList)
    {
        NodeList = nodeList;
    }

    public List<String> getContentIdList()
    {
        return contentIdList;
    }

    public void setContentIdList(List<String> contentIdList)
    {
        this.contentIdList = contentIdList;
    }

    public List<String> getGetContentIdList1()
    {
        return getContentIdList1;
    }

    public void setGetContentIdList1(List<String> getContentIdList1)
    {
        this.getContentIdList1 = getContentIdList1;
    }

    @Override public String toString()
    {
        return "ContentViewSearchRequestVO{" + "NodeList=" + NodeList + ", contentIdList="
               + contentIdList + ", getContentIdList1=" + getContentIdList1 + '}';
    }
}
