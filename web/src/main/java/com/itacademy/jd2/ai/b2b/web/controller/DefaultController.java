package com.itacademy.jd2.ai.b2b.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;
import com.itacademy.jd2.ai.b2b.service.ICategoryService;
import com.itacademy.jd2.ai.b2b.web.tag.I18N;

@Controller
public class DefaultController {

    private static final Locale LOCALE_RU = new Locale("ru");
    private static final Locale LOCALE_EN = new Locale("en");

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(final HttpServletRequest req,
            @RequestParam(name = "language", required = false) final String lang) {
        if (lang != null) {
            if ("ru".equals(lang)) {
                req.getSession().setAttribute(I18N.SESSION_LOCALE_KEY, LOCALE_RU);
            } else {
                req.getSession().setAttribute(I18N.SESSION_LOCALE_KEY, LOCALE_EN);
            }
        }
        return "index";

    }

    @Autowired
    private ICategoryService categoryService;

    private void loadCommonFormModels(Map<String, Object> hashMap) {
        List<ICategory> all = categoryService.getAll();

        final Map<Integer, String> cardMap = new HashMap<>();

        for (ICategory categor : all) {
            cardMap.put(categor.getId(), categor.getName());
        }

        hashMap.put("categorChoices", cardMap);

    }

}
