package taller03;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Estrategia de priorización basada en el impacto del tipo de tarea.
 * <p>
 * Implementa el patrón <b>Strategy</b>, definiendo un algoritmo que ordena las
 * tareas según su criticidad:
 * </p>
 *
 * <ul>
 *     <li><b>Bug</b> → Alta prioridad</li>
 *     <li><b>Feature</b> → Prioridad media</li>
 *     <li><b>Documentación</b> → Baja prioridad</li>
 * </ul>
 *
 * Las tareas con mayor impacto aparecerán primero.
 */
public class PorImpactoStrategy implements PrioritizacionStrategy {

    /**
     * Asigna un valor numérico al impacto según el tipo de tarea.
     *
     * @param tipo tipo textual de la tarea (Bug, Feature, Documentación).
     * @return entero representando prioridad: 3 = alta, 2 = media, 1 = baja.
     */
    private int impacto(String tipo) {
        if (tipo == null) return 0;
        String t = tipo.trim().toLowerCase();
        switch (t) {
            case "bug":
                return 3;
            case "feature":
                return 2;
            case "documentación":
            case "documentacion":
            case "document":
                return 1;
            default:
                return 0;
        }
    }

    /**
     * Ordena la lista de tareas colocando primero aquellas con mayor impacto.
     *
     * @param tareas lista de objetos {@link Tarea} que deben ser priorizados.
     */
    @Override
    public void priorizar(List<Tarea> tareas) {
        Collections.sort(tareas, new Comparator<Tarea>() {
            @Override
            public int compare(Tarea t1, Tarea t2) {
                // Orden descendente: mayor impacto primero
                return Integer.compare(impacto(t2.getTipo()), impacto(t1.getTipo()));
            }
        });
    }
}
