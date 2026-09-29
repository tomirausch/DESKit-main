package des;

/**
 * Representa un suceso instantáneo capaz de alterar el
 * estado del sistema en un punto específico del tiempo simulado. Actúa como
 * plantilla obligatoria para la definición de eventos primarios (por ejemplo,
 * el arribo de una entidad o la finalización de un servicio).
 * 
 * @Objective Representar una ocurrencia instantánea en el tiempo que altera el
 *            estado del sistema. Esta clase actúa como plantilla
 *            obligando a crear subclases solo para eventos primarios (ej.
 *            arribo
 *            de clientes).
 * @role Extension Point
 * @xmlBinding El único que debe escribirse en el configurador es el evento
 *             inicial.
 */
public abstract class Evento {

	private double tiempoQueFaltaParaQueOcurra;
	private double tiempoDeOcurrencia;

	/**
	 * Instancia un nuevo evento estableciendo el tiempo remanente para su
	 * ejecución.
	 *
	 * @Objective Crea un evento con un tiempo de ocurrencia inicial.
	 * @param saltoDeTiempo Tiempo que falta para que ocurra el evento. Este valor
	 *                      debe ser generado por la clase LibreríaDeRutinas.
	 * @simulationPhase Ejecución.
	 */
	public Evento(double saltoDeTiempo) {
		super();
		this.setTiempoQueFaltaParaQueOcurra(saltoDeTiempo);
	}

	/**
	 * Retorna el instante en el que este evento fue ejecutado o está programado
	 * para ejecutarse.
	 * 
	 * @Objective Obtiene el tiempo actual de ocurrencia del evento
	 * @return Tiempo de ocurrencia del evento
	 * @simulationPhase Ejecución.
	 */
	public double getTiempoDeOcurrencia() {
		return tiempoDeOcurrencia;
	}

	/**
	 * Retorna el tiempo relativo que aún resta para que el evento alcance su punto
	 * de ocurrencia y se convierta en el suceso inminente.
	 * 
	 * @Objective Obtiene el tiempo que falta para que ocurra el evento
	 * @return Tiempo que falta para que ocurra el evento
	 * @simulationPhase Ejecución.
	 */
	public double getTiempoQueFaltaParaQueOcurra() {
		return tiempoQueFaltaParaQueOcurra;
	}

	/**
	 * Descuenta el tiempo transcurrido al tiempo remanente del
	 * evento, acercándolo progresivamente a su ejecución.
	 * 
	 * @Objective Actualiza el tiempo que falta para que ocurra el evento
	 * @param elapsedTime Tiempo transcurrido
	 * @simulationPhase Ejecución.
	 */

	public void refreshTiempo(double elapsedTime) {
		this.tiempoQueFaltaParaQueOcurra -= elapsedTime;
	}

	/**
	 * Establece el instante de ocurrencia del evento en el momento en que este pasa
	 * a ser el suceso inminente.
	 * 
	 * @Objective Setea el tiempo de ocurrencia del evento
	 * @param tiempoDeReloj Tiempo de ocurrencia del evento
	 * @simulationPhase Ejecución.
	 */
	public void setTiempoDeOcurrencia(double tiempoDeReloj) {
		this.tiempoDeOcurrencia = tiempoDeReloj;
	}

	/**
	 * Define internamente el tiempo relativo faltante al momento de instanciar el
	 * evento.
	 * 
	 * @Objective Setea el tiempo que falta para que ocurra el evento
	 * @param saltoDeTiempo Tiempo que falta para que ocurra el evento
	 * @simulationPhase Ejecución.
	 */
	private void setTiempoQueFaltaParaQueOcurra(double saltoDeTiempo) {
		this.tiempoQueFaltaParaQueOcurra = saltoDeTiempo;
	}

	/**
	 * 
	 * Método abstracto donde se encapsula la lógica del suceso. Al
	 * invocarse, procesa las transiciones de estado, actualiza contadores y
	 * programa nuevos eventos en la lista.
	 * 
	 * @Objective Rutina que actualiza el estado del sistema cuando un tipo
	 *            particular de evento tiene lugar.
	 * @simulationPhase Ejecución.
	 * @param modelo     Estado del sistema
	 * @param contadores Contadores estadísticos
	 * @param eventos    Lista de eventos
	 * @param libreria   Librería de rutinas
	 */
	public abstract void rutinaDeEvento(EstadoDelSistema modelo, ContadoresEstadisticos contadores,
			ListaDeEventos eventos, LibreriaDeRutinas libreria);

}
