package com.work.account.service;

import cn.com.do1.conductor.client.BaseWorker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author zengxc
 */
@Component
public class AccountTask extends BaseWorker {
    @Autowired
    private AccountService accountService;

    @Override
    public String getTaskDefName() {
        return "accountTask";
    }


    @Override
    public TaskResult invoke(Task task) {
        Map<String, Object> inputData = task.getInputData();
        String userId =(String) inputData.get("userId");
        Integer count =(Integer) inputData.get("count");
        TaskResult taskResult = new TaskResult(task);
        try{
            BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));
            accountService.debit(userId,orderMoney);
            taskResult.setStatus(TaskResult.Status.COMPLETED);
        }catch (Exception ex){
            taskResult.setReasonForIncompletion(ex.getMessage());
            taskResult.setStatus(TaskResult.Status.FAILED);
        }
        return taskResult;
    }
}
