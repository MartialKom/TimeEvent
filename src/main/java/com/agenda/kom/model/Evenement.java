package com.agenda.kom.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="evenement")
public class Evenement {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="id_utilisateur")
	private Long id_U;
	
	@Column(name="descriptionEvenement",nullable = true)
	public String description;
	
	@Column(name="titre")
	public String titre;
	
	@Column(name="dateE")
	public String date;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId_U() {
		return id_U;
	}

	public void setId_U(Long id_U) {
		this.id_U = id_U;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
