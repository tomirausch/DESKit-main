package ejercicio1.componentespropios;

import des.ContadoresEstadisticos;

public class ContadoresEstadisticosEjercicio1 extends ContadoresEstadisticos {
    private int cantidadSolicitudesProcesadas;
    private int cantidadSolicitudesClase1;
    private double tiempoTotalEsperaSolicitudesClase1;

    private int[] cantidadSolicitudesMas3Minutos;

    @Override
    public void inicializar() {
        this.cantidadSolicitudesProcesadas = 0;
        this.cantidadSolicitudesClase1 = 0;
        this.tiempoTotalEsperaSolicitudesClase1 = 0;
        this.cantidadSolicitudesMas3Minutos = new int[5];
    }

    public int getCantidadSolicitudesProcesadas() {
        return this.cantidadSolicitudesProcesadas;
    }

    public void actualizarCantProcesadas() {
        this.cantidadSolicitudesProcesadas++;
    }

    public void actualizarCantClase1() {
        this.cantidadSolicitudesClase1++;
    }

    public int getCantidadSolicitudesClase1() {
        return this.cantidadSolicitudesClase1;
    }

    public void actualizarTiempoEsperaSolicitudesClase1(double tiempoEspera) {
        this.tiempoTotalEsperaSolicitudesClase1 += tiempoEspera;
    }

    public double getTiempoTotalEsperaSolicitudesClase1() {
        return this.tiempoTotalEsperaSolicitudesClase1;
    }

    public void actualizarCantMas3Minutos(int clase) {
        this.cantidadSolicitudesMas3Minutos[clase]++;
    }

    public int getCantMas3Minutos(int clase) {
        return this.cantidadSolicitudesMas3Minutos[clase];
    }
}
