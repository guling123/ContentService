package cn.people.controller.vo;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
* @ClassName: SplitDetailVO 
* @Description: 碎片详情反参对象
* @author zhangchengchun
* @date 2018年12月7日 上午9:42:41 
*  
 */
@ApiModel(value="碎片详情反参对象", description="碎片详情反参对象")
public class ModelSplitDetailVO implements Serializable
{
    private static final long serialVersionUID = -6706748050012067723L;

	@ApiModelProperty(value = "id")
	private  String id;

    @ApiModelProperty(value = "碎片id")
    private  String splitId;
    
    @ApiModelProperty(value = "碎片类型")
    private Integer type;
    
    @ApiModelProperty(value = "碎片名称")
    private String splitName;

    @ApiModelProperty(value = "主控栏目")
    private String channelId;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getSplitId()
	{
		return splitId;
	}

	public void setSplitId(String splitId)
	{
		this.splitId = splitId;
	}

	public Integer getType()
	{
		return type;
	}

	public void setType(Integer type)
	{
		this.type = type;
	}

	public String getSplitName()
	{
		return splitName;
	}

	public void setSplitName(String splitName)
	{
		this.splitName = splitName;
	}

	public String getChannelId()
	{
		return channelId;
	}

	public void setChannelId(String channelId)
	{
		this.channelId = channelId;
	}
}
