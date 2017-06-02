package rtrk.pnrs1.ra43_2014;

/*
* Filip Dutina ra43-2014
* 3. V 2017.
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ServiceConnection{

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

    //database
    private MyDatabase myDatabaseHelper;

    //service stuff
    private ServiceConnection myServiceConnection;
    private NotificationAidl myNotificationAidlInterface;
    private Intent myServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noviZadatak = (Button) findViewById(R.id.button);
        statistika = (Button) findViewById(R.id.button2);
        lista = (ListView) findViewById(R.id.lista);
        myAdapter = new MyAdapter(MainActivity.this);

        myDatabaseHelper = new MyDatabase(this);

        myServiceConnection = this;
        myServiceIntent = new Intent(this, ServiceNotifier.class);
        bindService(myServiceIntent, myServiceConnection, BIND_AUTO_CREATE);
        myArrayList = myAdapter.getTaskList();


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
                startActivity(intent);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("levo", getText(R.string.sacuvaj));
                intent.putExtra("desno", getText(R.string.obrisi));
                myPosition = position;
                intent.putExtra(myTaskPosition, position);
                startActivityForResult(intent, EDIT_TASK);

                return true;
            }
        });
    lista.setAdapter(myAdapter);

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        ListElement[] myListElement = myDatabaseHelper.readTasks();
        myAdapter.update(myListElement);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(myNotificationAidlInterface != null)
        {
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
                /*
                * "checked" sam izmenio da bude integer a bio je boolean
                * pronaci to i videti sta je problem; da li je u ListElementu, Adapteru ili na nekom trecem mestu...................
                * */
                ListElement myListElement = new ListElement(data.getStringExtra("ime"), data.getIntExtra("prioritet", 0), data.getStringExtra("vreme"), data.getStringExtra("datum"), data.getIntExtra("alarmImage", 0), data.getIntExtra("checked", 0), data.getExtras().getString("opis"));
                myDatabaseHelper.insert(myListElement);
                ListElement[] tasks = myDatabaseHelper.readTasks();
                myAdapter.update(tasks);
                myAdapter.notifyDataSetChanged();

                if(data.getExtras().getBoolean("checked"))
                {
                    Log.i("taskReminderSet", "true");
                }
                myAdapter.notifyDataSetChanged();
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
                /*
                *  public ListElement(String imeZadatka1, int prioritet1, String vreme1, String datum1 , int podsetnik1, int isSet, String opisZadatka1)
                    {
                        imeZadatka = imeZadatka1;
                        prioritet = prioritet1;
                        vreme = vreme1;
                        datum = datum1;
                        podsetnik = podsetnik1;
                        myTaskReminder = isSet;
                        opisZadatka = opisZadatka1;
                        myTaskDone = 0;
                        myTaskID = myRandom.nextInt(2147483647 - 0);
                    }
                * */

                ListElement myListElement = new ListElement(data.getStringExtra("ime"), data.getIntExtra("prioritet", 0), data.getStringExtra("vreme"), data.getStringExtra("datum"), data.getIntExtra("alarmImage", 0), data.getIntExtra("checked", 0), data.getExtras().getString("opis"));
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
}

