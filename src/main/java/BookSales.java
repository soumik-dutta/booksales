import csv.BookCsvReader;
import csv.SalesCsvReader;
import utils.DataUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookSales {
    /**
     * --books=/path/to/books.list --
     * sales=/path/to/sales.list --top_selling_books=3 --
     * top_customers=2 --sales_on_date=2018-02-01
     *
     * @param args
     */
    public static void main(String[] args) {
        Map<String, String> map = new HashMap();

        /**
         *  books csv file path
         *  sales csv file path
         *  top selling books
         *  top customer
         *  sales on date
         */
        String[] arguments = new String[]{"--books", "--sales", "--top_selling_books", "--top_customers", "--sale_on_date"};

        // the input parameter are parsed and stored
        for (String arg : args) {
            String str = null;
            for (String argument : arguments) {
                if (arg.contains(argument)) {
                    str = splitEquals(arg);
                    map.put(argument, str);
                }
            }
        }

        // business checks
        if (map.get("--books") == null && map.get("--sales") == null) {
            throw new IllegalArgumentException("--books and --sales are mandatory");
        }

        //scan book csv
        BookCsvReader bookCsvReader = new BookCsvReader(map.get("--books"));
        bookCsvReader.scan();

        //scan sales csv
        SalesCsvReader salesCsvReader = new SalesCsvReader(map.get("--sales"));
        salesCsvReader.scan();

        // top selling books calculation
        if (map.get("--top_selling_books") != null) {
            Integer countTopBooks = Integer.parseInt(map.get("--top_selling_books"));
            HashMap<String, Integer> sortedTopSellingBooks = DataUtils.sortByValue(DataUtils.topSellingBooks);
            int i = 1;
            System.out.print("top_selling_books \t");
            for (String bookid : sortedTopSellingBooks.keySet()) {
                System.out.print(bookid + "\t");
                // top n top selling books
                if (i++ == countTopBooks) break;
            }
        }

        // top_customers
        if (map.get("--top_customers") != null) {
            Integer countTopCoustomer = Integer.parseInt(map.get("--top_customers"));
            HashMap<String, Integer> sortedTopCustomer = DataUtils.sortByValue(DataUtils.topCustomers);
            int i = 1;
            System.out.println();
            System.out.print("top_customers \t");
            for (String email : sortedTopCustomer.keySet()) {
                System.out.print(email + "\t");
                // top n top selling books
                if (i++ == countTopCoustomer) break;
            }
        }

        if (map.get("--sale_on_date") != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD");
            Date date;
            try {
                date = simpleDateFormat.parse(map.get("--sale_on_date"));

            } catch (ParseException e) {
                System.out.println("Date entered should be in YYYY-MM-DD format");
            }
            Float sales = DataUtils.salesByDate.get(map.get("--sale_on_date"));
            System.out.println();
            System.out.print("sales_on_date \t"+map.get("--sale_on_date")+"\t"+sales);
            System.out.println();
        }


    }

    public static String splitEquals(String str) {
        String[] strArr = str.split("=");
        return strArr[1];
    }
}
