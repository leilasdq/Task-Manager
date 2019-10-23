package com.example.homeworrrrrk9.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract;

import com.example.homeworrrrrk9.Model.DaoMaster;
import com.example.homeworrrrrk9.Model.DaoSession;
import com.example.homeworrrrrk9.Model.Database.GreenDaoHandler.TaskDaoOpenHelper;
import com.example.homeworrrrrk9.Model.TaskManager;
import com.example.homeworrrrrk9.Model.TaskManagerDao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TasksRepository {
    private static TasksRepository ourInstance;
    private static Context mContext;
    private static TaskManagerDao taskDao;

    public static TasksRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new TasksRepository(context);
        }
        return ourInstance;
    }

    private TasksRepository(Context context) {
        mContext = context.getApplicationContext();

        SQLiteDatabase database =new TaskDaoOpenHelper(mContext).getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
//        if (DaoMaster.SCHEMA_VERSION == 1) {
//            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(mContext, TaskDaoOpenHelper.NAME, null);
//            devOpenHelper.onUpgrade(database, 1, 2);
//        }
        DaoSession daoSession = daoMaster.newSession();
        taskDao = daoSession.getTaskManagerDao();
    }

    public List<TaskManager> getRepositoryList(long id) {
        List<TaskManager> taskManagers = new ArrayList<>();

        if (id == 1){
            taskManagers = taskDao.queryBuilder().list();
        } else {
            taskManagers = taskDao.queryBuilder().where(TaskManagerDao.Properties.UserId.eq(id)).list();
        }

        return taskManagers;
    }

    public static void addTodoItem (TaskManager taskManager) {
        taskDao.insert(taskManager);
    }

    public static void editItem(TaskManager taskManager){
        taskDao.update(taskManager);
    }

    public static void deleteItem (TaskManager taskManager){
        taskDao.delete(taskManager);
    }

    public void cascadeDelete(Long id){
        List<TaskManager> taskManagerList =  getRepositoryList(1);
        for (int i = 0; i < taskManagerList.size() ; i++) {
            if (taskManagerList.get(i).getUserId().equals(id)){
                taskDao.delete(taskManagerList.get(i));
            }
        }
    }

    public static void deleteAll(Long id){
        getInstance(mContext).cascadeDelete(id);
    }

    public int getTasksCount(Long id){
        List<TaskManager> taskManagerList =  getRepositoryList(id);
        return taskManagerList.size();
    }

    public File getPhotoFile(TaskManager taskManager){
        return new File(mContext.getFilesDir(), taskManager.getPhotoName());
    }

}
