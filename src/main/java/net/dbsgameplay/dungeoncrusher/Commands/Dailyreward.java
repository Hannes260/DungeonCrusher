package net.dbsgameplay.dungeoncrusher.Commands;

import net.dbsgameplay.dungeoncrusher.DungeonCrusher;
import net.dbsgameplay.dungeoncrusher.listener.Joinlistener;
import net.dbsgameplay.dungeoncrusher.sql.MYSQLManager;
import net.dbsgameplay.dungeoncrusher.utils.*;
import net.dbsgameplay.dungeoncrusher.utils.Configs.ConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.LocationConfigManager;
import net.dbsgameplay.dungeoncrusher.utils.Configs.RewardConfigManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Dailyreward implements CommandExecutor {
    MYSQLManager mysqlManager;
    private DungeonCrusher dungeonCrusher;
    RewardConfigManager rewardConfigManager;
    ScoreboardBuilder scoreboardBuilder;
    LocationConfigManager locationConfigManager;
    public Dailyreward(DungeonCrusher dungeonCrusher, MYSQLManager mysqlManager, RewardConfigManager rewardConfigManager, LocationConfigManager locationConfigManager) {
        this.dungeonCrusher = dungeonCrusher;
        this.mysqlManager = mysqlManager;
        this.rewardConfigManager = rewardConfigManager;
        this.scoreboardBuilder = new ScoreboardBuilder(dungeonCrusher);
        this.locationConfigManager = locationConfigManager;
    }

    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String PlayerUUID = player.getUniqueId().toString();
            if (mysqlManager.canClaimDailyReward(PlayerUUID)) {
                mysqlManager.grantDailyReward(PlayerUUID);
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.claimed_daily_reward", "",""));
                giveRandomReward(player);
            }else {
                player.sendMessage(ConfigManager.getPrefix() + ConfigManager.getConfigMessage("message.already_claimed_reward","",""));
            }
        }else {
            sender.sendMessage(ConfigManager.getConfigMessage("message.noplayer","",""));
        }
        return false;
    }
    public void giveRandomReward(Player player) {
        Map<String, Map<String, Object>> rewardData = rewardConfigManager.loadRewards();
        // Zufällig einen Belohnungsschlüssel auswählen
        List<String> rewardKeys = new ArrayList<>(rewardData.keySet());
        String selectedRewardKey = rewardKeys.get((int) (Math.random() * rewardKeys.size()));
        giveReward(player, rewardData.get(selectedRewardKey));
    }
    private void giveReward(Player player, Map<String, Object> rewardInfo) {
        // Gegenstände aus dem Reward
        List<Map<String, Object>> itemsList = (List<Map<String, Object>>) rewardInfo.get("items");
        for (Map<String, Object> itemData : itemsList) {
            String materialName = (String) itemData.get("material");
            String ItemType = (String) itemData.get("material");
            int minAmount = (int) itemData.get("minAmount");
            int maxAmount = (int) itemData.get("maxAmount");
            Material material = Material.getMaterial(materialName);
            if (material != null) {
                int amountToDrop = (int) (Math.random() * (maxAmount - minAmount + 1)) + minAmount;
                if (amountToDrop > 0) {
                    String playerUUID = player.getUniqueId().toString();
                    int currentItem = mysqlManager.getItemAmount(playerUUID, ItemType);
                    mysqlManager.updateItemAmount(playerUUID, ItemType, currentItem + amountToDrop);
                    // Nachricht an Spieler senden
                    String itemName = translateMaterialName(material);
                    Joinlistener joinlistener = new Joinlistener(dungeonCrusher, mysqlManager, locationConfigManager);
                    joinlistener.setPlayerInventoryItems(player);
                    player.sendMessage(ConfigManager.getConfigMessage("message.additem", "%item%", itemName, "%amount%", String.valueOf(amountToDrop)));
                }
            }
        }

        // Geld aus dem Reward, falls vorhanden
        if (rewardInfo.containsKey("money")) {
            double minAmount = (double) rewardInfo.get("minAmount");
            double maxAmount = (double) rewardInfo.get("maxAmount");
            giveMoney(player, minAmount, maxAmount);
        }
    }
    public static String translateMaterialName(Material material) {
        switch (material) {
            case COBBLESTONE:
                return "Bruchstein";
            case STONE:
                return "Stein";
            case RAW_COPPER:
                return "RohKupfer";
            case COPPER_INGOT:
                return "Kupferbarren";
            case RAW_IRON:
                return "Roheisen";
            case IRON_INGOT:
                return "Eisenbarren";
            case RAW_GOLD:
                return "RohGold";
            case GOLD_INGOT:
                return "Goldbarren";
            case DIAMOND:
                return "Diamant";
            case DIAMOND_ORE:
                return "Diamanterz";
            case NETHERITE_SCRAP:
                return "Netherite-Schrott";
            case NETHERITE_INGOT:
                return "Netheritebarren";
            case COAL:
                return "Kohle";
            default:
                return material.name();
        }
    }
    private void giveMoney(Player player, double minAmount, double maxAmount) {
        double random = Math.random();
        double range = maxAmount - minAmount;
        double giveMoney = Math.round((random * range + minAmount) * 100.0) / 100.0;
        double newMoney;
        String currentMoney = mysqlManager.getBalance(player.getUniqueId().toString());
        currentMoney = currentMoney.replace(",", "");
        double money = Double.parseDouble(currentMoney);
        newMoney = money + giveMoney;
        String formattedMoney = String.format(Locale.ENGLISH, "%,.2f", newMoney);
        mysqlManager.updateBalance(String.valueOf(player.getUniqueId()), formattedMoney);
        scoreboardBuilder.updateMoney(player);

        player.sendMessage(ConfigManager.getConfigMessage("message.addmobkilledmoney", "%money%", String.valueOf(giveMoney)));
    }
}
