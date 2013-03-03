package com.example.page_viewer;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * Organize fragments,  we only use one (browser)
 * but it could be several, actually I am using fragments and not a new activity
 * cause I planned to show both links in the RSS, then I realized they were the same,
 * anyway, fragments are very useful especially for tablets.
 * 
 * @author Jachu
 *
 */
public class RSS_PagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	/**
	 * @param fm
	 * @param fragments
	 */
	public RSS_PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int position) {
		return this.fragments.get(position);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return this.fragments.size();
	}
	public CharSequence getPageTitle(int position) {
        return "Page #" + ( position + 1 );
    }
}
