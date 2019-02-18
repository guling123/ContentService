/**   
* @Title: ModelPageVO.java 
* @Package cn.people.controller.vo 
* @Description: 模板碎片列表查询结果 
* @author shidandan
* @date 2019年1月23日 上午11:14:03 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ModelPageVO 
* @Description: 模板碎片列表查询结果 
* @author shidandan
* @date 2019年1月23日 上午11:14:03 
*  
*/
@ApiModel(value="模板碎片列表查询结果", description="模板碎片列表查询结果")
public class ModelSplitPageVO implements Serializable
{

    private static final long serialVersionUID = -7545088376941119524L;
    @ApiModelProperty(value = "模板名称")
    private String modelName;
    
    @ApiModelProperty(value = "模板碎片值")
    List<ModelSplitVO> records;
    
    @ApiModelProperty(value = "总数")
    private long total = 0;
    
    @ApiModelProperty(value = "大小")
    private long size = 10;
    
    @ApiModelProperty(value = "当前值")
    private long current = 1;
    
    @ApiModelProperty(value = "当前分页总数")
    private long pages;
    public String getModelName()
    {
        return modelName;
    }
    public void setModelName(String modelName)
    {
        this.modelName = modelName;
    }
    public List<ModelSplitVO> getRecords()
    {
        return records;
    }
    public void setRecords(List<ModelSplitVO> records)
    {
        this.records = records;
    }
    public long getTotal()
    {
        return total;
    }
    public void setTotal(long total)
    {
        this.total = total;
    }
    
    public long getPages()
    {
        return pages;
    }
    public void setPages(long pages)
    {
        this.pages = pages;
    }
    public long getSize()
    {
        return size;
    }
    public void setSize(long size)
    {
        this.size = size;
    }
    public long getCurrent()
    {
        return current;
    }
    public void setCurrent(long current)
    {
        this.current = current;
    }
}
