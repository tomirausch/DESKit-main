package des;

/**
 * Representa un objeto, elemento o actor individual que interactúa con el sistema
 * y transita a través de diferentes fases durante la simulación.
 * Actúa como clase base abstracta para modelar las distintas entidades
 * específicas del dominio del problema.
 * 
 * @Objective Modelar elementos discretos del sistema que poseen identidad propia
 *            y evolucionan a lo largo de un ciclo de vida.
 * @role Extension Point
 */
public abstract class Entity {

	/**
	 * Identificador único de la entidad.
	 */
	private String id;

	/**
	 * Fase o estado actual del ciclo de vida en el que se encuentra la entidad.
	 */
	private String lifecyclePhase;
	
	/**
	 * Crea una nueva entidad con un identificador específico.
	 * 
	 * @Objective Instanciar una entidad asignándole su identificador único.
	 * @param id Identificador único de la entidad.
	 */
	public Entity(String id) {
		this.id = id;
	}
	
	/**
	 * Crea una nueva entidad especificando su identificador y su fase inicial del ciclo de vida.
	 * 
	 * @Objective Instanciar una entidad asignándole su identificador único y su estado o fase inicial.
	 * @param id Identificador único de la entidad.
	 * @param lifecyclePhase Fase o estado inicial en el ciclo de vida de la entidad.
	 */
	public Entity(String id, String lifecyclePhase) {
		this.id = id;
		this.setLifecyclePhase(lifecyclePhase);
	}
	
	/**
	 * Obtiene la fase actual del ciclo de vida en la que se encuentra la entidad.
	 * 
	 * @Objective Conocer el estado o fase actual del ciclo de vida de la entidad.
	 * @return Cadena que representa la fase del ciclo de vida actual.
	 */
	public String getLifecyclePhase() {
		return lifecyclePhase;
	}

	/**
	 * Establece o actualiza la fase del ciclo de vida de la entidad.
	 * 
	 * @Objective Actualizar el avance o transición de estado de la entidad dentro de su ciclo de vida.
	 * @param lifecyclePhase Nueva fase del ciclo de vida para la entidad.
	 */
	public void setLifecyclePhase(String lifecyclePhase) {
		this.lifecyclePhase = lifecyclePhase;	
	}	
	
	/**
	 * Obtiene el identificador único de la entidad.
	 * 
	 * @Objective Recuperar la identificación de la entidad.
	 * @return Identificador único de la entidad.
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * Muestra o imprime el estado actual y los atributos relevantes de la entidad.
	 * Cada subclase concreta debe definir cómo representar e informar su estado interno.
	 * 
	 * @Objective Visualizar o reportar el estado de la entidad en un momento determinado.
	 */
	public abstract void showState();
    
}