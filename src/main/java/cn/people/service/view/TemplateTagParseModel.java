package cn.people.service.view;

import cn.people.service.view.obj.ContentObj;

/**
 * 
* @ClassName: TemplateTagParseModel 
* @Description: TemplateTagParseService的参数类 
* @author zhangchengchun
* @date 2019年1月11日 上午11:36:46 
*  
 */
public class TemplateTagParseModel
{
    public TemplateTagParseModel(int ttId, Integer nodeId, String contentId, int pageNo) {
        this.ttId = ttId;
        this.nodeId = nodeId;
        this.contentId = contentId;
        this.pageNo = pageNo;
    }
    
    public TemplateTagParseModel(ContentObj content) {
        this.obj = content;
    }
    
    public TemplateTagParseModel(int ttId, Integer nodeId, String contentId, int pageNo,ContentObj content) {
        this.ttId = ttId;
        this.nodeId = nodeId;
        this.contentId = contentId;
        this.pageNo = pageNo;
        this.obj = content;
    }
    
    public TemplateTagParseModel() {
        
    }
    
    private int ttId = 0; //模板碎片逻辑id
    private Integer nodeId; //栏目id
    private String contentId ; //稿件id
    private int pageNo = 0; //页
    private ContentObj obj = null; //稿件对象
    public int getTtId()
    {
        return ttId;
    }
    public void setTtId(int ttId)
    {
        this.ttId = ttId;
    }
    public Integer getNodeId()
    {
        return nodeId;
    }
    public void setNodeId(Integer nodeId)
    {
        this.nodeId = nodeId;
    }
    public String getContentId()
    {
        return contentId;
    }
    public void setContentId(String contentId)
    {
        this.contentId = contentId;
    }
    public int getPageNo()
    {
        return pageNo;
    }
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    public ContentObj getObj()
    {
        return obj;
    }
    public void setObj(ContentObj obj)
    {
        this.obj = obj;
    }
    
    
}
