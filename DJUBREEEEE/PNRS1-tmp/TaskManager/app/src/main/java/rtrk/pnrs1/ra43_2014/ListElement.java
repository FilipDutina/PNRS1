package rtrk.pnrs1.ra43_2014;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by student on 11.4.2017.
 */

public class ListElement implements Serializable{

    private Random myRandom = new Random();

    public String imeZadatka;    //**
    public String vreme; //**
    public String datum; //**
    public String opisZadatka;
    public int prioritet;    //**
    public int podsetnik;
    public int myTaskReminder;   //dodato za proveru reminder-a
    public int myTaskDone;
    public int myTaskID;


    public ListElement(String imeZadatka1, int prioritet1, String vreme1, String datum1 , int podsetnik1, int isSet, String opisZadatka1)
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
