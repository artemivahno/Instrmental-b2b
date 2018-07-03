package com.itacademy.jd2.ai.b2b.web.controller;

import java.util.HashMap;
import java.util.List;
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

import com.itacademy.jd2.ai.b2b.dao.api.filter.ImageFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;
import com.itacademy.jd2.ai.b2b.service.IImageService;
import com.itacademy.jd2.ai.b2b.web.converter.ImageFromDtoConverter;
import com.itacademy.jd2.ai.b2b.web.converter.ImageToDtoConverter;
import com.itacademy.jd2.ai.b2b.web.dto.ImageDTO;
import com.itacademy.jd2.ai.b2b.web.dto.list.ListDTO;
import com.itacademy.jd2.ai.b2b.web.dto.search.ImageSearchDTO;

@Controller
@RequestMapping(value = "/image")
public class ImageController extends AbstractController<ImageDTO, ImageFilter> {

    private static final String SEARCH_FORM_MODEL = "searchFormModel";
    private static final String SEARCH_DTO = ImageController.class.getSimpleName()
            + "_SEACH_DTO";

    @Autowired
    private IImageService imageService;

    @Autowired
    private ImageFromDtoConverter fromDtoConverter;

    @Autowired
    private ImageToDtoConverter toDtoConverter;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest req,
            @ModelAttribute(SEARCH_FORM_MODEL) ImageSearchDTO searchDto,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {
        final ListDTO<ImageDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        if (req.getMethod().equalsIgnoreCase("get")) {
            // do not use empty payload which comes in case of GET
            searchDto = getSearchDTO(req);
        } else {
            req.getSession().setAttribute(SEARCH_DTO, searchDto);
        }

        final ImageFilter filter = new ImageFilter();
        if (searchDto.getName() != null) {
            filter.setName(searchDto.getName());
        }

        listDTO.setTotalCount(imageService.getCount(filter));

        applySortAndOrder2Filter(listDTO, filter);
        final List<IImage> entities = imageService.find(filter);
        listDTO.setList(
                entities.stream().map(toDtoConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        models.put(SEARCH_FORM_MODEL, searchDto);
        return new ModelAndView("image.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", new ImageDTO());

        return new ModelAndView("image.edit", hashMap);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("formModel") final ImageDTO formModel,
            final BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return "image.edit";
        } else {
            final IImage entity = fromDtoConverter.apply(formModel);
            imageService.save(entity);
            return "redirect:/image";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        imageService.delete(id);
        return "redirect:/image";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(
            @PathVariable(name = "id", required = true) final Integer id) {
        final IImage dbModel = imageService.get(id);
        final ImageDTO dto = toDtoConverter.apply(dbModel);
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);

        return new ModelAndView("image.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(
            @PathVariable(name = "id", required = true) final Integer id) {
        final ImageDTO dto = toDtoConverter.apply(imageService.get(id));

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        
        return new ModelAndView("image.edit", hashMap);
    }

    private ImageSearchDTO getSearchDTO(final HttpServletRequest req) {
        ImageSearchDTO searchDTO = (ImageSearchDTO) req.getSession()
                .getAttribute(SEARCH_DTO);
        if (searchDTO == null) {
            searchDTO = new ImageSearchDTO();
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }
        return searchDTO;
    }

}
