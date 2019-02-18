package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 碎片属性表对应原表TAG_PROP
 * </p>
 *
 * @author shidandan
 * @since 2019-01-07
 */
@TableName("tb_split_prop")
@ApiModel(value="SplitProp对象", description="碎片属性表对应原表TAG_PROP")
public class SplitProp extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "碎片id")
    @TableField("split_id")
    private String splitId;

    @ApiModelProperty(value = "属性名称")
    @TableField("prop_name")
    private String propName;

    @ApiModelProperty(value = "属性描述")
    @TableField("prop_des")
    private String propDes;

    @ApiModelProperty(value = "属性值")
    @TableField("prop_value")
    private String propValue;

    @ApiModelProperty(value = "属性类型，n表示数据，c表示字符型")
    @TableField("prop_type")
    private String propType;

    @ApiModelProperty(value = "属性级别，0表示高级，1表示普通(编辑在页面控制中可以修改其值)符型")
    @TableField("prop_level")
    private Integer propLevel;

    public String getSplitId() {
        return splitId;
    }

    public SplitProp setSplitId(String splitId) {
        this.splitId = splitId;
        return this;
    }
    public String getPropName() {
        return propName;
    }

    public SplitProp setPropName(String propName) {
        this.propName = propName;
        return this;
    }
    public String getPropDes() {
        return propDes;
    }

    public SplitProp setPropDes(String propDes) {
        this.propDes = propDes;
        return this;
    }
    public String getPropValue() {
        return propValue;
    }

    public SplitProp setPropValue(String propValue) {
        this.propValue = propValue;
        return this;
    }
    public String getPropType() {
        return propType;
    }

    public SplitProp setPropType(String propType) {
        this.propType = propType;
        return this;
    }
    public Integer getPropLevel() {
        return propLevel;
    }

    public SplitProp setPropLevel(Integer propLevel) {
        this.propLevel = propLevel;
        return this;
    }

    @Override
    public String toString() {
        return "SplitProp{" +
        "splitId=" + splitId +
        ", propName=" + propName +
        ", propDes=" + propDes +
        ", propValue=" + propValue +
        ", propType=" + propType +
        ", propLevel=" + propLevel +
        "}";
    }
}
