package csv;

import pojo.Books;
import utils.DataUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Parsing sales csv file
 * 2018-08-01,foo@bar.com,CASH,2,123A;3,1S45;1
 */
public class SalesCsvReader {

    File salesFile = null;
    Scanner scanner = null;

    /**
     * @param path given in the command line
     */
    public SalesCsvReader(String path) {
        salesFile = new File(path);
        try {
            scanner = new Scanner(salesFile);
        } catch (FileNotFoundException e) {
            System.out.println(path + " file not found");
        }
    }

    /**
     * scans the csv file and stores the data in the required data structures
     */
    public void scan() {
        while (scanner.hasNextLine()) {
            String[] dataArray = scanner.nextLine().split(",");
            //should have at least 4 inputs
            if (dataArray.length >= 4) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD");
                Date date;
                try {
                    date = simpleDateFormat.parse(dataArray[0] != null || dataArray[0] != "" ? dataArray[0] : null);
                } catch (ParseException e) {
                    System.out.println("Date should be given in YYYY-MM-DD format");
                }
                String email = dataArray[1];
                Integer quantityPurchased = dataArray[3] != null || dataArray[3] != "" ? Integer.parseInt(dataArray[3]) : null;
                // topSellingBooks
                Integer totalPurchases = 0;
                Map<String, Integer> bookList = new HashMap<String, Integer>();
                if (quantityPurchased > 0) {
                    for (int i = 4; i < quantityPurchased + 4; i++) {
                        String[] items = dataArray[i].split(";");
                        Integer itemCount = Integer.parseInt(items[1]);
                        totalPurchases += itemCount;
                        //add bookid
                        bookList.put(items[0], itemCount);
                        if (DataUtils.topSellingBooks.get(items[0]) != null) {
                            //if already present then update the count otherwise put a new entry
                            DataUtils.topSellingBooks.put(items[0], itemCount + DataUtils.topSellingBooks.get(items[0]));
                        } else {
                            DataUtils.topSellingBooks.put(items[0], itemCount);
                        }
                    }
                }
                //if the customer purchased books
                if (totalPurchases > 0) {
                    if (DataUtils.topCustomers.get(email) != null) {
                        DataUtils.topCustomers.put(email, totalPurchases + DataUtils.topCustomers.get(email));
                    } else {
                        DataUtils.topCustomers.put(email, totalPurchases);
                    }
                }
                // calculate total sales in the entry and update/add in the map
                Float total;
                for (String id : bookList.keySet()) {
                    Books books = DataUtils.books.get(id);
                    if (DataUtils.salesByDate.get(dataArray[0]) != null) {
                        Float sales = DataUtils.salesByDate.get(dataArray[0]);
                        sales = sales + books.getPrice() * bookList.get(id);
                        DataUtils.salesByDate.put(dataArray[0], sales);
                    } else {
                        DataUtils.salesByDate.put(dataArray[0], books.getPrice() * bookList.get(id));
                    }
                }
            } else {
                System.out.println("expects min 4 inputs");
            }


        }
    }
}
