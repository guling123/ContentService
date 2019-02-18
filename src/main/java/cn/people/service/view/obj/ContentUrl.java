package cn.people.service.view.obj;

/**
 * ContentUrl entity. @author MyEclipse Persistence Tools
 */

public class ContentUrl implements java.io.Serializable {

	// Fields

	private Integer logicId;
	private String url;
	private String domain;

	// Constructors

	/** default constructor */
	public ContentUrl() {
	}

	/** full constructor */
	public ContentUrl(String url, String domain) {
		this.url = url;
		this.domain = domain;
	}

	public Integer getLogicId()
    {
        return logicId;
    }

    public void setLogicId(Integer logicId)
    {
        this.logicId = logicId;
    }

    public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}