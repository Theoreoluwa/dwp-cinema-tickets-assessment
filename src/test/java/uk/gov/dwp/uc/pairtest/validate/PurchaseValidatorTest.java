package uk.gov.dwp.uc.pairtest.validate;

import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.AccountNotValidException;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PurchaseValidatorTest {

    private final PurchaseValidator validator = new PurchaseValidator();

    @Test
    void validateAcceptsValidAdultPurchase() {
        assertDoesNotThrow(
                () -> validator.validate(
                1L, new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 3)
        )

        );
    }

    @Test
    void shouldAcceptValidAdultChildInfantPurchase(){
        assertDoesNotThrow(
                () -> validator.validate(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),
                        new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
                        new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)
                )

        );
    }

    @Test
    void validateRejectionOfNullAccountId(){
        assertThrows(
                AccountNotValidException.class,
                () -> validator.validate(null, new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2))
        );
    }

    @Test
    void validateRejectionOfZeroAccountId(){
        assertThrows(AccountNotValidException.class,
                () -> validator.validate(0L, new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 3))
        );
    }

    @Test
    void validateRejectionOfNegativeAccountId(){
        assertThrows(AccountNotValidException.class,
                () -> validator.validate(-1L, new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 3))
        );
    }

    @Test
    void validateRejectsNullTicketRequestsArray() {
        assertThrows(
                InvalidPurchaseException.class,
                () -> validator.validate(1L, (TicketTypeRequest[]) null)
        );
    }

    @Test
    void validateRejectsEmptyTicketRequests() {
        assertThrows(
                InvalidPurchaseException.class,
                () -> validator.validate(1L)
        );
    }

    @Test
    void validateRejectsNullTicketRequest() {
        assertThrows(
                InvalidPurchaseException.class,
                () -> validator.validate(1L, (TicketTypeRequest) null)
        );
    }

    @Test
    void validateRejectsChildTicketPurchaseOnly() {
        assertThrows(
                InvalidPurchaseException.class,
                () -> validator.validate(1L, new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1))
        );
    }

    @Test
    void validateRejectsZeroTicketPurchase() {
        assertThrows(
                InvalidPurchaseException.class,
                () -> validator.validate(1L, new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 0))
        );
    }

    @Test
    void validateRejectionOfNullTicketType() {
        assertThrows(InvalidPurchaseException.class, () ->
                validator.validate(
                        1L,
                        new TicketTypeRequest(null, 1)
                )
        );
    }

    @Test
    void validateRejectsNZeroTicketQuantity() {
        assertThrows(
                InvalidPurchaseException.class,
                () -> validator.validate(1L, new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 0))
        );
    }

    @Test
    void validateRejectsNegativeTicketQuantity() {
        assertThrows(
                InvalidPurchaseException.class,
                () -> validator.validate(1L, new TicketTypeRequest(TicketTypeRequest.Type.ADULT, -1))
        );
    }

    @Test
    void validateRejectsMoreThan25Tickets() {
        TicketTypeRequest adult =
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 20);
        TicketTypeRequest child =
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 6);

        assertThrows(
                InvalidPurchaseException.class,
                () -> validator.validate(1L, adult, child)
        );
    }

    @Test
    void validateAcceptsExactly25Tickets() {
        assertDoesNotThrow(
                () -> validator.validate(1L, new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 25)));
    }

    @Test
    void validateRejectsChildTicketsWithoutAdult() {
        assertThrows(
                InvalidPurchaseException.class,
                () -> validator.validate(1L, new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1))
        );
    }

    @Test
    void validateRejectChildAndInfantWithoutAdult() {
        assertThrows(InvalidPurchaseException.class, () ->
                validator.validate(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1),
                        new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)
                )
        );
    }

    @Test
    void validateRejectMoreInfantsThanAdults() {
        assertThrows(InvalidPurchaseException.class, () ->
                validator.validate(
                        1L,
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1),
                        new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2)
                )
        );
    }

    @Test
    void validateRejectsInfantTicketsWithoutAdult() {
        assertThrows(
                InvalidPurchaseException.class,
                () -> validator.validate(1L, new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1))
        );
    }


}
