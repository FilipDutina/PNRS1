package rtrk.pnrs1.ra43_2014;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 4NIMA on 6/4/2017.
 */

public class TaskDataBase extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "task";

    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TASK_NAME = "TaskName";
    private static final String COLUMN_TASK_DESCRIPTION = "TaskDescription";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_TIME = "Time";
    private static final String COLUMN_ALARM_IMAGE = "Alarm";
    private static final String COLUMN_CHECKED = "Checked";
    private static final String COLUMN_PRIORITY = "Priority";


    TaskDataBase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER, " +
                COLUMN_TASK_NAME + " TEXT, " +
                COLUMN_TASK_DESCRIPTION + " TEXT, " +
                COLUMN_PRIORITY + " INTEGER, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_ALARM_IMAGE + " TEXT, " +
                COLUMN_CHECKED + " INTEGER);" );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    long insert(ListElement task)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, task.getID());
        values.put(COLUMN_TASK_NAME, task.getImeZadatka());
        values.put(COLUMN_TASK_DESCRIPTION, task.getOpis());
        values.put(COLUMN_DATE, task.getDatum());
        values.put(COLUMN_TIME, task.getVreme());
        values.put(COLUMN_ALARM_IMAGE, task.getPodsetnik());
        values.put(COLUMN_CHECKED, task.getMyTaskReminder());
        values.put(COLUMN_PRIORITY, task.getPrioritet());

        long a = db.insert(TABLE_NAME, null, values);
        close();
        return a;
    }


    void updateTask(ListElement task, String id) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, task.getID());
        values.put(COLUMN_TASK_NAME, task.getImeZadatka());
        values.put(COLUMN_TASK_DESCRIPTION, task.getOpis());
        values.put(COLUMN_DATE, task.getDatum());
        values.put(COLUMN_TIME, task.getVreme());
        values.put(COLUMN_ALARM_IMAGE, task.getPodsetnik());
        values.put(COLUMN_CHECKED, task.getMyTaskReminder());
        values.put(COLUMN_PRIORITY, task.getPrioritet());

        db.update(TABLE_NAME, values,"ID=?",new String[] {id});
        close();
    }


    ListElement[] readTasks() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        if (cursor.getCount() <= 0) {
            return null;
        }

        ListElement[] tasks = new ListElement[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            tasks[i++] = createTask(cursor);
        }

        close();
        return tasks;
    }


    public ListElement readTask(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_ID + "=?",
                new String[] {id}, null, null, null);
        cursor.moveToFirst();
        ListElement task = createTask(cursor);

        close();
        return task;
    }


    void deleteTask(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[] {id});
        close();
    }


    private ListElement createTask(Cursor cursor)
    {
        int ID = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        String taskName = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME));
        String taskDescription = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION));
        String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
        String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
        int alarmImage = cursor.getInt(cursor.getColumnIndex(COLUMN_ALARM_IMAGE));
        int checked = cursor.getInt(cursor.getColumnIndex(COLUMN_CHECKED)); //ne znam kako da pokupim boolean
        int priority = cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY));

        boolean checked2;
        if(checked > 0)
            checked2 = true;
        else
            checked2 = false;

        return new ListElement(taskName, priority, time, date, alarmImage, checked2, taskDescription, ID);

        /*  protected String imeZadatka;
            protected String vreme;
            protected String datum;
            protected int prioritet;
            protected int podsetnik;
            protected boolean myTaskReminder;   //dodato za proveru reminder-a
            protected String opis;
            protected int ID;

            public ListElement(String imeZadatka1, int prioritet1, String vreme1, String datum1 , int podsetnik1, boolean isSet, String opis1, int ID1)
            {
                imeZadatka = imeZadatka1;
                prioritet = prioritet1;
                vreme = vreme1;
                datum = datum1;
                podsetnik = podsetnik1; //SLIKA ALARMA U ELEMENTU LISTE
                myTaskReminder = isSet; //DA LI JE CEKIRAN CHECKBOX U ELEMENTU LISTE(za precrtavanje)
                opis = opis1;
                ID = ID1;
            }*/
    }
}
