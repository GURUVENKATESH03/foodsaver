

## 🥗 Meal Share – Food Reduction Platform

**Meal Share** is a full-stack web application developed to address the growing issue of **food waste** by connecting food donors (restaurants, caterers, households) with individuals and NGOs in need. The platform streamlines surplus food sharing, ensures accountability, and promotes a sustainable ecosystem of food redistribution.

---

### 🌟 Key Objectives:

* Reduce food wastage by redirecting surplus food to the needy.
* Build a centralized platform for individuals, NGOs, and food providers to collaborate.
* Promote social good while ensuring operational efficiency and transparency.

---

### 🧩 Core Features:

#### 🔐 Authentication & User Roles

* Secure registration and login for **Donors**, **Receivers (NGOs/Individuals)**, and **Admins**.
* Role-based access control using **Spring Security**.

#### 🍱 Donor Module

* List surplus food with quantity, pickup location, and expiry details.
* View donation history and track request status.

#### 🙌 Receiver Module

* Browse available food donations based on location and availability.
* Request food and communicate with donors through the platform.

#### 📍 Maps Integration

* Live map view to locate nearby food sources using **Google Maps / Leaflet** API.

#### 📊 Dashboard & Analytics

* Admin dashboard with total donations, active users, and food waste reduction metrics.
* Donor and receiver dashboards showing transaction history and status.

#### 🔁 Notifications & Reminders

* Real-time alerts for new food listings and request approvals.
* Email/SMS reminders for pickups and expiring listings.

---

### 💻 Technologies Used:

#### Backend:

* **Java**, **Spring Boot**, **Spring Security**
* **RESTful API** architecture

#### Frontend:

* **React.js** or **Thymeleaf**
* **Tailwind CSS** for clean and responsive UI

#### Database:

* **MySQL** for structured storage of users, food listings, and requests

#### Messaging & Scheduling:

* **RabbitMQ** for OTP/email notifications (if implemented)
* **Quartz Scheduler** or cron jobs for reminder automation

---

### 📦 DevOps & Tools:

* **Docker** for containerization
* **Git & GitHub** for version control
* **Postman** for API testing
* **Jenkins / GitHub Actions** for CI/CD (optional)

---

### 📈 Impact:

* Encourages community participation in solving food insecurity.
* Helps organizations track and report on their food donation activities.
* Builds awareness and accountability around sustainable practices.

