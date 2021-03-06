package pos.lab.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pos.lab.controller.ABCController;
import pos.lab.entities.Author;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuthorModelAssembler implements RepresentationModelAssembler<Author, EntityModel<Author>> {
    @Override
    public EntityModel<Author> toModel(Author author) {
        return EntityModel.of(author,
                linkTo(methodOn(ABCController.class).getAuthor(author.getId())).withSelfRel(),
                linkTo(methodOn(ABCController.class).getAllAuthorsWithRequestParam(author.getFirstname(), author.getLastname())).withRel("authors"));
    }
}
