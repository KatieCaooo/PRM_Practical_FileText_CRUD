package app.com.bookdemo.textactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import app.com.bookdemo.R;
import app.com.bookdemo.adapter.BookAdapter;
import app.com.bookdemo.dao.text.BookTextDAO;
import app.com.bookdemo.dto.BookDTO;

public class BookListActivity extends AppCompatActivity {
    private ListView listBook;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        final Intent intent = this.getIntent();
        List<BookDTO> result = (List<BookDTO>) intent.getSerializableExtra("INFO");
        bookAdapter = new BookAdapter(result);
        listBook = findViewById(R.id.listBooks);
        listBook.setAdapter(bookAdapter);
        listBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookDTO dto = (BookDTO) listBook.getItemAtPosition(position);
                Intent intentDetail = new Intent(BookListActivity.this, BookDetailActivity.class);
                intentDetail.putExtra("DTO", dto);
                startActivityForResult(intentDetail, 1000);
            }
        });
    }

    public void clickToSearch(View view) {
        float from = Float.parseFloat(((EditText) findViewById(R.id.from)).getText().toString());
        float to = Float.parseFloat(((EditText) findViewById(R.id.to)).getText().toString());
        BookTextDAO bookTextDAO = new BookTextDAO();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput("books.txt");
            List<BookDTO> result = bookTextDAO.findByPrice(fileInputStream, from, to);
            bookAdapter.setBookDTOList(result);
            bookAdapter.notifyDataSetChanged();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BookTextDAO bookTextDAO = new BookTextDAO();
        FileInputStream fileInputStream = null;
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            try {
                fileInputStream = openFileInput("books.txt");
                List<BookDTO> result = bookTextDAO.loadFromInternal(fileInputStream);
                bookAdapter.setBookDTOList(result);
                bookAdapter.notifyDataSetChanged();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public void clickToCreate(View view) {
        Intent intentDetail = new Intent(BookListActivity.this, BookCreateActivity.class);
        startActivityForResult(intentDetail, 1000);
    }
}
