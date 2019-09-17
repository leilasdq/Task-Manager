package com.example.homeworrrrrk9.Controller;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.homeworrrrrk9.Model.TaskManager;
import com.example.homeworrrrrk9.R;
import com.example.homeworrrrrk9.Repository.TasksRepository;
import com.example.homeworrrrrk9.State;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodoFragment extends Fragment {
    String TAG = "TODO FRAGMENT";

    private RecyclerView mRecyclerView;
    private List<TaskManager> models;
    private List<TaskManager> todoModels;
    private TodoAdapter adapter;

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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TodoAdapter(todoModels);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    private void initUi(View view) {
        mRecyclerView = view.findViewById(R.id.todo_recycler);
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
            Log.e(TAG, "getItemCount: " + mList.size() );
            return mList.size();
        }
    }
}
