package taller03;

/**
 * Implementación concreta del patrón Visitor que define acciones específicas
 * para cada tipo de {@link Tarea} del sistema.
 *
 * <p>Este visitor aplica efectos simbólicos según el tipo de tarea visitada:
 * <ul>
 *     <li><b>Bug:</b> incrementa la criticidad del proyecto.</li>
 *     <li><b>Feature:</b> sugiere recalcular la estimación de tiempo.</li>
 *     <li><b>Documentación:</b> indica una mejora en la calidad del proyecto.</li>
 * </ul>
 *
 * Actualmente estas acciones se representan mediante mensajes por consola,
 * pero pueden ampliarse para modificar atributos reales de los proyectos.
 */
public class AccionVisitor implements Visitor {

    /**
     * Ejecuta la acción correspondiente a una tarea de tipo Bug.
     *
     * <p>Simula un aumento en la criticidad del proyecto afectado.</p>
     *
     * @param t la tarea Bug a procesar
     */
    @Override
    public void visitBug(Tarea t) {
        System.out.println("[Visitor] Bug detectado: " + t.getDescripcion());
        System.out.println("→ Aumentando criticidad del proyecto " + t.getProyectoId());
        // Aquí podría actualizarse la criticidad real del proyecto.
    }

    /**
     * Ejecuta la acción correspondiente a una tarea de tipo Feature.
     *
     * <p>Representa un impacto en la estimación del tiempo del proyecto.</p>
     *
     * @param t la tarea Feature a procesar
     */
    @Override
    public void visitFeature(Tarea t) {
        System.out.println("[Visitor] Feature: " + t.getDescripcion());
        System.out.println("→ Recalcular estimación de tiempo para el proyecto " + t.getProyectoId());
    }

    /**
     * Ejecuta la acción correspondiente a una tarea de Documentación.
     *
     * <p>Simula una mejora en la calidad general del proyecto.</p>
     *
     * @param t la tarea de Documentación a procesar
     */
    @Override
    public void visitDocumentacion(Tarea t) {
        System.out.println("[Visitor] Documentación: " + t.getDescripcion());
        System.out.println("→ Mejora la calidad del proyecto " + t.getProyectoId());
    }
}