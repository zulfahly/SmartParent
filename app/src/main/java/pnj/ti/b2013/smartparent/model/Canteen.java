package pnj.ti.b2013.smartparent.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Fahly on 5/15/2017.
 */

public class Canteen
{
    public String NIS;
    public String id_transaksi;
    public String tanggal;
    public String jumlah_belanja;
    public String jumlah_harga;
    public List<DetailCanteen> detail;

}
