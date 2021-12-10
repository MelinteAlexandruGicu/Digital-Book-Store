package pos.lab.OrderModule.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;
import pos.lab.OrderModule.entities.Order;
import pos.lab.OrderModule.repositories.OrderRepository;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    //@Query(value="{}", fields = "{}")
    public Page<List<Order>> findAllOrders(Pageable pageable)
    {
        return orderRepository.findAll(pageable);
    }
}
