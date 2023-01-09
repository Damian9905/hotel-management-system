package pl.hotel.tobiczyk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.hotel.tobiczyk.domain.model.Room;

@Data
@AllArgsConstructor
public class RoomReadModel {
  private Long id;
  private String name;
  private String description;
  private Double price;
  private Integer numberOfPeople;

  public static RoomReadModel fromRoom(final Room room) {
    final var roomType = room.getRoomType();
    return new RoomReadModel(room.getId(), room.getDescription(), roomType.getDescription(), roomType.getPrice(), roomType.getNumberOfPeople());
  }
}
