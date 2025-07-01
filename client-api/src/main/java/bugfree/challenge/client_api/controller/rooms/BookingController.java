package bugfree.challenge.client_api.controller.rooms;

import bugfree.challenge.client_api.application.dto.BookingDto;
import bugfree.challenge.client_api.application.dto.BookingRequestDto;
import bugfree.challenge.client_api.application.mappers.BookingMapper;
import bugfree.challenge.client_api.application.usecases.*;
import bugfree.challenge.domain.entities.Booking;
import bugfree.challenge.domain.entities.idclasses.UserId;
import bugfree.challenge.client_api.utils.SecurityUtils;
import bugfree.challenge.domain.entities.idclasses.Uuid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Booking management operations
 */
@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Booking Management", description = "Operations for managing room bookings")
public class BookingController {

    private final BookRoomUseCase bookRoomUseCase;
    private final GetBookingUseCase getBookingUseCase;
    private final ListBookingsUseCase listBookingsUseCase;
    private final UpdateBookingUseCase updateBookingUseCase;
    private final CancelBookingUseCase cancelBookingUseCase;
    private final BookingMapper bookingMapper;

    public BookingController(
            BookRoomUseCase bookRoomUseCase,
            GetBookingUseCase getBookingUseCase,
            ListBookingsUseCase listBookingsUseCase,
            UpdateBookingUseCase updateBookingUseCase,
            CancelBookingUseCase cancelBookingUseCase,
            BookingMapper bookingMapper) {
        this.bookRoomUseCase = bookRoomUseCase;
        this.getBookingUseCase = getBookingUseCase;
        this.listBookingsUseCase = listBookingsUseCase;
        this.updateBookingUseCase = updateBookingUseCase;
        this.cancelBookingUseCase = cancelBookingUseCase;
        this.bookingMapper = bookingMapper;
    }

    @PostMapping
    @Operation(summary = "Create a new booking", description = "Books a room for the specified time period")
    @ApiResponse(responseCode = "201", description = "Booking created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid booking request")
    @ApiResponse(responseCode = "409", description = "Room already booked for this time period")
    public ResponseEntity<String> createBooking(@Valid @RequestBody BookingRequestDto bookingRequestDto) {

        UserId userIdObj = SecurityUtils.getCurrentUserIdAsObject();
        boolean success = bookRoomUseCase.bookRoom(userIdObj, bookingRequestDto);

        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Booking created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Unable to create booking - room may be unavailable");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID", description = "Retrieves a booking by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Booking found")
    @ApiResponse(responseCode = "404", description = "Booking not found")
    public ResponseEntity<BookingDto> getBookingById(
            @Parameter(description = "Booking ID") @PathVariable String id) {
        Booking booking = getBookingUseCase.execute(id);
        BookingDto bookingDto = bookingMapper.toDto(booking);
        return ResponseEntity.ok(bookingDto);
    }

    @GetMapping
    @Operation(summary = "List bookings", description = "Retrieves a paginated list of bookings with optional filters")
    @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully")
    public ResponseEntity<ListBookingsResponse> listBookings(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Filter by user ID") @RequestParam(required = false) UserId userId,
            @Parameter(description = "Filter by room ID") @RequestParam(required = false) Uuid roomId) {

        ListBookingsUseCase.ListBookingsRequest request = new ListBookingsUseCase.ListBookingsRequest(page, size, userId, roomId);
        ListBookingsUseCase.ListBookingsResponse response = listBookingsUseCase.execute(request);

        List<BookingDto> bookingDtos = response.bookings().stream()
            .map(bookingMapper::toDto)
            .toList();

        ListBookingsResponse apiResponse = new ListBookingsResponse(
            bookingDtos,
            response.totalCount(),
            response.page(),
            response.size(),
            response.totalPages()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/user/me")
    @Operation(summary = "List bookings by user", description = "Retrieves all bookings for a specific user")
    @ApiResponse(responseCode = "200", description = "User bookings retrieved successfully")
    public ResponseEntity<List<BookingDto>> getBookingsByUser() {
        UserId userId = SecurityUtils.getCurrentUserIdAsObject();
        List<Booking> bookings = listBookingsUseCase.executeByUser(userId);
        List<BookingDto> bookingDtos = bookings.stream()
            .map(bookingMapper::toDto)
            .toList();
        return ResponseEntity.ok(bookingDtos);
    }

    @GetMapping("/room/{roomId}")
    @Operation(summary = "List bookings by room", description = "Retrieves all bookings for a specific room")
    @ApiResponse(responseCode = "200", description = "Room bookings retrieved successfully")
    public ResponseEntity<List<BookingDto>> getBookingsByRoom(
            @Parameter(description = "Room ID") @PathVariable String roomId) {
        List<Booking> bookings = listBookingsUseCase.executeByRoom(roomId);
        List<BookingDto> bookingDtos = bookings.stream()
            .map(bookingMapper::toDto)
            .toList();
        return ResponseEntity.ok(bookingDtos);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update booking", description = "Updates booking details")
    @ApiResponse(responseCode = "200", description = "Booking updated successfully")
    @ApiResponse(responseCode = "404", description = "Booking not found")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<BookingDto> updateBooking(
            @Parameter(description = "Booking ID") @PathVariable String id,
            @Valid @RequestBody BookingRequestDto bookingRequestDto) {

        UpdateBookingUseCase.UpdateBookingRequest request = bookingMapper.toUpdateRequest(bookingRequestDto);
        Booking booking = updateBookingUseCase.execute(id, request);
        BookingDto bookingDto = bookingMapper.toDto(booking);
        return ResponseEntity.ok(bookingDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel booking", description = "Cancels a booking by its ID")
    @ApiResponse(responseCode = "204", description = "Booking cancelled successfully")
    @ApiResponse(responseCode = "404", description = "Booking not found")
    public ResponseEntity<Void> cancelBooking(
            @Parameter(description = "Booking ID") @PathVariable String id) {
        cancelBookingUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @Getter
    public record ListBookingsResponse(List<BookingDto> bookings, long totalCount, int page, int size, int totalPages) {
    }
}
