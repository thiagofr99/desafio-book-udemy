package com.devthiagofurtado.service;

import com.devthiagofurtado.converter.DozerConverter;
import com.devthiagofurtado.data.model.Book;
import com.devthiagofurtado.data.model.Person;
import com.devthiagofurtado.data.vo.BooksVO;
import com.devthiagofurtado.data.vo.PersonVO;
import com.devthiagofurtado.repository.BookRepository;
import com.devthiagofurtado.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public BooksVO create(BooksVO book) {

        return DozerConverter.parseObject(bookRepository.save(DozerConverter.parseObject(book, Book.class)), BooksVO.class);
    }

    public BooksVO update(BooksVO book) {
        Book bookAntigo = bookRepository.findById(book.getKey()).orElseThrow(() -> new ResourceNotFoundException("Book não localizado."));
        bookAntigo.setAuthor(book.getAuthor());
        bookAntigo.setLauchDate(book.getLauchDate());
        bookAntigo.setPrice(book.getPrice());
        bookAntigo.setTitle(book.getTitle());


        return DozerConverter.parseObject(bookRepository.save(bookAntigo), BooksVO.class);
    }

    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book não localizado."));
        bookRepository.delete(book);
    }

    public BooksVO findById(Long id) {
        return DozerConverter.parseObject(bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book não localizado.")), BooksVO.class);
    }

    public List<BooksVO> findAll() {
        return DozerConverter.parseListObjects(bookRepository.findAll(), BooksVO.class);
    }
}
