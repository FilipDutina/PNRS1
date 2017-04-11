package rtrk.pnrs1.ra43_2014;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    //values used for operations with list
    private int LIST_LONG_PRESS = 1;
    private int ADD_TASK_CLICK = 0;
    private int myPosition;

    //buttons
    private Button noviZadatak;
    private Button statistika;

    //adapter
    private MyAdapter myAdapter;

    //list
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noviZadatak = (Button) findViewById(R.id.button);
        statistika = (Button) findViewById(R.id.button2);
        lista = (ListView) findViewById(R.id.lista);
        myAdapter = new MyAdapter(MainActivity.this);


        noviZadatak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code here
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("dodaj", getText(R.string.dodaj));
                intent.putExtra("otkazi", getText(R.string.otkazi));
                startActivityForResult(intent, ADD_TASK_CLICK);
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
                intent.putExtra("sacuvaj", getText(R.string.sacuvaj));
                intent.putExtra("obrisi", getText(R.string.obrisi));
                myPosition = position;
                intent.putExtra("listElement", position);
                startActivityForResult(intent, LIST_LONG_PRESS);

                return true;
            }
        });
    lista.setAdapter(myAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_TASK_CLICK && resultCode == RESULT_OK)
        {
            myAdapter.addTask(new MyAdapter(data.getStringArrayExtra("ime")));
            myAdapter.notifyDataSetChanged();
        }
        if(requestCode == LIST_LONG_PRESS && RESULT_CANCELED == resultCode)
        {
            myAdapter.removeTask(myPosition);
        }
    }
}

