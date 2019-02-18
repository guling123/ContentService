package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 碎片表,对应表TAG
 * </p>
 *
 * @author shidandan
 * @since 2019-01-07
 */
@TableName("tb_split")
@ApiModel(value="Split对象", description="碎片表,对应表TAG")
public class Split extends BaseEntity {

    private static final long serialVersionUID = 1L;
    //表中无此字段是否添加？？？
    @ApiModelProperty(value = "逻辑id")
    @TableField("logic_id")
    private Integer logicId;
    
    @ApiModelProperty(value = "是否可以加入新闻")
    @TableField("has_content")
    private Integer hasContent;

    @ApiModelProperty(value = "是否可以加入文字")
    @TableField("has_text")
    private Integer hasText;
    //表中没此字段是否需要添加？？
    @ApiModelProperty(value = "碎片类型")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "是否加入节点")
    @TableField("has_node")
    private String hasNode;

    @ApiModelProperty(value = "是否可以加入图片")
    @TableField("hash_image")
    private String hashImage;

    @ApiModelProperty(value = "碎片名称")
    @TableField("split_name")
    private String splitName;

    @ApiModelProperty(value = "标记实际处理类")
    @TableField("class_name")
    private String className;

    @ApiModelProperty(value = "创建人")
    @TableField("creater_id")
    private String createrId;

    @TableField("create_time")
    private LocalDateTime createTime;

    public Integer getHasContent() {
        return hasContent;
    }

    public Integer getType()
    {
        return type;
    }

    public Integer getLogicId()
    {
        return logicId;
    }

    public void setLogicId(Integer logicId)
    {
        this.logicId = logicId;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Split setHasContent(Integer hasContent) {
        this.hasContent = hasContent;
        return this;
    }
    public Integer getHasText() {
        return hasText;
    }

    public Split setHasText(Integer hasText) {
        this.hasText = hasText;
        return this;
    }
    public String getHasNode() {
        return hasNode;
    }

    public Split setHasNode(String hasNode) {
        this.hasNode = hasNode;
        return this;
    }
    public String getHashImage() {
        return hashImage;
    }

    public Split setHashImage(String hashImage) {
        this.hashImage = hashImage;
        return this;
    }
    public String getSplitName() {
        return splitName;
    }

    public Split setSplitName(String splitName) {
        this.splitName = splitName;
        return this;
    }
    public String getClassName() {
        return className;
    }

    public Split setClassName(String className) {
        this.className = className;
        return this;
    }
    public String getCreaterId() {
        return createrId;
    }

    public Split setCreaterId(String createrId) {
        this.createrId = createrId;
        return this;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Split setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    @Override
    public String toString() {
        return "Split{" +
        "hasContent=" + hasContent +
        ", hasText=" + hasText +
        ", hasNode=" + hasNode +
        ", hashImage=" + hashImage +
        ", splitName=" + splitName +
        ", className=" + className +
        ", createrId=" + createrId +
        ", createTime=" + createTime +
        "}";
    }
}
