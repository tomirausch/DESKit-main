package ejercicio1.estadodelsistema;

import des.Entity;

/* Servidor en la nube. */
public class Servidor extends Entity {

	Boolean estaOcupado; /* false = libre / true = ocupado */
	Solicitud solicitudEnProcesamiento; /* Solicitud retenida en el servidor. */
	
	public Servidor(String id, boolean estado) {
		super(id);
		this.estaOcupado = estado;
		this.solicitudEnProcesamiento = null;
	}

	public boolean getEstaOcupado() { return this.estaOcupado; }

	public void pasarAOcupado(Solicitud solicitud) {
		estaOcupado = true;
		solicitudEnProcesamiento = solicitud;
	}

	public void setEstaOcupado(boolean estado) { this.estaOcupado = estado;}
	
	@Override
	public void showState() {
        System.out.println("Servidor ID: " + getId()
        + " | Fase: " + getLifecyclePhase()
        + " | Ocupado: " + estaOcupado
        + " | Solicitud en proceso: " + (solicitudEnProcesamiento != null ? 
        		solicitudEnProcesamiento.getClase() : "Ninguna"));
	}

}
