package com.agenda.kom.service;


import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.kom.model.Evenement;

import com.agenda.kom.repository.EvenementRepository;



@Service
public class EvenementService {

	@Autowired
	private EvenementRepository evenementRepository;
	
	
	public Optional<Evenement> getEvenementByID(Long id)
	{
		return evenementRepository.findById(id);
	}
	
	
	public Optional<Evenement> getEvenementByIDAndIDu(Long idE, Long idU)
	{
		return evenementRepository.getEventByIdUserAndIDEvent(idU, idE);
	}
	
	
	public Iterable<Evenement> getEvenement(Long idUtilisateur)
	{
		return evenementRepository.getEventByIdUser(idUtilisateur);
	}
	
	public Evenement Update(Long id, String description) 
	{
		//On recupere l'evenement qui existe,
		Evenement e = evenementRepository.findById(id).get();
		//on modifie la description
		e.setDescription(description);
		//et on fait un save pour qu'il soit écrasé
		return evenementRepository.save(e);
	}
	
	public void delete(Long id) 
	{
		//Suppression d'un evennement
		evenementRepository.deleteById(id);
	}
	
	public  ArrayList<Evenement> refresh(Long idU)
	{
		ArrayList<Evenement> array = new ArrayList<>();
		
		Iterable<Evenement> events = this.getEvenement(idU);
		
		for(Evenement e : events) 
		{
			/*
			 * String[] int_date = e.getDate().split("-"); LocalDate local =
			 * LocalDate.of(Integer.parseInt(int_date[0]), Integer.parseInt(int_date[1]),
			 * Integer.parseInt(int_date[2]));
			 */
			
			array.add(e);
		}
		
		return array;
	}
	
	public Evenement enregistrerEvent(Evenement e) 
	{
		return evenementRepository.save(e);
	}
	
	
}
