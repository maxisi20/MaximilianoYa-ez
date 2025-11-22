package taller03;

import java.util.List;

/**
 * Interfaz que define el contrato para implementar estrategias de priorización
 * de tareas dentro del sistema.  
 * <p>
 * Forma parte del patrón de diseño <b>Strategy</b>, permitiendo definir,
 * intercambiar y aplicar diferentes algoritmos de priorización sin modificar el
 * código que utiliza dichas estrategias.
 * </p>
 *
 * Cada clase que implemente esta interfaz deberá definir su propia forma de
 * ordenar la lista de tareas según un criterio específico (fecha, impacto,
 * complejidad, etc.).
 */
public interface PrioritizacionStrategy {

    /**
     * Aplica la estrategia de priorización sobre la lista de tareas recibida.
     * La implementación concreta deberá modificar el orden de la lista según
     * el criterio correspondiente.
     *
     * @param tareas lista de objetos {@link Tarea} a priorizar.  
     *               No debe ser nula, pero puede estar vacía.
     */
    void priorizar(List<Tarea> tareas);
}
