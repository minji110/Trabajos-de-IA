package codigos_a_modificar;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

class Node {
    int[][] state;
    Node parent;
    String action;
    int row;
    int col;

    public Node(int[][] state, int row, int col, Node parent, String action) {
        this.state = state;
        this.row = row;
        this.col = col;
        this.parent = parent;
        this.action = action;
    }
}

class StackFrontier {
    Deque<Node> frontier;

    public StackFrontier() {
        this.frontier = new ArrayDeque<>();
    }

    public void add(Node node) {
        this.frontier.addLast(node);
    }

    public boolean containsState(int[][] state) {
        return this.frontier.stream().anyMatch(node -> Arrays.deepEquals(node.state, state));
    }

    public boolean isEmpty() {
        return this.frontier.isEmpty();
    }

    public Node remove() {
        if (this.isEmpty()) {
            throw new RuntimeException("Empty Frontier");
        } else {
            return this.frontier.removeLast();
        }
    }
}

class QueueFrontier extends StackFrontier {
    @Override
    public Node remove() {
        if (this.isEmpty()) {
            throw new RuntimeException("Empty Frontier");
        } else {
            return this.frontier.removeFirst();
        }
    }
}

class Puzzle {
    Node start;
    Node goal;
    List<Node> solution;
    int numExplored;

    public Puzzle(int[][] start, int startRow, int startCol, int[][] goal, int goalRow, int goalCol) {
        this.start = new Node(start, startRow, startCol, null, null);
        this.goal = new Node(goal, goalRow, goalCol, null, null);
        this.solution = new ArrayList<>();
        this.numExplored = 0;
    }

    public List<Node> neighbors(Node node) {
        List<Node> results = new ArrayList<>();
        int[][] directions = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
        String[] actions = {"up", "left", "down", "right"};

        for (int i = 0; i < directions.length; i++) {
            int newRow = node.row + directions[i][0];
            int newCol = node.col + directions[i][1];

            if (newRow >= 0 && newRow < node.state.length && newCol >= 0 && newCol < node.state[0].length) {
                int[][] newState = deepCopy(node.state);
                newState[node.row][node.col] = newState[newRow][newCol];
                newState[newRow][newCol] = 0;
                results.add(new Node(newState, newRow, newCol, node, actions[i]));
            }
        }

        return results;
    }

    public boolean isGoalState(Node node) {
        for (int i = 0; i < node.state.length; i++) {
            for (int j = 0; j < node.state[i].length; j++) {
                if (node.state[i][j] != this.goal.state[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    

    public void printSolution() {
        System.out.println("Start State:");
        printState(this.start.state);
        System.out.println("Goal State:");
        printState(this.goal.state);
        System.out.println("\nStates Explored: " + this.numExplored);
        System.out.println("Solution:");
        for (Node node : this.solution) {
            System.out.println("action: " + node.action);
            printState(node.state);
        }
        System.out.println("Goal Reached!!");
    }

    public boolean doesNotContainState(List<Node> explored, int[][] state) {
        return explored.stream().noneMatch(node -> Arrays.deepEquals(node.state, state));
    }

    public void solveBFS() {
        QueueFrontier frontier = new QueueFrontier();
        frontier.add(this.start);
        List<Node> explored = new ArrayList<>();

        while (!frontier.isEmpty()) {
            Node node = frontier.remove();
            this.numExplored++;

            if (Arrays.deepEquals(node.state, this.goal.state)) {
                while (node.parent != null) {
                    this.solution.add(0, node);
                    node = node.parent;
                }
                return;
            }

            explored.add(node);
            for (Node child : this.neighbors(node)) {
                if (!frontier.containsState(child.state) && doesNotContainState(explored, child.state)) {
                    frontier.add(child);
                }
            }
        }

        throw new RuntimeException("No solution");
    }

    public void solveDFS() {
        StackFrontier frontier = new StackFrontier();
        frontier.add(this.start);
        List<Node> explored = new ArrayList<>();
    
        while (!frontier.isEmpty()) {
            Node node = frontier.remove();
            this.numExplored++;
    
            if (isGoalState(node)) {
                while (node.parent != null) {
                    this.solution.add(0, node);
                    node = node.parent;
                }
                return;
            }
    
            explored.add(node);
            for (Node child : this.neighbors(node)) {
                if (!frontier.containsState(child.state) && doesNotContainState(explored, child.state)) {
                    frontier.add(child);
                }
            }
        }
    
        throw new RuntimeException("No solution");
    }
    

    private static int[][] deepCopy(int[][] original) {
        return Arrays.stream(original).map(int[]::clone).toArray(int[][]::new);
    }

    private static void printState(int[][] state) {
        for (int[] row : state) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }
}

public class b {
    public static void main(String[] args) {
    int[][] start = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};
        int[][] goal = {{2, 8, 1}, {0, 4, 3}, {7, 6, 5}};
        int startRow = 1;
        int startCol = 1;
        int goalRow = 1;
        int goalCol = 0;

        Puzzle p = new Puzzle(start, startRow, startCol, goal, goalRow, goalCol);
        
        //este ya jala, pero tarda un monton ;_;
        long startTimeDFS = System.currentTimeMillis();
        System.err.println("\nDFS");
        p.solveDFS();
        long endTimeDFS = System.currentTimeMillis();
        //p.printSolution();
        System.out.println("Time taken by DFS: " + (endTimeDFS - startTimeDFS) + "ms");
        System.out.println("Numbers of visited nodes: ");

        //funciona rapido
        long startTimeBFS = System.currentTimeMillis();
        p.solveBFS();
        long endTimeBFS = System.currentTimeMillis();
        System.err.println("\nBFS");
        //p.printSolution();
        System.out.println("Time taken by BFS: " + (endTimeBFS - startTimeBFS) + "ms");
    }
}