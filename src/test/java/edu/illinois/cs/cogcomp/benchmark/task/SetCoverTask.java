package edu.illinois.cs.cogcomp.benchmark.task;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Created by haowu on 8/26/16.
 */
public class SetCoverTask {

    private int numberOfNeiborhood;
    private int[][] graph;

    public SetCoverTask(int numberOfNeiborhood, int[][] graph) {
        this.numberOfNeiborhood = numberOfNeiborhood;
        this.graph = graph;
    }

    /**
     * Randomly init graph with number of node [min, max].
     *
     * @param denseFactor number from 0 to 1. 0 will produce a graph with no edge, 1 will product a
     *                    graph with
     */
    public SetCoverTask(int min, int max, double denseFactor) {
        Random rand = new Random();
        int size = min + (int) Math.floor(rand.nextDouble() * max);

        randInitGraphWithSize(size, denseFactor);
    }

    public SetCoverTask(int k, double denseFactor) {
        // Randomly init graph.
        this.numberOfNeiborhood = k;
        randInitGraphWithSize(numberOfNeiborhood, denseFactor);
    }

    private void randInitGraphWithSize(int size, double denseFactor){
        numberOfNeiborhood = size;

        Random rand = new Random();

        int[][] adjMat = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (rand.nextDouble() < denseFactor) {
                    adjMat[i][j] = 1;
                    adjMat[j][i] = 1;
                }
            }
        }
        graph = new int[size][];

        for (int i = 0; i < size; i++) {
            int finalI = i;
            int[] line = IntStream.range(0, size).filter(k -> adjMat[finalI][k] == 1).toArray();
            graph[i] = line;
        }
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < numberOfNeiborhood; i++) {
            buffer.append("" + (i+1) + " ");
            buffer.append(StringUtils.join(Arrays.stream(graph[i]).mapToObj(x -> (x + 1) + "").collect(
                Collectors.toList()), " "));
            buffer.append("\n");
        }
        return buffer.toString().trim();
    }

    public boolean checkCondition(int[] assigned){
        throw new RuntimeException("Not implemented");
    }


    public static void main(String[] args) {
        System.out.println(new SetCoverTask(9, 0.5).toString());;
    }


}
