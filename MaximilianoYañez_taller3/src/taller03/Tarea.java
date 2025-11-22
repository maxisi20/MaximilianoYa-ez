package taller03;

/**
 * Clase abstracta que representa una tarea dentro del sistema.
 * <p>
 * Cada tarea posee información común como un identificador, tipo, descripción,
 * estado, responsable asignado, complejidad, fecha de creación y el proyecto al cual pertenece.
 * Además, utiliza el patrón Visitor para permitir operaciones externas según el tipo de tarea.
 */
public abstract class Tarea {

    /** Identificador único de la tarea. */
    protected String id;

    /** Tipo de tarea (Bug, Feature, Documentación, etc.). */
    protected String tipo;

    /** Descripción detallada de la tarea. */
    protected String descripcion;

    /** Estado actual de la tarea (Ej: Pendiente, En progreso, Finalizada). */
    protected String estado;

    /** Nombre del responsable asignado a la tarea. */
    protected String responsable;

    /** Nivel de complejidad de la tarea. */
    protected String complejidad;

    /** Fecha de creación o asignación de la tarea en formato yyyy-MM-dd. */
    protected String fecha;

    /** ID del proyecto al cual pertenece esta tarea. */
    protected String proyectoId;

    /**
     * Constructor para inicializar una tarea con todos sus atributos.
     *
     * @param id           Identificador único de la tarea.
     * @param tipo         Tipo de tarea.
     * @param descripcion  Descripción de la tarea.
     * @param estado       Estado actual de la tarea.
     * @param responsable  Responsable asignado.
     * @param complejidad  Nivel de complejidad.
     * @param fecha        Fecha asociada a la tarea.
     * @param proyectoId   ID del proyecto dueño de la tarea.
     */
    public Tarea(String id, String tipo, String descripcion, String estado,
                 String responsable, String complejidad, String fecha, String proyectoId) {

        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.responsable = responsable;
        this.complejidad = complejidad;
        this.fecha = fecha;
        this.proyectoId = proyectoId;
    }

    /** @return ID de la tarea. */
    public String getId() { return id; }

    /** @return Tipo de tarea. */
    public String getTipo() { return tipo; }

    /** @return Descripción de la tarea. */
    public String getDescripcion() { return descripcion; }

    /** @return Estado actual de la tarea. */
    public String getEstado() { return estado; }

    /**
     * Establece un nuevo estado para la tarea.
     *
     * @param estado Nuevo estado.
     */
    public void setEstado(String estado) { this.estado = estado; }

    /** @return Nombre del responsable asignado. */
    public String getResponsable() { return responsable; }

    /**
     * Cambia el responsable asignado a esta tarea.
     *
     * @param responsable Nuevo responsable.
     */
    public void setResponsable(String responsable) { this.responsable = responsable; }

    /** @return Nivel de complejidad de la tarea. */
    public String getComplejidad() { return complejidad; }

    /** @return Fecha asociada a la tarea. */
    public String getFecha() { return fecha; }

    /** @return ID del proyecto al que pertenece la tarea. */
    public String getProyectoId() { return proyectoId; }

    /**
     * Método abstracto para aplicar el patrón Visitor.
     * Cada subtipo de tarea (Bug, Feature, Documentación, etc.) debe implementar cómo
     * recibir al visitante.
     *
     * @param v Implementación del visitante que ejecutará operaciones específicas según el tipo de tarea.
     */
    public abstract void accept(Visitor v);
}