package com.mittendorf.megalocheck;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import com.mittendorf.megalocheck.R;

public class Screen2_Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen2_display);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//get Oxygen Percent from MixMenu or display warning
		TextView O2Per = (TextView) findViewById(R.id.cal_O2);
		O2Per.setText(" " + MainActivity.MainOxygenPercent + "%");
		if(MainActivity.MainOxygenPercent<=0){
			O2Per.setTextColor(Color.RED);
			O2Per.setText("Analyze O2!");
			}
			else{ O2Per.setTextColor(Color.rgb(34, 139, 34));
			}

		// find the check boxes on P2
		CheckBox cal_c1 = (CheckBox) findViewById(R.id.cal_c1);
		CheckBox cal_c2 = (CheckBox) findViewById(R.id.cal_c2);
		CheckBox cal_c3 = (CheckBox) findViewById(R.id.cal_c3);
		CheckBox cal_c4 = (CheckBox) findViewById(R.id.cal_c4);
		CheckBox cal_c5 = (CheckBox) findViewById(R.id.cal_c5);
		CheckBox cal_c7 = (CheckBox) findViewById(R.id.cal_c7);
		CheckBox cal_c8 = (CheckBox) findViewById(R.id.cal_c8);
		CheckBox cal_c9 = (CheckBox) findViewById(R.id.cal_c9);
		CheckBox cal_c10 = (CheckBox) findViewById(R.id.cal_c10);
		CheckBox cal_c11 = (CheckBox) findViewById(R.id.cal_c11);
		CheckBox cal_c12 = (CheckBox) findViewById(R.id.cal_c12);
		CheckBox cal_c14 = (CheckBox) findViewById(R.id.cal_c14);
		CheckBox cal_c15 = (CheckBox) findViewById(R.id.cal_c15);
		CheckBox cal_c16 = (CheckBox) findViewById(R.id.cal_c16);
		
		//find the sensor mv views
		TextView cal_ambient_s1 = (TextView) findViewById(R.id.cal_ambient_s1);
		TextView cal_ambient_s2 = (TextView) findViewById(R.id.cal_ambient_s2);
		TextView cal_ambient_s3 = (TextView) findViewById(R.id.cal_ambient_s3);
		TextView cal_sat_s1 = (TextView) findViewById(R.id.cam_max_s1);
		TextView cal_sat_s2 = (TextView) findViewById(R.id.cal_max_s2);
		TextView cal_sat_s3 = (TextView) findViewById(R.id.cal_max_s3);
		
		//set the check boxes from preferences
		SharedPreferences p2share = getApplicationContext().getSharedPreferences("p2share", android.content.Context.MODE_PRIVATE);	
		cal_c1.setChecked(p2share.getBoolean("cal_c1",cal_c1.isChecked()));
		cal_c2.setChecked(p2share.getBoolean("cal_c2",cal_c2.isChecked()));
		cal_c3.setChecked(p2share.getBoolean("cal_c3",cal_c3.isChecked()));
		cal_c4.setChecked(p2share.getBoolean("cal_c4",cal_c4.isChecked()));
		cal_c5.setChecked(p2share.getBoolean("cal_c5",cal_c5.isChecked()));
		cal_c7.setChecked(p2share.getBoolean("cal_c7",cal_c7.isChecked()));
		cal_c8.setChecked(p2share.getBoolean("cal_c8",cal_c8.isChecked()));
		cal_c9.setChecked(p2share.getBoolean("cal_c9",cal_c9.isChecked()));
		cal_c10.setChecked(p2share.getBoolean("cal_c10",cal_c10.isChecked()));
		cal_c11.setChecked(p2share.getBoolean("cal_c11",cal_c11.isChecked()));
		cal_c12.setChecked(p2share.getBoolean("cal_c12",cal_c12.isChecked()));
		cal_c14.setChecked(p2share.getBoolean("cal_c14",cal_c14.isChecked()));
		cal_c15.setChecked(p2share.getBoolean("cal_c15",cal_c15.isChecked()));
		cal_c16.setChecked(p2share.getBoolean("cal_c16",cal_c16.isChecked()));
		
		//set mv sensor readings from public vars
		cal_ambient_s1.setText(Double.toString(MainActivity.MainSensor_1_ambient));
		cal_ambient_s2.setText(Double.toString(MainActivity.MainSensor_2_ambient));
		cal_ambient_s3.setText(Double.toString(MainActivity.MainSensor_3_ambient));
		cal_sat_s1.setText(Double.toString(MainActivity.MainSensor_1_saturated));
		cal_sat_s2.setText(Double.toString(MainActivity.MainSensor_2_saturated));
		cal_sat_s3.setText(Double.toString(MainActivity.MainSensor_3_saturated));
		
		//call CalculateSaturated so the theoretical max o2 points calculate on load
		Screen2_Activity.this.CalculateSaturated(null);
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.screen2_, menu);
		
		return true;
	}
	

    //Action bar logic
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
	    case R.id.save:
			Screen2_Activity.this.backtomain(null);
			return true;
			}
			return false;
		
	}
	
	
	// called when a user clicks calibration submit button
	public void backtomain(View view) {
	
	//find the check boxes
	CheckBox cal_c1 = (CheckBox) findViewById(R.id.cal_c1);
	CheckBox cal_c2 = (CheckBox) findViewById(R.id.cal_c2);
	CheckBox cal_c3 = (CheckBox) findViewById(R.id.cal_c3);
	CheckBox cal_c4 = (CheckBox) findViewById(R.id.cal_c4);
	CheckBox cal_c5 = (CheckBox) findViewById(R.id.cal_c5);
	CheckBox cal_c7 = (CheckBox) findViewById(R.id.cal_c7);
	CheckBox cal_c8 = (CheckBox) findViewById(R.id.cal_c8);
	CheckBox cal_c9 = (CheckBox) findViewById(R.id.cal_c9);
	CheckBox cal_c10 = (CheckBox) findViewById(R.id.cal_c10);
	CheckBox cal_c11 = (CheckBox) findViewById(R.id.cal_c11);
	CheckBox cal_c12 = (CheckBox) findViewById(R.id.cal_c12);
	CheckBox cal_c14 = (CheckBox) findViewById(R.id.cal_c14);
	CheckBox cal_c15 = (CheckBox) findViewById(R.id.cal_c15);
	CheckBox cal_c16 = (CheckBox) findViewById(R.id.cal_c16);
		
	//put check box status in preferences
	SharedPreferences p2share = getApplicationContext().getSharedPreferences("p2share", android.content.Context.MODE_PRIVATE);	
	SharedPreferences.Editor editor = p2share.edit();
	editor.putBoolean("cal_c1",cal_c1.isChecked());
	editor.putBoolean("cal_c2",cal_c2.isChecked());
	editor.putBoolean("cal_c3",cal_c3.isChecked());
	editor.putBoolean("cal_c4",cal_c4.isChecked());
	editor.putBoolean("cal_c5",cal_c5.isChecked());
	editor.putBoolean("cal_c7",cal_c7.isChecked());
	editor.putBoolean("cal_c8",cal_c8.isChecked());
	editor.putBoolean("cal_c9",cal_c9.isChecked());
	editor.putBoolean("cal_c10",cal_c10.isChecked());
	editor.putBoolean("cal_c11",cal_c11.isChecked());
	editor.putBoolean("cal_c12",cal_c12.isChecked());
	editor.putBoolean("cal_c14",cal_c14.isChecked());
	editor.putBoolean("cal_c15",cal_c15.isChecked());
	editor.putBoolean("cal_c16",cal_c16.isChecked());
	editor.commit();
	
	
	//are all the check boxes checked?
	if(p2share.getBoolean("cal_c1",cal_c1.isChecked()) & 
			p2share.getBoolean("cal_c2",cal_c2.isChecked()) &
			p2share.getBoolean("cal_c3",cal_c3.isChecked()) &
			p2share.getBoolean("cal_c4",cal_c4.isChecked()) &
			p2share.getBoolean("cal_c5",cal_c5.isChecked()) &
			p2share.getBoolean("cal_c7",cal_c7.isChecked()) &
			p2share.getBoolean("cal_c8",cal_c8.isChecked()) &
			p2share.getBoolean("cal_c9",cal_c9.isChecked()) &
			p2share.getBoolean("cal_c10",cal_c10.isChecked()) &
			p2share.getBoolean("cal_c11",cal_c11.isChecked()) &
			p2share.getBoolean("cal_c12",cal_c12.isChecked()) &
			p2share.getBoolean("cal_c14",cal_c14.isChecked()) &
			p2share.getBoolean("cal_c15",cal_c15.isChecked()) &
			p2share.getBoolean("cal_c16",cal_c16.isChecked())){
		MainActivity.Page2ChecksComplete = true;}
		else{
			MainActivity.Page2ChecksComplete = false;
		}
	
	//find the sensor mv views
	TextView cal_ambient_s1 = (TextView) findViewById(R.id.cal_ambient_s1);
	TextView cal_ambient_s2 = (TextView) findViewById(R.id.cal_ambient_s2);
	TextView cal_ambient_s3 = (TextView) findViewById(R.id.cal_ambient_s3);
	TextView cal_sat_s1 = (TextView) findViewById(R.id.cam_max_s1);
	TextView cal_sat_s2 = (TextView) findViewById(R.id.cal_max_s2);
	TextView cal_sat_s3 = (TextView) findViewById(R.id.cal_max_s3);
	
	//store the entered mv readings
	MainActivity.MainSensor_1_ambient = Double.parseDouble(cal_ambient_s1.getText().toString());
	MainActivity.MainSensor_2_ambient = Double.parseDouble(cal_ambient_s2.getText().toString());
	MainActivity.MainSensor_3_ambient = Double.parseDouble(cal_ambient_s3.getText().toString());
	MainActivity.MainSensor_1_saturated = Double.parseDouble(cal_sat_s1.getText().toString());
	MainActivity.MainSensor_2_saturated = Double.parseDouble(cal_sat_s2.getText().toString());
	MainActivity.MainSensor_3_saturated = Double.parseDouble(cal_sat_s3.getText().toString());
		
	
	//go to main menu
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	//called when check box "Confirm Air Calibration" is clicked, and on p2 start
	public void CalculateSaturated (View view){
		TextView cal_ambient_s1 = (TextView) findViewById(R.id.cal_ambient_s1);
		TextView cal_ambient_s2 = (TextView) findViewById(R.id.cal_ambient_s2);
		TextView cal_ambient_s3 = (TextView) findViewById(R.id.cal_ambient_s3);
		TextView theo_s1 = (TextView) findViewById(R.id.cal_max_theo_s1);
		TextView theo_s2 = (TextView) findViewById(R.id.cal_max_theo_s2);
		TextView theo_s3 = (TextView) findViewById(R.id.cal_max_theo_s3);
		
		MainActivity.MainSensor_1_ambient = Double.parseDouble(cal_ambient_s1.getText().toString());
		MainActivity.MainSensor_2_ambient = Double.parseDouble(cal_ambient_s2.getText().toString());
		MainActivity.MainSensor_3_ambient = Double.parseDouble(cal_ambient_s3.getText().toString());
		
		double s1calc = 4.7 * MainActivity.MainSensor_1_ambient;
		double s2calc = 4.7 * MainActivity.MainSensor_2_ambient;
		double s3calc = 4.7 * MainActivity.MainSensor_3_ambient;
		
		theo_s1.setText(Double.toString(s1calc).format("%.1f", s1calc));
		theo_s2.setText(Double.toString(s2calc).format("%.1f", s2calc));
		theo_s3.setText(Double.toString(s3calc).format("%.1f", s3calc));
	
	}
	
}


