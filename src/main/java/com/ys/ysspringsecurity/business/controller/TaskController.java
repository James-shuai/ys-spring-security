package com.ys.ysspringsecurity.business.controller;

import org.springframework.web.bind.annotation.*;

/**
 * 权限的控制器去测试
 * @author ys
 * @date 2020/4/13 14:47
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

  @GetMapping
  public String listTasks(){
    return "任务列表";
  }

  @PostMapping
  public String newTasks(){
    return "创建了一个新的任务";
  }

  @PutMapping("/{taskId}")
  public String updateTasks(@PathVariable("taskId")Integer id){
    return "更新了一下id为:"+id+"的任务";
  }

  @DeleteMapping("/{taskId}")
  public String deleteTasks(@PathVariable("taskId")Integer id){
    return "删除了id为:"+id+"的任务";
  }
}

