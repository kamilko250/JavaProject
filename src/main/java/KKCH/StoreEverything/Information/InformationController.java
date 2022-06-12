package KKCH.StoreEverything.Information;

import KKCH.StoreEverything.AppUser.AppUser;
import KKCH.StoreEverything.AppUser.AppUserService;
import KKCH.StoreEverything.AppUser.CustomUser;
import KKCH.StoreEverything.Category.CategoryDto;
import KKCH.StoreEverything.Category.CategoryOrm;
import KKCH.StoreEverything.Category.CategoryService;
import KKCH.StoreEverything.Enums.LoggerEnum;
import KKCH.StoreEverything.Utils.KKCHLogger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping("/information")
public class InformationController {
    private final Logger log = KKCHLogger.getLogger(LoggerEnum.INFORMATION);
    private InformationService informationService;
    private ModelMapper modelMapper;
    private CategoryService categoryService;
    private AppUserService userService;

    @GetMapping("/add")
    public String showInformationForm (Model model) {

        model.addAttribute("information", new InformationDto(new CategoryDto()));
        List<CategoryDto> categoryDtoList = categoryService.getAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();
        model.addAttribute("categories", categoryDtoList);
        log.finest("Presented add information form");
        return "add-information";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm (@PathVariable("id") Long id, Model model) {
        Optional<InformationOrm> information = informationService.getById(id);
        if (!information.isPresent()) {
            log.warning("Failed to find information for edit with id "+ id);
            return "redirect:/";
        }else{
            log.finer("Successfully found information with id "+id);
        }
        model.addAttribute("information", modelMapper.map(information.get(), InformationDto.class));
        List<CategoryDto> categoryDtoList = categoryService.getAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();
        model.addAttribute("categories", categoryDtoList);
        return "add-information";
    }

    @Transactional
    @PostMapping("/add")
    public String addInformation (@Valid @ModelAttribute("information") InformationDto information,
                                  BindingResult result,
                                  Model model,
                                  Authentication auth
    ) {
        if (result.hasErrors()) {
            log.info("Form has errors");
            return "add-information";
        }
        if (information.getId() == null)
            information.setAddDate(LocalDate.now());

        InformationOrm informationOrm = modelMapper.map(information, InformationOrm.class);

        CustomUser user = null;
        AppUser creator = null;
        if (auth != null) {
            user = (CustomUser) auth.getPrincipal();
            creator = this.userService.get(user.getId());

            informationOrm.setAppUser(creator);
            log.info("Successfully set information creator " + user.getUsername());
        }

        informationService.create(informationOrm);
        log.finest("Successfully created information by user " + user.getUsername());

        return "redirect:/";
    }

    @GetMapping("/list")
    public String getAllInformations (@RequestParam(name = "startDate", required = false) String startDate,
                                      @RequestParam(name = "category", required = false) String selectedCategory,
                                      @RequestParam(name = "endDate", required = false) String endDate,
                                      @RequestParam(name = "sort", required = false) String sort,
                                      Model model,
                                      HttpServletResponse response,
                                      @CookieValue(value = "sort", defaultValue = "date_asc") String sortCookie,
                                      Authentication auth
    ) {
        LocalDate StartDate = startDate == null || startDate.isEmpty()
                ? LocalDate.now()
                : LocalDate.parse(startDate);

        LocalDate EndDate = endDate == null || endDate.isEmpty()
                ? LocalDate.MAX
                : LocalDate.parse(endDate);

        CustomUser user = null;
        AppUser allowed = null;
        if (auth != null) {
            user = (CustomUser) auth.getPrincipal();
            allowed = userService.get(user.getId());
        }

        String finalSelectedCategory = selectedCategory;
        List<InformationOrm> informationOrms = informationService.getAll(allowed);
        CategoryOrm categoryOrm = null;
        if (selectedCategory != null && selectedCategory != "") {
            Optional<CategoryOrm> categoryOrm1 = categoryService.getAll()
                    .stream()
                    .filter(x -> x.getName()
                            .equals(finalSelectedCategory))
                    .findFirst();
            if (categoryOrm1.isPresent())
                categoryOrm = categoryOrm1.get();
        }

        List<InformationOrm> filteredInformationOrms = new ArrayList<>();

        for (var inf : informationOrms) {
            if (categoryOrm != null && !inf.getCategory()
                    .getId()
                    .equals(categoryOrm.getId())) // sortowanie po kategorii
                continue;
            if (startDate == null && endDate == null && inf.getAddDate()
                    .isBefore(StartDate)) // sortowanie po aktualnej dacie
                continue;
            ;
            if (startDate != null && !startDate.equals("") && inf.getAddDate()
                    .isBefore(StartDate)) // sortowanie po dacie startowej
                continue;
            if (endDate != null && !endDate.equals("") && inf.getAddDate()
                    .isAfter(EndDate))// sortowanie po dacie końcowej
                continue;
            if (!(inf.isUserAllowed(allowed) || inf.getAppUser()
                    .getId()
                    .equals(Objects.requireNonNull(user)
                                    .getId())))
                continue;

            filteredInformationOrms.add(inf);
        }

        List<InformationDto> informationDto = modelMapper.map(filteredInformationOrms,
                                                              new TypeToken<List<InformationDto>>() {
                                                              }.getType()
        );

        if (sort != null || sortCookie != null) {
            if (sort == null && sortCookie != null)
                sort = sortCookie;

            Cookie cookie = new Cookie("sort", sort);
            response.addCookie(cookie);

            switch (sort) {
                case "date_asc":
                    Collections.sort(filteredInformationOrms, Comparator.comparing(obj -> obj.getAddDate()));
                    break;
                case "date_dec":
                    Collections.sort(filteredInformationOrms, Comparator.comparing(obj -> obj.getAddDate()));
                    Collections.reverse(filteredInformationOrms);
                    break;
                case "category_asc":
                    Collections.sort(filteredInformationOrms, Comparator.comparing(obj -> obj.getCategory()
                            .getName()));
                    break;
                case "category_desc":
                    Collections.sort(filteredInformationOrms, Comparator.comparing(obj -> obj.getCategory()
                            .getName()));
                    Collections.reverse(filteredInformationOrms);
                    break;
                case "alphabetic_asc":
                    Collections.sort(filteredInformationOrms, Comparator.comparing(obj -> obj.getTitle()));
                    break;
                case "alphabetic_desc":
                    Collections.sort(filteredInformationOrms, Comparator.comparing(obj -> obj.getTitle()));
                    Collections.reverse(filteredInformationOrms);
                    break;
                default:
                    break;
            }
        }

        model.addAttribute("InformationList", filteredInformationOrms);

        List<CategoryDto> categoryDtoList = categoryService.getAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();

        selectedCategory = selectedCategory == null
                ? ""
                : selectedCategory;

        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("category", selectedCategory);
        model.addAttribute("end_date", endDate);
        model.addAttribute("start_date", startDate);
        model.addAttribute("sort", sort);
        return "get-all";
    }

    @GetMapping("/{id}")
    public String getInformationById (@PathVariable("id") Long id, Model model) {
        model.addAttribute("users", userService.getAll());
        Optional<InformationOrm> informationOrm = informationService.getById(id);
        if (informationOrm.isPresent()) {
            model.addAttribute("information", modelMapper.map(informationOrm.get(), InformationDto.class));
        }else{
            log.warning("Information with id " + id + " is missing");
        }
        return "get_by_id";
    }

    @PostMapping("/{id}/delete")
    public String deleteInformationById (@PathVariable("id") Long id) {
        informationService.DeleteById(id);

        return "redirect:/information/list";
    }

    @Autowired
    public void setInformationService (InformationService informationService) {
        this.informationService = informationService;
    }

    @Autowired
    public void setModelMapper (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setCategoryService (CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setUserService (AppUserService appUserService) {
        this.userService = appUserService;
    }

}