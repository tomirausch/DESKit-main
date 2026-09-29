package ejercicio1.componentespropios;

import des.LibreriaDeRutinas;
import java.util.Random;

public class LibreriaDeRutinasEjercicio1 extends LibreriaDeRutinas {

    private final Random random;

    public LibreriaDeRutinasEjercicio1() {
        super();
        this.random = new Random();
    }

    public double tiempoEntreArribosSolicitudes() {
        double u = random.nextDouble();
        return -1.91 * Math.log(1.0 - u);
    }

    public double tiempoDeProcesamiento() {
        double u = random.nextDouble();
        return 0.5 + u * (2.5 - 0.5);
    }

    public int generarClaseSolicitud() {
        double u = random.nextDouble();
        return (int) (4 * u) + 1;
    }
}
