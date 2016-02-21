package magnetickush.trackziboo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button)findViewById(R.id.start);
        b2 = (Button) findViewById(R.id.stop);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start The Service
                startService(new Intent(getBaseContext(), ServiceLocation.class));
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Stop The Service
                stopService(new Intent(getBaseContext(), ServiceLocation.class));
            }
        });


    }
}