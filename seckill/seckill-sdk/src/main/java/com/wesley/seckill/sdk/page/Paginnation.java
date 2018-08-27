package com.wesley.seckill.sdk.page;

import org.apache.commons.lang3.StringUtils;

public class Paginnation {

	private int pageNo;
	private int pageSize;
	private int start;
	private int end;
	private int total = 0;
	private String orderBy;
	private String orderType;

	/*总页数*/
	private int pages;

	public Paginnation()
	  {
	  }

	public Paginnation(int pageNo, int pageSize)
	  {
	    this(pageNo, pageSize, null, "asc");
	  }

	public Paginnation(int pageNo, int pageSize, String orderBy, String orderType) {
	    this.pageNo = pageNo;
	    this.pageSize = pageSize;
	    this.orderBy = orderBy;
	    setOrderType(orderType);

	    if (pageNo == 1)
	      this.start = 0;
	    else {
	      this.start = ((pageNo - 1) * pageSize);
	    }

	    this.end = (this.start + pageSize - 1);
	  }

	public Paginnation(String orderBy, String orderType, int start, int pageSize)
	  {
	    this.pageSize = pageSize;
	    this.orderBy = orderBy;
	    setOrderType(orderType);

	    if (start < 0) {
	      start = 0;
	    }
	    this.start = start;

	    this.pageNo = (this.start % this.pageSize == 0 ? this.start / this.pageSize : this.start / this.pageSize + 1);

	    this.end = (this.start + pageSize - 1);
	  }

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		if (this.pageSize > 5000) {
			this.pageSize = 5000;
		}
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		return this.start;
	}

	public int getEnd() {
		if (this.total > 0) {
			return Math.min(this.end, this.total - 1);
		}
		return this.end;
	}

	public int getCount() {
		int count = getEnd() - getStart();
		return count < 0 ? 0 : count + 1;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotal() {
		return this.total;
	}

	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public void setOrderType(String orderType) {
		if (!StringUtils.isBlank(orderType)) {
			validateOrderType(orderType);
		}

		this.orderType = orderType;
	}

	private void validateOrderType(String orderType) {
		if ((!"asc".equalsIgnoreCase(orderType)) && (!"desc".equalsIgnoreCase(orderType)))
			throw new RuntimeException("错误的排序类型，orderType：" + orderType);
	}

	public String toString() {
		return "Paginnation [pageNo=" + getPageNo() + ", pageSize=" + getPageSize() + ", start=" + getStart() + ", end="
				+ getEnd() + ", total=" + this.total + "]";
	}

	public static void main(String[] args) {
		printMsg(new Paginnation(1, 20));
		printMsg(new Paginnation(38, 20));
		Paginnation query = new Paginnation(38, 20);
		query.setTotal(750);
		printMsg(query);
		printMsg(new Paginnation("name", "asc", 81, 10));
		printMsg(new Paginnation("name", "asc", 80, 10));
	}

	private static void printMsg(Paginnation query) {
		System.out.println(query.toString() + "    count:" + query.getCount());
	}
}
