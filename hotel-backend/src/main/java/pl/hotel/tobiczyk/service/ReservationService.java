package pl.hotel.tobiczyk.service;

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
public class ReservationService {
  private ReservationRepository reservationRepository;
  private BookedDayService bookedDayService;
  private PaymentService paymentService;
  private RoomService roomService;
  private UserService userService;

  public ReservationService(ReservationRepository reservationRepository, BookedDayService bookedDayService, PaymentService paymentService, RoomService roomService, UserService userService) {
    this.reservationRepository = reservationRepository;
    this.bookedDayService = bookedDayService;
    this.paymentService = paymentService;
    this.roomService = roomService;
    this.userService = userService;
  }

  public List<ReservationReadView> getAllReservations() {
    return mapToReservationReadView(reservationRepository.findAll().stream());
  }

  public List<ReservationReadView> getUserReservations(String userId) {
    return mapToReservationReadView(reservationRepository.findAllByCustomerId(userId).stream());
  }

  private List<ReservationReadView> mapToReservationReadView(Stream<Reservation> reservations) {
    return reservations
        .map(reservation -> ReservationReadView.builder()
            .id(reservation.getId())
            .roomName(reservation.getRoom().getName())
            .customerFirstName(userService.getUserProfile(reservation.getCustomerId()).getFirstName())
            .customerLastName(userService.getUserProfile(reservation.getCustomerId()).getLastName())
            .totalValue(reservation.getTotalValue())
            .paymentMethod(reservation.getPayment().getPaymentMethod())
            .dateFrom(reservation.getDateFrom())
            .dateTo(reservation.getDateTo())
            .numberOfPeople(reservation.getRoom().getRoomType().getNumberOfPeople())
            .build())
        .sorted((a,b) -> a.getDateFrom().isBefore(b.getDateFrom()) ? 1 : -1)
        .collect(Collectors.toList());
  }

  public ReservationWriteView getReservationWriteView(final Long roomId, final String dateFrom,
                                                      final String dateTo, final String totalValue) {
    return ReservationWriteView.builder()
        .roomId(roomId)
        .roomName(roomService.findRoomById(roomId).map(Room::getRoomType)
            .map(RoomType::getDescription).orElseThrow(NoSuchElementException::new))
        .dateFrom(dateFrom)
        .dateTo(dateTo)
        .numberOfPeople(roomService.findRoomById(roomId).map(Room::getRoomType)
            .map(RoomType::getNumberOfPeople).orElseThrow(NoSuchElementException::new))
        .totalValue(totalValue)
        .build();
  }

  @Transactional
  public Reservation makeReservation(final String roomId, final ReservationWriteView reservationWriteView, final OidcUser user) {
    Reservation reservation = Reservation.builder()
        .customerId(user.getName())
        .room(roomService.findRoomById(Long.valueOf(roomId)).orElseThrow(NoSuchElementException::new))
        .dateFrom(LocalDate.parse(reservationWriteView.getDateFrom()))
        .dateTo(LocalDate.parse(reservationWriteView.getDateTo()))
        .totalValue(Double.valueOf(reservationWriteView.getTotalValue()))
        .build();
    Payment payment = paymentService.createPayment(reservationWriteView.getPaymentMethod());
    reservation.setPayment(payment);
    reservationRepository.save(reservation);
    paymentService.save(payment);
    bookedDayService.blockRoom(new BlockRoomDto(
        Long.valueOf(roomId), LocalDate.parse(reservationWriteView.getDateFrom()), LocalDate.parse(reservationWriteView.getDateTo())
    ));
    return reservation;
  }
}
