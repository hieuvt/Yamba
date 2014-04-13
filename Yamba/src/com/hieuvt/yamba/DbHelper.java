package com.hieuvt.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	private static final String TAG = DbHelper.class.getSimpleName();

	public DbHelper(Context context) {
		super(context, StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		String sql = String
				.format("create table %s (%s integer primary key autoincrement, %s text, %s text, %s integer)",
						StatusContract.TABLE, StatusContract.Column.ID,
						StatusContract.Column.USER,
						StatusContract.Column.MESSAGE,
						StatusContract.Column.CREATED_AT);
		Log.d(TAG, "onCreate with SQL: "+sql);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists " + StatusContract.TABLE);
		onCreate(db);
	}

}
