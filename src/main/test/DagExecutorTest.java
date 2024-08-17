import com.ritesh.core.*;
import com.ritesh.core.dags.CustomDag1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Callable;

@Slf4j
public class DagExecutorTest {

    @Test
    public void testDagExecutor() {
        Callable<Void> t1 = () -> {
            log.info("Running callable 1");
            return null;
        };
        Callable<Void> t2 = () -> {
            log.info("Running callable 2");
            return null;
        };
        Callable<Void> t3 = () -> {
            log.info("Running callable 3");
            return null;
        };
        Callable<Void> t4 = () -> {
            log.info("Running callable 4");
            return null;
        };
        Callable<Void> t5 = () -> {
            log.info("Running callable 5");
            return null;
        };
        Callable<Void> t6 = () -> {
            log.info("Running callable 6");
            return null;
        };
        Callable<Void> t7 = () -> {
            log.info("Running callable 7");
            return null;
        };

        Callable<Void> t8 = () -> {
            log.info("Running callable 8");
            return null;
        };

        Callable<Void> postCoreLogic = () -> {
            log.info("Callable finished");
            return null;
        };

        Task task1 = new Task("task1", t1);
        task1.setAfterLogic(postCoreLogic);
        Task task2 = new Task("task2", t2);

        Task task3 = new Task("task3", t3);

        Task task4 = new Task("task4", t4);


        Task task5 = new Task("task5", t5);

        Task task6 = new Task("task6", t6);

        Task task7 = new Task("task7", t7);

        Task task8 = new Task("task8", t8);
        task8.setAfterLogic(postCoreLogic);

        task4.addDependency(task1);
        task5.addDependency(task1);

        task7.addDependency(task3);
        task6.addDependencies(Set.of(task1, task2));
        task8.addDependencies(Set.of(task4, task5));

        Dag dag = new Dag("dag", Set.of(task1, task2, task3, task4, task5, task6, task7, task8));

        DagExecutor dagExecutor = DagExecutor.getInstance();
        dagExecutor.executeDag(dag);
    }

    @Test
    public void testCustomDag1() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //new CustomDag1().run();
        DagExecutor.runDag(CustomDag1.class, new HashMap<>());
    }
}
