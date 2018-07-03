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

import com.itacademy.jd2.ai.b2b.dao.api.filter.AttributeFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IAttribute;
import com.itacademy.jd2.ai.b2b.service.IAttributeService;
import com.itacademy.jd2.ai.b2b.web.converter.AttributeFromDtoConverter;
import com.itacademy.jd2.ai.b2b.web.converter.AttributeToDtoConverter;
import com.itacademy.jd2.ai.b2b.web.dto.AttributeDTO;
import com.itacademy.jd2.ai.b2b.web.dto.list.ListDTO;
import com.itacademy.jd2.ai.b2b.web.dto.search.AttributeSearchDTO;

@Controller
@RequestMapping(value = "/attribute")
public class AttributeController
        extends AbstractController<AttributeDTO, AttributeFilter> {

    @Autowired
    private IAttributeService attributeService;
    @Autowired
    private AttributeFromDtoConverter fromDtoConverter;
    @Autowired
    private AttributeToDtoConverter toDtoConverter;

    private static final String SEARCH_FORM_MODEL = "searchFormModel";
    private static final String SEARCH_DTO = AttributeController.class.getSimpleName()
            + "_SEACH_DTO";

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest req,
            @ModelAttribute(SEARCH_FORM_MODEL) AttributeSearchDTO searchDto,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {
        final ListDTO<AttributeDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        if (req.getMethod().equalsIgnoreCase("get")) {
            // do not use empty payload which comes in case of GET
            searchDto = getSearchDTO(req);
        } else {
            req.getSession().setAttribute(SEARCH_DTO, searchDto);
        }

        final AttributeFilter filter = new AttributeFilter();
        if (searchDto.getName() != null) {
            filter.setName(searchDto.getName());
        }

        listDTO.setTotalCount(attributeService.getCount(filter));

        applySortAndOrder2Filter(listDTO, filter);
        final List<IAttribute> entities = attributeService.find(filter);
        listDTO.setList(
                entities.stream().map(toDtoConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        models.put(SEARCH_FORM_MODEL, searchDto);
        return new ModelAndView("attribute.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", new AttributeDTO());

        return new ModelAndView("attribute.edit", hashMap);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("formModel") final AttributeDTO formModel,
            final BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return "attribute.edit";
        } else {
            final IAttribute entity = fromDtoConverter.apply(formModel);
            attributeService.save(entity);
            return "redirect:/attribute";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        attributeService.delete(id);
        return "redirect:/attribute";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(
            @PathVariable(name = "id", required = true) final Integer id) {
        final IAttribute dbModel = attributeService.get(id);
        final AttributeDTO dto = toDtoConverter.apply(dbModel);
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);

        return new ModelAndView("attribute.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(
            @PathVariable(name = "id", required = true) final Integer id) {
        final AttributeDTO dto = toDtoConverter.apply(attributeService.get(id));

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);

        return new ModelAndView("attribute.edit", hashMap);
    }

    private AttributeSearchDTO getSearchDTO(final HttpServletRequest req) {
        AttributeSearchDTO searchDTO = (AttributeSearchDTO) req.getSession()
                .getAttribute(SEARCH_DTO);
        if (searchDTO == null) {
            searchDTO = new AttributeSearchDTO();
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }
        return searchDTO;
    }

}
