/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import Entity.Passenger;
import Entity.exceptions.NonexistentEntityException;
import facade.DBFacade;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joachimdittman
 */
public class Test {

    public static void main(String[] args) throws NonexistentEntityException, Exception {
        DBFacade facade = new DBFacade();

        //Testing Checking of seats in a plane
        long fID = 17;
        long seats = 20;
        long reservationId = 105;
        System.out.println(facade.checkSeats(fID, seats));

        System.out.println(facade.getReservation(reservationId));

    }

}
