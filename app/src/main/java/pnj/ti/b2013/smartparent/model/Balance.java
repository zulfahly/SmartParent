package pnj.ti.b2013.smartparent.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fahly on 5/1/2017.
 */

public class Balance implements Parcelable
{
    public String tanggal;
    public String jumlah_debit;
    public String limit_debit;

    protected Balance(Parcel in) {
        tanggal = in.readString();
        jumlah_debit = in.readString();
        limit_debit = in.readString();
    }

    public static final Creator<Balance> CREATOR = new Creator<Balance>() {
        @Override
        public Balance createFromParcel(Parcel in) {
            return new Balance(in);
        }

        @Override
        public Balance[] newArray(int size) {
            return new Balance[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tanggal);
        parcel.writeString(jumlah_debit);
        parcel.writeString(limit_debit);
    }
}
