package taller03;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal del sistema. Encargada de gestionar usuarios, proyectos y tareas.
 * Implementa funciones de carga/guardado, menús de interacción, aplicación de
 * patrones de diseño como Strategy y Visitor, y generación de reportes.
 * 
 * <p>Este sistema sigue una arquitectura modular orientada a objetos y es utilizado
 * como backend principal para la administración de proyectos.</p>
 * 
 * @author  
 * @version 1.0
 */
public class Sistema {

    /** Instancia única (Singleton). */
    private static Sistema instancia = null;

    /**
     * Obtiene la instancia única del Sistema (patrón Singleton).
     * 
     * @return instancia única de Sistema
     */
    public static Sistema getInstance() {
        if (instancia == null) instancia = new Sistema();
        return instancia;
    }

    // Listas principales del sistema
    private ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    private ArrayList<Proyecto> listaProyectos = new ArrayList<>();
    private ArrayList<Tarea> listaTareas = new ArrayList<>();

    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Scanner sc = new Scanner(System.in);

    /** Constructor privado para Singleton. */
    private Sistema(){}

    // ========================================================================
    //  MÉTODOS DE CARGA Y GUARDADO
    // ========================================================================

    /**
     * Carga los usuarios desde el archivo usuarios.txt.
     * El formato esperado es: usuario|password|rol
     */
    public void cargarUsuarios() {
        File f = new File("usuarios.txt");
        if (!f.exists()) return;
        
        try (Scanner sc = new Scanner(f)) {
            listaUsuarios.clear();
            while (sc.hasNextLine()) {
                String linea = sc.nextLine().trim();
                if (linea.isEmpty() || linea.startsWith("#")) continue;
                
                String[] p = linea.split("\\|");
                if (p.length < 3) continue;
                
                listaUsuarios.add(new Usuario(p[0].trim(), p[1].trim(), p[2].trim()));
            }
        } catch(Exception e) {}
    }

    /**
     * Carga los proyectos desde proyectos.txt.
     * Formato: id|nombre|responsable
     */
    public void cargarProyectos() {
        File f = new File("proyectos.txt");
        if (!f.exists()) return;
        
        try (Scanner sc = new Scanner(f)) {
            listaProyectos.clear();
            while (sc.hasNextLine()) {
                String linea = sc.nextLine().trim();
                if (linea.isEmpty() || linea.startsWith("#")) continue;
                
                String[] p = linea.split("\\|");
                if (p.length < 3) continue;
                
                listaProyectos.add(new Proyecto(p[0].trim(), p[1].trim(), p[2].trim()));
            }
        } catch(Exception e) {}
    }

    /**
     * Carga las tareas desde tareas.txt utilizando la TareaFactory
     * para instanciar la subclase correspondiente.
     */
    public void cargarTareas() {
        File f = new File("tareas.txt");
        if (!f.exists()) return;

        try (Scanner sc = new Scanner(f)) {
            listaTareas.clear();
            while (sc.hasNextLine()) {
                String linea = sc.nextLine().trim();
                if (linea.isEmpty() || linea.startsWith("#")) continue;

                String[] p = linea.split("\\|");
                if (p.length < 8) continue;

                Tarea t = TareaFactory.createTarea(
                        p[1].trim(), p[2].trim(), p[3].trim(), p[4].trim(),
                        p[5].trim(), p[6].trim(), p[7].trim(), p[0].trim()
                );

                if (t != null) listaTareas.add(t);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /** Guarda todos los usuarios en usuarios.txt. */
    public void saveUsuarios() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("usuarios.txt"))) {
            for (Usuario u : listaUsuarios) {
                pw.println(u.getUsername() + "|" + u.getPassword() + "|" + u.getRol());
            }
        } catch(IOException e) {}
    }

    /** Guarda todos los proyectos en proyectos.txt. */
    public void saveProyectos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("proyectos.txt"))) {
            for (Proyecto p : listaProyectos) {
                pw.println(p.getId() + "|" + p.getNombre() + "|" + p.getResponsable());
            }
        } catch(IOException e) {}
    }

    /** Guarda todas las tareas en tareas.txt. */
    public void saveTareas() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("tareas.txt"))) {
            for (Tarea t : listaTareas) {
                pw.println(
                        t.getProyectoId() + "|" +
                        t.getId() + "|" +
                        t.getTipo() + "|" +
                        t.getDescripcion().replace("|","/") + "|" +
                        t.getEstado() + "|" +
                        t.getResponsable() + "|" +
                        t.getComplejidad() + "|" +
                        t.getFecha()
                );
            }
        } catch(IOException e) {}
    }

    /**
     * Verifica si las credenciales ingresadas coinciden con un usuario registrado.
     * 
     * @param username nombre de usuario
     * @param password contraseña
     * @return Usuario autenticado o null si falla
     */
    public Usuario authenticate(String username, String password) {
        for (Usuario u : listaUsuarios) {
            if (u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    // ========================================================================
    // MENÚ PRINCIPAL
    // ========================================================================

    /**
     * Ejecuta el menú principal del sistema permitiendo iniciar sesión
     * como usuario o administrador.
     */
    public void menuPrincipal() {
        cargarUsuarios();
        cargarProyectos();
        cargarTareas();

        while (true) {
            System.out.println("\n--- LOGIN ---");
            System.out.print("Usuario (o 'salir'): ");
            String u = sc.nextLine().trim();

            if (u.equalsIgnoreCase("salir")) {
                saveUsuarios(); saveProyectos(); saveTareas();
                System.out.println("Saliendo...");
                return;
            }

            System.out.print("Contraseña: ");
            String p = sc.nextLine().trim();

            Usuario usuario = authenticate(u, p);

            if (usuario == null) {
                System.out.println("Login fallido.");
                continue;
            }

            if (usuario.getRol().equalsIgnoreCase("Administrador"))
                menuAdmin(sc, usuario);
            else
                menuUsuario(sc, usuario);
        }
    }

    // ========================================================================
    // MENÚ ADMINISTRADOR
    // ========================================================================

    /**
     * Muestra el menú del administrador con todas sus funciones evaluadas en el taller.
     *
     * @param sc scanner
     * @param usuario usuario administrador
     */
    private void menuAdmin(Scanner sc, Usuario usuario) {
        while (true) {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Ver lista completa de proyectos y tareas");
            System.out.println("2. Agregar o eliminar proyecto");
            System.out.println("3. Agregar o eliminar tarea");
            System.out.println("4. Asignar prioridades (Strategy)");
            System.out.println("5. Generar reporte");
            System.out.println("6. Salir");
            System.out.print("Seleccione opción: ");
            
            String opcion = sc.nextLine().trim();
            
            switch(opcion) {
                case "1": verProyectosYTareasCompletas(); break;
                case "2": menuGestionProyectos(); break;
                case "3": menuGestionTareas(); break;
                case "4": menuPriorizacionStrategy(); break;
                case "5": generarReporte(); break;
                case "6": return;
                default: System.out.println("Opción inválida");
            }
        }
    }

    /**
     * Muestra proyectos y sus tareas asociadas.
     */
    private void verProyectosYTareasCompletas() {
        System.out.println("\n=== LISTA COMPLETA DE PROYECTOS Y TAREAS ===");
        
        for (Proyecto proyecto : listaProyectos) {
            System.out.println("\nProyecto: " + proyecto.getNombre() + 
                             " (ID: " + proyecto.getId() + ")" +
                             " - Responsable: " + proyecto.getResponsable());
            
            List<Tarea> tareasProyecto = obtenerTareasPorProyecto(proyecto.getId());
            if (tareasProyecto.isEmpty()) {
                System.out.println("  No hay tareas asignadas");
            } else {
                for (Tarea tarea : tareasProyecto) {
                    System.out.println("  - Tarea: " + tarea.getDescripcion() +
                                     " | Tipo: " + tarea.getTipo() +
                                     " | Estado: " + tarea.getEstado() +
                                     " | Responsable: " + tarea.getResponsable() +
                                     " | Complejidad: " + tarea.getComplejidad() +
                                     " | Fecha: " + tarea.getFecha());
                }
            }
        }
    }

    // ========================================================================
    // GESTIÓN DE PROYECTOS
    // ========================================================================

    /** Muestra menú para agregar o eliminar proyectos. */
    private void menuGestionProyectos() {
        System.out.println("\n=== GESTIÓN DE PROYECTOS ===");
        System.out.println("1. Agregar proyecto");
        System.out.println("2. Eliminar proyecto");
        System.out.print("Seleccione opción: ");
        
        String opcion = sc.nextLine().trim();
        
        switch(opcion) {
            case "1": agregarProyecto(); break;
            case "2": eliminarProyecto(); break;
            default: System.out.println("Opción inválida");
        }
    }

    /** Agrega un nuevo proyecto. */
    private void agregarProyecto() {
        System.out.print("ID del proyecto: ");
        String id = sc.nextLine().trim();
        System.out.print("Nombre del proyecto: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Responsable del proyecto: ");
        String responsable = sc.nextLine().trim();
        
        listaProyectos.add(new Proyecto(id, nombre, responsable));
        System.out.println("Proyecto agregado exitosamente");
    }

    /** Elimina un proyecto por su ID junto con sus tareas asociadas. */
    private void eliminarProyecto() {
        System.out.print("ID del proyecto a eliminar: ");
        String id = sc.nextLine().trim();
        
        Proyecto proyectoAEliminar = null;
        for (Proyecto proyecto : listaProyectos) {
            if (proyecto.getId().equals(id)) {
                proyectoAEliminar = proyecto;
                break;
            }
        }
        
        if (proyectoAEliminar != null) {
            listaProyectos.remove(proyectoAEliminar);
            listaTareas.removeIf(tarea -> tarea.getProyectoId().equals(id));
            System.out.println("Proyecto y tareas eliminados exitosamente");
        } else {
            System.out.println("Proyecto no encontrado");
        }
    }

    // ========================================================================
    // GESTIÓN DE TAREAS
    // ========================================================================

    /** Muestra menú para agregar o eliminar tareas. */
    private void menuGestionTareas() {
        System.out.println("\n=== GESTIÓN DE TAREAS ===");
        System.out.println("1. Agregar tarea");
        System.out.println("2. Eliminar tarea");
        System.out.print("Seleccione opción: ");
        
        String opcion = sc.nextLine().trim();
        
        switch(opcion) {
            case "1": agregarTarea(); break;
            case "2": eliminarTarea(); break;
            default: System.out.println("Opción inválida");
        }
    }

    /**
     * Agrega una nueva tarea utilizando la fábrica de tareas.
     * Valida que el proyecto exista y que el responsable no tenga
     * otra tarea en la fecha actual.
     */
    private void agregarTarea() {
        System.out.print("ID del proyecto para la tarea: ");
        String proyectoId = sc.nextLine().trim();
        
        boolean proyectoExiste = false;
        for (Proyecto p : listaProyectos) {
            if (p.getId().equals(proyectoId)) {
                proyectoExiste = true;
                break;
            }
        }
        if (!proyectoExiste) {
            System.out.println("El proyecto no existe");
            return;
        }
        
        System.out.print("ID de la tarea: ");
        String id = sc.nextLine().trim();
        System.out.println("Tipo (Bug/Feature/Documentación): ");
        String tipo = sc.nextLine().trim();
        System.out.print("Descripción: ");
        String descripcion = sc.nextLine().trim();
        System.out.print("Estado: ");
        String estado = sc.nextLine().trim();
        System.out.print("Responsable: ");
        String responsable = sc.nextLine().trim();

        for (Tarea t : listaTareas) {
            if (t.getResponsable().equalsIgnoreCase(responsable) && 
                t.getFecha().equals(LocalDate.now().toString())) {
                System.out.println("El responsable ya tiene una tarea hoy.");
                return;
            }
        }

        System.out.print("Complejidad (Baja/Media/Alta): ");
        String complejidad = sc.nextLine().trim();
        
        Tarea nuevaTarea = TareaFactory.createTarea(
            id, tipo, descripcion, estado, responsable,
            complejidad, LocalDate.now().toString(), proyectoId
        );

        listaTareas.add(nuevaTarea);
        System.out.println("Tarea agregada exitosamente");
    }

    /** Elimina una tarea por su ID. */
    private void eliminarTarea() {
        System.out.print("ID de la tarea a eliminar: ");
        String id = sc.nextLine().trim();
        
        Tarea tareaAEliminar = null;
        for (Tarea tarea : listaTareas) {
            if (tarea.getId().equals(id)) {
                tareaAEliminar = tarea;
                break;
            }
        }
        
        if (tareaAEliminar != null) {
            listaTareas.remove(tareaAEliminar);
            System.out.println("Tarea eliminada exitosamente");
        } else {
            System.out.println("Tarea no encontrada");
        }
    }

    // ========================================================================
    // STRATEGY DE PRIORIZACIÓN
    // ========================================================================

    /**
     * Muestra el menú para seleccionar una estrategia de priorización
     * y aplica dicha estrategia a la lista de tareas.
     */
    private void menuPriorizacionStrategy() {
        System.out.println("\n=== PRIORIZACIÓN DE TAREAS ===");
        System.out.println("1. Priorizar por fecha");
        System.out.println("2. Priorizar por impacto");
        System.out.println("3. Priorizar por complejidad");
        System.out.print("Seleccione estrategia: ");
        
        String opcion = sc.nextLine().trim();
        
        PrioritizacionStrategy estrategia = null;
        
        switch(opcion) {
            case "1": estrategia = new PorFechaStrategy(); break;
            case "2": estrategia = new PorImpactoStrategy(); break;
            case "3": estrategia = new PorComplejidadStrategy(); break;
            default:
                System.out.println("Opción inválida");
                return;
        }
        
        estrategia.priorizar(listaTareas);
        System.out.println("Tareas priorizadas exitosamente");
        
        System.out.println("\n=== TAREAS PRIORIZADAS ===");
        for (Tarea tarea : listaTareas) {
            String prioridad = calcularPrioridad(tarea);
            System.out.println("- " + tarea.getEstado() + 
                             " | Tipo: " + tarea.getTipo() +
                             " | Descripción: " + tarea.getDescripcion() +
                             " | Proyecto: " + tarea.getProyectoId() +
                             " | Fecha: " + tarea.getFecha() +
                             " | Complejidad: " + tarea.getComplejidad() +
                             " | Prioridad: " + prioridad);
        }
    }

    // ========================================================================
    // REPORTE
    // ========================================================================

    /**
     * Genera un archivo reporte.txt con información detallada
     * de todos los proyectos y sus tareas.
     */
    private void generarReporte() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("reporte.txt"))) {
            pw.println("=== REPORTE DE PROYECTOS ===");
            pw.println("Fecha: " + LocalDate.now());
            pw.println();
            
            for (Proyecto proyecto : listaProyectos) {
                pw.println("Proyecto: " + proyecto.getNombre());
                pw.println("ID: " + proyecto.getId());
                pw.println("Responsable: " + proyecto.getResponsable());
                
                List<Tarea> tareasProyecto = obtenerTareasPorProyecto(proyecto.getId());
                pw.println("Total de tareas: " + tareasProyecto.size());
                
                if (!tareasProyecto.isEmpty()) {
                    pw.println("Tareas:");
                    for (Tarea tarea : tareasProyecto) {
                        pw.println("  - " + tarea.getDescripcion() +
                                 " | Tipo: " + tarea.getTipo() +
                                 " | Estado: " + tarea.getEstado() +
                                 " | Responsable: " + tarea.getResponsable() +
                                 " | Complejidad: " + tarea.getComplejidad());
                    }
                }
                pw.println();
            }
            
            System.out.println("Reporte generado exitosamente en reporte.txt");
        } catch (IOException e) {
            System.out.println("Error al generar reporte");
        }
    }

    // ========================================================================
    // MENÚ USUARIO
    // ========================================================================

    /**
     * Muestra el menú del usuario con opciones para visualizar y actualizar tareas,
     * así como aplicar el Visitor.
     * 
     * @param sc Scanner
     * @param usuario usuario autenticado
     */
    private void menuUsuario(Scanner sc, Usuario usuario) {
        while (true) {
            System.out.println("\n=== MENÚ USUARIO ===");
            System.out.println("1. Ver proyectos disponibles");
            System.out.println("2. Ver tareas asignadas");
            System.out.println("3. Actualizar estado de tarea");
            System.out.println("4. Aplicar Visitor sobre tareas");
            System.out.println("5. Salir");
            System.out.print("Seleccione opción: ");
            
            String opcion = sc.nextLine().trim();
            
            switch(opcion) {
                case "1": verProyectosDisponibles(); break;
                case "2": verTareasAsignadas(usuario.getUsername()); break;
                case "3": actualizarEstadoTarea(usuario.getUsername()); break;
                case "4": aplicarVisitorTareas(usuario.getUsername()); break;
                case "5": return;
                default: System.out.println("Opción inválida");
            }
        }
    }

    /** Muestra los proyectos disponibles para el usuario. */
    private void verProyectosDisponibles() {
        System.out.println("\n=== PROYECTOS DISPONIBLES ===");
        for (Proyecto proyecto : listaProyectos) {
            List<Tarea> tareasProyecto = obtenerTareasPorProyecto(proyecto.getId());
            System.out.println("- " + proyecto.getNombre() + 
                             " (ID: " + proyecto.getId() + ")" +
                             " - Responsable: " + proyecto.getResponsable() +
                             " - Tareas: " + tareasProyecto.size());
        }
    }

    /**
     * Muestra todas las tareas asignadas a un usuario.
     * 
     * @param username nombre del usuario
     */
    private void verTareasAsignadas(String username) {
        System.out.println("\n=== TAREAS ASIGNADAS A " + username + " ===");
        List<Tarea> tareasUsuario = obtenerTareasPorUsuario(username);
        
        if (tareasUsuario.isEmpty()) {
            System.out.println("No hay tareas asignadas");
        } else {
            for (Tarea tarea : tareasUsuario) {
                String proyectoNombre = obtenerNombreProyecto(tarea.getProyectoId());
                System.out.println("- Proyecto: " + proyectoNombre +
                                 " | Tarea: " + tarea.getDescripcion() +
                                 " | Tipo: " + tarea.getTipo() +
                                 " | Estado: " + tarea.getEstado() +
                                 " | Complejidad: " + tarea.getComplejidad());
            }
        }
    }

    /**
     * Permite al usuario actualizar el estado de una tarea propia.
     * 
     * @param username usuario actual
     */
    private void actualizarEstadoTarea(String username) {
        List<Tarea> tareasUsuario = obtenerTareasPorUsuario(username);
        
        if (tareasUsuario.isEmpty()) {
            System.out.println("No hay tareas asignadas para actualizar");
            return;
        }
        
        System.out.println("\n=== TUS TAREAS ===");
        for (int i = 0; i < tareasUsuario.size(); i++) {
            Tarea tarea = tareasUsuario.get(i);
            System.out.println((i + 1) + ". " + tarea.getDescripcion() + 
                             " - Estado actual: " + tarea.getEstado());
        }
        
        System.out.print("Seleccione número de tarea a actualizar: ");
        try {
            int seleccion = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (seleccion >= 0 && seleccion < tareasUsuario.size()) {
                Tarea tareaSeleccionada = tareasUsuario.get(seleccion);
                System.out.println("Estados disponibles: Pendiente, En progreso, Completada");
                System.out.print("Nuevo estado: ");
                String nuevoEstado = sc.nextLine().trim();
                
                if (esTransicionValida(tareaSeleccionada.getEstado(), nuevoEstado)) {
                    tareaSeleccionada.setEstado(nuevoEstado);
                    System.out.println("Estado actualizado exitosamente");
                } else {
                    System.out.println("Transición de estado no válida");
                }
            } else {
                System.out.println("Selección inválida");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese un número válido");
        }
    }

    /**
     * Aplica el patrón Visitor a todas las tareas asignadas a un usuario.
     * 
     * @param username usuario actual
     */
    private void aplicarVisitorTareas(String username) {
        List<Tarea> tareasUsuario = obtenerTareasPorUsuario(username);
        Visitor visitor = new AccionVisitor();
        
        if (tareasUsuario.isEmpty()) {
            System.out.println("No hay tareas asignadas");
            return;
        }
        
        System.out.println("\n=== APLICANDO VISITOR A TAREAS ===");
        for (Tarea tarea : tareasUsuario) {
            System.out.println("\nTarea: " + tarea.getDescripcion() + " (Tipo: " + tarea.getTipo() + ")");
            tarea.accept(visitor);
        }
    }

    // ========================================================================
    // MÉTODOS AUXILIARES
    // ========================================================================

    /**
     * Obtiene todas las tareas asociadas a un proyecto.
     * 
     * @param proyectoId identificador del proyecto
     * @return lista de tareas del proyecto
     */
    private List<Tarea> obtenerTareasPorProyecto(String proyectoId) {
        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : listaTareas) {
            if (tarea.getProyectoId().equals(proyectoId)) {
                resultado.add(tarea);
            }
        }
        return resultado;
    }

    /**
     * Obtiene todas las tareas asignadas a un usuario.
     * 
     * @param username nombre de usuario
     * @return lista de tareas asignadas
     */
    private List<Tarea> obtenerTareasPorUsuario(String username) {
        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : listaTareas) {
            if (tarea.getResponsable().equalsIgnoreCase(username)) {
                resultado.add(tarea);
            }
        }
        return resultado;
    }

    /**
     * Busca el nombre de un proyecto mediante su ID.
     * 
     * @param proyectoId ID del proyecto
     * @return nombre del proyecto o "Desconocido" si no existe
     */
    private String obtenerNombreProyecto(String proyectoId) {
        for (Proyecto proyecto : listaProyectos) {
            if (proyecto.getId().equals(proyectoId)) {
                return proyecto.getNombre();
            }
        }
        return "Desconocido";
    }

    /**
     * Valida si es posible realizar el cambio de estado.
     * 
     * @param estadoActual estado actual
     * @param nuevoEstado estado solicitado
     * @return true si la transición es válida
     */
    private boolean esTransicionValida(String estadoActual, String nuevoEstado) {
        if (estadoActual.equals("Pendiente") && nuevoEstado.equals("En progreso")) {
            return true;
        }
        if (estadoActual.equals("En progreso") && nuevoEstado.equals("Completada")) {
            return true;
        }
        return false;
    }

    /**
     * Calcula una prioridad derivada basada en el tipo y la complejidad.
     * 
     * @param t tarea a evaluar
     * @return string con la categoría de prioridad (Alta/Media/Baja)
     */
    private String calcularPrioridad(Tarea t) {
        int impacto = 0;
        String tipo = t.getTipo();
        if (tipo != null) {
            switch (tipo.trim().toLowerCase()) {
                case "bug": impacto = 3; break;
                case "feature": impacto = 2; break;
                case "documentacion":
                case "documentación": impacto = 1; break;
                default: impacto = 1; break;
            }
        }

        int comp = 0;
        String c = t.getComplejidad();
        if (c != null) {
            switch (c.trim().toLowerCase()) {
                case "alta": comp = 3; break;
                case "media": comp = 2; break;
                case "baja": comp = 1; break;
                default: comp = 1; break;
            }
        }

        int score = Math.max(impacto, comp);
        if (score >= 3) return "Alta";
        if (score == 2) return "Media";
        return "Baja";
    }
}