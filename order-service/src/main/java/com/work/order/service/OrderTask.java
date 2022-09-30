package com.work.order.service;
import cn.com.do1.conductor.client.BaseWorker;
import  com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zengxc
 */
@Component
public class OrderTask extends BaseWorker {

    @Autowired
    private OrderService orderService;

    @Override
    public String getTaskDefName() {
        return "orderTask";
    }

    @Override
    public TaskResult invoke(Task task) {
        Map<String, Object> inputData = task.getInputData();
        String userId =(String) inputData.get("userId");
        String commodityCode =(String) inputData.get("commodityCode");
        Integer count =(Integer) inputData.get("count");
        TaskResult taskResult = new TaskResult(task);
        try{
            orderService.create(userId,commodityCode,count);
            taskResult.setStatus(TaskResult.Status.COMPLETED);
        }catch (Exception ex){
            taskResult.setReasonForIncompletion(ex.getMessage());
            taskResult.setStatus(TaskResult.Status.FAILED);
        }
        return taskResult;
    }
}
