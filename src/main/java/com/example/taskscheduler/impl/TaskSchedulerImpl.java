package com.example.taskscheduler.impl;


import com.example.taskscheduler.api.Executor;
import com.example.taskscheduler.api.Result;
import com.example.taskscheduler.api.TaskScheduler;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class TaskSchedulerImpl implements TaskScheduler {

  private final Executor executor;
  private final Map<UUID, ResultImpl> results = new ConcurrentHashMap<>();
  private final Queue<TaskImpl> taskQueue = new ConcurrentLinkedQueue<>();
  private final Set<String> runningTaskGroups = new ConcurrentSkipListSet<>();
  private final Set<UUID> runningReadTasks = new ConcurrentSkipListSet<>();
  private final Set<UUID> runningWriteTasks = new ConcurrentSkipListSet<>();

  @Override
  public void submitTask(TaskImpl task) {
    log.debug("Added task to taskQueue with uuid: {}", task);
    taskQueue.add(task);
    processQueue();
  }

  @Override
  public Result getResult(UUID uuid) {
    log.debug("Getting result with uuid: {}", uuid);
    return results.get(uuid);
  }

  private void processQueue() {
    TaskImpl task = taskQueue.poll();
    if (task == null) {
      return;
    }
    boolean canExecute = canTaskStart(task);
    if (canExecute) {
      log.debug("Execute task with: {}", task.getUuid());
      executeTask(task);
    } else {
      log.debug("Task cannot be executed, adding it back to the queue: {}", task);
      taskQueue.add(task);
    }
  }

  private boolean canTaskStart(TaskImpl task) {
    String groupId = task.getGroupId();
    return !runningTaskGroups.contains(groupId)
        && (task.getType() != TaskType.READ || runningWriteTasks.isEmpty())
        && (task.getType() != TaskType.WRITE || runningReadTasks.isEmpty());
  }

  private void executeTask(TaskImpl task) {
    addTaskToGroup(task);
    executeTaskInExecutor(task);
  }

  private void addTaskToGroup(TaskImpl task) {
    runningTaskGroups.add(task.getGroupId());
    UUID uuid = task.getUuid();
    if (task.getType() == TaskType.READ) {
      runningReadTasks.add(uuid);
      log.debug("Execute read task: {}", task);
    } else {
      runningWriteTasks.add(uuid);
      log.debug("Execute write task: {}", task);
    }
  }

  private void executeTaskInExecutor(TaskImpl task) {
    executor.execute(task, (t, r) -> {
      removeTaskFromGroup(task);
      storeResult(task, r);
      processQueue();
    });
  }

  private void removeTaskFromGroup(TaskImpl task) {
    var uuid = task.getUuid();
    var groupId = task.getGroupId();

    runningTaskGroups.remove(groupId);
    runningReadTasks.remove(uuid);
    runningWriteTasks.remove(uuid);
  }

  private void storeResult(TaskImpl task, ResultImpl result) {
    results.put(task.getUuid(), result);
  }
}

