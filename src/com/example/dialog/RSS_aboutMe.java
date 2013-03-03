package com.example.dialog;

import com.example.rssfeeder.Feeder_UI;
import com.example.rssfeeder.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Toast;
/**
 * Just a joke, but it also shows an example of dialogs,
 * a good tool to show results and problems
 * @author Jachu
 *
 */
public class RSS_aboutMe extends DialogFragment {
	 
    public Dialog onCreateDialog(Bundle savedInstanceState) {
      
    	View view =View.inflate(Feeder_UI.getFeeder_UI(),R.layout.dialog_about_me, null);
			
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	       builder.setTitle("Hire?")
	       	   .setView(view)
	       	   .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
	       		  
	       		   public void onClick(DialogInterface dialog, int id) {
	       			Toast.makeText(Feeder_UI.getFeeder_UI(), "Yuhu!", Toast.LENGTH_SHORT).show();
	               }
	              })
	            .setNegativeButton("Maybe", new DialogInterface.OnClickListener() {
	            	 
	       		   public void onClick(DialogInterface dialog, int id) {
	       			 Toast.makeText(Feeder_UI.getFeeder_UI(), "Think twice!", Toast.LENGTH_SHORT).show();
	               }
	              });
       // Create the AlertDialog object and return it
       return builder.create();
   }
}
