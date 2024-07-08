package com.dbtermproject.service.orderdetail;

import com.dbtermproject.dto.orderdetail.request.OrderDetailRequest;
import com.dbtermproject.dto.orderdetail.response.OrderDetailResponse;
import com.dbtermproject.dto.search.ordersearch.OrderSearchRequest;
import com.dbtermproject.entity.Cart;
import com.dbtermproject.entity.Food;
import com.dbtermproject.entity.OrderDetail;
import com.dbtermproject.repository.cart.CartRepository;
import com.dbtermproject.repository.food.FoodRepository;
import com.dbtermproject.repository.orderdetail.OrderDetailRepository;
import com.dbtermproject.service.cart.CartService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final FoodRepository foodRepository;

    // 주문 상세를 추가하는 메소드, 이미 존재하는 상품이면 수량만 업데이트, itemNo은 장바구니 내에서의 순서이며 맨 뒤로 추가
    @Transactional
    public void addOrderDetail(OrderDetailRequest orderDetailRequest, String cno) {
        Cart cart = cartService.cartExists(cno);
        Food food = foodRepository.findById(orderDetailRequest.foodName()).get();
        List<OrderDetail> orderDetails = orderDetailRepository.findAllById(cart.getId());
        for(OrderDetail orderDetail : orderDetails) {
            if(orderDetail.getFood().getFoodName().equals(orderDetailRequest.foodName())) {
                orderDetail.updateQuantity(orderDetailRequest.quantity());
                orderDetailRepository.save(orderDetail);
                return;
            }
        }
        int itemNo;
        if (orderDetails.isEmpty()) {
            itemNo = 1;
        } else {
            itemNo = orderDetails.get(orderDetails.size() - 1).getItemNo() + 1;
        }
        OrderDetail orderDetail = new OrderDetail(
                itemNo, cart, orderDetailRequest.quantity(),
                orderDetailRequest.price()*orderDetailRequest.quantity(),
                food
        );
        orderDetailRepository.save(orderDetail);
    }

    // 주문내역 목록을 조회하는 메소드, 날짜가 없으면 전체 조회, 날짜가 있으면 해당 날짜 사이에서 조회
    @Transactional
    public List<OrderDetailResponse> getOrderDetailList(OrderSearchRequest orderSearchRequest, String cno) {
        List<Cart> paidCarts;
        if(orderSearchRequest.getStartDate() == null && orderSearchRequest.getEndDate() == null) {
            paidCarts = cartRepository.findPaidCart(cno).get();
        } else {
            paidCarts = cartRepository.findCartByDate(cno, orderSearchRequest.getStartDate(), orderSearchRequest.getEndDate()).get();
        }
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        if (paidCarts.isEmpty()) {
            return orderDetailResponses;
        }
        for (Cart cart : paidCarts) {
            List<Food> foods = orderDetailRepository.findFoodById(cart.getId());
            List<Integer> quantities = orderDetailRepository.findQuantityById(cart.getId());
            List<OrderDetail> orderDetails = orderDetailRepository.findAllById(cart.getId());
            int totalPrice = orderDetails.stream().mapToInt(OrderDetail::getTotalPrice).sum();
                orderDetailResponses.add(
                        new OrderDetailResponse(cart.getOrderDate(), foods, quantities, totalPrice));
        }
        return orderDetailResponses;
    }
}
