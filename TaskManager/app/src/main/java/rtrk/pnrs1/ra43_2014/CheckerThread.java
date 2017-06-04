package rtrk.pnrs1.ra43_2014;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Filip Dutina on 21-May-17.
 */

public class CheckerThread extends Thread {

    private boolean myRun;
    private Context myContext;
    private SimpleDateFormat mySimpleDateFormat;
    private static int SLEEP_PERIOD = 5000;
    private NotificationManager myNotificationManager;
    private Notification.Builder myNotificationBuilder;
    private boolean myNotificationReady;
    private TaskDataBase baza;


    CheckerThread(Context context)
    {
        myRun = true;
        myContext = context;
        mySimpleDateFormat = new SimpleDateFormat("hh:mm");
        myNotificationManager = (NotificationManager) myContext.getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationBuilder = new Notification.Builder(myContext).setContentTitle("Podsetnik").setSmallIcon(android.R.drawable.ic_popup_reminder);

        baza = new TaskDataBase(context);
    }

    @Override
    public synchronized void start()
    {
        myRun = true;
        super.start();
    }

    public synchronized void exit()
    {
        myRun = false;
    }

    @Override
    public void run()
    {
        while(myRun)
        {
            String msg = "15 minuta do isteka zadatka: ";
            boolean notiHasItems = false;

            ListElement[] tasks = baza.readTasks();
            if(tasks != null)
            {
                for(ListElement t : tasks)
                {
                    if(t.getDatum().equals("Danas") && t.getMyTaskReminder() == false && t.getPodsetnik() != 0)
                    {
                        Calendar current = Calendar.getInstance();
                        Calendar taskTime = Calendar.getInstance();

                        try
                        {
                            taskTime.setTime(mySimpleDateFormat.parse(t.getVreme()));
                        }
                        catch (ParseException e)
                        {
                            e.printStackTrace();
                        }

                        if(taskTime.get(Calendar.HOUR_OF_DAY) == current.get(Calendar.HOUR_OF_DAY))
                        {
                            if(taskTime.get(Calendar.MINUTE)-current.get(Calendar.MINUTE)<=15 && taskTime.get(Calendar.MINUTE)-current.get(Calendar.MINUTE)>=0)
                            {
                                if (notiHasItems)
                                    msg += " , " + t.getImeZadatka();
                                else
                                    msg += t.getImeZadatka();
                                notiHasItems = true;
                            }
                        }
                        else if (taskTime.get(Calendar.HOUR_OF_DAY) - current.get(Calendar.HOUR_OF_DAY) == 1)
                        {
                            if(taskTime.get(Calendar.MINUTE)+60-current.get(Calendar.MINUTE)<=15 && taskTime.get(Calendar.MINUTE)+60-current.get(Calendar.MINUTE)>=0)
                            {
                                if (notiHasItems)
                                    msg += " , " + t.getImeZadatka();
                                else
                                    msg += t.getImeZadatka();
                                notiHasItems = true;
                            }
                        }
                    }
                }
            }
            if(notiHasItems)
            {
                myNotificationBuilder.setContentTitle(msg);
                myNotificationManager.notify(0, myNotificationBuilder.build());
            }
            else
                myNotificationManager.cancel(0);
            try
            {
                long PERIOD = 2000;
                sleep(PERIOD);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

}
