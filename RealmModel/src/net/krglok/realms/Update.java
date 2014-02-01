package net.krglok.realms;

import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Update
{
	public static boolean msg = false;
	public static boolean outdated = false;
	public static byte v = -1;

	@SuppressWarnings("unused")
	private static String vOnline;
	@SuppressWarnings("unused")
	private static String vThis;
	private static Plugin plugin;

	public Update()
	{
		
	}
	/**
	 * check for updates, update variables
	 */
	public static void updateCheck(Plugin instance) 
	{
		plugin = instance;
		String pluginUrlString = "http://dev.bukkit.org/server-mods/classranks/files.rss";
		try 
		{
			URL url = new URL(pluginUrlString);
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder()
					.parse(url.openConnection().getInputStream());
			doc.getDocumentElement().normalize();
			NodeList nodes = doc.getElementsByTagName("item");
			Node firstNode = nodes.item(0);
			if (firstNode.getNodeType() == 1) {
				Element firstElement = (Element) firstNode;
				NodeList firstElementTagName = firstElement
						.getElementsByTagName("title");
				Element firstNameElement = (Element) firstElementTagName
						.item(0);
				NodeList firstNodes = firstNameElement.getChildNodes();

				String sOnlineVersion = firstNodes.item(0).getNodeValue();
				String sThisVersion = plugin.getDescription().getVersion();

				while (sOnlineVersion.contains(" ")) {
					sOnlineVersion = sOnlineVersion.substring(sOnlineVersion
							.indexOf(" ") + 1);
				}

				Update.vOnline = sOnlineVersion.replace("v", "");
				Update.vThis = sThisVersion.replace("v", "");

				return;
			}
		} catch (Exception localException) 
		{
			Bukkit.getLogger().info("Exception [ClassRanks] Update Check!");
		}
	}

	public static void message(Player player, String msg)
	{
		player.sendMessage(msg);
	}

}
