package des;

/**
 * Clase encargada de preparar el entorno de simulación antes de iniciar el
 * ciclo
 * iterativo. Su responsabilidad es establecer las condiciones iniciales del
 * modelo,
 * inicializar el reloj temporal en cero, reiniciar los contadores estadísticos
 * y
 * acondicionar la lista de eventos para el arranque.
 * 
 * @Objective Pone en cero el reloj, el modelo y los contadores al inicio de la
 *            ejecución.
 * @simulationPhase Inicialización.
 * @role Framework Core
 */
public class RutinaDeInicializacion {

	/**
	 * Constructor por defecto.
	 */
	public RutinaDeInicializacion() {
	}

	/**
	 * Invoca secuencialmente los métodos de inicialización de los componentes
	 * fundamentales del framework (reloj, modelo, contadores y lista de eventos),
	 * estableciendo las condiciones de arranque antes de entrar al
	 * bucle principal de simulación.
	 *
	 * @Objective Inicializa el sistema.
	 * @simulationPhase Inicialización.
	 * @param reloj      Reloj de la simulación
	 * @param modelo     Estado del sistema
	 * @param contadores Contadores estadísticos
	 * @param eventos    Lista de eventos
	 * @param libreria   Librería de rutinas
	 */
	public void run(RelojDeSimulacion reloj, EstadoDelSistema modelo, ContadoresEstadisticos contadores,
			ListaDeEventos eventos, LibreriaDeRutinas libreria) {

		// Setear el reloj de simulación en 0.
		reloj.inicializar();

		// Inicializar el estado del sistema.
		modelo.inicializar();

		// Inicializar los contadores estadísticos.
		contadores.inicializar();

		// Inicializar la lista de eventos.
		eventos.inicializar();

	}

}
