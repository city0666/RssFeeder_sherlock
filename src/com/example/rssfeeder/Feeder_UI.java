package com.example.rssfeeder;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.adapter.RSS_CustomAdapter;
import com.example.dialog.RSS_aboutMe;
import com.example.page_viewer.RSS_ViewPagerFragmentActivity;
import com.example.serverUtil.RSS_server_util;
import com.exmaple.util.RSS_StaticKeys;
/**
 * This class is implemented using Singleton (pattern) in order to make easier data sharing
 * between activities, we can do this since the info passed are very simple and we are not tacking risks,
 * specially talking about data consistency. * 
 * 
 * Basically it implements the ListView, all songs and their metadata are stored in a HashMap
 * (one per song), and every HashMap into an ArrayList, this structure is perfect, it is flexible, unboned, easy to modify
 *  in the future, and memory usage is really nice to!.
 * 
 * Example: 
 * We store one song
 * Song = new HashsMap <-(Tile:Beeper, Author: TheCounts..., Date:31.03.08, Link: www....)
 * ArrayList.add(Song).
 * 
 * We access to first song title.
 * Title(1)<-ArrayList.get(1).get("Title")
 * 
 * It also implements ActionBar, a design pattern introduced in Andorid since 3.0, especially thought
 * for tablets without menu button, in 2.3 ActionBar was not included in, this is a compatibility problem, solution: 
 * 			 Sherlock Action Bar, wonderful widget that adapts itself acording the Andorid version to show an action bar.
 * 
 * @author Jachu
 *
 */
public class Feeder_UI extends SherlockFragmentActivity {
	//singleton
	private static Feeder_UI singleton  = null;;
	//variables
	private ListView mainListView;
	private RSS_CustomAdapter adapter;
	private static Boolean debugMode = true;
	private String feedURL = "http://www.shazam.com/music/web/taglistrss?mode=xml&userName=shazam";

	
	public static Feeder_UI getFeeder_UI() {
		if (singleton == null){
			singleton= new Feeder_UI(); //This only executes if singleton does not exist
			if(debugMode){
				Log.i("Feeder UI instande","Creating new instance");
			}
		}
		if(debugMode){
			Log.i("Feeder UI instande","Returning instance");
		}
		return singleton;
	}
	      
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        actionBarConf();
       //Color improvement
	    getWindow().getAttributes().format = android.graphics.PixelFormat.RGBA_8888;
        singleton = this;
        setContentView(R.layout.activity_feeder__ui);
        mainListView = (ListView) findViewById(R.id.list);
        mainListView.setOnItemClickListener(getInItemClickListener());
        RSS_server_util server = new  RSS_server_util();
        adapter = new RSS_CustomAdapter();
        mainListView.setAdapter(adapter);
        server.execute();
        //Improve colors
        //mainListView.getBackground().setDither(true);
    }

    private OnItemClickListener getInItemClickListener(){
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,long id) {
				
				String url = adapter.getItem(pos).get(RSS_StaticKeys.KEY_GUID);
				if(debugMode)
					Log.i("Item Pressed","Openning link: "+url);
				
				Intent intent = new Intent(Feeder_UI.this,RSS_ViewPagerFragmentActivity.class);
				intent.putExtra("uri", url);
				startActivity(intent);
				//overridePendingTransition(R.anim.animation_left, R.anim.animation_right);
							
			}
		};
	}
    private void actionBarConf(){
    	//Action bar configuration
    	getSupportActionBar().setTitle("RSS");
    	getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false );
    	getSupportActionBar().setDisplayShowCustomEnabled(true);
    }
  
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	getSupportMenuInflater().inflate(R.menu.activity_feeder__ui, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

	    int itemId = item.getItemId();
	    switch (itemId) {
	    case R.id.about_me:
	    	RSS_aboutMe Dialog = new RSS_aboutMe();
			Dialog.show(getSupportFragmentManager(),"About me");
	        return true;
	    
	    default:
	    return super.onOptionsItemSelected(item);
	    }
	}
    
    protected  void onPause() {
		super.onPause();
	}

	protected  void onResume() {
		super.onResume();
	
	}
	protected void onStop() {
		super.onStop();
	}

	public ListView getMainListView() {
		return mainListView;
	}

	public void setMainListView(ListView mainListView) {
		this.mainListView = mainListView;
	}

	public Boolean getDebugMode() {
		return debugMode;
	}

	public void setDebugMode(Boolean debugMode) {
		this.debugMode = debugMode;
	}

	public String getFeedURL() {
		return feedURL;
	}

	public void setFeedURL(String feedURL) {
		this.feedURL = feedURL;
	}

	public RSS_CustomAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(RSS_CustomAdapter adapter) {
		this.adapter = adapter;
	}
	
	
    
}
