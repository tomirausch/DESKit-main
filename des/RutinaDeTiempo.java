package des;

/**
 * Clase responsable de ejecutar el enfoque de avance de tiempo basado en el
 * próximo evento. Determina el suceso más inminente de la lista, adelanta el
 * reloj de simulación hasta ese instante exacto y retorna el evento para su
 * posterior ejecución lógica.
 * 
 * @Objective Obtiene el próximo evento de la lista de eventos y actualiza el
 *            reloj.
 * @simulationPhase Ejecución.
 * @role Framework Core
 */
public class RutinaDeTiempo {

	/**
	 * Constructor por defecto.
	 */
	public RutinaDeTiempo() {
	}

	/**
	 * Ejecuta la rutina de tiempo: extrae el suceso más inminente del calendario,
	 * sincroniza el reloj global sumando el delta temporal correspondiente y
	 * establece
	 * el instante de ejecución del evento antes de retornarlo.
	 * 
	 * @Objective Obtiene el próximo evento de la lista de eventos, actualiza el
	 *            reloj, el tiempo que falta para que ocurran los demás eventos, y
	 *            setea el tiempo de ocurrencia del evento que se ejecutará.
	 * @simulationPhase Ejecución.
	 * @param eventos Lista de eventos
	 * @param reloj   Reloj de la simulación
	 * @return Evento más inminente
	 */
	public Evento run(ListaDeEventos eventos, RelojDeSimulacion reloj) {

		// Determinar el tipo de evento más inminente.
		Evento e = eventos.obtenerMasInminente();

		// Actualizar el reloj al tiempo de e.
		reloj.actualizar(e.getTiempoQueFaltaParaQueOcurra());
		e.setTiempoDeOcurrencia(reloj.getValor());

		return e;

	}

}
