package core.extjs;

import java.util.List;

/**
 * @author 郑为中
 */
public class ListView<E> {

	private Long totalRecord;
	private List<E> data;

	public Long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Long totalRecord) {
		this.totalRecord = totalRecord;
	}

	public List<E> getData() {
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}

}
