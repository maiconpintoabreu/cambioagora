package com.gmail.maiconspas.cambioagora;

import java.text.DecimalFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gmail.maiconspas.cambioagora.dao.CurrencyDao;
import com.gmail.maiconspas.cambioagora.model.Currency;
import com.gmail.maiconspas.cambioagora.model.CurrencyHistory;

public class AdapterCurrency  extends ArrayAdapter<CurrencyHistory> {
  private final Context context;
  private final CurrencyHistory[] values;

  public AdapterCurrency(Context context, CurrencyHistory[] values) {
	super(context, R.layout.adapter_currency, values);
	this.context = context;
	this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.adapter_currency, parent, false);
    TextView txtCurrencyTo = (TextView) rowView.findViewById(R.id.txt_adapter_currency_to);
    TextView txtCurrencyFor = (TextView) rowView.findViewById(R.id.txt_adapter_currency_for);
    TextView txtValueTo = (TextView) rowView.findViewById(R.id.txt_adapter_value_to);
    TextView txtValueFor = (TextView) rowView.findViewById(R.id.txt_adapter_value_for);
    CurrencyDao currencyDao = new CurrencyDao(context);
    Currency currencyTo = currencyDao.getCurrency(values[position].getCurrencyToId());
    Currency currencyFor = currencyDao.getCurrency(values[position].getCurrencyForId());
    
    txtCurrencyTo.setText(currencyTo.getNameLong());
    txtCurrencyFor.setText(currencyFor.getNameLong());
    txtValueTo.setText(currencyTo.getSymbol()+" 1.00"); //TODO:
	DecimalFormat df = new DecimalFormat("###0.000000"); 
    txtValueFor.setText(currencyFor.getSymbol()+" "+df.format(values[position].getHistoryRate()));

    return rowView;
  }
} 