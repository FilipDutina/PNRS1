package rtrk.pnrs1.ra43_2014;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MainActivity t = this;  //activity object reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button noviZadatak = (Button) findViewById(R.id.button);
        Button statistika = (Button) findViewById(R.id.button2);

        noviZadatak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code here
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                t.finish(); //close curent activity
            }
        });

        statistika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code here
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(intent);
                t.finish(); //close curent activity
            }
        });
    }
}
