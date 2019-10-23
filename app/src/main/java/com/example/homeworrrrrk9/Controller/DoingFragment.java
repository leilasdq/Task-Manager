package com.example.homeworrrrrk9.Controller;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.SearchView;
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
public class DoingFragment extends Fragment {

    public static final String TAG_CHANGE_ITEM = "Change item";
    public static final String TAG_ADD_ITEM_FRAGMENTS = "Add item fragments";
    public static final int SHOW_ITEM_FROM_TODO_REQUEST_CODE = 6;

    private RecyclerView mRecyclerView;
    private List<TaskManager> models;
    private List<TaskManager> doingModels;
    private DoingAdapter adapter;
    private FloatingActionButton doingFab;

    private String user, pass;
    private long userId;
    private AlertDialog delete;

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
        updateAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);
        adapter = new DoingAdapter(doingModels);
        mRecyclerView.setAdapter(adapter);

        doingFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowItemFragment itemFragment = ShowItemFragment.newInstance();
                itemFragment.show(getFragmentManager(), TAG_ADD_ITEM_FRAGMENTS);
                itemFragment.setTargetFragment(DoingFragment.this, SHOW_ITEM_FROM_TODO_REQUEST_CODE);
//                adapter.notifyItemInserted(doingModels.size()+1);
//                getFragmentManager().beginTransaction().hide(TodoFragment.this).commit();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK | data==null){
            return;
        }
        if (requestCode == SHOW_ITEM_FROM_TODO_REQUEST_CODE){
            boolean isTrue = data.getBooleanExtra(ShowItemFragment.EXTRA_FORCE_NOTIFY, true);
//            Log.e(TAG, "onActivityResult: " + isTrue  );
            if (isTrue) {
                updateAdapter();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAdapter();
    }

    private void initUi(View view) {
        user = ListsActivity.getUser();
        pass = ListsActivity.getPass();
        userId = ListsActivity.getUserId();

        mRecyclerView = view.findViewById(R.id.doing_recycler);
        models = TasksRepository.getInstance(getContext()).getRepositoryList(userId);
        doingFab = view.findViewById(R.id.doing_fab);
    }

    private class DoingViewHolder extends RecyclerView.ViewHolder {
        private TextView roundTxt;
        private TextView titleTxt;
        private TextView descriptionTxt;
        private TextView dateTxt;
        private TextView timeTxt;
        private ImageButton share;

        private TaskManager mTaskManager;

        public DoingViewHolder(@NonNull View itemView) {
            super(itemView);

            roundTxt = itemView.findViewById(R.id.round_text);
            titleTxt = itemView.findViewById(R.id.title_text);
            descriptionTxt = itemView.findViewById(R.id.description_text);
            dateTxt = itemView.findViewById(R.id.date_txt);
            timeTxt = itemView.findViewById(R.id.time_text);
            share = itemView.findViewById(R.id.share);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditItemFragment edit = EditItemFragment.newInstance(mTaskManager);
                    edit.show(getFragmentManager(), TAG_CHANGE_ITEM);
                    edit.setTargetFragment(DoingFragment.this, SHOW_ITEM_FROM_TODO_REQUEST_CODE);
                }
            });
        }

        public void bind(TaskManager taskManager){
            mTaskManager = taskManager;
            DateFormat date = new SimpleDateFormat("EEE, MMM d yyyy");
            DateFormat time = new SimpleDateFormat("hh:mm a");

            //roundTxt.setText(mTaskManager.getTitle().charAt(0));
            titleTxt.setText(mTaskManager.getTitle());
            roundTxt.setText(String.valueOf(mTaskManager.getTitle().charAt(0)).toUpperCase());
            descriptionTxt.setText(mTaskManager.getDetail());
            dateTxt.setText(date.format(mTaskManager.getDate()));
            timeTxt.setText(time.format(mTaskManager.getDate()));

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "I took a task for managing my ToDo lists.let's take a look at it: ");
                    intent.putExtra(Intent.EXTRA_TEXT, getTaskReport());
                    intent = Intent.createChooser(intent, "Choose your app to share");
                    startActivity(intent);
                }
            });
        }

        private  String getTaskReport(){
            String dateString = new SimpleDateFormat("yyyy/MM/dd").format(mTaskManager.getDate());
            return "Task title is " + mTaskManager.getTitle() + " and detail is: " + mTaskManager.getDetail() +
                    ".\nMy task state is " + mTaskManager.getState() + " so I am doing it from " + dateString + " till now.";
        }
    }

    private class DoingAdapter extends RecyclerView.Adapter<DoingViewHolder> implements Filterable {

        public void setList(List<TaskManager> list) {
            mList = list;
        }

        private List<TaskManager> mList;
        private List<TaskManager> fullList;

        public DoingAdapter(List<TaskManager> taskManagers) {
            mList = taskManagers;
            fullList = new ArrayList<>(taskManagers);
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

        @Override
        public Filter getFilter() {
            return taskFilter;
        }

        private Filter taskFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<TaskManager> filterTasks = new ArrayList<>();

                if (charSequence==null | charSequence.length()==0) {
                    filterTasks.addAll(fullList);
                } else {
                    String filterPattern = charSequence.toString().toLowerCase().trim();

                    for (TaskManager item :
                            fullList) {
                        if (item.getTitle().toLowerCase().contains(filterPattern)){
                            filterTasks.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filterTasks;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mList.clear();
                mList.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.action_bar_item_menu, menu);
        if (userId == 1) {
           // menu.getItem(R.id.user_control).setVisible(true);
            menu.findItem(R.id.user_control).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.user_control:
                Intent intent = UserControlActivity.newIntent(getActivity());
                startActivity(intent);
                return  true;
            case R.id.search:
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
                searchView.setIconified(false);
                searchView.setQueryHint("Search Here");
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        adapter.getFilter().filter(s);
                        adapter.notifyDataSetChanged();
                        return false;
                    }
                });
                searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                });
                item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem menuItem) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                        updateAdapter();
                        return true;
                    }
                });
                return true;
            case R.id.account:
                //Toast.makeText(this, "Account clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.show_account:
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Account detail")
                        .setMessage("username: " + user + "\npassword: " + pass+ "\nUserId: " + userId)
                        .setNegativeButton("OK", null).create();
                alertDialog.show();
                return true;
            case R.id.log_out:
                System.exit(1);
                return true;
            case R.id.delete_all:
                final List<TaskManager> models = TasksRepository.getInstance(getActivity()).getRepositoryList(userId);
                delete = new AlertDialog.Builder(getActivity())
                        .setTitle("Delete all items")
                        .setMessage("All items will be delete.\nAre you sure?")
                        .setCancelable(false)
                        .setIcon(R.drawable.ic_warning_black_24dp)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (models.size()>0){
                                    TasksRepository.deleteAll(userId);
                                    delete.cancel();
//                                    getActivity().recreate();
                                } else {
                                    Toast.makeText(getActivity(), "You didn't have any items", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialogInterface) {
                                updateAdapter();
                            }
                        })
                        .create();
                delete.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateAdapter(){
        setupList();
        if (adapter==null){
            adapter = new DoingAdapter(doingModels);
            mRecyclerView.setAdapter(adapter);
        } else {
            adapter.setList(doingModels);
            adapter.notifyDataSetChanged();
        }
    }

    private void setupList() {
        models = new ArrayList<>();
        models = TasksRepository.getInstance(getContext()).getRepositoryList(userId);
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



}
