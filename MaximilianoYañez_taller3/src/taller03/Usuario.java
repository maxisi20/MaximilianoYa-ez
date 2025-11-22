package taller03;

/**
 * Representa a un usuario del sistema, con nombre de usuario, contraseña y rol asociado.
 * Esta clase se utiliza para autenticar y gestionar permisos dentro del sistema.
 */
public class Usuario {
    /** Nombre de usuario utilizado para iniciar sesión. */
    private String username;

    /** Contraseña del usuario. */
    private String password;

    /** Rol del usuario (por ejemplo: "admin", "editor", "viewer"). */
    private String rol;

    /**
     * Crea una nueva instancia de Usuario.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña del usuario.
     * @param rol Rol asignado al usuario.
     */
    public Usuario(String username, String password, String rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return El nombre de usuario.
     */
    public String getUsername() { 
        return username; 
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña.
     */
    public String getPassword() { 
        return password; 
    }

    /**
     * Obtiene el rol asignado al usuario.
     *
     * @return El rol del usuario.
     */
    public String getRol() { 
        return rol; 
    }

    /**
     * Modifica la contraseña del usuario.
     *
     * @param password Nueva contraseña a asignar.
     */
    public void setPassword(String password) { 
        this.password = password; 
    }

    /**
     * Modifica el rol del usuario.
     *
     * @param rol Nuevo rol a asignar.
     */
    public void setRol(String rol) { 
        this.rol = rol; 
    }
}