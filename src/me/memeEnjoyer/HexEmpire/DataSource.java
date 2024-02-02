package me.memeEnjoyer.HexEmpire;

import java.sql.*;
import java.util.ArrayList;

class DataSource {
  private Connection con;

  public void connect() {
    try {
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/empires", "root", "root");
    this.con = con;
    } catch(Exception e) {
      System.out.println(e);
    }
  }

  public void write(int x, int y, String name, int size, String king) {
    try {
      Statement stmt = this.con.createStatement();
      String createTable = "create table " + name + " (x int, y int, size int, members text);";
      String insert = "insert into " + name + " (x, y, size, members) values (" + x + ", " + y + ", " + size + ", '" + king + "');";
      stmt.executeUpdate(createTable);
      stmt.executeUpdate(insert);
    } catch(Exception e) {
      System.out.println(e);
    }
  }

  public void addMember(String name, String member) {
    try {
      Statement stmt = this.con.createStatement();
      stmt.executeUpdate("insert into " + name + " (members) values ('" + member + "');");
    } catch(Exception e) {
      System.out.println(e);
    }
  }

  public void readInto(ArrayList<Empire> empires) {
    try {
      ArrayList<String> tableNames = new ArrayList<String>();

      Statement stmt = this.con.createStatement();
      
      String tablesQuery = "select table_name from information_schema.tables where table_schema = 'empires'";

      ResultSet tables = stmt.executeQuery(tablesQuery);
      while(tables.next()) { // Grabs all table names from database empires
        tableNames.add(tables.getString(1));
      }

      int x, y, size;
      String king;
      
      for(int i = 0; i < tableNames.size(); i++) { // Grabs the empire values from each table and adds it to the empires array
        String firstRowQuery = "select * from " + tableNames.get(i) + " limit 1";
        ResultSet row = stmt.executeQuery(firstRowQuery);

        while(row.next()) {
          x = row.getInt(1);
          y = row.getInt(2);
          size = row.getInt(3);
          king = row.getString(4);

          Empire empire = new Empire(x, y, tableNames.get(i), size, king);
          empires.add(empire);
        }
      }

      for(int i = 0; i < empires.size(); i++) { // Adds members to empires
        String membersQuery = "select members from " + empires.get(i).getName() + ";";
        ResultSet members = stmt.executeQuery(membersQuery);

        while(members.next()) {
          String member = members.getString(1);

          empires.get(i).addMember(member);
        }
      }

    } catch(Exception e) {
      System.out.println(e);
    }
  }
  
  public void disconnect() {
    try {
      this.con.close();
    } catch(Exception e) {
      System.out.println(e);
    }
  }
}
