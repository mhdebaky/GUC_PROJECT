package com.guc.covid19support.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.guc.covid19support.R;
import com.guc.covid19support.requests.Request;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RecViewAdapter extends RecyclerView.Adapter<RecViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Request> requests;
    DatabaseReference requestsRef,usersRef;
    boolean isDoctor;
    public  RecViewAdapter(Context context,ArrayList<Request> requests,DatabaseReference requestsRef,Boolean isDoctor){
        this.context = context;
        this.requests = requests;
        this.requestsRef=requestsRef;
        this.isDoctor = isDoctor;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_request,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameTextView.setText(requests.get(position).getUserName());
        holder.ageTextView.setText(requests.get(position).getUserAge());
        holder.numTextView.setText(requests.get(position).getUserPhone());
        holder.symtmsTextView.setText(requests.get(position).getSymptoms());
        holder.timeStampTextView.setText(requests.get(position).getTimeStamp());
        holder.replyTextView.setText(requests.get(position).getReply());
        if(isDoctor){
            holder.request_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showDialog(holder.getAdapterPosition());
                }
            });
        }
    }
    public void showDialog(int positionOfRequest){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.reply_dialog);
        dialog.setTitle("Reply");
        MaterialButton submit = dialog.findViewById(R.id.submit_button_reply);
        MaterialButton cancel = dialog.findViewById(R.id.cancel_button_reply);
        TextInputEditText textInputEditText = dialog.findViewById(R.id.reply_text_dialog);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChanged=false;
                if(textInputEditText.getText().toString().equals("")){
                    textInputEditText.setError("Please Enter Your Reply");
                    textInputEditText.requestFocus();
                }else{
                    String reply = textInputEditText.getText().toString();
                    requests.get(positionOfRequest).setReply(reply);
                    requestsRef.child(requests.get(positionOfRequest).getReqId()).setValue(requests.get(positionOfRequest)).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            requests.remove(positionOfRequest);
                            notifyDataSetChanged();
                        }
                    });



                }
                Toast.makeText(context, "Submitted Correctly", Toast.LENGTH_SHORT).show();
                dialog.cancel();


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "cancled reply", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();


    }
    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView ageTextView;
        TextView numTextView;
        TextView symtmsTextView;
        TextView replyTagTextView;
        TextView replyTextView;
        TextView timeStampTextView;
        LinearLayout request_item;
        LinearLayout dialogView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dialogView = itemView.findViewById(R.id.reply_dialog);
            request_item = (LinearLayout) itemView;
            nameTextView = itemView.findViewById(R.id.request_patient_name);
            ageTextView = itemView.findViewById(R.id.request_patient_age);
            numTextView = itemView.findViewById(R.id.request_patient_phone);
            symtmsTextView = itemView.findViewById(R.id.request_patient_symptoms);
            replyTextView = itemView.findViewById(R.id.request_patient_reply);
            replyTagTextView = itemView.findViewById(R.id.request_patient_reply_tag);
            timeStampTextView = itemView.findViewById(R.id.request_time_stamp);

        }
    }
}
