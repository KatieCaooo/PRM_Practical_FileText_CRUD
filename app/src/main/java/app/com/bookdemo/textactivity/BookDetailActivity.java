package app.com.bookdemo.textactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.com.bookdemo.R;
import app.com.bookdemo.dao.text.BookTextDAO;
import app.com.bookdemo.dto.BookDTO;

public class BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Intent intent = this.getIntent();
        BookDTO dto = (BookDTO) intent.getSerializableExtra("DTO");
        ((EditText) findViewById(R.id.edtBookId)).setText(dto.getBookId());
        ((EditText) findViewById(R.id.edtBookId)).setEnabled(false);
        ((EditText) findViewById(R.id.edtBookName)).setText(dto.getBookName());
        ((EditText) findViewById(R.id.edtBookDescription)).setText(dto.getDescription());
        ((Spinner) findViewById(R.id.spinnerStatus)).setSelection(dto.isStatus() ? 1 : 0);
        ((EditText) findViewById(R.id.edtTimeOfCreate)).setText(dto.getTimeOfCreate());
        ((EditText) findViewById(R.id.edtPrice)).setText(String.valueOf(dto.getPrice()));
    }

    public void clickToDelete(View view) {
        List<BookDTO> result = new ArrayList<>();
        try {
            FileInputStream fileInputStream = openFileInput("books.txt");
            BookTextDAO bookTextDAO = new BookTextDAO();
            result = bookTextDAO.loadFromInternal(fileInputStream);
            for (int i = result.size() - 1; i >= 0; i--) {


                if (result.get(i).getBookId().equals(((EditText) findViewById(R.id.edtBookId)).getText().toString())) {
                    result.remove(i);
                }
            }
            fileInputStream.close();
            FileOutputStream fileOutputStream = openFileOutput("books.txt", MODE_PRIVATE);
            bookTextDAO.saveToInternal(fileOutputStream, result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intent returnIntent = new Intent();
            setResult(AppCompatActivity.RESULT_OK, returnIntent);
            finish();
        }

    }

    public void clickToUpdate(View view) {
        List<BookDTO> result = new ArrayList<>();
        try {
            FileInputStream fileInputStream = openFileInput("books.txt");
            BookTextDAO bookTextDAO = new BookTextDAO();
            result = bookTextDAO.loadFromInternal(fileInputStream);
            for (BookDTO dto : result) {
                if (dto.getBookId().equals(((EditText) findViewById(R.id.edtBookId)).getText().toString())) {
                    BookDTO newDto = new BookDTO(
                            dto.getBookId(),
                            ((EditText) findViewById(R.id.edtBookName)).getText().toString(),
                            ((EditText) findViewById(R.id.edtBookDescription)).getText().toString(),
                            ((Spinner) findViewById(R.id.spinnerStatus)).getSelectedItem().toString().equals("Available"),
                            ((EditText) findViewById(R.id.edtTimeOfCreate)).getText().toString(),
                            Float.parseFloat(((EditText) findViewById(R.id.edtPrice)).getText().toString()));
                    result.set(result.indexOf(dto), newDto);
                }
            }
            fileInputStream.close();
            FileOutputStream fileOutputStream = openFileOutput("books.txt", MODE_PRIVATE);
            bookTextDAO.saveToInternal(fileOutputStream, result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intent returnIntent = new Intent();
            setResult(AppCompatActivity.RESULT_OK, returnIntent);
            finish();
        }
    }

    public void clickToCancle(View view) {
        Intent returnIntent = new Intent();
        setResult(AppCompatActivity.RESULT_CANCELED, returnIntent);
        finish();
    }

}
