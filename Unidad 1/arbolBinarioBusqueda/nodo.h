#pragma once
#include <iostream>

class nodo
{
	int dato;
	//nodo* haciendo referencia a un puntero (estran en privado)
	nodo* izq;
	nodo* der;

	//declarar clase amiga para poder acceder a los atributos
	friend class arbol;

public:
	nodo(int _dato);
	int GetDatoIzq();
	int GetDatoDer();

};

