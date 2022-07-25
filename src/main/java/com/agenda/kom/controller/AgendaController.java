package com.agenda.kom.controller;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.html.HTMLTableCaptionElement;

import com.agenda.kom.model.Evenement;
import com.agenda.kom.model.Utilisateur;
import com.agenda.kom.service.EvenementService;
import com.agenda.kom.service.UtilisateurService;


@Controller
public class AgendaController {

	
	@Autowired
	private UtilisateurService utilisateurService;
	
	@Autowired
	private EvenementService evenementService;
	

	
	static ArrayList<String> listMois = new ArrayList<>();
	
	@GetMapping("/")
	public String authentification() 
	{
		return "login";
	}
	
	
	@GetMapping("/s'inscrire")
	public String inscription() {
		return "register";
	}
	
	
	
	@PostMapping("/inscription")
	public String inscription(@ModelAttribute("user") Utilisateur user, @RequestParam String cpass, ModelMap modelmap) 
	{

		if(!user.getPassword().equals(cpass)) 
		{
			modelmap.put("erreur", "le mot de passe n'a pas été bien confirmé!!");
			return "register";
		}
		if(utilisateurService.getUtilisateur(user.getMail()).isPresent()) 
		{
			modelmap.put("erreur", "l'utilisateur existe déjà");
			return "register";
			
		}
		
		System.out.println("C'est bon: "+user.getPrenom());
		
		utilisateurService.saveUtilisateur(user);
		modelmap.put("result", "Inscription réussie!!");
		return "register";
	}
	
	
	
	@PostMapping("/connexion")
	public String connexion(@RequestParam String mail, @RequestParam String pass, ModelMap modelmap, HttpServletRequest request) 
	{
		
		//On vérifie si les identifiants sont faux
		if(!utilisateurService.getUtilisateur(mail, pass).isPresent()) 
		{
			modelmap.put("result", "echec");
			System.out.println("l'utilisateur n'existe pas");
			return "login";
			
		} else 
			
		{
			System.out.println("l'utilisateur existe");
			
			// On recupere toutes les données de l'utilisateur qui vient de se connecter
			Utilisateur u1  = utilisateurService.getUtilisateur(mail, pass).get();
			
			System.out.println("Je l'ai récupéré: "+u1.getPrenom());
			
			//if(evenements!=null)evenements.clear();
			ArrayList<Evenement> evenements1 = evenementService.refresh(u1.getId());
			
			ArrayList<Evenement> array = new ArrayList<>();
			
			
			String[] int_date = new String[3];
			// On recupere juste les evenements d'un mois et on les mets dans "array"
			
			for(Evenement event: evenements1) 
			{
				int_date = event.getDate().split("-");
				LocalDate local =LocalDate.of(Integer.parseInt(int_date[0]), Integer.parseInt(int_date[1]),Integer.parseInt(int_date[2]));
				  
				if(local.getMonth().toString().equalsIgnoreCase(LocalDate.now().getMonth().toString())) 
					{
						array.add(event); 
						
					}
				  
			}
			
			String[] mois = new DateFormatSymbols(Locale.ENGLISH).getMonths();
			
			if(listMois.size()>0) listMois.clear();
			for(int i=0; i<mois.length-1;i++) {
				listMois.add(mois[i]);
			}
			
			System.out.println("Taille des evenements: "+array.size());
			request.getSession().setAttribute("user", u1);
			request.getSession().setAttribute("evenements", evenements1);
			modelmap.put("events", array);
			modelmap.put("mois", LocalDate.now().getMonth().toString());
			modelmap.put("Listmois", listMois);

			return "index";
		}
	}
	

	@GetMapping("/consulter")
	public String consulter(ModelMap modelmap, HttpServletRequest request) 
	{
		
		
		if(request.getSession().getAttribute("evenements")==null || request.getSession().getAttribute("user") == null) return "error";
		
		Utilisateur u = (Utilisateur) request.getSession().getAttribute("user");
		
		ArrayList<Evenement>  evenements = evenementService.refresh(u.getId());
		
		ArrayList<Evenement> array = new ArrayList<>();
		
		
		String[] int_date = new String[3];
		// On recupere juste les evenements d'un mois et on les mets dans "array"
		
		for(Evenement event: evenements) 
		{
			int_date = event.getDate().split("-");
			LocalDate local =LocalDate.of(Integer.parseInt(int_date[0]), Integer.parseInt(int_date[1]),Integer.parseInt(int_date[2]));
			  
			if(local.getMonth().toString().equalsIgnoreCase(LocalDate.now().getMonth().toString())) 
				{
					array.add(event); 
					
				}
			  
		}
		
		System.out.println("Taille des evenements: "+array.size());
		request.getSession().setAttribute("evenements", evenements);
		modelmap.put("events", array); //On envoi la série d'évennement du mois courant 
		modelmap.put("mois", LocalDate.now().getMonth().toString());
		modelmap.put("Listmois", listMois);

		return "index";
		
		
		
	}
	
	
	
	@PostMapping("/getEvents")
	public String getEvenements(@RequestParam String mois, ModelMap modelmap, HttpServletRequest request) 
	{
		Utilisateur u = (Utilisateur) request.getSession().getAttribute("user");
		ArrayList<Evenement> evenements = evenementService.refresh(u.getId());
		
		ArrayList<Evenement> array = new ArrayList<>();
		
		
		String[] int_date = new String[3];
		// On recupere juste les evenements d'un mois et on les mets dans "array"
		
		for(Evenement event: evenements) 
		{
			int_date = event.getDate().split("-");
			LocalDate local =LocalDate.of(Integer.parseInt(int_date[0]), Integer.parseInt(int_date[1]),Integer.parseInt(int_date[2]));
			  
			if(local.getMonth().toString().equalsIgnoreCase(mois)) 
				{
					array.add(event); 
					
				}
			  
		}
		
		System.out.println("Taille des evenements: "+array.size());
		request.getSession().setAttribute("evenements", evenements);
		modelmap.put("events", array); //On envoi la série d'évennement du mois courant 
		modelmap.put("mois", mois);
		modelmap.put("Listmois", listMois);

		return "index";
	}
	
	@GetMapping("/ajouter")
	public String ajouterE(ModelMap modelmap, HttpServletRequest request) 
	{
		if(request.getSession().getAttribute("evenements")==null || request.getSession().getAttribute("user")==null) return "error";
		
		
		return "evenement";
	}
	
	@PostMapping("/addEvent")
	public String addEvent(@ModelAttribute("event")Evenement event, ModelMap modelmap, HttpServletRequest request) 
	{
		Utilisateur u = (Utilisateur) request.getSession().getAttribute("user");
		event.setId_U(u.getId());
		evenementService.enregistrerEvent(event);
		
		
		ArrayList<Evenement> evenements = evenementService.refresh(u.getId());
		
		
		modelmap.put("result", "L'evenement à été ajouté");
		request.getSession().setAttribute("evenements", evenements);
		return "evenement";
	}
	
	
	@GetMapping("/afficher")
	public String eventDescription(@RequestParam Long id, ModelMap modelmap, HttpServletRequest request) 
	{	
		if(request.getSession().getAttribute("evenements")==null || request.getSession().getAttribute("user")==null) return "error";
		
		Utilisateur u = (Utilisateur) request.getSession().getAttribute("user");
		
		if(!evenementService.getEvenementByIDAndIDu(id, u.getId()).isPresent()) return "error";
		
		Evenement e = new Evenement();
		LocalDate local = null;
		
		e = evenementService.getEvenementByIDAndIDu(id, u.getId()).get();
		String[] int_date = e.getDate().split("-");
		local =LocalDate.of(Integer.parseInt(int_date[0]), Integer.parseInt(int_date[1]),Integer.parseInt(int_date[2]));
		String date = local.getDayOfMonth()+" "+local.getMonth()+" "+local.getYear();
		
		modelmap.put("event", e);
		modelmap.put("date", date);
		
		return "description";

	}
	

	
	@GetMapping("/delete")
	public String deleteEvent(@RequestParam Long id, ModelMap modelmap, HttpServletRequest request) {
		
		if(request.getSession().getAttribute("evenements")==null) return "error";
		
		Utilisateur u = (Utilisateur) request.getSession().getAttribute("user");
		
		if(!evenementService.getEvenementByIDAndIDu(id, u.getId()).isPresent()) return "error";
		
		ArrayList<Evenement> evenements = evenementService.refresh(u.getId());
		
		for(Evenement event: evenements) 
		{
			if(event.getId()==id) 
			{
				evenementService.delete(id);
				evenements = evenementService.refresh(u.getId());
				
				ArrayList<Evenement> array = new ArrayList<>();
				String[] int_date = new String[3];
				
				// On recupere juste les evenements d'un mois et on les mets dans "array"
				for(Evenement e: evenements) 
				{
					int_date = e.getDate().split("-");
					LocalDate local =LocalDate.of(Integer.parseInt(int_date[0]), Integer.parseInt(int_date[1]),Integer.parseInt(int_date[2]));
					  
					if(local.getMonth().toString().equals(LocalDate.now().getMonth().toString())) 
					{  
						array.add(e); 
					}
					  
				 }
				
				request.getSession().setAttribute("evenements", evenements);
				 modelmap.put("event", array); //On envoi la série d'évennement du mois courant 
				 modelmap.put("mois", LocalDate.now().getMonth().toString());
				 modelmap.put("Listmois", listMois);
				
				return "index";
			}
		}
		
		return "error";
	}
	
	
	@GetMapping("/modifier")
	public String modif(@RequestParam Long id, ModelMap modelmap, HttpServletRequest request) 
	{
		if(request.getSession().getAttribute("evenements")==null) return "error";
		
		Utilisateur u = (Utilisateur) request.getSession().getAttribute("user");
		
		if(!evenementService.getEvenementByIDAndIDu(id, u.getId()).isPresent()) return "error";
		
		Evenement e = evenementService.getEvenementByIDAndIDu(id, u.getId()).get();
		
		modelmap.put("event", e);
		return "modifEvent";
	}
	
	@PostMapping("/update")
	public String updateEvent(@RequestParam Long id, @ModelAttribute("evenement") Evenement event,  ModelMap modelmap, HttpServletRequest request) {
		
		event.setId(id);
		Utilisateur u = (Utilisateur) request.getSession().getAttribute("user");
		event.setId_U(u.getId());
		
		Evenement savedEvent = evenementService.enregistrerEvent(event);
		
		String[] int_date = savedEvent.getDate().split("-");
		LocalDate local =LocalDate.of(Integer.parseInt(int_date[0]), Integer.parseInt(int_date[1]),Integer.parseInt(int_date[2]));
		
		modelmap.put("event", event);
		modelmap.put("date", local.getDayOfMonth()+" "+local.getMonth()+" "+local.getYear());
		modelmap.put("user", u);
		
		return "description";
	}
	
	@GetMapping("/deconnecter")
	public String deconnexion(HttpServletRequest request) {
		
		if(request.getSession().getAttribute("evenements")==null ||request.getSession().getAttribute("user")==null) return "error";
		
		request.getSession().setAttribute("evenements", null);
		request.getSession().setAttribute("user", null);
		return "login";
	}
	
	
	
}
