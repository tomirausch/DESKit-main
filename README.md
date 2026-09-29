# Simulador de Eventos Discretos (DES)

Este proyecto es un motor de simulación de eventos discretos escrito en Java. Está diseñado bajo una arquitectura desacoplada para permitir la modelización y simulación de distintos problemas y sistemas de colas.

La arquitectura se basa en la inyección dinámica de componentes a través de un archivo XML, utilizando la API de *Reflection* de Java para vincular e instanciar dinámicamente las clases del modelo sin necesidad de recompilar el paquete del motor.

---

## 1. Arquitectura del Motor (Paquete `des`)

El motor de simulación está contenido en el paquete `des`. **Estas clases representan el núcleo genérico y no estan hechas para ser modificadas**:

*   **`ProgramaPrincipal`**: Punto de entrada de la aplicación. Gestiona el ciclo de vida y la ejecución del bucle principal de simulación (`do-while`).
*   **`ConfiguradorXML`**: Encargado de leer `configuracion.xml` y cargar e instanciar dinámicamente las clases concretas del modelo y la condición de parada por reflexión.
*   **`RelojDeSimulacion`**: Administra la variable de tiempo virtual de la simulación ($t$).
*   **`ListaDeEventos`**: Administra la *Future Event List* (FEL) de sucesos futuros ordenados por el menor tiempo de ocurrencia.
*   **`RutinaDeTiempo`**: Sincroniza la lista de eventos con el avance del reloj simulado.
*   **`RutinaDeInicializacion`**: Configura las variables del motor y del modelo a su estado en $t = 0$.
*   **Clases Base Abstractas (Puntos de Extensión)**:
    *   `EstadoDelSistema`: Atributos y variables de estado del modelo.
    *   `ContadoresEstadisticos`: Acumuladores de métricas de rendimiento.
    *   `Evento`: Clase base para cada suceso con firma abstracta `rutinaDeEvento()`.
    *   `LibreriaDeRutinas`: Generador de variables aleatorias.
    *   `GeneradorDeReportes`: Procesamiento final e impresión de métricas.

---

## 2. Diagrama de Diseño (UML)

### A. Diagrama de Clases UML
```mermaid
classDiagram
    namespace des {
        class ProgramaPrincipal {
            -EstadoDelSistema modelo$
            -ContadoresEstadisticos contadores$
            -GeneradorDeReportes reporte$
            -LibreriaDeRutinas libreria$
            -ListaDeEventos eventos$
            -ConfiguradorXML configurador$
            +ProgramaPrincipal()
            +main(args: String[]) void$
            -crearComponentesDependientes() void$
            -terminoLaSimulacion(reloj: RelojDeSimulacion, contadores: ContadoresEstadisticos) boolean$
        }

        class ConfiguradorXML {
            -EstadoDelSistema modelo
            -ContadoresEstadisticos contadores
            -GeneradorDeReportes reporte
            -LibreriaDeRutinas libreria
            -Evento eventoInicial
            -String tipoFin
            -double valorFin
            -String metodoContadorFin
            +ConfiguradorXML()
            +cargarConfiguracion(rutaXML: String) void
            +getModelo() EstadoDelSistema
            +getContadores() ContadoresEstadisticos
            +getReporte() GeneradorDeReportes
            +getLibreria() LibreriaDeRutinas
            +getEventoInicial() Evento
            +getTipoFin() String
            +getValorFin() double
            +getMetodoContadorFin() String
        }

        class RelojDeSimulacion {
            -double valor
            +RelojDeSimulacion()
            +inicializar() void
            +actualizar(valor: double) void
            +getValor() double
        }

        class Evento {
            <<abstract>>
            -double tiempoQueFaltaParaQueOcurra
            -double tiempoDeOcurrencia
            +Evento(saltoDeTiempo: double)
            +getTiempoDeOcurrencia() double
            +getTiempoQueFaltaParaQueOcurra() double
            +setTiempoDeOcurrencia(tiempoDeReloj: double) void
            -setTiempoQueFaltaParaQueOcurra(saltoDeTiempo: double) void
            +refreshTiempo(elapsedTime: double) void
            +rutinaDeEvento(modelo: EstadoDelSistema, contadores: ContadoresEstadisticos, eventos: ListaDeEventos, libreria: LibreriaDeRutinas)* void
        }

        class ListaDeEventos {
            #List~Evento~ lista
            #Evento primerEvento
            +ListaDeEventos(primerEvento: Evento)
            +inicializar() void
            +obtenerMasInminente() Evento
            -actualizarListado(tiempoTranscurrido: double) void
            +agregar(nuevoEvento: Evento) void
        }

        class EstadoDelSistema {
            <<abstract>>
            +EstadoDelSistema()
            +inicializar()* void
        }

        class ContadoresEstadisticos {
            <<abstract>>
            +ContadoresEstadisticos()
            +inicializar()* void
        }

        class LibreriaDeRutinas {
            <<abstract>>
            +LibreriaDeRutinas()
        }

        class GeneradorDeReportes {
            <<abstract>>
            +GeneradorDeReportes()
            +run(contadores: ContadoresEstadisticos)* void
        }

        class RutinaDeInicializacion {
            +RutinaDeInicializacion()
            +run(reloj: RelojDeSimulacion, modelo: EstadoDelSistema, contadores: ContadoresEstadisticos, eventos: ListaDeEventos, libreria: LibreriaDeRutinas) void
        }

        class RutinaDeTiempo {
            +RutinaDeTiempo()
            +run(eventos: ListaDeEventos, reloj: RelojDeSimulacion) Evento
        }
    }

    %% Relaciones
    ProgramaPrincipal ..> ConfiguradorXML : Instancia y consulta
    ProgramaPrincipal ..> RutinaDeInicializacion : Ejecuta
    ProgramaPrincipal ..> RutinaDeTiempo : Ejecuta ciclo
    ProgramaPrincipal --> RelojDeSimulacion : Utiliza
    ProgramaPrincipal --> ListaDeEventos : Utiliza
    ProgramaPrincipal --> EstadoDelSistema : Ref. Polimórfica
    ProgramaPrincipal --> ContadoresEstadisticos : Ref. Polimórfica
    ProgramaPrincipal --> GeneradorDeReportes : Ref. Polimórfica
    ProgramaPrincipal --> LibreriaDeRutinas : Ref. Polimórfica

    ConfiguradorXML --> EstadoDelSistema : Instancia vía Reflection
    ConfiguradorXML --> ContadoresEstadisticos : Instancia vía Reflection
    ConfiguradorXML --> GeneradorDeReportes : Instancia vía Reflection
    ConfiguradorXML --> LibreriaDeRutinas : Instancia vía Reflection
    ConfiguradorXML --> Evento : Instancia evento inicial

    ListaDeEventos "1" o-- "*" Evento : Contiene listado activo
    ListaDeEventos --> Evento : primerEvento

    RutinaDeInicializacion ..> RelojDeSimulacion : Reinicia a 0
    RutinaDeInicializacion ..> EstadoDelSistema : Inicializa
    RutinaDeInicializacion ..> ContadoresEstadisticos : Inicializa
    RutinaDeInicializacion ..> ListaDeEventos : Inicializa

    RutinaDeTiempo ..> ListaDeEventos : Extrae evento mas inminente
    RutinaDeTiempo ..> RelojDeSimulacion : Avanza tiempo

    Evento ..> EstadoDelSistema : Modifica en rutinaDeEvento
    Evento ..> ContadoresEstadisticos : Actualiza métricas en rutinaDeEvento
    Evento ..> ListaDeEventos : Agenda nuevos eventos en rutinaDeEvento
    Evento ..> LibreriaDeRutinas : Genera muestras aleatorias en rutinaDeEvento

    GeneradorDeReportes ..> ContadoresEstadisticos : Procesa métricas finales

```

---

## 3. ¿Cómo implementar una nueva simulación?

### Paso 1: Crear los paquetes y clases concretas
1. **Crear un paquete nuevo:** Crea un paquete para tu problema, por ejemplo: `ejercicio1`.
2. **Heredar de los componentes base:** Implementa las clases concretas heredando de las abstractas del paquete `des`:
   *   `EstadoDelSistema` (ej. `ejercicio1.estadoDelSistema.Ejercicio1`)
   *   `ContadoresEstadisticos` (ej. `ejercicio1.componentesPropios.ContadoresEstadisticosEjercicio1`)
   *   `LibreriaDeRutinas` (ej. `ejercicio1.componentesPropios.LibreriaDeRutinasEjercicio1`)
   *   `GeneradorDeReportes` (ej. `ejercicio1.componentesPropios.GeneradorDeReportesEjercicio1`)
3. **Crear los Eventos:** Crea clases que hereden de `Evento` (ej. `ejercicio1.eventos.EventoArribarACola`), implementando su correspondiente lógica en el método `rutinaDeEvento()`.

### Paso 2: Configurar `configuracion.xml`
Vincula tus clases del modelo con el motor genérico configurándolas en el archivo XML de la raíz:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<simulacion>
    <!-- 1. Clases concretas del modelo -->
    <modelo>ejercicio1.estadoDelSistema.Ejercicio1</modelo>
    <contadores>ejercicio1.componentesPropios.ContadoresEstadisticosEjercicio1</contadores>
    <reporte>ejercicio1.componentesPropios.GeneradorDeReportesEjercicio1</reporte>
    <libreria>ejercicio1.componentesPropios.LibreriaDeRutinasEjercicio1</libreria>
    
    <!-- 2. Evento inicial  -->
    <eventoInicial>
        <clase>ejercicio1.eventos.EventoArribarACola</clase>
        <metodoTiempo>tiempoEntreArribosSolicitudes</metodoTiempo>
    </eventoInicial>
    
    <!-- 3. Condición de fin de simulación (tipo: tiempo o cantidad) -->
    <condicionFin>
        <tipo>tiempo</tipo>
        <valor>1000</valor>
        <!-- Dejar vacío si el tipo es tiempo. Si es de tipo cantidad, se debe colocar el nombre del método de la clase contadores que devuelve el valor que funciona como umbral para que se detenga la simulación.  -->
        <metodoContador></metodoContador> 
    </condicionFin>
</simulacion>
```

### Paso 3: Ejecutar la simulación
1. Ejecuta el método `main()` de la clase [ProgramaPrincipal.java](file:///c:/Users/Pc/Documents/UTN/Beca/simuladorJava/des/ProgramaPrincipal.java).
2. El simulador cargará el XML, instanciará los componentes dinámicamente y computará la simulación hasta cumplir con la condición de fin especificada.

---

## 4. Requisitos y Notas de Implementación
*   **Nombres y mayúsculas:** Las rutas en el XML no son sensibles a mayúsculas y minúsculas y deben coincidir exactamente con los nombres de paquete y clase en Java.
*   **Tipo de finalización:** La etiqueta de tipo de finalización no es sensible a mayúsculas y minúsculas.

---

## 5. Documentación Javadoc

El proyecto cuenta con documentación para que puedas comprender fácilmente la finalidad, rol y fase de ejecución de cada una de las clases y métodos principales en el paquete base `des`.

### Cómo generar y visualizar la documentación

Para generar la página web interactiva con la documentación (usando las etiquetas personalizadas de este simulador), abre una terminal en la carpeta principal del proyecto (donde se ubica este archivo) y ejecuta el siguiente comando:

```bash
javadoc -d javadoc -sourcepath . -subpackages des -windowtitle "Documentación DESKit" -tag Objective:a:"Objetivo:" -tag role:a:"Rol:" -tag simulationPhase:a:"Fase de Simulación:" -tag xmlBinding:a:"Etiqueta XML:"
```

Una vez que termine, verás que se creó una carpeta llamada `javadoc`. Sólo tienes que abrir el archivo `index.html` contenido en ella con tu navegador web preferido para empezar a navegar por la documentación interactiva.

---

## 6. Enlaces Útiles

*   **Ejemplo resuelto:** https://github.com/SofiaBolea/Ejemplo-DESKit
*   **Sitio web oficial:** https://github.com/SofiaBolea/Web-DESKit
