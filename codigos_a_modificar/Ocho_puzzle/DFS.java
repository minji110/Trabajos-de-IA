package codigos_a_modificar.Ocho_puzzle;
import java.util.*;

public class DFS {

    public static Stack<String> agenda = new Stack<>();
    public static Set<String> visitedStates = new HashSet<>();
    public static Map<String, String> stateHistory = new HashMap<>();

    public static void solve(String initialState) {
        add(initialState, null);

        while (!agenda.isEmpty()) {
            String currentState = agenda.pop();
            if (visitedStates.contains(currentState)) {
                continue;
            }
            visitedStates.add(currentState);

            if (currentState.equals("01234567")) {
                printSolution(currentState);
                return; // Exit the loop if the goal state is reached
            }

            // Explore next states
            up(currentState);
            down(currentState);
            left(currentState);
            right(currentState);
        }

        System.out.println("No solution exists.");
    }

    public static void add(String newState, String oldState) {
        agenda.add(newState);
        stateHistory.put(newState, oldState);
    }

    public static void addIfNotVisited(String newState, String oldState) {
        if (!visitedStates.contains(newState)) {
            agenda.add(newState);
            stateHistory.put(newState, oldState);
            visitedStates.add(newState); // Mark the state as visited
        }
    }
    public static void up(String currentState) {
        int a = currentState.indexOf("0");
        if (a > 2) {
            String nextState = currentState.substring(0, a - 3) + currentState.charAt(a) + currentState.substring(a - 2, a) + "0" + currentState.substring(a + 1);
            addIfNotVisited(nextState, currentState);
        }
    }
    
    public static void down(String currentState) {
        int a = currentState.indexOf("0");
        if (a < 6) {
            String nextState = currentState.substring(0, a) + currentState.substring(a + 3, a + 4) + currentState.substring(a + 1, a + 3) + "0" + currentState.substring(a + 4);
            addIfNotVisited(nextState, currentState);
        }
    }
    
    public static void left(String currentState) {
        int a = currentState.indexOf("0");
        if (a != 0 && a != 3 && a != 6) {
            String nextState = currentState.substring(0, a - 1) + "0" + currentState.charAt(a - 1) + currentState.substring(a + 1);
            addIfNotVisited(nextState, currentState);
        }
    }
    
    public static void right(String currentState) {
        int a = currentState.indexOf("0");
        if (a != 2 && a != 5 && a != 8) {
            String nextState = currentState.substring(0, a) + currentState.charAt(a + 1) + "0" + currentState.substring(a + 2);
            addIfNotVisited(nextState, currentState);
        }
    }
    
    

    public static void printSolution(String goalState) {
        System.out.println("Solution found:");
        String traceState = goalState;
        while (traceState != null) {
            System.out.println(traceState);
            traceState = stateHistory.get(traceState);
        }
    }

    /*public static void main(String[] args) {
        solve("120345678");
    }*/
}


