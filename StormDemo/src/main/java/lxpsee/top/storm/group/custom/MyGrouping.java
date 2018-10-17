package lxpsee.top.storm.group.custom;

import org.apache.storm.generated.GlobalStreamId;
import org.apache.storm.grouping.CustomStreamGrouping;
import org.apache.storm.task.WorkerTopologyContext;

import java.util.ArrayList;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/15 16:36.
 */
public class MyGrouping implements CustomStreamGrouping {
    private List<Integer> targetTasks;

    @Override
    public void prepare(WorkerTopologyContext context, GlobalStreamId stream, List<Integer> targetTasks) {
        this.targetTasks = targetTasks;
    }

    @Override
    public List<Integer> chooseTasks(int taskId, List<Object> list) {
        int num = targetTasks.size() / 2;
        List<Integer> subTasksId = new ArrayList<Integer>(num);

        for (int i = 0; i <= num; i++) {
            subTasksId.add(targetTasks.get(i));
        }

        return subTasksId;
    }
}
