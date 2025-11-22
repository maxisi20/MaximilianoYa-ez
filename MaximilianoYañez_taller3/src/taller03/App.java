// nombre: Maximiliano Hernan Yañez Castillo Rut: 216689666 carrera: ITI
package taller03;

/**
 * Clase principal del programa.
 * <p>
 * Se encarga de inicializar el sistema, cargar los datos necesarios
 * (usuarios, proyectos y tareas) y luego mostrar el menú principal
 * donde el usuario puede interactuar con las funcionalidades del sistema.
 */
public class App {

    /**
     * Método principal que inicia la ejecución del programa.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        Sistema s = Sistema.getInstance();

        // Carga inicial de datos
        s.cargarUsuarios();
        s.cargarProyectos();
        s.cargarTareas();

        // Inicio de la interacción del sistema
        s.menuPrincipal();
    }
}