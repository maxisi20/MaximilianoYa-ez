package taller03;

/**
 * Interfaz para implementar el patrón de diseño Visitor aplicado a las clases que
 * extienden {@link Tarea}.  
 * <p>
 * Permite definir comportamientos específicos para cada tipo concreto de tarea 
 * sin modificar sus clases, promoviendo así la adherencia al principio de 
 * abierto/cerrado (Open/Closed Principle).
 * </p>
 *
 * Cada método corresponde a un tipo particular de tarea:
 * <ul>
 *   <li>{@code visitBug} para tareas de tipo {@link Bug}</li>
 *   <li>{@code visitFeature} para tareas de tipo {@link Feature}</li>
 *   <li>{@code visitDocumentacion} para tareas de tipo {@link Documentacion}</li>
 * </ul>
 */
public interface Visitor {

    /**
     * Operación a ejecutar cuando el Visitor procesa una tarea de tipo Bug.
     *
     * @param t instancia de {@link Tarea} correspondiente a un Bug.
     */
    void visitBug(Tarea t);

    /**
     * Operación a ejecutar cuando el Visitor procesa una tarea de tipo Feature.
     *
     * @param t instancia de {@link Tarea} correspondiente a un Feature.
     */
    void visitFeature(Tarea t);

    /**
     * Operación a ejecutar cuando el Visitor procesa una tarea de tipo Documentación.
     *
     * @param t instancia de {@link Tarea} correspondiente a Documentación.
     */
    void visitDocumentacion(Tarea t);
}