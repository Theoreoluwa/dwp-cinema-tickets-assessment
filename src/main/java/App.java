import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.TicketService;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.AccountNotValidException;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class App {

    public static void main(String[] args) {
        TicketService ticketService = new TicketServiceImpl(new TicketPaymentServiceImpl(), new SeatReservationServiceImpl());

        try {
            ticketService.purchaseTickets(
                    3L,
                    new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 0),
                    new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1),
                    new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)
            );

            System.out.println("Tickets purchased successfully and seats reserved.");
        }//end try

        catch (AccountNotValidException ex) {
            System.out.println("Invalid account, unable to verify. " + ex.getMessage());
        }
        catch (InvalidPurchaseException ex) {
            System.out.println("Invalid purchase, unable to verify. " + ex.getMessage());
        }
    }//end main
}
