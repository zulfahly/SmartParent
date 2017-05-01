package pnj.ti.b2013.smartparent.view.student;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.interfaces.Mainlistener;
import pnj.ti.b2013.smartparent.model.Student;

/**
 * Created by Fahly on 4/25/2017.
 **/

public class SelectStudentAdapter extends RecyclerView.Adapter<SelectStudentAdapter.ViewHolder>
{
    String TAG = SelectStudentAdapter.class.getSimpleName();

    private Context context;
    private List<Student> studentList;
    private StudentListener listener;

    public SelectStudentAdapter(Context context, List<Student> studentList, StudentListener listener) {
        this.context = context;
        this.studentList = studentList ;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_select_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SelectStudentAdapter.ViewHolder holder, int position) {

        holder.student = studentList.get(position);
        holder.nameLabel.setText(holder.student.nama_siswa);
        holder.className.setText(holder.student.nama_kelas);


        Glide.with(context).load(holder.student.foto).placeholder(R.drawable.ic_profile).into(holder.photo);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != listener) {
                    listener.onSelectStudent(holder.student);
                }else{
                    Toast.makeText(context,"NO LISTENER",Toast.LENGTH_LONG).show();
                }
            }
        });

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

    public interface StudentListener {
        void onSelectStudent(Student student);
    }
}
