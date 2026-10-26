package uk.gov.dwp.uc.pairtest.calculate;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

public class TicketCalculator {

    private static final int ADULT_PRICE = 25;
    private static final int CHILD_PRICE = 15;

    public int calculatePayment(TicketTypeRequest... ticketTypes) {
        int adultTickets = countTickets(ticketTypes, TicketTypeRequest.Type.ADULT);
        int childTickets = countTickets(ticketTypes, TicketTypeRequest.Type.CHILD);

        return (adultTickets * ADULT_PRICE)
                + (childTickets * CHILD_PRICE);
    }

    private int countTickets(TicketTypeRequest[] ticketTypes, TicketTypeRequest.Type type) {
        int count = 0;

        for (TicketTypeRequest ticketTypeRequest : ticketTypes) {
            if(ticketTypeRequest.getTicketType() == type) {
                count += ticketTypeRequest.getNoOfTickets();
            }//end-if
        }//end-for

        return count;
    }

    public int calculateSeatReservation(TicketTypeRequest... ticketTypes) {
        int adultTickets = countTickets(ticketTypes, TicketTypeRequest.Type.ADULT);
        int childTickets = countTickets(ticketTypes, TicketTypeRequest.Type.CHILD);

        return adultTickets + childTickets;
    }
}
