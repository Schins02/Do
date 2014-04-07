package com.example.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class signUp extends Activity {

	
	private EditText userName;
	private EditText password;
	private TextView errorMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);

		userName = (EditText) findViewById(R.id.register_username);
		password = (EditText) findViewById(R.id.register_password);
		errorMsg = (TextView) findViewById(R.id.error_messages);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}*/

	public void register(final View signUpView){
		if(userName.getText().length() == 0 || password.getText().length() == 0)
			return;

		signUpView.setEnabled(false);
		ParseUser user = new ParseUser();
		user.setUsername(userName.getText().toString());
		user.setPassword(password.getText().toString());
		errorMsg.setText("");

		user.signUpInBackground(new SignUpCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					Intent intent = new Intent(signUp.this, MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					switch(e.getCode()){
					case ParseException.USERNAME_TAKEN:
						errorMsg.setText("This username already exists, please choose different one.");
						break;
					case ParseException.USERNAME_MISSING:
						errorMsg.setText("Please choose and enter a username to register");
						break;
					case ParseException.PASSWORD_MISSING:
						errorMsg.setText("Please choose and enter a password to register.");
						break;
					default:
						errorMsg.setText(e.getLocalizedMessage());
					}
					signUpView.setEnabled(true);
				}
			}
		});
	}

	public void showLogin(View v) {
		Intent intent = new Intent(this, signUp.class);
		startActivity(intent);
		finish();
	}
}