/**   
* @Title: ContentOperateLogVO.java 
* @Package cn.people.controller.vo 
* @Description: 内容操作日志 
* @author shidandan
* @date 2018年12月26日 下午5:10:01 
* @version V1.0   
*/
package cn.people.controller.vo;

import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/** 
* @ClassName: ContentPageVo 
* @Description: 稿件预览分页vo 
* @author shidandan
* @date 2018年12月26日 下午5:10:01 
*  
*/
public class ContentPageVo implements Serializable
{
    private static final long serialVersionUID = 8242702368009302004L;
    @ApiModelProperty(value = "稿件分页页码")
    private Integer pageNo;

    @ApiModelProperty(value = "稿件url")
    private String url;
    // 域名
    @ApiModelProperty(value = "域名")
    private String domain;

    public Integer getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }

    public String getDomain()
    {
        return domain;
    }

    public void setDomain(String domain)
    {
        this.domain = domain;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    } 
    
    
}
