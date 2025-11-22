package taller03;

/**
 * Representa una tarea de tipo Documentación dentro del sistema.
 *
 * <p>Esta clase es una implementación concreta de {@link Tarea} que participa
 * en el patrón Visitor. Aunque no agrega nuevos atributos, permite que un
 * {@link Visitor} aplique una operación específica para tareas de documentación.</p>
 *
 * <p>Los datos generales de la tarea (id, tipo, descripción, estado, responsable,
 * complejidad, fecha y proyectoId) son gestionados por la clase base {@link Tarea}.</p>
 */
public class Documentacion extends Tarea {

    /**
     * Crea una nueva tarea de Documentación.
     *
     * @param id           identificador único de la tarea
     * @param tipo         tipo de tarea (se espera "Documentacion" o "Documentación")
     * @param descripcion  descripción detallada de la tarea
     * @param estado       estado actual de la tarea (ej.: "Pendiente", "En progreso", "Completada")
     * @param responsable  persona asignada a realizar la tarea
     * @param complejidad  nivel de complejidad de la tarea ("Alta", "Media", "Baja")
     * @param fecha        fecha asociada en formato yyyy-MM-dd
     * @param proyectoId   identificador del proyecto al que pertenece la tarea
     */
    public Documentacion(String id, String tipo, String descripcion, String estado,
                         String responsable, String complejidad, String fecha, String proyectoId) {
        super(id, tipo, descripcion, estado, responsable, complejidad, fecha, proyectoId);
    }

    /**
     * Acepta un {@link Visitor} para permitir aplicar una operación externa
     * específica para tareas de documentación.
     *
     * @param v visitor que procesará esta tarea
     */
    @Override
    public void accept(Visitor v) {
        v.visitDocumentacion(this);
    }
}