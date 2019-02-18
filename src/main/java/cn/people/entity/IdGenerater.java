package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 数值 ID 生成器 简易
 * </p>
 *
 * @author shidandan
 * @since 2019-01-10
 */
@TableName("tb_id_generater")
@ApiModel(value="IdGenerater对象", description="数值 ID 生成器 简易")
public class IdGenerater extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "范围ID 全局限定时：0 ,站点限定时，站点ID")
    @TableField("scope_id")
    private String scopeId;

    @ApiModelProperty(value = "实体表名  ID_配置master的ID")
    @TableField("entity_name")
    private String entityName;

    @ApiModelProperty(value = "库表中 限定范围内最大值")
    @TableField("current_value")
    private Integer currentValue;

    public String getScopeId() {
        return scopeId;
    }

    public IdGenerater setScopeId(String scopeId) {
        this.scopeId = scopeId;
        return this;
    }
    public String getEntityName() {
        return entityName;
    }

    public IdGenerater setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }
    public Integer getCurrentValue() {
        return currentValue;
    }

    public IdGenerater setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
        return this;
    }

    @Override
    public String toString() {
        return "IdGenerater{" +
        "scopeId=" + scopeId +
        ", entityName=" + entityName +
        ", currentValue=" + currentValue +
        "}";
    }
}
