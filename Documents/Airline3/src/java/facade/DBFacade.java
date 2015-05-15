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
    public List<Passenger> getPassengers(Long id) {
         List<Passenger> allReservations = new ArrayList();
     Query query = em.createQuery("SELECT p FROM Passenger p WHERE p.reservationId = "+id);
        allReservations = query.getResultList();
        
        return   allReservations;
    }
    @Override
    public List<Flight> getAllFlights(String startAirport, Long departuredate) {
        List<Flight> allFlights = new ArrayList();
        Query query = em.createQuery("SELECT e FROM Flight e WHERE e.departuredate='"+departuredate+"'  AND e.depature='"+startAirport.toLowerCase()+"' ");
        allFlights = query.getResultList();
        return allFlights;
    }

    @Override
  public List<Flight> getAllFlights2(String startAirport,String endAirport, String date) {
        List<Flight> allFlights = new ArrayList();
        Query query = em.createQuery("SELECT e FROM Flight e WHERE e.destination='"+endAirport.toLowerCase()+"' AND e.depature='"+startAirport.toLowerCase()+"' AND e.departuredate>='"+date+"'");
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
    
  @Override
    public Reservation CreateReservation(Passenger[] passengerObjects, long flightID) {
         
          
       
        Reservation r = new Reservation();
         r.setFlightId(flightID);
         r.setPassengerCount(Long.parseLong(""+passengerObjects.length));
         
          em.getTransaction().begin(); 
        em.merge(r);
          em.getTransaction().commit();
          
             List<Reservation> allReservations = new ArrayList();
        Query query = em.createQuery("SELECT r FROM Reservation r");
        allReservations = query.getResultList();
          for (Passenger passengerObject : passengerObjects) {
        Passenger p = new Passenger(); 
         p.setName(passengerObject.getName());
         p.setAddress(passengerObject.getAddress());
         p.setCity(passengerObject.getCity());
         p.setCountry(passengerObject.getCountry());
        p.setReservationId(allReservations.get(allReservations.size()-1).getId());
          
         em.getTransaction().begin(); 
          em.merge(p);
            em.getTransaction().commit();
        }
        
       
        return null;
    }
 @Override
    public Passenger CreatePassenger(Passenger passengerObject) {
         
         
        return null;
    }
 
 
}
