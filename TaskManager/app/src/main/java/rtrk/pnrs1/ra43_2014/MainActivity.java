package rtrk.pnrs1.ra43_2014;

/*
* Filip Dutina ra43-2014
* 4. V 2017.
* Nedelja vece..........................
* */

/*Dodajem za IV zadatak*/
import android.content.ComponentName;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import java.text.ParseException;
//***************************
import android.app.ListActivity;
import android.content.Intent;
import android.content.ServiceConnection;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ServiceConnection{

    //dodato za 5. zadatak
    Intent intentZaActivity2, intentZaActivity3;
    public static String IME_ZADATKA = "imeZadatkaText";
    public static String OPIS_ZADATKA = "opisZadatkaText";
    public static String BOJA = "boja";
    public static String DATUM = "datum";
    public static String SAT = "sat";
    public static String CHECKBOX_ALARM = "checkBoxAlarm";
    public static String FLAG_ZA_BTN_SACUVAJ = "checkBox";
    public static int SACUVAJ = 5;
    static int position;

    //strings
    public static String myButtonCode = "buttonCode";
    public static String myLeftCode = "leftButton";
    public static String myRightCode = "rightButton";
    public static String myTaskPosition = "taskPosition";
    public static String myRequestCode = "requestCode";

    //values used for operations with list
    private int LIST_LONG_PRESS = 1;
    private int ADD_TASK_CLICK = 0;
    private int myPosition;
    public static int EDIT_TASK = 1;
    public static int ADD_TASK = 0;

    //buttons
    private Button noviZadatak;
    private Button statistika;

    //adapter
    private MyAdapter myAdapter;

    //list
    private ListView lista;
    public static ArrayList<ListElement> myArrayList;

    //service stuff
    private ServiceConnection myServiceConnection;
    private NotificationAidl myNotificationAidlInterface;
    private Intent myServiceIntent;

    //database
    static TaskDataBase myTaskDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noviZadatak = (Button) findViewById(R.id.button);
        statistika = (Button) findViewById(R.id.button2);
        lista = (ListView) findViewById(R.id.lista);
        myAdapter = new MyAdapter(MainActivity.this);


        myServiceConnection = this;
        myServiceIntent = new Intent(this, ServiceNotifier.class);
        bindService(myServiceIntent, myServiceConnection, BIND_AUTO_CREATE);
        myArrayList = myAdapter.getTaskList();

        myTaskDataBase = new TaskDataBase(this);
        intentZaActivity2 = new Intent(MainActivity.this, Main2Activity.class);
        intentZaActivity3 = new Intent(MainActivity.this, Main3Activity.class);

        noviZadatak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code here
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("levo", getText(R.string.dodaj));
                intent.putExtra("desno", getText(R.string.otkazi));
                startActivityForResult(intent, ADD_TASK);
            }
        });

        statistika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code here
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                if(izracunajStatistiku(intent))
                    startActivity(intent);

                //break;
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("levo", getText(R.string.sacuvaj));
                intent.putExtra("desno", getText(R.string.obrisi));

                //ListElement task = myTaskDataBase.readTask(String.valueOf(position));
                /*intent.putExtra(DATUM, task.getDatum());
                intent.putExtra(SAT, task.getVreme());
                intent.putExtra(IME_ZADATKA, task.getImeZadatka());
                intent.putExtra(OPIS_ZADATKA, task.getOpis());
                intent.putExtra(BOJA, task.getPrioritet());
                intent.putExtra(CHECKBOX_ALARM, task.getPodsetnik());*/

                myPosition = position;
                intent.putExtra(myTaskPosition, position);
                startActivityForResult(intent, EDIT_TASK);

                return true;
            }
        });
        lista.setAdapter(myAdapter);

        //ServiceConnection mServiceConnection = this;
        //Intent serviceIntent = new Intent(this, ServiceNotifier.class);
        //bindService(serviceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ListElement[] tasks = myTaskDataBase.readTasks();
        myAdapter.update(tasks);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(myNotificationAidlInterface != null) {
            unbindService(this);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_TASK && resultCode == RESULT_OK)
        {
            if(data.getStringExtra(myButtonCode).equals(myLeftCode))
            {
                //myAdapter.addTask(new ListElement(data.getStringExtra("ime"), data.getIntExtra("prioritet", 0), data.getStringExtra("vreme"), data.getStringExtra("datum"), data.getIntExtra("alarmImage", 0), data.getExtras().getBoolean("checked")));
                ListElement myListElement = new ListElement(data.getStringExtra("ime"), data.getIntExtra("prioritet", 0), data.getStringExtra("vreme"), data.getStringExtra("datum"), data.getIntExtra("alarmImage", 0), data.getExtras().getBoolean("checked"), data.getStringExtra("opis"), data.getIntExtra("ID", 0));
                myTaskDataBase.insert(myListElement);
                ListElement[] tasks = myTaskDataBase.readTasks();
                myAdapter.update(tasks);
                /*if(data.getExtras().getBoolean("checked"))
                {
                    Log.i("taskReminderSet", "true");
                }*/
                //myAdapter.notifyDataSetChanged();
                try
                {
                    myNotificationAidlInterface.notificationAdd();
                }
                catch (RemoteException e)
                {
                    e.printStackTrace();
                }
            }
            //myAdapter.addTask(new ListElement(data.getStringExtra("ime"), data.getIntExtra("prioritet", 0), data.getStringExtra("vreme"), data.getStringExtra("datum"), data.getIntExtra("alarmImage", 0)));
            //myAdapter.notifyDataSetChanged();
        }
        if(requestCode == EDIT_TASK && RESULT_OK == resultCode)
        {
            if(data.getStringExtra(myButtonCode).equals(myLeftCode))
            {
                ListElement myListElement = new ListElement(data.getStringExtra("ime"), data.getIntExtra("prioritet", 0), data.getStringExtra("vreme"), data.getStringExtra("datum"), data.getIntExtra("alarmImage", 0), data.getExtras().getBoolean("checked"), data.getStringExtra("opis"), data.getIntExtra("ID", 0));
                myTaskDataBase.insert(myListElement);
                ListElement[] tasks = myTaskDataBase.readTasks();
                myAdapter.update(tasks);
                myAdapter.editTask(data.getIntExtra(myTaskPosition, 0), myListElement);
                try
                {
                    myNotificationAidlInterface.notificationEdit();
                }
                catch (RemoteException e)
                {
                    e.printStackTrace();
                }
            }
            else if(data.getStringExtra(myButtonCode).equals(myRightCode))
            {
                myTaskDataBase.deleteTask(String.valueOf(myPosition));

                if(myAdapter.getCount() > 1)
                    izmeniID(myPosition);

                ListElement[] tasks = myTaskDataBase.readTasks();
                myAdapter.update(tasks);

                myAdapter.removeTask(data.getIntExtra(myTaskPosition, 0));
                try
                {
                    myNotificationAidlInterface.notificationDelete();
                }
                catch (RemoteException e)
                {
                    e.printStackTrace();
                }
            }
            //myAdapter.removeTask(myPosition);

        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service)
    {
        myNotificationAidlInterface = NotificationAidl.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name)
    {


    }

    void izmeniID (int position)
    {
        ListElement[] tasks = myTaskDataBase.readTasks();
        for (ListElement task : tasks) {
            if (task.getID() > position)
            {
                task.setID(task.getID() - 1);
                myTaskDataBase.updateTask(task, String.valueOf(task.getID() + 1));
            }
        }

    }

    public boolean izracunajStatistiku(Intent intent)
    {
        //
        //
        // Intent intent3 = new Intent(MainActivity.this, Main3Activity.class);

        int redBr = 0;
        int yellowBr = 0;
        int greenBr = 0;
        int redBrChecked = 0;
        int yellowBrChecked = 0;
        int greenBrChecked = 0;
        int suma;

        ListElement[] tasks = myTaskDataBase.readTasks();
        if(myAdapter.getCount() == 0)
        {
            //Toast.makeText(getApplicationContext(), "Nema zadataka!",
                    //Toast.LENGTH_LONG).show();
            return false;
        }
        for(ListElement task : tasks)
        {
            if(task.getPrioritet() == 2)//zeleni
                yellowBr++;
            else if(task.getPrioritet() == 1)//crveni
                greenBr++;
            else//zuti
                redBr++;
        }
        suma = redBr + yellowBr + greenBr;
        if(suma == 0)
            return false;

        if(redBr != 0)
            redBrChecked = (100 * redBr) / suma;
        if(yellowBr != 0)
            yellowBrChecked = (100 * yellowBr) / suma;
        if(greenBr != 0)
            greenBrChecked = (100 * greenBr) / suma;


        intent.putExtra("redPostotak", redBrChecked);
        intent.putExtra("greenPostotak", greenBrChecked);
        intent.putExtra("yellowPostotak", yellowBrChecked);

        return true;

    }
}

