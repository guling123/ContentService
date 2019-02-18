/**   
* @Title: ChannelTreeVO.java 
* @Package cn.people.controller.vo 
* @Description: 渠道树 
* @author shidandan
* @date 2019年1月3日 上午10:12:07 
* @version V1.0   
*/
package cn.people.controller.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/** 
* @ClassName: ChannelTreeVO 
* @Description:  渠道树
* @author guling
* @date 2019年1月9日 13点56分 
*  
*/
public class ChannelTreeVO implements Serializable
{

    private static final long serialVersionUID = -707847564457723174L;
    @ApiModelProperty(value = "栏目名称")
    private String channelname;

    @ApiModelProperty(value = "栏目id")
    private String channelid;
    
    @ApiModelProperty(value = "父栏目id")
    private String parentid;

    @ApiModelProperty(value = "栏目显示，0隐藏，1显示")
    private Integer display;

    @ApiModelProperty(value = "栏目类型 ，1 栏目，2专题")
    private Integer dtype;

    @ApiModelProperty(value = "创建人id")
    private String createrId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "下级节点列表")
    List<ChannelTreeVO> subList=new ArrayList<>();

    public String getChannelname()
    {
        return channelname;
    }

    public void setChannelname(String channelname)
    {
        this.channelname = channelname;
    }

    public String getChannelid()
    {
        return channelid;
    }

    public void setChannelid(String channelid)
    {
        this.channelid = channelid;
    }

    public String getParentid()
    {
        return parentid;
    }

    public void setParentid(String parentid)
    {
        this.parentid = parentid;
    }

    public List<ChannelTreeVO> getSubList()
    {
        return subList;
    }

    public void setSubList(List<ChannelTreeVO> subList)
    {
        this.subList = subList;
    }

    public Integer getDisplay()
    {
        return display;
    }

    public void setDisplay(Integer display)
    {
        this.display = display;
    }

    public Integer getDtype()
    {
        return dtype;
    }

    public void setDtype(Integer dtype)
    {
        this.dtype = dtype;
    }

    public String getCreaterId()
    {
        return createrId;
    }

    public void setCreaterId(String createrId)
    {
        this.createrId = createrId;
    }

    public LocalDateTime getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime)
    {
        this.createTime = createTime;
    }
}
