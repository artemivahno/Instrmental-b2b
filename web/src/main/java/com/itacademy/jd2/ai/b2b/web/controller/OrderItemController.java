package com.itacademy.jd2.ai.b2b.web.controller;

import java.util.ArrayList;
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
import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;
import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;
import com.itacademy.jd2.ai.b2b.service.IOrderItemService;
import com.itacademy.jd2.ai.b2b.service.IOrderObjectService;
import com.itacademy.jd2.ai.b2b.service.IProductService;
import com.itacademy.jd2.ai.b2b.service.IUserService;
import com.itacademy.jd2.ai.b2b.web.converter.OrderItemFromDtoConverter;
import com.itacademy.jd2.ai.b2b.web.converter.OrderItemToDtoConverter;
import com.itacademy.jd2.ai.b2b.web.dto.OrderItemDTO;
import com.itacademy.jd2.ai.b2b.web.dto.list.ListDTO;
import com.itacademy.jd2.ai.b2b.web.dto.search.OrderItemSearchDTO;
import com.itacademy.jd2.ai.b2b.web.security.AuthHelper;

@Controller
@RequestMapping(value = "/orderItemMain")
public class OrderItemController
        extends AbstractController<OrderItemDTO, OrderItemFilter> {

    private static final String SEARCH_FORM_MODEL = "searchFormModel";
    private static final String SEARCH_DTO = OrderItemController.class.getSimpleName()
            + "_SEACH_DTO";

    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private OrderItemFromDtoConverter fromDtoConverter;
    @Autowired
    private OrderItemToDtoConverter toDtoConverter;

    @Autowired
    private IProductService productService;
    @Autowired
    private IOrderObjectService orderObjectService;
    @Autowired
    private IUserService userService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest req,
            @ModelAttribute(SEARCH_FORM_MODEL) OrderItemSearchDTO searchDto,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {
        final ListDTO<OrderItemDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        if (req.getMethod().equalsIgnoreCase("get")) {
            // do not use empty payload which comes in case of GET
            searchDto = getSearchDTO(req);
        } else {
            req.getSession().setAttribute(SEARCH_DTO, searchDto);
        }

        final OrderItemFilter filter = new OrderItemFilter();

        if (searchDto.getQuantity() != null) {
            filter.setQuantity(searchDto.getQuantity());
        }

        listDTO.setTotalCount(orderItemService.getCount(filter));

        applySortAndOrder2Filter(listDTO, filter);
        final List<IOrderItem> entities = orderItemService.find(filter);
        final List<String> listNames = new ArrayList<String>();
        for (final IOrderItem iOrderItem : entities) {
            final IOrderObject orderObject = orderObjectService.getFullinfo(iOrderItem.getOrderObject().getId());
            listNames.add(orderObject.getCreator().getName());
        }
       
        listDTO.setList(
                entities.stream().map(toDtoConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        models.put("listNames", listNames);
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        models.put(SEARCH_FORM_MODEL, searchDto);
        return new ModelAndView("orderItemMain.list", models);
    }

    private OrderItemSearchDTO getSearchDTO(final HttpServletRequest req) {
        OrderItemSearchDTO searchDTO = (OrderItemSearchDTO) req.getSession()
                .getAttribute(SEARCH_DTO);
        if (searchDTO == null) {
            searchDTO = new OrderItemSearchDTO();
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }
        return searchDTO;
    }
    
    @RequestMapping(value = "/item/add", method = RequestMethod.POST)
    public String addItem(
            @Valid @ModelAttribute("formModel") final OrderItemDTO formModel,
            final BindingResult result) {
        /*if (result.hasErrors()) {
            final Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("formModel", formModel);
            return "product.edit";
        } */
            final OrderObjectFilter objectFilter = new OrderObjectFilter();
            final IUser user = userService.createEntity();
            user.setId(AuthHelper.getLoggedUserId());
            objectFilter.setUserId(user.getId());
            objectFilter.setClose(false);
            final List<IOrderObject> orderObjects = orderObjectService.find(objectFilter);
            IOrderObject orderObject;
            if (orderObjects.size() < 1) {
                orderObject = orderObjectService.createEntity();
                orderObject.setCreator(user);
                orderObject.setClose(false);
                
                orderObjectService.save(orderObject);
            } else {
                orderObject = orderObjects.get(0);
            }

            final IOrderItem entity = fromDtoConverter.apply(formModel);
            entity.setOrderObject(orderObject);
            orderItemService.save(entity);
            return "redirect:/product/list4buy";
        
    }
    
    @RequestMapping(value = "/item/close", method = RequestMethod.POST)
    public String closeOrder(
            @Valid @ModelAttribute("formModel") final OrderItemDTO formModel,
            final BindingResult result) {
            
            final OrderObjectFilter objectFilter = new OrderObjectFilter();
            final IUser user = userService.createEntity();
            user.setId(AuthHelper.getLoggedUserId());
            objectFilter.setUserId(user.getId());
            objectFilter.setClose(false);
            final List<IOrderObject> orderObjects = orderObjectService.find(objectFilter);
            IOrderObject orderObject;
                        
            if (orderObjects.size() < 1) {
                /*orderObject = orderObjectService.createEntity();
                orderObject.setCreator(user);
                orderObject.setClose(true);
                
                orderObjectService.save(orderObject);*/
                return "redirect:/product/list4buy";
            } else {
                orderObject = orderObjects.get(0);
                orderObject.setClose(true);
            }
            orderObjectService.save(orderObject);
            return "redirect:/product/list4buy";
        
    }
    
    
    
    @RequestMapping(value = "/item/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        orderItemService.delete(id);
        return "redirect:/orderItemMain";
    }
    

    private void loadCommonFormMOrderItems(final Map<String, Object> hashMap) {
        final List<IProduct> products = productService.getAll();
        
        final Map<Integer, String> productMap = productService.getAll().stream()
                .collect(Collectors.toMap(IProduct::getId, IProduct::getName));
        hashMap.put("productChoices", productMap);
        
        final Map<Integer, Double> productMap1 = productService.getAll().stream()
                .collect(Collectors.toMap(IProduct::getId, IProduct::getPrice));
        hashMap.put("productPrice", productMap1);
        
    }
    
    @RequestMapping(value = "/{productId}/add", method = RequestMethod.GET)
    public ModelAndView addItem(
            @PathVariable(name = "productId", required = true) final Integer productId) {
        
        final HashMap<String, Object> hashMap = new HashMap<>();
        final OrderItemDTO dto = new OrderItemDTO();
        dto.setProductId(productId);
        hashMap.put("formModel", dto);
        loadCommonFormMOrderItems(hashMap);
        return new ModelAndView("orderItemmain.edit", hashMap);
    }

    
   /* @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final HashMap<String, Object> hashMap = new HashMap<>();
        final OrderItemDTO dto = new OrderItemDTO();
        hashMap.put("formModel", dto);
        loadCommonFormMOrderItems(hashMap);
        return new ModelAndView("orderItemMain.edit", hashMap);
    }*/

    /*@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("formModel") final OrderItemDTO formModel,
            final BindingResult result) {
        if (result.hasErrors()) {
            final Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("formModel", formModel);
            loadCommonFormMOrderItems(hashMap);
            return "orderItem.edit";
        } else {
            final IOrderItem entity = fromDtoConverter.apply(formModel);
            orderItemService.save(entity);
            return "redirect:/orderItemMain";
        }
    }*/


    /*@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(
            @PathVariable(name = "id", required = true) final Integer id) {
        final IOrderItem dbModel = orderItemService.get(id);
        final OrderItemDTO dto = toDtoConverter.apply(dbModel);
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);
        loadCommonFormMOrderItems(hashMap);
        return new ModelAndView("orderItemMain.edit", hashMap);
    }*/

    /*@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(
            @PathVariable(name = "id", required = true) final Integer id) {
        final OrderItemDTO dto = toDtoConverter.apply(orderItemService.get(id));

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        loadCommonFormMOrderItems(hashMap);
        return new ModelAndView("orderItemMain.edit", hashMap);
    }*/


}
