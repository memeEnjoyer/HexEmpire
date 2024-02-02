package me.memeEnjoyer.HexEmpire; 

import java.util.ArrayList;

public class Empire {
  private ArrayList<String> members = new ArrayList<String>();
  private int x, z, size;
  private String name;

  public Hexagon boundaries;

  public Empire(int x, int z, String name, int size, String king) {
    this.x = x;
    this.z = z;
    this.name = name;
    this.size = size;
    this.members.add(king);
    this.boundaries = new Hexagon(x, z, size);
  }

  public int getX() {
    return this.x;
  }

  public int getZ() {
    return this.z;
  }

  public String getName() {
    return this.name;
  }

  public ArrayList<String> getMembers() {
    return this.members;
  }

  public void addMember(String member) {
    if(!this.members.contains(member)) {
      this.members.add(member);
    }
  }
}
