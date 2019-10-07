package com.example.homeworrrrrk9.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.homeworrrrrk9.Model.Database.TaskDatabaseSchema;
import com.example.homeworrrrrk9.Model.Database.TasksOpenHelper;
import com.example.homeworrrrrk9.Model.TaskManager;
import com.example.homeworrrrrk9.Model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository {
    private static UserRepository ourInstance;
    private static Context mContext;
    private static SQLiteDatabase database;

    public static UserRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new UserRepository(context);
        }
        return ourInstance;
    }

    private UserRepository(Context context) {
        mContext = context.getApplicationContext();

        database = new TasksOpenHelper(mContext).getWritableDatabase();
    }

    public List<User> getRepositoryList(){
        List<User> users = new ArrayList<>();
        Cursor cursor = database.query(TaskDatabaseSchema.UserTale.USERTABLENAME,
                null,
                null,
                null,
                null,
                null,
                null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                long userId = cursor.getLong(cursor.getColumnIndex(TaskDatabaseSchema.UserTale.Cols._USERID));
                String username = cursor.getString(cursor.getColumnIndex(TaskDatabaseSchema.UserTale.Cols.USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(TaskDatabaseSchema.UserTale.Cols.USERPASSWORD));
                String strUuid = cursor.getString(cursor.getColumnIndex(TaskDatabaseSchema.UserTale.Cols.USERUUID));

                UUID uuid = UUID.fromString(strUuid);

                User user = new User(uuid);
                user.setUserId(userId);
                user.setUsername(username);
                user.setPassword(password);

                users.add(user);

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return users;
    }

    public static void addUsers (User user) {
        ContentValues values = getTasksContentValues(user);
        database.insert(TaskDatabaseSchema.UserTale.USERTABLENAME, null, values);
        //sTaskManagers.add(taskManager);
    }

    private static ContentValues getTasksContentValues(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskDatabaseSchema.UserTale.Cols.USERUUID, user.getId().toString());
        contentValues.put(TaskDatabaseSchema.UserTale.Cols.USERNAME, user.getUsername());
        contentValues.put(TaskDatabaseSchema.UserTale.Cols.USERPASSWORD, user.getPassword());

        return contentValues;
    }
}
