package cn.people.controller.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.people.entity.SplitContent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 碎片内容表
 * </p>
 *
 * @author fuqiang
 * @since 2018-12-04
 */
@ApiModel(value="碎片对象列表", description="碎片对象列表")
public class SplitContentListVO implements Serializable {
    private static final long serialVersionUID = -6110105846214671959L;
    
    @ApiModelProperty(value = "创建人ID")
    private String createrid;
    
    @ApiModelProperty(value = "碎片列表")
    List<SplitContent> splitList=new ArrayList<>();
    public List<SplitContent> getSplitList()
    {
        return splitList;
    }
    public void setSplitList(List<SplitContent> splitList)
    {
        this.splitList = splitList;
    }
    
    public String getCreaterid()
    {
        return createrid;
    }
    public void setCreaterid(String createrid)
    {
        this.createrid = createrid;
    }
    @Override
        public String toString()
        {
            return splitList.toString();
        }
    
}
