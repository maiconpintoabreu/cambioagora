package com.gmail.maiconspas.cambioagora.model;

public class Currency {
	private Integer id;
	private String name;
	private String symbol;
	private String link;
	private String nameLong;
	private String nameShort;
	
	public Currency(){}
	
	public Currency(Integer id, String name, String symbol, String link,
			String nameShort, String nameLong) {
		super();
		this.id = id;
		this.name = name;
		this.symbol = symbol;
		this.link = link;
		this.nameLong = nameLong;
		this.nameShort = nameShort;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	public String getNameLong() {
		return nameLong;
	}
	public void setNameLong(String nameLong) {
		this.nameLong = nameLong;
	}
	public String getNameShort() {
		return nameShort;
	}
	public void setNameShort(String nameShort) {
		this.nameShort = nameShort;
	}

}
