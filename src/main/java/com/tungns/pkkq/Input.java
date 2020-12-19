package com.tungns.pkkq;
import com.tungns.pkkq.Book;
import com.tungns.pkkq.BorrowingInfo;
import com.tungns.pkkq.IDGenerator;
import com.tungns.pkkq.Member;
import com.tungns.pkkq.Searching;
import com.tungns.pkkq.Utils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;

public class Input {
  private static Input instance;
  private Gson gson = new Gson();
  private Scanner scanner = new Scanner(System.in);
  private Searching searching = Searching.getInstance();
  private Input(){}
  
  public static Input getInstance() {
    if (null == instance) {
      instance = new Input();
    }
    return instance;
  }
  
  public List<Book> inputNewBooks() {
    System.out.println("-----Add book-----");
    List<Book> books = new ArrayList<Book>();
    System.out.print("Quantity of book: ");
    
    int bookQuantity = scanner.nextInt();
    scanner.nextLine();
    for (int i = 0; i < bookQuantity; i++) {
      System.out.println("Book number " + (i + 1));
      Book book = inputBookDetail();
      int bookId = IDGenerator.generateBookId();
      book.setId(bookId);
      books.add(book);
    }
    System.out.println(gson.toJson(books));
    
    return books;
  }
  
  public List<Member> inputNewMembers() {
    System.out.println("-----Add member-----");
    List<Member> members = new ArrayList<Member>();
    System.out.print("Quantity of member: ");
    
    int memberQuantity = scanner.nextInt();
    scanner.nextLine();
    for (int i = 0; i < memberQuantity; i++) {
      System.out.println("Member number " + (i + 1));
      Member member = inputMemberDetail();
      int memberId = IDGenerator.generateMemberId();
      member.setId(memberId);
      members.add(member);
    }
    
    System.out.println(gson.toJson(members));
    return members;
  }
  
  public BorrowingInfo inputNewBorrowingInfo(List<Book> allBooks, List<Member> allMembers) {
    BorrowingInfo info = new BorrowingInfo();
    List<Book> booksBorrowed = new ArrayList<>();
    int memberId = inputMemberId();
    Member member = searching.searchMemberById(memberId, allMembers);
    if (null != member) {
      info.setMember(member);
    }
    int quantity = inputQuantityBorrowedBook();
    for (int i=1; i<=quantity; i++) {
      int bookId = inputBookId(i);
      Book book = searching.searchBookById(bookId, allBooks);
      if (null != book) {
        booksBorrowed.add(book);
      }
    }
    info.setBooks(booksBorrowed);
    info.setBorrowingDate(LocalDate.now());
    LocalDate expectedReturningDate = inputExpectedReturningDate();
    info.setExpectedReturningDate(expectedReturningDate);
    info.calculateTotalCost();
    System.out.println(gson.toJson(info));
    return info;
  }
  
  public String inputBookName() {
    System.out.print("Enter book name: ");
    String bookName = scanner.nextLine();
    return bookName;
  }
  
  public int inputMemberId() {
    System.out.print("Enter member ID: ");
    int memberId = scanner.nextInt();
    scanner.nextLine();
    return memberId;
  }
  
  public int inputQuantityBorrowedBook() {
    System.out.print("Enter quantity of borrowed book: ");
    int quantity = scanner.nextInt();
    scanner.nextLine();
    return quantity;
  }
  
  public int inputBookId(int index) {
    System.out.print("Enter book #"+index+" id: ");
    int bookId = scanner.nextInt();
    scanner.nextLine();
    return bookId;
  }
  
  public LocalDate inputExpectedReturningDate() {
    LocalDate date = null;
    do {
      System.out.print("Expected returning date : ");
      String expectedReturningDateText = scanner.nextLine();
      date = Utils.convertStringToDate(expectedReturningDateText);
    } while (null == date);
    return date;
  }
  
  public Book inputBookDetail() {
    System.out.print("Book name: ");
    String name = scanner.nextLine();
    System.out.print("Book type: ");
    String type = scanner.nextLine();
    System.out.print("Book author: ");
    String author = scanner.nextLine();
    LocalDate releaseDate = null;
    do {
      System.out.print("Book release date: ");
      String releaseDateText = scanner.nextLine();
      releaseDate = Utils.convertStringToDate(releaseDateText);
    } while(null == releaseDate);
    System.out.print("Book quantity: ");
    int quantity = scanner.nextInt();
    scanner.nextLine();
    Book book = new Book(name, type, author, releaseDate, quantity);
    return book;
  }
  
  public Member inputMemberDetail() {
    System.out.print("Member name: ");
    String name = scanner.nextLine();
    System.out.print("Member ID card number: ");
    String idCardNumber = scanner.nextLine();
    Member member = new Member(name, idCardNumber);
    return member;
  }
  
  public int inputIdBorrowingInfo() {
    System.out.print("Id Borrowing Info: ");
    int id = scanner.nextInt();
    scanner.nextLine();
    return id;
  }
  
  public String inputMemberName(){
    System.out.print("Member name: ");
    String name = scanner.nextLine();
    return name;
  }
  
  
  
  public void closeScanner() {
    this.scanner.close();
  }
}