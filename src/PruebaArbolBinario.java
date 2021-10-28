import java.util.Scanner;

class NodoArbol{
	private NodoArbol nodoIzquierdo;
	private int dato; //Alumno dato;
	private NodoArbol nodoDerecho;
	
	public NodoArbol(int dato) {
		this.dato = dato;
	}

	public NodoArbol getNodoIzquierdo() {
		return nodoIzquierdo;
	}

	public void setNodoIzquierdo(NodoArbol nodoIzquierdo) {
		this.nodoIzquierdo = nodoIzquierdo;
	}

	public int getDato() {
		return dato;
	}

	public void setDato(int dato) {
		this.dato = dato;
	}

	public NodoArbol getNodoDerecho() {
		return nodoDerecho;
	}

	public void setNodoDerecho(NodoArbol nodoDerecho) {
		this.nodoDerecho = nodoDerecho;
	}

	@Override
	public String toString() {
		return "Nodo [nodoIzquierdo=" + nodoIzquierdo + ", dato=" + dato + ", nodoDerecho=" + nodoDerecho + "]";
	}
}


/*
 * 1) Crear
 * 2) Igregar
 * 3) Eliminar
 * 4) Buscar
 */

class ArbolBinarioBusqueda{
	NodoArbol raiz;
	
	// 1) Crear
	public ArbolBinarioBusqueda(){
		crearArbol();
	}
	
	public void crearArbol() {
		raiz = null;
	}
	// 2) Insertar
	public void agregarNodo(int dato) {
		NodoArbol nuevoNodo = new NodoArbol(dato);
		
		if(raiz == null) { //arbol vacio
			raiz = nuevoNodo;
			System.out.println("\nSe agrego el dato");
		} else { // ya existen nodos
			//Nodos auxiliares para recorrer arbol
			NodoArbol nodoPadre;
			NodoArbol nodoActual = raiz;
			
			while(nodoActual != null) {
				nodoPadre = nodoActual;
				if(dato <= nodoActual.getDato()) { // movimiento a la Izquierda
					nodoActual = nodoActual.getNodoIzquierdo();
					if(nodoActual == null) {
						nodoPadre.setNodoIzquierdo(nuevoNodo);
						System.out.println("\nSe agrego el dato");
					}
				} else { // movimiento a la derecha
					nodoActual = nodoActual.getNodoDerecho();
					if(nodoActual == null) {
						nodoPadre.setNodoDerecho(nuevoNodo);
						System.out.println("\nSe agrego el dato");
					}
				}
			} //while
		} // else
	} // agregar nodos
	
	// R-I-D
	public void recorridoPreorden(NodoArbol raiz) {
		if(!(raiz == null)) {
			System.out.print(raiz.getDato() + " => ");
			recorridoPreorden(raiz.getNodoIzquierdo());
			recorridoPreorden(raiz.getNodoDerecho());
		}
		
	}
	
	// En Orden I-R-D
	public void recorridoOrden(NodoArbol raiz) {
		if(!(raiz == null)) {
			
			recorridoOrden(raiz.getNodoIzquierdo());
			System.out.print(raiz.getDato() + " => ");
			recorridoOrden(raiz.getNodoDerecho());
		}
		
	}
	
	// Post ORDEN I-D-R
	public void recorridoPostOrden(NodoArbol raiz) {
		if(!(raiz == null)) {
			
			recorridoPostOrden(raiz.getNodoIzquierdo());
			recorridoPostOrden(raiz.getNodoDerecho());
			System.out.print(raiz.getDato() + " => ");
		}
		
	}
	
	public boolean eliminarElemento(int dato) {
		
		if(!(raiz == null)) {
			NodoArbol anterior = raiz;
			NodoArbol aux = raiz;
			String ladoArbol = "";
			
			// Proceso de busqueda ------------------
			while(aux.getDato() != dato) {
				anterior = aux;
				if(dato <= aux.getDato()) { // Izquierda
					aux = aux.getNodoIzquierdo();
					ladoArbol = "IZQ";
				} else{ // Derecha
					aux = aux.getNodoDerecho();
					ladoArbol = "DER";
				}
				
				if (aux == null) {
					System.out.println("\nBuscado y no encontrado");
					return false;
				}
			} // while
			
			System.out.println("\n"+ dato +" Encontrado");
			
			// Proceso de eliminacion (se encontro el dato)-----
			
			// Escenario 1: EL nodo a eliminar es hoja
			if(aux.getNodoIzquierdo() == null && aux.getNodoDerecho() == null) { // verificar si es hoja
				if(aux == raiz) {
					raiz = null;
				} else if(ladoArbol.equals("IZQ")) {
					anterior.setNodoIzquierdo(null);
				} else {
					anterior.setNodoDerecho(null);
				}
					
			} else if(aux.getNodoIzquierdo() == null) {
				if(aux == raiz) {
					raiz = aux.getNodoDerecho();
				} else if(ladoArbol.equals("IZQ")) {
					anterior.setNodoIzquierdo(aux.getNodoDerecho());
				} else {
					anterior.setNodoDerecho(aux.getNodoIzquierdo());
				}	
				
			} else if(aux.getNodoDerecho() == null) {
				if(aux == raiz) {
					raiz = aux.getNodoIzquierdo();
				} else if(ladoArbol.equals("IZQ")) {
					anterior.setNodoIzquierdo(aux.getNodoIzquierdo());
				} else {
					anterior.setNodoDerecho(aux.getNodoIzquierdo());
				}
					
			} else{ // de lo contrario tiene dos hijos
				
				NodoArbol reemplazo = reemplazar(aux);
				
				if(aux== raiz) {
					raiz = reemplazo;
				} else if(ladoArbol.equals("IZQ")) {
					anterior.setNodoIzquierdo(reemplazo);
				} else {
					anterior.setNodoDerecho(reemplazo);
				}
				reemplazo.setNodoIzquierdo(aux.getNodoIzquierdo());
			}
			System.out.println("\nEliminando . . .");
			return true;	
			
		} else {
			System.out.println("\nArbol vacio");
			return false;
		}
	}// metodo eliminar
	
	public NodoArbol reemplazar(NodoArbol nodo) {
		NodoArbol reemplazarPadre = nodo;
		NodoArbol reemplazo = nodo;
		NodoArbol auxiliar = nodo.getNodoDerecho();
		
		while(auxiliar != null){
			reemplazarPadre = reemplazo;
			reemplazo = auxiliar;
			auxiliar = auxiliar.getNodoIzquierdo();
		}
		
		if(reemplazo != nodo.getNodoDerecho()) {
			reemplazarPadre.setNodoIzquierdo(reemplazo.getNodoDerecho());
			reemplazo.setNodoDerecho(nodo.getNodoDerecho());
		}
		return reemplazo;
	}
	
	public int mostrarDatoMayor() {
		if(raiz == null) {
			System.out.println("\nArbol vacio");
			return 0;
		} else {
			NodoArbol nodoActual = raiz;
			NodoArbol nodoPadre = null;
			while(nodoActual != null) {
				nodoPadre = nodoActual;
				nodoActual = nodoActual.getNodoDerecho();
			}
			System.out.println("Dato mayor: " + nodoPadre.getDato());
			return nodoPadre.getDato();
		}
	}
	
	public int mostrarDatoMenor() {
		if(raiz == null) {
			System.out.println("\nArbol vacio");
			return 0;
		} else {
			NodoArbol nodoActual = raiz;
			NodoArbol nodoPadre = null;
			while(nodoActual != null) {
				nodoPadre = nodoActual;
				nodoActual = nodoActual.getNodoIzquierdo();
			}
			
			System.out.println("Dato menor: " + nodoPadre.getDato());
			return nodoPadre.getDato();
		}
	}
	
	public boolean buscarNodo(int dato) {
		if(!(raiz == null)) {
			NodoArbol auxiliar = raiz;
			while(auxiliar.getDato()!= dato) {
				if(dato<auxiliar.getDato()) {
					auxiliar = auxiliar.getNodoIzquierdo();
				} else {
					auxiliar = auxiliar.getNodoDerecho();
				}
				
				if(auxiliar == null ) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
		
	
	
}

public class PruebaArbolBinario {
	public static void main(String[] args) {
		ArbolBinarioBusqueda abb = new ArbolBinarioBusqueda();
		Scanner entrada = new Scanner(System.in);
		int opcion = 0;
		int dato = 0;
		/*
		abb.agregarNodo(10);
		abb.agregarNodo(18);
		abb.agregarNodo(8);
		abb.agregarNodo(3);
		abb.agregarNodo(9);
		abb.agregarNodo(1);
		abb.agregarNodo(24);
		*/
		
		do {
			System.out.println("\nElije una de las siguientes opciones: ");
			System.out.println("1) Insertar nodo");
			System.out.println("2) Eliminar nodo");
			System.out.println("3) Mostrar nodos");
			System.out.println("4) Mostrar dato mayor");
			System.out.println("5) Mostrar dato menor");
			System.out.println("6) Buscar dato");
			System.out.println("7) Salir");
			System.out.println("Introduce opcion: ");
			opcion = entrada.nextInt();
			
			switch (opcion) {
			case 1:
				System.out.println("\nIntroduce dato: ");
				dato = entrada.nextInt();
				abb.agregarNodo(dato);
				break;
			case 2:
				System.out.println("\nIntroduce dato: ");
				dato = entrada.nextInt();
				System.out.println(abb.eliminarElemento(dato)?"\nSe elimino correctamente":"\nNo se elimino");
				break;
			case 3:
				System.out.println("\nElige metodo: ");
				System.out.println("1) PreOrden");
				System.out.println("2) InOrden");
				System.out.println("3) PostOrden");
				System.out.println("Introduce opcion: ");
				int op = entrada.nextInt();
				if (op == 1) {
					System.out.println();
					abb.recorridoPreorden(abb.raiz);
					System.out.println();
				} else if(op == 2) {
					System.out.println();
					abb.recorridoOrden(abb.raiz);
					System.out.println();
				} else if(op == 3) {
					System.out.println();
					abb.recorridoPostOrden(abb.raiz);
					System.out.println();
				} else {
					System.out.println("\nOpcion Incorrecta");
				}
				break;
			case 4:
				abb.mostrarDatoMayor();
				break;
			case 5:
				abb.mostrarDatoMenor();
				break;
			case 6:
				System.out.println("\nIntroduce dato: ");
				dato = entrada.nextInt();
				System.out.println("\nBuscando . . .");
				System.out.println(abb.buscarNodo(dato)?"\nSe encontro el dato":"\nNo se encontro el dato");
				break;
			case 7:
				System.out.println("\nSaliendo . . .");
				break;
			default:
				System.out.println("\nOpcion incorrecta");
				break;
			}
			
		} while( opcion != 7);
		
	}
}
