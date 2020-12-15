package com.tungns.pkkq;

import com.tungns.pkkq.Book;
import com.tungns.pkkq.Member;
import java.util.List;
import lombok.Setter;
import lombok.Getter;
import java.time.LocalDate;

@Setter
@Getter
public class BorrowingInfo {
  private int id;
  private Member member;
  private List<Book> books;
  private LocalDate borrowingDate;
}
