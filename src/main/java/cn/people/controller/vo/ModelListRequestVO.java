package cn.people.controller.vo;


import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @ClassName: ModelListRequestVo 
 * @Description: 查询模板列表入参
 * @author zhangchengchun
 * @date 2018年12月6日 上午10:41:09    
 */
@ApiModel(value = "查询模板列表入参对象", description = "查询模板列表入参对象")
public class ModelListRequestVO extends PageVO
{
    private static final long serialVersionUID = 2034285051905629376L;

    @ApiModelProperty(value = "模板名称/ID", notes = "模板名称/ID")
    private String nameOrid;

    @ApiModelProperty(value = "模板类型")
    private String typeId;

    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    @ApiModelProperty(value = "创建人ids")
    private Set<String> createIds;

    public Set<String> getCreateIds()
    {
        return createIds;
    }

    public void setCreateIds(Set<String> createIds)
    {
        this.createIds = createIds;
    }

    public String getCreateName()
    {
        return createName;
    }

    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getNameOrid()
    {
        return nameOrid;
    }

    public void setNameOrid(String nameOrid)
    {
        this.nameOrid = nameOrid;
    }

    public String getTypeId()
    {
        return typeId;
    }

    public void setTypeId(String typeId)
    {
        this.typeId = typeId;
    }
}
