package com.example.homeworrrrrk9.Model.Database;

public class TaskDatabaseSchema {
    public static final String DATABSENAME = "Tasks.db";

    // Task Table
    public static final class TaskTable{
        public static final String TASKTABLENAME = "Task_table";

        public static final class Cols{
            public static final String _TASKID = "_id";
            public static final String TASKUUID = "uuid";
            public static final String TASKTITLE = "title";
            public static final String TASKDETAIL = "detail";
            public static final String TASKDATE = "date";
            public static final String TASKSTATE = "state";
            // Foreign key from user table
            public static final String USERID = "user_id";
        }
    }

    // User Table
    public static final class UserTale{
        public static final String USERTABLENAME = "User_table";

        public static final class Cols{
            public static final String _USERID = "_id";
            public static final String USERUUID = "uuid";
            public static final String USERNAME = "username";
            public static final String USERPASSWORD = "password";
        }
    }
}
