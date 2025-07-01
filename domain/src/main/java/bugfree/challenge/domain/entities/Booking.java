package bugfree.challenge.domain.entities;

import bugfree.challenge.domain.entities.idclasses.Uuid;
import lombok.Data;

@Data
public class Booking {
    private Uuid id;
    private User bookedBy;
    private Room room;
    private Long startTime;
    private Long endTime;
    private String description;
    private String status;
    private String createdBy;
    private String updatedBy;
    private Long createdAt;
    private Long updatedAt;
    private String bookingType;
    private String bookingPurpose;
    private String bookingStatus;
    private String bookingSource;
    private String bookingPriority;
    private String bookingNotes;
}
