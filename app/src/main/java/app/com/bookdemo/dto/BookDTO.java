package app.com.bookdemo.dto;

import java.io.Serializable;

public class BookDTO implements Serializable {
    private String bookId;
    private String bookName;
    private String description;
    private boolean status;
    private String timeOfCreate;
    private float price;

    @Override
    public String toString() {
        return bookId + " - " + bookName + " - " + description + " - " + (status ? "1":"0") + " - " + timeOfCreate + " - " + price;
    }

    public BookDTO() {
    }

    public BookDTO(String bookId, String bookName, String description, boolean status, String timeOfCreate, float price) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.description = description;
        this.status = status;
        this.timeOfCreate = timeOfCreate;
        this.price = price;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTimeOfCreate() {
        return timeOfCreate;
    }

    public void setTimeOfCreate(String timeOfCreate) {
        this.timeOfCreate = timeOfCreate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
