package uk.gov.dwp.uc.pairtest.validate;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.AccountNotValidException;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class PurchaseValidator {
    private static final int maximumTicketsPerPurchase = 25;

    public void validate(Long id, TicketTypeRequest... ticketTypeRequests){
        validateAccount(id);
        ticketRequestValidation(ticketTypeRequests);

        int adultTickets = ticketsCount(ticketTypeRequests, TicketTypeRequest.Type.ADULT);
        int childTickets = ticketsCount(ticketTypeRequests, TicketTypeRequest.Type.CHILD);
        int infantTickets = ticketsCount(ticketTypeRequests, TicketTypeRequest.Type.INFANT);
        
        int totalTickets = adultTickets + childTickets + infantTickets;

        validateTotalTickets(totalTickets);
        adultRequirementValidation(adultTickets, childTickets, totalTickets);
    }


    private void validateAccount(Long id){
        if(id == null || id <= 0){
            throw new AccountNotValidException("Account ID must be greater than 0");
        }
    }

    private void validateTotalTickets(int totalTickets){
        if(totalTickets > maximumTicketsPerPurchase){
            throw new InvalidPurchaseException("Total tickets cannot exceed maximum number of tickets (25)");
        }
    }

    private int ticketsCount(TicketTypeRequest[] ticketTypeRequests, TicketTypeRequest.Type type) {
        int totalTickets = 0;

        for(TicketTypeRequest ticketTypeRequest : ticketTypeRequests){
            if(ticketTypeRequest.getTicketType() == type){
                totalTickets += ticketTypeRequest.getNoOfTickets();
            }
        }
        return totalTickets;
    }

    private void adultRequirementValidation(int adultTickets, int childTickets, int infantTickets){
        boolean hasInfantOrChild = childTickets > 0 || infantTickets > 0;

        if(hasInfantOrChild && adultTickets == 0){
            throw new InvalidPurchaseException("A child or infant ticket must be accompanied by an adult ticket");
        }

        if (infantTickets > adultTickets){
            throw new InvalidPurchaseException("A child or infant ticket must be accompanied by an adult ticket");
        }
    }

    private void ticketRequestValidation(TicketTypeRequest... ticketTypeRequests){
        if (ticketTypeRequests == null || ticketTypeRequests.length == 0){
            throw new InvalidPurchaseException("Minimum of one ticket requests is required");
        }

        for (TicketTypeRequest request : ticketTypeRequests) {
            if (request == null){
                throw new InvalidPurchaseException("Ticket request cannot be null");
            }

            if (request.getTicketType() == null){
                throw new InvalidPurchaseException("Ticket type cannot be null");
            }

            if (request.getNoOfTickets() < 0){
                throw new InvalidPurchaseException("Number of tickets must be greater than 0");
            }
        }
    }


}
