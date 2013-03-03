package com.example.serverUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.rssfeeder.Feeder_UI;
import com.exmaple.util.RSS_StaticKeys;
/**
 * Class that runs an AyncTaks, the best tool to run short task in a different thread,
 * it has 3 methods, postExecute, doItBackgopund, and preExecute, this is fantastic since the first one
 * and the last one runs on the UI thread, after and before the second one (which runs in a different thread) and sharing all the info
 * it makes things easier in order to keep data consistency.
 * 
 * @author Jachu
 *
 */
public class RSS_server_util extends AsyncTask<Void, Integer,InputStream> {
	
	private final static String feedUrl= Feeder_UI.getFeeder_UI().getFeedURL();
	private final static  Boolean debugMode = Feeder_UI.getFeeder_UI().getDebugMode();
	
	@Override
	//runs in a new thread
	protected InputStream doInBackground(Void... arg0) {
		Thread.currentThread().setName("Reciving info");
		try {
		
			Log.i("Coencxion","reating conexion");
			URL url = new URL(feedUrl);
			return url.openConnection().getInputStream();
			
		} catch (IOException e) {
			Log.e("RSS calling","Error oppening URL");
			//There is another way checking available internet or not, but this is working even when the signal is poor
			Toast.makeText(Feeder_UI.getFeeder_UI(), "No internet conexion", Toast.LENGTH_SHORT).show();
			if(debugMode)
				e.printStackTrace();
			return null;
		}
	}
	//runs on UI thread
	protected void onPostExecute(InputStream result){
		HashMap<String,String> song= null;
		ArrayList<HashMap<String,String>> songList = new ArrayList<HashMap<String,String>>(); 

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(false);
			XmlPullParser xpp = factory.newPullParser();
			// We will get the XML from an input stream
			xpp.setInput(result,"ISO-8859-1");
			boolean insideItem = false;
			// Returns the type of current event: START_TAG, END_TAG, etc..
			int eventType = xpp.getEventType();
			
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					
					if (xpp.getName().equalsIgnoreCase(RSS_StaticKeys.KEY_ITEM)) {
						insideItem = true;
						song= new HashMap<String,String>();
					} else if (xpp.getName().equalsIgnoreCase(RSS_StaticKeys.KEY_ARTIST)) {
						if (insideItem){
							song.put(RSS_StaticKeys.KEY_ARTIST,xpp.nextText());
							if(debugMode)
								Log.i("RSS artist: ",song.get(RSS_StaticKeys.KEY_ARTIST));
						}
						
					} else if (xpp.getName().equalsIgnoreCase(RSS_StaticKeys.KEY_GUID)) {
						if (insideItem){
							song.put(RSS_StaticKeys.KEY_GUID,xpp.nextText());
							if(debugMode)
								Log.i("RSS artist: ",song.get(RSS_StaticKeys.KEY_GUID));
						}
						
					}else if (xpp.getName().equalsIgnoreCase(RSS_StaticKeys.KEY_ID)) {
						if (insideItem){
							song.put(RSS_StaticKeys.KEY_ID,xpp.nextText());
							if(debugMode)
								Log.i("RSS artist: ",song.get(RSS_StaticKeys.KEY_ID));
						}
						
					}else if (xpp.getName().equalsIgnoreCase(RSS_StaticKeys.KEY_LINK)) {
						if (insideItem){
							song.put(RSS_StaticKeys.KEY_LINK,xpp.nextText());
							if(debugMode)
								Log.i("RSS artist: ",song.get(RSS_StaticKeys.KEY_LINK));
						}
						
					}else if (xpp.getName().equalsIgnoreCase(RSS_StaticKeys.KEY_PUB_DATE)) {
						if (insideItem){
							song.put(RSS_StaticKeys.KEY_PUB_DATE,xpp.nextText());
							if(debugMode)
								Log.i("RSS artist: ",song.get(RSS_StaticKeys.KEY_PUB_DATE));
						}
						
					}else if (xpp.getName().equalsIgnoreCase(RSS_StaticKeys.KEY_TRACK_NAME)) {
						if (insideItem){
							song.put(RSS_StaticKeys.KEY_TRACK_NAME,xpp.nextText());
							if(debugMode)
								Log.i("RSS artist: ",song.get(RSS_StaticKeys.KEY_TRACK_NAME));
						}
						
					}else if (xpp.getName().equalsIgnoreCase(RSS_StaticKeys.KEY_GUID)) {
						if (insideItem){
							song.put(RSS_StaticKeys.KEY_GUID,xpp.nextText());
							if(debugMode)
								Log.i("RSS artist: ",song.get(RSS_StaticKeys.KEY_GUID));
						}
					}
					
				}else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase(RSS_StaticKeys.KEY_ITEM)){
					insideItem=false;
					if(song != null)
						songList.add(song);
				}
				eventType = xpp.next(); //move to next element
			}
		
		}catch(MalformedURLException e){ 
			Log.e("RSS calling","Bad URL");
			if(debugMode)
				e.printStackTrace();
		}catch(XmlPullParserException e){ 
			Log.e("RSS calling","Bad XML");
			if(debugMode)
				e.printStackTrace();
		}catch(IOException e){ 
			Log.e("RSS calling","Error reading XML");
			if(debugMode)
				e.printStackTrace();
		}
		//This part of code runs on UI thread, update if it the first time, update if not (for the future refresh)
		//future pullDowntorefresh
		if(Feeder_UI.getFeeder_UI().getAdapter().getData()==null)
			Feeder_UI.getFeeder_UI().getAdapter().setData(songList);
		else
			Feeder_UI.getFeeder_UI().getAdapter().getData().addAll(songList);
			Feeder_UI.getFeeder_UI().getAdapter().notifyDataSetChanged();
	}
}
