package me.memeEnjoyer.HexEmpire;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.block.Block;
import java.awt.Point;

public class BoundariesCheck implements Listener {
  
  private HexEmpire plugin;

  public BoundariesCheck(HexEmpire plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    Player player = event.getPlayer();
    Block block = event.getBlockPlaced();
    int x = block.getX();
    int z = block.getZ();
    Point point = new Point(x, z);

    for(int i = 0; i < this.plugin.empires.size(); i++) {
      Empire empire = this.plugin.empires.get(i);

      if(empire.boundaries.isPointInside(point)) {
        if(!empire.getMembers().contains(player.getName())) {
          event.setBuild(false);
          player.sendMessage("Вы не можете строить на территории империи " + empire.getName() + ", так как не являетесь её частью!");
        } else {
          continue;
        }
      }
    }
  }

}
