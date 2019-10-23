package com.example.homeworrrrrk9.Controller;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homeworrrrrk9.Model.User;
import com.example.homeworrrrrk9.R;
import com.example.homeworrrrrk9.Repository.TasksRepository;
import com.example.homeworrrrrk9.Repository.UserRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserControlFragment extends Fragment {
    private User mUser;
    private UserAdapter mAdapter;
    private List<User> mUsers;

    private RecyclerView mRecyclerView;
    private TextView userName, date, count;
    private ImageButton action;
    AlertDialog delete;


    public UserControlFragment() {
        // Required empty public constructor
    }

    public static UserControlFragment newInstance() {
        
        Bundle args = new Bundle();
        
        UserControlFragment fragment = new UserControlFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_controll, container, false);

        initView(view);
        adapterSet();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.user_recycle);
    }

    private class UserHolder extends RecyclerView.ViewHolder {

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.controller_user_name);
            date = itemView.findViewById(R.id.controller_date);
            count = itemView.findViewById(R.id.controller_count);
            action = itemView.findViewById(R.id.controller_action);
        }

        private void bind(final User user) {
            mUser = user;
            int taskCount = TasksRepository.getInstance(getContext()).getTasksCount(user.get_userId());

            DateFormat formatDate = new SimpleDateFormat("EEE, MMM d yyyy");
            Log.e("User Control", "bind: " + user.getSignupDate());

            userName.setText(user.getUsername());
            count.setText(String.valueOf(taskCount));
            date.setText(formatDate.format(user.getSignupDate()));

            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   delete = new AlertDialog.Builder(getActivity())
                            .setTitle("Delete user")
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    TasksRepository.getInstance(getContext()).cascadeDelete(user.get_userId());
                                    UserRepository.deleteUser(user);
                                    delete.cancel();
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, null)
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialogInterface) {
                                    adapterSet();
                                }
                            })
                            .show();
                }
            });
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {
        public void setUserList(List<User> userList) {
            mUserList = userList;
        }

        private List<User> mUserList;

        public UserAdapter(List<User> userList) {
            mUserList = userList;
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.user_control_list_item, parent, false);
            return new UserHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {
            holder.bind(mUserList.get(position));
        }

        @Override
        public int getItemCount() {
            return mUserList.size();
        }
    }

    private void adapterSet(){
        mUsers = UserRepository.getInstance(getContext()).adminUsersList();
        if (mAdapter == null){
            mAdapter = new UserAdapter(mUsers);
        } else {
            mAdapter.setUserList(mUsers);
            mAdapter.notifyDataSetChanged();
        }
    }

}
