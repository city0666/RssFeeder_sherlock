package com.example.page_viewer;


import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Window;


import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.example.rssfeeder.R;
/**
 * Organize fragmets
 * @author Jachu
 *
 */

public class RSS_ViewPagerFragmentActivity extends SherlockFragmentActivity {
	private String url;
	private RSS_PagerAdapter mPagerAdapter;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//overridePendingTransition(R.anim.animation_left, R.anim.animation_left);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		actionBarConf();
		super.setContentView(R.layout.view_pager_layout);
		Bundle bundle = getIntent().getExtras();
		url = bundle.getString("uri"); 
		this.initialisePaging();
	}
	private void actionBarConf(){
    	//Action bar configuration
		getSupportActionBar().setTitle("RSS");
     	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    	getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
	}
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

	    int itemId = item.getItemId();
	    switch (itemId) {
	    case android.R.id.home:
	    	onBackPressed();
	        break;
	    }
	    return true;
	}
	private void initialisePaging() {
		List<Fragment> fragments = new Vector<Fragment>();
		Bundle arg1 = new Bundle();
		arg1.putString("uri", url);
		fragments.add(Fragment.instantiate(this, RSS_WebView_Frag.class.getName(),arg1));
		this.mPagerAdapter  = new RSS_PagerAdapter(super.getSupportFragmentManager(), fragments);
		ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
		pager.setAdapter(this.mPagerAdapter);
	}


	public void onBackPressed() {
	    //super.onBackPressed();
	    this.finish();
	   // overridePendingTransition(R.anim.animation_right, R.anim.animation_right);
	   
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

}
