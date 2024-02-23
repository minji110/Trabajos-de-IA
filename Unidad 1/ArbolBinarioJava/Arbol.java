package ArbolBinarioJava;
public class Arbol {
    private Nodo raiz;

    public Arbol() {
        raiz = null;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    public void insertar(int dato) {
        if (estaVacio())
            raiz = new Nodo(dato);
        else
            insertar(dato, raiz);
    }

    private void insertar(int dato, Nodo Nodo) {
        if (dato < Nodo.getDato()) {
            if (Nodo.getIzq() == null)
                Nodo.setIzq(new Nodo(dato));
            else
                insertar(dato, Nodo.getIzq());
        } else {
            if (Nodo.getDer() == null)
                Nodo.setDer(new Nodo(dato));
            else
                insertar(dato, Nodo.getDer());
        }
    }

    public Nodo buscar(int dato) {
        return buscar(dato, raiz);
    }

    private Nodo buscar(int dato, Nodo Nodo) {
        if (Nodo == null)
            return null;

        if (dato == Nodo.getDato())
            return Nodo;

        if (dato < Nodo.getDato())
            return buscar(dato, Nodo.getIzq());
        else
            return buscar(dato, Nodo.getDer());
    }

    public void mostrarEnPreorden() {
        mostrarEnPreorden(raiz);
    }

    private void mostrarEnPreorden(Nodo Nodo) {
        if (Nodo == null)
            return;

        System.out.println(Nodo.getDato());
        mostrarEnPreorden(Nodo.getIzq());
        mostrarEnPreorden(Nodo.getDer());
    }

    public void mostrarEnOrden() {
        mostrarEnOrden(raiz);
    }

    private void mostrarEnOrden(Nodo Nodo) {
        if (Nodo == null)
            return;

        mostrarEnOrden(Nodo.getIzq());
        System.out.println(Nodo.getDato());
        mostrarEnOrden(Nodo.getDer());
    }

    public void mostrarEnPostorden() {
        mostrarEnPostorden(raiz);
    }

    private void mostrarEnPostorden(Nodo Nodo) {
        if (Nodo == null)
            return;

        mostrarEnPostorden(Nodo.getIzq());
        mostrarEnPostorden(Nodo.getDer());
        System.out.println(Nodo.getDato());
    }

    public void eliminar(int dato) {
        eliminar(dato, raiz, null);
    }

    private void eliminar(int dato, Nodo Nodo, Nodo padre) {
        if (Nodo == null) {
            System.out.println("El nodo especificado no existe");
            return;
        }

        if (dato < Nodo.getDato())
            eliminar(dato, Nodo.getIzq(), Nodo);
        else if (dato > Nodo.getDato())
            eliminar(dato, Nodo.getDer(), Nodo);

        if (Nodo.getIzq() == null && Nodo.getDer() == null) {
            if (Nodo == raiz)
                raiz = null;
            else {
                if (padre.getIzq() == Nodo)
                    padre.setIzq(null);
                else
                    padre.setDer(null);
            }
            Nodo = null;
        } else if (Nodo.getIzq() != null && Nodo.getDer() != null) {
            Nodo aux = Nodo.getDer();
            Nodo padreaux = Nodo;

            while (aux.getIzq() != null) {
                padreaux = aux;
                aux = aux.getIzq();
            }

            Nodo.setDato(aux.getDato());
            if (aux.getDer() != null) {
                padreaux.setIzq(aux.getDer());

                if (padreaux.getIzq() == aux)
                    padreaux.setIzq(null);
                else
                    padreaux.setDer(null);
            }
            aux = null;
        } else if (Nodo.getIzq() != null) {
            if (Nodo == raiz)
                raiz = Nodo.getIzq();
            else {
                if (padre.getIzq() == Nodo)
                    padre.setIzq(Nodo.getIzq());
                else
                    padre.setDer(Nodo.getIzq());
            }
            Nodo = null;
        } else if (Nodo.getDer() != null) {
            if (Nodo == raiz)
                raiz = Nodo.getDer();
            else {
                if (padre.getIzq() == Nodo)
                    padre.setIzq(Nodo.getDer());
                else
                    padre.setDer(Nodo.getDer());
            }
            Nodo = null;
        }
    }
}