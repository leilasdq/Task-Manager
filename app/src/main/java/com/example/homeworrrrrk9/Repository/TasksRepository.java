package com.example.homeworrrrrk9.Repository;

import com.example.homeworrrrrk9.Model.TaskManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
//        for (int i = 1; i <= 4 ; i++) {
//            TaskManager task = new TaskManager();
//
//            GregorianCalendar gc = new GregorianCalendar();
//            int year = randBetween(2000, 2019);
//            gc.set(gc.YEAR, year);
//            int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
//            gc.set(gc.DAY_OF_YEAR, dayOfYear);
//
//            task.setTitle("Title# " + i);
//            task.setDetail("Detail# " + i);
//            task.setDate(gc.getTime());
//            task.setState(State.TODO);
//
//            sTaskManagers.add(task);
//        }
//        for (int i = 5; i <= 10 ; i++) {
//            TaskManager task = new TaskManager();
//
//            GregorianCalendar gc = new GregorianCalendar();
//            int year = randBetween(2000, 2019);
//            gc.set(gc.YEAR, year);
//            int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
//            gc.set(gc.DAY_OF_YEAR, dayOfYear);
//
//            task.setTitle("Title# " + i);
//            task.setDetail("Detail# " + i);
//            task.setDate(gc.getTime());
//            task.setState(State.DONE);
//
//            sTaskManagers.add(task);
//        }
//        for (int i = 11; i <= 15 ; i++) {
//            TaskManager task = new TaskManager();
//
//            GregorianCalendar gc = new GregorianCalendar();
//            int year = randBetween(2000, 2019);
//            gc.set(gc.YEAR, year);
//            int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
//            gc.set(gc.DAY_OF_YEAR, dayOfYear);
//
//            task.setTitle("Title# " + i);
//            task.setDetail("Detail# " + i);
//            task.setDate(gc.getTime());
//            task.setState(State.DOING);
//
//            sTaskManagers.add(task);
//        }
    }

    public List<TaskManager> getRepositoryList() {
        return sTaskManagers;
    }

    public static void addTodoItem (TaskManager taskManager) {
        sTaskManagers.add(taskManager);
    }

    public static void editItem(TaskManager taskManager){
        deleteItem(taskManager);
        sTaskManagers.add(taskManager);
    }

    public static void deleteItem (TaskManager taskManager){
        sTaskManagers.remove(taskManager);
    }

    public static void deleteAll(){
        sTaskManagers.clear();
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    public static TaskManager getTask(UUID uuid){
        for (TaskManager task : sTaskManagers) {
            if (task.getUUID().equals(uuid))
                return task;
        }

        return null;
    }
}
