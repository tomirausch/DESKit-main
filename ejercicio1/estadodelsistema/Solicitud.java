package ejercicio1.estadodelsistema;

import des.Entity;

/* Solicitud que puede ser procesada por el servidor. */
public class Solicitud extends Entity {

    private static int contadorSolicitudes = 0;
    private int clase;
    private double tiempoArribo; // tiempo en el que llega al sistema

    public Solicitud() {
        this("Solicitud-" + (++contadorSolicitudes), (int) ((Math.random() * 4) + 1));
    }

    public Solicitud(String id) {
        this(id, (int) ((Math.random() * 4) + 1));
        this.tiempoArribo = 0.0;
    }

    public Solicitud(String id, int clase) {
        super(id, "ARRIBADA");
        this.clase = clase;
        this.tiempoArribo = 0.0;
    }

    public Solicitud(String id, String lifecyclePhase, int clase) {
        super(id, lifecyclePhase);
        this.clase = clase;
        this.tiempoArribo = 0.0;
    }

    public int getClase() {
        return this.clase;
    }

    public void setClase(int clase) {
        this.clase = clase;
    }

    @Override
    public void showState() {
        System.out.println("Solicitud ID: " + getId()
                + " | Clase: " + clase
                + " | Fase: " + getLifecyclePhase());
    }

    public double getTiempoArribo() {
        return this.tiempoArribo;
    }

    public void setTiempoArribo(double tiempoArribo) {
        this.tiempoArribo = tiempoArribo;
    }

}
