package com.gmail.maiconspas.cambioagora.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gmail.maiconspas.cambioagora.model.Currency;

public class CurrencyDao {

	DatabaseHandler databaseHandler;

	public CurrencyDao(Context context) {
		databaseHandler = new DatabaseHandler(context);
	}

	public Currency getCurrency(int id) {
		SQLiteDatabase db = databaseHandler.getReadableDatabase();

		Cursor cursor = db.query(DatabaseHandler.TABLE_CURRENCY, new String[] {
				DatabaseHandler.KEY_CURRENCY_ID,
				DatabaseHandler.KEY_CURRENCY_NAME,
				DatabaseHandler.KEY_CURRENCY_SYMBOL,
				DatabaseHandler.KEY_CURRENCY_LINK,
				DatabaseHandler.KEY_CURRENCY_NAME_SHORT,
				DatabaseHandler.KEY_CURRENCY_NAME_LONG},
				DatabaseHandler.KEY_CURRENCY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		Currency currency = null;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();

			currency = new Currency(
					Integer.parseInt(cursor.getString(0))
					,cursor.getString(1).toString()
					,cursor.getString(2).toString()
					,cursor.getString(3).toString()
					,cursor.getString(4).toString()
					,cursor.getString(5).toString());
		}
		cursor.close();
		db.close();
		return currency;
	}

	public Currency getCurrencyByShortName(String shortName) {
		SQLiteDatabase db = databaseHandler.getReadableDatabase();

		Cursor cursor = db.query(DatabaseHandler.TABLE_CURRENCY, new String[] {
				DatabaseHandler.KEY_CURRENCY_ID,
				DatabaseHandler.KEY_CURRENCY_NAME,
				DatabaseHandler.KEY_CURRENCY_SYMBOL,
				DatabaseHandler.KEY_CURRENCY_LINK,
				DatabaseHandler.KEY_CURRENCY_NAME_SHORT,
				DatabaseHandler.KEY_CURRENCY_NAME_LONG},
				DatabaseHandler.KEY_CURRENCY_NAME_SHORT + "=?",
				new String[] { String.valueOf(shortName) }, null, null, null, null);
		Currency currency = null;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();

			currency = new Currency(
					Integer.parseInt(cursor.getString(0))
					,cursor.getString(1).toString()
					,cursor.getString(2).toString()
					,cursor.getString(3).toString()
					,cursor.getString(4).toString()
					,cursor.getString(5).toString());
		}
		cursor.close();
		db.close();
		return currency;
	}

	public List<Currency> getAllCurrency() {
		List<Currency> currencyList = new ArrayList<Currency>();
		String selectQuery = "SELECT "+DatabaseHandler.KEY_CURRENCY_ID+","
				+DatabaseHandler.KEY_CURRENCY_NAME+","
				+DatabaseHandler.KEY_CURRENCY_SYMBOL+","
				+DatabaseHandler.KEY_CURRENCY_LINK+","
				+DatabaseHandler.KEY_CURRENCY_NAME_SHORT+","
				+DatabaseHandler.KEY_CURRENCY_NAME_LONG
				+" FROM " + DatabaseHandler.TABLE_CURRENCY
				+" ORDER BY "+DatabaseHandler.KEY_CURRENCY_NAME;

		SQLiteDatabase db = databaseHandler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {

				Currency currency = new Currency(
						Integer.parseInt(cursor.getString(0))
						,cursor.getString(1).toString()
						,cursor.getString(2).toString()
						,cursor.getString(3).toString()
						,cursor.getString(4).toString()
						,cursor.getString(5).toString());
				currencyList.add(currency);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return currencyList;
	}

	public int getCurrencyListCount() {
		String countQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_CURRENCY;
		SQLiteDatabase db = databaseHandler.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		return cursor.getCount();
	}

}
