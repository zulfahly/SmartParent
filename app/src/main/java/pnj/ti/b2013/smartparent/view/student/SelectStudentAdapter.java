package pnj.ti.b2013.smartparent.view.student;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.model.Student;

/**
 * Created by Fahly on 4/25/2017.
 **/

public class SelectStudentAdapter extends RecyclerView.Adapter<SelectStudentAdapter.ViewHolder>
{
    String TAG = SelectStudentAdapter.class.getSimpleName();

    private Context context;
    private List<Student> studentList;

    public SelectStudentAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_select_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectStudentAdapter.ViewHolder holder, int position) {

        holder.student = studentList.get(position);
        holder.nameLabel.setText(holder.student.nama_siswa);
        holder.className.setText(holder.student.nama_kelas);


//        Glide.with(context)
//                .load(holder.student.foto).asBitmap()
//                .centerCrop()
//                .placeholder(R.drawable.img_default_profile)
//                .into(new BitmapImageViewTarget(holder.imgSponsor) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        RoundedBitmapDrawable circularBitmapDrawable =
//                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                        circularBitmapDrawable.setCircular(true);
//                        holder.imgSponsor.setImageDrawable(circularBitmapDrawable);
//                    }
//
//
//                });
//
//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(listener!=null){
//                    listener.onSelectMessageNotification(holder.messageNotification, holder.profile, holder.seed);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void updateData(List<Student> students) {
        this.studentList.clear();
        this.studentList.addAll(students);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameLabel;
        private TextView className;
        private View view;
        private ImageView photo;
        public Student student;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.nameLabel = (TextView) view.findViewById(R.id.studentName);
            this.className = (TextView) view.findViewById(R.id.classOfStudent);
            this.photo = (ImageView) view.findViewById(R.id.studentPicture);
        }
    }
}
