package com.gmail.maiconspas.cambioagora;

import android.content.Context;
import android.widget.ArrayAdapter;
//Verify your necessity
public enum EnumTypeMoney {
	BRL(1, "http://brl.fx-exchange.com/rss.xml", "R$ "), 
	EUR(2, "http://eur.fx-exchange.com/rss.xml", "€ "), 
	USD(3, "http://usd.fx-exchange.com/rss.xml", "$ "),
	GBP(4, "http://gbp.fx-exchange.com/rss.xml", "£ "),
	CAD(5, "http://cad.fx-exchange.com/rss.xml", "C$ ");

	private int cod;
	private String desc;
	private String longDesc;
	private String symbol;

	private EnumTypeMoney(int cod, String desc, String symbol) {
		this.cod = cod;
		this.desc = desc;
		this.longDesc = desc;
		this.setSymbol(symbol);
	}

	public static EnumTypeMoney valueOf(int codigo) {
		for (EnumTypeMoney valor : values()) {
			if (valor.getCod() == codigo) {
				return valor;
			}
		}
		return null;

	}

	public static boolean isValid(Integer codigo) {
		return valueOf(codigo) != null;
	}

	public static Integer getSize() {
		return values().length;
	}

	public String getName() {
		return this.name();
	}

	public int getCod() {
		return this.cod;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getLongDesc() {
		return ((this.longDesc == new String())) ? this.desc : this.longDesc;
	}

	public String toString() {
		return this.getDesc();
	}

	public static ArrayAdapter<CharSequence> getArrayAdapter(Context context) {
		ArrayAdapter<CharSequence> sa = new ArrayAdapter<CharSequence>(context,
				android.R.layout.simple_spinner_item);
		for (EnumTypeMoney enumItem : values()) {
			sa.add(enumItem.getDesc());
		}
		return sa;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
