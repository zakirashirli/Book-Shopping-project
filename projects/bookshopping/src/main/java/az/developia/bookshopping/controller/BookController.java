package az.developia.bookshopping.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import az.developia.bookshopping.dao.BookDAO;
import az.developia.bookshopping.model.Book;

@Controller
public class BookController {

	@Autowired
	private BookDAO bookDAO;

	@GetMapping(path = "/books")
	public String showBooks(Model model) {

		List<Book> books = bookDAO.findAll();

		model.addAttribute("books", books);

		return "books";

	}

	@GetMapping(path = "/books/new")
	public String openNewBookPage(Model model) {

		Book book = new Book();

		model.addAttribute("book", book);

		return "new-book";

	}

	@PostMapping(path = "/books/new-book-process")
	public String saveBook(@ModelAttribute(name = "book") Book book, Model model) {
		
		book.setImage("book.image");
		book.setUsername("dea");
		
		bookDAO.save(book);
		
		List<Book> books = bookDAO.findAll();
		
		model.addAttribute("books", books);

		return "books";

	}
	
	@GetMapping(path = "/books/delete/{id}")
	public String deleteBook(@PathVariable(name="id") Integer id, Model model) {

		boolean bookExists = bookDAO.findById(id).isPresent();
		
		if (bookExists) {
			
			bookDAO.deleteById(id);
			
		} else {
			
		}
		
		List<Book> books = bookDAO.findAll();
		
		model.addAttribute("books", books);

		return "redirect:/books";

	}
	
	@GetMapping(path = "/books/edit/{id}")
	public String editBook(@PathVariable(name="id") Integer id, Model model) {
		
		Optional<Book> bookOptional = bookDAO.findById(id);
		
		boolean bookExists = bookOptional.isPresent();
		
		Book book = new Book();
		
		if (bookExists) {
			
			book = bookOptional.get();
			
		} else {
			// no response back
		}
		
		model.addAttribute("book", book);

		return "new-book";

	}
	
}
