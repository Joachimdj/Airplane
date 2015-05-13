/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import Entity.*; 
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Michael
 */
public class DBFacade implements DBInterface{

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA2");
    private EntityManager em = emf.createEntityManager();

    private static DBFacade instance = null;

    public DBFacade() {

    }
 

     @Override
    public Reservation getReservation(Long id) {
        Reservation found = em.find(Reservation.class, id);
        return found;
    }
    @Override
    public List<Flight> getAllFlights(String startAirport, Long departuredate) {
        List<Flight> allFlights = new ArrayList();
        Query query = em.createQuery("SELECT e FROM Flight e WHERE e.destination<='"+startAirport+"' AND e.departuredate>='"+departuredate+"'");
        allFlights = query.getResultList();
        return allFlights;
    }

    @Override
    public List<Flight> getAllFlights2(String startAirport,String endAirport,String date) {
        List<Flight> allFlights = new ArrayList();
        Query query = em.createQuery("SELECT e FROM Flight e WHERE e.destination='"+startAirport+"' AND e.endAirport='"+endAirport+"' AND e.departuredate LIKE '"+date+"'");
        allFlights = query.getResultList();
        return allFlights;
    }
    
    @Override
    public void deleteReservation(Long id){
         
        Query query = em.createQuery("DELETE FROM Reservation p WHERE p.reservationId = "+id);
//      
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
        
        
    }
    
  /*  @Override
    public Reservation CreateReservation(List<Passengers> passengerObjects, long flightID) {
         Query query = em.createQuery("SELECT f FROM Flight f WHERE f.flightid='"+flightID+"'");
         
      List<Flight> allFlights = query.getResultList();
        
       
        Reservation r = new Reservation();
         r.setFlightId(Long.parseLong(allFlights.get(0).toString()));
        r.setPassengerCollection(passengerObjects); 
        em.getTransaction().begin(); 
     //   em.merge(p);
        em.getTransaction().commit();
        return null;
    }
 */
 
 
}
