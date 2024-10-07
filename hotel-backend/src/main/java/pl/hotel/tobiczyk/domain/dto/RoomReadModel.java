package pl.hotel.tobiczyk.domain.dto;
import pl.hotel.tobiczyk.domain.model.Room;

public record RoomReadModel(
    Long id,
    String name,
    String description,
    Double price,
    Integer numberOfPeople
) {
  public static RoomReadModel fromRoom(final Room room) {
    final var roomType = room.getRoomType();
    return new RoomReadModel(room.getId(), room.getDescription(), roomType.getDescription(), roomType.getPrice(), roomType.getNumberOfPeople());
  }
}
