package app.com.bookdemo.dao.text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import app.com.bookdemo.dto.BookDTO;

public class BookTextDAO {

    public List<BookDTO> loadFromRaw(InputStream inputStream) {
        List<BookDTO> result = new ArrayList<>();
        BookDTO dto = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        String s = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((s = bufferedReader.readLine()) != null) {
                String[] tmp = s.split("-");
                dto = new BookDTO(tmp[0].trim(), tmp[1].trim(), tmp[2].trim(), tmp[3].trim().equals("1"), tmp[4].trim(), Float.parseFloat(tmp[5]));
                result.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public List<BookDTO> loadFromInternal(FileInputStream fileInputStream) {
        List<BookDTO> result = new ArrayList<>();
        BookDTO dto = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        String s = null;
        try {
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((s = bufferedReader.readLine()) != null) {
                String[] tmp = s.split("-");
                dto = new BookDTO(tmp[0].trim(), tmp[1].trim(), tmp[2].trim(), tmp[3].trim().equals("1"), tmp[4].trim(), Float.parseFloat(tmp[5]));
                result.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public List<BookDTO> findByPrice(FileInputStream fileInputStream, float from, float to) {
        List<BookDTO> result = new ArrayList<>();
        for (BookDTO dto : loadFromInternal(fileInputStream)) {
            if (dto.getPrice() >= from && dto.getPrice() <= to) {
                result.add(dto);
            }
        }
        return result;
    }


    public void saveToInternal(FileOutputStream fileOutputStream, List<BookDTO> studentDTO) {
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            String result = "";
            for (BookDTO dto : studentDTO) {
                result += dto.toString() + "\n";
            }
            outputStreamWriter.write(result);
            outputStreamWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isIdExist(FileInputStream inputStream, String id) {
        boolean bool = false;
        List<BookDTO> result = loadFromInternal(inputStream);
        for (BookDTO dto : result) {
            if (dto.getBookId().equals(id)) {
                return true;
            }
        }
        return bool;
    }
}

