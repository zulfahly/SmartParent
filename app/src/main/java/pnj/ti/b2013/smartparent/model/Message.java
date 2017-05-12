package pnj.ti.b2013.smartparent.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fahly on 5/11/2017.
 */

public class Message implements Parcelable
{
    public String sender;
    public String isread;
    public String content;
    public String tanggal;

    protected Message(Parcel in) {
        sender = in.readString();
        isread = in.readString();
        content = in.readString();
        tanggal = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sender);
        dest.writeString(isread);
        dest.writeString(content);
        dest.writeString(tanggal);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
