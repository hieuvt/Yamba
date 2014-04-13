package com.hieuvt.yamba;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.net.Uri;
import android.os.SystemClock;
import android.provider.BaseColumns;
import android.provider.Settings.System;
import android.text.format.Time;

public class StatusContract {

	public static final String DB_NAME = "timeline.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "status";

	// public static final String DEFAULT_SORT = Column.CREATED_AT + " DESC";
	public static final String DEFAULT_SORT = Column.ID + " DESC";

	public static final List<MyStatus> timeline = Arrays
			.asList(new MyStatus("Android1", "I am Android 1", "1.00"),
					new MyStatus(
							"Android2",
							"I am Android 2 I am Android 2 I am Android 2I am Android 2  I am Android 2 I am Android 2 I am Android 2",
							"2.00"), new MyStatus("Android3", "I am Android 3",
							"3.00"));

	public static final String AUTHORITY = "com.hieuvt.yamba.StatusProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE);
	public static final int STATUS_ITEM = 1;
	public static final int STATUS_DIR = 2;

	// MIME
	public static final String STATUS_TYPE_ITEM = "vnd.android.cursor.item/vnd.com.hieuvt.yamba.provider.status";
	public static final String STATUS_TYPE_DIR = "vnd.android.cursor.dir/vnd.com.hieuvt.yamba.provider.status";

	public class Column {
		public static final String ID = BaseColumns._ID;
		public static final String USER = "user";
		public static final String MESSAGE = "message";
		public static final String CREATED_AT = "created_at";
	}

}
