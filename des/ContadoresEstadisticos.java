package des;

/**
 * 
 * Variables que almacenan información estadística referida al comportamiento
 * del sistema.
 * 
 * @Objective Su objetivo es agrupar las variables utilizadas para almacenar
 *            información estadística sobre el rendimiento del sistema.
 * 
 * @role Extension Point
 * @xmlBinding {@code <contadores>}
 */
public abstract class ContadoresEstadisticos {

	/**
	 * Constructor por defecto.
	 */
	public ContadoresEstadisticos() {
	}

	/**
	 * Método abstracto encargado de reiniciar y establecer las condiciones
	 * iniciales
	 * de todas las variables métricas del modelo antes de arrancar el motor
	 * temporal.
	 * 
	 * @Objective Poner en vacío o en 0 todas sus sumatorias y contadores al
	 *            comienzo
	 *            de la simulación.
	 * 
	 * @simulationPhase Inicialización
	 * 
	 */
	public abstract void inicializar();

}
