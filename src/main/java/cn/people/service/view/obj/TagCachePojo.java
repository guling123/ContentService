package cn.people.service.view.obj;

import java.util.Date;
import java.util.HashMap;

public class TagCachePojo {
	// id生成规则是 ttId_nodeId_pageNo
	private String id;
	private int ttId;
	private int nodeId;
	private Date date = new Date();
	private int pageNo;
	private String view;
	// 所有标记属性值md5，用来验证属性值是否有改动，作为判断缓存是否过期的依据
	private String propValueMd5 = "";
	// 判断是否过期的条件，由标记维护
	private HashMap<String, Object> conditionMap = new HashMap<String, Object>();

	public TagCachePojo(int ttId, int nodeId, int pageNo, String view, String propValueMd5) {
		this.ttId = ttId;
		this.nodeId = nodeId;
		this.pageNo = pageNo;
		this.view = view;
		this.propValueMd5 = propValueMd5;
	}

	public TagCachePojo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTtId() {
		return ttId;
	}

	public void setTtId(int ttId) {
		this.ttId = ttId;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getPropValueMd5() {
		return propValueMd5;
	}

	public void setPropValueMd5(String propValueMd5) {
		this.propValueMd5 = propValueMd5;
	}

	public HashMap<String, Object> getConditionMap() {
		return conditionMap;
	}

	public void setConditionMap(HashMap<String, Object> conditionMap) {
		this.conditionMap = conditionMap;
	}

}
