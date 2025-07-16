package com.geremiasirisarri.aplicacionesmoviles;

/**
 * Modelo de servicio para Firestore.
 */
public class Service {
    private String id;
    private String name;
    private String description;
    private String imageName;  // Nombre del drawable sin extensión

    /**
     * Constructor vacío público requerido por Firestore.
     */
    public Service() {
        // No args
    }

    /**
     * Constructor de conveniencia para uso local.
     */
    public Service(String name, String description, String imageName) {
        this.name = name;
        this.description = description;
        this.imageName = imageName;
    }

    // Getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
