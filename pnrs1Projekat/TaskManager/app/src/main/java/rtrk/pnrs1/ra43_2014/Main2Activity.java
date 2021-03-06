package rtrk.pnrs1.ra43_2014;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {

    //buttons
    protected Button crveno;
    protected Button zuto;
    protected Button zeleno;
    protected Button levo;
    protected Button desno;

    //checkBox
    protected CheckBox boks;

    //editText
    protected EditText imeZadatka;
    protected EditText opisZadatka;

    //timePicker
    protected TimePicker vreme;

    //datePicker
    protected DatePicker datum;

    //strings
    protected CharSequence uspesanTekst;
    protected CharSequence ponistiTekst;
    private String levoDugmeIme;
    private String desnoDugmeIme;
    private String zadatakImeString;
    private String datumString;
    private String vremeString;

    //flags
    protected boolean tekstoviZadovoljeni;
    protected boolean prioritetiZadovoljeni;

    protected TextWatcher textWatcher = new TextWatcher() {
        @Override
       public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            //do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            //do nothing
        }

        @Override
        public void afterTextChanged(Editable s)
        {
            zadatakImeString = imeZadatka.getText().toString();
            provera();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //buttons
        crveno = (Button) findViewById(R.id.button4);
        zuto = (Button) findViewById(R.id.button3);
        zeleno = (Button) findViewById(R.id.button5);
        levo = (Button) findViewById(R.id.button6);
        desno = (Button) findViewById(R.id.button7);

        //checkBox
        boks = (CheckBox) findViewById(R.id.checkBox);

        //editText
        imeZadatka = (EditText) findViewById(R.id.editText);
        opisZadatka = (EditText) findViewById(R.id.editText2);

        //timePicker
        vreme = (TimePicker) findViewById(R.id.timePicker);

        //datePicker
        datum = (DatePicker) findViewById(R.id.datePicker);

        //flags
        tekstoviZadovoljeni = false;
        prioritetiZadovoljeni = false;

        imeZadatka.addTextChangedListener(textWatcher);
        opisZadatka.addTextChangedListener(textWatcher);

        provera();

        //button name according to action
        levoDugmeIme = getIntent().getStringExtra("dodaj");
        desnoDugmeIme = getIntent().getStringExtra("otkazi");

        levo.setText(levoDugmeIme);
        desno.setText(desnoDugmeIme);

        levo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code here
                //Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                Intent intent = new Intent();
                intent.putExtra("ime", zadatakImeString);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        desno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code here
                //Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                //startActivity(intent);
                finish();
            }
        });

        /*
        *
        *
        *
        * */

        datum.setMinDate(System.currentTimeMillis() - 1000);    //disable all past dates

        /*
        *
        *
        *
        * */

        crveno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code here
                crveno.setEnabled(false); //enable other two buttons
                crveno.setAlpha(0.5f);   //change brightens of button
                zuto.setAlpha(1);
                zeleno.setAlpha(1);
                prioritetiZadovoljeni = true;
                if(tekstoviZadovoljeni)
                {
                    levo.setEnabled(true);
                }
                zuto.setEnabled(true);
                zeleno.setEnabled(true);
            }
        });

        zuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code here
                zuto.setEnabled(false); //enable other two buttons
                zuto.setAlpha(0.5f); //change brightens of button
                crveno.setAlpha(1);
                zeleno.setAlpha(1);
                prioritetiZadovoljeni = true;
                if(tekstoviZadovoljeni)
                {
                    levo.setEnabled(true);
                }
                crveno.setEnabled(true);
                zeleno.setEnabled(true);
            }
        });

        zeleno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code here
                zeleno.setEnabled(false); //enable other two buttons
                zeleno.setAlpha(0.5f);   //change brightens of button
                crveno.setAlpha(1);
                zuto.setAlpha(1);
                prioritetiZadovoljeni = true;
                if(tekstoviZadovoljeni)
                {
                    levo.setEnabled(true);
                }
                crveno.setEnabled(true);
                zuto.setEnabled(true);
            }
        });

    }

    private void provera()
    {
        String s1 = imeZadatka.getText().toString();
        String s2 = opisZadatka.getText().toString();

        if(!(s1.isEmpty()) && !(s2.isEmpty()))
        {
            tekstoviZadovoljeni = true;
            if(prioritetiZadovoljeni)
            {
                levo.setEnabled(true);
            }
        }
        else
        {
            levo.setEnabled(false);
        }
    }
}
