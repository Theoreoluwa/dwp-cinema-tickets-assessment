package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.AccountNotValidException;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TicketServiceImplTest {

    private TicketService ticketService;
    private TicketPaymentService ticketPaymentService;
    private SeatReservationService seatReservationService;

    @BeforeEach
    void setUp() {
        ticketPaymentService = mock(TicketPaymentService.class);
        seatReservationService = mock(SeatReservationService.class);

        ticketService = new TicketServiceImpl(
                ticketPaymentService,
                seatReservationService
        );
    }

    @Test
    void shouldProcessValidAdultPurchase() {
         ticketService.purchaseTickets(1L, new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2));

        verify(ticketPaymentService).makePayment(1L, 50);
        verify(seatReservationService).reserveSeat(1L, 2);
    }

    @Test
    void shouldProcessCorrectPaymentForValidAdultChildAndInfantPurchase() {
        ticketService.purchaseTickets(
                1L,
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)
        );
        verify(ticketPaymentService).makePayment(1L, 80);
        verify(seatReservationService).reserveSeat(1L, 4);
    }

    @Test
    void shouldRejectChildPurchaseWithoutAdultAndNotCallExternalServices() {
        assertThrows(
                InvalidPurchaseException.class,
                () -> ticketService.purchaseTickets(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1)
                )
        );
        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    @Test
    void shouldRejectPurchaseWhenAccountIdIsInvalid() {
        assertThrows(
                AccountNotValidException.class,
                () -> ticketService.purchaseTickets(
                        0L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1)
                )
        );
        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    @Test
    void shouldRejectMoreInfantsThanAdultsAndNotCallExternalServices() {
        assertThrows(InvalidPurchaseException.class, () ->
                ticketService.purchaseTickets(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1),
                        new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2)
                )
        );

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }


    @Test
    void shouldRejectPurchaseExceedingMaximumTicketLimit() {
        assertThrows(
                InvalidPurchaseException.class,
                () -> ticketService.purchaseTickets(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 26)
                )
        );

        verifyNoInteractions(ticketPaymentService, seatReservationService);
    }


}

