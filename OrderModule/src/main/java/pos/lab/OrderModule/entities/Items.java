package pos.lab.OrderModule.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Items {
    private String isbn;
    private String title;
    private int price;
    private int quantity;

    public Items(String isbn, String title, int price, int quantity) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }
}
