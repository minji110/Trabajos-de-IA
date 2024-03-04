package Unidad_2.Ocho_puzzle2;

import java.util.*;

class Node {
    int[][] state;
    Node parent;
    String action;
    int row;
    int col;
    int priority; // Para A*

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

class AStarFrontier extends PriorityQueue<Node> {
    public AStarFrontier(Comparator<? super Node> comparator) {
        super(comparator);
    }

    public boolean add(Node node) {
        return super.add(node);
    }
    

    public boolean containsState(int[][] state) {
        for (Node node : this) {
            if (Arrays.deepEquals(node.state, state)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public Node remove() {
        return super.poll();
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
        this.numExplored = 0;

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
        this.numExplored = 0;

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

    public void solveAStar() {
        AStarFrontier frontier = new AStarFrontier(Comparator.comparingInt(node -> node.priority));
        frontier.add(this.start);
        Map<Node, Integer> costSoFar = new HashMap<>();
        costSoFar.put(this.start, 0);
        this.numExplored = 0;

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

            for (Node child : this.neighbors(node)) {
                int newCost = costSoFar.get(node) + 1;
                if (!costSoFar.containsKey(child) || newCost < costSoFar.get(child)) {
                    costSoFar.put(child, newCost);
                    int priority = newCost + calculateHeuristic(child);
                    child.priority = priority;
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

    // Función de heurística
    private static int calculateHeuristic(Node node) {
        // Implementa aquí tu función de heurística
        // Por ejemplo, la distancia Manhattan
        int h = 0;
        for (int i = 0; i < node.state.length; i++) {
            for (int j = 0; j < node.state[i].length; j++) {
                int value = node.state[i][j];
                if (value != 0) {
                    int goalRow = (value - 1) / node.state.length;
                    int goalCol = (value - 1) % node.state.length;
                    h += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }
        return h;
    }
}

public class Main {
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
        p.printSolution();
        System.out.println("Time taken by DFS: " + (endTimeDFS - startTimeDFS) + "ms");

        
        //funciona rapido
        long startTimeBFS = System.currentTimeMillis();
        p.solveBFS();
        long endTimeBFS = System.currentTimeMillis();
        System.err.println("\nBFS");
        p.printSolution();
        System.out.println("Time taken by BFS: " + (endTimeBFS - startTimeBFS) + "ms");

        // A*
        long startTimeAStar = System.currentTimeMillis();
        System.err.println("\nA*");
        p.solveAStar();
        long endTimeAStar = System.currentTimeMillis();
        p.printSolution();
        System.out.println("Time taken by A*: " + (endTimeAStar - startTimeAStar) + "ms");
    }
}
