package des;

import java.util.ArrayList;
import java.util.List;

/**
 * Colección de variables de estado necesarias para describir el sistema en un
 * momento particulara partir de un conjunto de entidades.
 * 
 * @Objective Colección de variables de estado necesarias para describir el
 *            sistema en un punto en el tiempo.
 * @role Extension Point
 * @xmlBinding {@code <modelo>}
 */
public abstract class EstadoDelSistema {

	private List<Entity> entidades;

	/**
	 * Constructor por defecto.
	 */
	public EstadoDelSistema() {
		this.entidades = new ArrayList<>();
	}

	/**
	 * Inicializa las variables de estado al inicio de la simulación.
	 * 
	 * @Objective Configurar el estado inicial del modelo.
	 * @simulationPhase Inicialización.
	 */
	public abstract void inicializar();

	/**
	 * Agrega una entidad al conjunto de entidades del estado del sistema.
	 * 
	 * @Objective Registrar una entidad en el estado del sistema.
	 * @param entidad Entidad a ser agregada.
	 */
	public void agregarEntidad(Entity entidad) {
		this.entidades.add(entidad);
	}

	/**
	 * Remueve una entidad del conjunto de entidades del estado del sistema.
	 * 
	 * @Objective Eliminar una entidad del estado del sistema.
	 * @param entidad Entidad a ser removida.
	 */
	public void removerEntidad(Entity entidad) {
		this.entidades.remove(entidad);
	}

	/**
	 * Obtiene la lista de entidades que componen el estado del sistema.
	 * 
	 * @Objective Recuperar el conjunto de entidades del modelo.
	 * @return Lista de entidades registradas en el sistema.
	 */
	public List<Entity> getEntidades() {
		return this.entidades;
	}

}
