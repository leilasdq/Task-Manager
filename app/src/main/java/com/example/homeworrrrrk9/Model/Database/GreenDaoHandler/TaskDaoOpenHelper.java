package com.example.homeworrrrrk9.Model.Database.GreenDaoHandler;

import android.content.Context;

import com.example.homeworrrrrk9.Model.DaoMaster;
import com.example.homeworrrrrk9.Model.DaoSession;
import com.example.homeworrrrrk9.Model.User;
import com.example.homeworrrrrk9.Model.UserDao;

import org.greenrobot.greendao.database.Database;

public class TaskDaoOpenHelper extends DaoMaster.OpenHelper {
    public static final String NAME = "task.db";
    public TaskDaoOpenHelper(Context context) {
        super(context, NAME);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);

        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setId(user.getId());

        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();

        userDao.insert(user);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}