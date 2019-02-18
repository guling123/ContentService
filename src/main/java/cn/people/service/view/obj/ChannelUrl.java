package cn.people.service.view.obj;

/**
 * ChannelUrl entity. @author MyEclipse Persistence Tools
 */

public class ChannelUrl implements java.io.Serializable
{
    private Integer nodeId;

    private String urlPath;

    private String domain;

    public ChannelUrl(){}


    public Integer getNodeId()
    {
        return this.nodeId;
    }

    public void setNodeId(Integer nodeId)
    {
        this.nodeId = nodeId;
    }

    public String getUrlPath()
    {
        return urlPath;
    }


    public void setUrlPath(String urlPath)
    {
        this.urlPath = urlPath;
    }


    public String getDomain()
    {
        return this.domain;
    }

    public void setDomain(String domain)
    {
        this.domain = domain;
    }
}