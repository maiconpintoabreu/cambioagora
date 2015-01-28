package com.gmail.maiconspas.cambioagora;

import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.gmail.maiconspas.cambioagora.dao.CurrencyDao;
import com.gmail.maiconspas.cambioagora.dao.CurrencyHistoryDao;
import com.gmail.maiconspas.cambioagora.model.CurrencyHistory;
import com.google.analytics.tracking.android.EasyTracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class HistoryActivity extends SherlockActivity  {

	private CurrencyHistoryDao currencyHistoryDao;
	private CurrencyDao currencyDao;
	private ListView listCurrencyHistory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);        
		setContentView(R.layout.activity_history);
		currencyHistoryDao = new CurrencyHistoryDao(this);
		currencyDao = new CurrencyDao(this);
		Intent intent = getIntent();
		Bundle params = intent.getExtras();  


		int currencyToId = params.getInt("currencyToId");
		int currencyForId = params.getInt("currencyForId");
		setTitle(currencyDao.getCurrencyById(currencyToId).getNameShort() +" - "+currencyDao.getCurrencyById(currencyForId).getNameShort());
		
		listCurrencyHistory = (ListView) findViewById(R.id.list_currency_history);
		
		List<CurrencyHistory> currencyHistoryList = currencyHistoryDao
				.getAllCurrencyHistoryByPreference(currencyToId, currencyForId);
		
		CurrencyHistory[] currencyHistoryArray = new CurrencyHistory[currencyHistoryList.size()];
		int i=0;
		for(CurrencyHistory currencyHistory : currencyHistoryList){
			currencyHistoryArray[i] = currencyHistory;
			i++;
		}
		AdapterCurrencyHistory adapter = new AdapterCurrencyHistory(HistoryActivity.this, currencyHistoryArray);
		listCurrencyHistory.setAdapter(adapter);
	}
	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}
}
