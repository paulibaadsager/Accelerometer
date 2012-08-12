package com.example.accelerometer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener, OnClickListener {

	private SensorManager sensor;
	private Sensor accelerometer;
	private TextView output;
	private ArrayList<String> event2;
	// private 
	//private String filename = Environment.getExternalStorageDirectory().getParent() + "acceleration.dat";
	private String filename = "/sdcard/acceleration.dat";
	
	public void writeToFile(String filename) {
		try {
			FileWriter writer = new FileWriter(filename);
			for (int i = 0; i < event2.size(); i++) {
				String timestamp = event2.get(i);
				// String x = Float.toString(event2.get(i).values[0]);
				// String y = Float.toString(event2.get(i).values[1]);
				// String z = Float.toString(event2.get(i).values[2]);
				// writer.write(timestamp + " " + x + " " + " " + y + " " + z + "\n");
				writer.write(timestamp + "\n");
			}
			writer.close();
		}
		catch(IOException e) {
			System.err.println("There was a problem writing to the file.");
		}
	}
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String s = "hurra";
        event2 = new ArrayList<String>();
        event2.add(s);
        
        sensor = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        output = (TextView) findViewById(R.id.output);
        
        View exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
        
    }
    
    public void onClick(View v) {
    	writeToFile(filename);
    	finish();
    }

    @Override
    protected void onResume() {
    	super.onResume();
    	sensor.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	sensor.unregisterListener(this);
    	writeToFile(this.filename);
    }

    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	writeToFile(this.filename);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	writeToFile(this.filename);
    }
    
    
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
	    
    	StringBuilder builder = new StringBuilder();
    	    	
	    builder.append("\n\n--- EVENT ---");
	    builder.append("\nAccuracy: ");
	    builder.append(event.accuracy);
	    // builder.append(event2.get(0));
	    builder.append("\nTimestamp: ");
	    builder.append(event.timestamp);
	    builder.append("\nValues:\n");
	    
    
	    for (int i = 0; i < event.values.length; i++) {
	       // ...
	       builder.append("   [");
	       builder.append(i);
	       builder.append("] = ");
	       builder.append(event.values[i]);
	       builder.append("\n");
	    }
	    output.setText(builder);
	    // SystemClock.sleep(3000);
	    // output.setText("hurra1");
	    event2.add(Long.toString(event.timestamp));
    	//event2.add("hej");
	    //event2.add(builder.toString());
	    //output.setText("hurra2");

	    
    }
    
}