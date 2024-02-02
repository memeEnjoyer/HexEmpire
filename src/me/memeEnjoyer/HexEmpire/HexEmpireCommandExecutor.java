package me.memeEnjoyer.HexEmpire;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HexEmpireCommandExecutor implements CommandExecutor {

	private HexEmpire plugin;
	
	public HexEmpireCommandExecutor(HexEmpire plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = null;
		
		if(sender instanceof Player) {
			player = (Player) sender;
		}

    if(args.length < 2) {
      return false;
    }
		
		if(cmd.getName().equalsIgnoreCase("empireCreate")) {
      for(int i = 0; i < this.plugin.empires.size(); i++) {
        if(this.plugin.empires.get(i).getName().equals(args[0])) {
          player.sendMessage("Такая империя уже существует!");
          return true;
        }
      }

      if(Integer.parseInt(args[1]) <= 0) {
        player.sendMessage("Империя не может быть отрицательного или нулевого размера!");
        return true;
      }

      if(Integer.parseInt(args[1]) > 500) {
        player.sendMessage("Империя не может быть такой большой!");
        return true;
      }

      int x = (int) Math.round(player.getLocation().getX());
      int z = (int) Math.round(player.getLocation().getZ());
      Empire empire = new Empire(x, z, args[0], Integer.parseInt(args[1]), player.getName());
      this.plugin.empires.add(empire);
      player.sendMessage("Империя " + empire.getName() + " создана!");

      this.plugin.dataSource.write(x, z, args[0], Integer.parseInt(args[1]), player.getName());

      return true;
    }


    if(cmd.getName().equalsIgnoreCase("addMember")) {

      Empire empire = null;
      for(int i = 0; i < this.plugin.empires.size(); i++) {
        if(this.plugin.empires.get(i).getName().equals(args[0])) {
          empire = this.plugin.empires.get(i);
        }
      }

      if(!(empire == null) && empire.getMembers().contains(player.getName())) {
        if(empire.getMembers().contains(args[1])) {
          player.sendMessage("Игрок " + args[1] + " уже является частью этой империи!");
          return true;
        }

        empire.addMember(args[1]);
        this.plugin.dataSource.addMember(empire.getName(), args[1]);
        player.sendMessage("Игрок " + args[1] + " стал частью империи " + empire.getName() + "!");
      } else {
        player.sendMessage("Вы не являетесь частью этой империи!");
      }
      return true;
    }

	return false;
  }
}

// TODO: Add /displayBoundaries, boundaries should not cross, add /conquer empireName conquerSize
