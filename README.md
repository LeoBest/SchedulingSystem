# SchedulingSystem
public interface TaskScheduler {
    /**
     * Submit new task to be scheduled and executed.
     * @param task a task to be scheduled and executed
     */
    void submitTask(Task task);
    /**
     * Return result of a completed task
     * @param uuid task UUID
     * @return execution result of the task
     */
    Result getResult(UUID uuid);
}
public interface Task {
    Result execute();
    // add any other necessary methods
}
public interface Result {
    boolean isSuccess();
    Object getValue();
}
interface Executor {
    /**
     * Run the task and invoke completionCallback when task is completed.
     * @param task a task to be executed
     * @param completionCallback a callback called on task completion
     */
    void execute(Task task, BiConsumer<Task, Result> completionCallback);
}

A task handled by the system has the following properties:
  1. A UUID for identification.
  2. A task group ID (GID)
  3. An operation type (READ or WRITE).
  4. Has an execute method that returns operation results.
  The system should have the following properties:
    1. Tasks can be submitted concurrently, and task submission should not
       block the caller.
    2. Tasks are executed asynchronously and concurrently.
    3. Once a task is finished, its results can be retrieved from the system
       based on the task's original UUID.
    4. An order of tasks must be preserved. The first task accepted must be
       the first task to be started.
    5. Tasks with the same GID must not run concurrently.
    6. READ and WRITE tasks must not run concurrently.
       * Multiple READ tasks may run concurrently.
       * Multiple WRITE tasks may run concurrently.
============================================================================

Explanation:
The TaskSchedulerImpl class implements the TaskScheduler interface and
contains the logic for scheduling tasks based on the 6 properties specified.

The submitTask() method uses a concurrent queue to add the tasks
and they can be submitted concurrently without blocking the caller.
The executor.execute() method is used to execute the tasks asynchronously and concurrently.
The results of the task can be retrieved from the results map using the task's UUID.
The processQueue() method is used to process the tasks in the queue in order.
The canExecute() method checks if any task with the same GID is running
or if any READ or WRITE tasks are running before executing the task.
Multiple READ tasks can run concurrently and multiple WRITE tasks can run concurrently.
