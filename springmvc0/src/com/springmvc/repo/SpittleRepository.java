package com.springmvc.repo;

import java.util.List;

import com.springmvc.pojo.Spittle;

public interface SpittleRepository {

  List<Spittle> findSpittles(long limit, int offset);
  Spittle findSpittle(int id); 
  int getItemSum();
}
