package pnj.ti.b2013.smartparent.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fahly on 4/25/2017.
 */

public class Student implements Parcelable
{
    public String NIS;
    public String nama_siswa;
    public String nama_kelas;
    public String alamat;
    public String nama_orangtua;
    public String jenis_kelamin;
    public String agama;
    public String foto;
    public String status;
    public String tahun_angkatan;
    public String debit;


    protected Student(Parcel in) {
        NIS = in.readString();
        nama_siswa = in.readString();
        nama_kelas = in.readString();
        alamat = in.readString();
        nama_orangtua = in.readString();
        jenis_kelamin = in.readString();
        agama = in.readString();
        foto = in.readString();
        status = in.readString();
        tahun_angkatan = in.readString();
        debit = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(NIS);
        parcel.writeString(nama_siswa);
        parcel.writeString(nama_kelas);
        parcel.writeString(alamat);
        parcel.writeString(nama_orangtua);
        parcel.writeString(jenis_kelamin);
        parcel.writeString(agama);
        parcel.writeString(foto);
        parcel.writeString(status);
        parcel.writeString(tahun_angkatan);
        parcel.writeString(debit);
    }

    @Override
    public String toString() {
        return nama_siswa;
    }
}
