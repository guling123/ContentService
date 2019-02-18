package cn.people.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author guling
 * @ClassName: SplitContentDelVO
 * @Description: 碎片删除id集合
 * @date 2019/1/29 9:37
 */
@ApiModel(value="删除id集合", description="删除id集合")
public class SplitContentDelVO implements Serializable
{
    @ApiModelProperty(value = " 碎片删除id集合")
    private ArrayList<String> ids;

    public ArrayList<String> getIds()
    {
        return ids;
    }

    public void setIds(ArrayList<String> ids)
    {
        this.ids = ids;
    }

    @Override public String toString()
    {
        return "SplitContentDelVO{" + "ids=" + ids + '}';
    }
}
