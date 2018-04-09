package co.ke.bigfootke.app.pojos;

public class Page {
	private int size;
	private int totalElements;
	private int totalPages;
	private int pageNumber;
	
	public Page() {
	}

	public int getSize() {
		return size;
	}

	public Page(int size, int totalElements, int totalPages, int pageNumber) {
		super();
		this.size = size;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.pageNumber = pageNumber;
	}

	public Page(Page page) {
		super();
		this.size = page.getSize();
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		this.pageNumber = page.getPageNumber();
	}
	
	public void setSize(int size) {
		this.size = size;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
}
