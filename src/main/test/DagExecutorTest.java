import com.ritesh.core.*;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class DagExecutorTest {

    @Test
    public void testDagExecutor() {
        Task1 task1 = new Task1();
        task1.setName("task1");
        Task2 task2 = new Task2();
        task2.setName("task2");
        Task3 task3 = new Task3();
        task3.setName("task3");
        Task4 task4 = new Task4();
        task4.setName("task4");

        Task5 task5 = new Task5();
        task5.setName("task5");
        Task6 task6 = new Task6();
        task6.setName("task6");
        Task7 task7 = new Task7();
        task7.setName("task7");
        Task8 task8 = new Task8();
        task8.setName("task8");

        task4.addDependency(task1);
        task5.addDependency(task1);

        task7.addDependency(task3);
        task6.addDependencies(Set.of(task1, task2));
        task8.addDependencies(Set.of(task4, task5));

        Dag dag = new Dag("dag", Set.of(task1, task2, task3, task4, task5, task6, task7, task8));

        DagExecutor dagExecutor = DagExecutor.getInstance();
        dagExecutor.executeDag(dag);
    }
}
