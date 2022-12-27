package com.example.taskscheduler.api;

import com.example.taskscheduler.impl.TaskImpl;
import java.util.UUID;

public interface TaskScheduler {
  /**
   * Submit new task to be scheduled and executed.
   *
   * @param task a task to be scheduled and executed
   */

  void submitTask(TaskImpl task);
  /**
   * Return result of a completed task
   *
   * @param uuid task UUID
   * @return execution result of the task
   */

  Result getResult(UUID uuid);
}
