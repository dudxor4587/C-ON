package com.dbtermproject.controller.admin;

import com.dbtermproject.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public String getAdmin(Model model) {
        model.addAttribute("foodList", adminService.getFoodList());
        model.addAttribute("customerOrderList", adminService.getCustomerOrderList());
        return "admin";
    }
}
