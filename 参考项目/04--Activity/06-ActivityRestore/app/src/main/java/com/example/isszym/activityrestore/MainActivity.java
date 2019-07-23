package com.example.isszym.activityrestore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
        private static final String TAG = "测试";
        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // If an instance of this activity had previously stopped, we can
            // get the original text it started with.
            if(null != savedInstanceState)
            {
                int IntTest = savedInstanceState.getInt("IntTest");
                String StrTest = savedInstanceState.getString("StrTest");
                Log.d(TAG, "onCreate get the savedInstanceState+IntTest ="+IntTest+"+StrTest="+StrTest);
            }
            setContentView(R.layout.activity_main);
            Log.d(TAG, "onCreate");
        }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)  {
        // Save away the original text, so we still have it if the activity
        // needs to be killed while paused.
        savedInstanceState.putInt("intTest", 100);
        savedInstanceState.putString("strTest", "Hello，world！");
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState");
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int intTest = savedInstanceState.getInt("intTest");
        String strTest = savedInstanceState.getString("strTest");
        Log.d(TAG, "onRestoreInstanceState：intTest ="+intTest+"，strTest="+strTest);
    }

}
