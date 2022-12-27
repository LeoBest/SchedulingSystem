package com.example.taskscheduler.impl;


import com.example.taskscheduler.api.Executor;
import java.util.function.BiConsumer;

public class ExecutorImpl implements Executor {

  @Override
  public void execute(TaskImpl task,
      BiConsumer<TaskImpl, ResultImpl> completionCallback) {

    new Thread(() -> completionCallback
        .accept(task, task.execute()))
        .start();
  }
}