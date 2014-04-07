package com.example.todo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseQuery.CachePolicy;

public class MainActivity extends Activity implements OnItemClickListener {


//hambo
//gord
	
	private EditText laborInput;
	private ListView theListView;
	private LaborAdapter theAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Parse.initialize(this, "n1AH5Rv42AWQvMJC9L1RvbAPmYNn4hwZXVWVOYhI",
				"MVsvPfGl7Ha7JexozjX3cBIolMV3LIdCY5DhWZsB");
		ParseAnalytics.trackAppOpened(getIntent());
		ParseObject.registerSubclass(Labor.class);

		ParseUser currentUser = ParseUser.getCurrentUser();
		if(currentUser == null){
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);
		finish();
		}

		theAdapter = new LaborAdapter(this, new ArrayList<Labor>());

		laborInput = (EditText) findViewById(R.id.labor_text);
		theListView = (ListView) findViewById(R.id.labor_list);

		theListView.setAdapter(theAdapter);
		updateData();



	}

	public void updateData() {
		ParseQuery<Labor> query = ParseQuery.getQuery(Labor.class);
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);

		query.findInBackground(new FindCallback<Labor>() {
			@Override
			public void done(List<Labor> labors, ParseException error) {
				if (labors != null) {
					theAdapter.clear();
					for (int i = 0; i < labors.size(); i++) {
						theAdapter.add(labors.get(i));
					}
				}
			}
		});
	}

	public void createLabor(View v) {
		if (laborInput.getText().length() > 0) {
			Labor newLabor = new Labor();
			newLabor.setACL(new ParseACL(ParseUser.getCurrentUser()));
			newLabor.setUser(ParseUser.getCurrentUser());
			newLabor.setLabor(laborInput.getText().toString());
			newLabor.setDone(false);
			newLabor.saveEventually();
			theAdapter.insert(newLabor, 0);
			laborInput.setText("");
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Labor labor = theAdapter.getItem(position);
		TextView laborInput = (TextView) view.findViewById(R.id.labor_input);
		

		labor.setDone(!labor.isDone());

		if (labor.isDone()) {
			laborInput.setPaintFlags(laborInput.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
		} else {
			laborInput.setPaintFlags(laborInput.getPaintFlags()
					& (~Paint.STRIKE_THRU_TEXT_FLAG));
		}
		labor.saveEventually();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
