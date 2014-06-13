package megalocheck;
import jm.megalocheck.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class prefs extends PreferenceActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
	

}
