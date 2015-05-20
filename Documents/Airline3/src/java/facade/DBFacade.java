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
public class DBFacade implements DBInterface {

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
        Query query = em.createQuery("SELECT p FROM Passenger p WHERE p.reservationId = " + id);
        allReservations = query.getResultList();

        return allReservations;
    }

    public List<Plane> getPlane(Long id) {
        Query query = em.createQuery("SELECT p FROM Plane p WHERE p.id = " + id);
        return query.getResultList();
    }

    @Override
    public List<Flight> getAllFlights(String startAirport, Long departuredate) {
        List<Flight> allFlights = new ArrayList();
        Query query = em.createQuery("SELECT e FROM Flight e WHERE e.departuredate='" + departuredate + "'  AND e.depature='" + startAirport.toLowerCase() + "' ");
        allFlights = query.getResultList();
        return allFlights;
    }

    @Override
    public List<Flight> getAllFlights2(String startAirport, String endAirport, String date) {
        List<Flight> allFlights = new ArrayList();
        Query query = em.createQuery("SELECT e FROM Flight e WHERE e.destination='" + endAirport.toLowerCase() + "' AND e.depature='" + startAirport.toLowerCase() + "' AND e.departuredate>='" + date + "'");
        allFlights = query.getResultList();
        return allFlights;
    }

    @Override
    public void deleteReservation(Long id) {

        Query query = em.createQuery("DELETE FROM Reservation p WHERE p.reservationId = " + id);
//      
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();

    }

    @Override
    public boolean CreateReservation(Passenger[] passengerObjects, long flightID) {

        if (checkSeats(flightID, passengerObjects.length) == true) {
            System.out.println("True");

            Reservation r = new Reservation();
            r.setFlightId(flightID);
            r.setPassengerCount(Long.parseLong("" + passengerObjects.length));

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
                p.setReservationId(allReservations.get(allReservations.size() - 1).getId());

                em.getTransaction().begin();
                em.merge(p);
                em.getTransaction().commit();
            }

            return true;
        } else {
            return false;
        }

    }

    public boolean checkSeats(long FlightID, long seats) {
        List<Flight> allFlights = new ArrayList();
        List<Plane> allPlane = new ArrayList();

        Query query = em.createQuery("SELECT e FROM Flight e WHERE e.flightid='" + FlightID + "'");
        allFlights = query.getResultList();

        String planeId = allFlights.get(0).getFlightid().toString();
        String bookedSeats = allFlights.get(0).getBookedseats().toString();

        Query query1 = em.createQuery("SELECT p FROM Plane p WHERE p.id='" + planeId + "'");
        allPlane = query1.getResultList();
        String planeSeats = allPlane.get(0).getSeats();

        int fSeats = Integer.parseInt(bookedSeats) + Integer.parseInt("" + seats);
        int finalSeatsLeft = Integer.parseInt(planeSeats) - fSeats;
        System.out.println(fSeats);
        System.out.println(finalSeatsLeft);
        if (finalSeatsLeft >= 0) {
            em.getTransaction().begin();
            Query q1 = em.createQuery(
                    "UPDATE Flight e SET e.bookedseats = e.bookedseats + " + seats + " WHERE  e.flightid='" + FlightID + "'");
            q1.executeUpdate();
            em.getTransaction().commit();
            System.out.println("Ja");

            return true;

        } else {
            System.out.println("Nej");
            return false;
        }

    }

}
