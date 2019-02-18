package cn.people.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 公共请求参数
 * <p>
 * Created by wilson on 2018-12-06.
 */
@ApiModel(value = "公共请求参数")
public class CommonParams {

    @ApiModelProperty("操作人id")
    private String operatorid;

    public String getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid;
    }

}
