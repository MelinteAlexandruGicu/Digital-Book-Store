package pos.lab.SoapModule;

import io.spring.guides.gs_producing_web_service.Book;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ContentDeliveryRepository {
    private static final Map<String, Book> eBooks = new HashMap<>();

    @PostConstruct
    public void initData()
    {
        Book eBook1 = new Book();
        eBook1.setTitle("Povestea proiectului la POS");
        eBook1.setAuthor("Melinte Alexandru-Gicu");
        eBook1.setYear(2021);
        eBook1.setGenre("De plans");
        eBook1.setEdition("Laborator trecut");

        eBooks.put(eBook1.getTitle(), eBook1);

        Book eBook2 = new Book();
        eBook2.setTitle("Povestea proiectului de licenta");
        eBook2.setAuthor("Melinte Alexandru-Gicu");
        eBook2.setYear(2022);
        eBook2.setGenre("Cu success");
        eBook2.setEdition("Inginer");

        eBooks.put(eBook2.getTitle(), eBook2);
    }

    public Book findBook(String title) {
        Assert.notNull(title, "The eBook's title must not be null!");
        return eBooks.get(title);
    }
}
