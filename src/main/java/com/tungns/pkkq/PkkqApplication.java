package com.tungns.pkkq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.tungns.pkkq.Book;
import com.tungns.pkkq.BorrowingInfo;
import com.tungns.pkkq.IDGenerator;
import com.tungns.pkkq.Member;
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

  public static void main(String[] args) {
    SpringApplication.run(PkkqApplication.class, args);
    int choice;
    do {
      choice = showMenu();
      switch (choice) {
        case 1:
          List<Book> newBooks = addBook();
          books.addAll(newBooks);
          break;
        case 2:
          List<Member> newMembers = addMember();
          members.addAll(newMembers);
          break;
        case 3:
          BorrowingInfo newBorrowingInfo = addBorrowingInfo();
          borrowingInfos.add(newBorrowingInfo);
          break;
        case 4:
          String memberName = inputMemberName();
          List<BorrowingInfo> borrowingInfoSearched = searchBorrowingInfoByMemberName(memberName);
          System.out.println(gson.toJson(borrowingInfoSearched));
          break;
        case 5:
          int idBorrowingInfo = inputIdBorrowingInfo();
          makePayment(idBorrowingInfo);
          break;
      }
    } while (6 != choice);
    if (6 == choice) {
      scanner.close();
    }
  }

  public static int showMenu() {
    System.out.println("-----Library Management-----");
    System.out.println("1. Add book");
    System.out.println("2. Add Member");
    System.out.println("3. Borrow Book");
    System.out.println("4. Search Borrowing Info");
    System.out.println("5. Make payment");
    System.out.println("6. Exit");
    System.out.print("Select your choice: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    return choice;
  }

  public static List<Book> addBook() {
    System.out.println("-----Add book-----");
    List<Book> books = new ArrayList<Book>();
    System.out.print("Quantity of book: ");

    int bookQuantity = scanner.nextInt();
    scanner.nextLine();
    for (int i = 0; i < bookQuantity; i++) {
      System.out.println("Book number " + (i + 1));
      Book book = addBookDetail();
      int bookId = IDGenerator.generateBookId();
      book.setId(bookId);
      books.add(book);
    }
    System.out.println(gson.toJson(books));

    return books;
  }

  public static List<Member> addMember() {
    System.out.println("-----Add member-----");
    List<Member> members = new ArrayList<Member>();
    System.out.print("Quantity of member: ");

    int memberQuantity = scanner.nextInt();
    scanner.nextLine();
    for (int i = 0; i < memberQuantity; i++) {
      System.out.println("Member number " + (i + 1));
      Member member = addMemberDetail();
      int memberId = IDGenerator.generateMemberId();
      member.setId(memberId);
      members.add(member);
    }

    System.out.println(gson.toJson(members));
    return members;
  }

  public static BorrowingInfo addBorrowingInfo() {
    BorrowingInfo info = new BorrowingInfo();
    List<Book> booksBorrowed = new ArrayList<>();
    int choice = showMenuBorrowBook();

    switch (choice) {
      case 1:
        System.out.print("Enter member name: ");
        String memberName = scanner.nextLine();

        List<Member> membersSearchResult = searchMemberByName(memberName);
        System.out.println(gson.toJson(membersSearchResult));

        break;
      case 2:
        System.out.print("Enter book name: ");
        String bookName = scanner.nextLine();
        List<Book> booksSearchResult = searchBookByName(bookName);
        System.out.println(gson.toJson(booksSearchResult));
        break;
      case 3:
        System.out.print("Enter member ID: ");
        int memberId = scanner.nextInt();
        scanner.nextLine();
        Member member = searchMemberById(memberId);
        if (null != member) {
          info.setMember(member);
        }
        System.out.print("Enter quantity of borrowed book: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        for (int i=1; i<=quantity; i++) {
          System.out.print("Enter book #"+i+" id: ");
          int bookId = scanner.nextInt();
          scanner.nextLine();
          Book book = searchBookById(bookId);
          if (null != book) {
            booksBorrowed.add(book);
          }
        }
        info.setBooks(booksBorrowed);
        info.setBorrowingDate(LocalDate.now());
        System.out.print("Returning date expected: ");
        String expectedReturningDateText = scanner.nextLine();
        LocalDate expectedReturningDate = LocalDate.now();
        try {
          DateTimeFormatter f =
              DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
    
          expectedReturningDate = LocalDate.parse(expectedReturningDateText, f);
        } catch (Exception ex) {
          System.out.print("error");
          ex.printStackTrace();
        }
        info.setExpectedReturningDate(expectedReturningDate);
        info.calculateTotalCost();
        System.out.println(gson.toJson(info));
        break;
        }

    return info;
  }

  public static int showMenuBorrowBook() {
    System.out.println("-----Borrow book-----");
    System.out.println("1. Search member");
    System.out.println("2. Search book");
    System.out.println("3. Input borrowing information");
    System.out.print("Select your choice: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    return choice;
  }

  public static Book addBookDetail() {

    System.out.print("Book name: ");
    String name = scanner.nextLine();
    System.out.print("Book type: ");
    String type = scanner.nextLine();
    System.out.print("Book author: ");
    String author = scanner.nextLine();
    System.out.print("Book release date: ");
    String releaseDateText = scanner.nextLine();
    LocalDate releaseDate = LocalDate.now();
    try {
      DateTimeFormatter f =
          DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);

      releaseDate = LocalDate.parse(releaseDateText, f);
    } catch (Exception ex) {
      System.out.print("error");
      ex.printStackTrace();
    }

    System.out.print("Book quantity: ");
    int quantity = scanner.nextInt();
    scanner.nextLine();
    Book book = new Book(name, type, author, releaseDate, quantity);

    return book;
  }

  public static Member addMemberDetail() {

    System.out.print("Member name: ");
    String name = scanner.nextLine();
    System.out.print("Member ID card number: ");
    String idCardNumber = scanner.nextLine();

    Member member = new Member(name, idCardNumber);
    return member;
  }

  public static List<Member> searchMemberByName(String memberName) {
    List<Member> memberSearched = new ArrayList<>();
    for (Member mem : members) {
      if (mem.getName().equalsIgnoreCase(memberName)) {
        memberSearched.add(mem);
      }
    }

    return memberSearched;
  }

  public static List<Book> searchBookByName(String bookName) {
    List<Book> bookSearched = new ArrayList<>();
    for (Book book : books) {
      if (book.getName().equalsIgnoreCase(bookName)) {
        bookSearched.add(book);
      }
    }

    return bookSearched;
  }
  
  public static Member searchMemberById(int memberId) {
    for (Member mem: members) {
      if (mem.getId() == memberId){
        return mem;
      }
    }
    return null;
  }
  
  public static Book searchBookById(int bookId) {
    for (Book book: books) {
      if (book.getId() == bookId){
        return book;
      }
    }
    return null;
  }
  
  public static String inputMemberName(){
    System.out.print("Member name: ");
    String name = scanner.nextLine();
    return name;
  }
  
  public static List<BorrowingInfo> searchBorrowingInfoByMemberName(String memberName) {
    List<BorrowingInfo> result = new ArrayList<>();
    for (BorrowingInfo info: borrowingInfos) {
      if (info.getMember().getName().equalsIgnoreCase(memberName)) {
        result.add(info);
      }
    }
    return result;
  }
  
  public static int inputIdBorrowingInfo() {
    System.out.print("Id Borrowing Info: ");
    int id = scanner.nextInt();
    scanner.nextLine();
    return id;
  }
  
  public static boolean makePayment(int idBorrowingInfo) {
    for (BorrowingInfo info: borrowingInfos) {
      if (info.getId() == idBorrowingInfo && !info.isPaid()) {
        info.setPaid(true);
        info.setRealReturningDate(LocalDate.of(2020, 12, 24));
        info.calculateMoneyPaid();
        System.out.println("Payment successfully");
        return true;
      }
    }
    return false;
  }
}
