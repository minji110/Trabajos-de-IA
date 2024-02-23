package codigos_a_modificar;
import java.util.*;
//funciona ANCHURA
public class a {
    
    static final int N = 3; // 3x3 puzzle
    static final String goal = "123456780"; // Goal state
    
    static class State {
        String board;
        
        State(String board) {
            this.board = board;
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
                    neighbors.add(new State(String.valueOf(newBoard)));
                }
            }
            return neighbors;
        }
    }
    
    static List<State> solveBFS(String initial) {
        Queue<State> queue = new LinkedList<>();
        Map<String, State> parent = new HashMap<>();
        Set<String> visited = new HashSet<>(); // Keep track of visited states
        
        State start = new State(initial);
        queue.add(start);
        visited.add(initial);
        parent.put(initial, null);
        
        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.isGoal()) {
                List<State> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parent.get(current.board);
                }
                Collections.reverse(path);
                return path;
            }
            
            for (State neighbor : current.getNeighbors()) {
                if (!visited.contains(neighbor.board)) {
                    queue.add(neighbor);
                    visited.add(neighbor.board);
                    parent.put(neighbor.board, current);
                }
            }
        }
        
        return null;
    }
    
    public static void main(String[] args) {
        String initial = "123045678"; // Initial state
        List<State> solution = solveBFS(initial);
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
            System.out.println("No solution exists!");
        }
    }
}
