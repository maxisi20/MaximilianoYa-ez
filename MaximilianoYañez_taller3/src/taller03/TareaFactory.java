package taller03;

/**
 * Fábrica de objetos para la creación de instancias de {@link Tarea}.
 * <p>
 * Esta clase implementa el patrón Factory Method, permitiendo crear diferentes
 * tipos de tareas (Bug, Feature, Documentación) a partir de los parámetros recibidos.
 * La selección del tipo se realiza mediante una normalización del campo
 * <code>tipo</code> para evitar inconsistencias comunes en los datos de entrada.
 */
public class TareaFactory {

    /**
     * Crea una instancia concreta de {@link Tarea} según el tipo especificado.
     * <p>
     * Los tipos reconocidos son:
     * <ul>
     *   <li><b>bug</b> → crea un objeto {@link Bug}</li>
     *   <li><b>feature</b> → crea un objeto {@link Feature}</li>
     *   <li><b>documentación</b>, <b>documentacion</b>, <b>document</b> → crea un objeto {@link Documentacion}</li>
     * </ul>
     * Si el tipo no coincide con ninguno de los anteriores, se retorna una instancia de {@link Feature} por defecto.
     *
     * @param id           Identificador único de la tarea.
     * @param tipo         Tipo de tarea según el archivo de entrada.
     * @param descripcion  Descripción de la tarea.
     * @param estado       Estado actual de la tarea.
     * @param responsable  Responsable asignado.
     * @param complejidad  Nivel de complejidad.
     * @param fecha        Fecha asociada en formato yyyy-MM-dd.
     * @param proyectoId   Identificador del proyecto al que pertenece.
     *
     * @return Un objeto de una subclase de {@link Tarea}.
     */
    public static Tarea createTarea(String id, String tipo, String descripcion, String estado,
                                    String responsable, String complejidad, String fecha, String proyectoId) {

        // Normalizar tipo para evitar errores por mayúsculas, espacios o tildes faltantes
        String t = tipo == null ? "" : tipo.trim().toLowerCase();

        switch (t) {
            case "bug":
                return new Bug(id, tipo, descripcion, estado, responsable, complejidad, fecha, proyectoId);

            case "feature":
                return new Feature(id, tipo, descripcion, estado, responsable, complejidad, fecha, proyectoId);

            case "documentación":
            case "documentacion":
            case "document":
                return new Documentacion(id, tipo, descripcion, estado, responsable, complejidad, fecha, proyectoId);

            default:
                // Tipo no reconocido → se asigna Feature por defecto
                return new Feature(id, tipo, descripcion, estado, responsable, complejidad, fecha, proyectoId);
        }
    }
}