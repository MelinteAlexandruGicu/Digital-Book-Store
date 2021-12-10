package pos.lab.OrderModule.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pos.lab.OrderModule.entities.Order;

import java.awt.print.Pageable;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String>, PagingAndSortingRepository<Order, String> {
    Order findByObjectid();

    Page<List<Order>> findAll(Pageable pageable);
}
