package bugfree.challenge.client_api.application.usecases;

import bugfree.challenge.domain.entities.Booking;

public interface GetBookingUseCase {
    Booking execute(String bookingId);
}
