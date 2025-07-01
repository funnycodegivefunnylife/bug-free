package bugfree.challenge.client_api.application.dto;

import lombok.Data;

@Data
public class BookingDto {
    private String id;
    private String bookedById;
    private String bookedByName;
    private String roomId;
    private String roomName;
    private Long startTime;
    private Long endTime;
    private String description;
    private String status;
    private String bookingType;
    private String bookingPurpose;
    private String bookingStatus;
    private String bookingSource;
    private String bookingPriority;
    private String bookingNotes;
    private Long createdAt;
    private Long updatedAt;
}
