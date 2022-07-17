package ufrn.imd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ufrn.imd.graph.Graph;

import java.util.*;

import static java.lang.Math.pow;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GraphTests {

    @Test
    public void analyisManyShortestSpanningSubtree() {
        int SPREAD = 10;
        int INITIAL_AMOUNT_OF_NODES = 100;
        int AMOUNT_OF_TESTS = 5;
        int ITERATIONS = 50;
        long SEED = 0;

        printInfo(SPREAD, INITIAL_AMOUNT_OF_NODES, AMOUNT_OF_TESTS, ITERATIONS, SEED);

        List<Long> seeds = GenerateGraphs.generateSeeds(ITERATIONS, SEED);

        for (int i = 0; i < ITERATIONS; i ++) {
            analysisMeanOfOneShortestSpanningSubtree(
                    INITIAL_AMOUNT_OF_NODES + (i*SPREAD),
                    AMOUNT_OF_TESTS,
                    seeds.get(i)
            );
        }
    }

    public void analysisMeanOfOneShortestSpanningSubtree(int amountOfNodes, int amountOfTests, long seed) {
        List<ObjectGaphForAnalysis> list = new ArrayList<>();
        List<Long> seeds = GenerateGraphs.generateSeeds(amountOfTests, seed);

        for (int i = 0; i < amountOfTests; i++) {
            list.add(shortestSpanningSubtree(amountOfNodes, seeds.get(i)));
        }

        printMeanObjectGraphForAnalysis(list, amountOfTests, amountOfNodes);
    }

    private ObjectGaphForAnalysis shortestSpanningSubtree(int numberOfNodes, long seed) {
        ObjectGaphForAnalysis objectGaphForAnalysis = initializeGraph(numberOfNodes, seed);

        long nanoInitialTime = System.nanoTime();
        objectGaphForAnalysis.setShortestSpanningSubtree(objectGaphForAnalysis.getCompleteGraph().shortestSpanningSubtree(true));
        long nanoFinalTime = System.nanoTime();
        objectGaphForAnalysis.setEnlapsedTimeInNanos(nanoFinalTime - nanoInitialTime);

        return objectGaphForAnalysis;
    }

    private ObjectGaphForAnalysis initializeGraph(int size) {
        Random random = new Random();
        long seed = random.nextLong();
        return initializeGraph(size, seed);
    }

    private ObjectGaphForAnalysis initializeGraph(int size, long seed) {
        List<Long> seeds = GenerateGraphs.generateSeeds(size, seed);
        Graph graph = GenerateGraphs.completeGraph(size, seeds);

        return new ObjectGaphForAnalysis(graph, seed);
    }

    private void printMeanObjectGraphForAnalysis(List<ObjectGaphForAnalysis> list, int amountOfTests, int amountOfNodes) {
        double sum = list.stream().mapToDouble(e -> (double) e.getEnlapsedTimeInNanos()).sum();

        if (amountOfTests == list.size()){
            System.out.println(amountOfNodes + "," + sum/amountOfTests);
        }
        else {
            System.out.println("error,error  <---------------");
        }

    }

    private void printInfo(int spread, int initialAmountOfNodes, int amountOfTests, int iterations, long seed) {
        System.out.println("--------- INFORMAÇÕES DO TESTE ---------");
        System.out.println("SPREAD: " + spread);
        System.out.println("INITIAL_AMOUNT_OF_NODES: " + initialAmountOfNodes);
        System.out.println("AMOUNT_OF_TESTS: " + amountOfTests);
        System.out.println("ITERATIONS: " + iterations);
        System.out.println("SEED: " + seed);
        System.out.println("----------------------------------------");
        System.out.println();
        System.out.println("---------------- MÉDIAS ----------------");
        System.out.println();
        System.out.println("amountOfNodes,mean(nano)");
    }
}
