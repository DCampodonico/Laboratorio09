package dam.isi.frsf.utn.edu.ar.laboratorio09;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

	private SensorManager mSensorManager;
	private Sensor mAceleration;
	private float aceleracionMaxima[];
	private TextView textViewHoraX;
	private TextView textViewHoraY;
	private TextView textViewHoraZ;
	private TextView textViewMagnitudX;
	private TextView textViewMagnitudY;
	private TextView textViewMagnitudZ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

		Intent intent = new Intent(MainActivity.this, FirebaseService.class);
		startService(intent);
		intent = new Intent(MainActivity.this, MyFirebaseMessagingService.class);
		startService(intent);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		textViewHoraX = (TextView) findViewById(R.id.textViewHoraX);
		textViewHoraY = (TextView) findViewById(R.id.textViewHoraY);
		textViewHoraZ = (TextView) findViewById(R.id.textViewHoraZ);
		textViewMagnitudX = (TextView) findViewById(R.id.textViewMagnitudX);
		textViewMagnitudY = (TextView) findViewById(R.id.textViewMagnitudY);
		textViewMagnitudZ = (TextView) findViewById(R.id.textViewMagnitudZ);
		aceleracionMaxima = new float[3];
		aceleracionMaxima[0] = 0;
		aceleracionMaxima[1] = 0;
		aceleracionMaxima[2] = 0;
	}

	@Override
	protected void onResume(){
		super.onResume();
		mSensorManager.registerListener(this, mAceleration, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause(){
		super.onPause();
		mSensorManager.unregisterListener(this);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.values[0] > aceleracionMaxima[0]){
			aceleracionMaxima[0] = event.values[0];
			textViewHoraX.setText(String.valueOf(System.currentTimeMillis()));
			textViewMagnitudX.setText(String.valueOf(aceleracionMaxima[0]));
		}
		if(event.values[1] > aceleracionMaxima[1]){
			aceleracionMaxima[1] = event.values[1];
			textViewHoraY.setText(String.valueOf(System.currentTimeMillis()));
			textViewMagnitudY.setText(String.valueOf(aceleracionMaxima[1]));
		}
		if(event.values[2] > aceleracionMaxima[2]){
			aceleracionMaxima[2] = event.values[2];
			textViewHoraZ.setText(String.valueOf(System.currentTimeMillis()));
			textViewMagnitudZ.setText(String.valueOf(aceleracionMaxima[2]));
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
}
