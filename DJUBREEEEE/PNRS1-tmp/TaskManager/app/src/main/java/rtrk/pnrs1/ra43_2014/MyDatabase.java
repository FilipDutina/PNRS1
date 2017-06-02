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

    public static int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "zadaci.db";
    public static final String TABLE_NAME = "Zadaci";
    public static final String COLUMN_NAME = "ZadatakIme";
    public static final String COLUMN_DATE = "ZadatakDatum";
    public static final String COLUMN_TIME = "ZadatakVreme";
    public static final String COLUMN_DESCRIPTION = "ZadatakOpis";
    public static final String COLUMN_REMINDER = "ZadatakPodsetnik";
    public static final String COLUMN_PRIORITY = "ZadatakPrioritet";
    public static final String COLUMN_DONE = "ZadatakUradjen";
    public static final String COLUMN_ALARM = "ZadatakAlarm";
    public static final String COLUMN_ID = "ZadatakID";


    public MyDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i("Pravim", "bazu");
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("+COLUMN_ID+" INT, "+COLUMN_NAME+" TEXT, "+COLUMN_DATE+" TEXT, "+COLUMN_TIME+" TEXT, "+COLUMN_DESCRIPTION+" TEXT, "+COLUMN_REMINDER+" INT, "+COLUMN_ALARM+" INT, "+COLUMN_PRIORITY+" INT, "+COLUMN_DONE+" INT);");
        Log.i("Baza", "gotova");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public void insert(ListElement myListElement)
    {
        SQLiteDatabase mySQLiteDatabase = getWritableDatabase();
        ContentValues myContentValues = new ContentValues();
        myContentValues.put(COLUMN_ID, myListElement.getID());
        myContentValues.put(COLUMN_NAME, myListElement.getImeZadatka());
        myContentValues.put(COLUMN_DATE, myListElement.getDatum());
        myContentValues.put(COLUMN_TIME, myListElement.getVreme());
        myContentValues.put(COLUMN_DESCRIPTION, myListElement.getOpisZadatka());
        myContentValues.put(COLUMN_REMINDER, myListElement.getMyTaskReminder());
        myContentValues.put(COLUMN_PRIORITY, myListElement.getPrioritet());
        myContentValues.put(COLUMN_ALARM, myListElement.getPodsetnik());
        myContentValues.put(COLUMN_DONE, myListElement.getMyTaskDone());

        mySQLiteDatabase.insert(TABLE_NAME, null, myContentValues);
        mySQLiteDatabase.close();
    }

    public ListElement[] readTasks()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

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
        String ime = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        String datum = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
        String vreme = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
        String opis = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
        String prioritet = cursor.getString(cursor.getColumnIndex(COLUMN_PRIORITY));
        String alarm = cursor.getString(cursor.getColumnIndex(COLUMN_ALARM));
        String podsetnik = cursor.getString(cursor.getColumnIndex(COLUMN_REMINDER));

        return new ListElement(ime, Integer.getInteger(prioritet), vreme, datum, Integer.getInteger(podsetnik), Integer.getInteger(alarm), opis);
    }

}
