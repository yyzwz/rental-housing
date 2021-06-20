package core.support;

import java.util.List;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class PageView<E> {

	/** list data * */
	private List<E> records;
	/** total page * */
	private long totalPage = 1;
	/** count per page * */
	private int maxResult = 12;
	/** current page * */
	private int currentPage = 0;
	/** total record qty * */
	private long totalRecord;
	/** page count * */
	private int pageCode = 10;

	public PageView(int maxResult, int currentPage) {
		this.maxResult = maxResult;
		this.currentPage = currentPage;
	}

	public int getPageCode() {
		return pageCode;
	}

	public void setPagecode(int pageCode) {
		this.pageCode = pageCode;
	}

	public void setQueryResult(QueryResult<E> qr) {
		setRecords(qr.getResultList());
		setTotalRecord(qr.getTotalCount());
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
		setTotalPage(this.totalRecord % this.maxResult == 0 ? this.totalRecord / this.maxResult : this.totalRecord / this.maxResult + 1);
	}

	public List<E> getRecords() {
		return records;
	}

	public void setRecords(List<E> records) {
		this.records = records;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalpage) {
		this.totalPage = totalpage;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getFirstResult() {
		return (this.currentPage - 1) * this.maxResult;
	}

}
