/**   
* @Title: SplitContentFetchVO.java 
* @Package cn.people.controller.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author shidandan
* @date 2018年12月21日 下午1:56:51 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: SplitContentFetchVO 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author shidandan
* @date 2018年12月21日 下午1:56:51 
*  
*/
public class SplitContentFetchVO implements Serializable
{

    private static final long serialVersionUID = 3898881274333975273L;
    @ApiModelProperty(value = "碎片自动抓取栏目")
    private String channelid;

    @ApiModelProperty(value = "忽略条数")
    private Integer ignorenum;

    @ApiModelProperty(value = "抓取条数")
    private Integer fetchnum;
    
    @ApiModelProperty(value = "内容类型。1图文、2图集、3点播、4直播")
    private Integer contentdtype;
    
    @ApiModelProperty(value = "添加人id")
    private String createrid;

    public String getChannelid()
    {
        return channelid;
    }

    public String getCreaterid()
    {
        return createrid;
    }

    public void setCreaterid(String createrid)
    {
        this.createrid = createrid;
    }

    public void setChannelid(String channelid)
    {
        this.channelid = channelid;
    }

    public Integer getIgnorenum()
    {
        return ignorenum;
    }

    public void setIgnorenum(Integer ignorenum)
    {
        this.ignorenum = ignorenum;
    }

    public Integer getFetchnum()
    {
        return fetchnum;
    }

    public void setFetchnum(Integer fetchnum)
    {
        this.fetchnum = fetchnum;
    }

    public Integer getContentdtype()
    {
        return contentdtype;
    }

    public void setContentdtype(Integer contentdtype)
    {
        this.contentdtype = contentdtype;
    }
}
