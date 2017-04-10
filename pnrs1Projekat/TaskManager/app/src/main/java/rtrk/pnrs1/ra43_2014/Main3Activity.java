package rtrk.pnrs1.ra43_2014;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Button nazad = (Button) findViewById(R.id.button8);

        nazad.setOnClickListener(new View.OnClickListener() {   //go back on the main activity
            @Override
            public void onClick(View v) {
                //code here
                Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
