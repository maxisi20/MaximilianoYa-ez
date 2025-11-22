package taller03;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Estrategia de priorización basada en la fecha de creación de las tareas.
 * <p>
 * Implementa el patrón <b>Strategy</b>, ordenando las tareas desde la más
 * antigua a la más reciente. Se asume que la fecha está en formato
 * <b>yyyy-MM-dd</b>, lo cual permite una comparación lexicográfica correcta.
 * </p>
 */
public class PorFechaStrategy implements PrioritizacionStrategy {

    /**
     * Ordena la lista de tareas por fecha de creación.
     *
     * @param tareas lista de objetos {@link Tarea} que deben ser ordenados.
     */
    @Override
    public void priorizar(List<Tarea> tareas) {

        Collections.sort(tareas, new Comparator<Tarea>() {
            @Override
            public int compare(Tarea t1, Tarea t2) {
                return t1.getFecha().compareTo(t2.getFecha()); // más antigua → primero
            }
        });
    }
}