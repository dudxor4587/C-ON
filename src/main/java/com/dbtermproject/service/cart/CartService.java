package com.dbtermproject.service.cart;

import com.dbtermproject.dto.orderdetail.OrderDetailId;
import com.dbtermproject.entity.Cart;
import com.dbtermproject.entity.Customer;
import com.dbtermproject.entity.OrderDetail;
import com.dbtermproject.repository.cart.CartRepository;
import com.dbtermproject.repository.customer.CustomerRepository;
import com.dbtermproject.repository.orderdetail.OrderDetailRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final OrderDetailRepository orderDetailRepository;

    // 장바구니가 존재하는지 확인하고 없으면 생성하는 메소드
    @Transactional
    public Cart cartExists(String cno) {
        Optional<Cart> cart = cartRepository.findCart(cno);
        return cart.orElseGet(() -> addCart(cno));
    }

    // 장바구니를 생성하는 메소드
    @Transactional
    public Cart addCart(String cno) {
        Customer customer = customerRepository.findById(cno).get();
        Cart cart = new Cart(null, customer);
        return cartRepository.save(cart);
    }

    // 장바구니의 id를 조회하는 메소드
    @Transactional
    public int getCartId(String cno) {
        Cart cart = cartExists(cno);
        return cart.getId();
    }

    // 장바구니 목록을 조회하는 메소드, 장바구니가 없으면 생성하고 조회
    @Transactional
    public List<OrderDetail> getCartList(String cno) {
        Cart cart = cartExists(cno);
        return orderDetailRepository.findAllById(cart.getId());
    }

    // 장바구니에서 상품을 삭제하는 메소드
    @Transactional
    public void deleteFromCart(int itemNo, int cartId) {
        Cart cart = cartRepository.findById(cartId).get();
        orderDetailRepository.deleteById(new OrderDetailId(itemNo, cart));
    }

    // 장바구니에서 결제하는 메소드, 주문일자를 현재 시간으로 업데이트
    @Transactional
    public void pay(int cartId) {
        Cart cart = cartRepository.findById(cartId).get();
        cart.updateOrderDate(LocalDateTime.now());
    }

    // 장바구니 상품의 수량을 증가시키는 메소드
    @Transactional
    public void plus(int quantity, int itemNo, int cartId) {
        OrderDetail orderDetail = orderDetailRepository.findById(
                new OrderDetailId(itemNo, cartRepository.findById(cartId).get())
        ).get();
        orderDetail.plusQuantity(quantity);
        orderDetailRepository.save(orderDetail);
    }

    // 장바구니 상품의 수량을 감소시키는 메소드
    @Transactional
    public void minus(int quantity, int itemNo, int cartId) {
        OrderDetail orderDetail = orderDetailRepository.findById(
                new OrderDetailId(itemNo, cartRepository.findById(cartId).get())
        ).get();
        orderDetail.minusQuantity(quantity);
        orderDetailRepository.save(orderDetail);
    }

}
