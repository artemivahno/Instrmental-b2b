package com.itacademy.jd2.ai.b2b.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.ai.b2b.dao.api.filter.CategoryFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;
import com.itacademy.jd2.ai.b2b.service.ICategoryService;
import com.itacademy.jd2.ai.b2b.service.IImageService;
import com.itacademy.jd2.ai.b2b.web.converter.CategoryFromDtoConverter;
import com.itacademy.jd2.ai.b2b.web.converter.CategoryToDtoConverter;
import com.itacademy.jd2.ai.b2b.web.dto.CategoryDTO;
import com.itacademy.jd2.ai.b2b.web.dto.list.ListDTO;
import com.itacademy.jd2.ai.b2b.web.dto.search.CategorySearchDTO;

@Controller
@RequestMapping(value = "/category")
public class CategoryController extends AbstractController<CategoryDTO, CategoryFilter> {

    private static final String SEARCH_FORM_MODEL = "searchFormModel";
    private static final String SEARCH_DTO = CategoryController.class.getSimpleName()
            + "_SEACH_DTO";

    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private CategoryFromDtoConverter fromDtoConverter;
    @Autowired
    private CategoryToDtoConverter toDtoConverter;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest req,
            @ModelAttribute(SEARCH_FORM_MODEL) CategorySearchDTO searchDto,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {
        final ListDTO<CategoryDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        if (req.getMethod().equalsIgnoreCase("get")) { // do not use emptyfinal
                                                       // payload which comes in
                                                       // case final of GET

            searchDto = getSearchDTO(req);
        } else {
            req.getSession().setAttribute(SEARCH_DTO, searchDto);
        }

        final CategoryFilter filter = new CategoryFilter();
        if (searchDto.getName() != null) {
            filter.setName(searchDto.getName());
        }

        if (searchDto.getPosition() != null) {
            filter.setPosition(searchDto.getPosition());
        }

        listDTO.setTotalCount(categoryService.getCount(filter));

        applySortAndOrder2Filter(listDTO, filter);
        final List<ICategory> entities = categoryService.find(filter);
        listDTO.setList(
                entities.stream().map(toDtoConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        models.put(SEARCH_FORM_MODEL, searchDto);
        return new ModelAndView("category.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final HashMap<String, Object> hashMap = new HashMap<>();
        final CategoryDTO dto = new CategoryDTO();
        hashMap.put("formModel", dto);
        loadComboboxesImages(hashMap);
        return new ModelAndView("category.edit", hashMap);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object save(@Valid @ModelAttribute("formModel") final CategoryDTO formModel,
            final BindingResult result) throws Exception {
        final Map<String, Object> hashMap = new HashMap<>();
        if (result.hasErrors()) {
            loadComboboxesImages(hashMap);
            hashMap.put("formModel", formModel);
            return new ModelAndView("category.edit", hashMap);
        } else {
            final ICategory entity = fromDtoConverter.apply(formModel);
            categoryService.save(entity);
            return "redirect:/category";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        categoryService.delete(id);
        return "redirect:/category";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(
            @PathVariable(name = "id", required = true) final Integer id) {
        final ICategory dbModel = categoryService.get(id);
        final CategoryDTO dto = toDtoConverter.apply(dbModel);
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);
        loadComboboxesImages(hashMap);
        return new ModelAndView("category.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(
            @PathVariable(name = "id", required = true) final Integer id) {
        final CategoryDTO dto = toDtoConverter.apply(categoryService.get(id));

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        loadComboboxesImages(hashMap);
        return new ModelAndView("category.edit", hashMap);
    }

    private CategorySearchDTO getSearchDTO(final HttpServletRequest req) {
        CategorySearchDTO searchDTO = (CategorySearchDTO) req.getSession()
                .getAttribute(SEARCH_DTO);
        if (searchDTO == null) {
            searchDTO = new CategorySearchDTO();
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }
        return searchDTO;
    }


    private void loadComboboxesImages(final Map<String, Object> hashMap) {
        final List<IImage> images = imageService.getAll();

        final Map<Integer, String> imageMap = images.stream()
                .collect(Collectors.toMap(IImage::getId, IImage::getName));

        hashMap.put("imagesChoices", imageMap);

    }
}
