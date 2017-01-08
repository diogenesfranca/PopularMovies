package app.com.example.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                //Display the fragment as the main content
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
