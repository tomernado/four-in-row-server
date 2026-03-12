# 🔴🟡 Connect 4 - Multiplayer Java Backend Server

A robust, scalable, and concurrent backend server for the classic "Connect 4" game. 
This project was built to deepen my understanding of Object-Oriented Programming (OOP), multi-threading, and server architecture using raw Java and Apache Tomcat.

*(Note: This repository contains the Server-side logic. The JavaFX Client can be found [here](https://github.com/tomernado/four-in-row-client))*

## 🚀 Key Features & Architecture

* **Concurrency & Thread Safety:** Utilizes `ConcurrentHashMap` for global game state management and implements **Granular Locking** (`synchronized` blocks on specific game instances) to prevent race conditions without bottlenecking the entire server.
* **Design Patterns:** The `GameManager` is implemented as a **Singleton**, serving as the single source of truth for all active games in memory.
* **Optimized Algorithms:** The win-checking algorithm calculates only the immediate 4-axis radius around the last dropped disc (O(1) localized check), rather than scanning the entire 42-cell board (O(N)) on every turn.
* **REST-like API:** Clear separation of concerns with Jakarta Servlets handling HTTP GET/POST requests and isolating the core game logic.

## 🛠️ Tech Stack
* **Language:** Java 17+
* **Server:** Apache Tomcat & Jakarta Servlets
* **API Testing:** Postman
* **State Management:** In-Memory (ConcurrentHashMap)

## 📡 API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/create?code={code}&playerId={id}` | Creates a new game instance. |
| `POST` | `/api/join?code={code}&playerId={id}` | Joins an existing game. |
| `POST` | `/api/move?code={code}&playerId={id}&col={col}` | Drops a disc in the specified column. |
| `GET` | `/api/state?code={code}&playerId={id}` | Returns the current board state and turn info. |
| `GET` | `/api/ping` | Health check for the server. |

## 💻 How to Run
1. Clone the repository: `git clone https://github.com/tomernado/four-in-row-server.git`
2. Deploy the project on a local **Apache Tomcat** server (Default port: 8080).
3. Use Postman or the accompanying JavaFX client to interact with the API endpoints.
