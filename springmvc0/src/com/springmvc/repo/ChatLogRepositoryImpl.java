package com.springmvc.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class ChatLogRepositoryImpl implements ChatLogRepository {
  
  private JdbcOperations jdbc;
  private String sql;

  @Autowired
  public ChatLogRepositoryImpl(JdbcOperations jdbc) {
    this.jdbc = jdbc;
  }
   
}
