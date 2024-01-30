#include "arbol.h"

arbol::arbol() {

	raiz = NULL;
}

bool arbol::estaVacio() {
	return raiz == NULL;
}

void arbol::inserta(int dato) {
	if (estaVacio())
		raiz = new nodo(dato);
	else
		inserta(dato, raiz);
}

void arbol::inserta(int dato, nodo* Nodo) {
	if (dato < Nodo->dato) {
		if (Nodo->izq == NULL)
			Nodo->izq = new nodo(dato);
		else
			inserta(dato, Nodo->izq);
	}
	else {
		if (Nodo->der == NULL)
			Nodo->der = new nodo(dato);
		else
			inserta(dato, Nodo->der);

	}

}

nodo* arbol::buscar(int dato) {
	return buscar(dato, raiz);
}

nodo* arbol::buscar(int dato, nodo* Nodo) {
	if (Nodo == NULL)
		return NULL;

	if (dato == Nodo->dato) 
		return Nodo;
	
	if (dato < Nodo->dato)
		return buscar(dato, Nodo->izq);
	else
		return buscar(dato, Nodo->der);
}

void arbol::mostrarEnPreorden() {
	mostrarEnPreorden(raiz);
}

void arbol::mostrarEnPreorden(nodo* Nodo) {
	if (Nodo == NULL)
		return;
	//mostrar el dato
	cout << Nodo->dato << endl;
	mostrarEnPreorden(Nodo->izq);
	mostrarEnPreorden(Nodo->der);
}

void arbol::mostrarEnOrden() {
	mostrarEnOrden(raiz);
}

void arbol::mostrarEnOrden(nodo* Nodo) {
	if (Nodo == NULL)
		return;
	mostrarEnOrden(Nodo->izq);
	//mostrar el dato
	cout << Nodo->dato << endl;
	mostrarEnOrden(Nodo->der);
}

void arbol::mostrarEnPostorden() {
	mostrarEnPostorden(raiz);
}

void arbol::mostrarEnPostorden(nodo* Nodo) {
	if (Nodo == NULL)
		return;
	mostrarEnPostorden(Nodo->izq);
	mostrarEnPostorden(Nodo->der);
	//mostrar el dato
	cout << Nodo->dato << endl;
}

void arbol::eliminar(int dato) {
	eliminar(dato, raiz, NULL);
}

void arbol::eliminar(int dato, nodo* Nodo, nodo* padre) {
	if (Nodo == NULL) {
		cout << "El nodo especificado no existe" << endl;
		return;
	}

	if (dato < Nodo->dato)
		return eliminar(dato, Nodo->izq, Nodo);
	else if(dato > Nodo->dato)
		return eliminar(dato, Nodo->der, Nodo);


	if (Nodo->izq == NULL && Nodo->der == NULL) {

		if (Nodo == raiz)
			raiz = NULL;
		else {
			if (padre->izq == Nodo)
				padre->izq = NULL;
			else
				padre->der = NULL;
		}
		delete(Nodo);

	}
	else if (Nodo->izq != NULL && Nodo->der == NULL) {
		nodo* aux = Nodo->der;
		nodo* padreaux = Nodo;

		while (aux->izq != NULL) {
			padreaux = aux;
			aux = aux->izq;
		}

		Nodo->dato = aux->dato;
		if (aux->der) {
			padre->izq = aux->der;

			delete(aux);
		}

	}
	else if (Nodo->izq != NULL) {

		if (Nodo == raiz) 
			raiz = Nodo->izq;
		else {
			if (padre->izq == Nodo)
				padre->izq = Nodo->izq;
			else
				padre->der = Nodo->izq;
		}

		delete(Nodo);
	}
	else if (Nodo->der != NULL) {

		if (Nodo == raiz)
			raiz = Nodo->der;
		else {
			if (padre->izq == Nodo)
				padre->izq = Nodo->der;
			else
				padre->der = Nodo->der;
		}

		delete(Nodo);
	}
}