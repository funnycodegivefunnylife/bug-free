package bugfree.challenge.domain.entities;

import bugfree.challenge.domain.entities.idclasses.Uuid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Room {
  private Uuid id;
  private String name;
  private String description;
  private String location;
  private Integer capacity;
  private String createdBy;
  private String updatedBy;
  private Long createdAt;
  private Long updatedAt;
  private RoomType type;

  public enum RoomType {
    CONFERENCE_ROOM,
    MEETING_ROOM,
    WORKSPACE,
    LOUNGE
  }
}
