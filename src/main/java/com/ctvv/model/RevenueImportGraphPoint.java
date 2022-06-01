package com.ctvv.model;

public class RevenueImportGraphPoint {
	private String label;
	private long revenue;
	private long importAmount;

	public RevenueImportGraphPoint(){

	}
	public RevenueImportGraphPoint(RevenueImportGraphPoint point){
		label = point.label;
		revenue = point.revenue;
		importAmount = point.importAmount;
	}
	public RevenueImportGraphPoint(String label, long revenue, long importAmount) {
		this.label = label;
		this.revenue = revenue;
		this.importAmount = importAmount;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public long getRevenue() {
		return revenue;
	}

	public void setRevenue(long revenue) {
		this.revenue = revenue;
	}

	public long getImportAmount() {
		return importAmount;
	}

	public void setImportAmount(long importAmount) {
		this.importAmount = importAmount;
	}
}
