package com.devthiagofurtado.controller;

import com.devthiagofurtado.data.vo.BooksVO;
import com.devthiagofurtado.data.vo.PersonVO;
import com.devthiagofurtado.service.BookService;
import com.devthiagofurtado.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("api/book/v1")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public BooksVO buscarPorId(@PathVariable(value = "id") Long id) {
        BooksVO booksVO = bookService.findById(id);
        booksVO.add(linkTo(methodOn(BookController.class).buscarPorId(id)).withSelfRel());
        return booksVO;
    }

    @GetMapping(value = {}, produces = {"application/json", "application/xml", "application/x-yaml"})
    public List<BooksVO> buscarTodos() {
        List<BooksVO> booksVO = bookService.findAll();
        booksVO.forEach(p->{
            p.add(linkTo(methodOn(BookController.class).buscarPorId(p.getKey())).withSelfRel());
        });
        return booksVO;
    }

    @PostMapping(value = "/salvar", produces = {"application/json", "application/xml", "application/x-yaml"}
            , consumes = {"application/json", "application/xml", "application/x-yaml"})
    public BooksVO salvar(@RequestBody BooksVO request) {
        BooksVO booksVO =bookService.create(request);
        booksVO.add(linkTo(methodOn(BookController.class).buscarPorId(booksVO.getKey())).withSelfRel());
        return booksVO;
    }

    @PutMapping( value = "/atualizar", produces = {"application/json", "application/xml", "application/x-yaml"}
            , consumes = {"application/json", "application/xml", "application/x-yaml"})
    public BooksVO atualizar(@RequestBody BooksVO request) {
        BooksVO booksVO = bookService.update(request);
        booksVO.add(linkTo(methodOn(BookController.class).buscarPorId(booksVO.getKey())).withSelfRel());
        return booksVO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        bookService.delete(id);
        return ResponseEntity.ok().build();
    }

}
