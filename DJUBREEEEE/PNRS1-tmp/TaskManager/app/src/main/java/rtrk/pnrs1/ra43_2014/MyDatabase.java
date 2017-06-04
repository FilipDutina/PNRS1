package rtrk.pnrs1.ra43_2014;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Filip Dutina on 01-Jun-17.
 */

public class MyDatabase extends SQLiteOpenHelper{

    /*
    *  public String imeZadatka;    //mTaskEntryName
        public String vreme; //mTaskEntryTime
        public String datum; //mTaskEntryDate
        public String opisZadatka;  //mTaskEntryDescription
        public int prioritet;    //mTaskEntryPriority
        public int podsetnik;   //mTaskEntryAlarm
        public int myTaskReminder;  //mTaskReminderSet
        public int myTaskDone;  //mTaskDone
        public int myTaskID;    //mTaskEntryID
    *
    * */

    public static int DATABASE_VERSION = 1;

    public static final String BAZA_IME = "zadaci.db";
    public static final String TABELA_IME = "Zadaci";
    public static final String ZADATAK_IME = "ZadatakIme";
    public static final String ZADATAK_DATUM = "ZadatakDatum";
    public static final String ZADATAK_VREME = "ZadatakVreme";
    public static final String ZADATAK_OPIS = "ZadatakOpis";
    public static final String ZADATAK_PODSETNIK = "ZadatakPodsetnik";
    public static final String ZADATAK_PRIORITET = "ZadatakPrioritet";
    public static final String ZADATAK_URADJEN = "ZadatakUradjen";
    public static final String ZADATAK_SLIKA_ALARMA = "ZadatakAlarm";
    public static final String ZADATAK_ID = "ZadatakID";


    public MyDatabase(Context context)
    {
        super(context, BAZA_IME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i("Baza", "pravim");

        db.execSQL("CREATE TABLE" + TABELA_IME +" ("+ZADATAK_ID+" INT, "+ZADATAK_IME+" TEXT, "+ZADATAK_VREME+" TEXT, "+ZADATAK_DATUM+" TEXT, "+ZADATAK_OPIS+" TEXT, "+ZADATAK_PODSETNIK+" INT, "+ZADATAK_SLIKA_ALARMA+" INT, "+ZADATAK_PRIORITET+" INT, "+ZADATAK_URADJEN+" INT);");

        Log.i("Baza", "gotova");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public void insert(ListElement myListElement)
    {
        Log.i("Baza", "insert");
        //id, ime, vreme, datum, opis, podsetnik(checkbox), prioritet(button), zadatak izvrsen
        SQLiteDatabase mySQLiteDatabase = getWritableDatabase();
        ContentValues myContentValues = new ContentValues();
        myContentValues.put(ZADATAK_ID, myListElement.getID());
        myContentValues.put(ZADATAK_IME, myListElement.getImeZadatka());
        myContentValues.put(ZADATAK_DATUM, myListElement.getDatum());
        myContentValues.put(ZADATAK_VREME, myListElement.getVreme());
        myContentValues.put(ZADATAK_OPIS, myListElement.getOpisZadatka());
        myContentValues.put(ZADATAK_PODSETNIK, myListElement.getMyTaskReminder());
        myContentValues.put(ZADATAK_PRIORITET, myListElement.getPrioritet());
        myContentValues.put(ZADATAK_SLIKA_ALARMA, myListElement.getPodsetnik());
        myContentValues.put(ZADATAK_URADJEN, myListElement.getMyTaskDone());

        mySQLiteDatabase.insert(TABELA_IME, null, myContentValues);
        mySQLiteDatabase.close();
    }

    public ListElement[] readTasks()
    {
        Log.i("Baza", "readTasks");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABELA_IME, null, null, null, null, null, null, null);

        if(cursor.getCount() <= 0)
        {
            return null;
        }

        ListElement[] tasks = new ListElement[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            tasks[i++] = createTask(cursor);
        }

        close();
        return tasks;
    }

    private ListElement createTask(Cursor cursor)
    {
        Log.i("Baza", "createTask");
        String ime = cursor.getString(cursor.getColumnIndex(ZADATAK_IME));
        String datum = cursor.getString(cursor.getColumnIndex(ZADATAK_DATUM));
        String vreme = cursor.getString(cursor.getColumnIndex(ZADATAK_VREME));
        String opis = cursor.getString(cursor.getColumnIndex(ZADATAK_OPIS));
        int prioritet = Integer.getInteger(cursor.getString(cursor.getColumnIndex(ZADATAK_PRIORITET)));
        int podsetnik = Integer.getInteger(cursor.getString(cursor.getColumnIndex(ZADATAK_SLIKA_ALARMA)));
        int myReminder = Integer.getInteger(cursor.getString(cursor.getColumnIndex(ZADATAK_PODSETNIK)));

        /*
        *   public String imeZadatka;    //mTaskEntryName
            public String vreme; //mTaskEntryTime
            public String datum; //mTaskEntryDate
            public String opisZadatka;  //mTaskEntryDescription
            public int prioritet;    //mTaskEntryPriority
            public int podsetnik;   //mTaskEntryAlarm
            public int myTaskReminder;  //mTaskReminderSet
            public int myTaskDone;  //mTaskDone
            public int myTaskID;    //mTaskEntryID


            public ListElement(String imeZadatka1, int prioritet1, String vreme1, String datum1, int podsetnik1, int myTaskReminder2, String opisZadatka1)
            {
                imeZadatka = imeZadatka1;   //ime zadatka
                prioritet = prioritet1; //prioritet(crveno, zuto, zeleno)
                vreme = vreme1; //vreme
                datum = datum1; //datum
                podsetnik = podsetnik1; //SLIKA ALARMA!!!!!
                myTaskReminder = myTaskReminder2;
                opisZadatka = opisZadatka1; //opis zadatka
                myTaskDone = 0; //da li je zadatak izvrsen?
                myTaskID = myRandom.nextInt(2147483647 - 0);    //random generator ID-ja zadatka
            }
                *
                * */
        Log.i("Baza", "createTaskGotov");

        return new ListElement(ime, prioritet, vreme, datum, podsetnik, myReminder, opis);
    }

}
