/**   
* @Title: SplitListVO.java 
* @Package cn.people.controller.vo 
* @Description: 碎片列表查询  
* @author shidandan
* @date 2018年12月19日 下午2:07:10 
* @version V1.0   
*/
package cn.people.controller.vo;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: SplitListVO 
* @Description: 碎片列表查询 
* @author shidandan
* @date 2018年12月19日 下午2:07:10 
*  
*/
@ApiModel(value="碎片列表查询", description="碎片列表查询")
public class SplitPageVO extends PageVO
{
    private static final long serialVersionUID = -3939615992797718984L;
    
    @ApiModelProperty(value = "碎片名称")
    private String splitnameOrId;

    @ApiModelProperty(value = "碎片类型。 1 自动碎片，2手动碎片，3静态碎片")
    private Integer type;
    
    @ApiModelProperty(value = "创建人")
    private String createrId;
    
    @ApiModelProperty(value = "创建人")
    private Set<String> createrIds;
    
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Set<String> getCreaterIds()
    {
        return createrIds;
    }

    public void setCreaterIds(Set<String> createrIds)
    {
        this.createrIds = createrIds;
    }

    public String getSplitnameOrId()
    {
        return splitnameOrId;
    }

    public void setSplitnameOrId(String splitnameOrId)
    {
        this.splitnameOrId = splitnameOrId;
    }

    public String getCreaterId()
    {
        return createrId;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public void setCreaterId(String createrId)
    {
        this.createrId = createrId;
    }
}
