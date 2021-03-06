/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import Entity.*;
import Entity.Reservation;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface DBInterface {

    public Reservation getReservation(Long id);

    public List<Flight> getAllFlights(String startAirport, Long departuredate);

    public List<Flight> getAllFlights2(String startAirport, String endAirport, String date);

    public void deleteReservation(Long id);

    public boolean CreateReservation(Passenger[] passengerObjects, long flightID);

    public List<Plane> getPlane(Long id);

    public boolean checkSeats(long FlightID, long seats);

    public List<Passenger> getPassengers(Long id);
 

}
