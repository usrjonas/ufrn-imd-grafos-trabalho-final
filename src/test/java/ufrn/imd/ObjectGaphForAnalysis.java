package ufrn.imd;

import lombok.Data;
import ufrn.imd.graph.Graph;

@Data
public class ObjectGaphForAnalysis {
    public Graph completeGraph;
    public Graph shortestSpanningSubtree;
    public Long seed;
    public Long enlapsedTimeInNanos;

    public ObjectGaphForAnalysis(Graph completeGraph, Long seed) {
        this.seed = seed;
        this.completeGraph = completeGraph;

        this.shortestSpanningSubtree = null;
        this.enlapsedTimeInNanos = null;
    }

    @Override
    public String toString() {
        return  "seed: " + seed + "\n" +
                "size: " + completeGraph.getNodes().size() + "\n" +
                "completeGraph: " + completeGraph + "\n" +
                "shortestSpanningSubtree: " + shortestSpanningSubtree + "\n" +
                "enlapsedTimeInNanos: " + enlapsedTimeInNanos + "\n";
    }
}
