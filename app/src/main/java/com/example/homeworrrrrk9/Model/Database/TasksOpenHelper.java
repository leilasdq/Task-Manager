package com.example.homeworrrrrk9.Model.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.homeworrrrrk9.Model.User;

import java.util.UUID;

import androidx.annotation.Nullable;

import static com.example.homeworrrrrk9.Model.Database.TaskDatabaseSchema.*;
import static com.example.homeworrrrrk9.Model.Database.TaskDatabaseSchema.UserTale.USERTABLENAME;

public class TasksOpenHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;

    public TasksOpenHelper(@Nullable Context context) {
        super(context, TaskDatabaseSchema.DATABSENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE \"" + TaskTable.TASKTABLENAME + "\"("
                + TaskTable.Cols._TASKID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TaskTable.Cols.TASKUUID + ", "
                + TaskTable.Cols.TASKTITLE + ", "
                + TaskTable.Cols.TASKDETAIL + ", "
                + TaskTable.Cols.TASKDATE + ", "
                + TaskTable.Cols.TASKSTATE + ", "
                + TaskTable.Cols.USERID
                + ");"
        );

        sqLiteDatabase.execSQL("CREATE TABLE \"" + USERTABLENAME + "\"("
                + UserTale.Cols._USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserTale.Cols.USERUUID + ", "
                + UserTale.Cols.USERNAME + ", "
                + UserTale.Cols.USERPASSWORD
                + ");"
        );

        User user = new User();
        sqLiteDatabase.execSQL("INSERT INTO " + USERTABLENAME + " ("
                + UserTale.Cols.USERUUID + ", " + UserTale.Cols.USERNAME + ", " + UserTale.Cols.USERPASSWORD + ")"
                + " VALUES (\"" + user.getId().toString() + "\", \"admin\", \"admin\");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
