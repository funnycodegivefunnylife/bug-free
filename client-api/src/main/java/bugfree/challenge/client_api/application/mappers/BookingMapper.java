package bugfree.challenge.client_api.application.mappers;

import org.springframework.stereotype.Component;

import bugfree.challenge.client_api.application.dto.BookingDto;
import bugfree.challenge.client_api.application.dto.BookingRequestDto;
import bugfree.challenge.client_api.application.usecases.UpdateBookingUseCase;
import bugfree.challenge.domain.entities.Booking;

@Component
public class BookingMapper {

  public BookingDto toDto(Booking booking) {
    BookingDto dto = new BookingDto();
    dto.setId(booking.getId().getValue());
    dto.setBookedById(booking.getBookedBy().getId().getValue());
    dto.setBookedByName(booking.getBookedBy().getFullName());
    dto.setRoomId(booking.getRoom().getId().getValue());
    dto.setRoomName(booking.getRoom().getName());
    dto.setStartTime(booking.getStartTime());
    dto.setEndTime(booking.getEndTime());
    dto.setDescription(booking.getDescription());
    dto.setStatus(booking.getStatus());
    dto.setBookingType(booking.getBookingType());
    dto.setBookingPurpose(booking.getBookingPurpose());
    dto.setBookingStatus(booking.getBookingStatus());
    dto.setBookingSource(booking.getBookingSource());
    dto.setBookingPriority(booking.getBookingPriority());
    dto.setBookingNotes(booking.getBookingNotes());
    dto.setCreatedAt(booking.getCreatedAt());
    dto.setUpdatedAt(booking.getUpdatedAt());
    return dto;
  }

  public UpdateBookingUseCase.UpdateBookingRequest toUpdateRequest(BookingRequestDto dto) {
    return new UpdateBookingUseCase.UpdateBookingRequest(
        dto.getDescription(),
        dto.getStartTime(),
        dto.getEndTime(),
        null, // bookingPurpose not in BookingRequestDto
        null // bookingNotes not in BookingRequestDto
        );
  }
}
