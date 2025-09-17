package org.ies63.progI.entities;

import java.util.Objects;

public class Auto implements Comparable {

	private int idAuto;
	private String patente;
	private String color;
	private int anio;
	private int kilometraje;
	private Marca marca;
	private String modelo;
	private  Cliente cliente;
	private Seguro seguro;


	public Auto(){
		this.cliente= new Cliente();
		this.seguro= new Seguro();
	}


	public Auto(String patente){
		idAuto=-1;
		this.patente= patente;
		this.cliente= new Cliente();
		this.seguro= new Seguro();
	}

	public Auto(String patente, String color, int anio, int kilometraje,
							Marca marca, String modelo, Cliente cliente, Seguro seguro) {
		this.patente = patente;
		this.color = color;
		this.anio = anio;
		this.kilometraje = kilometraje;
		this.marca = marca;
		this.modelo = modelo;
		this.cliente = cliente;
		this.seguro = seguro;
	}


	public Auto(int id, String patente, String color, int anio, int kilometraje, Marca marca, String modelo) {
		this.idAuto= id;
		this.patente = patente;
		this.color = color;
		this.anio = anio;
		this.kilometraje = kilometraje;
		this.marca = marca;
		this.modelo = modelo;
		this.cliente= null;
		this.seguro= null;
	}

	public Seguro getSeguro() {
		return seguro;
	}

	public void setSeguro(Seguro seguro) {
		this.seguro = seguro;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getIdAuto() {
		return idAuto;
	}


	public void setIdAuto(int idAuto) {
		this.idAuto = idAuto;
	}


	public String getPatente() {
		return patente;
	}


	public void setPatente(String patente) {
		this.patente = patente;
	}


	public String getColor() {
		return color;
	}





	public void setColor(String color) {
		this.color = color;
	}


	public int getAnio() {
		return anio;
	}


	public void setAnio(int anio) {
		this.anio = anio;
	}


	public int getKilometraje() {
		return kilometraje;
	}


	public void setKilometraje(int kilometraje) {
		this.kilometraje = kilometraje;
	}


	public Marca getMarca() {
		return marca;
	}


	public void setMarca(Marca marca) {
		this.marca = marca;
	}


	public String getModelo() {
		return modelo;
	}


	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	@Override
	public String toString() {
		return "Auto [" +  idAuto +"  patente=" + patente + ", color=" + color + ", anio=" + anio + ", kilometraje=" + kilometraje
				+ ", marca=" + marca + ", modelo=" + modelo + ", "
				+  this.cliente.toString() + " " + this.seguro.toString() + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(patente);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Auto other = (Auto) obj;
		return Objects.equals(patente, other.patente);
	}


	@Override
	public int compareTo(Object o) {
		// -1  menor , 0 iguales  , 1 mayor

		Auto otroAuto= (Auto) o;
		int resultado=Integer.compare(this.anio, otroAuto.anio);

		if(resultado ==0)
			resultado= Integer.compare(this.kilometraje, otroAuto.kilometraje);


		return resultado;
	}



}