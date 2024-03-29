package com.hieuvt.yamba;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

public class TimelineFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private static final String TAG = TimelineFragment.class.getSimpleName();
	private static final String[] FROM = { StatusContract.Column.USER,
			StatusContract.Column.MESSAGE, StatusContract.Column.CREATED_AT };
	private static final int[] TO = { R.id.list_item_text_user,
			R.id.list_item_text_message, R.id.list_item_text_created_at };
	private static final int LOADER_ID = 42;
	private SimpleCursorAdapter mAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item,
				null, FROM, TO, 0);
		// mAdapter.setViewBinder(new TimelineViewBinder());
		mAdapter.setViewBinder(null);
		setListAdapter(mAdapter);
		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		if (id != LOADER_ID) {
			return null;
		}
		Log.d(TAG, "onCreateLoader");

		return new CursorLoader(getActivity(), StatusContract.CONTENT_URI,
				null, null, null, StatusContract.DEFAULT_SORT);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// TODO Auto-generated method stub
		// Get the details fragment
		DetailsFragment fragment = (DetailsFragment) getFragmentManager()
				.findFragmentById(R.id.fragment_details);
		// Is details fragment visible?
		if (fragment != null && fragment.isVisible() && data.getCount() == 0) {
			fragment.updateView(-1);
			Toast.makeText(getActivity(), "No data", Toast.LENGTH_LONG).show();
		}

		Log.d(TAG, "onLoadFinished with cursor: " + data.getCount());

		mAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(null);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		// Get the details fragment
		Log.d(TAG, "onListItemClicked");
		DetailsFragment fragment = (DetailsFragment) getFragmentManager()
				.findFragmentById(R.id.fragment_details);
		// Is details fragment visible?
//		if (fragment != null && fragment.isVisible()) { //
//			fragment.updateView(id); //
//		} else {
			startActivity(new Intent(getActivity(), DetailsActivity.class)
					.putExtra(StatusContract.Column.ID, id)); //
//		}

	}

	private static final class TimelineViewBinder implements ViewBinder {

		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			// TODO Auto-generated method stub
			if (view.getId() != R.id.list_item_text_created_at) {
				return false;
			}
			long timestamp = cursor.getLong(columnIndex);
			CharSequence relativeTime = DateUtils
					.getRelativeTimeSpanString(timestamp);
			((TextView) view).setText(relativeTime);
			return true;
		}

	}
}
