package bugfree.challenge.client_api.application.usecases;

import bugfree.challenge.domain.entities.Booking;

public interface UpdateBookingUseCase {

  record UpdateBookingRequest(
      String description,
      Long startTime,
      Long endTime,
      String bookingPurpose,
      String bookingNotes) {}

  Booking execute(String bookingId, UpdateBookingRequest request);
}
