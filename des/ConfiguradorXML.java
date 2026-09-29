package des;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.lang.reflect.Method;

/**
 * 
 * Carga e instancia dinámicamente los componentes del modelo mediante lectura
 * del xml y Reflection.
 * 
 * @Objective Instanciar componentes dinámicos a partir de la lectura de un
 *            archivo XML.
 * @role Framework Core
 */

public class ConfiguradorXML {

    /**
     * Constructor por defecto.
     */
    public ConfiguradorXML() {
    }

    private EstadoDelSistema modelo;
    private ContadoresEstadisticos contadores;
    private GeneradorDeReportes reporte;
    private LibreriaDeRutinas libreria;
    private Evento eventoInicial;
    private String tipoFin;
    private double valorFin;
    private String metodoContadorFin;

    /**
     * Lee y normaliza el archivo XML de configuración para instanciar las clases
     * del usuario.
     * 
     * @Objective Cargar las clases creadas como extensiones de las clases base para
     *            un modelo en particular, junto a los parámetros de finalización de
     *            la simulación.
     * 
     * @simulationPhase Configuración
     * 
     * @param rutaXML Ruta del archivo XML de configuración (cargada en el método
     *                crearComponentesDependientes de la clase ProgramaPrincipal).
     * 
     * @throws Exception Si ocurren errores durante la lectura o validación del XML, tales como:
     *                   - El archivo no existe o no tiene formato XML válido.
     *                   - Faltan etiquetas principales o requeridas (ej. {@code <eventoInicial>}, {@code <condicionFin>}).
     *                   - Las clases o métodos referenciados no existen (error tipográfico o no respetan mayúsculas/minúsculas).
     *                   - El tipo de condición de fin no es válido (debe ser "tiempo" o "cantidad").
     *                   - El valor de la condición de fin está vacío o no es un número válido.
     *                   - Falta el método contador cuando la condición de fin es de tipo "cantidad".
     * 
     */
    public void cargarConfiguracion(String rutaXML) throws Exception {

        // Lee el archivo xml
        File xmlFile = new File(rutaXML);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        // Extrae el texto del XML verificando la existencia de las etiquetas
        if (doc.getElementsByTagName("modelo").getLength() == 0 ||
                doc.getElementsByTagName("contadores").getLength() == 0 ||
                doc.getElementsByTagName("reporte").getLength() == 0 ||
                doc.getElementsByTagName("libreria").getLength() == 0) {
            throw new Exception(
                    "Error en la configuración: Faltan etiquetas principales en el XML (posible error de tipeo).");
        }

        String modeloClassName = doc.getElementsByTagName("modelo").item(0).getTextContent();
        String contadoresClassName = doc.getElementsByTagName("contadores").item(0).getTextContent();
        String reporteClassName = doc.getElementsByTagName("reporte").item(0).getTextContent();
        String libreriaClassName = doc.getElementsByTagName("libreria").item(0).getTextContent();

        try {
            // Crea los objetos
            this.modelo = (EstadoDelSistema) Class.forName(modeloClassName).getDeclaredConstructor().newInstance();
            this.contadores = (ContadoresEstadisticos) Class.forName(contadoresClassName).getDeclaredConstructor()
                    .newInstance();
            this.reporte = (GeneradorDeReportes) Class.forName(reporteClassName).getDeclaredConstructor().newInstance();
            this.libreria = (LibreriaDeRutinas) Class.forName(libreriaClassName).getDeclaredConstructor().newInstance();

            if (doc.getElementsByTagName("eventoInicial").getLength() == 0) {
                throw new Exception("Error en la configuración: Falta la etiqueta <eventoInicial>.");
            }
            Element eventoInicialElement = (Element) doc.getElementsByTagName("eventoInicial").item(0);

            if (eventoInicialElement.getElementsByTagName("clase").getLength() == 0 ||
                    eventoInicialElement.getElementsByTagName("metodoTiempo").getLength() == 0) {
                throw new Exception("Error en la configuración: Faltan etiquetas dentro de <eventoInicial>.");
            }
            String eventoClassName = eventoInicialElement.getElementsByTagName("clase").item(0).getTextContent();
            String metodoTiempoName = eventoInicialElement.getElementsByTagName("metodoTiempo").item(0)
                    .getTextContent();

            // Obtener el tiempo ejecutando el método en la librería mediante reflexión
            Method metodoTiempo = this.libreria.getClass().getMethod(metodoTiempoName);
            Object tiempoObj = metodoTiempo.invoke(this.libreria);

            // Castear a double (independientemente si retorna int o double)
            double tiempo = ((Number) tiempoObj).doubleValue();

            this.eventoInicial = (Evento) Class.forName(eventoClassName).getDeclaredConstructor(double.class)
                    .newInstance(tiempo);
        } catch (ClassNotFoundException e) {
            throw new Exception("Error en la configuración: No se encontró la clase '" + e.getMessage()
                    + "'. Verificá que la ruta y el nombre estén escritos correctamente respetando mayúsculas y minúsculas.");
        } catch (NoSuchMethodException e) {
            throw new Exception("Error en la configuración: No se encontró el método '" + e.getMessage()
                    + "'. Verificá que el nombre esté escrito correctamente respetando mayúsculas y minúsculas.");
        }

        // Configuración de condición de fin
        Element condicionFinElement = (Element) doc.getElementsByTagName("condicionFin").item(0);

        if (condicionFinElement != null) {
            if (condicionFinElement.getElementsByTagName("tipo").getLength() == 0 ||
                    condicionFinElement.getElementsByTagName("valor").getLength() == 0) {
                throw new Exception(
                        "Error en la configuración: Faltan etiquetas 'tipo' o 'valor' dentro de <condicionFin>.");
            }
            this.tipoFin = condicionFinElement.getElementsByTagName("tipo").item(0).getTextContent();
            if (!"tiempo".equalsIgnoreCase(this.tipoFin) && !"cantidad".equalsIgnoreCase(this.tipoFin)) {
                throw new Exception("Error en la configuración: El tipo de fin '" + this.tipoFin
                        + "' no es válido. Debe ser 'tiempo' o 'cantidad'.");
            }
            String valorStr = condicionFinElement.getElementsByTagName("valor").item(0).getTextContent();
            if (valorStr == null || valorStr.trim().isEmpty()) {
                throw new Exception(
                        "Error en la configuración: La etiqueta <valor> dentro de <condicionFin> está vacía.");
            }
            try {
                this.valorFin = Double.parseDouble(valorStr);
            } catch (NumberFormatException e) {
                throw new Exception("Error en la configuración: El contenido de la etiqueta <valor> ('" + valorStr
                        + "') no es un número válido.");
            }
            if (condicionFinElement.getElementsByTagName("metodoContador").getLength() > 0) {
                this.metodoContadorFin = condicionFinElement.getElementsByTagName("metodoContador").item(0)
                        .getTextContent();
            }

            if ("cantidad".equalsIgnoreCase(this.tipoFin)) {
                if (this.metodoContadorFin == null || this.metodoContadorFin.trim().isEmpty()) {
                    throw new Exception(
                            "Error en la configuración: Si el tipo de fin es 'cantidad', la etiqueta <metodoContador> no puede estar vacía.");
                }
                try {
                    this.contadores.getClass().getMethod(this.metodoContadorFin);
                } catch (NoSuchMethodException e) {
                    throw new Exception("Error en la configuración: No se encontró el método '" + this.metodoContadorFin
                            + "' en la clase de contadores (" + this.contadores.getClass().getSimpleName()
                            + "). Verificá que el nombre esté escrito correctamente.");
                }
            }
        }
    }

    /**
     * Retorna la instancia del estado del sistema instanciada para la ejecución
     * actual.
     *
     * @Objective Obtener el modelo cargado por el usuario desde el archivo XML.
     * @simulationPhase Configuración
     * @return Estado del sistema instanciado.
     */
    public EstadoDelSistema getModelo() {
        return modelo;
    }

    /**
     * Retorna la instancia encargada de registrar y agrupar las métricas
     * estadísticas del sistema.
     *
     * @Objective Obtener los contadores estadísticos cargados por el usuario desde
     *            el archivo XML.
     * @simulationPhase Configuración
     * @return Contadores estadísticos instanciados.
     */
    public ContadoresEstadisticos getContadores() {
        return contadores;
    }

    /**
     * Retorna la instancia responsable de procesar e imprimir los resultados de la
     * simulación.
     *
     * @Objective Obtener el generador de reportes cargado por el usuario desde el
     *            archivo XML.
     * @simulationPhase Configuración
     * @return Generador de reportes instanciado.
     */
    public GeneradorDeReportes getReporte() {
        return reporte;
    }

    /**
     * Retorna la instancia que provee los cálculos probabilísticos y tiempos
     * estocásticos.
     *
     * @Objective Obtener la librería de rutinas cargada por el usuario desde el
     *            archivo XML.
     * @simulationPhase Configuración
     * @return Librería de rutinas instanciada.
     */
    public LibreriaDeRutinas getLibreria() {
        return libreria;
    }

    /**
     * Retorna el primer suceso pre-configurado que dará inicio a la cadena de
     * eventos del simulador.
     *
     * @Objective Obtener el evento inicial del sistema.
     * @simulationPhase Configuración
     * @return Evento inicial de la simulación.
     */
    public Evento getEventoInicial() {
        return eventoInicial;
    }

    /**
     * Retorna la estrategia definida para detener el bucle de simulación ("tiempo"
     * o "cantidad").
     *
     * @Objective Obtener el tipo de condición de fin cargado por el usuario desde
     *            el archivo XML. Puede ser tiempo o cantidad.
     * @simulationPhase Configuración
     * @return Tipo de condición de fin (ej. "tiempo" o "cantidad").
     */
    public String getTipoFin() {
        return tipoFin;
    }

    /**
     * Retorna el límite numérico exacto establecido para disparar la condición de
     * parada.
     *
     * @Objective Obtener el valor de la condición de fin cargado por el usuario
     *            desde el archivo XML.
     * @simulationPhase Configuración
     * @return Valor numérico límite de la condición de fin.
     */
    public double getValorFin() {
        return valorFin;
    }

    /**
     * Retorna el nombre del método contador que se ejecutará para obtener la
     * condición de fin en el caso de que tipo fin sea "cantidad".
     *
     * @Objective Obtener el nombre del método contador que se ejecutará para
     *            obtener la condición de fin en el caso de que tipo fin sea
     *            "cantidad".
     * @simulationPhase Configuración
     * @return Nombre del método contador de fin.
     */
    public String getMetodoContadorFin() {
        return metodoContadorFin;
    }
}
