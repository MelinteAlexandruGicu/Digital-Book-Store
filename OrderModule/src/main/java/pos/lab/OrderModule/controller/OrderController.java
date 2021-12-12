package pos.lab.OrderModule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.lab.OrderModule.assemblers.OrderModelAssembler;
import pos.lab.OrderModule.entities.Order;
import pos.lab.OrderModule.entities.Status;
import pos.lab.OrderModule.exceptions.OrderNotFoundException;
import pos.lab.OrderModule.repositories.OrderRepository;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderModelAssembler orderModelAssembler;

    @GetMapping("/api/order/orders")
    public ResponseEntity<CollectionModel<EntityModel<Order>>> getAllOrders()
    {
        List<EntityModel<Order>> orders = orderRepository.findAll().stream()
                .map(orderModelAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(orders,
                linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel()));
    }

    @GetMapping("api/orders/{id}")
    public ResponseEntity<EntityModel<Order>> getOrderById(@PathVariable String objectid)
    {
        Order order = orderRepository.findById(objectid)
                .orElseThrow(() -> new OrderNotFoundException(objectid));
        return ResponseEntity.ok(orderModelAssembler.toModel(order));
    }

    @PostMapping("/api/order/orders")
    public ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order)
    {
        order.setStatus(Status.IN_PROGRESS);
        Order newOrder = orderRepository.save(order);
        return ResponseEntity
                .created(linkTo(methodOn(OrderController.class).getOrderById(newOrder.getObjectID())).toUri())
                .body(orderModelAssembler.toModel(newOrder));
    }

    @DeleteMapping("/api/orders/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable String objectid)
    {
        Order order = orderRepository.findById(objectid)
                .orElseThrow(() -> new OrderNotFoundException(objectid));
        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(orderModelAssembler.toModel(orderRepository.save(order)));
        }
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't cancel an order that is in the " + order.getStatus() + " status"));
    }

    @PutMapping("/api/orders/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable String objectid)
    {
        Order order = orderRepository.findById(objectid)
                .orElseThrow(() -> new OrderNotFoundException(objectid));
        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(orderModelAssembler.toModel(orderRepository.save(order)));
        }
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't complete an order that is in the " + order.getStatus() + " status"));
    }


}
