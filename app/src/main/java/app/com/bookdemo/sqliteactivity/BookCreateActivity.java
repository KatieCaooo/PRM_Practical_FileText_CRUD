package app.com.bookdemo.sqliteactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.com.bookdemo.R;
import app.com.bookdemo.dao.text.BookTextDAO;
import app.com.bookdemo.dto.BookDTO;

public class BookCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ((LinearLayout) findViewById(R.id.updateBtnLayout)).setVisibility(View.GONE);
        ((EditText) findViewById(R.id.edtTimeOfCreate)).setEnabled(false);
        ((Button) findViewById(R.id.btnCreate)).setVisibility(View.VISIBLE);
    }

    public void clickToCreate(View view) {
        List<BookDTO> result = new ArrayList<>();
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.CANADA);
        try {
            FileInputStream fileInputStream = openFileInput("books.txt");
            BookTextDAO bookTextDAO = new BookTextDAO();
            result = bookTextDAO.loadFromInternal(fileInputStream);
            BookDTO newDto = new BookDTO(
                    ((EditText) findViewById(R.id.edtBookId)).getText().toString(),
                    ((EditText) findViewById(R.id.edtBookName)).getText().toString(),
                    ((EditText) findViewById(R.id.edtBookDescription)).getText().toString(),
                    ((Spinner) findViewById(R.id.spinnerStatus)).getSelectedItem().toString().equals("Available"),
                    df.format(Date.from(Instant.now())),
                    Float.parseFloat(((EditText) findViewById(R.id.edtPrice)).getText().toString()));
            fileInputStream.close();
            fileInputStream = openFileInput("books.txt");
            if (!bookTextDAO.isIdExist(fileInputStream, newDto.getBookId())) {
                result.add(newDto);
                fileInputStream.close();
                FileOutputStream fileOutputStream = openFileOutput("books.txt", MODE_PRIVATE);
                bookTextDAO.saveToInternal(fileOutputStream, result);
                fileOutputStream.close();
            } else {
                Toast.makeText(this, "ID aldready Existed", Toast.LENGTH_LONG).show();
                fileInputStream.close();
                return;
            }
            Intent returnIntent = new Intent();
            setResult(AppCompatActivity.RESULT_OK, returnIntent);
            finish();

        } catch (Exception e) {
            if (e instanceof NumberFormatException) {
                Toast.makeText(this, "Please input price", Toast.LENGTH_SHORT).show();
            }
            e.printStackTrace();
        }
    }
}
