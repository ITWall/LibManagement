package com.tungns.pkkq;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Book {
  private int id;
  private int quantity;
  private String name;
  private String type;
  private String author;
  private LocalDate releaseDate;

  public Book() {}

  public Book(String name, String type, String author, LocalDate releaseDate, int quantity) {
    this.name = name;
    this.type = type;
    this.author = author;
    this.releaseDate = releaseDate;
    this.quantity = quantity;
  }
}
