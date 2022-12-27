package com.example.taskscheduler.impl;

import com.example.taskscheduler.api.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true,fluent = true)
public class ResultImpl implements Result {
  private  boolean success;
  private  Object value;

  @Override
  public boolean isSuccess() {
    return success;
  }

  @Override
  public Object getValue() {
    return value;
  }
}