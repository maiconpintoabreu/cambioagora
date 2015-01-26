package com.gmail.maiconspas.cambioagora;

import java.util.List;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.gmail.maiconspas.cambioagora.dao.CurrencyDao;
import com.gmail.maiconspas.cambioagora.dao.CurrencyHistoryDao;
import com.gmail.maiconspas.cambioagora.model.Currency;
import com.gmail.maiconspas.cambioagora.model.CurrencyHistory;
import com.gmail.maiconspas.cambioagora.service.SyncService;
import com.google.analytics.tracking.android.EasyTracker;

public class MainActivity extends SherlockActivity {

	ListView list_currency;
	String listPreference;
	String listPreferenceAux;
	ProgressDialog dialog;

	private CurrencyHistoryDao currencyHistoryDao;
	private CurrencyDao currencyDao;
	

	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			onStart();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

	    LocalBroadcastManager.getInstance(this).registerReceiver(
	            mMessageReceiver, new IntentFilter("refresh"));
		
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());  
		listPreference = sp.getString("key_currenty", null);
		if(listPreference == null){
			listPreference = "BRL";
		}
		setTitle(getText(R.string.txt_base_currency)+" = "+listPreference);
		listPreferenceAux = listPreference;
		list_currency = (ListView) findViewById(R.id.list_currency);
		currencyHistoryDao = new CurrencyHistoryDao(this);
		currencyDao = new CurrencyDao(this);
		Intent intentService = new Intent(this, SyncService.class);
		stopService(intentService);
		startService(intentService);
		new MakeScreenTask().execute();
		list_currency.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CurrencyHistory itemSelected = (CurrencyHistory) list_currency.getAdapter().getItem(position);
				Bundle params = new Bundle();
                params.putInt("currencyToId", itemSelected.getCurrencyToId());
                params.putInt("currencyForId", itemSelected.getCurrencyForId());
	        	Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                intent.putExtras(params);
	        	startActivity(intent);
				
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.main_settings, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	        	Intent intent = new Intent(MainActivity.this, PreferenceActivity.class);
	        	startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	@Override
	public void onStart() {
		super.onStart();
		new MakeScreenTask().execute();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}
	

	private class MakeScreenTask extends AsyncTask<String, Void, CurrencyHistory[]> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(dialog == null){
				dialog = ProgressDialog.show(MainActivity.this,
						"Aguarde", "Por Favor Aguarde...");
			}

		}

		@Override
		protected CurrencyHistory[] doInBackground(String... params) {
			CurrencyHistory[] retornos = new CurrencyHistory[0];
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());  
			listPreference = sp.getString("key_currenty", null);
			if(listPreference == null){
				listPreference = "BRL";
			}
			Currency currencyPreference = currencyDao.getCurrencyByShortName(listPreference);
			if(currencyPreference !=null){
				List<CurrencyHistory> currencyHistory = currencyHistoryDao.getAllCurrencyHistoryByPreference(currencyPreference.getId());
				retornos = new CurrencyHistory[currencyHistory.size()];
				
				for(int i=0;i<currencyHistory.size();i++){
					retornos[i] = currencyHistory.get(i);
				}
			}
			return retornos;
		}

		@Override
		protected void onPostExecute(CurrencyHistory[] result) {
			AdapterCurrency adapter = new AdapterCurrency(MainActivity.this, result);
			list_currency.setAdapter(adapter);
			super.onPostExecute(result);
			setTitle(getText(R.string.txt_base_currency)+" = "+listPreference);
			if(result.length > 0){
					dialog.dismiss();
			}
		}
	}

}
