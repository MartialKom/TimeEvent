package com.agenda.kom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agenda.kom.model.Evenement;


@Repository
public interface EvenementRepository extends CrudRepository<Evenement, Long> {

	@Query("from Evenement e where e.id_U=?1")
	public Iterable<Evenement> getEventByIdUser(Long id_U);
	
	@Query("from Evenement e where e.id_U=?1 and e.id=?2")
	public Optional<Evenement> getEventByIdUserAndIDEvent(Long id_U, Long id_E);
	
}
