package app.com.bookdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import app.com.bookdemo.dao.text.BookTextDAO;
import app.com.bookdemo.dto.BookDTO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            BookTextDAO dao = new BookTextDAO();
            FileOutputStream fileOutputStream = openFileOutput("books.txt", MODE_PRIVATE);
            InputStream inputStream = this.getResources().openRawResource(R.raw.books);
            List<BookDTO> studentDTO = dao.loadFromRaw(inputStream);
            dao.saveToInternal(fileOutputStream, studentDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToTextDemo(View view) {
        Intent intent = new Intent(MainActivity.this, TextMainActivity.class);
        startActivity(intent);
    }
}
