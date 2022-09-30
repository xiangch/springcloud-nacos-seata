package com.work.account.service;

import cn.com.do1.conductor.client.BaseWorker;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zengxc
 */
@Component
public class StockTask extends BaseWorker {

    @Autowired
    private StockService stockService;

    @Override
    public String getTaskDefName() {
        return "stockTask";
    }

    @Override
    public TaskResult invoke(Task task) {
        Map<String, Object> inputData = task.getInputData();
        TaskResult taskResult = new TaskResult(task);
        try{
            String commodityCode =(String) inputData.get("commodityCode");
            Integer count =(Integer) inputData.get("count");
            stockService.deduct(commodityCode,count);
            taskResult.setStatus(TaskResult.Status.COMPLETED);
        }catch (Exception ex){
            ex.printStackTrace();
            taskResult.setReasonForIncompletion(ex.getMessage());
            taskResult.setStatus(TaskResult.Status.FAILED);
        }
        return taskResult;
    }

}
