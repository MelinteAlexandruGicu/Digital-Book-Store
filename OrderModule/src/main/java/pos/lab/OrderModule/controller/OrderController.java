package pos.lab.OrderModule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pos.lab.OrderModule.entities.Order;
import pos.lab.OrderModule.services.OrderService;

import java.awt.print.Pageable;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private PagedResourcesAssembler<Order> pagedResourcesAssembler;

    @GetMapping("/api/order/orders")
    public ResponseEntity<PagedModel<List<Order>>> findAllOrders(Pageable pageable) {
        try {
            Page<List<Order>> orders = orderService.findAllOrders(pageable);

            PagedModel<List<Order>> collModel = pagedResourcesAssembler.toModel(orders, )
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
