package com.gmail.maiconspas.cambioagora.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gmail.maiconspas.cambioagora.model.CurrencyHistory;

public class CurrencyHistoryDao {

	DatabaseHandler databaseHandler;

	public CurrencyHistoryDao(Context context) {
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
		List<CurrencyHistory> currencyList = new ArrayList<CurrencyHistory>();
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
				currencyList.add(currencyHistory);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return currencyList;
	}

	public List<CurrencyHistory> getAllCurrencyHistoryByPreference(
			Integer currency_to_id) {
		List<CurrencyHistory> currencyList = new ArrayList<CurrencyHistory>();
		String selectQuery = "SELECT "
				+ DatabaseHandler.KEY_CURRENCY_HISTORY_ID + ","
				+ DatabaseHandler.KEY_CURRENCY_HISTORY_RATE + ","
				+ DatabaseHandler.KEY_CURRENCY_HISTORY_DATE + ","
				+ DatabaseHandler.KEY_CURRENCY_OF_HISTORY_ID + ","
				+ DatabaseHandler.KEY_CURRENCY_FOR_HISTORY_ID + " FROM "
				+ DatabaseHandler.TABLE_CURRENCY_HISTORY 
				+ " WHERE "+ DatabaseHandler.KEY_CURRENCY_OF_HISTORY_ID + " = "
				+ currency_to_id 
				+ " ORDER BY "+ DatabaseHandler.KEY_CURRENCY_HISTORY_ID+ " DESC"
				+ " LIMIT 3";

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
				currencyList.add(currencyHistory);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return currencyList;
	}

	public int getCurrencyHistoryListCount() {
		String countQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_CURRENCY;
		SQLiteDatabase db = databaseHandler.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		return cursor.getCount();
	}

}
