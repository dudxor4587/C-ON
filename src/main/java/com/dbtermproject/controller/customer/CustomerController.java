package com.dbtermproject.controller.customer;

import com.dbtermproject.dto.customer.request.LoginRequest;
import com.dbtermproject.service.customer.CustomerService;
import jakarta.servlet.http.HttpSession;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute LoginRequest loginRequest, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            customerService.login(loginRequest.cno(), loginRequest.password());
            session.setAttribute("cno", loginRequest.cno());
            if(loginRequest.cno().equals("C0")) {
                mav.setViewName("redirect:/admin");
            } else {
                mav.setViewName("redirect:/");
            }
        } catch (NoSuchElementException e) {
            mav.setViewName("loginPage");
            mav.addObject("error", "아이디가 일치하지 않습니다.");
        } catch (Exception e) {
            mav.setViewName("loginPage");
            mav.addObject("error", e.getMessage());
        }
        return mav;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "loginPage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
