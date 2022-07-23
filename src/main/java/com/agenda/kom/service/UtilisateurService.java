package com.agenda.kom.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.kom.model.Utilisateur;
import com.agenda.kom.repository.UtilisateurRepository;



@Service
public class UtilisateurService {
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;

	public Optional<Utilisateur> getUtilisateur(String mail, String pass)
	{
		//Recherche un utilisateur avec le mail et le pass envoyé
		
		return utilisateurRepository.getUtilisateurWithPassAndMail(mail, pass);
		
	}
	
	public Optional<Utilisateur> getUtilisateur(String mail)
	{
		return utilisateurRepository.getUtilisateurWithMail(mail);
	}
	
	public Utilisateur saveUtilisateur(Utilisateur u) 
	{
		//Sauvegarder un Utilisateur
		return utilisateurRepository.save(u);
	}
}
