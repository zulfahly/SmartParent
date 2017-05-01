package pnj.ti.b2013.smartparent.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fahly on 4/30/2017.
 **/

public class Presense implements Parcelable {
    public String nama_siswa;
    public String keterangan;
    public String jam;

    protected Presense(Parcel in) {
        nama_siswa = in.readString();
        keterangan = in.readString();
        jam = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama_siswa);
        dest.writeString(keterangan);
        dest.writeString(jam);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Presense> CREATOR = new Creator<Presense>() {
        @Override
        public Presense createFromParcel(Parcel in) {
            return new Presense(in);
        }

        @Override
        public Presense[] newArray(int size) {
            return new Presense[size];
        }
    };
}
