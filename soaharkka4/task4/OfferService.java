package com.demo3.task3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OfferService {

	List<Offers> lista;
	
	public OfferService() {
		// TODO Auto-generated constructor stub
	}

	public List<Offers> getAllOffers() {
		populate();
		return lista;
	}
	
	public void populate(){
		lista = new ArrayList<Offers>();
		lista.add(new Offers(30, "Firma1", "West"));
		lista.add(new Offers(20, "Firma2", "East"));
		lista.add(new Offers(25, "Firma3", "North"));
		lista.add(new Offers(24, "Firma4", "South"));
		lista.add(new Offers(22, "Firma5", "South"));
		lista.add(new Offers(21, "Firma6", "North"));
		lista.add(new Offers(28, "Firma7", "East"));
		lista.add(new Offers(23, "Firma8", "West"));
		lista.add(new Offers(27, "Firma9", "West"));
	}

	public Offers getCheapest() {
		populate();
		Collections.sort(lista);	   
		Offers cheapest = lista.get(0);
		return cheapest;
	}
	
	
	public List<Offers> postOffer(int price, String company, String region){
		populate();
		lista.add(new Offers(price, company, region));
		return lista;
	}

	public Offers getCheapest(String region) {
		populateWithCondition(region);
		Collections.sort(lista);	   
		Offers cheapest = lista.get(0);
		return cheapest;
	}
	
	public void populateWithCondition(String region){
		lista = new ArrayList<Offers>();
		switch (region) {
		case "west":
			lista.add(new Offers(30, "Firma1", "West"));
			lista.add(new Offers(23, "Firma8", "West"));
			lista.add(new Offers(27, "Firma9", "West"));
			break;
		
		case "east":
			lista.add(new Offers(20, "Firma2", "East"));
			lista.add(new Offers(28, "Firma7", "East"));
			break;
		
		case "north":
			lista.add(new Offers(25, "Firma3", "North"));
			lista.add(new Offers(21, "Firma6", "North"));
			break;
		
		case "south":
			lista.add(new Offers(24, "Firma4", "South"));
			lista.add(new Offers(22, "Firma5", "South"));
			break;
			
		default:
			throw new DataNotFoundException("Region "+region+" not found");
		}
	}

}
