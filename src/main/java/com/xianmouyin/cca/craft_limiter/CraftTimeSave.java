package com.xianmouyin.cca.craft_limiter;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

public class CraftTimeSave extends SavedData {
    private static final String DATA_NAME = "craftable_creative_ability_crafted_times";

    private final Map<String, Integer> craftedTimes = new HashMap<>();

    public static CraftTimeSave get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(CraftTimeSave::load, CraftTimeSave::new, DATA_NAME);
    }

    public int getTimes(String slot) {
        return craftedTimes.getOrDefault(slot, 0);
    }

    public void addTimes(String slot, int delta) {
        craftedTimes.put(slot, Math.max(0, getTimes(slot) + delta));
        setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        CompoundTag map = new CompoundTag();
        for (var e : craftedTimes.entrySet()) {
            map.putInt(e.getKey(), e.getValue());
        }
        tag.put("craftedTimes", map);
        return tag;
    }

    public static CraftTimeSave load(CompoundTag tag) {
        CraftTimeSave data = new CraftTimeSave();
        CompoundTag map = tag.getCompound("craftedTimes");
        for (String key : map.getAllKeys()) {
            data.craftedTimes.put(key, map.getInt(key));
        }
        return data;
    }
}
