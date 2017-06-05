package rtrk.pnrs1.ra43_2014;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main3Activity extends AppCompatActivity {

    protected Button nazad;
    protected PieChart visokPrioritet;
    protected PieChart srednjiPrioritet;
    protected PieChart nizakPrioritet;

    protected int crveno;
    protected int zuto;
    protected int zeleno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        nazad = (Button) findViewById(R.id.button8);

        crveno = getIntent().getExtras().getInt("redPostotak");
        zuto = getIntent().getExtras().getInt("yellowPostotak");
        zeleno = getIntent().getExtras().getInt("greenPostotak");

        visokPrioritet = (PieChart) findViewById(R.id.visokaPita);
        srednjiPrioritet = (PieChart) findViewById(R.id.srednjaPita);
        nizakPrioritet = (PieChart) findViewById(R.id.niskaPita);

        if(crveno != 0)
            visokPrioritet.setMyPercentageTarget(crveno);
        else
            visokPrioritet.setMyPercentageTarget(0);
        if(zuto != 0)
            srednjiPrioritet.setMyPercentageTarget(zuto);
        else
            srednjiPrioritet.setMyPercentageTarget(0);
        if(zeleno != 0)
            nizakPrioritet.setMyPercentageTarget(zeleno);
        else
            nizakPrioritet.setMyPercentageTarget(0);

        srednjiPrioritet.getPaint().setColor(getResources().getColor(R.color.yellowColor));
        nizakPrioritet.getPaint().setColor(getResources().getColor(R.color.greenColor));

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
