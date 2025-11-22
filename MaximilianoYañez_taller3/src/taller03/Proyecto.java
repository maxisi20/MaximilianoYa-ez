package taller03;

import java.util.ArrayList;

/**
 * Representa un proyecto dentro del sistema.
 * <p>
 * Un proyecto posee un identificador, un nombre, un responsable asignado
 * y una lista de tareas asociadas. Permite agregar tareas y obtener sus datos.
 */
public class Proyecto {

    /** Identificador único del proyecto. */
    private String id;

    /** Nombre del proyecto. */
    private String nombre;

    /** Nombre del usuario responsable del proyecto. */
    private String responsable;

    /** Lista de tareas asociadas al proyecto. */
    private ArrayList<Tarea> tareas = new ArrayList<>();

    /**
     * Crea un nuevo proyecto con los datos especificados.
     *
     * @param id          Identificador único del proyecto.
     * @param nombre      Nombre del proyecto.
     * @param responsable Responsable asignado al proyecto.
     */
    public Proyecto(String id, String nombre, String responsable) {
        this.id = id;
        this.nombre = nombre;
        this.responsable = responsable;
    }

    /**
     * Obtiene el ID del proyecto.
     *
     * @return ID del proyecto.
     */
    public String getId() { return id; }

    /**
     * Obtiene el nombre del proyecto.
     *
     * @return Nombre del proyecto.
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene el responsable del proyecto.
     *
     * @return Nombre del responsable.
     */
    public String getResponsable() { return responsable; }

    /**
     * Obtiene la lista de tareas asociadas al proyecto.
     *
     * @return Lista de tareas del proyecto.
     */
    public ArrayList<Tarea> getTareas() { return tareas; }

    /**
     * Agrega una nueva tarea al proyecto.
     *
     * @param t Tarea a agregar.
     */
    public void agregarTarea(Tarea t) { tareas.add(t); }
}