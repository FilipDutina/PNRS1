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
    private static int SLEEP_PERIOD = 15000;
    private NotificationManager myNotificationManager;
    private Notification.Builder myNotificationBuilder;
    private boolean myNotificationReady;


    CheckerThread(Context context)
    {
        myRun = true;
        myContext = context;
        mySimpleDateFormat = new SimpleDateFormat("hh:mm");
        myNotificationManager = (NotificationManager) myContext.getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationBuilder = new Notification.Builder(myContext).setContentTitle("Podsetnik").setSmallIcon(android.R.drawable.ic_popup_reminder);
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
        super.run();
        while(myRun)
        {
            Log.i("Nit", "aktivna");
            String myMessage = "Zadatak treba biti izvršen za 15 minuta";
            for(ListElement myListElement : MainActivity.myArrayList)
            {
                Log.i("For", "petlja");
                if(myListElement.getDatum().equals("Danas") && myListElement.getPodsetnik() == 1)
                {
                    Calendar myCurrentDate = Calendar.getInstance();
                    Calendar myTaskTime = Calendar.getInstance();
                    try
                    {
                        myTaskTime.setTime(mySimpleDateFormat.parse(myListElement.getVreme()));
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }

                    if(myTaskTime.get(Calendar.HOUR_OF_DAY) == myCurrentDate.get(Calendar.HOUR_OF_DAY))
                    {
                        if((myTaskTime.get(Calendar.MINUTE) - myCurrentDate.get(Calendar.MINUTE)) <= 15 && (myTaskTime.get(Calendar.MINUTE) - myCurrentDate.get(Calendar.MINUTE)) >= 0)
                        {
                            if(myNotificationReady)
                            {
                                myMessage += ", " + myListElement.getImeZadatka();
                            }
                            else
                            {
                                myMessage += myListElement.getImeZadatka();
                            }
                            myNotificationReady = true;
                        }
                    }
                    else if((myTaskTime.get(Calendar.HOUR_OF_DAY) - myCurrentDate.get(Calendar.HOUR_OF_DAY)) == 1)
                    {
                        if((myTaskTime.get(Calendar.MINUTE) + 60 - myCurrentDate.get(Calendar.MINUTE)) <= 15 && (myTaskTime.get(Calendar.MINUTE) + 60 - myCurrentDate.get(Calendar.MINUTE) + 60) >= 0)
                        {
                            if(myNotificationReady)
                            {
                                myMessage += ", " + myListElement.getImeZadatka();
                            }
                            else
                            {
                                myMessage += myListElement.getImeZadatka();
                            }
                            myNotificationReady = true;
                        }
                    }
                }
            }
            if(myNotificationReady)
            {
                Log.i("Notifikacija", "spremna");
                myNotificationBuilder.setContentText(myMessage);

                myNotificationManager.notify(0, myNotificationBuilder.build());
            }
            else
            {
                myNotificationManager.cancel(0);
            }
            try
            {
                sleep(SLEEP_PERIOD);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

}
