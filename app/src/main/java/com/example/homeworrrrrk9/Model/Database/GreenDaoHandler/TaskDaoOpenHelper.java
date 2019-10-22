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

//    @Override
//    public void onUpgrade(Database db, int oldVersion, int newVersion) {
//        super.onUpgrade(db, oldVersion, newVersion);
//
//        if (oldVersion==1 & newVersion==2) {
//            db.execSQL("ALTER TABLE 'Task' ADD 'photo_path' TEXT");
//        } else {
//            db.execSQL("ALTER TABLE 'USER' ADD 'signup_date' Date");
//        }
//    }
}