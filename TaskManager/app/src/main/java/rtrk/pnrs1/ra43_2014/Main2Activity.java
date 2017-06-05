package rtrk.pnrs1.ra43_2014;

/*
* 3. V 2017.
* */

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {

    //variables for dataBase
    Boolean redFlag, yellowFlag, greenFlag, dodajBtnEnabled = false, vremeFlag = false, datumFlag = false;

    //buttons
    protected Button crveno;
    protected Button zuto;
    protected Button zeleno;
    protected Button levo;
    protected Button desno;

    //checkBox
    protected CheckBox boks;

    //random
    protected int id;

    //editText
    protected EditText imeZadatka;
    protected EditText opisZadatka;

    //timePicker
    protected TimePicker vreme;

    //datePicker
    protected DatePicker datum;
    protected Calendar trenutniKalendar;

    //priority
    protected int priority;

    //alarm
    protected int alarmTask;

    //strings
    protected CharSequence uspesanTekst;
    protected CharSequence ponistiTekst;
    private String levoDugmeIme;
    private String desnoDugmeIme;
    private String zadatakImeString;
    private String opisZadatkaString;
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
            opisZadatkaString = opisZadatka.getText().toString();
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
        levoDugmeIme = getIntent().getStringExtra("levo");  //get the name of the left button
        desnoDugmeIme = getIntent().getStringExtra("desno");    //get the name of the right button

        levo.setText(levoDugmeIme); //set name of the left button according to action in activity1
        desno.setText(desnoDugmeIme);   //set name of the right button according to action in activity1


        //set current day
        //int dayofmonth = cal.get(Calendar.DAY_OF_MONTH);
        trenutniKalendar = Calendar.getInstance();
        trenutniKalendar.setTimeInMillis(System.currentTimeMillis() - 1000);

        //date
        datum.setMinDate(System.currentTimeMillis() - 1000);    //disable all past dates

        datum.init(trenutniKalendar.get(Calendar.YEAR), trenutniKalendar.get(Calendar.MONTH), trenutniKalendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int godina, int mesec, int dan) {
                //Log.d("Date", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);
                //if(trenutniKalendar.get(Calendar.YEAR) == godina)
                //{

                    if(dan - trenutniKalendar.get(Calendar.DAY_OF_MONTH) == 0)
                    {
                        datumString = "Danas";
                        Log.d("Date", "Year=" + godina + " Month=" + (mesec + 1) + " day=" + dan);
                    }
                    else if(dan - trenutniKalendar.get(Calendar.DAY_OF_MONTH) == 1)
                    {
                        datumString = "Sutra";
                        Log.d("Date", "Year=" + godina + " Month=" + (mesec + 1) + " day=" + dan);
                    }
                    else if(dan - trenutniKalendar.get(Calendar.DAY_OF_MONTH) == 2)
                    {
                        datumString = "Prekosutra";
                        Log.d("Date", "Year=" + godina + " Month=" + (mesec + 1) + " day=" + dan);
                    }
                    else if((dan - trenutniKalendar.get(Calendar.DAY_OF_MONTH) > 2) && (dan - trenutniKalendar.get(Calendar.DAY_OF_MONTH) < 7))
                    {
                        if(Calendar.MONDAY == (dan + 1))
                        {
                            datumString = "Ponedeljak";
                            Log.d("Date", "Year=" + godina + " Month=" + (mesec + 1) + " day=" + dan);
                            //break;
                        }
                        else if(Calendar.TUESDAY == (dan + 1))
                        {
                            datumString = "Utorak";
                            Log.d("Date", "Year=" + godina + " Month=" + (mesec + 1) + " day=" + dan);
                            //break;
                        }
                        else if(Calendar.WEDNESDAY == (dan + 1))
                        {
                            datumString = "Sreda";
                            Log.d("Date", "Year=" + godina + " Month=" + (mesec + 1) + " day=" + dan);
                            //break;
                        }
                        else if(Calendar.THURSDAY == (dan + 1))
                        {
                            datumString = "Četvrtak";
                            Log.d("Date", "Year=" + godina + " Month=" + (mesec + 1) + " day=" + dan);
                            //break;
                        }
                        else if(Calendar.FRIDAY == (dan + 1))
                        {
                            datumString = "Petak";
                            Log.d("Date", "Year=" + godina + " Month=" + (mesec + 1) + " day=" + dan);
                            //break;
                        }
                        else if(Calendar.SATURDAY == (dan + 1))
                        {
                            datumString = "Subota";
                            Log.d("Date", "Year=" + godina + " Month=" + (mesec + 1) + " day=" + dan);
                            //break;
                        }
                        else
                        {
                            datumString = "Nedelja";
                            Log.d("Date", "Year=" + godina + " Month=" + (mesec + 1) + " day=" + dan);
                            //break;
                        }
                    }
                    else
                    {
                        datumString = Integer.toString(dan) + "-" + Integer.toString(mesec+1) + "-" + Integer.toString(godina);
                        Log.d("Date", "Year=" + godina + " Month=" + (mesec + 1) + " day=" + dan);
                    }


            }
        });

        /*
        *
        *
        *
        * */

        //time

        vreme.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if(hourOfDay < 10 && minute < 10)
                    vremeString = "0" + Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute);
                else if(hourOfDay < 10)
                    vremeString = "0" + Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
                else if(minute < 10)
                    vremeString = Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute);
                else
                    vremeString = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
            }
        });


        /*
        *
        *
        *
        * */
        alarmTask = 0;
        Random rand = new Random();
        //Random rand = new Random(); int value = rand.nextInt(50)
        id = rand.nextInt(10000);

        levo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code here
                //Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                Intent intent = new Intent();
                if(boks.isChecked())
                {
                    alarmTask = 1;
                    intent.putExtra("checked", true);   //podsetnik je cekiran
                }
                intent.putExtra("ime", zadatakImeString);
                intent.putExtra("vreme", vremeString);
                intent.putExtra("datum", datumString);
                intent.putExtra("prioritet", priority);
                intent.putExtra("alarmImage", alarmTask);
                intent.putExtra("opis", opisZadatkaString);
                intent.putExtra("ID", id);


                intent.putExtra(MainActivity.myButtonCode, MainActivity.myLeftCode);
                if(getIntent().getIntExtra(MainActivity.myRequestCode, 0) == MainActivity.EDIT_TASK)
                {
                    intent.putExtra(MainActivity.myTaskPosition, getIntent().getIntExtra(MainActivity.myTaskPosition, 0));
                }

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        desno.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent();
            @Override
            public void onClick(View v) {
                //code here
                //Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                //startActivity(intent);
                intent.putExtra(MainActivity.myButtonCode, MainActivity.myRightCode);
                intent.putExtra(MainActivity.myButtonCode, MainActivity.myRightCode);

                setResult(RESULT_OK, intent);

                finish();
            }
        });


        crveno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code here
                priority = 3;   //for the list
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
                priority = 2;   //for the list
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
                priority = 1;   //for the list
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

        if(getIntent().getExtras().getInt(MainActivity.FLAG_ZA_BTN_SACUVAJ) == 1)
        {
            ListElement task = MainActivity.myTaskDataBase.readTask(String.valueOf(MainActivity.myPosition));
            datumString = task.getDatum();
            vremeString = task.getVreme();
            zadatakImeString = task.getImeZadatka();
            opisZadatkaString = task.getOpis();


            imeZadatka.setText(getIntent().getExtras().getString(MainActivity.IME_ZADATKA));
            opisZadatka.setText(getIntent().getExtras().getString(MainActivity.OPIS_ZADATKA));
            //datumString.setText(getIntent().getExtras().getString(MainActivity.DATUM));
            //vremeString.setText(getIntent().getExtras().getString(MainActivity.SAT));



            if (getIntent().getExtras().getInt(MainActivity.BOJA) == 3)
                crveno.performClick();
            else if (getIntent().getExtras().getInt(MainActivity.BOJA) == 2)
                zeleno.performClick();
            else
                zuto.performClick();


            if ((getIntent().getExtras().getInt(MainActivity.CHECKBOX_ALARM) == 1))
                boks.setChecked(true);
        }

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
