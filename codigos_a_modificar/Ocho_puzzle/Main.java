package codigos_a_modificar.Ocho_puzzle;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String initialState = "724506831";

        while (true) {
            System.out.println("Menú:");
            System.out.println("1. Resolver con BFS");
            System.out.println("2. Resolver con DFS");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume la nueva línea después de leer el entero

            switch (choice) {
                case 1:
                    //esta funciona
                    System.out.println("Búsqueda en Amplitud (BFS):");
                    BFS bfsSolver = new BFS();
                    bfsSolver.solve(initialState);
                    break;
                case 2:
                    //encontrar la razon por la cual se cicla y agarra estados repetidos
                    System.out.println("Búsqueda en Profundidad (DFS):");
                    DFS dfsSolver = new DFS();
                    dfsSolver.solve(initialState);
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    scanner.close();
                    return;  // Sale del bucle y termina el programa
                default:
                    System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
            }
        }
    }
}


