package app.com.bookdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.List;

import app.com.bookdemo.dao.text.BookTextDAO;
import app.com.bookdemo.dto.BookDTO;
import app.com.bookdemo.textactivity.BookListActivity;

public class TextMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_main);
    }

    public void clickToLoad(View view) {
        Intent intent = new Intent(this, BookListActivity.class);
        try {
            BookTextDAO studentDAO = new BookTextDAO();
            FileInputStream fileInputStream = openFileInput("books.txt");
            List<BookDTO> result = studentDAO.loadFromInternal(fileInputStream);
            intent.putExtra("INFO", (Serializable) result);
        }catch (Exception e){
            e.printStackTrace();
        }
        startActivity(intent);
    }
}
