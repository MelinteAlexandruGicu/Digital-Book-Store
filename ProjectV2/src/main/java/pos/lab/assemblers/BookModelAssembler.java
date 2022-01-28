package pos.lab.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pos.lab.controller.ABCController;
import pos.lab.entities.Book;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {
    @Override
    public EntityModel<Book> toModel(Book book) {
        return EntityModel.of(book,
                linkTo(methodOn(ABCController.class).getBook(book.getIsbn())).withSelfRel(),
                linkTo(methodOn(ABCController.class).getAllBooksWithRequestParam(book.getYear(), book.getTitle(), book.getGenre(), null, null, null)).withRel("books"));
    }
}
