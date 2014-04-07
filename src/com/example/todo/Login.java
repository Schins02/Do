package com.example.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends Activity {

	private EditText username;
	private EditText password;
	private TextView errorMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		errorMsg = (TextView) findViewById(R.id.error_messages);
	}

		public void signIn(final View loginView){
		loginView.setEnabled(false);
		ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					Intent intent = new Intent(Login.this, MainActivity.class);
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
					case ParseException.OBJECT_NOT_FOUND:
						errorMsg.setText("Incorrect login info.");
						break;
					default:
						errorMsg.setText(e.getLocalizedMessage());
						break;
					}
					loginView.setEnabled(true);
				}
			}
		});
	}

	public void showRegistration(View v) {
	Intent intent = new Intent(this, signUp.class);
	startActivity(intent);
	finish();
	}


}
