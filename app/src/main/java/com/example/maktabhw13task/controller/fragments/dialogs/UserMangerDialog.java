package com.example.maktabhw13task.controller.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.maktabhw13task.R;
import com.example.maktabhw13task.adapter.UserRecyclerViewAdapter;
import com.example.maktabhw13task.controller.activity.TaskViewPagerActivity;
import com.example.maktabhw13task.repository.TaskRepository;
import com.example.maktabhw13task.repository.UserRepository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class UserMangerDialog extends DialogFragment implements UserRecyclerViewAdapter.OnNoUserListeners {

    private UserRepository mUserRepository;
    private TaskRepository mTaskRepository;
    private RecyclerView mRecyclerView;
    private UserRecyclerViewAdapter mAdapter;
    private TextView mTextViewNoUser;

    public static UserMangerDialog newInstance() {

        Bundle args = new Bundle();

        UserMangerDialog fragment = new UserMangerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskRepository = TaskRepository.getInstance();
        mUserRepository = UserRepository.getInstance();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_user_manger, null);

        findAllViews(view);

        if (mUserRepository.getUserList().size() == 1)
            return new AlertDialog.Builder(getActivity())
                    .setTitle("Delete User")
                    .setPositiveButton(android.R.string.ok, null)
                    .setMessage("There are no user.")
                    .show();

        else {
            setAdapter();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ((TaskViewPagerActivity) getActivity()).updateRecyclerView();
                            dismiss();
                        }
                    })
                    .setTitle("Delete User")
                    .setView(view)
                    .setCancelable(false);
            Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            return dialog;

        }
    }

    private void findAllViews(View view) {
        mRecyclerView = view.findViewById(R.id.userRecyclerView);
        mTextViewNoUser = view.findViewById(R.id.textViewNoUser);
    }

    private void setAdapter() {
        mAdapter = new UserRecyclerViewAdapter(mUserRepository.getUserList().subList(1, mUserRepository.getUserList().size()), mTaskRepository.getTaskList(), getActivity(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void noUser(int position) {
        deleteItem(position);
        setNoUser();
    }

    private void deleteItem(final int position) {
        deleteTask(position);
        mUserRepository.getUserList().remove(position + 1);
        mAdapter.setUserList(mUserRepository.getUserList().subList(1, mUserRepository.getUserList().size()));
        mAdapter.notifyItemRemoved(position);
    }

    private void deleteTask(int position) {

        for (int i = 0; i < mTaskRepository.getTaskList().size(); i++) {
            if (mTaskRepository.getTaskList().get(i).getUserId().equals(mUserRepository.getUserList().get(position + 1).getId())) {
                mTaskRepository.getTaskList().remove(i--);
            }
        }
    }

    private void setNoUser() {
        if (mUserRepository.getUserList().size() == 1) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mTextViewNoUser.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mTextViewNoUser.setVisibility(View.INVISIBLE);
        }
    }
}