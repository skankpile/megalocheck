package megalocheck;

import jm.megalocheck.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class SysScreen extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sys_screen);
		// Show the Up button in the action bar.
		setupActionBar();

		// find the check boxes and set from preferences P3	
		CheckBox[] cboxs = new CheckBox[37];
		SharedPreferences p3share = getApplicationContext().getSharedPreferences("p3share", android.content.Context.MODE_PRIVATE);
		for (int i= 1; i<37; i++){
		int resId = getResources().getIdentifier("Sys_chk"+ i, "id", getPackageName());
		cboxs[i] = (CheckBox) findViewById(resId);
		cboxs[i].setChecked(p3share.getBoolean("Sys_chk"+i,cboxs[i].isChecked()));
		}
		
		//scrubber time from main var on load
		TextView remainTime = (TextView) findViewById(R.id.Sys_ScrubberRemainNum);
		remainTime.setText(Integer.toString(MainActivity.ScrubberAccum));
		
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
		getMenuInflater().inflate(R.menu.sys_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
	    case R.id.save:
			SysScreen.this.Sys_Submit(null);
			return true;
			}
			return false;
		
	}

	// called when a user clicks Sys_Submit button
	public void Sys_Submit(View view) {
	
	//setup SharedPreferences editor	
	SharedPreferences p3share = getApplicationContext().getSharedPreferences("p3share", android.content.Context.MODE_PRIVATE);	
	SharedPreferences.Editor editor = p3share.edit();
	
	//get check boxes and load values into preferences on submit
	CheckBox[] cboxs = new CheckBox[37];
	for (int i= 1; i<37; i++){
	int resId = getResources().getIdentifier("Sys_chk"+ i, "id", getPackageName());
	//find the check box ID
	cboxs[i] = (CheckBox) findViewById(resId);
	//load the preferences
	editor.putBoolean("Sys_chk"+i,cboxs[i].isChecked());
	}
	editor.commit();
	
	//check if all check boxes are checked
	for (int i= 1; i<37; i++){
	int resId = getResources().getIdentifier("Sys_chk"+ i, "id", getPackageName());
	cboxs[i] = (CheckBox) findViewById(resId);
	
	//boolean stays true if all are checked
	if(cboxs[i].isChecked()){
		MainActivity.Page3ChecksComplete = true;}
	else{
		MainActivity.Page3ChecksComplete = false;
		i=37;
		}
	}
	//then go to main menu
	Intent intent = new Intent(this, MainActivity.class);
	startActivity(intent);
	}
	
	
	//called when button "Update" is clicked
	public void ScrubberTime (View view){
		
		TextView updateTime = (TextView) findViewById(R.id.Sys_ScrubberAccMin);
		TextView remainTime = (TextView) findViewById(R.id.Sys_ScrubberRemainNum);
		remainTime.setText(updateTime.getText().toString());
		MainActivity.ScrubberAccum = Integer.parseInt(remainTime.getText().toString());
	}
	
}
