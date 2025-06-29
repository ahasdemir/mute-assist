# Mute Assist Mod - Direkt Komut Gönderimi

## 🚀 Yeni Özellik: Direkt Sunucu Gönderimi

Mod artık komutları **otomatik olarak sunucuya gönderir**! Chat ekranı açılmaz, direkt sunucuya gönderilir.

## ⚡ Hızlı Komutlar

### Tam Komutlar (Direkt Gönderilir)
```bash
/m PlayerName 1h Küfür        # Direkt mute komutu gönderir
/tm PlayerName 30m Spam       # Direkt tempmute komutu gönderir  
/um PlayerName                # Direkt unmute komutu gönderir
/mf PlayerName Cinsellik      # YENİ! Otomatik süre ile hızlı mute (60m)
```

### Kısmi Komutlar (Chat Açılır)
```bash
/m PlayerName                 # Chat açılır: "/mute PlayerName " 
/m PlayerName 1h              # Chat açılır: "/mute PlayerName 1h "
/tm PlayerName                # Chat açılır: "/tempmute PlayerName "
```

## 📝 Kullanım Örnekleri

### Örnek 1: Hızlı Mute
```
Komut: /m BadPlayer 2h Küfür
Sonuç: Sunucuya "/mute BadPlayer 2h Küfür" gönderilir
Action Bar: "§aMuted BadPlayer for 2h: Küfür"
```

### Örnek 2: Temp Mute
```
Komut: /tm SpammerPlayer 30m Spam
Sonuç: Sunucuya "/tempmute SpammerPlayer 30m Spam" gönderilir
Action Bar: "§aTemp muted SpammerPlayer for 30m: Spam"
```

### Örnek 3: Hızlı Mute (YENİ!)
```
Komut: /mf BadPlayer Hakaret
Sonuç: Sunucuya "/mute BadPlayer 3h Hakaret" gönderilir (otomatik süre)
Action Bar: "§aFast muted BadPlayer for 3h: Hakaret"
```

### Örnek 4: Unmute
```
Komut: /um PlayerName
Sonuç: Sunucuya "/unmute PlayerName" gönderilir
Action Bar: "§aUnmuted PlayerName"
```

## 🎯 Tab Completion Desteği

Tüm komutlarda akıllı öneriler çalışır:

### Oyuncu Önerileri
- Çevrimiçi oyuncular otomatik tespit edilir
- Tab tuşu ile oyuncu isimlerini görebilirsiniz

### Süre Önerileri  
- `30m`, `1h`, `2h`, `6h`, `12h`, `1d`, `3d`, `7d`, `14d`, `30d`, `perm`
- Her süre için açıklayıcı tooltip

### Türkçe Sebep Önerileri
- `Küfür`, `Spam`, `Reklam`, `Trolling`, `Saygısızlık`
- `Caps`, `Flood`, `Taciz`, `Hakaret`, `Kavga`

### Hızlı Mute Tablosu (YENİ!)
- `Chat Kirletimi` → 10m
- `Cinsellik` → 60m  
- `Argo Kelime Kullanımı` → 60m
- `Hakaret` → 3h
- `Küfür Kullanımı` → 3h
- `Reklam` → 12h
- Ve daha fazlası... (Tab ile görün!)

## ⚙️ Avantajlar

### ✅ Hızlı ve Verimli
- Chat ekranı açılmaz
- Komut anında gönderilir
- Action bar'da onay mesajı

### ✅ Akıllı Öneriler
- Tab completion her adımda çalışır
- Oyuncular otomatik tespit edilir
- Türkçe sebep önerileri

### ✅ Esnek Kullanım
- Tam komut → Direkt gönderilir
- Kısmi komut → Chat açılır, tamamlamanız için

### ✅ Güvenli
- Server komutlarıyla çakışmaz
- Sadece client-side çalışır
- Hata durumunda action bar'da bilgi verir

## 🔄 Helper Komutlar (Chat Açılır)

Eğer komutları chat'te görmek istiyorsanız:

```bash
/mutehelp PlayerName 1h Küfür    # Chat açılır: "/mute PlayerName 1h Küfür"
/tempmutehelp PlayerName 1h Küfür # Chat açılır: "/tempmute PlayerName 1h Küfür"
/unmutehelp PlayerName           # Chat açılır: "/unmute PlayerName"
```

## 📋 Komut Karşılaştırması

| Komut Türü | Ne Yapar | Ne Zaman Kullanılır |
|-------------|----------|---------------------|
| `/m player 1h reason` | Direkt sunucuya gönderir | Hızlı mute için |
| `/m player` | Chat açılır | Süre/sebep eklemek için |
| `/mutehelp player 1h reason` | Chat açılır | Komutu görmek için |

## 🎮 Sunucu Yanıtları

Komut gönderildikten sonra sunucudan gelecek yanıtlar:

### ✅ Başarılı Durumlar
- `Player has been muted for 1 hour`
- `PlayerName muted for Küfür`
- `PlayerName has been unmuted`

### ❌ Hata Durumları
- `You don't have permission` → Yetkiniz yok
- `Player not found` → Oyuncu bulunamadı
- `Unknown command` → Sunucu bu komutu tanımıyor

### 🔧 Sorun Giderme
- Action bar mesajı görüyorsanız komut gönderilmiştir
- Sunucu yanıt vermiyorsa, yetki veya bağlantı sorunu olabilir
- `/muteassist` ile mod bilgilerini kontrol edebilirsiniz

## 💡 İpuçları

1. **Hız için**: `/m`, `/tm`, `/um` kullanın
2. **Kontrol için**: Helper komutları (`/mutehelp`) kullanın  
3. **Tab tuşu**: Her adımda önerileri görmek için
4. **Action bar**: Komutun gönderildiğini onaylamak için
5. **Kısmi komutlar**: Eksik bilgileri sonra eklemek için

Artık mod çok daha hızlı ve verimli! 🚀
