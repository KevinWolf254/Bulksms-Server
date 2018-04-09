package co.ke.bigfootke.app.pojos;

import java.util.List;

public class LineChartData {

	private String label;
	private List<Integer> data;
	
	public LineChartData() {
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Integer> getData() {
		return data;
	}

	public void setData(List<Integer> data) {
		this.data = data;
	}
	
}
