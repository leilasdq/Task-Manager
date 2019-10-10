package com.example.homeworrrrrk9.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.homeworrrrrk9.Model.DaoMaster;
import com.example.homeworrrrrk9.Model.DaoSession;
import com.example.homeworrrrrk9.Model.Database.GreenDaoHandler.TaskDaoOpenHelper;
import com.example.homeworrrrrk9.Model.User;
import com.example.homeworrrrrk9.Model.UserDao;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static UserRepository ourInstance;
    private static Context mContext;
    public static UserDao userDao;

    public static UserRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new UserRepository(context);
        }
        return ourInstance;
    }

    private UserRepository(Context context) {
        mContext = context.getApplicationContext();

        SQLiteDatabase database = new TaskDaoOpenHelper(mContext).getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        userDao = daoSession.getUserDao();
    }

    public List<User> getRepositoryList(){
        List<User> users = new ArrayList<>();

        users = userDao.loadAll();

        return users;
    }

    public static void addUsers (User user) {
        userDao.insert(user);
    }
}
