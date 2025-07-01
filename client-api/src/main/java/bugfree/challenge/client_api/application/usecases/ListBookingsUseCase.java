package bugfree.challenge.client_api.application.usecases;

import bugfree.challenge.domain.entities.Booking;
import bugfree.challenge.domain.entities.idclasses.UserId;
import bugfree.challenge.domain.entities.idclasses.Uuid;

import java.util.List;
import java.util.UUID;

public interface ListBookingsUseCase {

    record ListBookingsRequest(int page, int size, UserId userId, Uuid roomId) {}

    record ListBookingsResponse(List<Booking> bookings, long totalCount, int page, int size, int totalPages) {}

    ListBookingsResponse execute(ListBookingsRequest request);

    List<Booking> executeByUser(UserId userId);

    List<Booking> executeByRoom(String roomId);
}
