package com.tungns.pkkq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.tungns.pkkq.Book;
import com.tungns.pkkq.BorrowingInfo;
import com.tungns.pkkq.IDGenerator;
import com.tungns.pkkq.Member;
import com.tungns.pkkq.Input;
import com.tungns.pkkq.Searching;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;

@SpringBootApplication
public class PkkqApplication {
  
  static List<Book> books = new ArrayList<>();
  static List<Member> members = new ArrayList<>();
  static List<BorrowingInfo> borrowingInfos = new ArrayList<>();
  static Scanner scanner = new Scanner(System.in);
  static Gson gson = new Gson();
  static Input input = Input.getInstance();
  static Searching searching = Searching.getInstance();
  
  public static void main(String[] args) {
    SpringApplication.run(PkkqApplication.class, args);
    int choice;
    do {
      choice = showMenu();
      switch (choice) {
        case 1:
          List<Book> newBooks = input.inputNewBooks();
          books.addAll(newBooks);
          break;
        case 2:
          List<Member> newMembers = input.inputNewMembers();
          members.addAll(newMembers);
          break;
        case 3:
          String bookName = input.inputStringWithLabel("Enter book name: ");
          List<Book> booksSearchResult = searching.searchBookByName(bookName, books);
          System.out.println(gson.toJson(booksSearchResult));
          break;
        case 4:
          String memberName = input.inputStringWithLabel("Enter member name: ");
          List<Member> membersSearchResult = searching.searchMemberByName(memberName, members);
          System.out.println(gson.toJson(membersSearchResult));
          break;
        case 5:
          BorrowingInfo newBorrowingInfo = input.inputNewBorrowingInfo(books, members);
          borrowingInfos.add(newBorrowingInfo);
          break;
        case 6:
          memberName = input.inputStringWithLabel("Enter member name: ");
          List<BorrowingInfo> borrowingInfoSearched = searching.searchBorrowingInfoByMemberName(memberName, borrowingInfos);
          System.out.println(gson.toJson(borrowingInfoSearched));
          break;
        case 7:
          int idBorrowingInfo = input.inputNumberWithLabel("Id Borrowing Info: ");
          makePayment(idBorrowingInfo);
          break;
      }
    } while (8 != choice);
    if (8 == choice) {
      scanner.close();
      input.closeScanner();
    }
  }
  
  public static int showMenu() {
    System.out.println("-----Library Management-----");
    System.out.println("1. Add book");
    System.out.println("2. Add Member");
    System.out.println("3. Search Book");
    System.out.println("4. Search Member");
    System.out.println("5. Borrow Book");
    System.out.println("6. Search Borrowing Info");
    System.out.println("7. Make payment");
    System.out.println("8. Exit");
    int choice = input.inputNumberWithLabel("Select your choice: ");
    return choice;
  }
  
  public static boolean makePayment(int idBorrowingInfo) {
    BorrowingInfo info = searching.searchUnpaidBorrowingInfoByIc(idBorrowingInfo, borrowingInfos);
    if (null != info) {
      info.setPaid(true);
      info.setRealReturningDate(LocalDate.of(2020, 12, 24));
      info.calculateMoneyPaid();
      System.out.println("Payment successfully");
      return true;
    }
    System.out.println("Borrowing info not found");
    return false;
  }
}
