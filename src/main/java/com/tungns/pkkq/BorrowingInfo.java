package com.tungns.pkkq;

import com.tungns.pkkq.Book;
import com.tungns.pkkq.Member;
import java.util.List;
import lombok.Setter;
import lombok.Getter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Setter
@Getter
public class BorrowingInfo {
  private int id;
  private Member member;
  private List<Book> books;
  private LocalDate borrowingDate;
  private LocalDate expectedReturningDate;
  private LocalDate realReturningDate;
  private double totalCost;
  private double moneyPaid;
  private boolean isPaid;
  
  public BorrowingInfo() {
    this.isPaid = false;
    this.borrowingDate = LocalDate.now();
  }
  
  public double calculateTotalCost() {
    long borrowingDays = ChronoUnit.DAYS.between(borrowingDate, expectedReturningDate);
    this.totalCost = books.size() * 8000 * borrowingDays;
    return this.totalCost;
  }
  
  public double calculateMoneyPaid() {
    if (realReturningDate.isAfter(expectedReturningDate)) {
      this.moneyPaid = 1.1d * this.totalCost;
    } else {
      this.moneyPaid = this.totalCost;
    }
    return this.moneyPaid;
  }
}
