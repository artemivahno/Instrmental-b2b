package com.itacademy.jd2.ai.b2b.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping(value = "/feedback")
public class FeedbackController {
    
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public Object doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        request.setAttribute("messages", messages);

        String address = request.getParameter("address");

        if (address == null || address.trim().isEmpty()) {
            messages.put("address", "Пожалуйста, заполните поле");
        }

        if (messages.isEmpty()) {            
            // Сформировать текст сообщения и отправить на почту        
            messages.put("succes", "Сообщение успешно отослано");
        }

        request.getRequestDispatcher("feedback.jsp").forward(request, response);
        return "user.registration";
    }

}
