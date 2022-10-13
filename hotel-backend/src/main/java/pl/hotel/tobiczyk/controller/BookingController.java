package pl.hotel.tobiczyk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.hotel.tobiczyk.domain.dto.SearchDto;
import pl.hotel.tobiczyk.service.BookedDayService;

import javax.validation.Valid;

@Controller
class BookingController {
    private BookedDayService bookedDaysService;

    public BookingController(final BookedDayService bookedDaysService) {
        this.bookedDaysService = bookedDaysService;
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
}
