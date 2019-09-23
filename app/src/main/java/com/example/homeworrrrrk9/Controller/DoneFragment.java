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
public class DoneFragment extends Fragment {

    public static final String TAG_CHANGE_ITEM = "Change item";
    public static final String TAG_ADD_ITEM_FRAGMENTS = "Add item fragments";
    public static final int SHOW_ITEM_FROM_TODO_REQUEST_CODE = 7;

    private RecyclerView mRecyclerView;
    private List<TaskManager> models;
    private List<TaskManager> doneModels;
    private DoneAdapter adapter;
    private FloatingActionButton doneFab;

    public static DoneFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DoneFragment fragment = new DoneFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public DoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_done, container, false);

        initUi(view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DoneAdapter(doneModels);
        mRecyclerView.setAdapter(adapter);

        doneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowItemFragment itemFragment = ShowItemFragment.newInstance();
                itemFragment.show(getFragmentManager(), TAG_ADD_ITEM_FRAGMENTS);
                itemFragment.setTargetFragment(DoneFragment.this, SHOW_ITEM_FROM_TODO_REQUEST_CODE);
                adapter.notifyDataSetChanged();
//                getFragmentManager().beginTransaction().hide(TodoFragment.this).commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }

    private void initUi(View view) {
        mRecyclerView = view.findViewById(R.id.done_recycler);
        models = TasksRepository.getInstance().getRepositoryList();
        doneFab = view.findViewById(R.id.done_fab);
        doneModels = new ArrayList<>();
        if (models.size()>0){
            for (int i = 0; i < models.size() ; i++) {
                if (models.get(i).getState()== State.DONE) doneModels.add(models.get(i));
            }
        }
        if (doneModels.size()==0){
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private class DoneViewHolder extends RecyclerView.ViewHolder {
        private TextView roundTxt;
        private TextView titleTxt;
        private TextView descriptionTxt;
        private TextView dateTxt;
        private TextView timeTxt;

        private TaskManager mTaskManager;

        public DoneViewHolder(@NonNull View itemView) {
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

    private class DoneAdapter extends RecyclerView.Adapter<DoneViewHolder>{
        List<TaskManager> mList;

        public DoneAdapter(List<TaskManager> taskManagers) {
            mList = taskManagers;
        }

        @NonNull
        @Override
        public DoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_items, parent, false);
            return new DoneViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DoneViewHolder holder, int position) {
            holder.bind(mList.get(position));
        }

        @Override
        public int getItemCount() {
//            Log.e(TAG, "getItemCount: " + mList.size() );
            return mList.size();
        }
    }

}
