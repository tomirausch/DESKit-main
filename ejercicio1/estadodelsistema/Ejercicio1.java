package ejercicio1.estadodelsistema;

import des.Entity;
import des.EstadoDelSistema;

public class Ejercicio1 extends EstadoDelSistema {
	
	private ColaDeSolicitudes cola;
	private Servidor servidor;

	@Override
	public void inicializar() {
		cola = new ColaDeSolicitudes("cola");
		servidor = new Servidor("servidor",false);
		this.agregarEntidad(cola);
		this.agregarEntidad(servidor);
	}
	
	public void encolarSolicitud(Solicitud solicitudParaAgregar) {		
		System.out.println("\t\t-- El MODELO encola una solicitud de la clase " + solicitudParaAgregar.getClase() + " ya que el servidor está ocupado.");
		cola.encolarSolicitud(solicitudParaAgregar);
	}

	public boolean estaServidorOcupado() {
		return servidor.getEstaOcupado();
	}

	public void atenderSolicitud(Solicitud solicitudParaAgregar) {
		System.out.println("\t\t-- El MODELO atiende una solicitud de la clase " + solicitudParaAgregar.getClase() + ".");
		servidor.pasarAOcupado(solicitudParaAgregar);
	}

	public boolean haySolicitudesEnEspera() {
		return (cola.getCantSolicitudesEsperando()>0);
	}

	public Solicitud obtenerSolicitudPrioritaria() {
		return cola.solicitudPrioritaria();
	}

	public void actualizarServidorDisponible() {
		System.out.println("\t\t-- El MODELO deja al servidor disponible ya que no hay solicitudes en espera.");
		servidor.setEstaOcupado(false);
	}
	
    public void mostrarEstadoEntidades() {
        for (Entity entidad : getEntidades()) {
            entidad.showState();
        }
    }

}
