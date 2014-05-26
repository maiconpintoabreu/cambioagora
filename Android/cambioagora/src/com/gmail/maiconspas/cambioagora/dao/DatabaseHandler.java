package com.gmail.maiconspas.cambioagora.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 2;

	// Database Name
	private static final String DATABASE_NAME = "currency_table";

	// Contacts table name
	public static final String TABLE_CURRENCY = "currency";
	public static final String TABLE_CURRENCY_HISTORY = "currency_history";

	// Contacts Table Columns names
	public static final String KEY_CURRENCY_ID = "id";
	public static final String KEY_CURRENCY_NAME = "name";
	public static final String KEY_CURRENCY_NAME_LONG = "name_long";
	public static final String KEY_CURRENCY_NAME_SHORT = "name_short";
	public static final String KEY_CURRENCY_SYMBOL = "symbol";
	public static final String KEY_CURRENCY_LINK = "link";
	
	public static final String KEY_CURRENCY_HISTORY_ID = "id";
	public static final String KEY_CURRENCY_OF_HISTORY_ID = "currency_of_id";
	public static final String KEY_CURRENCY_FOR_HISTORY_ID = "currency_for_id";
	public static final String KEY_CURRENCY_HISTORY_DATE = "history_date";
	public static final String KEY_CURRENCY_HISTORY_RATE = "history_rate";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CURRENCY_TABLE = "CREATE TABLE " + TABLE_CURRENCY + "("
				+ KEY_CURRENCY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ KEY_CURRENCY_NAME + " TEXT," 
				+ KEY_CURRENCY_SYMBOL + " TEXT,"
				+ KEY_CURRENCY_LINK + " TEXT," 
				+ KEY_CURRENCY_NAME_LONG + " TEXT," 
				+ KEY_CURRENCY_NAME_SHORT + " TEXT"
				+ ")";
		db.execSQL(CREATE_CURRENCY_TABLE);
		
		String CREATE_CURRENCY_HISTORY_TABLE = "CREATE TABLE " + TABLE_CURRENCY_HISTORY + "("
				+ KEY_CURRENCY_HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ KEY_CURRENCY_HISTORY_DATE + " INTEGER," 
				+ KEY_CURRENCY_OF_HISTORY_ID + " INTEGER,"
				+ KEY_CURRENCY_FOR_HISTORY_ID + " INTEGER,"
				+ KEY_CURRENCY_HISTORY_RATE + " REAL"
				+ ")";
		db.execSQL(CREATE_CURRENCY_HISTORY_TABLE);

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_CURRENCY_NAME, "Real");
		values.put(DatabaseHandler.KEY_CURRENCY_SYMBOL, "R$");
		values.put(DatabaseHandler.KEY_CURRENCY_LINK, "http://brl.fx-exchange.com/rss.xml");
		values.put(DatabaseHandler.KEY_CURRENCY_NAME_LONG, "Brazilian Real");
		values.put(DatabaseHandler.KEY_CURRENCY_NAME_SHORT, "BRL");
		db.insert(DatabaseHandler.TABLE_CURRENCY, null, values);
		
		values = new ContentValues();
		values.put(DatabaseHandler.KEY_CURRENCY_NAME, "Euro");
		values.put(DatabaseHandler.KEY_CURRENCY_SYMBOL, "€");
		values.put(DatabaseHandler.KEY_CURRENCY_LINK, "http://eur.fx-exchange.com/rss.xml");
		values.put(DatabaseHandler.KEY_CURRENCY_NAME_LONG, "Euro");
		values.put(DatabaseHandler.KEY_CURRENCY_NAME_SHORT, "EUR");
		db.insert(DatabaseHandler.TABLE_CURRENCY, null, values);
		
		values = new ContentValues();
		values.put(DatabaseHandler.KEY_CURRENCY_NAME, "US Dollar");
		values.put(DatabaseHandler.KEY_CURRENCY_SYMBOL, "$");
		values.put(DatabaseHandler.KEY_CURRENCY_LINK, "http://usd.fx-exchange.com/rss.xml");
		values.put(DatabaseHandler.KEY_CURRENCY_NAME_LONG, "United States Dollar");
		values.put(DatabaseHandler.KEY_CURRENCY_NAME_SHORT, "USD");
		db.insert(DatabaseHandler.TABLE_CURRENCY, null, values);
		
		values = new ContentValues();
		values.put(DatabaseHandler.KEY_CURRENCY_NAME, "Libra");
		values.put(DatabaseHandler.KEY_CURRENCY_SYMBOL, "£");
		values.put(DatabaseHandler.KEY_CURRENCY_LINK, "http://gbp.fx-exchange.com/rss.xml");
		values.put(DatabaseHandler.KEY_CURRENCY_NAME_LONG, "British Pound Sterling");
		values.put(DatabaseHandler.KEY_CURRENCY_NAME_SHORT, "GBP");
		db.insert(DatabaseHandler.TABLE_CURRENCY, null, values);

	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCY_HISTORY);
		// Create tables again
		onCreate(db);
	}
}
