package com.example.maktabhw13task.controller.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.maktabhw13task.controller.activity.TaskViewPagerActivity;
import com.example.maktabhw13task.repository.TaskRepository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DeleteAllTaskDialog extends DialogFragment {

    public static DeleteAllTaskDialog newInstance() {
        
        Bundle args = new Bundle();
        
        DeleteAllTaskDialog fragment = new DeleteAllTaskDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle("Delete All")
                .setMessage("Are you sure delete all task?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TaskRepository taskRepository = TaskRepository.getInstance();
                        taskRepository.deleteAllTask();
                        ((TaskViewPagerActivity)getActivity()).updateRecyclerView();
                        
                    }
                })
                .setNeutralButton(android.R.string.cancel, null)
                .create();
    }
}
