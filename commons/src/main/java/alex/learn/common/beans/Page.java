package alex.learn.common.beans;

import java.io.Serializable;

public class Page implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Integer currentPageNo = 1;

	protected Integer pageSize = 10;

	public Integer getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(Integer currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
