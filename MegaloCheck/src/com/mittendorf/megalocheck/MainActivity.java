package com.mittendorf.megalocheck;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	//---mix view variables---
	//O2
	public static int MainOxygenPercent = 0;
	public static int MainOxygenCylVolume = 0;
	public static int MainOxygenPSI = 0;
	public static double MainOxygenMetab = 0.8; //in preferences
	public static double MinutesOfOxy = 0;
	
	//Diluent
	public static double MainMix_DillO2num = 0;
	public static int MainMix_DillHEnum = 0;
	public static int MainMix_DillPSINum = 0;
	public static double MainMixDillPO2set = 1.0;  //in preferences
	public static double DillMOD = 0;
	
	//Bailout
	public static double MainMix_Bai1O2num = 0;
	public static int MainMix_BailHEnum = 0;
	public static int MainMix_BailPSINum = 0;
	public static double MainMixBailPO2set = 1.4;  //in preferences
	public static double BailMod = 0;
	
	//Deco
	public static int MainMix_DecoPSI = 0;
	public static double MainMix_DecoO2num = 0;
	public static int MainMix_DecoHEnum = 0;
	public static double MainMixDecoPO2set = 1.6;  //in preferences
	public static double DecoMod = 0;
	
	//O2 Sensors
	public static double MainSensor_1_ambient = 0;
	public static double MainSensor_2_ambient = 0;
	public static double MainSensor_3_ambient = 0;
	public static double MainSensor_1_saturated = 0;
	public static double MainSensor_2_saturated = 0;
	public static double MainSensor_3_saturated = 0;
			
	//house variables
	public static boolean gascheck=false;
	public static boolean Page2ChecksComplete = false;
	public static boolean Page3ChecksComplete = false;
	public static int ScrubberAccum = 10;					//in preferences
	
	
	
	
	
	//load the layout
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
//WIP	//load default preferences
		PreferenceManager.setDefaultValues(this, R.xml.preferences, true);
		SharedPreferences DP = PreferenceManager.getDefaultSharedPreferences(this);
		
		//MainActivity.ScrubberAccum = Integer.parseInt(DP.getString("ScrubberAccum", "242"));
		MainActivity.MainOxygenMetab = Double.parseDouble(DP.getString("MainOxygenMetab", ".5"));
		MainActivity.MainMixDillPO2set = Double.parseDouble(DP.getString("MainMixDillPO2set", "1.9"));
		MainActivity.MainMixBailPO2set = Double.parseDouble(DP.getString("MainMixBailPO2set", "1.9"));
		MainActivity.MainMixDecoPO2set = Double.parseDouble(DP.getString("MainMixDecoPO2set", "1.9"));
		
		//only update this the first time
		//PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(PreferenceManager.KEY_HAS_SET_DEFAULT_VALUES, true).commit();
		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PreferenceManager.KEY_HAS_SET_DEFAULT_VALUES, true)){
			MainActivity.ScrubberAccum = Integer.parseInt(DP.getString("ScrubberAccum", "242"));
		};
		
		
		//need to add sensors to default parameters
	   // <EditTextPreference android:key="S1_genesis" android:defaultValue="365"/>
	   //<EditTextPreference android:key="S2_genesis" android:defaultValue="365"/>
	   // <EditTextPreference android:key="S3_genesis" android:defaultValue="365"/>
		

	    
	    
	    
		//initialize main buttons
		Button bc = (Button) findViewById(R.id.button_Calibration);
		Button gas = (Button) findViewById(R.id.button_Gas);
		Button sys = (Button) findViewById(R.id.button_System);
		
		//initialize main fields
		TextView o2press = (TextView) findViewById(R.id.tx_O2Press);
		TextView dillpress = (TextView) findViewById(R.id.tx_DillPress);
		TextView bailpress = (TextView) findViewById(R.id.tx_BailPress);
		TextView decopress = (TextView) findViewById(R.id.tx_DecoPress);
		TextView oxymin = (TextView) findViewById(R.id.tx_O2Remain);
		TextView dillmod = (TextView) findViewById(R.id.tx_DillMOD);
		TextView dillmix = (TextView) findViewById(R.id.tx_DillMix);
		TextView bailmod = (TextView) findViewById(R.id.tx_BailMOD);
		TextView bailmix = (TextView) findViewById(R.id.tx_BailMix);
		TextView decomod = (TextView) findViewById(R.id.tx_DecoMOD);
		TextView decomix = (TextView) findViewById(R.id.tx_DecoMix);
		TextView scrubcal = (TextView) findViewById(R.id.tx_scrubberCal);
		TextView date = (TextView) findViewById(R.id.date_preformed);
	
		
		//Calibrate button color 
		if(MainActivity.Page2ChecksComplete){
			bc.setTextColor(Color.rgb(34, 139, 34));
			}
			else{bc.setTextColor(Color.RED);
			}
		
		//Gas button color
		if(MainActivity.gascheck){
			gas.setTextColor(Color.rgb(34, 139, 34));
			}
		else {gas.setTextColor(Color.RED);
			}
		
		//System button color
		if(MainActivity.Page3ChecksComplete){
			sys.setTextColor(Color.rgb(34, 139, 34));
			}
		else {sys.setTextColor(Color.RED);
			}
		
		//Set Status text and color
		TextView txstatus =  (TextView) findViewById(R.id.tx_Status);
		if(MainActivity.gascheck & MainActivity.Page2ChecksComplete & MainActivity.Page3ChecksComplete){
			txstatus.setText("Status: System Ready");
			txstatus.setTextColor(Color.rgb(34, 139, 34));}
		else{
			txstatus.setTextColor(Color.RED);
		}
		
		//do other stuff
		scrubcal.setText(Integer.toString(MainActivity.ScrubberAccum)+ "Min");
		o2press.setText(Integer.toString(MainActivity.MainOxygenPSI)+ " psi");
		dillpress.setText(Integer.toString(MainActivity.MainMix_DillPSINum)+ " psi");
		bailpress.setText(Integer.toString(MainActivity.MainMix_BailPSINum)+ " psi");
		decopress.setText(Integer.toString(MainActivity.MainMix_DecoPSI)+ " psi");
		dillmix.setText(Double.toString(MainActivity.MainMix_DillO2num).format("%02.0f", MainActivity.MainMix_DillO2num)+"/"+(Integer.toString(MainActivity.MainMix_DillHEnum).format("%02d", MainActivity.MainMix_DillHEnum)));
		bailmix.setText(Double.toString(MainActivity.MainMix_Bai1O2num).format("%02.0f", MainActivity.MainMix_Bai1O2num)+"/"+(Integer.toString(MainActivity.MainMix_BailHEnum).format("%02d", MainActivity.MainMix_BailHEnum)));
		decomix.setText(Double.toString(MainActivity.MainMix_DecoO2num).format("%02.0f", MainActivity.MainMix_DecoO2num)+"/"+(Integer.toString(MainActivity.MainMix_DecoHEnum).format("%02d", MainActivity.MainMix_DecoHEnum)));
		
		
		//oxygen metabolism minutes
		MainActivity.MinutesOfOxy = MainActivity.MainOxygenCylVolume * (MainActivity.MainOxygenPSI*0.0689)/MainActivity.MainOxygenMetab;
		oxymin.setText(Double.toString(MainActivity.MinutesOfOxy).format("%.0f", MainActivity.MinutesOfOxy) + "Min");
		
		//diluent MOD
		if(MainActivity.MainMix_DillO2num < 1){
			dillmod.setText("No Dill");
		}else
			{
			MainActivity.DillMOD = ((MainActivity.MainMixDillPO2set) / ((MainActivity.MainMix_DillO2num/100)) - 1 ) * 33;
			dillmod.setText(Double.toString(MainActivity.DillMOD).format("%.0f", MainActivity.DillMOD) + "MOD");
			};
			
		//Bailout MOD
		if(MainActivity.MainMix_Bai1O2num < 1){
			bailmod.setText("No Bail");
		}else
			{
			MainActivity.BailMod = ((MainActivity.MainMixBailPO2set) / ((MainActivity.MainMix_Bai1O2num/100)) - 1 ) * 33;
			bailmod.setText(Double.toString(MainActivity.BailMod).format("%.0f", MainActivity.BailMod) + "MOD");
			};	

		//deco MOD
		if(MainActivity.MainMix_DecoO2num < 1){
			decomod.setText("No Deco");
		}else
			{
			MainActivity.DecoMod = ((MainActivity.MainMixDecoPO2set) / ((MainActivity.MainMix_DecoO2num/100)) - 1 ) * 33;
			decomod.setText(Double.toString(MainActivity.DecoMod).format("%.0f", MainActivity.DecoMod) + "MOD");
			};	
			
			//give feedback on status/workflow
			if (!gascheck ){
				date.setText("Analyze/Enter Gas");
			} else if(!Page2ChecksComplete & gascheck){
				date.setText("Calibrate Head");
			}else if(!Page3ChecksComplete & Page2ChecksComplete & gascheck ){
				date.setText("Maintain System");
			}else if(Page3ChecksComplete & Page2ChecksComplete & gascheck ){
				String formattedDate = new SimpleDateFormat("MM-dd-yyyy HH:mm").format(Calendar.getInstance().getTime());
				date.setText(formattedDate);
			}
				
			
		}
			


	//Load Menu Items, don't use in Android 10
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//find the menu items and do something when they are clicked
	@Override	
	public boolean onOptionsItemSelected(MenuItem item) {
		//handle it
			switch(item.getItemId()){
			case R.id.reset:
				MainActivity.this.reset(null);
				return true;
			case R.id.new_dive:
				MainActivity.this.newdive(null);
				return true;
			}
			return false;
			
	}
		
	
	// called when a user clicks button_Calibrate
	public void LaunchCalibration(View view) {
	//go to screen 2
		Intent intent = new Intent(this, Screen2_Activity.class);
		startActivity(intent);
	}
	
	// called when a user clicks Mix (GAS Button)
	public void LaunchMix(View view) {
	//go to Mix screen
		Intent intent = new Intent(this, MixScreen.class);
		startActivity(intent);
	}
	
	// called when a user clicks Sys
	public void LaunchSys(View view) {
	//go to sys screen
		Intent intent = new Intent(this, SysScreen.class);
		startActivity(intent);
	}
	
	//called from reset menu item ---------------------------------------------
	//Clears everything
	//-------------------------------------------------------------------------
	public void reset (View view){

	//default preferences	
	SharedPreferences DP = PreferenceManager.getDefaultSharedPreferences(this);		
	//set status in preferences to unchecked for Calibrate page (p2)
	SharedPreferences p2share = getApplicationContext().getSharedPreferences("p2share", android.content.Context.MODE_PRIVATE);	
	SharedPreferences.Editor editor = p2share.edit();
	editor.putBoolean("cal_c1", false);
	editor.putBoolean("cal_c2", false);
	editor.putBoolean("cal_c3", false);
	editor.putBoolean("cal_c4", false);
	editor.putBoolean("cal_c5", false);
	editor.putBoolean("cal_c7", false);
	editor.putBoolean("cal_c8", false);
	editor.putBoolean("cal_c9", false);
	editor.putBoolean("cal_c10", false);
	editor.putBoolean("cal_c11", false);
	editor.putBoolean("cal_c12", false);
	editor.putBoolean("cal_c14", false);
	editor.putBoolean("cal_c15", false);
	editor.putBoolean("cal_c16", false);
	editor.commit();
	
	//setup SharedPreferences editor for SysScreen (p3)
	SharedPreferences p3share = getApplicationContext().getSharedPreferences("p3share", android.content.Context.MODE_PRIVATE);	
	SharedPreferences.Editor editor2 = p3share.edit();
	
	//clear the checkboxes on the system screen
	for (int i= 0; i<37; i++){
	editor2.putBoolean("Sys_chk"+i,false);
	}
	editor2.commit();
	
	//reset variables
	MainActivity.MainOxygenPercent = 0;
	MainActivity.MainOxygenCylVolume = 0;
	MainActivity.MainOxygenPSI = 0;
	//MainActivity.MainOxygenMetab = 0.8;
	MainActivity.MinutesOfOxy = 0;
	//Diluent
	MainActivity.MainMix_DillO2num = 0;
	MainActivity.MainMix_DillHEnum = 0;
	MainActivity.MainMix_DillPSINum = 0;
	//MainActivity.MainMixDillPO2set = 1.0;
	MainActivity.DillMOD = 0;
	//Bailout
	MainActivity.MainMix_Bai1O2num = 0;
	MainActivity.MainMix_BailHEnum = 0;
	MainActivity.MainMix_BailPSINum = 0;
	//MainActivity.MainMixBailPO2set = 1.4;
	MainActivity.BailMod = 0;
	//Deco
	MainActivity.MainMix_DecoPSI = 0;
	MainActivity.MainMix_DecoO2num = 0;
	MainActivity.MainMix_DecoHEnum = 0;
	//MainActivity.MainMixDecoPO2set = 1.6;
	MainActivity.DecoMod = 0;
	//O2 Sensors
	MainActivity.MainSensor_1_ambient = 0;
	MainActivity.MainSensor_2_ambient = 0;
	MainActivity.MainSensor_3_ambient = 0;
	MainActivity.MainSensor_1_saturated = 0;
	MainActivity.MainSensor_2_saturated = 0;
	MainActivity.MainSensor_3_saturated = 0;		
	//house variables
	MainActivity.gascheck=false;
	MainActivity.Page2ChecksComplete = false;
	MainActivity.Page3ChecksComplete = false;
	MainActivity.ScrubberAccum = Integer.parseInt(DP.getString("ScrubberAccum", "242"));
	
	onCreate(null);
	}
	
	//called from new dive menu item 
	public void newdive (View view){
	TextView date = (TextView) findViewById(R.id.date_preformed);
	date.setText("new dive selected");
	}
	
	
	//Default preferences
	public class readit extends PreferenceActivity{
		@Override
		protected void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences);	
		}	
	}
	
	
	
	
}
