package bugfree.challenge.client_api.application.usecases;


import bugfree.challenge.client_api.application.dto.BookingRequestDto;
import bugfree.challenge.domain.entities.idclasses.UserId;
import bugfree.challenge.domain.entities.idclasses.Uuid;

public interface BookRoomUseCase {

    boolean bookRoom(UserId userId, BookingRequestDto bookingRequestDto);
}
