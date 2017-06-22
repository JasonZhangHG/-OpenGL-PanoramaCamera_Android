package org.cyanogenmod.focal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import fr.xplod.focal.R;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
    public void openCamera(View v){
        Intent intent=new Intent(this,CameraActivity.class);
        startActivityForResult(intent, 100);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
