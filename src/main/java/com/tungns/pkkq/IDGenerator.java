package com.tungns.pkkq;

public class IDGenerator {
  private static int bookId;
  private static int memberId;
  private static int borrowingInfoId;

  public static int generateBookId() {
    return bookId++;
  }

  public static int generateMemberId() {
    return memberId++;
  }

  public static int generateBorrowingInfoId() {
    return borrowingInfoId++;
  }
}
