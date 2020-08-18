package com.example.maktabhw13task.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maktabhw13task.R;
import com.example.maktabhw13task.model.TaskModel;
import com.example.maktabhw13task.model.UserModel;

import java.text.DateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder> {

    private List<UserModel> mUserList;
    private List<TaskModel> mTaskList;
    private Context mContext;
    private OnNoUserListeners mOnNoUserListeners;

    public UserRecyclerViewAdapter(List<UserModel> userList, List<TaskModel> taskList, Context context, OnNoUserListeners onNoUserListeners) {
        mUserList = userList;
        mTaskList = taskList;
        mContext = context;
        this.mOnNoUserListeners = onNoUserListeners;
    }

    public void setUserList(List<UserModel> userList) {
        mUserList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(mContext).inflate(R.layout.user_item_row, parent, false), mOnNoUserListeners);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.mTitle.setText(mUserList.get(position).getUsername());
        holder.mSubtitle.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(mUserList.get(position).getDate()));
        holder.mFirstLetter.setText(String.valueOf(mUserList.get(position).getUsername().toUpperCase().charAt(0)));
        holder.mTaskNumber.setText("(Tasks: " + getTaskNumbers(position) + ")");
    }

    private int getTaskNumbers(int position) {
        int counter = 0;
        for (int i = 0; i < mTaskList.size(); i++) {
            if (mTaskList.get(i).getUserId().equals(mUserList.get(position).getId()))
                counter++;
        }
        return counter;
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }


    public interface OnNoUserListeners {
        void noUser(int position);
    }

    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnNoUserListeners onNoUserListeners;
        private TextView mTitle, mSubtitle, mFirstLetter, mTaskNumber;
        private ImageView mDelete;

        public UserViewHolder(@NonNull View itemView, OnNoUserListeners onNoUserListeners) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.textViewUserItemTitle);
            mSubtitle = itemView.findViewById(R.id.textViewUserItemSubtitle);
            mFirstLetter = itemView.findViewById(R.id.textViewUserItemFirstLetter);
            mTaskNumber = itemView.findViewById(R.id.textViewTaskNumber);
            mDelete = itemView.findViewById(R.id.imageViewDeleteUser);
            this.onNoUserListeners = onNoUserListeners;
            mDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoUserListeners.noUser(getAdapterPosition());
        }
    }


}
