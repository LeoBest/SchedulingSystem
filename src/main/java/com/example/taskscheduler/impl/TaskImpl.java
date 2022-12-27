package com.example.taskscheduler.impl;


import com.example.taskscheduler.api.Task;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskImpl implements Task {

  private String groupId;
  private TaskType type;
  private UUID uuid;

  @Override
  public ResultImpl execute() {
    // Do something
    return new ResultImpl(true, null);
  }
}

