package rtrk.pnrs1.ra43_2014;

import java.io.Serializable;

/**
 * Created by student on 11.4.2017.
 */

public class ListElement implements Serializable{

    protected String imeZadatka;
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

    public void setMyTaskReminder(boolean myTaskReminder)
    {
        this.myTaskReminder = myTaskReminder;
    }

    public boolean getMyTaskReminder()
    {
        return myTaskReminder;
    }

    public String getOpis()
    {
        return opis;
    }

    public void setOpis(String opis)
    {
        this.opis = opis;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

}
