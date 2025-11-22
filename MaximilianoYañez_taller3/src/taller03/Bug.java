package taller03;

/**
 * Representa una tarea de tipo Bug dentro del sistema.
 *
 * <p>Esta clase es una implementación concreta de {@link Tarea} que participa
 * en el patrón Visitor. Permite que un {@link Visitor} ejecute una operación
 * específica sobre tareas marcadas como errores o fallas del sistema.</p>
 *
 * <p>Los atributos generales de la tarea (id, tipo, descripción, estado,
 * responsable, complejidad, fecha y proyectoId) son administrados por la
 * clase base {@link Tarea}.</p>
 */
public class Bug extends Tarea {

    /**
     * Crea una nueva tarea de tipo Bug.
     *
     * @param id           identificador único de la tarea
     * @param tipo         tipo de tarea (se espera "Bug")
     * @param descripcion  descripción del problema o error
     * @param estado       estado actual del bug (ej.: "Pendiente", "En progreso", "Resuelto")
     * @param responsable  persona asignada para corregir el bug
     * @param complejidad  nivel de complejidad ("Alta", "Media", "Baja")
     * @param fecha        fecha asociada en formato yyyy-MM-dd
     * @param proyectoId   id del proyecto al que pertenece la tarea
     */
    public Bug(String id, String tipo, String descripcion, String estado,
               String responsable, String complejidad, String fecha, String proyectoId) {
        super(id, tipo, descripcion, estado, responsable, complejidad, fecha, proyectoId);
    }

    /**
     * Acepta un {@link Visitor} para permitir la ejecución de operaciones
     * específicas sobre tareas de tipo Bug.
     *
     * @param v visitor que procesará este bug
     */
    @Override
    public void accept(Visitor v) {
        v.visitBug(this);
    }
}