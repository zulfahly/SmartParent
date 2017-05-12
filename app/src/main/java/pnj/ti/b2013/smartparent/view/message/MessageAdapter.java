package pnj.ti.b2013.smartparent.view.message;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Message;

/***
 * Created by Fahly on 5/11/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private MessageAdapter adapter;
    private Context context;
    private ArrayList<Message> messageArrayList;
    public MessageAdapter(Context context, ArrayList<Message> messageArrayList) {
        this.context = context;
        this.messageArrayList = messageArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pesan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MessageAdapter.ViewHolder holder, int position) {
        holder.message = messageArrayList.get(position);
        final String sender = holder.message.sender;
        if (sender.equals("Saya")) {
            holder.gravity.setGravity(Gravity.END);
            holder.card.setBackgroundColor(ContextCompat.getColor(context, R.color.colorChatBox));
            holder.sender.setText("Anda : ");
            holder.sender.setTextColor(ContextCompat.getColor(context, R.color.colorGold));
        } else {
            holder.gravity.setGravity(Gravity.START);
            holder.sender.setText("Guru");
            holder.sender.setTextColor(ContextCompat.getColor(context, R.color.colorGolddark));
        }
        holder.content.setText(holder.message.content);

        ViewTreeObserver viewTreeObserver = holder.content.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    holder.content.getViewTreeObserver();
                    int cellWidth = holder.content.getWidth();
                    if (cellWidth > 410) {
                        RelativeLayout.LayoutParams lp;
                        lp = new RelativeLayout.LayoutParams(cellWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        lp.addRule(RelativeLayout.BELOW, holder.content.getId());
                        holder.stats.setLayoutParams(lp);
                        holder.stats.setGravity(Gravity.RIGHT);
                        holder.stats.setOrientation(LinearLayout.HORIZONTAL);
                    }
                }
            });
        }
        holder.tanggal.setText(holder.message.tanggal);
        final String isread = holder.message.isread;
        switch (isread) {
            case "0":
                holder.indicator.setImageResource(R.drawable.check_all_gray);
                break;
            case "1":
                holder.indicator.setImageResource(R.drawable.check_all_blue);
                break;
            default:
                holder.indicator.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }


    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView sender;
        public TextView content;
        public TextView tanggal;
        public ImageView indicator;
        public RelativeLayout card;
        public LinearLayout stats;
        public LinearLayout gravity;
        public CardView cv;
        public Message message;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            card = (RelativeLayout) itemView.findViewById(R.id.card);
            sender = (TextView) itemView.findViewById(R.id.sender);
            content = (TextView) itemView.findViewById(R.id.content);
            tanggal = (TextView) itemView.findViewById(R.id.receivetime);
            indicator = (ImageView) itemView.findViewById(R.id.indicator);
            stats = (LinearLayout) itemView.findViewById(R.id.stats);
            gravity = (LinearLayout) itemView.findViewById(R.id.gravity);
        }
    }

    public void updateData(List<Message> messages) {
        this.messageArrayList.clear();
        this.messageArrayList.addAll(messages);
        notifyDataSetChanged();
    }
}
