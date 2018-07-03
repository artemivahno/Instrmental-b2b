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

import com.itacademy.jd2.ai.b2b.dao.api.filter.BrandFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;
import com.itacademy.jd2.ai.b2b.service.IBrandService;
import com.itacademy.jd2.ai.b2b.service.IImageService;
import com.itacademy.jd2.ai.b2b.web.converter.BrandFromDtoConverter;
import com.itacademy.jd2.ai.b2b.web.converter.BrandToDtoConverter;
import com.itacademy.jd2.ai.b2b.web.dto.BrandDTO;
import com.itacademy.jd2.ai.b2b.web.dto.list.ListDTO;
import com.itacademy.jd2.ai.b2b.web.dto.search.BrandSearchDTO;

@Controller
@RequestMapping(value = "/brand")
public class BrandController extends AbstractController<BrandDTO, BrandFilter> {

    @Autowired
    private IBrandService brandService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private BrandFromDtoConverter fromDtoConverter;
    @Autowired
    private BrandToDtoConverter toDtoConverter;
    
    private static final String SEARCH_FORM_MODEL = "searchFormModel";
    private static final String SEARCH_DTO = BrandController.class.getSimpleName()
            + "_SEACH_DTO";

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest req,
            @ModelAttribute(SEARCH_FORM_MODEL) BrandSearchDTO searchDto,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {
        final ListDTO<BrandDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        if (req.getMethod().equalsIgnoreCase("get")) {
            searchDto = getSearchDTO(req);
        } else {
            req.getSession().setAttribute(SEARCH_DTO, searchDto);
        }

        final BrandFilter filter = new BrandFilter();
        if (searchDto.getName() != null) {
            filter.setName(searchDto.getName());
        }

        listDTO.setTotalCount(brandService.getCount(filter));

        applySortAndOrder2Filter(listDTO, filter);
        final List<IBrand> entities = brandService.find(filter);
        listDTO.setList(
                entities.stream().map(toDtoConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        models.put(SEARCH_FORM_MODEL, searchDto);
        return new ModelAndView("brand.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", new BrandDTO());
        loadComboboxesImages(hashMap);
        return new ModelAndView("brand.edit", hashMap);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("formModel") final BrandDTO formModel,
            final BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return "brand.edit";
        } else {
            final IBrand entity = fromDtoConverter.apply(formModel);
            brandService.save(entity);
            return "redirect:/brand";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        brandService.delete(id);
        return "redirect:/brand";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(
            @PathVariable(name = "id", required = true) final Integer id) {
        final IBrand dbModel = brandService.get(id);
        final BrandDTO dto = toDtoConverter.apply(dbModel);
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);
        loadComboboxesImages(hashMap);
        return new ModelAndView("brand.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(
            @PathVariable(name = "id", required = true) final Integer id) {
        final BrandDTO dto = toDtoConverter.apply(brandService.get(id));

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        loadComboboxesImages(hashMap);
        return new ModelAndView("brand.edit", hashMap);
    }

    private void loadComboboxesImages(final Map<String, Object> hashMap) {
        final List<IImage> images = imageService.getAll();

        final Map<Integer, String> imageMap = images.stream()
                .collect(Collectors.toMap(IImage::getId, IImage::getName));

        hashMap.put("imagesChoices", imageMap);

    }

    private BrandSearchDTO getSearchDTO(final HttpServletRequest req) {
        BrandSearchDTO searchDTO = (BrandSearchDTO) req.getSession()
                .getAttribute(SEARCH_DTO);
        if (searchDTO == null) {
            searchDTO = new BrandSearchDTO();
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }
        return searchDTO;
    }
}
