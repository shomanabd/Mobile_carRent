

# 🚗 Mobile Car Rental Application

## 📱 Overview
An Android application that enables users to browse, search, and reserve rental cars. The app supports user authentication, car search by brand and date, detailed car listings, and secure booking confirmation with payment information.

---

## ✨ Features

### 🔐 User Authentication
- Register new accounts
- Login with email and password
- Reset forgotten passwords via Firebase Authentication

### 🚘 Car Search & Reservation
- Filter available cars by **brand** and **date range**
- View car details: name, description, seating capacity, price, and image
- Confirm bookings with payment details

### ☁️ Backend & Cloud Integration
- Firebase Authentication for secure user management
- PHP-based backend with MySQL database for data storage
- RESTful API communication via **Volley**
- Fast image loading using **Glide**

---

## 🧱 Technical Stack

- **Language:** Java  
- **Platform:** Android  
- **Authentication:** Firebase  
- **Backend API:** PHP + MySQL  
- **Networking:** Volley  
- **Image Handling:** Glide  

---

## 📂 Project Structure

| File/Activity              | Description                                      |
|---------------------------|--------------------------------------------------|
| `CarModel.java`           | Data model for car objects                      |
| `ConfirmReservation.java` | Handles reservation confirmation and payment    |
| `ForgetPass.java`         | Triggers Firebase password reset process        |
| `ResetEmail.java`         | Manages email-based password reset              |
| `MainActivity.java`       | App entry point with navigation                 |
| `RecyclerView.java`       | Displays search results for available cars      |
| `RecyclerViewAdapter.java`| Adapter to bind car data to views               |
| `loggin.java`             | User login functionality                        |
| `singUp.java`             | Handles user registration                       |

---

## ⚙️ Setup Instructions

### 🔧 Prerequisites
- Android Studio (latest version)
- JDK 11 or higher
- Android SDK (min API level 26)
- Web server with PHP support (e.g., XAMPP/WAMP)
- Firebase account
- Internet connection

---

### 🌐 Backend Setup

1. Deploy the following PHP files to your web server under `/mobile/CarRent/`:
   - `select.php`: Fetch available cars
   - `insert.php`: Handle reservation submission
   - `log_in.php`: Authenticate users
   - `singUp.php`: Register new users

2. Create and configure your MySQL database

3. Update connection details inside each PHP script accordingly

---

### 🔥 Firebase Setup

1. Go to [Firebase Console](https://console.firebase.google.com/) and create a new project  
2. Add an Android app:
   - Package name: `com.example.mobile_carrent`
3. Download `google-services.json` and place it inside your app module directory  
4. Add Firebase SDK to `build.gradle`:
   ```groovy
   implementation 'com.google.firebase:firebase-auth:XX.X.X'
   apply plugin: 'com.google.gms.google-services'
   ```

---

### 📱 Android Project Setup

1. Clone or download the source code
2. Open it in Android Studio
3. Wait for Gradle sync to complete
4. Ensure your server IPs are configured correctly in the following files:

   - `RecyclerView.java`:
     ```java
     private String baseUrl = "http://YOUR_SERVER_IP/mobile/CarRent/select.php?type=";
     ```
   - `ConfirmReservation.java`:
     ```java
     private String baseUrl = "http://YOUR_SERVER_IP/mobile/CarRent/insert.php?Car_ID=";
     ```
   - `loggin.java`:
     ```java
     String url = "http://YOUR_SERVER_IP/mobile/CarRent/log_in.php";
     ```
   - `singUp.java`:
     ```java
     String url = "http://YOUR_SERVER_IP/mobile/CarRent/singUp.php";
     ```

---

### ▶️ Running the App

1. Connect an Android device or emulator
2. Ensure USB debugging is enabled (for physical devices)
3. Click **Run** in Android Studio or press `Shift + F10`
4. Select your device and deploy the app

---

## 🧪 Testing the App

1. **Sign Up** using a new account  
2. **Login** with your credentials  
3. Select a **car brand** and **rental date range**  
4. Click **Search** to view available cars  
5. Choose a car and proceed to **booking confirmation**  
6. Enter payment details and confirm the reservation  

---

## 🌐 API Endpoints

| Endpoint        | Description                        |
|----------------|------------------------------------|
| `select.php`    | Fetch cars by brand and date       |
| `insert.php`    | Submit new reservation             |
| `log_in.php`    | Handle user login                  |
| `singUp.php`    | Handle user registration           |

---

## 🔄 User Flow

1. User launches the app and logs in or registers  
2. Searches for cars by brand and dates  
3. Views available car listings  
4. Selects a car and confirms booking with payment info  
5. Reservation is stored via backend API  

---

## 🛠️ Troubleshooting

- **Network Issues**: Check server IP and PHP accessibility  
- **Firebase Errors**: Review Firebase Console for logs  
- **Volley Failures**: Ensure valid JSON responses from backend  
- **Crashes**: Use Logcat for debugging  

---

## 📚 Android Course Integration

This project demonstrates:
- Activity navigation using **Intents**
- Data display using **RecyclerView**
- **Firebase Authentication** implementation
- Use of **Volley** for API requests
- Date selection via **DatePickerDialog**
- Dynamic image loading with **Glide**

### Team Collaboration
- Use Git branches for feature isolation  
- Assign modules (UI, API, Auth, Payment) per member  
- Conduct regular pull requests and code reviews  

---

## 🚀 Future Enhancements

- Filter by price, features, and car types  
- Add **user profiles** and **reservation history**  
- Enable **ratings and reviews** for cars  
- Integrate **Google Maps** for pickup location visualization  
- Improve **UI/UX** with animations and transitions  

---


