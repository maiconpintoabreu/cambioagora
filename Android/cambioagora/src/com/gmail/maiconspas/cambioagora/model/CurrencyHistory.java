package com.gmail.maiconspas.cambioagora.model;

public class CurrencyHistory {
	private Integer id;
	private Double historyRate;
	private Long historyDate;
	private Integer currencyToId;
	private Integer currencyForId;
	
	public CurrencyHistory(){}

	public CurrencyHistory(Integer id, Double historyRate,
			Long historyDate, Integer currencyToId, Integer currencyForId) {
		this.id = id;
		this.historyRate = historyRate;
		this.historyDate = historyDate;
		this.currencyToId = currencyToId;
		this.currencyForId = currencyForId;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getHistoryRate() {
		return historyRate;
	}

	public void setHistoryRate(Double historyRate) {
		this.historyRate = historyRate;
	}

	public Long getHistoryDate() {
		return historyDate;
	}

	public void setHistoryDate(Long historyDate) {
		this.historyDate = historyDate;
	}

	public Integer getCurrencyToId() {
		return currencyToId;
	}

	public void setCurrencyToId(Integer currencyToId) {
		this.currencyToId = currencyToId;
	}

	public Integer getCurrencyForId() {
		return currencyForId;
	}

	public void setCurrencyForId(Integer currencyForId) {
		this.currencyForId = currencyForId;
	}
	
	
}
