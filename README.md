# Game Management System

Aplikasi web manajemen game yang dibangun menggunakan Spring Boot dengan fitur autentikasi, CRUD operations, dan role-based access control.

## 🚀 Fitur Utama

- **Sistem Autentikasi**: Login dan registrasi pengguna dengan Spring Security
- **Role-Based Access Control**: Sistem role USER dan ADMIN dengan akses berbeda
- **CRUD Game**: Create, Read, Update, Delete game dengan validasi lengkap
- **Pagination & Sorting**: Navigasi halaman dan pengurutan data
- **Search Functionality**: Pencarian game berdasarkan nama
- **Responsive UI**: Interface yang user-friendly menggunakan Thymeleaf
- **Database Integration**: MySQL dengan JPA/Hibernate ORM

## 🛠️ Teknologi yang Digunakan

- **Java 17**
- **Spring Boot 3.4.0**
- **Spring Security** - Untuk autentikasi dan autorisasi
- **Spring Data JPA** - Untuk operasi database
- **Thymeleaf** - Template engine untuk UI
- **MySQL** - Database relational
- **Maven** - Build tool dan dependency management
- **Lombok** - Mengurangi boilerplate code

## 📋 Prerequisites

Sebelum menjalankan aplikasi ini, pastikan Anda sudah menginstall:

- Java Development Kit (JDK) 17 atau lebih tinggi
- MySQL Server
- Maven 3.6 atau lebih tinggi

## 🚀 Cara Menjalankan

### 1. Clone Repository
```bash
git clone <repository-url>
cd test3
```

### 2. Konfigurasi Database
Update konfigurasi database di `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/game_management_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build dan Run Aplikasi
```bash
# Build aplikasi
mvn clean install

# Jalankan aplikasi
mvn spring-boot:run
```

Aplikasi akan berjalan di `http://localhost:8080`

## 🔐 Akses Aplikasi

### Admin Access
- **URL**: `/games/admin`
- **Fitur**: Full CRUD operations, melihat semua game, mengelola data

### User Access
- **URL**: `/games/user`
- **Fitur**: Melihat daftar game dengan pagination dan sorting

## 📊 Struktur Database

### Tabel Users
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) DEFAULT 'USER'
);
```

### Tabel Games
```sql
CREATE TABLE games (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    genre VARCHAR(255) NOT NULL,
    release_date DATE,
    developer VARCHAR(255) NOT NULL,
    description TEXT,
    image_url VARCHAR(500),
    youtube_url VARCHAR(500),
    rating DOUBLE,
    platform VARCHAR(255) NOT NULL
);
```

## 🎮 Fitur Game Management

### Informasi Game
- Nama game (wajib diisi, maksimal 100 karakter)
- Genre game (wajib diisi)
- Tanggal rilis
- Developer/publisher
- Deskripsi lengkap
- URL gambar dan video YouTube
- Rating (0-5)
- Platform

### Validasi Input
- Validasi format URL untuk gambar dan YouTube
- Rating harus dalam rentang 0-5
- Tanggal rilis tidak boleh di masa depan
- Field wajib tidak boleh kosong

## 🔒 Sistem Keamanan

- **Spring Security** untuk autentikasi
- **Password encoding** untuk keamanan password
- **Role-based authorization**:
  - `USER`: Akses baca game
  - `ADMIN`: Akses penuh CRUD operations
- **Session management** dan **CSRF protection**

## 🎨 UI Templates

Aplikasi menggunakan template Thymeleaf dengan layout yang konsisten:

- `login.html` - Halaman login
- `register.html` - Halaman registrasi
- `game-list.html` - Daftar game (Admin)
- `games-user.html` - Daftar game (User)
- `game-detail.html` - Detail game
- `game-form.html` - Form tambah/edit game
- `layout.html` - Template layout utama

## 📝 API Endpoints

| Method | Endpoint | Deskripsi |
|--------|----------|-----------|
| GET | `/` | Redirect ke login |
| GET | `/login` | Halaman login |
| GET | `/register` | Halaman registrasi |
| GET | `/games/admin` | Daftar game (Admin) |
| GET | `/games/user` | Daftar game (User) |
| GET | `/games/{id}` | Detail game |
| GET | `/games/new` | Form tambah game |
| POST | `/games` | Simpan game baru |
| GET | `/games/edit/{id}` | Form edit game |
| POST | `/games/{id}` | Update game |
| GET | `/games/delete/{id}` | Hapus game |

## 🔧 Development

### Menjalankan dalam Mode Development
```bash
mvn spring-boot:run
```

### Build untuk Production
```bash
mvn clean package
java -jar target/test3-0.0.1-SNAPSHOT.jar
```

### Testing
```bash
mvn test
```

## 📁 Struktur Project

```
src/
├── main/
│   ├── java/com/example/test3/
│   │   ├── controller/     # REST Controllers
│   │   ├── model/         # Entity classes
│   │   ├── repository/    # Data repositories
│   │   ├── service/       # Business logic
│   │   └── config/        # Configuration classes
│   └── resources/
│       ├── templates/     # Thymeleaf templates
│       ├── static/        # CSS, JS, images
│       └── application.properties
└── test/                  # Test classes
```

## 🚨 Troubleshooting

### Error Database Connection
- Pastikan MySQL server berjalan
- Periksa kredensial database di `application.properties`
- Verifikasi URL database sudah benar

### Error Build
- Pastikan Java 17 sudah terinstall
- Bersihkan cache Maven: `mvn clean`
- Update dependencies: `mvn dependency:resolve`

## 🤝 Contributing

1. Fork repository
2. Buat feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push ke branch (`git push origin feature/AmazingFeature`)
5. Buat Pull Request

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

## 📞 Contact

Email: your-email@example.com
Project Link: [https://github.com/your-username/test3](https://github.com/your-username/test3)