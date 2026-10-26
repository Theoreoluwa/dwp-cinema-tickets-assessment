package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.calculate.TicketCalculator;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.validate.PurchaseValidator;

public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */

    private final PurchaseValidator purchaseValidator;
    private final TicketCalculator ticketCalculator;

    private final TicketPaymentService ticketPaymentService;
    private final SeatReservationService seatReservationService;

    public TicketServiceImpl(TicketPaymentService ticketPaymentService, SeatReservationService seatReservationService){
        this.ticketPaymentService = ticketPaymentService;
        this.seatReservationService = seatReservationService;
        this.purchaseValidator = new PurchaseValidator();
        this.ticketCalculator = new TicketCalculator();
    }//end ctor




    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {

        purchaseValidator.validate(accountId, ticketTypeRequests);

        int totalPurchase = ticketCalculator.calculatePayment(ticketTypeRequests);
        int totalSeatsReserved = ticketCalculator.calculateSeatReservation(ticketTypeRequests);

        ticketPaymentService.makePayment(accountId, totalPurchase);
        seatReservationService.reserveSeat(accountId, totalSeatsReserved);
    }

}
