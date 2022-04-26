package KKCH.StoreEverything.Information;

import KKCH.StoreEverything.Category.CategoryDto;
import KKCH.StoreEverything.Category.CategoryOrm;
import KKCH.StoreEverything.Category.CategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/information")
public class InformationController {
    private InformationService informationService;
    private ModelMapper modelMapper;
    private CategoryService categoryService;

    @GetMapping("/add")
    public String showInformationForm( Model model) {

        model.addAttribute("information", new InformationDto(new CategoryDto()));
        List<CategoryDto> categoryDtoList = categoryService.getAll()
                .stream()
                .map(category->modelMapper.map(category,CategoryDto.class))
                .toList();
        model.addAttribute("categories", categoryDtoList);
        return "add-information";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm (@PathVariable("id") Long id ,Model  model) {
        Optional<InformationOrm> information = informationService.getById(id);
        if(!information.isPresent())
            return "redirect:/";
        model.addAttribute("information", modelMapper.map(information.get(),InformationDto.class));
        List<CategoryDto> categoryDtoList = categoryService.getAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();
        model.addAttribute("categories", categoryDtoList);
        return "add-information";
    }

    @Transactional
    @PostMapping("/add")
    public String addInformation (@Valid @ModelAttribute("information")  InformationDto information, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-information";
        }
        if(information.getId() == null)
          information.setAddDate(LocalDate.now());

        InformationOrm informationOrm = modelMapper.map(information,InformationOrm.class);

        informationService.create(informationOrm);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String getAllInformations(@RequestParam(name = "startDate",required = false) String startDate, @RequestParam(name = "category", required = false) String selectedCategory,
                                     @RequestParam(name="endDate", required = false) String endDate, @RequestParam(name="sort", required = false ,defaultValue = "date_asc") String sort, Model  model){
        startDate = startDate == null
                ? LocalDate.now()
                .toString()
                : startDate;

        List<InformationDto> informationDto = modelMapper.map(informationService.getAll(), new TypeToken<List<InformationDto>>() {}.getType());

        model.addAttribute("InformationList", informationDto);

        List<CategoryDto> categoryDtoList = categoryService.getAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();

        selectedCategory = selectedCategory == null
                ? ""
                : selectedCategory;

        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("category", selectedCategory);
        model.addAttribute("end_date", endDate );
        model.addAttribute("start_date", startDate);
        model.addAttribute("sort", sort );
        return "get-all";
    }

    @GetMapping("/{id}")
    public String getInformationById(@PathVariable("id") Long id, Model model) {
        Optional<InformationOrm> informationOrm = informationService.getById(id);
        if(informationOrm.isPresent())
            model.addAttribute("information", modelMapper.map(informationOrm.get(), InformationDto.class));

        return "get_by_id";
    }

    @PostMapping("/{id}/delete")
    public String deleteInformationById(@PathVariable("id") Long id){
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
}