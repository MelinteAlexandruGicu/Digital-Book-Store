package pos.lab.OrderModule.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.*;

@Document(collection = "order")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Order implements Serializable {
    @Id @GeneratedValue
    private String objectID;
    private String date;
    private Items items;
    private Status status;

    public Order(String date, Items items, Status status) {
        this.date = date;
        this.items = items;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(getObjectID(), order.getObjectID()) && Objects.equals(getDate(), order.getDate()) && Objects.equals(getItems(), order.getItems()) && getStatus() == order.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getObjectID(), getDate(), getItems(), getStatus());
    }

    @Override
    public String toString() {
        return "Order{" +
                "objectid='" + objectID + '\'' +
                ", date='" + date + '\'' +
                ", items=" + items +
                ", status=" + status +
                '}';
    }
}
