package pnj.ti.b2013.smartparent.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fahly on 4/23/2017.
 */

public class Profile implements Parcelable {
    public String id;
    public String username;
    public String nama_orangtua;
    public String alamat;
    public String nomor_telepon;

    public Profile() {
    }

    public Profile(Parcel in) {
        id = in.readString();
        username = in.readString();
        nama_orangtua = in.readString();
        alamat = in.readString();
        nomor_telepon = in.readString();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(username);
        parcel.writeString(nama_orangtua);
        parcel.writeString(alamat);
        parcel.writeString(nomor_telepon);
    }
}
