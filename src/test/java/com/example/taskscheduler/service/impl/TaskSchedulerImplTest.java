package com.example.taskscheduler.service.impl;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.taskscheduler.impl.ExecutorImpl;
import com.example.taskscheduler.impl.TaskImpl;
import com.example.taskscheduler.impl.TaskType;
import com.example.taskscheduler.api.TaskScheduler;
import com.example.taskscheduler.impl.TaskSchedulerImpl;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class TaskSchedulerImplTest {

  @Test
  void testTaskScheduling() throws InterruptedException {
    TaskScheduler scheduler = new TaskSchedulerImpl(new ExecutorImpl());
    UUID uuid1 = UUID.randomUUID();
    UUID uuid2 = UUID.randomUUID();
    UUID uuid3 = UUID.randomUUID();
    UUID uuid4 = UUID.randomUUID();
    UUID uuid5 = UUID.randomUUID();
    UUID uuid6 = UUID.randomUUID();

    scheduler.submitTask(new TaskImpl("group1", TaskType.READ, uuid1));
    scheduler.submitTask(new TaskImpl("group2", TaskType.READ, uuid2));
    scheduler.submitTask(new TaskImpl("group3", TaskType.WRITE, uuid3));
    scheduler.submitTask(new TaskImpl("group1", TaskType.WRITE, uuid4));
    scheduler.submitTask(new TaskImpl("group2", TaskType.READ, uuid5));
    scheduler.submitTask(new TaskImpl("group3", TaskType.WRITE, uuid6));

    Thread.sleep(1000);
    assertEquals(true, scheduler.getResult(uuid1).isSuccess());
    assertEquals(true, scheduler.getResult(uuid2).isSuccess());
    assertEquals(true, scheduler.getResult(uuid3).isSuccess());
    assertEquals(true, scheduler.getResult(uuid4).isSuccess());
    assertEquals(true, scheduler.getResult(uuid5).isSuccess());
    assertEquals(true, scheduler.getResult(uuid6).isSuccess());
  }
}
