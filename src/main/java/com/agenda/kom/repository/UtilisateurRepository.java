package com.agenda.kom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agenda.kom.model.Utilisateur;

@Repository
public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {
	
	@Query("from Utilisateur u where u.mail=?1 and u.password=?2")
	public Optional<Utilisateur> getUtilisateurWithPassAndMail(String mail, String pass);
	
	@Query("from Utilisateur u where u.mail=?1")
	public Optional<Utilisateur> getUtilisateurWithMail(String mail);
	
}
