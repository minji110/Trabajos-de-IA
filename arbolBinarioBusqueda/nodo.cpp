#include "nodo.h"

nodo::nodo(int _dato) {
	dato = _dato;
	izq = NULL;
	der = NULL;
}

int nodo::GetDatoIzq() {
	return izq == NULL ? NULL : izq->dato;
}

int nodo::GetDatoDer() {
	return der == NULL ? NULL : der->dato;
}