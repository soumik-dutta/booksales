package csv;

import pojo.Books;
import utils.DataUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Parsing Books data
 * 123A,A brief history of time, Stephen Hawking, 23.22
 */
public class BookCsvReader {
    File salesFile = null;
    Scanner scanner = null;

    /**
     *
     * @param path given in the command line
     */
    public BookCsvReader(String path) {
        salesFile = new File(path);
        try {
            scanner = new Scanner(salesFile);
        } catch (FileNotFoundException e) {
            System.out.println(path + " file not found");
        }
    }

    /**
     * scans the file and stores it in the POJO
     */
    public void scan(){
        Books books = null;
        while (scanner.hasNextLine()){
            String[] dataArray = scanner.nextLine().split(",");
            books = new Books();
            books.setTitle(dataArray[1]);
            books.setAuthor(dataArray[2]);
            books.setPrice(Float.parseFloat(dataArray[3]!="" || dataArray[3] !=null ? dataArray[3]:null));
            DataUtils.books.put(dataArray[0],books);
        }
    }


}
