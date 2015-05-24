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
public class Flight {

    @Id
    private Integer flightid;
    private String airline;
    private String landingDate;
    private Long bookedseats;
    private String takeOffDate;
    private Long price;
    private String destination;
    private String depature;
    private Long planeid;

    public Flight(Integer flightid, String airline, String landingDate, Long bookedseats, String takeOffDate, Long price, String destination, String depature, Long planeid) {
        this.flightid = flightid;
        this.airline = airline;
        this.landingDate = landingDate;
        this.bookedseats = bookedseats;
        this.takeOffDate = takeOffDate;
        this.price = price;
        this.destination = destination;
        this.depature = depature;
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

    public String getairline() {
        return airline;
    }

    public void setairline(String airline) {
        this.airline = airline;
    }

    public String getLandingDate() {
        return landingDate;
    }

    public void setLandingDate(String landingDate) {
        this.landingDate = landingDate;
    }

    public Long getBookedseats() {
        return bookedseats;
    }

    public void setBookedseats(Long bookedseats) {
        this.bookedseats = bookedseats;
    }

    public String getTakeOffDate() {
        return takeOffDate;
    }

    public void setTakeOffDate(String takeOffDate) {
        this.takeOffDate = takeOffDate;
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
