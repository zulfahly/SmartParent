package pnj.ti.b2013.smartparent.view.canteen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Canteen;
import pnj.ti.b2013.smartparent.model.DetailCanteen;

/**
 * Created by Fahly on 5/15/2017.
 */

public class DetailTransactionAdapter extends RecyclerView.Adapter<DetailTransactionAdapter.ViewHolder>
{

    List<DetailCanteen> detailCanteenList;
    Context context;

    public DetailTransactionAdapter(List<DetailCanteen> detailCanteenList, Context context) {
        this.detailCanteenList = detailCanteenList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detail_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        for (int i=0; i<holder.canteen.detail.size(); i++){
            holder.detailCanteen = detailCanteenList.get(position);
        }

        holder.itemName.setText(holder.detailCanteen.nama_barang);
        holder.itemValue.setText(holder.detailCanteen.kuantitas);
    }

    @Override
    public int getItemCount() {
        return detailCanteenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private TextView itemValue;
        private View view;
        private Canteen canteen;
        private DetailCanteen detailCanteen;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.itemValue = (TextView) itemView.findViewById(R.id.jumlahItem);
            this.itemName = (TextView) itemView.findViewById(R.id.namaItem);
        }
    }
}
