/**   
* @Title: ChannelVO.java 
* @Package cn.people.controller.vo 
* @Description: 栏目信息 
* @author shidandan
* @date 2018年12月17日 下午4:57:47 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ChannelVO 
* @Description: 栏目信息
* @author shidandan
* @date 2018年12月17日 下午4:57:47 
*  
*/
@ApiModel(value = "栏目信息", description = "栏目信息")
public class ChannelVO implements Serializable
{

    private static final long serialVersionUID = -2351097770450532578L;
    @ApiModelProperty(value = "栏目名称")
    private String channelname;

    @ApiModelProperty(value = "栏目id")
    private String channelid;
    
    @ApiModelProperty(value = "父栏目id")
    private String parentid;
    
    @ApiModelProperty(value = "是否选中")
    private boolean checked=false;
    
    @ApiModelProperty(value = "下级节点列表")
    List<ChannelVO> subList=new ArrayList<>();

    public String getChannelname()
    {
        return channelname;
    }

    public boolean getChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
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
}
