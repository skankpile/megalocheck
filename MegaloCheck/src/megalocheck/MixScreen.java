package megalocheck;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import jm.megalocheck.R;

public class MixScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mix_screen);
		// Show the Up button in the action bar.
		setupActionBar();
		//populate PSI or BAR	
		TextView oxunit = (TextView) findViewById(R.id.Mix_OxygenPSI);
		oxunit.setText(MainActivity.gunit.toUpperCase()+"=");
				
		TextView dilunit = (TextView) findViewById(R.id.Mix_DillPSI);
		dilunit.setText(MainActivity.gunit.toUpperCase()+"=");
				
		TextView bailunit = (TextView) findViewById(R.id.Mix_BailPSI);
		bailunit.setText(MainActivity.gunit.toUpperCase()+"=");
				
		TextView decounit = (TextView) findViewById(R.id.Mix_DecoPSI);
		decounit.setText(MainActivity.gunit.toUpperCase()+"=");
		
	//Fill in Oxygen Defaults on Mix load from Public Variables
		EditText OxygenPercent = (EditText) findViewById(R.id.Mix_OxygenPercent);
		OxygenPercent.setText(Integer.toString(MainActivity.MainOxygenPercent));
		
		EditText OxygenVol = (EditText) findViewById(R.id.Mix_OxygenCylVolNum);
		OxygenVol.setText(Integer.toString(MainActivity.MainOxygenCylVolume));
		
		EditText OxygenPSI = (EditText) findViewById(R.id.Mix_OxygenPSINum);
		OxygenPSI.setText(Integer.toString(MainActivity.MainOxygenPSI));
		
    //Fill in Diluent Defaults on Mix load from Public Variables
		EditText DillO2Percent = (EditText) findViewById(R.id.Mix_DillO2num);
		DillO2Percent.setText(Double.toString(MainActivity.MainMix_DillO2num).format("%.0f", MainActivity.MainMix_DillO2num));

		
		EditText DillHEnum = (EditText) findViewById(R.id.Mix_DillHEnum);
		DillHEnum.setText(Integer.toString(MainActivity.MainMix_DillHEnum));
		
		EditText DillPSI = (EditText) findViewById(R.id.Mix_DillPSINum);
		DillPSI.setText(Integer.toString(MainActivity.MainMix_DillPSINum));
		
	//Fill in Bailout Defaults on Mix load from Public Variables
	    EditText BailO2Percent = (EditText) findViewById(R.id.Mix_Bai1O2num);
	    BailO2Percent.setText(Double.toString(MainActivity.MainMix_Bai1O2num).format("%.0f", MainActivity.MainMix_Bai1O2num));
			
		EditText BailHEnum = (EditText) findViewById(R.id.Mix_BailHEnum);
		BailHEnum.setText(Integer.toString(MainActivity.MainMix_BailHEnum));
			
		EditText BailPSI = (EditText) findViewById(R.id.Mix_BailPSINum);
		BailPSI.setText(Integer.toString(MainActivity.MainMix_BailPSINum));	
		
	//Fill in Deco Defaults on Mix load from Public Variables
	    EditText DecoO2Percent = (EditText) findViewById(R.id.Mix_DecoO2num);
	    DecoO2Percent.setText(Double.toString(MainActivity.MainMix_DecoO2num).format("%.0f", MainActivity.MainMix_DecoO2num));
			
		EditText DecoHEnum = (EditText) findViewById(R.id.Mix_DecoHEnum);
		DecoHEnum.setText(Integer.toString(MainActivity.MainMix_DecoHEnum));
			
		EditText DecoPSI = (EditText) findViewById(R.id.Mix_DecoPSINum);
		DecoPSI.setText(Integer.toString(MainActivity.MainMix_DecoPSI));
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
		getMenuInflater().inflate(R.menu.mix_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
	    case R.id.save:
			MixScreen.this.Gas_Submit(null);
			return true;
			}
			return false;
		
	}
	
	// called when a user clicks mix submit button
	public void Gas_Submit(View view) {
	//go to main menu
		Intent intent = new Intent(this, MainActivity.class);
				
	  //Assign Oxygen values to public variables from Input fields
		//O2
		EditText OxygenPercent = (EditText) findViewById(R.id.Mix_OxygenPercent);
		MainActivity.MainOxygenPercent=Integer.parseInt(OxygenPercent.getText().toString());
		
		EditText OxygenVol = (EditText) findViewById(R.id.Mix_OxygenCylVolNum);
		MainActivity.MainOxygenCylVolume = Integer.parseInt(OxygenVol.getText().toString());
		
		EditText OxygenPSI = (EditText) findViewById(R.id.Mix_OxygenPSINum);
		MainActivity.MainOxygenPSI = Integer.parseInt(OxygenPSI.getText().toString());
		
		//Dill
		EditText DillO2Percent = (EditText) findViewById(R.id.Mix_DillO2num);
		MainActivity.MainMix_DillO2num=Double.parseDouble(DillO2Percent.getText().toString());

		EditText DillHEnum = (EditText) findViewById(R.id.Mix_DillHEnum);
		MainActivity.MainMix_DillHEnum = Integer.parseInt(DillHEnum.getText().toString());
		
		EditText DillPSI = (EditText) findViewById(R.id.Mix_DillPSINum);
		MainActivity.MainMix_DillPSINum = Integer.parseInt(DillPSI.getText().toString());
		
		//Bailout
		EditText BailO2Percent = (EditText) findViewById(R.id.Mix_Bai1O2num);
		MainActivity.MainMix_Bai1O2num=Double.parseDouble(BailO2Percent.getText().toString());
		
		EditText BailHEnum = (EditText) findViewById(R.id.Mix_BailHEnum);
		MainActivity.MainMix_BailHEnum = Integer.parseInt(BailHEnum.getText().toString());
		
		EditText BailPSI = (EditText) findViewById(R.id.Mix_BailPSINum);
		MainActivity.MainMix_BailPSINum = Integer.parseInt(BailPSI.getText().toString());
		
		//Deco
		EditText DecoO2Percent = (EditText) findViewById(R.id.Mix_DecoO2num);
		MainActivity.MainMix_DecoO2num=Double.parseDouble(DecoO2Percent.getText().toString());
		
		EditText DecoHEnum = (EditText) findViewById(R.id.Mix_DecoHEnum);
		MainActivity.MainMix_DecoHEnum = Integer.parseInt(DecoHEnum.getText().toString());
		
		EditText DecoPSI = (EditText) findViewById(R.id.Mix_DecoPSINum);
		MainActivity.MainMix_DecoPSI = Integer.parseInt(DecoPSI.getText().toString());
		
		
		//check if the gases are entered and turn the gas button green
		if(MainActivity.MainOxygenPercent !=0 & MainActivity.MainOxygenCylVolume !=0 & MainActivity.MainOxygenPSI !=0
				& MainActivity.MainMix_DillO2num !=0 & MainActivity.MainMix_DillPSINum !=0 & MainActivity.MainMix_Bai1O2num !=0
				& MainActivity.MainMix_BailPSINum !=0) {
			MainActivity.gascheck=true;
		}
			else {
				MainActivity.gascheck=false;
			}
						
		
		startActivity(intent);
}

}
