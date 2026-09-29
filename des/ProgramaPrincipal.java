package des;

/**
 * Clase principal y núcleo orquestador del motor de simulación. Su
 * responsabilidad radica en inicializar el entorno mediante la carga dinámica
 * de componentes
 * (vía XML), establecer el estado inicial y gestionar el ciclo de vida continuo
 * del simulador.
 * Implementa el bucle principal algorítmico, alternando secuencialmente entre
 * la determinación del próximo suceso inminente, el avance del reloj de
 * simulación
 * y la ejecución lógica del evento correspondiente, hasta satisfacer la
 * condición de parada.
 * 
 * @Objective Controlar el flujo completo de la simulación, desde la carga
 *            de componentes y configuración del estado inicial, hasta la
 *            iteración de eventos y la generación de reportes finales.
 * @role Framework Core
 * @xmlBinding {@code <programaPrincipal>}
 */

public class ProgramaPrincipal {

	/**
	 * Constructor por defecto.
	 */
	public ProgramaPrincipal() {
	}

	// NO MODIFICAR! Creación de los componentes propios del ejemplo.
	private static EstadoDelSistema modelo;
	private static ContadoresEstadisticos contadores;
	private static GeneradorDeReportes reporte;
	private static LibreriaDeRutinas libreria;
	private static ListaDeEventos eventos;
	private static ConfiguradorXML configurador;
	private static String tipoDeFin;
	private static double valorFin;
	private static String metodoContadorFin;

	// NO MODIFICAR!
	/**
	 * Controla la ejecución. Invoca la rutina de inicialización y
	 * entra en el bucle principal de simulación: llama a la RutinaDeTiempo.java
	 * para avanzar el reloj al próximo evento, invoca la rutina del Evento.java
	 * correspondiente, y verifica la condición de fin. Al terminar, llama al
	 * GeneradorDeReportes.java.
	 *
	 * @Objective Controlar la ejecución de la simulación.
	 * @simulationPhase Inicialización, Ejecución, Finalización.
	 * @param args Argumentos de la línea de comandos
	 */
	public static void main(String[] args) {

		// Creación de los componentes propios del ejemplo a ejecutar.
		crearComponentesDependientes();

		// Creación de los componentes generales.
		RutinaDeInicializacion inicializacion = new RutinaDeInicializacion();
		RutinaDeTiempo manejoDeTiempo = new RutinaDeTiempo();
		RelojDeSimulacion reloj = new RelojDeSimulacion();

		System.out.println("------------------------------------------------------");
		System.out.println("***INICIALIZACION");
		System.out.println("------------------------------------------------------");

		// Flujo de control
		inicializacion.run(reloj, modelo, contadores, eventos, libreria);

		do {

			System.out.println("------------------------------------------------------");
			System.out.println("***PROGRAMA PRINCIPAL *** t=" + reloj.getValor());
			System.out.println("------------------------------------------------------");

			// Invocar a la rutina de tiempo.
			Evento eventoPorEjecutar = manejoDeTiempo.run(eventos, reloj);

			System.out.println("\t\t-- El SIMULADOR determina que el EVENTO MAS INMINENTE se dará en "
					+ eventoPorEjecutar.getTiempoQueFaltaParaQueOcurra() + " unidades de tiempo.");
			System.out.println("\t\t-- El SIMULADOR actualiza el RELOJ para ejecutar el EVENTO MAS INMINENTE del tipo "
					+ eventoPorEjecutar.getClass().getSimpleName() + ".");

			// Invocar a la rutina de evento.
			eventoPorEjecutar.rutinaDeEvento(modelo, contadores, eventos, libreria);

		} while (!terminoLaSimulacion(reloj, contadores));

		reporte.run(contadores);

	}

	/**
	 * Busca los componentes propios del modelo del usuario.
	 * 
	 * @Objective Crea los componentes propios del modelo del usuario para que el
	 *            simulador los utilice.
	 * @simulationPhase Configuración.
	 */
	private static void crearComponentesDependientes() {
		try {
			configurador = new ConfiguradorXML();
			configurador.cargarConfiguracion("configuracion.xml");

			modelo = configurador.getModelo();
			contadores = configurador.getContadores();
			reporte = configurador.getReporte();
			libreria = configurador.getLibreria();

			tipoDeFin = configurador.getTipoFin();
			valorFin = configurador.getValorFin();
			if ("cantidad".equalsIgnoreCase(tipoDeFin)) {
				try {
					metodoContadorFin = configurador.getMetodoContadorFin();
				} catch (Exception e) {
					System.err.println("Error al obtener el método del contador de fin de simulación desde XML:");
					e.printStackTrace();
					System.exit(1);
				}
			}
			Evento primerEvento = configurador.getEventoInicial();
			eventos = new ListaDeEventos(primerEvento);
		} catch (Exception e) {
			System.err.println("Error al cargar la configuración desde XML:");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Determina si la simulación debe finalizar basándose en el tipo de condición
	 * de parada configurada (tiempo transcurrido o cantidad de algún evento).
	 * 
	 * @Objective Verifica si la simulación ha terminado.
	 * @simulationPhase Finalización.
	 * @param reloj      Reloj de la simulación
	 * @param contadores Contadores estadísticos
	 * @return boolean Indica si la simulación ha terminado.
	 */
	private static boolean terminoLaSimulacion(RelojDeSimulacion reloj, ContadoresEstadisticos contadores) {

		if ("tiempo".equalsIgnoreCase(tipoDeFin)) {
			return reloj.getValor() >= valorFin;
		} else if ("cantidad".equalsIgnoreCase(tipoDeFin)) {
			try {
				java.lang.reflect.Method m = contadores.getClass().getMethod(metodoContadorFin);
				Object resultado = m.invoke(contadores);
				double cantidad = ((Number) resultado).doubleValue();
				return cantidad >= valorFin;
			} catch (Exception e) {
				System.err.println("Error al evaluar fin de simulacion por cantidad:");
				e.printStackTrace();
				System.exit(1);
			}
		}
		return false;
	}

}
