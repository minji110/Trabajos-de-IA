// arbolBinarioBusqueda.cpp : Este archivo contiene la función "main". La ejecución del programa comienza y termina ahí.
//

#include <iostream>
#include "arbol.h"

int main()
{
    arbol* Arbol = new arbol();
    int opc = 0;

    while (opc != 7) {

        system("cls");
        cout << "1. Insertar" << endl;
        cout << "2. Buscar" << endl;
        cout << "3. Mostrar en pre-orden" << endl;
        cout << "4. Mostrar en orden" << endl;
        cout << "5. Mostrar en post-orden" << endl;
        cout << "6. Eliminar" << endl;
        cout << "7. Salir" << endl;

        cout << "Elija una opcion del menu:";
        cin >> opc;

        system("cls");
        switch (opc)
        {
            //caso 1
        case 1: {
            int dato = 0;
            cout << "inserte el dato:";
            cin >> dato;

            Arbol->inserta(dato);

            break;
        }
        //caso 2
        case 2: {
            if (Arbol->estaVacio()) {
                cout << "El arbol esta vacio" << endl;
            }
            else {
                int dato = 0;
                cout << "inserte el dato a buscar:";
                cin >> dato;

                nodo* Nodo = Arbol->buscar(dato);
                if (Nodo == NULL)
                    cout << "Ese nodo no se encuentra en el arbol" << endl;
                else
                {
                    cout << "Nodo " << dato << " encontrado" << endl;
                    cout << "Hijo izquierdo:" << Nodo->GetDatoIzq() << endl;
                    cout << "Hijo derecho:" << Nodo->GetDatoDer() << endl;
                }
            }

            break;
        }
        //caso 3
        case 3: {
            if (Arbol->estaVacio()) {
                cout << "El arbol esta vacio" << endl;
            }
            else {
                Arbol->mostrarEnPreorden();
            }
            break;
        }
        //caso 4
        case 4: {
            if (Arbol->estaVacio()) {
                cout << "El arbol esta vacio" << endl;
            }
            else {
                Arbol->mostrarEnOrden();
            }
            break;
        
        }
        //caso 5
        case 5: {
            if (Arbol->estaVacio()) {
                cout << "El arbol esta vacio" << endl;
            }
            else {
                Arbol->mostrarEnPostorden();
            }
            break;
        }
        //caso 6
        case 6: {
            if (Arbol->estaVacio()) {
                cout << "El arbol esta vacio" << endl;
            }
            else {
                int dato = 0;
                cout << "inserte el dato a eliminar:";
                cin >> dato;

                Arbol->eliminar(dato);
            }
            break;
        }
        //fin del case
        }
        cin.get();
        cin.get();
    }
}

