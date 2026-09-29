package ejercicio1.eventos;

import des.ContadoresEstadisticos;
import des.Evento;
import des.EstadoDelSistema;
import des.LibreriaDeRutinas;
import des.ListaDeEventos;
import ejercicio1.componentespropios.ContadoresEstadisticosEjercicio1;
import ejercicio1.componentespropios.LibreriaDeRutinasEjercicio1;
import ejercicio1.estadodelsistema.Ejercicio1;
import ejercicio1.estadodelsistema.Solicitud;

public class EventoArribarACola extends Evento {
    public EventoArribarACola(double tiempoDeOcurrencia) {
        super(tiempoDeOcurrencia);
    }

    @Override
    public void rutinaDeEvento(EstadoDelSistema modelo, ContadoresEstadisticos contadores, ListaDeEventos eventos,
            LibreriaDeRutinas libreria) {
        Ejercicio1 modeloActual = (Ejercicio1) modelo;
        LibreriaDeRutinasEjercicio1 libreriaActual = (LibreriaDeRutinasEjercicio1) libreria;
        // Agendar próximo arribo
        EventoArribarACola nuevoEvento = new EventoArribarACola(libreriaActual.tiempoEntreArribosSolicitudes());
        eventos.agregar(nuevoEvento);
        Solicitud solicitudParaAgregar = new Solicitud();
        solicitudParaAgregar.setTiempoArribo(this.getTiempoDeOcurrencia());
        if (modeloActual.estaServidorOcupado()) {
            modeloActual.encolarSolicitud(solicitudParaAgregar);
        } else {
            if (solicitudParaAgregar.getClase() == 1) {
                ContadoresEstadisticosEjercicio1 contadoresEj = (ContadoresEstadisticosEjercicio1) contadores;
                contadoresEj.actualizarCantClase1();
                contadoresEj.actualizarTiempoEsperaSolicitudesClase1(0.0);
            }
            modeloActual.atenderSolicitud(solicitudParaAgregar);
            double duracionDelProcesamiento = libreriaActual.tiempoDeProcesamiento();
            EventoTerminaProcesamiento nuevoEventoAdicional = new EventoTerminaProcesamiento(duracionDelProcesamiento);
            eventos.agregar(nuevoEventoAdicional);
        }
        modeloActual.mostrarEstadoEntidades();
    }
}
