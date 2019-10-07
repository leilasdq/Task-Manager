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
//    static List<TaskManager> sTaskManagers;
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

        database = new TasksOpenHelper(mContext).getWritableDatabase();
    }

    public List<TaskManager> getRepositoryList(long id) {
//        sTaskManagers = new ArrayList<>();
        Cursor cursor;
        if (id==1) {
            cursor = database.query(TaskDatabaseSchema.TaskTable.TASKTABLENAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
        } else {
            String where = TaskDatabaseSchema.TaskTable.Cols.USERID + " = ?";
            String[] whereArgs = {String.valueOf(id)};
            cursor = database.query(TaskDatabaseSchema.TaskTable.TASKTABLENAME,
                    null,
                    where,
                    whereArgs,
                    null,
                    null,
                    null);
        }
        List<TaskManager> taskManagers = new ArrayList<>();

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

                taskManagers.add(taskManager);

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return taskManagers;
    }

    public static void addTodoItem (TaskManager taskManager) {
        ContentValues values = getTasksContentValues(taskManager);
        database.insert(TaskDatabaseSchema.TaskTable.TASKTABLENAME, null, values);
        //sTaskManagers.add(taskManager);
    }

    public static void editItem(TaskManager taskManager){
        ContentValues values = getTasksContentValues(taskManager);
        String[] whereArgs = {String.valueOf(taskManager.getTaskId())};
        database.update(TaskDatabaseSchema.TaskTable.TASKTABLENAME, values, TaskDatabaseSchema.TaskTable.Cols._TASKID + " = ?", whereArgs);
    }

    public static void deleteItem (TaskManager taskManager){
        String[] whereArgs = {String.valueOf(taskManager.getTaskId())};
        database.delete(TaskDatabaseSchema.TaskTable.TASKTABLENAME, TaskDatabaseSchema.TaskTable.Cols._TASKID + " = ?", whereArgs);
    }

    public static void deleteAll(){
        database.delete(TaskDatabaseSchema.TaskTable.TASKTABLENAME, null, null);
    }

    private static ContentValues getTasksContentValues(TaskManager taskManager){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskDatabaseSchema.TaskTable.Cols.TASKUUID, taskManager.getUUID().toString());
        contentValues.put(TaskDatabaseSchema.TaskTable.Cols.TASKTITLE, taskManager.getTitle());
        contentValues.put(TaskDatabaseSchema.TaskTable.Cols.TASKDETAIL, taskManager.getDetail());
        contentValues.put(TaskDatabaseSchema.TaskTable.Cols.TASKDATE, taskManager.getDate().getTime());
        contentValues.put(TaskDatabaseSchema.TaskTable.Cols.TASKSTATE, taskManager.getState().toString());
        contentValues.put(TaskDatabaseSchema.TaskTable.Cols.USERID, taskManager.getUserId());

        return contentValues;
    }

}
