package pos.lab.controller;


import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.lab.assemblers.AuthorModelAssembler;
import pos.lab.assemblers.BookModelAssembler;
import pos.lab.entities.Author;
import pos.lab.entities.Book;
import pos.lab.services.ABCService;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 400, message = "This is a bad request, please follow the API documentation for the proper request format"),
        @io.swagger.annotations.ApiResponse(code = 401, message = "Due to security constraints, your access request cannot be authorized"),
        @io.swagger.annotations.ApiResponse(code = 500, message = "The server is down. Please bear with us."),
}
)

public class ABCController {
    private final ABCService abcService;
    private final BookModelAssembler bookModelAssembler;
    private final AuthorModelAssembler authorModelAssembler;


    @Autowired
    public ABCController(ABCService abcService, BookModelAssembler bookModelAssembler, AuthorModelAssembler authorModelAssembler) {
        this.abcService = abcService;
        this.bookModelAssembler = bookModelAssembler;
        this.authorModelAssembler = authorModelAssembler;
    }

    @GetMapping("/api/bookcollection/books")
    public CollectionModel<EntityModel<Book>> getAllBooks(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "items_per_page", required = false) Integer itemPerPage
    ) {
//        Pageable pageable;
//        int limit = 5;
//        pageable = (Pageable) page.map(value -> itemPerPage.map(integer -> PageRequest.of(value * integer, integer))
//                .orElseGet(() -> PageRequest.of(value * 10, 10)))
//                .orElseGet(() -> PageRequest.of(0, limit));
        if(year == null && title != null && genre != null)
        {
            List<EntityModel<Book>> books = abcService.findBookByGenreAndTitle(genre, title).stream()
                    .map(bookModelAssembler::toModel).collect(Collectors.toList());
            return CollectionModel.of(books, linkTo(methodOn(ABCController.class).getAllBooks(year, title, genre, page, itemPerPage)).withSelfRel());
        }
        if(year == null && title == null && genre != null)
        {
            List<EntityModel<Book>> books = abcService.findBookByGenre(genre).stream()
                    .map(bookModelAssembler::toModel).collect(Collectors.toList());
            return CollectionModel.of(books, linkTo(methodOn(ABCController.class).getAllBooks(year, title, genre, page, itemPerPage)).withSelfRel());
        }
        if(year == null && genre == null && title != null)
        {
            List<EntityModel<Book>> books = abcService.findBookByTitle(title).stream()
                    .map(bookModelAssembler::toModel).collect(Collectors.toList());
            return CollectionModel.of(books, linkTo(methodOn(ABCController.class).getAllBooks(year, title, genre, page, itemPerPage)).withSelfRel());
        }
        if(year != null && genre != null && title == null)
        {
            List<EntityModel<Book>> books = abcService.findBookByYearAndGenre(year, genre).stream()
                    .map(bookModelAssembler::toModel).collect(Collectors.toList());
            return CollectionModel.of(books, linkTo(methodOn(ABCController.class).getAllBooks(year, title, genre, page, itemPerPage)).withSelfRel());
        }

        if(year != null && genre == null && title != null)
        {
            List<EntityModel<Book>> books = abcService.findBookByYearAndTitle(year, title).stream()
                    .map(bookModelAssembler::toModel).collect(Collectors.toList());
            return CollectionModel.of(books, linkTo(methodOn(ABCController.class).getAllBooks(year, title, genre, page, itemPerPage)).withSelfRel());
        }

        if(year != null && genre == null && title == null)
        {
            List<EntityModel<Book>> books = abcService.findBookByYear(year).stream()
                    .map(bookModelAssembler::toModel).collect(Collectors.toList());
            return CollectionModel.of(books, linkTo(methodOn(ABCController.class).getAllBooks(year, title, genre, page, itemPerPage)).withSelfRel());
        }

        List<EntityModel<Book>> books = abcService.findAllBooks().stream()
                .map(bookModelAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(books, linkTo(methodOn(ABCController.class).getAllBooks(year, title, genre, page, itemPerPage)).withSelfRel());
    }

    @GetMapping("/api/bookcollection/books/{isbn}")
    public EntityModel<Book> getBook(@PathVariable String isbn) {
        Book book = abcService.findBookByIsbn(isbn);
        return bookModelAssembler.toModel(book);
    }

    @PostMapping("/api/bookcollection/books")
    public ResponseEntity<?> newBook(@RequestBody Book newBook) {
        EntityModel<Book> entityModel = bookModelAssembler.toModel(abcService.addBook(newBook));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/api/bookcollection/books/{isbn}")
    public ResponseEntity<?> updateBook(@RequestBody Book newBook, @PathVariable String isbn) {
        EntityModel<Book> entityModel = bookModelAssembler.toModel(abcService.updateBook(newBook, isbn));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/api/bookcollection/books/{isbn}")
    public ResponseEntity<?>  deleteBook(@PathVariable String isbn) {
        abcService.deleteByIsbn(isbn);
        return ResponseEntity.noContent().build();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/api/bookcollection/authors")
    public CollectionModel<EntityModel<Author>> getAllAuthors(
            @RequestParam(name = "firstname", required = false) String firstname,
            @RequestParam(name = "lastname", required = false) String lastname,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer itemPerPage
            )
    {
        if(firstname != null && lastname == null)
        {
            List<EntityModel<Author>> authors = abcService.findAuthorByFirstname(firstname).stream()
                    .map(authorModelAssembler::toModel).collect(Collectors.toList());
            return CollectionModel.of(authors, linkTo(methodOn(ABCController.class).getAllAuthors(firstname, lastname, page, itemPerPage)).withSelfRel());
        }

        if(firstname == null && lastname != null)
        {
            List<EntityModel<Author>> authors = abcService.findAuthorByLastname(lastname).stream()
                    .map(authorModelAssembler::toModel).collect(Collectors.toList());
            return CollectionModel.of(authors, linkTo(methodOn(ABCController.class).getAllAuthors(firstname, lastname, page, itemPerPage)).withSelfRel());
        }
        List<EntityModel<Author>> authors = abcService.findAllAuthors().stream()
                .map(authorModelAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(authors, linkTo(methodOn(ABCController.class).getAllAuthors(firstname, lastname, page, itemPerPage)).withSelfRel());
    }

    @GetMapping("/api/bookcollection/authors/{id}")
    public EntityModel<Author> getAuthor(@PathVariable Integer id) {
        Author author = abcService.findById(id);
        return authorModelAssembler.toModel(author);
    }

    @PostMapping("/api/bookcollection/authors")
    public ResponseEntity<?> newAuthor(@RequestBody Author newAuthor) {
        EntityModel<Author> entityModel = authorModelAssembler.toModel(abcService.addAuthor(newAuthor));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/api/bookcollection/authors/{id}")
    public ResponseEntity<?> updateAuthor(@RequestBody Author newAuthor, @PathVariable Integer id) {
        EntityModel<Author> entityModel = authorModelAssembler.toModel(abcService.updateAuthor(newAuthor, id));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/api/bookcollection/authors/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Integer id) {
        abcService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/api/bookcollection/books/{isbn}/authors")
    public List<Author> getAll(@PathVariable String isbn) {
        List<Author> authors = abcService.findAllBooksAuthors(isbn);
        return authors;
    }


}
