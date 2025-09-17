package org.ies63.progI.entities;

import java.util.Objects;

public class Seguro implements Comparable<Seguro> {
  private int idSeguro;
  private double costoMensual;
  private String tipo;
  private String compañia;

  public Seguro() {}

  public Seguro(String tipo, double costoMensual, String compañia) {
    this.tipo = tipo;
    this.costoMensual = costoMensual;
    this.compañia = compañia;
  }

  public int getIdSeguro() {
    return idSeguro;
  }

  public void setIdSeguro(int idSeguro) {
    this.idSeguro = idSeguro;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public double getCostoMensual() {
    return costoMensual;
  }

  public void setCostoMensual(double costoMensual) {
    this.costoMensual = costoMensual;
  }

  public String getCompañia() {
    return compañia;
  }

  public void setCompañia(String compañia) {
    this.compañia = compañia;
  }

  @Override
  public String toString() {
    return "Seguro{" +
        "idSeguro=" + idSeguro +
        ", tipo='" + tipo + '\'' +
        ", costoMensual=" + costoMensual +
        ", compañia='" + compañia + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Seguro seguro = (Seguro) o;
    return idSeguro == seguro.idSeguro &&
        Objects.equals(tipo, seguro.tipo) &&
        Objects.equals(costoMensual, seguro.costoMensual) &&
        Objects.equals(compañia, seguro.compañia);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idSeguro, tipo, costoMensual, compañia);
  }

  @Override
  public int compareTo(Seguro otroSeguro) {
    int resultadoValor = Double.compare(this.costoMensual, otroSeguro.costoMensual);
    if (resultadoValor != 0) {
      return resultadoValor;
    }
    int resultadoTipo = this.tipo.compareTo(otroSeguro.tipo);
    if (resultadoTipo != 0) {
      return resultadoTipo;
    }
    return this.compañia.compareTo(otroSeguro.compañia);
  }
}