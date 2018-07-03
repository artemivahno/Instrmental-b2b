package com.itacademy.jd2.ai.b2b.web.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itacademy.jd2.ai.b2b.dao.api.filter.UserFilter;
import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;
import com.itacademy.jd2.ai.b2b.dao.api.model.enums.UserRole;
import com.itacademy.jd2.ai.b2b.service.IEmailService;
import com.itacademy.jd2.ai.b2b.service.IUserService;
import com.itacademy.jd2.ai.b2b.web.converter.UserFromDtoConverter;
import com.itacademy.jd2.ai.b2b.web.converter.UserToDtoConverter;
import com.itacademy.jd2.ai.b2b.web.dto.UserDTO;
import com.itacademy.jd2.ai.b2b.web.dto.list.ListDTO;
import com.itacademy.jd2.ai.b2b.web.dto.search.UserSearchDTO;

@Controller
@RequestMapping(value = "/user")
public class UserController extends AbstractController<UserDTO, UserFilter> {

    @Autowired
    private IUserService userService;
    @Autowired
    private UserFromDtoConverter fromDtoConverter;
    @Autowired
    private UserToDtoConverter toDtoConverter;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private IEmailService emailService;

    private static final String SEARCH_FORM_MODEL = "searchFormModel";
    private static final String SEARCH_DTO = UserController.class.getSimpleName()
            + "_SEACH_DTO";

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest req,
            @ModelAttribute(SEARCH_FORM_MODEL) UserSearchDTO searchDto,
            @RequestParam(name = "page", required = false) final Integer pageNumber,
            @RequestParam(name = "sort", required = false) final String sortColumn) {
        final ListDTO<UserDTO> listDTO = getListDTO(req);
        listDTO.setPage(pageNumber);
        listDTO.setSort(sortColumn);

        if (req.getMethod().equalsIgnoreCase("get")) {
            searchDto = getSearchDTO(req);
        } else {
            req.getSession().setAttribute(SEARCH_DTO, searchDto);
        }

        final UserFilter filter = new UserFilter();
        if (searchDto.getName() != null) {
            filter.setName(searchDto.getName());
        }

        /*if ((searchDto.getEnabled() != null) && searchDto.getEnabled()) {
            filter.setEnabled(Boolean.FALSE);
        } else {
            filter.setEnabled(null);
        }*/

        listDTO.setTotalCount(userService.getCount(filter));

        applySortAndOrder2Filter(listDTO, filter);
        final List<IUser> entities = userService.find(filter);
        listDTO.setList(
                entities.stream().map(toDtoConverter).collect(Collectors.toList()));

        final HashMap<String, Object> models = new HashMap<>();
        models.put(ListDTO.SESSION_ATTR_NAME, listDTO);
        models.put(SEARCH_FORM_MODEL, searchDto);
        return new ModelAndView("user.list", models);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showForm() {

        final HashMap<String, Object> hashMap = new HashMap<>();
        final UserDTO dto = new UserDTO();
        hashMap.put("formModel", dto);
        loadComboboxesRole(hashMap);
        return new ModelAndView("user.edit", hashMap);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object save(@Valid @ModelAttribute("formModel") final UserDTO formModel,
            final BindingResult result) {
        final Map<String, Object> hashMap = new HashMap<>();
        loadComboboxesRole(hashMap);
        if (emailExist(formModel.getEmail())&&formModel.getId()==null) {

            hashMap.put("formModel", formModel);
            hashMap.put("showDuplicateError", true);

            return new ModelAndView("user.edit", hashMap);
        }
        if (result.hasErrors()) {

            hashMap.put("formModel", formModel);
            return "user.edit";
        } else {
            final IUser entity = fromDtoConverter.apply(formModel);
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            userService.save(entity);
            final String setText = "12345 ";
            emailService.confirmRegistration(entity.getEmail(), setText);
            return "redirect:/user";
        }
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView showFormRegistration() {

        final HashMap<String, Object> hashMap = new HashMap<>();
        final UserDTO dto = new UserDTO();
        hashMap.put("formModel", dto);
        return new ModelAndView("user.registration", hashMap);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public Object registration(
            @Valid @ModelAttribute("formModel") final UserDTO formModel,
            final BindingResult result) {
        formModel.setRole("customer");
        formModel.setEnabled(false);
        if (emailExist(formModel.getEmail())) {
            final Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("showDuplicateError", true);
            return new ModelAndView("user.registration", hashMap);
        }
        if (result.hasErrors()) {
            final Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("formModel", formModel);
            return "user.registration";
            
        } else {
            final IUser entity = fromDtoConverter.apply(formModel);
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            userService.save(entity);
            final String setText = "12345";
            emailService.confirmRegistration(entity.getEmail(), setText);
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable(name = "id", required = true) final Integer id) {
        userService.delete(id);
        return "redirect:/user";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewDetails(
            @PathVariable(name = "id", required = true) final Integer id) {
        final IUser dbModel = userService.get(id);
        final UserDTO dto = toDtoConverter.apply(dbModel);
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        hashMap.put("readonly", true);
        loadComboboxesRole(hashMap);
        return new ModelAndView("user.edit", hashMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView edit(
            @PathVariable(name = "id", required = true) final Integer id) {
        final UserDTO dto = toDtoConverter.apply(userService.get(id));

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("formModel", dto);
        loadComboboxesRole(hashMap);
        return new ModelAndView("user.edit", hashMap);
    }

    private UserSearchDTO getSearchDTO(final HttpServletRequest req) {
        UserSearchDTO searchDTO = (UserSearchDTO) req.getSession()
                .getAttribute(SEARCH_DTO);
        if (searchDTO == null) {
            searchDTO = new UserSearchDTO();
            req.getSession().setAttribute(SEARCH_DTO, searchDTO);
        }
        return searchDTO;
    }

    private void loadComboboxesRole(final Map<String, Object> hashMap) {

        final List<UserRole> userRoleList = Arrays.asList(UserRole.values());
        final Map<String, String> userRoleMap = userRoleList.stream()
                .collect(Collectors.toMap(UserRole::name, UserRole::name));
        hashMap.put("typeChoices", userRoleMap);

    }

    private boolean emailExist(final String email) {
        final IUser user = userService.getByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
}
