# 🏆 CP Contest Tracker Bot

A real-time Telegram Bot built with **Java** and **Spring Boot** that acts as a centralized dashboard for Competitive Programming (CP) students. It fetches live, upcoming contest schedules from platforms like Codeforces, LeetCode, CodeChef, and HackerRank.

## 🚀 Features
* **Global Aggregation:** Fetches live data from the Clist.by REST API for all major coding platforms.
* **Smart Commands:**
    * `/contests` - Returns a list of upcoming global coding competitions.
    * `/tomorrow` - Filters and shows events starting within the next 24 hours.
* **Timezone Conversion:** Automatically converts server-side UTC timestamps into readable Indian Standard Time (IST) formats.
* **Bot-to-API Security:** Implements custom HTTP headers to securely pass authentication tokens and bypass anti-bot mechanisms.

## 🛠️ Tech Stack
* **Language:** Java 17
* **Framework:** Spring Boot 3.2.x
* **Build Tool:** Maven
* **APIs:** Telegram Bots API, Clist.by REST API
* **Networking:** Spring `RestTemplate`, JSON Parsing (`Jackson`)

## 🧠 What I Learned (First-Year Project)
Building this project was a deep dive into backend engineering and API integration:
1. **REST API Integration:** Learned how to consume third-party JSON data, map it to Java Records, and handle network timeouts.
2. **Authentication & Headers:** Handled `401 Unauthorized` and `403 Forbidden` (Cloudflare) errors by injecting `User-Agent` and custom `Authorization` API Keys into HTTP headers.
3. **Spring Framework Architecture:** Grasped the core concepts of Dependency Injection (`@Component`, `@Service`, `@Bean`) to separate business logic from the Telegram UI layer.
4. **Date & Time Manipulation:** Handled complex timezone parsing using Java's `ZonedDateTime` to convert raw UTC strings to local time formats.

## ⚙️ Local Setup & Installation

### Prerequisites
* Java Development Kit (JDK) 17+
* Maven installed
* A Telegram Bot Token (from [@BotFather](https://t.me/botfather))
* A Clist.By API Key (from [clist.by/api/v4/doc](https://clist.by/api/v4/doc/))

### Steps to Run
1. **Clone the repository:**
   ```bash
   git clone [https://github.com/tyagiakshit-boop/cp-tracker-bot.git](https://github.com/tyagiakshit-boop/cp-tracker-bot.git)
   cd cp-tracker-bot