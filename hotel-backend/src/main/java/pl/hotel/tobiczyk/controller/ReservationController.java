package pl.hotel.tobiczyk.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.hotel.tobiczyk.domain.dto.ReservationWriteView;
import pl.hotel.tobiczyk.domain.dto.SearchDto;
import pl.hotel.tobiczyk.domain.model.PaymentMethod;
import pl.hotel.tobiczyk.service.BookedDayService;
import pl.hotel.tobiczyk.service.ReservationService;
import pl.hotel.tobiczyk.service.UserService;

import javax.validation.Valid;

@Controller
class ReservationController {
    private BookedDayService bookedDaysService;
    private ReservationService reservationService;
    private UserService userService;

    public ReservationController(final BookedDayService bookedDaysService, final ReservationService reservationService, UserService userService) {
        this.bookedDaysService = bookedDaysService;
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @GetMapping("/search")
    public String getSearchPage(final Model model) {
        model.addAttribute("searchDto", new SearchDto());
        return "search";
    }
    @PostMapping("/search")
    public String getSearchResults(@ModelAttribute @Valid final SearchDto searchDto,
                                   final BindingResult result,
                                   final Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return getSearchPage(model);
        }
        model.addAttribute("searchResult", true);
        model.addAttribute("oldSearchDto", searchDto);
        model.addAttribute("availableRooms",
            bookedDaysService.getAvailableRooms(searchDto.getDateFrom(),searchDto.getDateTo()));
        model.addAttribute("numberOfNights", bookedDaysService.countNights(searchDto.getDateFrom(), searchDto.getDateTo()));
        return getSearchPage(model);
    }

    @PostMapping("/newReservation")
    public String getNewReservationForm(@AuthenticationPrincipal OidcUser oidcUser, @RequestParam("roomId") Long id, final Model model,
                                        @RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo,
                                        @RequestParam("totalValue") String totalValue) {

        final var user = userService.getUserDto(oidcUser);
        final var reservationDto = reservationService.getReservationWriteView(id, dateFrom, dateTo, totalValue);

        model.addAttribute("reservationDto", reservationDto);
        model.addAttribute("paymentMethods", PaymentMethod.values());
        model.addAttribute("reservation", new ReservationWriteView());
        model.addAttribute("oidcUser", user);
        return "newReservation";
    }

    @PostMapping("reserve")
    public String makeReservation(@AuthenticationPrincipal OidcUser oidcUser,
                                  @RequestParam("roomId") String roomId,
                                  final ReservationWriteView toCreate) {
        reservationService.makeReservation(roomId, toCreate, oidcUser);
        return "index";
    }

    @GetMapping("/panel/admin/reservations")
    public String showAllReservations(Model model) {
        model.addAttribute("reservations", reservationService.getAllReservations());
        return "reservations";
    }
}
