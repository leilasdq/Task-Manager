package com.example.homeworrrrrk9.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
    private static TaskManagerDao taaskDao;

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
        DaoSession daoSession = daoMaster.newSession();
        taaskDao = daoSession.getTaskManagerDao();
    }

    public List<TaskManager> getRepositoryList(long id) {
        List<TaskManager> taskManagers = new ArrayList<>();

        if (id == 1){
            taskManagers = taaskDao.loadAll();
        } else {
            taskManagers = taaskDao.queryBuilder().where(TaskManagerDao.Properties.UserId.eq(id)).list();
        }

        return taskManagers;
    }

    public static void addTodoItem (TaskManager taskManager) {
        taaskDao.insert(taskManager);
    }

    public static void editItem(TaskManager taskManager){
        taaskDao.update(taskManager);
    }

    public static void deleteItem (TaskManager taskManager){
        taaskDao.delete(taskManager);
    }

    public static void deleteAll(){
        taaskDao.deleteAll();
    }

    public File getPhotoFile(TaskManager taskManager){
        return new File(mContext.getFilesDir(), taskManager.getPhotoName());
    }

}
