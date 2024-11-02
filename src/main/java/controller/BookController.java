package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import model.builder.BookBuilder;
import service.BookService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

import java.util.List;

public class BookController {
    private final BookView bookView;
    private final BookService bookService;

    public BookController(BookView bookView, BookService bookService){
        this.bookView = bookView;
        this.bookService = bookService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();

            if (title.isEmpty() || author.isEmpty()){
                bookView.displayAlertMessage("Save Error", "Problem at Title or Author fields", "Can not have empty Author or Title fields. Please fill in the fields before submitting Save!");
            }
            BookDTO bookDTO = new BookDTOBuilder().setAuthor(author).setTitle(title).build();
            boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));

            if (savedBook){
                bookView.displayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database.");
                bookView.addBookToObservableList(bookDTO);
            } else {
                bookView.displayAlertMessage("Save Not Successful", "Book was not added", "There was a problem at adding the book into the database.");
            }
        }
    }

}
