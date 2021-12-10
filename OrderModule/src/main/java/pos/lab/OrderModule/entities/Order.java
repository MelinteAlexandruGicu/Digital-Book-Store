package pos.lab.OrderModule.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.*;

@Document(collection = "order")
@Data
public class Order implements Serializable {
    @Id
    private String objectid;

    private String date;
    private Items items;
    private Status status;

    public Order() {
    }

    public Order(String date, Items items, Status status) {
        this.date = date;
        this.items = items;
        this.status = status;
    }

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(getObjectid(), order.getObjectid()) && Objects.equals(getDate(), order.getDate()) && Objects.equals(getItems(), order.getItems()) && getStatus() == order.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getObjectid(), getDate(), getItems(), getStatus());
    }

    @Override
    public String toString() {
        return "Order{" +
                "objectid='" + objectid + '\'' +
                ", date='" + date + '\'' +
                ", items=" + items +
                ", status=" + status +
                '}';
    }
}
