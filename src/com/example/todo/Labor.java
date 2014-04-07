package com.example.todo;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName("Labor")
public class Labor extends ParseObject {
	
	  public Labor(){}
	  
	  public boolean isDone(){
	      return getBoolean("done");
	  }

	  public void setDone(boolean done){
	      put("completed", done);
	  }

	  public void setLabor(String labor){
	      put("labor", labor);
	  }

	 public String getLabor(){
	      return getString("labor");
	  }
	 
	 public void setUser(ParseUser user){
		 put("user", user);
	 }
	

}
