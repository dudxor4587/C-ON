package com.dbtermproject.controller.orderdetail;

import com.dbtermproject.dto.orderdetail.request.OrderDetailRequest;
import com.dbtermproject.dto.orderdetail.response.OrderDetailResponse;
import com.dbtermproject.dto.search.ordersearch.OrderSearchRequest;
import com.dbtermproject.service.customer.CustomerService;
import com.dbtermproject.service.orderdetail.OrderDetailService;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    private final CustomerService customerService;

    @PostMapping
    public String addOrder(Model model, OrderDetailRequest orderDetailRequest, HttpSession session){
        if(session.getAttribute("cno") == null) {
            model.addAttribute("error", "로그인이 필요합니다.");
            return "loginPage";
        }
        String cno = session.getAttribute("cno").toString();
        orderDetailService.addOrderDetail(orderDetailRequest, cno);
        model.addAttribute("message", "장바구니에 추가되었습니다.");
        return "success";
    }

    @GetMapping
    public String getOrderList(Model model, OrderSearchRequest orderSearchRequest, HttpSession session) {
        String cno = session.getAttribute("cno").toString();
        List<OrderDetailResponse> orderDetailList = orderDetailService.getOrderDetailList(orderSearchRequest, cno);
        Collections.reverse(orderDetailList);
        model.addAttribute("name", customerService.getName(cno));
        model.addAttribute("orderDetailList", orderDetailList);
        return "order";
    }

    @PostMapping("/search")
    public String searchOrderList(Model model, OrderSearchRequest orderSearchRequest, HttpSession session) {
        String cno = session.getAttribute("cno").toString();
        List<OrderDetailResponse> orderDetailList = orderDetailService.getOrderDetailList(orderSearchRequest, cno);
        Collections.reverse(orderDetailList);
        model.addAttribute("orderDetailList", orderDetailList);
        model.addAttribute("name", customerService.getName(cno));
        model.addAttribute("default", orderSearchRequest);
        return "order";
    }
}
