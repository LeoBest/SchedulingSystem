package com.example.taskscheduler.api;

import com.example.taskscheduler.impl.ResultImpl;
import com.example.taskscheduler.impl.TaskImpl;
import java.util.function.BiConsumer;

public interface Executor {
  /**

   * Run the task and invoke completionCallback when task is completed.

   * @param task a task to be executed

   * @param completionCallback a callback called on task completion

   */

  void execute(TaskImpl task, BiConsumer<TaskImpl, ResultImpl> completionCallback);

}
