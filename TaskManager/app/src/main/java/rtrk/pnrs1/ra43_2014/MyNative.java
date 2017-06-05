package rtrk.pnrs1.ra43_2014;

/**
 * Created by studentJeKralj on 5.6.2017.
 */

public class MyNative {

    public native int racun(int ukupanBrojZadataka, int urdajeniZadaci);

    static
    {
        System.loadLibrary("statistika");
    }
}
