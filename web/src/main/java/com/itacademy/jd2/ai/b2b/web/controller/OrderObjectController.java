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

import com.itacademy.jd2.ai.b2b.dao.api.filter.OrderItemFilter;
import com.itacademy.jd2.ai.b2b.dao.api.filter.OrderObjectFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderItem;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderObject;
import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;
import com.itacademy.jd2.ai.b2b.dao.api.model.enums.UserRole;
import com.itacademy.jd2.ai.b2b.service.IOrderItemService;
import com.itacademy.jd2.ai.b2b.service.IOrderObjectService;
import com.itacademy.jd2.ai.b2b.service.IUserService;
import com.itacademy.jd2.ai.b2b.web.converter.OrderItemToDtoConverter;
import com.itacademy.jd2.ai.b2b.web.converter.OrderObjectFromDtoConverter;
import com.itacademy.jd2.ai.b2b.web.converter.OrderObjectToDtoConverter;
import com.itacademy.jd2.ai.b2b.web.dto.OrderItemDTO;
import com.itacademy.jd2.ai.b2b.web.dto.OrderObjectDTO;
import com.itacademy.jd2.ai.b2b.web.dto.list.ListDTO;
import com.itacademy.jd2.ai.b2b.web.dto.search.OrderObjectSearchDTO;
import com.itacademy.jd2.ai.b2b.web.security.AuthHelper;

@Controller
@RequestMapping(value = "/order")
public class OrderObjectController
        extends AbstractController<OrderObjectDTO, OrderObjectFilter> {

    private static final String SEARCH_FORM_MODEL = "searchFormModel";
    private static final String SEARCH_DTO = OrderObjectController.class.getSimpleName()
            + "_SEACH_DTO";

    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private OrderItemToDtoConverter itemsToDtoConverter;
    
    @Autowired
    private IOrderObjectService orderObjectService;

    @Autowired
    private IUserService userService;

    @Autowired
    private OrderObjectFromDtoConverter fromDtoConverter;

    @Autowired
    private OrderObjectToDtoConverter toDtoConverter;

    @RequestMapping(method = { RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest req,
            @ModelAttribute(SEARCH_FORM_MODEL) OrderObjectSearchDTO searchDto,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {
        final ListDTO<OrderObjectDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        if (req.getMethod().equalsIgnoreCase("get")) {
            // do not use empty payload which comes in case of GET
            searchDto = getSearchDTO(req);
        } else {
            req.getSession().setAttribute(SEARCH_DTO, searchDto);
        }

        final OrderObjectFilter filter = new OrderObjectFilter();

        if ((searchDto.getClose() != null) && searchDto.getClose()) {
            filter.setClose(Boolean.FALSE);
        } else {
            filter.setClose(null);
        }

        listDTO.setTotalCount(orderObjectService.getCount(filter));

        applySortAndOrder2Filter(listDTO, filter);

        final Integer loggedUserId = AuthHelper.getLoggedUserId();
        if (loggedUserId == null) {
            throw new IllegalArgumentException(
                    "this page can be accessed by logged in user only");
        }
        if (!AuthHelper.containsRole(UserRole.admin)) {
            filter.setUserId(loggedUserId);
        }

        final List<IOrderObject> entities = orderObjectService.find(filter);
        listDTO.setList(
                entities.stream().map(toDtoConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        models.put(SEARCH_FORM_MODEL, searchDto);
        return new ModelAndView("orderObject.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final HashMap<String, Object> hashMap = new HashMap<>();
        final OrderObjectDTO dto = new OrderObjectDTO();
        hashMap.put("formModel", dto);
        loadComboboxesUsers(hashMap);
        return new ModelAndView("orderObject.edit", hashMap);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("formModel") final OrderObjectDTO formModel,
            final BindingResult result) throws Exception {
        if (result.hasErrors()) {
            final Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("formModel", formModel);
            loadComboboxesUsers(hashMap);
            return "orderObject.edit";
        } else {
            final IOrderObject entity = fromDtoConverter.apply(formModel);
            orderObjectService.save(entity);
            return "redirect:/order";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        orderItemService.delete(id);
        return "redirect:/order";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(
            @PathVariable(name = "id", required = true) final Integer id) {
        final IOrderObject dbModel = orderObjectService.getFullinfo(id);
        final OrderObjectDTO dto = toDtoConverter.apply(dbModel);
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);
        final OrderItemFilter filter = new OrderItemFilter();
        filter.setOrderId(id);
        final List<IOrderItem> entities = orderItemService.find(filter);
        final List<OrderItemDTO> itemDtos = entities.stream().map(itemsToDtoConverter).collect(Collectors.toList());
        hashMap.put("orderItems", itemDtos);
        
        loadComboboxesUsers(hashMap);
        return new ModelAndView("orderObject.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(
            @PathVariable(name = "id", required = true) final Integer id) {
        final OrderObjectDTO dto = toDtoConverter.apply(orderObjectService.getFullinfo(id));

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        loadComboboxesUsers(hashMap);
        return new ModelAndView("orderObject.edit", hashMap);
    }

    private void loadComboboxesUsers(final Map<String, Object> hashMap) {
        final List<IUser> user = userService.getAll();

        final Map<Integer, String> userMap = user.stream()
                .collect(Collectors.toMap(IUser::getId, IUser::getName));

        hashMap.put("userChoices", userMap);

    }

    private OrderObjectSearchDTO getSearchDTO(final HttpServletRequest req) {
        OrderObjectSearchDTO searchDTO = (OrderObjectSearchDTO) req.getSession()
                .getAttribute(SEARCH_DTO);
        if (searchDTO == null) {
            searchDTO = new OrderObjectSearchDTO();
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }
        return searchDTO;
    }

}
