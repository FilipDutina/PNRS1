package rtrk.pnrs1.ra43_2014;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by student on 11.4.2017.
 */

public class ListElement /*implements Serializable*/{

    //random generator
    private Random myRandom = new Random();

    public String imeZadatka;    //mTaskEntryName
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

    /*
    *   SETERI I GETERI
    */

    public void setImeZadatka(String imeZadatka)
    {
        this.imeZadatka = imeZadatka;
    }

    public String getImeZadatka()
    {
        return imeZadatka;
    }

    public void setVreme(String vreme)
    {
        this.vreme = vreme;
    }

    public String getVreme()
    {
        return vreme;
    }

    public void setDatum(String datum)
    {
        this.datum = datum;
    }

    public String getDatum()
    {
        return datum;
    }

    public void setPrioritet(int prioritet)
    {
        this.prioritet = prioritet;
    }

    public int getPrioritet()
    {
        return prioritet;
    }

    public void setPodsetnik(int podsetnik)
    {
        this.podsetnik = podsetnik;
    }

    public int getPodsetnik()
    {
        return podsetnik;
    }

    public void setMyTaskReminder(int myTaskReminder)
    {
        this.myTaskReminder = myTaskReminder;
    }

    public int getMyTaskReminder()
    {
        return myTaskReminder;
    }

    public int getID()
    {
        return myTaskID;
    }

    public String getOpisZadatka()
    {
        return opisZadatka;
    }

    public int getMyTaskDone()
    {
        return myTaskDone;
    }

    public void setMyTaskDone(int done)
    {
        myTaskDone = done;
    }

}
