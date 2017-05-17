package pnj.ti.b2013.smartparent.view.canteen;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Canteen;
import pnj.ti.b2013.smartparent.model.DetailCanteen;
import pnj.ti.b2013.smartparent.model.Presense;

/***
 * Created by Fahly on 5/8/2017.
 */

public class CanteenAdapter extends RecyclerView.Adapter<CanteenAdapter.ViewHolder> {

    String TAG = CanteenAdapter.class.getSimpleName();

    private Context context;
    private List<Canteen> canteenList;
    private DetailTransactionAdapter detailTransactionAdapter;
    private ArrayList<DetailCanteen> listDetailCanteen;

    public CanteenAdapter(Context context, List<Canteen> canteenList) {
        this.context = context;
        this.canteenList = canteenList;
    }

    @Override
    public CanteenAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_canteen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.canteen = canteenList.get(position);
        holder.tglTransaksi.setText(holder.canteen.tanggal);
        holder.idTransaksi.setText(holder.canteen.id_transaksi);
        holder.jumlahTransaksi.setText(holder.canteen.jumlah_belanja);
        holder.totalHarga.setText(holder.canteen.jumlah_harga);
        listDetailCanteen = new ArrayList<>();

        detailTransactionAdapter = new DetailTransactionAdapter(listDetailCanteen,context);
        holder.recyItem.setLayoutManager(new LinearLayoutManager(context));
        holder.recyItem.setAdapter(detailTransactionAdapter);
    }

    @Override
    public int getItemCount() {
        return canteenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView tglTransaksi;
        private TextView idTransaksi;
        private TextView jumlahTransaksi;
        private RecyclerView recyItem;
        private TextView totalHarga;
        private Canteen canteen;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.tglTransaksi = (TextView) itemView.findViewById(R.id.tglTransaksi);
            this.idTransaksi = (TextView) itemView.findViewById(R.id.idTransaksi);
            this.jumlahTransaksi = (TextView) itemView.findViewById(R.id.valJumlah);
            this.recyItem = (RecyclerView) itemView.findViewById(R.id.recyclerItem);
            this.totalHarga = (TextView) itemView.findViewById(R.id.valTotalharga);
        }
    }

    public void updateData(List<Canteen> listCanteen) {
        this.canteenList.clear();
        this.canteenList.addAll(listCanteen);
        notifyDataSetChanged();

    }
}
