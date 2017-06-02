package rtrk.pnrs1.ra43_2014;

/**
 * Created by Filip Dutina on 28-May-17.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
//import android.os.

public class CustomNotifications extends NotificationAidl.Stub{

    private Context myContext;
    private NotificationManager myNotificationManager;
    private Notification.Builder myNotificationBuilder;

    CustomNotifications(Context context)
    {
        myContext = context;
    }

    @Override
    public void notificationAdd() throws RemoteException
    {
        myNotificationManager = (NotificationManager) myContext.getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationBuilder = new Notification.Builder(myContext).setContentTitle("ZADATAK").setSmallIcon(android.R.drawable.ic_popup_reminder).setLargeIcon(BitmapFactory.decodeResource(myContext.getResources(), R.drawable.reminder)).setContentText("Zadatak dodat!");
        myNotificationManager.notify(1, myNotificationBuilder.build()); //id i notifikacija
    }

    @Override
    public void notificationEdit() throws RemoteException
    {
        myNotificationManager = (NotificationManager) myContext.getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationBuilder = new Notification.Builder(myContext).setContentTitle("ZADATAK").setSmallIcon(android.R.drawable.ic_popup_reminder).setLargeIcon(BitmapFactory.decodeResource(myContext.getResources(), R.drawable.reminder)).setContentText("Zadatak izmenjen!");
        myNotificationManager.notify(1, myNotificationBuilder.build()); //id i notifikacija
    }

    @Override
    public void notificationDelete() throws RemoteException
    {
        myNotificationManager = (NotificationManager) myContext.getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationBuilder = new Notification.Builder(myContext).setContentTitle("ZADATAK").setSmallIcon(android.R.drawable.ic_popup_reminder).setLargeIcon(BitmapFactory.decodeResource(myContext.getResources(), R.drawable.reminder)).setContentText("Zadatak obrisan!");
        myNotificationManager.notify(1, myNotificationBuilder.build()); //id i notifikacija
    }
}
