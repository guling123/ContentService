/**   
* @Title: SplitCreateVO.java 
* @Package cn.people.controller.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author shidandan
* @date 2018年12月20日 下午5:16:34 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: SplitCreateVO 
* @Description: 创建碎片参数
* @author shidandan
* @date 2018年12月20日 下午5:16:34 
*  
*/
@ApiModel(value = "创建碎片参数", description = "创建碎片参数")
public class SplitCreateVO implements Serializable
{

    private static final long serialVersionUID = -836712022736262553L;
    @NotBlank(message="模板ID不能为空")
    @ApiModelProperty(value = "模板ID")
    private String modelid;
    
    @ApiModelProperty(value = "碎片Id")
    private String splitId;
    
    @NotBlank(message="碎片名称不能为空")
    @ApiModelProperty(value = "碎片名称")
    private String splitName;
    
    @ApiModelProperty(value = "创建人")
    private String createrId;
    
    @ApiModelProperty(value = "主控栏目")
    private String channelId;
    public String getModelid()
    {
        return modelid;
    }
   
	public String getChannelId() {
		return channelId;
	}
	
	public String getSplitName()
    {
        return splitName;
    }

    public void setSplitName(String splitName)
    {
        this.splitName = splitName;
    }

    public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public void setModelid(String modelid)
    {
        this.modelid = modelid;
    }
  
    public String getCreaterId()
    {
        return createrId;
    }
    public void setCreaterId(String createrId)
    {
        this.createrId = createrId;
    }

    public String getSplitId()
    {
        return splitId;
    }

    public void setSplitId(String splitId)
    {
        this.splitId = splitId;
    }
   
}
