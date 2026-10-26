package uk.gov.dwp.uc.pairtest.calculate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketCalculatorTesting {

    private final TicketCalculator calculator = new TicketCalculator();
    @Test
    void calculatePaymentForAdultOnlyTicket(){
        TicketTypeRequest adult =
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 3);

        assertEquals(75, calculator.calculatePayment(adult));
    }

    @Test
    void calculatePaymentForMixedTickets(){
        TicketTypeRequest adult =
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest child =
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);
        TicketTypeRequest infant =
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);

        assertEquals(75, calculator.calculatePayment(adult, child, infant));
    }

    @Test
    void countTicketsReturnsCorrectNumberForRequestedType() {
        TicketTypeRequest adult =
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest child =
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 3);
        TicketTypeRequest infant =
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);

        assertEquals(
                5,
                calculator.countTickets(
                        new TicketTypeRequest[]{adult, child, infant},
                        TicketTypeRequest.Type.CHILD
                )
        );
    }

    @Test
    void countTicketsReturnsZeroWhenTypeIsNotPresent() {
        TicketTypeRequest child =
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2);

        assertEquals(
                0,
                calculator.countTickets(
                        new TicketTypeRequest[]{child},
                        TicketTypeRequest.Type.INFANT
                )
        );
    }

    @Test
    void calculateSeatReservationExcludesInfants() {
        TicketTypeRequest adult =
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest child =
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 3);
        TicketTypeRequest infant =
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 4);

        assertEquals(
                5,
                calculator.calculateSeatReservation(adult, child, infant)
        );
    }


}
