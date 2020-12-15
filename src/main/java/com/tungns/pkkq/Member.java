package com.tungns.pkkq;

import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Member {
  private int id;
  private String name;
  private String idCardNumber;
  
  public Member(){}
  
  public Member(String name, String idCardNumber) {
    this.name = name;
    this.idCardNumber = idCardNumber;
  }
}
