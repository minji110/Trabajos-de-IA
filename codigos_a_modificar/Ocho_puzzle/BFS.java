package codigos_a_modificar.Ocho_puzzle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BFS {

    public static Queue<String> agenda = new LinkedList<>(); // Utilizamos una cola para el BFS
    public static Map<String, Integer> stateDepth = new HashMap<>(); // HashMap para ignorar nodos repetidos
    public static Map<String, String> stateHistory = new HashMap<>(); // Relaciona cada posición con su predecesor

    public static void solve(String initialState) {
        add(initialState, null);

        while (!agenda.isEmpty()) {
            String currentState = agenda.remove();
            up(currentState);
            down(currentState);
            left(currentState);
            right(currentState);
        }

        System.out.println("La solución no existe.");
    }

    // Método para agregar un nuevo estado a la cola y al mapa
    public static void add(String newState, String oldState) {
        if (!stateDepth.containsKey(newState)) {
            int newValue = oldState == null ? 0 : stateDepth.get(oldState) + 1;
            stateDepth.put(newState, newValue);
            agenda.add(newState);
            stateHistory.put(newState, oldState);
        }
    }

    // Métodos para mover el espacio en blanco en las cuatro direcciones posibles
    public static void up(String currentState) {
        int a = currentState.indexOf("0");
        if (a > 2) {
            String nextState = currentState.substring(0, a - 3) + "0" + currentState.substring(a - 2, a) + currentState.charAt(a - 3) + currentState.substring(a + 1);
            checkCompletion(currentState, nextState);
        }
    }

    public static void down(String currentState) {
        int a = currentState.indexOf("0");
        if (a < 6) {
            String nextState = currentState.substring(0, a) + currentState.substring(a + 3, a + 4) + currentState.substring(a + 1, a + 3) + "0" + currentState.substring(a + 4);
            checkCompletion(currentState, nextState);
        }
    }

    public static void left(String currentState) {
        int a = currentState.indexOf("0");
        if (a != 0 && a != 3 && a != 6) {
            String nextState = currentState.substring(0, a - 1) + "0" + currentState.charAt(a - 1) + currentState.substring(a + 1);
            checkCompletion(currentState, nextState);
        }
    }

    public static void right(String currentState) {
        int a = currentState.indexOf("0");
        if (a != 2 && a != 5 && a != 8) {
            String nextState = currentState.substring(0, a) + currentState.charAt(a + 1) + "0" + currentState.substring(a + 2);
            checkCompletion(currentState, nextState);
        }
    }

    // Método para verificar si el estado objetivo ha sido alcanzado
    public static void checkCompletion(String oldState, String newState) {
        add(newState, oldState);
        if (newState.equals("876543210")) {
            System.out.println("La solución existe en el nivel " + stateDepth.get(newState) + " del árbol.");
            String traceState = newState;
            while (traceState != null) {
                System.out.println(traceState + " en el nivel " + stateDepth.get(traceState));
                traceState = stateHistory.get(traceState);
            }
            System.exit(0);
        }
    }

    /*public static void main(String[] args) {
        solve("120345678");
    }*/
}
