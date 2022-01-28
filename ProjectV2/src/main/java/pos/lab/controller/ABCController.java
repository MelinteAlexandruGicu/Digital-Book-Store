package pos.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.lab.assemblers.AuthorModelAssembler;
import pos.lab.assemblers.BookModelAssembler;
import pos.lab.entities.Author;
import pos.lab.entities.Book;
import pos.lab.repositories.AuthorRepository;
import pos.lab.repositories.BookAuthorRepository;
import pos.lab.repositories.BookRepository;

import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ABCController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookAuthorRepository bookAuthorRepository;
    @Autowired
    private BookModelAssembler bookModelAssembler;
    @Autowired
    private AuthorModelAssembler authorModelAssembler;

    /////////////////////////////////////////////////   BOOK   //////////////////////////////////////////////////
    /*@RequestMapping(value = "/api/bookCollection/books", method = RequestMethod.OPTIONS)
    ResponseEntity<?> getAllBooksWithRequestParam()
    {
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS)
                .build();
    }*/

    @GetMapping("/api/bookCollection/books")
    public ResponseEntity<CollectionModel<EntityModel<Book>>> getAllBooksWithRequestParam(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(defaultValue = "isbn", required = false) String sortBy
    ) {
            if (year == null && title != null && genre != null) {
                List<EntityModel<Book>> books = bookRepository.findBookByGenreAndTitle(genre, title).stream()
                        .map(bookModelAssembler::toModel).collect(Collectors.toList());
                return ResponseEntity.ok(CollectionModel.of(books, linkTo(methodOn(ABCController.class).getAllBooksWithRequestParam(year, title, genre, pageNo, pageSize, sortBy)).withSelfRel()));
            }
            if (year == null && title == null && genre != null) {
                List<EntityModel<Book>> books = bookRepository.findBookByGenre(genre).stream()
                        .map(bookModelAssembler::toModel).collect(Collectors.toList());
                return ResponseEntity.ok(CollectionModel.of(books, linkTo(methodOn(ABCController.class).getAllBooksWithRequestParam(year, null, genre, pageNo, pageSize, sortBy)).withSelfRel()));
            }
            if (year == null && title != null) {
                List<EntityModel<Book>> books = bookRepository.findBookByTitle(title).stream()
                        .map(bookModelAssembler::toModel).collect(Collectors.toList());
                return ResponseEntity.ok(CollectionModel.of(books, linkTo(methodOn(ABCController.class).getAllBooksWithRequestParam(year, title, null, pageNo, pageSize, sortBy)).withSelfRel()));
            }
            if (year != null && genre != null && title == null) {
                List<EntityModel<Book>> books = bookRepository.findBookByYearAndGenre(year, genre).stream()
                        .map(bookModelAssembler::toModel).collect(Collectors.toList());
                return ResponseEntity.ok(CollectionModel.of(books, linkTo(methodOn(ABCController.class).getAllBooksWithRequestParam(year, null, genre, pageNo, pageSize, sortBy)).withSelfRel()));
            }

            if (year != null && genre == null && title != null) {
                List<EntityModel<Book>> books = bookRepository.findBookByYearAndTitle(year, title).stream()
                        .map(bookModelAssembler::toModel).collect(Collectors.toList());
                return ResponseEntity.ok(CollectionModel.of(books, linkTo(methodOn(ABCController.class).getAllBooksWithRequestParam(year, title, null, pageNo, pageSize, sortBy)).withSelfRel()));
            }

            if (year != null && genre == null) {
                List<EntityModel<Book>> books = bookRepository.findBookByYear(year).stream()
                        .map(bookModelAssembler::toModel)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(CollectionModel.of(books, linkTo(methodOn(ABCController.class).getAllBooksWithRequestParam(year, null, null, pageNo, pageSize, sortBy)).withSelfRel()));
            }

            if(pageNo == null && pageSize == null && sortBy == null)
            {
                List<EntityModel<Book>> books = bookRepository.findAll().stream()
                        .map(bookModelAssembler::toModel)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(CollectionModel.of(books, linkTo(methodOn(ABCController.class).getAllBooksWithRequestParam(null, null, null, null, null, null)).withSelfRel().withType("GET")));
            }
            else
            {
                Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
                List<EntityModel<Book>> books = bookRepository.findAll(paging).stream()
                        .map(bookModelAssembler::toModel)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(CollectionModel.of(books, linkTo(methodOn(ABCController.class).getAllBooksWithRequestParam(null, null, null, pageNo, pageSize, sortBy)).withSelfRel().withType("GET")));
            }
    }

    /*@RequestMapping(value = "/api/bookCollection/books", method = RequestMethod.OPTIONS)
    ResponseEntity<?> getBook()
    {
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.OPTIONS)
                .build();
    }*/
    @GetMapping("/api/bookCollection/books/{isbn}")
    public ResponseEntity<?> getBook(@PathVariable String isbn) {
        EntityModel<Book> entityModel = null;
        if(bookRepository.findBookByIsbn(isbn).isPresent())
            entityModel = bookModelAssembler.toModel(bookRepository.findBookByIsbn(isbn).get());
        assert entityModel != null;
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PostMapping("/api/bookCollection/books")
    public ResponseEntity<?> newBook(@RequestBody Book newBook) {
        EntityModel<Book> entityModel = bookModelAssembler.toModel(bookRepository.save(newBook));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/api/bookCollection/books/{isbn}")
    public ResponseEntity<?> updateBook(@RequestBody Book newBook, @PathVariable String isbn) {
       Book updateBook = bookRepository.findBookByIsbn(isbn)
               .map(book -> {
                   book.setYear(newBook.getYear());
                   book.setGenre(newBook.getGenre());
                   book.setTitle(newBook.getTitle());
                   book.setEdition(newBook.getEdition());
                   return bookRepository.save(book);
               })
               .orElseGet(() -> {
                   newBook.setIsbn(isbn);
                   return bookRepository.save(newBook);
               });
       EntityModel<Book> bookEntityModel = bookModelAssembler.toModel(updateBook);
       return ResponseEntity.created(bookEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(bookEntityModel);
    }

    @DeleteMapping("/api/bookCollection/books/{isbn}")
    public ResponseEntity<?>  deleteBook(@PathVariable String isbn) {
        bookRepository.deleteByIsbn(isbn);
        return ResponseEntity.noContent().build();
    }

    /////////////////////////////////////////////////   AUTHORS   //////////////////////////////////////////////////

//    public CollectionModel<EntityModel<Author>> getAllAuthors() {
//        List<EntityModel<Author>> authors = authorRepository.findAll().stream()
//                .map(authorModelAssembler::toModel)
//                .collect(Collectors.toList());
//        return CollectionModel.of(authors, linkTo(methodOn(ABCController.class).getAllAuthors()).withSelfRel());
//    }

    @GetMapping("/api/bookCollection/authors")
    public ResponseEntity<CollectionModel<EntityModel<Author>>> getAllAuthorsWithRequestParam(
            @RequestParam(name = "firstname", required = false) String firstname,
            @RequestParam(name = "lastname", required = false) String lastname
            /*@RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer itemPerPage*/
            )
    {
        if(firstname != null && lastname == null)
        {
            List<EntityModel<Author>> authors = authorRepository.findByFirstname(firstname).stream()
                    .map(authorModelAssembler::toModel).collect(Collectors.toList());
            return ResponseEntity.ok(CollectionModel.of(authors, linkTo(methodOn(ABCController.class).getAllAuthorsWithRequestParam(firstname, null)).withSelfRel()));
        }

        if(firstname == null && lastname != null)
        {
            List<EntityModel<Author>> authors = authorRepository.findByLastname(lastname).stream()
                    .map(authorModelAssembler::toModel).collect(Collectors.toList());
            return ResponseEntity.ok(CollectionModel.of(authors, linkTo(methodOn(ABCController.class).getAllAuthorsWithRequestParam(null, lastname)).withSelfRel()));
        }
        List<EntityModel<Author>> authors = authorRepository.findAll().stream()
                .map(authorModelAssembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(authors, linkTo(methodOn(ABCController.class).getAllAuthorsWithRequestParam(null, null)).withSelfRel()));
    }

    @GetMapping("/api/bookCollection/authors/{id}")
    public ResponseEntity<?> getAuthor(@PathVariable Integer id) {
        EntityModel<Author> entityModel = null;
        if(authorRepository.findById(id).isPresent())
            entityModel = authorModelAssembler.toModel(authorRepository.findById(id).get());
        assert entityModel != null;
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PostMapping("/api/bookCollection/authors")
    public ResponseEntity<?> newAuthor(@RequestBody Author newAuthor) {
        EntityModel<Author> entityModel = authorModelAssembler.toModel(authorRepository.save(newAuthor));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/api/bookCollection/authors/{id}")
    public ResponseEntity<?> updateAuthor(@RequestBody Author newAuthor, @PathVariable Integer id) {
        Author updateAuthor = authorRepository.findById(id)
                .map(author -> {
                    author.setFirstname(newAuthor.getFirstname());
                    author.setLastname(newAuthor.getLastname());
                    return authorRepository.save(author);
                })
                .orElseGet(() -> {
                    newAuthor.setId(id);
                    return authorRepository.save(newAuthor);
                });
        EntityModel<Author> authorEntityModel = authorModelAssembler.toModel(updateAuthor);
        return ResponseEntity.created(authorEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(authorEntityModel);
    }

    @DeleteMapping("/api/bookCollection/authors/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Integer id) {
        authorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /////////////////////////////////////////////////   BOOK AUTHORS   //////////////////////////////////////////////////

    @GetMapping("/api/bookCollection/books/{isbn}/authors")
    public ResponseEntity<CollectionModel<EntityModel<Author>>> getAll(@PathVariable String isbn) {
        List<EntityModel<Author>> authors = null;
        if (bookAuthorRepository.findByBookIsbnIsbn(isbn).isEmpty()) {
            authors = authorRepository.findAllByBookAuthorsIn(bookAuthorRepository.findByBookIsbnIsbn(isbn)).stream()
                    .map(authorModelAssembler::toModel).collect(Collectors.toList());
        }
        assert authors != null;
        return ResponseEntity.ok(CollectionModel.of(authors, linkTo(methodOn(ABCController.class).getAll(isbn)).withSelfRel()));
    }
}
