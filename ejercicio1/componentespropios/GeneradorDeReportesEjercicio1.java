package ejercicio1.componentespropios;

import des.ContadoresEstadisticos;
import des.GeneradorDeReportes;

public class GeneradorDeReportesEjercicio1 extends GeneradorDeReportes {
    @Override
    public void run(ContadoresEstadisticos contadores) {
        ContadoresEstadisticosEjercicio1 contadoresEjemplo = (ContadoresEstadisticosEjercicio1) contadores;
        System.out
                .println("Cantidad de solicitudes procesadas: " + contadoresEjemplo.getCantidadSolicitudesProcesadas());
        System.out.println("Cantidad de solicitudes clase 1: " + contadoresEjemplo.getCantidadSolicitudesClase1());
        System.out.println("Tiempo total de espera solicitudes clase 1: "
                + contadoresEjemplo.getTiempoTotalEsperaSolicitudesClase1());
        System.out.println("Tiempo promedio de espera solicitudes clase 1: "
                + contadoresEjemplo.getTiempoTotalEsperaSolicitudesClase1()
                        / contadoresEjemplo.getCantidadSolicitudesClase1());
        for (int i = 1; i <= 4; i++) {
            System.out.println("Cantidad de solicitudes clase " + i + " mas de 3 minutos: "
                    + contadoresEjemplo.getCantMas3Minutos(i));
        }
    }
}
