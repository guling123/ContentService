package cn.people.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查询栏目的模板中的标记集合反参对象
* @ClassName: ModelSplitMapResponseVO 
* @Description: 查询栏目的模板中的标记集合反参对象
* @author zhangchengchun
* @date 2019年1月16日 上午10:27:29 
*  
 */
@ApiModel(value = "查询栏目的模板中的标记集合反参对象", description = "查询栏目的模板中的标记集合反参对象")
public class ModelSplitMapResponseVO
{
    @ApiModelProperty(value = "模板碎片Key，eg：${TAG_12345_TAG}")
    private String modelSplitKey;
    @ApiModelProperty(value = "碎片名称，eg：首页轮播图")
    private String splitName;
    @ApiModelProperty(value = "模板碎片内容，eg：一段html代码")
    private String modelSplitContent;
    @ApiModelProperty(value = "模板碎片id，模板碎片表id")
    private String modelSplitId;
    @ApiModelProperty(value = "模板碎片逻辑id，模板碎片表logic_id")
    private Integer modelSplitLogicId;
    public String getModelSplitKey()
    {
        return modelSplitKey;
    }
    public void setModelSplitKey(String modelSplitKey)
    {
        this.modelSplitKey = modelSplitKey;
    }
    public String getSplitName()
    {
        return splitName;
    }
    public void setSplitName(String splitName)
    {
        this.splitName = splitName;
    }
    public String getModelSplitContent()
    {
        return modelSplitContent;
    }
    public void setModelSplitContent(String modelSplitContent)
    {
        this.modelSplitContent = modelSplitContent;
    }
    public String getModelSplitId()
    {
        return modelSplitId;
    }
    public void setModelSplitId(String modelSplitId)
    {
        this.modelSplitId = modelSplitId;
    }
    public Integer getModelSplitLogicId()
    {
        return modelSplitLogicId;
    }
    public void setModelSplitLogicId(Integer modelSplitLogicId)
    {
        this.modelSplitLogicId = modelSplitLogicId;
    }
    
}
