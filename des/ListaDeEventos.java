package des;

import java.util.LinkedList;
import java.util.List;

/**
 * Estructura de datos que administra el "calendario" de sucesos futuros
 * del
 * modelo. Se encarga de almacenar, gestionar y proveer los eventos programados,
 * garantizando que el motor de simulación siempre reciba el suceso más
 * inminente.
 * 
 * @Objective Su objetivo es ordenar los eventos según su tiempo de ocurrencia.
 * 
 * @role Framework Core
 */

public class ListaDeEventos {

	/**
	 * Lista interna que almacena los eventos programados.
	 */
	protected List<Evento> lista;
	
	/**
	 * Evento inicial de la simulación.
	 *
	 * @xmlBinding {@code <eventoInicial>}
	 */
	protected Evento primerEvento;

	/**
	 * 
	 * Almacena el evento inicial (leído desde el XML) antes de que la lista sea
	 * creada en memoria.
	 * 
	 * 
	 * @param primerEvento Evento inicial con el que arrancará la simulación.
	 * @Objective Cargar el primer evento de la lista. Aun no se crea la lista
	 *            de eventos.
	 * 
	 * @simulationPhase Inicialización
	 * 
	 */
	public ListaDeEventos(Evento primerEvento) {
		this.primerEvento = primerEvento;
	}

	/**
	 * 
	 * Agrega a la lista el primer evento (cargado con la función del constructor).
	 * 
	 * @Objective Inicializar la lista de eventos, creando la lista y agregando el
	 *            primer evento (cargado con la función del constructor).
	 *
	 * @simulationPhase Inicialización
	 * 
	 */
	public void inicializar() {
		lista = new LinkedList<Evento>();
		agregar(primerEvento);
	}

	/**
	 * 
	 * Recorre la cola, identifica el evento más próximo a ocurrir, lo
	 * remueve de la lista, actualiza el tiempo de los eventos restantes y lo
	 * devuelve para su ejecución.
	 * 
	 * @Objective Obtiene el evento mas inminente de la lista de eventos.
	 *
	 * @simulationPhase Ejecución.
	 * @return Evento. Retorna el próximo evento a ejecutar.
	 */
	public Evento obtenerMasInminente() {
		if (!lista.isEmpty()) {
			double menor = lista.get(0).getTiempoQueFaltaParaQueOcurra();
			int subindiceMenor = 0;
			Evento masInminente = lista.get(0);

			for (int i = 1; i < lista.size(); i++) {
				if (lista.get(i).getTiempoQueFaltaParaQueOcurra() < menor) {
					subindiceMenor = i;
					menor = lista.get(i).getTiempoQueFaltaParaQueOcurra();
					masInminente = lista.get(i);
				}
			}
			lista.remove(subindiceMenor);
			this.actualizarListado(masInminente.getTiempoQueFaltaParaQueOcurra());
			return masInminente;
		} else {
			System.out.println("ERROR: Busqueda de evento mas inminente en lista de eventos vacia.");
			return null;
		}
	}

	/**
	 * Método interno que resta el salto temporal (del evento recién extraído) al
	 * tiempo relativo de todos los eventos que aún aguardan en la lista.
	 * 
	 * @Objective Actualiza el tiempo restante para que ocurran los eventos en la
	 *            lista de eventos.
	 * @param tiempoTranscurrido
	 */
	private void actualizarListado(double tiempoTranscurrido) {
		if (!lista.isEmpty()) {
			for (int i = 0; i < lista.size(); i++)
				lista.get(i).refreshTiempo(tiempoTranscurrido);
		}
	}

	/**
	 * Agrega un nuevo evento a la lista de eventos y registra la acción en la
	 * consola para facilitar la traza de ejecución.
	 * 
	 * @Objective Agregar un nuevo evento a la lista de eventos.
	 * @simulationPhase Ejecución.
	 * @param nuevoEvento El evento a agregar a la lista.
	 */
	public void agregar(Evento nuevoEvento) {
		System.out.println("\t\t-- El MODELO pide al SIMULADOR agregar un EVENTO a la LISTA DE EVENTOS "
				+ nuevoEvento.getClass().getSimpleName() + " el cual tendrá lugar en "
				+ nuevoEvento.getTiempoQueFaltaParaQueOcurra() + " unidades de tiempo.");
		lista.add(nuevoEvento);
	}

}
