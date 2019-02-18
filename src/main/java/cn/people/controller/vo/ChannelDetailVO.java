/**   
* @Title: ChannelDetailVO.java 
* @Package cn.people.controller.vo 
* @Description: 栏目详情 
* @author shidandan
* @date 2018年12月20日 下午1:28:40 
* @version V1.0   
*/
package cn.people.controller.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;


/** 
* @ClassName: ChannelDetailVO 
* @Description: 栏目详情
* @author shidandan
* @date 2018年12月20日 下午1:28:40 
*  
*/
@ApiModel(value = "栏目详情", description = "栏目详情")
public class ChannelDetailVO extends ChannelCreateVO
{

    private static final long serialVersionUID = -3105787413734970719L;
    
    @ApiModelProperty(value = "栏目id")
    private String id;
    
    @ApiModelProperty(value = "逻辑ID 4 位数值，100000起,站点ID内不重复 （暂时不用）")
    private Integer logicId;

    @ApiModelProperty(value = "栏目模板名称")
    private String channelModelName;

    @ApiModelProperty(value = "图文内容模板")
    private String contentModelName;

    @ApiModelProperty(value = "图集内容模板名称")
    private String imagesModelName;
    
    @ApiModelProperty(value = "栏目URL")
    private String channelUrl;
    //备用
   /* @ApiModelProperty(value = "状态")
    private Integer statusRelease;

    @ApiModelProperty(value = "创建人id")
    private String createrId;

    @ApiModelProperty(value = "状态。1未启用，2已启用，3停用")
    private Integer dstatus;*/

    public String getId()
    {
        return id;
    }

    public String getChannelUrl()
    {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl)
    {
        this.channelUrl = channelUrl;
    }

    public Integer getLogicId()
    {
        return logicId;
    }

    public void setLogicId(Integer logicId)
    {
        this.logicId = logicId;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getChannelModelName()
    {
        return channelModelName;
    }

    public void setChannelModelName(String channelModelName)
    {
        this.channelModelName = channelModelName;
    }

    public String getContentModelName()
    {
        return contentModelName;
    }

    public void setContentModelName(String contentModelName)
    {
        this.contentModelName = contentModelName;
    }

    public String getImagesModelName()
    {
        return imagesModelName;
    }

    public void setImagesModelName(String imagesModelName)
    {
        this.imagesModelName = imagesModelName;
    }
}
