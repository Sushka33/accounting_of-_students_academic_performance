package com.example.popitka;


import com.example.popitka.People;

public class User extends People {


 private int mathh;
 private int inform;
 private int bel;


 public User(String spisok, int mathh, int inform, int bel) {
  super();
  this.spisok= spisok;
  this.mathh = mathh;
  this.inform = inform;
  this.bel=bel;
 }
 public User() {

 }
 public int getMathh() {
  return mathh;
 }

 public int getInform() {
  return inform;
 }

 public int getBel() {
  return bel;
 }

 public void setMathh(int mathh) {
  this.mathh = mathh;
 }

 public void setInform(int inform) {
  this.inform = inform;
 }

 public void setBel(int bel) {
  this.bel = bel;
 }
}

