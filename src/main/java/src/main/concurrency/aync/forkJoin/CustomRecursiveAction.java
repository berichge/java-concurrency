package src.main.concurrency.aync.forkJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class CustomRecursiveAction extends RecursiveAction {

    private String workload = "";
    private static final int THRESHOLD = 4;

    public CustomRecursiveAction(String workload) {
        this.workload = workload;
    }

    @Override
    protected void compute() {
        if (workload.length() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubTask());
        } else {
            process(workload);
        }
    }

    private List<CustomRecursiveAction> createSubTask() {
        List<CustomRecursiveAction> subtask = new ArrayList<>();
        String partOne = workload.substring(0, workload.length()/2);
        String partTwo = workload.substring(workload.length()/2, workload.length());

        subtask.add(new CustomRecursiveAction(partOne));
        subtask.add(new CustomRecursiveAction(partTwo));

        return subtask;
    }

    private void process(String work) {
        String result = work.toUpperCase();
        System.out.println("This result - (" + result + ") - was processed by "
                + Thread.currentThread().getName());
    }
}
