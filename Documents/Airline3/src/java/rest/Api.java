/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import Entity.*;
import facade.DBFacade;
import Entity.exceptions.*;
import org.json.simple.JSONObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import com.google.gson.Gson; 
import com.google.gson.JsonObject;
import facade.DBInterface; 
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import org.json.simple.JSONArray;

/**
 * REST Web Service
 *
 * @author JoachimDittman
 */
@Path("flights")
public class Api {

    @Context
    private UriInfo context;
    private Object rand;
    private DBInterface facade = new DBFacade();

    private Gson gson = new Gson();

    /**
     * Creates a new instance of Api
     */
    public Api() throws NonexistentEntityException, Exception {
 

 
 
    }


    /**
     * Retrieves representation of an instance of api.Api
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{startAirport}/{departuredate}")
    @Produces("text/plain")
    public String getFlights(@PathParam("startAirport") String startAirport,@PathParam("departuredate") long departuredate) throws NonexistentEntityException {

    
        List<Flight> allFlights = facade.getAllFlights(startAirport,departuredate);
          
         if (allFlights.isEmpty()) { 
             errorHandling("1", "No available flights", "No available flights");
        } 
       
        return gson.toJson(allFlights);
    }
     
    @GET
    @Path("{startAirport}/{endAirport}/{date}")
    @Produces("text/plain")
    public String getFlights2(@PathParam("startAirport") String startAirport,@PathParam("endAirport") String endAirport,@PathParam("date") String date) throws NonexistentEntityException {
 
        List<Flight> allFlights = facade.getAllFlights2(startAirport,endAirport,date);
      
        if (allFlights.isEmpty()) { 
             errorHandling("1", "No available flights", "No available flights");
        }   
        return gson.toJson(allFlights);
        
        
    }

     
    @GET
    @Path("{reservationId}")
    @Produces("application/json")
    public String getSingleReservation(@PathParam("reservationId") long reservationId) throws NonexistentEntityException {

        Reservation p = facade.getReservation(reservationId);
        System.out.println();
        if (p == null) {
           
             errorHandling("1", "Invalid ReservationID", "Invalid ReservationID");
        }
        else
        
        {
        JSONObject reservation = new JSONObject();
reservation.put("ReservationId", p.getId());
reservation.put("PassengerCount", p.getPassengerCount());
reservation.put("FlightId", p.getFlightId()); 

 
            JSONArray passengersList = new JSONArray();
    List<Passenger>   passengers = facade.getPassengers(reservationId);
         for(int i = 0 ; i< passengers.size() ; i++){
            Passenger p1 = passengers.get(i);
     JSONObject passenger = new JSONObject();
        passenger.put("passengerId", p1.getId()); 
        passenger.put("reservationId", p1.getReservationId()); 
        passenger.put("firstName", p1.getFirstName()); 
        passenger.put("lastName", p1.getLastName()); 
        passenger.put("street", p1.getAddress()); 
        passenger.put("city", p1.getCity()); 
        passenger.put("country", p1.getCountry()); 
       passengersList.add(passenger);
                                                    } 
        reservation.put("Passengers", passengersList);
        
        
        
        return reservation.toString();
    
        
        }
        return null;
    }

    @POST  
    @Path("{flightID}")
    @Consumes("application/json")
    public String addReservation(String Passengers,@PathParam("flightID") long flightID) { 
       Passenger[] p = gson.fromJson(Passengers, Passenger[].class);
        String returnString = "";
        
       if(facade.CreateReservation(p, flightID)== true){
        if(Arrays.toString(p).isEmpty()){ 
            errorHandling("9", "Unknown error", "Reservation not created");
        }  
        else 
        { 
         returnString =    errorHandling("1", "Success", "Reservation complete"); 
        }
       
        }
       else
       { 
      returnString =   errorHandling("10", "Unknown error", "Not enough tickets"); 
       }  
      return returnString;
    }
    
    
    
    @DELETE
    @Path("delete/{reservationId}") 
    public void deleteReservation(@PathParam("reservationId") long reservationId) throws NonexistentEntityException {
         facade.deleteReservation(reservationId); 
    }
    
    @GET
    @Path("plane/{planeID}")
    @Produces("text/plain")
    public String getPlane(@PathParam("planeID") long planeID) throws NonexistentEntityException {

    
        List<Plane> allPlane = facade.getPlane(planeID);
          
         if (allPlane.isEmpty()) {
             errorHandling("1", "Error", "Invalid PlaneID"); 
        } 
       
        return gson.toJson(allPlane);
    }
     
    
    public String errorHandling(String code, String message, String description){
     JsonObject jo = new JsonObject();
            jo.addProperty("code",code);
            jo.addProperty("message", message);
            jo.addProperty("description", description); 
            return  jo.toString();
    
    }
}