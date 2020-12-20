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
    int bookQuantity = inputNumberWithLabel("Quantity of book: ");
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
    int memberQuantity = inputNumberWithLabel("Quantity of member: ");
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
    int memberId = inputNumberWithLabel("Enter member id: ");
    Member member = searching.searchMemberById(memberId, allMembers);
    if (null != member) {
      info.setMember(member);
    }
    int quantity = inputNumberWithLabel("Quantity of borrowed book: ");
    for (int i=1; i<=quantity; i++) {
      int bookId = inputNumberWithLabel("Enter book #"+i+" id: ");
      Book book = searching.searchBookById(bookId, allBooks);
      if (null != book) {
        booksBorrowed.add(book);
      }
    }
    info.setBooks(booksBorrowed);
    info.setBorrowingDate(LocalDate.now());
    LocalDate expectedReturningDate = inputDateWithLabel("Expected returning date : ");
    info.setExpectedReturningDate(expectedReturningDate);
    info.calculateTotalCost();
    System.out.println(gson.toJson(info));
    return info;
  }
  
  public Book inputBookDetail() {
    String name = inputStringWithLabel("Book name: ");
    String type = inputStringWithLabel("Book type: ");
    String author = inputStringWithLabel("Book author: ");
    LocalDate releaseDate = inputDateWithLabel("Book release date: ");
    int quantity = inputNumberWithLabel("Book quantity: ");
    Book book = new Book(name, type, author, releaseDate, quantity);
    return book;
  }
  
  public Member inputMemberDetail() {
    String name = inputStringWithLabel("Member name: ");
    String idCardNumber = inputStringWithLabel("Member ID card number: ");
    Member member = new Member(name, idCardNumber);
    return member;
  }
  
  public LocalDate inputDateWithLabel(String label) {
    LocalDate output = null;
    do {
      System.out.print(label);
      String input = scanner.nextLine();
      output = Utils.convertStringToDate(input);
    } while(output == null);
    return output;
  }
  
  public int inputNumberWithLabel(String label) {
    int output = -1;
    do {
      System.out.print(label);
      String input = this.scanner.nextLine();
      output = Utils.convertStringToNumber(input);
    } while(output == -1);
    return output;
  }
  
  public String inputStringWithLabel(String label) {
    String input = "";
    boolean isValidated = false;
    do {
      System.out.print(label);
      input = this.scanner.nextLine();
      isValidated = Utils.validateInputString(input);
    } while(!isValidated);
    return input;
  }
  
  public void closeScanner() {
    this.scanner.close();
  }
}