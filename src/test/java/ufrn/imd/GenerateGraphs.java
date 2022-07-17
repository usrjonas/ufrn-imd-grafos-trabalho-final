package ufrn.imd;

import ufrn.imd.graph.Coordinates;
import ufrn.imd.graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class GenerateGraphs {
    public static Graph emptyGraph() {
        return new Graph();
    }

    public static Graph completeGraph(int size, List<Long> seeds) {
        Graph graph = new Graph(size);
        for (int i = 0; i < size; i++) {
            graph.addNode(getRandomCoordinate(seeds.get(i)));
        }
        graph.generateCompleteGraph();
        return graph;
    }

    private static Coordinates getRandomCoordinate(long seed) {
        Random random = new Random(seed);
        double latitude = random.nextDouble() * 180d - 90d;
        double longitude = random.nextDouble() * 360d - 180d;
        return new Coordinates(latitude, longitude);
    }

    public static List<Long> generateSeeds(int size, long seed) {
        Random random = new Random(seed);
        List<Long> seeds = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            seeds.add(random.nextLong());
        }
        return seeds;
    }
}
