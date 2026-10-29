# TP6 DPBO 2025/2026 - Java Swing GUI: GameDev Part 2 (Flappy Bird)

## Janji
Saya Reza Putra Wardani dengan NIM 2507880 mengerjakan TP 6 dalam mata kuliah DPBO untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

---

## Desain Program
Program **Flappy Bird (TP6)** merupakan implementasi game 2D berbasis desktop menggunakan **Java Swing GUI** yang dibangun berdasarkan panduan **Modul 7 (GameDev Part 2)**. 

Arsitektur program memisahkan tanggung jawab logika permainan, antarmuka visual (kanvas), dan entitas game ke dalam kelas-kelas terpisah yang saling berkolaborasi secara modular:

### 1. Struktur Kelas Program
1. **`Player.java`**:
   - Merepresentasikan entitas burung (*player*).
   - Menyimpan atribut koordinat `posX`, `posY`, ukuran `width = 34`, `height = 24`, asset gambar `image`, dan kecepatan vertikal `velocityY`.
   - Dilengkapi *getter & setter* untuk mendukung manipulasi posisi oleh game engine.
2. **`Pipe.java`**:
   - Merepresentasikan rintangan pipa (pipa atas dan pipa bawah) sesuai spesifikasi Modul 7.
   - Menyimpan atribut koordinat `posX`, `posY`, dimensi `width = 64`, `height = 512`, asset gambar pipa `image`, kecepatan gerak horizontal `velocityX = -2`, serta penanda `boolean passed` untuk pencatatan skor.
   - Dilengkapi *getter & setter* lengkap.
3. **`Logic.java`**:
   - Pusat kendali logika game (*game logic engine*), mengimplementasikan `ActionListener` dan `KeyListener`.
   - Mengelola dua buah `Timer`:
     - `gameLoop` (60 FPS / interval ~16 ms) untuk pergerakan objek dan pembaruan frame.
     - `pipesCooldown` (interval 1500 ms) untuk memanggil method `placePipes()` yang memunculkan pasangan pipa acak.
   - Mengatur gravitasi (`gravity = 1`), lompatan burung (`velocityY = -9` saat tombol spasi ditekan), pergerakan pipa ke arah kiri, deteksi tabrakan (*AABB collision detection*), penambahan skor, dan status *Game Over*.
4. **`View.java`**:
   - Turunan dari `JPanel` berdimensi `360 x 640` piksel yang bertindak sebagai kanvas render.
   - Memiliki komponen **`JLabel scoreLabel`** di bagian atas untuk menampilkan perolehan skor pemain secara dinamis.
   - Meng-override method `paintComponent(Graphics g)` dan `draw(Graphics g)` untuk merender latar belakang (*background*), burung, seluruh pipa aktif, serta overlay teks *"GAME OVER"* dan *"Press 'R' to Restart"* saat pemain kalah.
5. **`App.java`**:
   - Titik awal masuk program (*entry point* / `main`).
   - Menyediakan antarmuka **Main Menu** di awal aplikasi menggunakan `JPanel`, `JLabel`, dan 2 tombol `JButton`:
     - **Start Game**: Memulai sesi permainan dan mengaktifkan timer game loop.
     - **Exit**: Menutup aplikasi (`System.exit(0)`).
6. **`Sound.java`**:
   - Helper audio menggunakan pustaka standar `javax.sound.sampled` untuk memutar efek suara saat pemain melompat (`jump.wav`), berhasil meraih skor (`point.wav`), dan saat menabrak rintangan (`hit.wav`).

---

## Penjelasan Alur Program

1. **Inisialisasi & Main Menu**:
   - Saat aplikasi dijalankan melalui `java App`, jendela berukuran `360 x 640` piksel muncul menampilkan Main Menu.
   - Pengguna disajikan pilihan tombol: **Start Game** atau **Exit**.
2. **Memulai Permainan (Start Game)**:
   - Saat tombol *Start Game* diklik, panel menu dilepas dan panel `View` game dipasang pada frame.
   - Method `logic.startGame()` dieksekusi: posisi burung diset di tengah layar (`x = 180, y = 320`), list pipa dikosongkan, skor di-set 0, serta timer `gameLoop` dan `pipesCooldown` mulai berjalan.
3. **Gameplay & Mekanika Fisika**:
   - Setiap frame (60 FPS), method `move()` menambahkan gravitasi ke `velocityY`, menggerakkan burung ke bawah, dan menggeser pipa ke kiri sebesar `pipeVelocityX = -2`.
   - Pemain menekan tombol **Spasi** (`KeyEvent.VK_SPACE`) untuk melompat; kecepatan vertikal diset ke `-9` dan suara loncatan berbunyi.
   - Setiap 1,5 detik, timer `pipesCooldown` mengeksekusi `placePipes()` untuk menghasilkan celah pipa acak.
4. **Pencatatan Skor**:
   - Saat burung melewati posisi pipa yang belum ditandai (`!pipe.isPassed()`), atribut `passed` diubah menjadi `true`, skor bertambah +1, dan efek suara poin diputar. Nilai skor langsung diperbarui pada `scoreLabel`.
5. **Kondisi Game Over & Restart**:
   - Jika burung menabrak pipa atas, pipa bawah, atau menyentuh lantai bawah frame, suara tabrakan diputar dan method `triggerGameOver()` menghentikan seluruh pergerakan.
   - Layar menampilkan overlay gelap bertuliskan *"GAME OVER"*, skor akhir, dan instruksi *"Press 'R' to Restart"*.
   - Pemain dapat menekan tombol **'R'** pada keyboard untuk me-restart permainan seketika tanpa harus menutup aplikasi.

---

## Dokumentasi Jalannya Program

- **File Video**: [Dokumentasi/gameplay.mp4](Dokumentasi/gameplay.mp4)


## note
aset "minjem" dari https://megacrash.itch.io/flappy-bird-assets