package com.example.homeworrrrrk9.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.homeworrrrrk9.Model.Database.TaskDatabaseSchema;
import com.example.homeworrrrrk9.Model.Database.TasksOpenHelper;
import com.example.homeworrrrrk9.Model.TaskManager;
import com.example.homeworrrrrk9.Model.User;
import com.example.homeworrrrrk9.State;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TasksRepository {
    private static TasksRepository ourInstance;
    static List<TaskManager> sTaskManagers;
    private static Context mContext;
    private static SQLiteDatabase database;

    public static TasksRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new TasksRepository(context);
        }
        return ourInstance;
    }

    private TasksRepository(Context context) {
        mContext = context.getApplicationContext();

        TasksOpenHelper helper = new TasksOpenHelper(context);
        database = helper.getWritableDatabase();
    }

    public List<TaskManager> getRepositoryList() {
        sTaskManagers = new ArrayList<>();
        Cursor cursor = database.query(TaskDatabaseSchema.TaskTable.TASKTABLENAME, null, null, null, null, null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                long taskId = cursor.getLong(cursor.getColumnIndex(TaskDatabaseSchema.TaskTable.Cols._TASKID));
                String stringUuid = cursor.getString(cursor.getColumnIndex(TaskDatabaseSchema.TaskTable.Cols.TASKUUID));
                String title = cursor.getString(cursor.getColumnIndex(TaskDatabaseSchema.TaskTable.Cols.TASKTITLE));
                String detail = cursor.getString(cursor.getColumnIndex(TaskDatabaseSchema.TaskTable.Cols.TASKDETAIL));
                long longDate = cursor.getLong(cursor.getColumnIndex(TaskDatabaseSchema.TaskTable.Cols.TASKDATE));
                String stringState = cursor.getString(cursor.getColumnIndex(TaskDatabaseSchema.TaskTable.Cols.TASKSTATE));
                int userId = cursor.getInt(cursor.getColumnIndex(TaskDatabaseSchema.TaskTable.Cols.USERID));

                UUID uuid = UUID.fromString(stringUuid);
                Date date = new Date(longDate);
                State state = State.valueOf(stringState);

                TaskManager taskManager = new TaskManager(uuid);
                taskManager.setTaskId(taskId);
                taskManager.setTitle(title);
                taskManager.setDetail(detail);
                taskManager.setDate(date);
                taskManager.setState(state);
                taskManager.setUserId(userId);

                sTaskManagers.add(taskManager);

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return sTaskManagers;
    }

    public static void addTodoItem (TaskManager taskManager) {
        ContentValues values = getTasksContentValues(taskManager);
        database.insert(TaskDatabaseSchema.TaskTable.TASKTABLENAME, null, values);
        //sTaskManagers.add(taskManager);
    }

    public static void editItem(TaskManager taskManager){
        deleteItem(taskManager);
        sTaskManagers.add(taskManager);
    }

    public static void deleteItem (TaskManager taskManager){
        String[] whereArgs = {String.valueOf(taskManager.getTaskId())};
        database.delete(TaskDatabaseSchema.TaskTable.TASKTABLENAME, TaskDatabaseSchema.TaskTable.Cols._TASKID + " = ?", whereArgs);
        //sTaskManagers.remove(taskManager);
    }

    public static void deleteAll(){
        database.delete(TaskDatabaseSchema.TaskTable.TASKTABLENAME, null, null);
        //sTaskManagers.clear();
    }

    public static TaskManager getTask(UUID uuid){
        for (TaskManager task : sTaskManagers) {
            if (task.getUUID().equals(uuid))
                return task;
        }

        return null;
    }

    private static ContentValues getTasksContentValues(TaskManager taskManager){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskDatabaseSchema.TaskTable.Cols._TASKID, taskManager.getTaskId());
        contentValues.put(TaskDatabaseSchema.TaskTable.Cols.TASKUUID, taskManager.getUUID().toString());
        contentValues.put(TaskDatabaseSchema.TaskTable.Cols.TASKTITLE, taskManager.getTitle());
        contentValues.put(TaskDatabaseSchema.TaskTable.Cols.TASKDETAIL, taskManager.getDetail());
        contentValues.put(TaskDatabaseSchema.TaskTable.Cols.TASKDATE, taskManager.getDate().getTime());
        contentValues.put(TaskDatabaseSchema.TaskTable.Cols.TASKSTATE, taskManager.getState().toString());
        contentValues.put(TaskDatabaseSchema.TaskTable.Cols.USERID, taskManager.getUserId());

        return contentValues;
    }

}
