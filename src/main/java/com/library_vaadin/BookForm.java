package com.library_vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class BookForm extends FormLayout {

    private final TextField title = new TextField("Title");
    private final Binder<Book> binder = new Binder<>(Book.class);
    private final MainView mainView;
    private final BookService service = BookService.getInstance();

    public BookForm(MainView mainView) {
        this.mainView = mainView;
        ComboBox<BookType> type = new ComboBox<>("Book type");
        type.setItems(BookType.values());
        Button delete = new Button("Delete");
        Button save = new Button("Save");
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        TextField publicationYear = new TextField("Publication year");
        TextField author = new TextField("Author");
        add(title, author, publicationYear, type, buttons);
        binder.bindInstanceFields(this);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
    }

    private void save() {
        Book book = binder.getBean();
        service.save(book);
        mainView.refresh();
        setBook(null);
    }

    private void delete() {
        Book book = binder.getBean();
        service.delete(book);
        mainView.refresh();
        setBook(null);
    }

    public void setBook(Book book) {
        binder.setBean(book);

        if (book == null) {
            setVisible(false);
        } else {
            setVisible(true);
            title.focus();
        }
    }
}