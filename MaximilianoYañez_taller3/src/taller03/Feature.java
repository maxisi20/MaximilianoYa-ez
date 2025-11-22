package taller03;

/**
 * Representa una tarea de tipo Feature dentro del sistema.
 * 
 * <p>Una Feature es una implementación concreta de {@link Tarea} que
 * participa en el patrón Visitor. Esta clase no agrega nuevos atributos,
 * pero permite diferenciar el comportamiento cuando un {@link Visitor}
 * la procesa.</p>
 *
 * <p>Los atributos principales (id, tipo, descripción, estado,
 * responsable, complejidad, fecha y proyectoId) son manejados por la
 * clase base {@link Tarea}.</p>
 */
public class Feature extends Tarea {

    /**
     * Crea una nueva instancia de Feature.
     *
     * @param id           identificador único de la tarea
     * @param tipo         tipo de tarea (en este caso, se espera "Feature")
     * @param descripcion  descripción de la tarea
     * @param estado       estado actual de la tarea (por ejemplo: "Pendiente", "En progreso", "Completada")
     * @param responsable  persona asignada a la tarea
     * @param complejidad  nivel de complejidad (por ejemplo: "Alta", "Media", "Baja")
     * @param fecha        fecha asociada a la tarea (en formato yyyy-MM-dd)
     * @param proyectoId   identificador del proyecto al que pertenece la tarea
     */
    public Feature(String id, String tipo, String descripcion, String estado,
                   String responsable, String complejidad, String fecha, String proyectoId) {
        super(id, tipo, descripcion, estado, responsable, complejidad, fecha, proyectoId);
    }

    /**
     * Acepta un {@link Visitor} para aplicar una operación externa
     * definida por dicho visitor.
     *
     * @param v visitor que desea procesar esta Feature
     */
    @Override
    public void accept(Visitor v) {
        v.visitFeature(this);
    }
}