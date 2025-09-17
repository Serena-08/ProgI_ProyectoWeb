package org.ies63.progI.entities;

public class Cliente implements Comparable {
  private int id;
  private String nombre;
  private String apellido;
  private String telefono;

  // Constructor
  public Cliente() {

  }
  public Cliente(int id, String nombre, String apellido, String telefono) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.telefono = telefono;
  }

  // Getters y Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  @Override
  public String toString() {
    return "Cliente{" +
        "id=" + id +
        ", nombre='" + nombre + '\'' +
        ", apellido='" + apellido + '\'' +
        ", telefono='" + telefono + '\'' +
        '}';
  }

  @Override
  public int compareTo(Object o) {
    Cliente otro= (Cliente) o;
    int comparacionApellido = this.apellido.compareTo(otro.apellido);

    if (comparacionApellido != 0) {
      return comparacionApellido;
    }

    return this.nombre.compareTo(otro.nombre);
  }
}