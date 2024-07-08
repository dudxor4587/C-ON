package com.dbtermproject.controller;

import com.dbtermproject.dto.search.mainsearch.MainSearchRequest;
import com.dbtermproject.entity.Food;
import com.dbtermproject.service.customer.CustomerService;
import com.dbtermproject.service.food.FoodService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private final FoodService foodService;
    private final CustomerService customerService;

    @GetMapping
    public String getMain(Model model, MainSearchRequest mainSearchRequest, HttpSession session) {
        List<Food> foodList = foodService.getFoodList(mainSearchRequest);
        model.addAttribute("foodList", foodList);
        if(session.getAttribute("cno") == null) {
            return "main";
        }
        String cno = session.getAttribute("cno").toString();
        model.addAttribute("name", customerService.getName(cno));
        return "main";
    }


    @PostMapping("/search")
    public String search(Model model, MainSearchRequest mainSearchRequest, HttpSession session) {
        model.addAttribute("foodList", foodService.getFoodList(mainSearchRequest));
        model.addAttribute("default", mainSearchRequest);
        if(session.getAttribute("cno") == null) {
            return "main";
        }
        String cno = session.getAttribute("cno").toString();
        model.addAttribute("name", customerService.getName(cno));
        return "main";
    }
}
