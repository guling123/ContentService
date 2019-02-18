/**   
* @Title: ModelSplitVO.java 
* @Package cn.people.controller.vo 
* @Description: 模板碎片
* @author shidandan
* @date 2019年1月22日 下午4:10:10 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ModelSplitVO 
* @Description: 模板碎片
* @author shidandan
* @date 2019年1月22日 下午4:10:10 
*  
*/
@ApiModel(value="模板碎片", description="模板碎片")
public class ModelSplitVO implements Serializable
{
    private static final long serialVersionUID = -8208426217923330793L;
    @ApiModelProperty(value = "id")
    private String id;
    
    @ApiModelProperty(value = "逻辑ID 6 位数值，100000起,站点ID内不重复 （暂时不用） 暂以TAG_id_TAG 形式定位")
    private Integer logicId;

    @ApiModelProperty(value = "模板id")
    private String modelId;

    @ApiModelProperty(value = "碎片ID")
    private String splitId;

    @ApiModelProperty(value = "碎片名称")
    private String splitName;
    
    @ApiModelProperty(value = "碎片类型")
    private Integer type;
    
    @ApiModelProperty(value = "碎片类型")
    private String typeName;

    @ApiModelProperty(value = "栏目id")
    private String channelId;

    @ApiModelProperty(value = "引用时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createId;

    public Integer getLogicId()
    {
        return logicId;
    }

    public void setLogicId(Integer logicId)
    {
        this.logicId = logicId;
    }

    public String getModelId()
    {
        return modelId;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setModelId(String modelId)
    {
        this.modelId = modelId;
    }

    public String getSplitId()
    {
        return splitId;
    }

    public void setSplitId(String splitId)
    {
        this.splitId = splitId;
    }

    public String getSplitName()
    {
        return splitName;
    }

    public void setSplitName(String splitName)
    {
        this.splitName = splitName;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getChannelId()
    {
        return channelId;
    }

    public void setChannelId(String channelId)
    {
        this.channelId = channelId;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getCreateId()
    {
        return createId;
    }

    public void setCreateId(String createId)
    {
        this.createId = createId;
    }
    
}
