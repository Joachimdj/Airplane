/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author JoachimDittman
 */
@Entity
public class Flight   {
    
    @Id
    private Integer flightid; 
    private String AirlineName; 
    private String arrivaldate;
    private Long bookedseats;
    private String departuredate;
    private Long price;
    private String destination;
    private String depature;
    private Long planeid;

    public Flight(Integer flightid, String AirlineName, String arrivaldate, Long bookedseats, String departuredate, Long price, String destination, String  depature, Long planeid) {
        this.flightid = flightid;
        this.AirlineName = AirlineName;
        this.arrivaldate = arrivaldate;
        this.bookedseats = bookedseats;
        this.departuredate = departuredate;
        this.price = price;
        this.destination = destination;
        this.depature =  depature;
        this.planeid = planeid;
    }

    public Flight() {
      
    }

    public Integer getFlightid() {
        return flightid;
    }

    public void setFlightid(Integer flightid) {
        this.flightid = flightid;
    }

    public String getAirlineName() {
        return AirlineName;
    }

    public void setAirlineName(String AirlineName) {
        this.AirlineName = AirlineName;
    }

    public String getArrivaldate() {
        return arrivaldate;
    }

    public void setArrivaldate(String arrivaldate) {
        this.arrivaldate = arrivaldate;
    }

    public Long getBookedseats() {
        return bookedseats;
    }

    public void setBookedseats(Long bookedseats) {
        this.bookedseats = bookedseats;
    }

    public String getDeparturedate() {
        return departuredate;
    }

    public void setDeparturedate(String departuredate) {
        this.departuredate = departuredate;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepature() {
        return depature;
    }

    public void setDepature(String depature) {
        this.depature = depature;
    }

    public Long getPlaneid() {
        return planeid;
    }

    public void setPlaneid(Long planeid) {
        this.planeid = planeid;
    }


    
}