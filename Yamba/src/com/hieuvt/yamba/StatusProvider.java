package com.hieuvt.yamba;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class StatusProvider extends ContentProvider {

	private static final String TAG = StatusProvider.class.getSimpleName();
	private DbHelper dbHelper;

	private static final UriMatcher sURIMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(StatusContract.AUTHORITY, StatusContract.TABLE,
				StatusContract.STATUS_DIR);
		sURIMatcher.addURI(StatusContract.AUTHORITY, StatusContract.TABLE + "/#",
				StatusContract.STATUS_ITEM);
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		dbHelper = new DbHelper(getContext());
		Log.d(TAG, "onCreated");
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(StatusContract.TABLE);

		switch (sURIMatcher.match(uri)) {
		case StatusContract.STATUS_DIR:
			break;
		case StatusContract.STATUS_ITEM:
			qb.appendWhere(StatusContract.Column.ID + "="
					+ uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Illegal uri: " + uri);
		}

		String orderBy = (TextUtils.isEmpty(sortOrder)) ? StatusContract.DEFAULT_SORT
				: sortOrder;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = qb.query(db, projection, selection, selectionArgs,
				null, null, orderBy);
		
		cursor.setNotificationUri(getContext().getContentResolver(), uri);		
		Log.d(TAG, "queried records: "+cursor.getCount());
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (sURIMatcher.match(uri)) {
		case StatusContract.STATUS_DIR:
			Log.d(TAG, "gotType: " + StatusContract.STATUS_TYPE_DIR);
			return StatusContract.STATUS_TYPE_DIR;
		case StatusContract.STATUS_ITEM:
			Log.d(TAG, "gotType: " + StatusContract.STATUS_ITEM);
			return StatusContract.STATUS_TYPE_ITEM;
		default:
			throw new IllegalArgumentException("Illegal uri: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub

		Uri ret = null;

		// Assert correct uri //
		if (sURIMatcher.match(uri) != StatusContract.STATUS_DIR) {
			throw new IllegalArgumentException("Illegal uri: " + uri);
		}
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId = db.insertWithOnConflict(StatusContract.TABLE, null,
				values, SQLiteDatabase.CONFLICT_IGNORE);

		// Was insert successful?
		if (rowId != -1) {
//			long id = values.getAsLong(StatusContract.Column.ID);
//			ret = ContentUris.withAppendedId(uri, id);
			ret = ContentUris.withAppendedId(uri, rowId);
			Log.d(TAG, "inserted uri: " + ret);
			// Notify that data for this uri has changed
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return ret;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub

		String where;

		switch (sURIMatcher.match(uri)) {
		case StatusContract.STATUS_DIR:
			// so we count deleted rows
			where = (selection == null) ? "1" : selection;
			break;
		case StatusContract.STATUS_ITEM:
			long id = ContentUris.parseId(uri);
			where = StatusContract.Column.ID
					+ "="
					+ id
					+ (TextUtils.isEmpty(selection) ? "" : " and ( "
							+ selection + " )");
			break;
		default:
			throw new IllegalArgumentException("Illegal uri: " + uri);
		}
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int ret = db.delete(StatusContract.TABLE, where, selectionArgs);

		if (ret > 0) {
			// Notify that data for this uri has changed
			getContext().getContentResolver().notifyChange(uri, null);
		}
		Log.d(TAG, "deleted records: " + ret);

		return ret;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub

		String where;
		switch (sURIMatcher.match(uri)) {
		case StatusContract.STATUS_DIR:
			where = selection;
			break;
		case StatusContract.STATUS_ITEM:
			long id = ContentUris.parseId(uri);
			where = StatusContract.Column.ID
					+ "="
					+ id
					+ (TextUtils.isEmpty(selection) ? "" : " and ( "
							+ selection + " )");
			break;
		default:
			throw new IllegalArgumentException("Illegal uri: " + uri);
		}
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int ret = db.update(StatusContract.TABLE, values, where, selectionArgs);

		if (ret > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return ret;
	}

}
