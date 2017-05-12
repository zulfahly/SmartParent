package pnj.ti.b2013.smartparent.view.presence;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Presense;

/**
 * Created by Fahly on 4/30/2017.
 **/

public class PresenceAdapter extends RecyclerView.Adapter<PresenceAdapter.ViewHolder> {

    String TAG = PresenceAdapter.class.getSimpleName();

    private Context context;
    private List<Presense> presenseList;

    public PresenceAdapter(Context context,List<Presense> presenseList) {
        this.presenseList = presenseList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_presence, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.presense = presenseList.get(position);
        holder.studentName.setText(holder.presense.nama_siswa);
        holder.presenceDate.setText(holder.presense.jam);
        holder.detail.setText("Keterangan : presensi "+holder.presense.keterangan);
    }

    @Override
    public int getItemCount() {
        return presenseList.size();
    }

    public void updateData(List<Presense> presenses) {
        this.presenseList.clear();
        this.presenseList.addAll(presenses);
        notifyDataSetChanged();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView studentName;
        private TextView presenceDate;
        private TextView detail;
        public Presense presense;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.studentName = (TextView) view.findViewById(R.id.studentName);
            this.presenceDate = (TextView) view.findViewById(R.id.date);
            this.detail = (TextView) view.findViewById(R.id.detailContent);

        }
    }
}
