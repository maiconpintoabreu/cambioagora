package com.gmail.maiconspas.cambioagora.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gmail.maiconspas.cambioagora.model.Currency;
import com.gmail.maiconspas.cambioagora.model.CurrencyHistory;

public class CurrencyHistoryDao {

	DatabaseHandler databaseHandler;
	Context context;
	public CurrencyHistoryDao(Context context) {
		this.context = context;
		databaseHandler = new DatabaseHandler(context);
	}

	public void addCurrencyHistory(CurrencyHistory currencyHistory) {

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_CURRENCY_HISTORY_DATE,
				new Date().getTime());
		values.put(DatabaseHandler.KEY_CURRENCY_OF_HISTORY_ID,
				currencyHistory.getCurrencyToId());
		values.put(DatabaseHandler.KEY_CURRENCY_FOR_HISTORY_ID,
				currencyHistory.getCurrencyForId());
		values.put(DatabaseHandler.KEY_CURRENCY_HISTORY_RATE,
				currencyHistory.getHistoryRate());
		// Inserting Row
		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		db.insert(DatabaseHandler.TABLE_CURRENCY_HISTORY, null, values);
		db.close();
	}

	public CurrencyHistory getCurrencyHistory(int id) {
		SQLiteDatabase db = databaseHandler.getReadableDatabase();

		Cursor cursor = db.query(DatabaseHandler.TABLE_CURRENCY_HISTORY,
				new String[] { DatabaseHandler.KEY_CURRENCY_HISTORY_ID,
						DatabaseHandler.KEY_CURRENCY_HISTORY_RATE,
						DatabaseHandler.KEY_CURRENCY_HISTORY_DATE,
						DatabaseHandler.KEY_CURRENCY_OF_HISTORY_ID,
						DatabaseHandler.KEY_CURRENCY_FOR_HISTORY_ID },
				DatabaseHandler.KEY_CURRENCY_HISTORY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		CurrencyHistory currencyHistory = null;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();

			currencyHistory = new CurrencyHistory(Integer.parseInt(cursor
					.getString(0)), Double.parseDouble(cursor.getString(1)),
					Long.parseLong(cursor.getString(2)),
					Integer.parseInt(cursor.getString(3)),
					Integer.parseInt(cursor.getString(4)));
		}
		cursor.close();
		db.close();
		return currencyHistory;
	}

	public List<CurrencyHistory> getAllCurrencyHistory() {
		List<CurrencyHistory> currencyHistList = new ArrayList<CurrencyHistory>();
		String selectQuery = "SELECT "
				+ DatabaseHandler.KEY_CURRENCY_HISTORY_ID + ","
				+ DatabaseHandler.KEY_CURRENCY_HISTORY_RATE + ","
				+ DatabaseHandler.KEY_CURRENCY_HISTORY_DATE + ","
				+ DatabaseHandler.KEY_CURRENCY_OF_HISTORY_ID + ","
				+ DatabaseHandler.KEY_CURRENCY_FOR_HISTORY_ID + " FROM "
				+ DatabaseHandler.TABLE_CURRENCY_HISTORY + " ORDER BY "
				+ DatabaseHandler.KEY_CURRENCY_OF_HISTORY_ID;

		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				CurrencyHistory currencyHistory = new CurrencyHistory(
						Integer.parseInt(cursor.getString(0)),
						Double.parseDouble(cursor.getString(1)),
						Long.parseLong(cursor.getString(2)),
						Integer.parseInt(cursor.getString(3)),
						Integer.parseInt(cursor.getString(4)));
				currencyHistList.add(currencyHistory);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return currencyHistList;
	}

	public List<CurrencyHistory> getAllCurrencyHistoryByPreference(
			Integer currency_for_id) {
		List<CurrencyHistory> currencyHistList = new ArrayList<CurrencyHistory>();
		CurrencyDao currencyDao = new CurrencyDao(context);
		List<Currency> currencyList = currencyDao.getAllCurrency();
		for(Currency currency : currencyList){
			if(currency.getId() != currency_for_id){
				CurrencyHistory currencyHistory = getLastCurrencyHistoryByPreference(currency.getId(), currency_for_id);
				if(currencyHistory != null){
					currencyHistList.add(currencyHistory);
				}
			}
		}
		return currencyHistList;
	}

	public CurrencyHistory getLastCurrencyHistoryByPreference(
		Integer currency_of_id, Integer currency_for_id) {
		String selectQuery = "SELECT "
				+ DatabaseHandler.KEY_CURRENCY_HISTORY_ID + ","
				+ DatabaseHandler.KEY_CURRENCY_HISTORY_RATE + ","
				+ DatabaseHandler.KEY_CURRENCY_HISTORY_DATE + ","
				+ DatabaseHandler.KEY_CURRENCY_OF_HISTORY_ID + ","
				+ DatabaseHandler.KEY_CURRENCY_FOR_HISTORY_ID + " FROM "
				+ DatabaseHandler.TABLE_CURRENCY_HISTORY 
				+ " WHERE "+ DatabaseHandler.KEY_CURRENCY_OF_HISTORY_ID + " = "
				+ currency_of_id 
				+ " AND "+ DatabaseHandler.KEY_CURRENCY_FOR_HISTORY_ID + " = "
				+ currency_for_id 
				+ " ORDER BY "+ DatabaseHandler.KEY_CURRENCY_HISTORY_ID+ " DESC"
				+ " LIMIT 1";

		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		CurrencyHistory retorno = null;
		if (cursor.moveToFirst()) {
			CurrencyHistory currencyHistory = new CurrencyHistory(
					Integer.parseInt(cursor.getString(0)),
					Double.parseDouble(cursor.getString(1)),
					Long.parseLong(cursor.getString(2)),
					Integer.parseInt(cursor.getString(3)),
					Integer.parseInt(cursor.getString(4)));
			retorno = currencyHistory;
		}
		cursor.close();
		db.close();
		return retorno;
	}
	public List<CurrencyHistory> getAllCurrencyHistoryByPreference(
			Integer currency_of_id, Integer currency_for_id) {
		List<CurrencyHistory> currencyHistList = new ArrayList<CurrencyHistory>();
		String selectQuery = "SELECT "
				+ DatabaseHandler.KEY_CURRENCY_HISTORY_ID + ","
				+ DatabaseHandler.KEY_CURRENCY_HISTORY_RATE + ","
				+ DatabaseHandler.KEY_CURRENCY_HISTORY_DATE + ","
				+ DatabaseHandler.KEY_CURRENCY_OF_HISTORY_ID + ","
				+ DatabaseHandler.KEY_CURRENCY_FOR_HISTORY_ID + " FROM "
				+ DatabaseHandler.TABLE_CURRENCY_HISTORY 
				+ " WHERE "+ DatabaseHandler.KEY_CURRENCY_OF_HISTORY_ID + " = "
				+ currency_of_id 
				+ " AND "+ DatabaseHandler.KEY_CURRENCY_FOR_HISTORY_ID + " = "
				+ currency_for_id 
				+ " ORDER BY "+ DatabaseHandler.KEY_CURRENCY_HISTORY_ID + " DESC";

		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				CurrencyHistory currencyHistory = new CurrencyHistory(
						Integer.parseInt(cursor.getString(0)),
						Double.parseDouble(cursor.getString(1)),
						Long.parseLong(cursor.getString(2)),
						Integer.parseInt(cursor.getString(3)),
						Integer.parseInt(cursor.getString(4)));
				if(currencyHistList.size() == 0){
					currencyHistList.add(currencyHistory);
				}else if(currencyHistList.size() < 20
						&& !currencyHistList.get(currencyHistList.size()-1)
							.getHistoryRate().equals(currencyHistory.getHistoryRate())){
					currencyHistList.add(currencyHistory);
				}
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return currencyHistList;
	}

	public int getCurrencyHistoryListCount() {
		String countQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_CURRENCY;
		SQLiteDatabase db = databaseHandler.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		return cursor.getCount();
	}

}
