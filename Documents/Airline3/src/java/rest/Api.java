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
              JsonObject jo = new JsonObject();
            jo.addProperty("code","1");
            jo.addProperty("message", " No available flights");
            jo.addProperty("description", "No available flights"); 
            return  jo.toString();
        } 
       
        return gson.toJson(allFlights);
    }
     
    @GET
    @Path("{startAirport}/{endAirport}/{date}")
    @Produces("text/plain")
    public String getFlights2(@PathParam("startAirport") String startAirport,@PathParam("endAirport") String endAirport,@PathParam("date") String date) throws NonexistentEntityException {
 
        List<Flight> allFlights = facade.getAllFlights2(startAirport,endAirport,date);
      
        if (allFlights.isEmpty()) {
               JsonObject jo = new JsonObject();
            jo.addProperty("code","1");
            jo.addProperty("message", " No available flights");
            jo.addProperty("description", "No available flights"); 
            return  jo.toString();
        }   
        else
        {
        return gson.toJson(allFlights);
        }
    }

     
    @GET
    @Path("{reservationId}")
    @Produces("application/json")
    public String getSingleReservation(@PathParam("reservationId") long reservationId) throws NonexistentEntityException {

        Reservation p = facade.getReservation(reservationId);
        System.out.println();
        if (p == null ) {
                JsonObject jo = new JsonObject();
            jo.addProperty("code","1");
            jo.addProperty("message", "Invalid ReservationID");
            jo.addProperty("description", "Invalid ReservationID"); 
            return  jo.toString();
        }
        else{
        JSONObject reservation = new JSONObject();
reservation.put("ReservationId", p.getId());
reservation.put("PassengerCount", p.getPassengerCount());
reservation.put("FlightId", p.getFlightId()); 

 
            JSONArray passengersList = new JSONArray();
    List<Passenger>   passengers = facade.getPassengers(reservationId);
         for(int i = 0 ; i< passengers.size() ; i++){
            Passenger p1 = passengers.get(i);
     JSONObject passenger = new JSONObject();
        passenger.put("PassengerId", p1.getId()); 
        passenger.put("ReservationId", p1.getReservationId()); 
        passenger.put("Name", p1.getName()); 
        passenger.put("Address", p1.getAddress()); 
        passenger.put("City", p1.getCity()); 
        passenger.put("Country", p1.getCountry()); 
       passengersList.add(passenger);
                                                    } 
         
        JSONObject mainObj = new JSONObject();
        mainObj.put("Reservation", reservation);
        mainObj.put("Passengers", passengersList);
        
        return mainObj.toString();
    
        
        }
    }

    @POST  
    @Path("{flightID}")
    @Consumes("application/json")
    public String addReservation(String Passengers,@PathParam("flightID") long flightID) { 
       Passenger[] p = gson.fromJson(Passengers, Passenger[].class);
        
        facade.CreateReservation(p, flightID);
        if(Arrays.toString(p).isEmpty()){
                JsonObject jo = new JsonObject();
            jo.addProperty("code","9");
            jo.addProperty("message", "Unknown error");
            jo.addProperty("description", "Reservation not created"); 
            return  jo.toString();
        } else {
            return  Arrays.toString(p);
        }
        
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
              JsonObject jo = new JsonObject();
            jo.addProperty("code","1");
            jo.addProperty("message", " Invalid PlaneID");
            jo.addProperty("description", "Invalid PlaneID"); 
            return  jo.toString();
        } 
       
        return gson.toJson(allPlane);
    }
     
}