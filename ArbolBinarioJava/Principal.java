package ArbolBinarioJava;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Arbol arbol = new Arbol();
        Scanner scanner = new Scanner(System.in);
        int opc = 0;

        while (opc != 7) {
            
            System.out.println("1. Insertar");
            System.out.println("2. Buscar");
            System.out.println("3. Mostrar en pre-orden");
            System.out.println("4. Mostrar en orden");
            System.out.println("5. Mostrar en post-orden");
            System.out.println("6. Eliminar");
            System.out.println("7. Salir");

            System.out.print("Elija una opción del menú: ");
            opc = scanner.nextInt();

            
            switch (opc) {
                case 1: {
                    System.out.print("Inserte el dato: ");
                    int dato = scanner.nextInt();
                    arbol.insertar(dato);
                    break;
                }
                case 2: {
                    if (arbol.estaVacio()) {
                        System.out.println("El árbol está vacío");
                    } else {
                        System.out.print("Inserte el dato a buscar: ");
                        int dato = scanner.nextInt();
                        Nodo nodo = arbol.buscar(dato);
                        if (nodo == null)
                            System.out.println("Ese nodo no se encuentra en el árbol");
                        else {
                            System.out.println("Nodo " + dato + " encontrado");
                            System.out.println("Hijo izquierdo: " + ((nodo.izq != null) ? nodo.izq.dato : "NULL"));
                            System.out.println("Hijo derecho: " + ((nodo.der != null) ? nodo.der.dato : "NULL"));
                        }
                    }
                    break;
                }
                case 3: {
                    if (arbol.estaVacio()) {
                        System.out.println("El árbol está vacío");
                    } else {
                        System.out.println("Recorrido en pre-orden:");
                        arbol.mostrarEnPreorden();
                    }
                    break;
                }
                case 4: {
                    if (arbol.estaVacio()) {
                        System.out.println("El árbol está vacío");
                    } else {
                        System.out.println("Recorrido en orden:");
                        arbol.mostrarEnOrden();
                    }
                    break;
                }
                case 5: {
                    if (arbol.estaVacio()) {
                        System.out.println("El árbol está vacío");
                    } else {
                        System.out.println("Recorrido en post-orden:");
                        arbol.mostrarEnPostorden();
                    }
                    break;
                }
                case 6: {
                    if (arbol.estaVacio()) {
                        System.out.println("El árbol está vacío");
                    } else {
                        System.out.print("Inserte el dato a eliminar: ");
                        int dato = scanner.nextInt();
                        arbol.eliminar(dato);
                    }
                    break;
                }
                default:
                    break;
            }
           
        }
        scanner.close();
    }
}