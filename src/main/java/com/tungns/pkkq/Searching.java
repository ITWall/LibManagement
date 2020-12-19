package com.tungns.pkkq;

import com.tungns.pkkq.Book;
import com.tungns.pkkq.BorrowingInfo;
import com.tungns.pkkq.IDGenerator;
import com.tungns.pkkq.Member;
import java.util.ArrayList;
import java.util.List;

public class Searching {
  private static Searching instance;
  
  private Searching(){}
  
  public static Searching getInstance() {
    if (null == instance) {
      instance = new Searching();
    }
    return instance;
  }
  
  public List<Member> searchMemberByName(String memberName, List<Member> allMembers) {
    List<Member> memberSearched = new ArrayList<>();
    for (Member mem : allMembers) {
      if (mem.getName().equalsIgnoreCase(memberName)) {
        memberSearched.add(mem);
      }
    }
    return memberSearched;
  }
  
  public List<Book> searchBookByName(String bookName, List<Book> allBooks) {
    List<Book> bookSearched = new ArrayList<>();
    for (Book book : allBooks) {
      if (book.getName().equalsIgnoreCase(bookName)) {
        bookSearched.add(book);
      }
    }
    return bookSearched;
  }
  
  public Member searchMemberById(int memberId, List<Member> allMembers) {
    for (Member mem: allMembers) {
      if (mem.getId() == memberId){
        return mem;
      }
    }
    return null;
  }
  
  public Book searchBookById(int bookId, List<Book> allBooks) {
    for (Book book: allBooks) {
      if (book.getId() == bookId){
        return book;
      }
    }
    return null;
  }
  
  public List<BorrowingInfo> searchBorrowingInfoByMemberName(String memberName, List<BorrowingInfo> allBorrowingInfo) {
    List<BorrowingInfo> result = new ArrayList<>();
    for (BorrowingInfo info: allBorrowingInfo) {
      if (info.getMember().getName().equalsIgnoreCase(memberName)) {
        result.add(info);
      }
    }
    return result;
  }
  
  public BorrowingInfo searchUnpaidBorrowingInfoByIc(int idBorrowingInfo, List<BorrowingInfo> allBorrowingInfos) {
    for (BorrowingInfo info: allBorrowingInfos) {
      if (info.getId() == idBorrowingInfo && !info.isPaid()) {
        return info;
      }
    }
    return null;
  }
  
}