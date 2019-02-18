package cn.people.controller.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * @author guling
 * @ClassName: ModelSplitPropListParentVO
 * @Description: 模板碎片属性列表查询返回结果集(这里用一句话描述这个类的作用)
 * @date 2019/1/23 13:56
 */
public class ModelSplitPropListParentVO implements Serializable
{
    @ApiModelProperty(value = "模板名称")
    private String splitName;

    @ApiModelProperty(value = "模板内容")
    ArrayList<ModelSplitPropListVO> records;

    public String getSplitName()
    {
        return splitName;
    }

    public void setSplitName(String splitName)
    {
        this.splitName = splitName;
    }

    public ArrayList<ModelSplitPropListVO> getRecords()
    {
        return records;
    }

    public void setRecords(ArrayList<ModelSplitPropListVO> records)
    {
        this.records = records;
    }
}
