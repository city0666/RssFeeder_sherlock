package com.example.adapter;
/**
 * This class defines a custom adapter for the ListView,
 * it uses the pattern "ViewHolder" which make the process
 * of loading new info for the row in the screen even more faster
 * actually is the fastest way, recommended by Romain Guy.
 * 
 * The ListView is updated in real time according to the rows being displayed in the screen,
 * and thanks to that and ViewHolder pattern the speed is maximum and memory 
 * usage is minimal.
 */

import java.util.ArrayList;
import java.util.HashMap;

import com.example.rssfeeder.Feeder_UI;
import com.example.rssfeeder.R;
import com.exmaple.util.RSS_StaticKeys;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RSS_CustomAdapter extends BaseAdapter{
	//private ;
	private ArrayList<HashMap<String,String>> data;
	
	public RSS_CustomAdapter () {
		data=new ArrayList<HashMap<String,String>>();
	}
	public RSS_CustomAdapter (ArrayList<HashMap<String,String>> dataIn) {
		data=dataIn;
	}
	
	public ArrayList<HashMap<String, String>> getData() {
		return data;
	}

	public void setData(ArrayList<HashMap<String, String>> data) {
		this.data = data;
		this.notifyDataSetChanged();
	}

	public int getCount() {
		return data.size();
	}
	public HashMap<String,String> getItem(int position) {
	    return data.get(position);
	}
	public long getItemId(int position) {
		return Long.parseLong(getItem(position).get(RSS_StaticKeys.KEY_ID));
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
	    Activity activity = Feeder_UI.getFeeder_UI();
	    LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        
	    if(convertView==null){
	    	convertView = inflater.inflate(R.layout.rss_row, null);
	        //We use a viewHolder to keep the reference
	        viewHolder = new ViewHolder();
	        viewHolder.track_name =(TextView)convertView.findViewById(R.id.track_name); // title
	        viewHolder.trackArtist =(TextView)convertView.findViewById(R.id.track_artist); // artist name
	        viewHolder.date = (TextView)convertView.findViewById(R.id.date);
	        //set the tag of the view to the view holder
	        convertView.setTag(viewHolder);
	        
	    }else{
        	viewHolder = (ViewHolder)convertView.getTag();
        }
	        
	    HashMap<String,String> song = data.get(position);
	        
        viewHolder.track_name.setText(song.get(RSS_StaticKeys.KEY_TRACK_NAME));
        viewHolder.trackArtist.setText(song.get(RSS_StaticKeys.KEY_ARTIST));
        viewHolder.date.setText(song.get(RSS_StaticKeys.KEY_PUB_DATE));
            
        return convertView;
	       
	    }

	  //ViewHolder class.
	    static class ViewHolder {
	    	
	    	TextView track_name;
	        TextView trackArtist;
	        TextView date;
	      
		}
}
    

	

