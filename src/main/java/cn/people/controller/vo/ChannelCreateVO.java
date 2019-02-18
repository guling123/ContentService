/**   
* @Title: ChannelCreateVO.java 
* @Package cn.people.controller.vo 
* @Description: 新增子栏目 
* @author shidandan
* @date 2018年12月20日 上午11:11:04 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ChannelCreateVO 
* @Description: 新增子栏目
* @author shidandan
* @date 2018年12月20日 上午11:11:04 
*  
*/
@ApiModel(value = "新增子栏目", description = "新增子栏目")
public class ChannelCreateVO implements Serializable
{

    private static final long serialVersionUID = -235748225392009549L;


    @ApiModelProperty(value = "站点id")
    private String siteid;

    //@NotBlank(message="父栏目id不能为空")
    @ApiModelProperty(value = "父栏目id")
    private String parentId;

    @NotBlank(message="栏目名称不能为空")
    @ApiModelProperty(value = "栏目名称")
    private String channelName;

    @ApiModelProperty(value = "栏目类型 ，1 栏目，2专题")
    private Integer dtype;

    @ApiModelProperty(value = "栏目显示，0隐藏，1显示")
    private Integer display;

    @ApiModelProperty(value = "栏目头图图片url")
    private String imageUrl;

    @ApiModelProperty(value = "栏目描述")
    private String description;

    @ApiModelProperty(value = "栏目页模板id")
    private String channelModelId;

    @ApiModelProperty(value = "图文内容模板id")
    private String contentModelId;

    @ApiModelProperty(value = "图集内容模板id")
    private String imagesModelId;

    @ApiModelProperty(value = "栏目路径")
    private String domain;
    
    @ApiModelProperty(value = "URL路径")
    private String url;

    @ApiModelProperty(value = "栏目标识")
    private String ident;

    @ApiModelProperty(value = "创建人id")
    private String createrId;

    public String getCreaterId()
    {
        return createrId;
    }

    public void setCreaterId(String createrId)
    {
        this.createrId = createrId;
    }

    public String getSiteid()
    {
        return siteid;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setSiteid(String siteid)
    {
        this.siteid = siteid;
    }

    public String getChannelName()
    {
        return channelName;
    }

    public void setChannelName(String channelName)
    {
        this.channelName = channelName;
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

    public void setDisplay(Integer display)
    {
        this.display = display;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getDomain()
    {
        return domain;
    }

    public void setDomain(String domain)
    {
        this.domain = domain;
    }

    public String getIdent()
    {
        return ident;
    }

    public void setIdent(String ident)
    {
        this.ident = ident;
    }

    public String getChannelModelId()
    {
        return channelModelId;
    }

    public void setChannelModelId(String channelModelId)
    {
        this.channelModelId = channelModelId;
    }

    public String getContentModelId()
    {
        return contentModelId;
    }

    public void setContentModelId(String contentModelId)
    {
        this.contentModelId = contentModelId;
    }

    public String getImagesModelId()
    {
        return imagesModelId;
    }

    public void setImagesModelId(String imagesModelId)
    {
        this.imagesModelId = imagesModelId;
    }
}
