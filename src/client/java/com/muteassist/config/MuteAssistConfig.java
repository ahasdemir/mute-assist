package com.muteassist.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.muteassist.Muteassist;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration manager for Mute Assist mod
 * Handles loading and saving of custom mute reasons and durations
 */
public class MuteAssistConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("mute-assist.json");
    
    private static MuteAssistConfig instance;
    
    // Default configuration values
    public List<String> customDurations = new ArrayList<>(Arrays.asList(
            "5m", "10m", "15m", "30m", "45m", "1h", "2h", "3h", "6h", "12h", "1d", "3d", "7d", "14d", "30d", "perm"
    ));
    
    public List<String> customReasons = new ArrayList<>(Arrays.asList(
            "Chat Kirletimi",
            "Cinsellik", 
            "Argo Kelime Kullanımı",
            "Amacı Dışına Ada Reklam Kullanımı",
            "Argo Kelime Benzetmeleri",
            "Chat Amacı Dışında Kullanma",
            "Hakaret",
            "Küfür Kullanımı",
            "Tartışma",
            "Kışkırtma",
            "Yetkilileri Rahatsız Etmek",
            "Sohbete Ada Reklamını Mesaj Olarak Atmak",
            "Dini Muhabbet",
            "Siyasi Muhabet",
            "Link Paylaşımı",
            "Reklam",
            "Ailevi Küfür Kullanımı",
            "Yetkiliyle Özelden Hakaret",
            "Spam",
            "Flood",
            "Caps Lock Kullanımı",
            "Rahatsız Edici Davranış",
            "Trollük",
            "Oyun Bozmak",
            "Açık Arama",
            "Gereksiz Etiketleme",
            "Anlamsız Mesajlar",
            "Tekrar Eden Mesajlar",
            "Büyük Harf Kullanımı",
            "Gereksiz Tartışma Başlatma"
    ));
    
    public boolean enableDebugLogging = false;
    public boolean sortPlayersSuggestions = true;
    public boolean enablePartialReasonMatching = true;
    
    // Reason to duration mapping for /mf command
    public Map<String, String> reasonDurationMap = new HashMap<>();
    
    /**
     * Constructor to initialize default values
     */
    public MuteAssistConfig() {
        initializeDefaultMappings();
    }
    
    /**
     * Initialize default reason-duration mappings
     */
    private void initializeDefaultMappings() {
        if (reasonDurationMap.isEmpty()) {
            reasonDurationMap.put("Sohbet Kirletimi (1. Uyarı)", "15m");
            reasonDurationMap.put("Sohbet Kirletimi (Tekrar)", "1h");
            reasonDurationMap.put("Cinsellik", "60m");
            reasonDurationMap.put("Cinsellik (Tekrar)", "3h");
            reasonDurationMap.put("Argo Kelime Kullanımı", "20m");
            reasonDurationMap.put("Argo Kelime Kullanımı (Tekrar)", "1h");
            reasonDurationMap.put("Amacı Dışında AdaReklam Kullanımı", "60m");
            reasonDurationMap.put("Amacı Dışında AdaReklam Kullanımı(Tekrar)", "2h");
            reasonDurationMap.put("Hakaret", "30m");
            reasonDurationMap.put("Hakaret (Tekrar)", "2h");
            reasonDurationMap.put("Küfür Kullanımı", "30m");
            reasonDurationMap.put("Küfür Kullanımı(Tekrar)", "2h");
            reasonDurationMap.put("Kavga", "30m");
            reasonDurationMap.put("Kavga (Tekrar)", "2h");
            reasonDurationMap.put("Kışkırtma", "30m");
            reasonDurationMap.put("Kışkırtma (Tekrar)", "2h");
            reasonDurationMap.put("Dini Değerlere Hareket", "3h");
            reasonDurationMap.put("Dini Değerlere Hareket (Tekrar)", "8h");
            reasonDurationMap.put("Siyaset Yapmak", "3h");
            reasonDurationMap.put("Siyaset Yapmak (Tekrar)", "8h");
            reasonDurationMap.put("Ailevi Küfür Kullanımı", "3h");
            reasonDurationMap.put("Ailevi Küfür Kullanımı (Tekrar)", "8h");
        }
    }
    
    public static MuteAssistConfig getInstance() {
        if (instance == null) {
            instance = load();
        }
        return instance;
    }
    
    private static MuteAssistConfig load() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                String json = Files.readString(CONFIG_PATH);
                MuteAssistConfig config = GSON.fromJson(json, MuteAssistConfig.class);
                if (config != null) {
                    // Ensure mappings are initialized if they're null or empty
                    if (config.reasonDurationMap == null) {
                        config.reasonDurationMap = new HashMap<>();
                    }
                    config.initializeDefaultMappings();
                    Muteassist.LOGGER.info("Loaded Mute Assist configuration from {}", CONFIG_PATH);
                    return config;
                }
            }
        } catch (Exception e) {
            Muteassist.LOGGER.warn("Failed to load configuration, using defaults: {}", e.getMessage());
        }
        
        // Return default configuration and save it
        MuteAssistConfig defaultConfig = new MuteAssistConfig();
        defaultConfig.save();
        return defaultConfig;
    }
    
    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            String json = GSON.toJson(this);
            Files.writeString(CONFIG_PATH, json);
            Muteassist.LOGGER.info("Saved Mute Assist configuration to {}", CONFIG_PATH);
        } catch (IOException e) {
            Muteassist.LOGGER.error("Failed to save configuration: {}", e.getMessage());
        }
    }
    
    public List<String> getDurations() {
        return new ArrayList<>(customDurations);
    }
    
    public List<String> getReasons() {
        return new ArrayList<>(customReasons);
    }
    
    public void addCustomDuration(String duration) {
        if (!customDurations.contains(duration)) {
            customDurations.add(duration);
            save();
        }
    }
    
    public void addCustomReason(String reason) {
        if (!customReasons.contains(reason)) {
            customReasons.add(reason);
            save();
        }
    }
    
    public void removeDuration(String duration) {
        if (customDurations.remove(duration)) {
            save();
        }
    }
    
    public void removeReason(String reason) {
        if (customReasons.remove(reason)) {
            save();
        }
    }
    
    // Methods for reason-duration mapping
    public String getDurationForReason(String reason) {
        return reasonDurationMap.get(reason);
    }
    
    public Map<String, String> getReasonDurationMap() {
        return new HashMap<>(reasonDurationMap);
    }
    
    public List<String> getFastMuteReasons() {
        return new ArrayList<>(reasonDurationMap.keySet());
    }
    
    public void addReasonDurationMapping(String reason, String duration) {
        reasonDurationMap.put(reason, duration);
        // Also add to custom reasons if not already present
        if (!customReasons.contains(reason)) {
            customReasons.add(reason);
        }
        // Add duration to custom durations if not already present
        if (!customDurations.contains(duration)) {
            customDurations.add(duration);
        }
        save();
    }
    
    public void removeReasonDurationMapping(String reason) {
        reasonDurationMap.remove(reason);
        save();
    }
    
    public boolean hasReasonDurationMapping(String reason) {
        return reasonDurationMap.containsKey(reason);
    }
    
    public int getMappingCount() {
        return reasonDurationMap.size();
    }
}
