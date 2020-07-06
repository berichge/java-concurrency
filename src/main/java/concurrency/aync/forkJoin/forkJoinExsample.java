package concurrency.aync.forkJoin;

import java.util.concurrent.ForkJoinPool;

public class forkJoinExsample {

    public static void main(String[] args) {
        String inputStr = "lanlandetiankong";
        ForkJoinPool fp = new ForkJoinPool(2);
        CustomRecursiveAction action = new CustomRecursiveAction(inputStr);
        fp.execute(action);
        action.join();
//        fp.invoke(new CustomRecursiveAction(inputStr));
    }
}
