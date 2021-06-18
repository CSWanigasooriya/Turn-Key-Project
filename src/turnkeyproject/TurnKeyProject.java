/*
   MIT License

   Copyright (c) 2021 Chamath Wanigasooriya

   Permission is hereby granted, free of charge, to any person obtaining a copy
   of this software and associated documentation files (the "Software"), to deal
   in the Software without restriction, including without limitation the rights
   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
   copies of the Software, and to permit persons to whom the Software is
   furnished to do so, subject to the following conditions:

   The above copyright notice and this permission notice shall be included in all
   copies or substantial portions of the Software.

   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
   SOFTWARE.
 */
package turnkeyproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author chamath wanigasooriya
 */
public class TurnKeyProject {

//    public static void main(String[] args) {
//
//        //Get User ID and Name as CustomerID and Customer Name respectively from the login module.
//        Customer user = new Customer(0001, "Chamath Wanigasooriya");
//
//        TheaterA a = TheaterA.getInstance();
//        TheaterB b = TheaterB.getInstance();
//
//        a.setTheatreId("A0001"); //Change Default Theater ID
//        a.setNumberOfSeats(100); //Change Defualt Number of Seats
//        a.setSeatFee(115); //Change Defult Seat Price
//
//        b.setTheatreId("B0002"); //Change Default Theater ID
//        b.setNumberOfSeats(100); //Change Defualt Number of Seats
//        b.setSeatFee(95); //Change Defult Seat Price
//
//        int val = -1;
//        while (val != 0) {
//            Scanner sc = new Scanner(System.in);
//            System.out.println("Select Theater (Int): ");
//            System.out.println("1. Theater A \n2. Theater B");
//            int theater = sc.nextInt();
//
//            switch (theater) {
//                case 1 -> {
//                    System.out.println("Enter seat ID (Int): ");
//                    int seatId = sc.nextInt();
//                    System.out.println("=======================================================");
//                    Seat s = new Seat(seatId, user);
//                    a.bookSeat(s);
//                    Theater ta = new Theater(a);
//                    Theater tb = new Theater(b);
//                    ta.exectuteStratergy(user);
//                    tb.exectuteStratergy(user);
//                }
//                case 2 -> {
//                    System.out.println("Enter seat ID (Int): ");
//                    int seatId = sc.nextInt();
//                    System.out.println("=======================================================");
//                    Seat s = new Seat(seatId, user);
//                    b.bookSeat(s);
//                    Theater ta = new Theater(a);
//                    Theater tb = new Theater(b);
//                    ta.exectuteStratergy(user);
//                    tb.exectuteStratergy(user);
//                }
//                default ->
//                    val = 0;
//            }
//
//        }
//    }

}

//[Stratergy Pattern used since every theater has their own stratergy and conventions]
class Theater {

    private final TheaterStratergy stratergy;

    public Theater(TheaterStratergy ts) {
        this.stratergy = ts;
    }

    public void exectuteStratergy(Customer cus) {
        System.out.println("ID: " + this.stratergy.getTheatreId());
        System.out.println("Theater Name: " + this.stratergy.getTheatreName());
        System.out.println("Number of Seats Available: " + this.stratergy.getNumberofSeats());

        System.out.println("Seats booked by " + cus.getCusName() + ": ");
        this.stratergy.displayBookedSeatList();

        System.out.print("Total Fee for the seats: ");
        this.stratergy.displayTotalFee(cus);
        System.out.println("=======================================================");
    }
}

interface TheaterStratergy {

    public void setTheatreId(String id);

    public void setTheatreName(String name);

    public void setNumberOfSeats(int seats);

    public void setSeatFee(double price);

    public void bookSeat(Seat seat);

    public String getTheatreId();

    public String getTheatreName();

    public int getNumberofSeats();

    public void removeSeat(Seat seat);

    public void displayBookedSeatList();

    public void displayTotalFee(Customer cus);
}

//[Each Theater is a singleton class so that the same seat will not be booked twice]
class TheaterA implements TheaterStratergy {

    private String theaterId;
    private String theaterName;
    private int numberOfSeats;
    private double seatPrice;

    private final List<Seat> bookedSeatList = new ArrayList<Seat>();

    private static TheaterA instance = null;

    private TheaterA() {
        this.theaterId = "0001";
        this.theaterName = "Theater A"; //Default Name is Theater A.
        this.numberOfSeats = 100; //Default Number of seats in Theater A.
        this.seatPrice = 100; //Default Seat Price For Theater A.
    }

    public static TheaterA getInstance() {
        if (instance == null) {
            instance = new TheaterA();
        }

        return instance;
    }

    @Override
    public void setSeatFee(double price) {
        this.seatPrice = price;
    }

    @Override
    public void bookSeat(Seat seat) {
        if (this.bookedSeatList.size() < this.numberOfSeats && !isBooked(seat)) {
            bookedSeatList.add(seat);
        } else {
            System.out.println("\u001B[31m" + "****Seat Already Booked****" + "\u001B[0m");
            System.out.println("=======================================================");
        }
    }

    @Override
    public void removeSeat(Seat seat) {
        bookedSeatList.remove(seat);
    }

    @Override
    public void displayBookedSeatList() {
        bookedSeatList.forEach(seat -> {
            System.out.println("    Seat ID: " + seat.getSeatId());
        });
    }

    @Override
    public void displayTotalFee(Customer cus) {
        double val = 0;

        System.out.println("\u001B[32m" + val + "\u001B[0m");
    }

    @Override
    public void setNumberOfSeats(int seats) {
        this.numberOfSeats = seats;
    }

    @Override
    public void setTheatreId(String id) {
        this.theaterId = id;
    }

    @Override
    public void setTheatreName(String name) {
        this.theaterName = name;
    }

    @Override
    public String getTheatreId() {
        return this.theaterId;
    }

    @Override
    public String getTheatreName() {
        return this.theaterName;
    }

    @Override
    public int getNumberofSeats() {
        return this.numberOfSeats - bookedSeatList.size();
    }

    private boolean isBooked(Seat s) {
        return this.bookedSeatList.stream().anyMatch(seat -> (seat.getSeatId() == s.getSeatId()));
    }
}

class TheaterB implements TheaterStratergy {

    private String theaterId;
    private String theaterName;
    private int numberOfSeats;
    private double seatPrice;

    private final List<Seat> seatList = new ArrayList<Seat>();

    private static TheaterB instance = null;

    private TheaterB() {
        this.theaterId = "0001";
        this.theaterName = "Theater"; //Default Name is Theater A.
        this.numberOfSeats = 100; //Default Number of seats in Theater A.
        this.seatPrice = 100; //Default Seat Price For Theater A.
    }

    public static TheaterB getInstance() {
        if (instance == null) {
            instance = new TheaterB();
        }

        return instance;
    }

    @Override
    public void setSeatFee(double price) {
        this.seatPrice = price;
    }

    @Override
    public void bookSeat(Seat seat) {
        if (this.seatList.size() < this.numberOfSeats && !isBooked(seat)) {
            seatList.add(seat);
        } else {
            System.out.println("\u001B[31m" + "****Seat Already Booked****" + "\u001B[0m");
            System.out.println("=======================================================");
        }
    }

    @Override
    public void removeSeat(Seat seat) {
        seatList.remove(seat);
    }

    @Override
    public void displayBookedSeatList() {
        seatList.forEach(seat -> {
            System.out.println("    Seat ID: " + seat.getSeatId());
        });
    }

    @Override
    public void displayTotalFee(Customer cus) {
        double val = 0;

        System.out.println("\u001B[32m" + val + "\u001B[0m");
    }

    @Override
    public void setNumberOfSeats(int seats) {
        this.numberOfSeats = seats;
    }

    @Override
    public void setTheatreId(String id) {
        this.theaterId = id;
    }

    @Override
    public void setTheatreName(String name) {
        this.theaterName = name;
    }

    @Override
    public String getTheatreId() {
        return this.theaterId;
    }

    @Override
    public String getTheatreName() {
        return this.theaterName;
    }

    @Override
    public int getNumberofSeats() {
        return this.numberOfSeats - seatList.size();
    }

    //Returns true is seat is already in the list
    private boolean isBooked(Seat s) {
        return this.seatList.stream().anyMatch(seat -> (seat.getSeatId() == s.getSeatId()));
    }
}

//[Seat Fee Calculation using the decorator pattern to add up the commission for every seat]
interface Payments {

    public double fee();
}

class Commission implements Payments {

    @Override
    public double fee() {
        return 10;
    }

}

class SeatBooking extends Commission {

    @Override
    public double fee() {
        return super.fee() + 100;
    }

}

//[Model Class for seats]
class Seat {

    private int seatNumber;
    private Customer customer;

    Seat(int id, Customer cus) {
        this.seatNumber = id;
        this.customer = cus;
    }

    public void setSeatId(int id) {
        this.seatNumber = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getSeatId() {
        return seatNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

}

//[Customer Model]
class Customer {

    private int cusId;
    private String cusName;

    public Customer(int cusId, String cusName) {
        this.cusId = cusId;
        this.cusName = cusName;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public int getCusId() {
        return cusId;
    }

    public String getCusName() {
        return cusName;
    }

}

class Show {

}
