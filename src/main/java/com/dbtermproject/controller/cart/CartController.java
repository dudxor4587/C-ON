package com.dbtermproject.controller.cart;

import com.dbtermproject.entity.OrderDetail;
import com.dbtermproject.service.cart.CartService;
import com.dbtermproject.service.customer.CustomerService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CustomerService customerService;

    @GetMapping
    public String getCartList(Model model, HttpSession session) {
        String cno = session.getAttribute("cno").toString();
        List<OrderDetail> cartlList = cartService.getCartList(cno);
        int cartId = cartService.getCartId(cno);
        int totalPrice = cartlList.stream().mapToInt(OrderDetail::getTotalPrice).sum();
        model.addAttribute("cartList", cartlList);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartId", cartId);
        model.addAttribute("name", customerService.getName(cno));
        return "cart";
    }

    @PostMapping("/delete")
    public String deleteFromCart(Model model, int itemNo, int cartId) {
        cartService.deleteFromCart(itemNo, cartId);
        model.addAttribute("message", "삭제되었습니다.");
        return "success";
    }

    @PostMapping("/pay")
    public String pay(Model model, int cartId) {
        cartService.pay(cartId);
        model.addAttribute("message", "결제가 완료되었습니다.");
        return "success";
    }

    @PostMapping("/plus")
    public ResponseEntity<Void> plus(int quantity, int itemNo, int cartId) {
        cartService.plus(quantity, itemNo, cartId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/minus")
    public ResponseEntity<Void> minus(int quantity, int itemNo, int cartId) {
        cartService.minus(quantity, itemNo, cartId);
        return ResponseEntity.ok().build();
    }
}
