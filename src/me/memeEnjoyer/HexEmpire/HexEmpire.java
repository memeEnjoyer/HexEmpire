package me.memeEnjoyer.HexEmpire;

import java.util.logging.Logger;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HexEmpire extends JavaPlugin {
  public DataSource dataSource = new DataSource();

	Logger log = getLogger();

  public ArrayList<Empire> empires = new ArrayList<Empire>();

  private BoundariesCheck listener = new BoundariesCheck(this);
  private HexEmpireCommandExecutor commandExecutor = new HexEmpireCommandExecutor(this);

	@Override
	public void onEnable() {
    this.dataSource.connect();

		log.info("HexEmpire enabled");
    getServer().getPluginManager().registerEvents(listener, this);
		getCommand("empireCreate").setExecutor(commandExecutor);
    getCommand("addMember").setExecutor(commandExecutor);

    dataSource.readInto(this.empires);
    log.info("Successfully read " + this.empires.size() + " empires!");

	}
	
	public void onDisable() {
    this.dataSource.disconnect();
		log.info("HexEmpire disabled");
	}
	
}
