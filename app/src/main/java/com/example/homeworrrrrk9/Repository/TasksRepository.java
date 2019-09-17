package com.example.homeworrrrrk9.Repository;

import com.example.homeworrrrrk9.Model.TaskManager;
import com.example.homeworrrrrk9.State;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TasksRepository {
    private static TasksRepository ourInstance;
    static List<TaskManager> sTaskManagers;

    public static TasksRepository getInstance() {
        if (ourInstance == null)
            ourInstance = new TasksRepository();
        return ourInstance;
    }

    private TasksRepository() {
        sTaskManagers = new ArrayList<>();
        for (int i = 1; i <= 7 ; i++) {
            TaskManager task = new TaskManager();

            GregorianCalendar gc = new GregorianCalendar();
            int year = randBetween(2000, 2019);
            gc.set(gc.YEAR, year);
            int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
            gc.set(gc.DAY_OF_YEAR, dayOfYear);

            task.setTitle("Title# " + i);
            task.setDetail("Detail# " + i);
            task.setDate(gc.getTime());
            task.setState(State.TODO);

            sTaskManagers.add(task);
        }
        for (int i = 8; i <= 10 ; i++) {
            TaskManager task = new TaskManager();

            GregorianCalendar gc = new GregorianCalendar();
            int year = randBetween(2000, 2019);
            gc.set(gc.YEAR, year);
            int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
            gc.set(gc.DAY_OF_YEAR, dayOfYear);

            task.setTitle("Title# " + i);
            task.setDetail("Detail# " + i);
            task.setDate(gc.getTime());
            task.setState(State.DONE);

            sTaskManagers.add(task);
        }
    }

    public List<TaskManager> getRepositoryList() {
        return sTaskManagers;
    }

    public static void addTodoItem (TaskManager taskManager) {
        sTaskManagers.add(taskManager);
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
