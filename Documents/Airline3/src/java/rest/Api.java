/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import Entity.*;
import facade.DBFacade;
import Entity.exceptions.*;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import com.google.gson.Gson; 
import facade.DBInterface; 
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;

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
        if (allFlights == null) {
            throw new NonexistentEntityException("404: No persons in database");
        } 
      
        String json = gson.toJson(allFlights);
        System.out.println(json);
        return json;
    }
     
    @GET
    @Path("{startAirport}/{endAirport}/{date}/{sdsd}")
    @Produces("text/plain")
    public String getFlights2(@PathParam("startAirport") String startAirport,@PathParam("endAirport") String endAirport,@PathParam("date") String date) throws NonexistentEntityException {
 
        List<Flight> allFlights = facade.getAllFlights2(startAirport,endAirport,date);
        if (allFlights == null) {
            throw new NonexistentEntityException("404: No persons in database");
        } 
      
        String json = gson.toJson(allFlights);
        System.out.println(json);
        return json;
    }
     
    @GET
    @Path("{reservationId}")
    @Produces("application/json")
    public String getSingleReservation(@PathParam("reservationId") long reservationId) throws NonexistentEntityException {

        Reservation p = facade.getReservation(reservationId);
        if (p == null) {
            throw new NonexistentEntityException("404: Reservation not found");
        }
        String json = gson.toJson(p);
        return json;
    }

    @POST  
    public String addReservation(String s) { 
        Reservation r = new Reservation();
      //   facade.createReservation(r);
        return "{\"result\" : \"Ok\"}";
    }
    
      @DELETE
      @Path("delete/{reservationId}") 
    public void deleteReservation(@PathParam("reservationId") long reservationId) throws NonexistentEntityException {
         facade.deleteReservation(reservationId);
    }
     
}