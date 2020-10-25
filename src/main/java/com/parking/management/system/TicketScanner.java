package com.parking.management.system;

public  interface TicketScanner {

    boolean scanTicket(String number);

    boolean processPayment();
}
