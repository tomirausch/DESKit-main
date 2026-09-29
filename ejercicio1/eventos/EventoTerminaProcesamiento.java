package ejercicio1.eventos;

import des.ContadoresEstadisticos;
import des.EstadoDelSistema;
import des.Evento;
import des.LibreriaDeRutinas;
import des.ListaDeEventos;
import ejercicio1.componentespropios.ContadoresEstadisticosEjercicio1;
import ejercicio1.componentespropios.LibreriaDeRutinasEjercicio1;
import ejercicio1.estadodelsistema.Ejercicio1;
import ejercicio1.estadodelsistema.Solicitud;

public class EventoTerminaProcesamiento extends Evento {

    public EventoTerminaProcesamiento(double tiempoDeOcurrencia) {
        super(tiempoDeOcurrencia);
    }

    @Override
    public void rutinaDeEvento(EstadoDelSistema modelo, ContadoresEstadisticos contadores, ListaDeEventos eventos,
            LibreriaDeRutinas libreria) {
        ContadoresEstadisticosEjercicio1 contadoresEjemplo = (ContadoresEstadisticosEjercicio1) contadores;
        contadoresEjemplo.actualizarCantProcesadas();
        Ejercicio1 modeloActual = (Ejercicio1) modelo;
        LibreriaDeRutinasEjercicio1 libreriaActual = (LibreriaDeRutinasEjercicio1) libreria;
        modeloActual.actualizarServidorDisponible();
        if (modeloActual.haySolicitudesEnEspera()) {
            Solicitud solicitudAProcesar = modeloActual.obtenerSolicitudPrioritaria();

            double tiempoDeEspera = this.getTiempoDeOcurrencia() - solicitudAProcesar.getTiempoArribo();

            if (tiempoDeEspera > 3.0) {
                contadoresEjemplo.actualizarCantMas3Minutos(solicitudAProcesar.getClase());
            }

            if (solicitudAProcesar.getClase() == 1) {
                contadoresEjemplo.actualizarCantClase1();
                contadoresEjemplo.actualizarTiempoEsperaSolicitudesClase1(tiempoDeEspera);
            }
            modeloActual.atenderSolicitud(solicitudAProcesar);
            double duracionDelProcesamiento = libreriaActual.tiempoDeProcesamiento();
            EventoTerminaProcesamiento nuevoEvento = new EventoTerminaProcesamiento(duracionDelProcesamiento);
            eventos.agregar(nuevoEvento);
        }
        modeloActual.mostrarEstadoEntidades();
    }
}
