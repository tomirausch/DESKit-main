package des;

/**
 * Componente responsable de administrar el tiempo lógico del sistema. Actúa
 * como cronómetro del motor, permitiendo inicializar, consultar y
 * avanzar la variable temporal de manera discreta ante cada nuevo evento.
 * 
 * @Objective Mantener y gestionar el tiempo virtual (o simulado) en el que
 *            transcurre el modelo de simulación RelojDeSimulacion.
 * @simulationPhase Inicialización, Ejecución, Finalización.
 * @role Framework Core
 */
public class RelojDeSimulacion {

	private double valor;

	/**
	 * Construye la instancia inicial del reloj durante la fase de configuración del
	 * framework.
	 * 
	 * @Objective Crea un reloj.
	 * @simulationPhase Configuración.
	 */
	public RelojDeSimulacion() {
		super();
	}

	/**
	 * Adelanta el reloj sumando el intervalo de tiempo que
	 * transcurrió desde el suceso anterior hasta el suceso inminente.
	 * 
	 * @Objective Actualiza el valor del reloj sumándole el tiempo transcurrido
	 *            desde el último evento.
	 * @simulationPhase Ejecución.
	 * @param valor Tiempo transcurrido
	 */
	public void actualizar(double valor) {
		this.valor += valor;
	}

	/**
	 * Retorna el tiempo actual de la simulación, sirviendo como referencia
	 * para registrar métricas o programar futuros eventos.
	 * 
	 * @Objective Obtiene el valor actual del reloj.
	 * @simulationPhase Ejecución.
	 * @return Valor actual del reloj.
	 */
	public double getValor() {
		return this.valor;
	}

	/**
	 * Pone a cero el cronómetro interno. Es invocado por la RutinaDeInicializacion
	 * justo antes de arrancar el bucle principal.
	 * 
	 * @Objective Pone en 0 el valor del reloj.
	 * @simulationPhase Inicialización.
	 */
	public void inicializar() {
		this.valor = 0;
	}

}
