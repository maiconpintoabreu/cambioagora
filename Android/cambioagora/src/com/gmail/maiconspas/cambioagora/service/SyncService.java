package com.gmail.maiconspas.cambioagora.service;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.gmail.maiconspas.cambioagora.EnumTypeMoney;
import com.gmail.maiconspas.cambioagora.dao.CurrencyDao;
import com.gmail.maiconspas.cambioagora.dao.CurrencyHistoryDao;
import com.gmail.maiconspas.cambioagora.model.Currency;
import com.gmail.maiconspas.cambioagora.model.CurrencyHistory;

public class SyncService extends IntentService {

	public SyncService() {
		super("SyncService");
	}

	private CurrencyDao currencyDao;

	private CurrencyHistoryDao currencyHistoryDao;

	TimerSinc timerSinc;
	
	private static final Integer time = 15;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		currencyDao = new CurrencyDao(this);
		currencyHistoryDao = new CurrencyHistoryDao(this);
		try {
			Log.i(this.getClass().toString(), "Received start id " + startId
					+ ": " + intent);
			if (timerSinc == null) {
				timerSinc = new TimerSinc();

				Timer t = new Timer();

				try {
					long period = time * 60000;
					t.scheduleAtFixedRate(timerSinc, 1000, period);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (NullPointerException ne) {
			stopSelf();
		}
		return START_STICKY;
	}

	private class TimerSinc extends TimerTask {
		@Override
		public void run() {
			try{
			List<Currency> currencyList = currencyDao.getAllCurrency();
			if (checkInternetConnection()) {
				String title = new String();
				String description = new String();
				try {
					DocumentBuilderFactory dbf = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder db;
					db = dbf.newDocumentBuilder();
					for(Currency currencyItem : currencyList){
						Document doc = db.parse(currencyItem.getLink());
						NodeList listItem = doc.getElementsByTagName("item");
						for (int x = 0; x < listItem.getLength(); x++) {
							title = listItem.item(x).getChildNodes().item(0)
									.getChildNodes().item(0).getNodeValue();
							description = listItem.item(x).getChildNodes().item(4)
									.getChildNodes().item(0).getNodeValue();
							if (title.contains(EnumTypeMoney.EUR.getName()) && !EnumTypeMoney.EUR.getName().equalsIgnoreCase(currencyItem.getNameShort())) {
								CurrencyHistory currencyHistory = new CurrencyHistory();
								currencyHistory.setHistoryRate(processValue(description));
								currencyHistory.setCurrencyToId(currencyItem.getId());
								currencyHistory.setCurrencyForId(currencyDao.getCurrencyByShortName(EnumTypeMoney.EUR.getName()).getId());
								currencyHistoryDao.addCurrencyHistory(currencyHistory);
							} else if (title.contains(EnumTypeMoney.USD.getName()) && !EnumTypeMoney.USD.getName().equalsIgnoreCase(currencyItem.getNameShort())) {
								CurrencyHistory currencyHistory = new CurrencyHistory();
								currencyHistory.setHistoryRate(processValue(description));
								currencyHistory.setCurrencyToId(currencyItem.getId());
								currencyHistory.setCurrencyForId(currencyDao.getCurrencyByShortName(EnumTypeMoney.USD.getName()).getId());
								currencyHistoryDao.addCurrencyHistory(currencyHistory);
							} else if (title.contains(EnumTypeMoney.GBP.getName()) && !EnumTypeMoney.GBP.getName().equalsIgnoreCase(currencyItem.getNameShort())) {
								CurrencyHistory currencyHistory = new CurrencyHistory();
								currencyHistory.setHistoryRate(processValue(description));
								currencyHistory.setCurrencyToId(currencyItem.getId());
								currencyHistory.setCurrencyForId(currencyDao.getCurrencyByShortName(EnumTypeMoney.GBP.getName()).getId());
								currencyHistoryDao.addCurrencyHistory(currencyHistory);
							} else if (title.contains(EnumTypeMoney.BRL.getName()) && !EnumTypeMoney.BRL.getName().equalsIgnoreCase(currencyItem.getNameShort())) {
								CurrencyHistory currencyHistory = new CurrencyHistory();
								currencyHistory.setHistoryRate(processValue(description));
								currencyHistory.setCurrencyToId(currencyItem.getId());
								currencyHistory.setCurrencyForId(currencyDao.getCurrencyByShortName(EnumTypeMoney.BRL.getName()).getId());
								currencyHistoryDao.addCurrencyHistory(currencyHistory);
							}
						}
					}
				} catch (ParserConfigurationException e) {
					Log.d(this.toString(), e.getMessage());
				} catch (SAXException e) {
					Log.d(this.toString(), e.getMessage());
				} catch (IOException e) {
					Log.d(this.toString(), e.getMessage());
				}
			}else{
				Toast.makeText(getApplicationContext(), "Erro ao sincronizar, sem internet!", Toast.LENGTH_SHORT).show(); //TODO: Translate
			}
			
			refreshMessageToActivity();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private Double processValue(String value) {
		value = value.replaceAll("[^0-9-.]+", " ");
		String[] valores = value.split(" ");
		value = valores[1];
		Double valorCalculavel = Double.valueOf(value);
		return valorCalculavel;
	}

	private boolean checkInternetConnection() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// test for connection
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			Log.v(this.getClass().toString(), "Internet Connection Not Present");
			return false;
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {
	}
	
	private void refreshMessageToActivity() {
	    Intent intent = new Intent("refresh");
	    sendRefresh(intent);
	}
	public void sendRefresh(Intent intent) {
		intent.putExtra("msg", "actionRefresh");
	    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}
}
