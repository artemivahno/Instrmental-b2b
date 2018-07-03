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

import com.itacademy.jd2.ai.b2b.dao.api.filter.OrderObjectFilter;
import com.itacademy.jd2.ai.b2b.dao.api.filter.ProductFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;
import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderObject;
import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;
import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;
import com.itacademy.jd2.ai.b2b.dao.orm.model.UserAccount;
import com.itacademy.jd2.ai.b2b.service.IBrandService;
import com.itacademy.jd2.ai.b2b.service.ICategoryService;
import com.itacademy.jd2.ai.b2b.service.IImageService;
import com.itacademy.jd2.ai.b2b.service.IOrderObjectService;
import com.itacademy.jd2.ai.b2b.service.IProductService;
import com.itacademy.jd2.ai.b2b.service.IUserService;
import com.itacademy.jd2.ai.b2b.web.converter.ProductFromDtoConverter;
import com.itacademy.jd2.ai.b2b.web.converter.ProductToDtoConverter;
import com.itacademy.jd2.ai.b2b.web.dto.OrderItemDTO;
import com.itacademy.jd2.ai.b2b.web.dto.ProductDTO;
import com.itacademy.jd2.ai.b2b.web.dto.list.ListDTO;
import com.itacademy.jd2.ai.b2b.web.dto.search.ProductSearchDTO;
import com.itacademy.jd2.ai.b2b.web.security.AuthHelper;

@Controller
@RequestMapping(value = "/product")
public class ProductController extends AbstractController<ProductDTO, ProductFilter> {


    @Autowired
    private IProductService productService;
    @Autowired
    private ProductFromDtoConverter fromDtoConverter;
    @Autowired
    private ProductToDtoConverter toDtoConverter;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private IBrandService brandService;

    private static final String SEARCH_FORM_MODEL = "searchFormModel";
    private static final String SEARCH_DTO = ProductController.class.getSimpleName()
            + "_SEACH_DTO";

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest req,
            @ModelAttribute(SEARCH_FORM_MODEL) ProductSearchDTO searchDto,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {
        final ListDTO<ProductDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        if (req.getMethod().equalsIgnoreCase("get")) {
            // do not use empty payload which comes in case of GET
            searchDto = getSearchDTO(req);
        } else {
            req.getSession().setAttribute(SEARCH_DTO, searchDto);
        }

        final ProductFilter filter = new ProductFilter();
        if (searchDto.getName() != null) {
            filter.setName(searchDto.getName());
        }

        applySortAndOrder2Filter(listDTO, filter);

        listDTO.setTotalCount(productService.getCount(filter));

        filter.setFetchBrand(true);
        filter.setFetchCategory(true);
        final List<IProduct> entities = productService.find(filter);
        listDTO.setList(
                entities.stream().map(toDtoConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        models.put(SEARCH_FORM_MODEL, searchDto);
        return new ModelAndView("product.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final HashMap<String, Object> hashMap = new HashMap<>();
        // final ProductDTO dto = new ProductDTO();

        final IProduct newEntity = productService.createEntity();
        hashMap.put("formModel", toDtoConverter.apply(newEntity));
        // hashMap.put("formModel", dto);

        loadProductComboboxes(hashMap);
        return new ModelAndView("product.edit", hashMap);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("formModel") final ProductDTO formModel,
            final BindingResult result) {
        if (result.hasErrors()) {
            final Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("formModel", formModel);
            loadProductComboboxes(hashMap);
            return "product.edit";
        } else {
            final IProduct entity = fromDtoConverter.apply(formModel);
            productService.save(entity);
            return "redirect:/product";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        productService.delete(id);
        return "redirect:/product";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(
            @PathVariable(name = "id", required = true) final Integer id) {
        final IProduct dbModel = productService.getFullInfo(id);
        final ProductDTO dto = toDtoConverter.apply(dbModel);
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);
        loadProductComboboxes(hashMap);
        return new ModelAndView("product.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(
            @PathVariable(name = "id", required = true) final Integer id) {
        final ProductDTO dto = toDtoConverter.apply(productService.getFullInfo(id));

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        loadProductComboboxes(hashMap);
        return new ModelAndView("product.edit", hashMap);
    }

    private ProductSearchDTO getSearchDTO(final HttpServletRequest req) {
        ProductSearchDTO searchDTO = (ProductSearchDTO) req.getSession()
                .getAttribute(SEARCH_DTO);
        if (searchDTO == null) {
            searchDTO = new ProductSearchDTO();
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }
        return searchDTO;
    }

    private void loadProductComboboxes(final Map<String, Object> hashMap) {
        final Map<Integer, String> brandsMap = brandService.getAll().stream()
                .collect(Collectors.toMap(IBrand::getId, IBrand::getName));
        hashMap.put("brandsChoices", brandsMap);

        final Map<Integer, String> categoryMap = categoryService.getAll().stream()
                .collect(Collectors.toMap(ICategory::getId, ICategory::getName));
        hashMap.put("categoryChoices", categoryMap);

        final Map<Integer, String> imageMap = imageService.getAll().stream()
                .collect(Collectors.toMap(IImage::getId, IImage::getName));
        hashMap.put("imageChoices", imageMap);
    }

//----------------------- List Products 4 buy

    @RequestMapping(value = "/list4buy", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView list4buy(final HttpServletRequest req,
            @ModelAttribute(SEARCH_FORM_MODEL) ProductSearchDTO searchDto,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {
        final ListDTO<ProductDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        
        if (req.getMethod().equalsIgnoreCase("get")) {
            // do not use empty payload which comes in case of GET
            searchDto = getSearchDTO(req);
        } else {
            req.getSession().setAttribute(SEARCH_DTO, searchDto);
        }
        
        /*final ProductDTO dto = toDtoConverter.apply(productService.getFullInfo(id));
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);*/

        final ProductFilter filter = new ProductFilter();
        if (searchDto.getName() != null) {
            filter.setName(searchDto.getName());
        }

        if (searchDto.getCategoryId() != null) {
            filter.setFetchCategory (true);
            filter.setCategoryId(searchDto.getCategoryId());
        }
        
        applySortAndOrder2Filter(listDTO, filter);

        listDTO.setTotalCount(productService.getCount(filter));

        filter.setFetchBrand(true);
        filter.setFetchCategory(true);
        final List<IProduct> entities = productService.find(filter);
        listDTO.setList(
                entities.stream().map(toDtoConverter).collect(Collectors.toList()));
        
        
        final HashMap<String, Object> models = new HashMap<>();
        loadProductComboboxes(models);
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        models.put(SEARCH_FORM_MODEL, searchDto);
        return new ModelAndView("product.list4buy", models);

    }
}
