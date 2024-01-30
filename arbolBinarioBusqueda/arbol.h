#pragma once
#include "nodo.h"

using namespace std;

class arbol
{
	nodo* raiz;
	void inserta(int dato, nodo* Nodo);
	nodo* buscar(int dato, nodo* Nodo);
	void mostrarEnPreorden(nodo* Nodo);
	void mostrarEnOrden(nodo* Nodo);
	void mostrarEnPostorden(nodo* Nodo);
	void eliminar(int dato, nodo* Nodo, nodo* padre);

	public:
		arbol();
		bool estaVacio();	//retorna true si el arbol esta vacio
		void inserta(int dato);  //inserta un nodo con el dato pasado como parámetro en el arbol
		nodo* buscar(int dato); //busca y retirba el nodo que contiene el dato pasado como parametro

		void mostrarEnPreorden();	//muestran los nodos en pre-orden
		void mostrarEnOrden();		//muestra los nodos en orden
		void mostrarEnPostorden();	//muestra los nodos en post-orden
		void eliminar(int dato);	//elimina el nodo que tiene el dato pasado como parametro
};

