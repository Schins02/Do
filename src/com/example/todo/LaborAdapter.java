package com.example.todo;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LaborAdapter extends ArrayAdapter<Labor> {
	private Context theContext;
	private List<Labor> theLabors;
	
	public LaborAdapter(Context context, List<Labor> labors) {
		super(context, R.layout.labor_row_item, labors);
		this.theContext = context;
		this.theLabors = labors;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView == null){
			LayoutInflater theLayoutInflater = LayoutInflater.from(theContext);
			convertView = theLayoutInflater.inflate(R.layout.labor_row_item, null);
		}
		
		Labor theLabor = theLabors.get(position);
		
		TextView descriptionView = (TextView) convertView.findViewById(R.id.labor_input);
		
		descriptionView.setText(theLabor.getLabor());
		
		if(theLabor.isDone()){
			descriptionView.setPaintFlags(descriptionView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}else{
			descriptionView.setPaintFlags(descriptionView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		}
		
		return convertView;
	}

}
