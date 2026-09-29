package des;

/**
 * 
 * Calcula las estimaciones de las medidas de performance a partir de los
 * contadores estadísticos.
 * 
 * @Objective Su objetivo es calcular las estimaciones de las medidas de
 *            performance (a partir de los Contadores Estadísticos).
 * 
 * @role Extension Point
 * @xmlBinding {@code <reporte>}
 */
public abstract class GeneradorDeReportes {

	/**
	 * Constructor por defecto.
	 */
	public GeneradorDeReportes() {
	}

	/**
	 * Calcula las estimaciones de las medidas de performance.
	 * 
	 * @Objective Calcular las estimaciones de las medidas de performance
	 * 
	 * @simulationPhase Finalización
	 * 
	 * @param contadores objeto de la clase ContadoresEstadisticos que contiene las
	 *                   estadísticas del sistema
	 * 
	 */
	public abstract void run(ContadoresEstadisticos contadores);

}
