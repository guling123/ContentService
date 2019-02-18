package cn.people.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.people.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * ID 生成配置
 * </p>
 *
 * @author shidandan
 * @since 2019-01-10
 */
@TableName("m_id_config")
@ApiModel(value="IdConfig对象", description="ID 生成配置")
public class IdConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "物理表名为key ,数据事先添加" + 
        "tb_sys_org,tb_content_source,tb_channel,tb_sys_user,tb_content,tb_model，tb_split")
    private String  id;
    
    @ApiModelProperty(value = "物理表，设计名称 租户表，稿件来源表，栏目信息表，管理用户表，内容主体表，模板表，碎片表")
    @TableField("note")
    private String note;
    
    @ApiModelProperty(value = "限定范围 0：全局限定，1:站点限定")
    @TableField("scope_flg")
    private String scopeFlg;

    @ApiModelProperty(value = "tb_sys_org 1000"+
        "tb_content_source 1000"+
        "tb_channel 100000 "+
        "tb_sys_user 1000 "+
        "tb_content 10000000 "+
        "tb_model 100000"+
        "tb_split 100000")
    @TableField("starter")
    private Integer starter;

    public String getNote() {
        return note;
    }

    public IdConfig setNote(String note) {
        this.note = note;
        return this;
    }
    public String getScopeFlg() {
        return scopeFlg;
    }

    public IdConfig setScopeFlg(String scopeFlg) {
        this.scopeFlg = scopeFlg;
        return this;
    }
    public Integer getStarter() {
        return starter;
    }

    public IdConfig setStarter(Integer starter) {
        this.starter = starter;
        return this;
    }

    @Override
    public String toString() {
        return "IdConfig{" +
        "note=" + note +
        ", scopeFlg=" + scopeFlg +
        ", starter=" + starter +
        "}";
    }
}
