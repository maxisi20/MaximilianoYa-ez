package taller03;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Estrategia de priorización basada en la complejidad asignada a cada tarea.
 * <p>
 * Implementa el patrón <b>Strategy</b>, ordenando las tareas desde la de mayor
 * complejidad hacia la menor. Los valores reconocidos son:
 * <ul>
 *     <li><b>Alta</b> → prioridad máxima</li>
 *     <li><b>Media</b></li>
 *     <li><b>Baja</b> → prioridad mínima</li>
 * </ul>
 * Cualquier valor no reconocido recibe prioridad 0.
 * </p>
 */
public class PorComplejidadStrategy implements PrioritizacionStrategy {

    /**
     * Convierte el texto de complejidad en un valor numérico para comparación.
     *
     * @param c cadena que representa la complejidad (Alta, Media, Baja).
     * @return entero correspondiente al nivel de complejidad.
     */
    private int valorComplejidad(String c) {
        if (c == null) return 0;
        String s = c.trim().toLowerCase();
        switch (s) {
            case "alta":  return 3;
            case "media": return 2;
            case "baja":  return 1;
            default:      return 0;
        }
    }

    /**
     * Ordena las tareas según su complejidad, priorizando las de mayor nivel.
     *
     * @param tareas lista de {@link Tarea} a ordenar.
     */
    @Override
    public void priorizar(List<Tarea> tareas) {
        Collections.sort(tareas, new Comparator<Tarea>() {
            @Override
            public int compare(Tarea t1, Tarea t2) {
                return Integer.compare(
                        valorComplejidad(t2.getComplejidad()),
                        valorComplejidad(t1.getComplejidad())
                );
            }
        });
    }
}
