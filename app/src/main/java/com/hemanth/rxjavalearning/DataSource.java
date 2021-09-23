package com.hemanth.rxjavalearning;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static List<Task> createTaskList(){
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Make my bed",true, 1));
        tasks.add(new Task("Clear dishwasher",true, 2));
        tasks.add(new Task("Make breakfast",true, 3));
        tasks.add(new Task("Learn something today",false, 4));
        tasks.add(new Task("Prepare Dinner",false, 5));
        return tasks;
    }
}
