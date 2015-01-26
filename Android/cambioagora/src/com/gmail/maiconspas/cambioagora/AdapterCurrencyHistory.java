package com.gmail.maiconspas.cambioagora;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gmail.maiconspas.cambioagora.model.CurrencyHistory;

public class AdapterCurrencyHistory  extends ArrayAdapter<CurrencyHistory> {
  private final Context context;
  private final CurrencyHistory[] values;
  private double maiorValor = Double.MIN_VALUE;
  private double menorValor = Double.MAX_VALUE;

  public AdapterCurrencyHistory(Context context, CurrencyHistory[] values) {
	super(context, R.layout.adapter_currency, values);
	this.context = context;
	this.values = values;
	for(CurrencyHistory cH : values){
		if(cH.getHistoryRate() > maiorValor){
			maiorValor = cH.getHistoryRate();
		}
		if(cH.getHistoryRate() < menorValor){
			menorValor = cH.getHistoryRate();
		}
	}
  }

  @SuppressLint("SimpleDateFormat")
@Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.adapter_currency_history, parent, false);
    Button viewChartBar = (Button)rowView.findViewById(R.id.view_chart_bar);
    TextView txtCurrencyHistory = (TextView) rowView.findViewById(R.id.txt_currency_history);
    
	DecimalFormat df = new DecimalFormat("###0.000000"); 
	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    txtCurrencyHistory.setText(sf.format(new Date(values[position].getHistoryDate())) 
    		+ " - " + df.format(values[position].getHistoryRate()));
    Double percentVariation;
    if(maiorValor-menorValor != 0d){
    	percentVariation = (maiorValor-values[position].getHistoryRate())/(maiorValor-menorValor)*100;
    }else{
    	percentVariation = 100d;
    }
    
    int sizeInt = (int) Math.ceil(((100-percentVariation)/100)*600);
    viewChartBar.setWidth(sizeInt);
    return rowView;
  }
} 