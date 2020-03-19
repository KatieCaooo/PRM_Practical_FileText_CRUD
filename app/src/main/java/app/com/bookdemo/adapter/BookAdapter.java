package app.com.bookdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import app.com.bookdemo.R;
import app.com.bookdemo.dto.BookDTO;

public class BookAdapter extends BaseAdapter {
    private List<BookDTO> bookDTOList;

    public BookAdapter(List<BookDTO> bookDTOList) {
        this.bookDTOList = bookDTOList;
    }

    public void setBookDTOList(List<BookDTO> bookDTOList) {
        this.bookDTOList = bookDTOList;
    }

    @Override
    public int getCount() {
        return bookDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return bookDTOList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.book_item, parent, false);
        }
        BookDTO bookDTO = bookDTOList.get(position);
        ((TextView) convertView.findViewById(R.id.book_id_value)).setText(bookDTO.getBookId());
        ((TextView) convertView.findViewById(R.id.book_name_value)).setText(bookDTO.getBookName());
        ((TextView) convertView.findViewById(R.id.book_price_value)).setText(String.valueOf(bookDTO.getPrice()));
        ((TextView) convertView.findViewById(R.id.time_of_creation_value)).setText(bookDTO.getTimeOfCreate());
        return convertView;
    }
}
