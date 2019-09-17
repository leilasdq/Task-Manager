package com.example.homeworrrrrk9.Controller;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
public class DoingFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<TaskManager> models;
    private List<TaskManager> doingModels;
    private DoingAdapter adapter;

    public static DoingFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DoingFragment fragment = new DoingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public DoingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doing, container, false);

        initUi(view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DoingAdapter(doingModels);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    private void initUi(View view) {
        mRecyclerView = view.findViewById(R.id.doing_recycler);
        models = TasksRepository.getInstance().getRepositoryList();
        doingModels = new ArrayList<>();
        if (models.size()>0){
            for (int i = 0; i < models.size() ; i++) {
                if (models.get(i).getState()== State.DOING) doingModels.add(models.get(i));
            }
        }
        if (doingModels.size()==0){
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private class DoingViewHolder extends RecyclerView.ViewHolder {
        private TextView roundTxt;
        private TextView titleTxt;
        private TextView descriptionTxt;
        private TextView dateTxt;
        private TextView timeTxt;

        private TaskManager mTaskManager;

        public DoingViewHolder(@NonNull View itemView) {
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

    private class DoingAdapter extends RecyclerView.Adapter<DoingViewHolder>{
        List<TaskManager> mList;

        public DoingAdapter(List<TaskManager> taskManagers) {
            mList = taskManagers;
        }

        @NonNull
        @Override
        public DoingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_items, parent, false);
            return new DoingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DoingViewHolder holder, int position) {
            holder.bind(mList.get(position));
        }

        @Override
        public int getItemCount() {
//            Log.e(TAG, "getItemCount: " + mList.size() );
            return mList.size();
        }
    }

}
