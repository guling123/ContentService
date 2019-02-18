/**   
* @Title: ChannelTreeVO.java 
* @Package cn.people.controller.vo 
* @Description: 渠道树 
* @author shidandan
* @date 2019年1月3日 上午10:12:07 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ChannelTreeVO 
* @Description:  渠道树
* @author shidandan
* @date 2019年1月3日 上午10:12:07 
*  
*/
public class ChannelListTreeVO implements Serializable
{

    private static final long serialVersionUID = -707847564457723174L;
    @ApiModelProperty(value = "栏目名称")
    private String channelName;

    @ApiModelProperty(value = "栏目id")
    private String id;
    
    @ApiModelProperty(value = "逻辑ID 4 位数值，100000起,站点ID内不重复 （暂时不用）")
    private Integer logicId;
    
    @ApiModelProperty(value = "父栏目id")
    private String parentId;
    
    @ApiModelProperty(value = "栏目类型 ，1 栏目，2专题")
    private Integer dtype;

    @ApiModelProperty(value = "栏目显示，0隐藏，1显示")
    private Integer display;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人id")
    private String createrId;
    
    @ApiModelProperty(value = "下级节点列表")
    List<ChannelListTreeVO> children=new ArrayList<>();


    public Integer getLogicId()
    {
        return logicId;
    }

    public void setLogicId(Integer logicId)
    {
        this.logicId = logicId;
    }

    public Integer getDtype()
    {
        return dtype;
    }

    public void setDtype(Integer dtype)
    {
        this.dtype = dtype;
    }

    public Integer getDisplay()
    {
        return display;
    }

    public String getChannelName()
    {
        return channelName;
    }

    public void setChannelName(String channelName)
    {
        this.channelName = channelName;
    }


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public void setDisplay(Integer display)
    {
        this.display = display;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getCreaterId()
    {
        return createrId;
    }

    public void setCreaterId(String createrId)
    {
        this.createrId = createrId;
    }

    public List<ChannelListTreeVO> getChildren()
    {
        return children;
    }

    public void setChildren(List<ChannelListTreeVO> children)
    {
        this.children = children;
    }
   
}
