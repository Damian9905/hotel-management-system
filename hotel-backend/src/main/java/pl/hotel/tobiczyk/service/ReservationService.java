package pl.hotel.tobiczyk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import pl.hotel.tobiczyk.domain.dto.BlockRoomDto;
import pl.hotel.tobiczyk.domain.dto.ReservationReadView;
import pl.hotel.tobiczyk.domain.dto.ReservationWriteView;
import pl.hotel.tobiczyk.domain.model.Payment;
import pl.hotel.tobiczyk.domain.model.Reservation;
import pl.hotel.tobiczyk.domain.model.Room;
import pl.hotel.tobiczyk.domain.model.RoomType;
import pl.hotel.tobiczyk.repository.ReservationRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ReservationService {
  private final ReservationRepository reservationRepository;
  private final BookedDayService bookedDayService;
  private final PaymentService paymentService;
  private final RoomService roomService;
  private final UserService userService;

  public List<ReservationReadView> getAllReservations() {
    return mapToReservationReadView(reservationRepository.findAll().stream());
  }

  public List<ReservationReadView> getUserReservations(String userId) {
    return mapToReservationReadView(reservationRepository.findAllByCustomerId(userId).stream());
  }

  private List<ReservationReadView> mapToReservationReadView(Stream<Reservation> reservations) {
    return reservations
        .map(reservation -> ReservationReadView.builder()
            .id(reservation.id())
            .roomName(reservation.room().name())
            .customerFirstName(userService.getUserProfile(reservation.customerId()).getFirstName())
            .customerLastName(userService.getUserProfile(reservation.customerId()).getLastName())
            .totalValue(reservation.totalValue())
            .paymentMethod(reservation.payment().paymentMethod())
            .dateFrom(reservation.dateFrom())
            .dateTo(reservation.dateTo())
            .numberOfPeople(reservation.room().roomType().numberOfPeople())
            .build())
        .sorted((a,b) -> a.dateFrom().isBefore(b.dateFrom()) ? 1 : -1)
        .collect(Collectors.toList());
  }

  public ReservationWriteView getReservationWriteView(final Long roomId, final String dateFrom,
                                                      final String dateTo, final String totalValue) {
    return ReservationWriteView.builder()
        .roomId(roomId)
        .roomName(roomService.findRoomById(roomId).map(Room::roomType)
            .map(RoomType::description).orElseThrow(NoSuchElementException::new))
        .dateFrom(dateFrom)
        .dateTo(dateTo)
        .numberOfPeople(roomService.findRoomById(roomId).map(Room::roomType)
            .map(RoomType::numberOfPeople).orElseThrow(NoSuchElementException::new))
        .totalValue(totalValue)
        .build();
  }

  @Transactional
  public Reservation makeReservation(final String roomId, final ReservationWriteView reservationWriteView, final OidcUser user) {
    Reservation reservation = Reservation.builder()
        .customerId(user.getName())
        .room(roomService.findRoomById(Long.valueOf(roomId)).orElseThrow(NoSuchElementException::new))
        .dateFrom(LocalDate.parse(reservationWriteView.dateFrom()))
        .dateTo(LocalDate.parse(reservationWriteView.dateTo()))
        .totalValue(Double.valueOf(reservationWriteView.totalValue()))
        .build();
    Payment payment = paymentService.createPayment(reservationWriteView.paymentMethod());
    reservationRepository.save(reservation);
    paymentService.save(payment);
    bookedDayService.blockRoom(new BlockRoomDto(
        Long.valueOf(roomId), LocalDate.parse(reservationWriteView.dateFrom()), LocalDate.parse(reservationWriteView.dateTo())
    ));
    return reservation;
  }

  public void cancelReservation(final Long id) {
    reservationRepository.deleteById(id);
    paymentService.cancelPayment(id);
  }
}
