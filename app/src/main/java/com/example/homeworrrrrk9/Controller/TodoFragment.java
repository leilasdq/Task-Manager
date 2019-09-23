package com.example.homeworrrrrk9.Controller;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homeworrrrrk9.Model.TaskManager;
import com.example.homeworrrrrk9.R;
import com.example.homeworrrrrk9.Repository.TasksRepository;
import com.example.homeworrrrrk9.State;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodoFragment extends Fragment {
    public static final String TAG_ADD_ITEM_FRAGMENTS = "Add item fragments";
    public static final String TAG_CHANGE_ITEM = "Change item";
    public static final int SHOW_ITEM_FROM_TODO_REQUEST_CODE = 5;
    String TAG = "TODO FRAGMENT";

    private RecyclerView mRecyclerView;
    private List<TaskManager> models;
    private List<TaskManager> todoModels;
    private TodoAdapter adapter;
    private FloatingActionButton todoFab;


    public static TodoFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TodoFragment fragment = new TodoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public TodoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        initUi(view);
        updateAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        todoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowItemFragment itemFragment = ShowItemFragment.newInstance();
                itemFragment.show(getFragmentManager(), TAG_ADD_ITEM_FRAGMENTS);
                itemFragment.setTargetFragment(TodoFragment.this, SHOW_ITEM_FROM_TODO_REQUEST_CODE);
//                getFragmentManager().beginTransaction().hide(TodoFragment.this).commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAdapter();
    }

    private void initUi(View view) {
        mRecyclerView = view.findViewById(R.id.todo_recycler);
        todoFab = view.findViewById(R.id.todo_fab);
        models = TasksRepository.getInstance().getRepositoryList();
        todoModels = new ArrayList<>();
        if (models.size()>0){
            for (int i = 0; i < models.size() ; i++) {
                if (models.get(i).getState()== State.TODO) todoModels.add(models.get(i));
            }
        }
        if (todoModels.size()==0){
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private class TodoViewHolder extends RecyclerView.ViewHolder {
        private TextView roundTxt;
        private TextView titleTxt;
        private TextView descriptionTxt;
        private TextView dateTxt;
        private TextView timeTxt;

        private TaskManager mTaskManager;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);

            roundTxt = itemView.findViewById(R.id.round_text);
            titleTxt = itemView.findViewById(R.id.title_text);
            descriptionTxt = itemView.findViewById(R.id.description_text);
            dateTxt = itemView.findViewById(R.id.date_txt);
            timeTxt = itemView.findViewById(R.id.time_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditItemFragment edit = EditItemFragment.newInstance(mTaskManager);
                    edit.show(getFragmentManager(), TAG_CHANGE_ITEM);
                    Toast.makeText(getActivity(), "On Item Clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bind(TaskManager taskManager){
            mTaskManager = taskManager;
            DateFormat date = new SimpleDateFormat("EEE, MMM d yyyy");
            DateFormat time = new SimpleDateFormat("hh:mm a");

            //roundTxt.setText(mTaskManager.getTitle().charAt(0));
            titleTxt.setText(mTaskManager.getTitle());
            roundTxt.setText(String.valueOf(mTaskManager.getTitle().charAt(0)));
            descriptionTxt.setText(mTaskManager.getDetail());
            dateTxt.setText(date.format(mTaskManager.getDate()));
            timeTxt.setText(time.format(mTaskManager.getDate()));
        }
    }

    private class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder>{
        List<TaskManager> mList;

        public TodoAdapter(List<TaskManager> taskManagers) {
            mList = taskManagers;
        }

        @NonNull
        @Override
        public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_items, parent, false);
            return new TodoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
            holder.bind(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    private void updateAdapter(){
        if (adapter==null){
            adapter = new TodoAdapter(todoModels);
            mRecyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public void notifyAdapter(){
        if (adapter!=null){
            Log.e(TAG, "notifyAdapter: GRRRRR");
            models = TasksRepository.getInstance().getRepositoryList();
            if (models.size()>0){
                for (int i = 0; i < models.size() ; i++) {
                    if (models.get(i).getState()== State.TODO) todoModels.add(models.get(i));
                }
            }
            adapter.notifyDataSetChanged();
            adapter.notifyItemInserted(todoModels.size());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK | data==null){
            return;
        }
        if (requestCode == SHOW_ITEM_FROM_TODO_REQUEST_CODE){
            boolean isTrue = data.getBooleanExtra(ShowItemFragment.EXTRA_FORCE_NOTIFY, true);
            Log.e(TAG, "onActivityResult: " + isTrue  );
            if (isTrue) {
                notifyAdapter();
            }
        }
    }
}
