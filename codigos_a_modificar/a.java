package Ocho_puzzle;
import java.util.*;

public class a {
    
    static final int N = 3; // 3x3 puzzle
    static final String goal = "123456780"; // Goal state
    
    static class State {
        String board;
        int depth; // Depth of the current state
        
        State(String board, int depth) {
            this.board = board;
            this.depth = depth;
        }
        
        boolean isGoal() {
            return board.equals(goal);
        }
        
        List<State> getNeighbors() {
            List<State> neighbors = new ArrayList<>();
            int[] dx = {0, 0, 1, -1};
            int[] dy = {1, -1, 0, 0};
            
            // Find position of empty tile (0)
            int zeroIndex = board.indexOf('0');
            int x = zeroIndex / N;
            int y = zeroIndex % N;
            
            for (int k = 0; k < 4; k++) {
                int nx = x + dx[k];
                int ny = y + dy[k];
                if (nx >= 0 && nx < N && ny >= 0 && ny < N) {
                    int newPos = nx * N + ny;
                    char[] newBoard = board.toCharArray();
                    newBoard[zeroIndex] = newBoard[newPos];
                    newBoard[newPos] = '0';
                    neighbors.add(new State(String.valueOf(newBoard), depth + 1));
                }
            }
            return neighbors;
        }
    }
    
    static List<State> solveDFS(String initial, int depthLimit) {
        Stack<State> stack = new Stack<>();
        Map<String, State> parent = new HashMap<>();
        
        State start = new State(initial, 0);
        stack.push(start);
        parent.put(initial, null);
        
        while (!stack.isEmpty()) {
            State current = stack.pop();
            if (current.isGoal()) {
                List<State> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parent.get(current.board);
                }
                Collections.reverse(path);
                return path;
            }
            // Check depth limit
            if (current.depth >= depthLimit) {
                continue; // Skip this state if it exceeds depth limit
            }
            
            for (State neighbor : current.getNeighbors()) {
                if (!parent.containsKey(neighbor.board)) {
                    stack.push(neighbor);
                    parent.put(neighbor.board, current);
                }
            }
        }
        
        return null;
    }
    
    public static void main(String[] args) {
        String initial = "724506831"; // Initial state
        List<State> solution = solveDFS(initial, 50); // Limit depth to 50
        if (solution != null) {
            for (State state : solution) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        System.out.print(state.board.charAt(i * N + j) + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
        } else {
            System.out.println("No solution exists within the depth limit!");
        }
    }
}

