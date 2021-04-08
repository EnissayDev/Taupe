package fr.enissay.heads;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class Heads {

	public static void setHead(ItemMeta im, String playerSkullTexture) {
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", playerSkullTexture).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = im.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(im, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
	}

	public static ItemStack playerSkullForTexture(String texture, String name, List<String> lore)
	{
		ItemStack is = new ItemStack(Material.PLAYER_HEAD, 1);
		is.setDurability((short)3);
		SkullMeta meta = (SkullMeta)is.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		setHead(meta, texture);
		is.setItemMeta(meta);
		return is;
	}
	public static void skullTexture(String texture, ItemMeta meta)
	{
		ItemStack is = new ItemStack(Material.PLAYER_HEAD, 1);
		is.setDurability((short)3);
		setHead(meta, texture);
		is.setItemMeta(meta);
	}
	
	public static void skullTexture(String texture, ItemStack is)
	{
		is.setDurability((short)3);
		setHead(is.getItemMeta(), texture);
	}
}
