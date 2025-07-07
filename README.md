# FealtyX - Student CRUD API with Ollama Integration

This project is a simple RESTful API built using Java to manage a list of students. It supports basic CRUD operations and integrates with the Ollama AI (Llama3 model) to generate AI-based summaries for individual students.

## 🧠 Features

- Create, Read, Update, Delete student records
- In-memory data storage
- Ollama integration for generating student summaries
- Proper error handling & input validation
- Safe for concurrent API requests

---

## 📚 Student Model

Each student contains the following fields:

- `ID` (integer)
- `Name` (string)
- `Age` (integer)
- `Email` (string)
- `summary` (string)

---

## 🚀 API Endpoints

| Method | Endpoint                   | Description                        |
|--------|----------------------------|------------------------------------|
| POST   | `/students`                | Create a new student               |
| GET    | `/students`                | Get all students                   |
| GET    | `/students/{id}`           | Get a student by ID                |
| PUT    | `/students/{id}`           | Update a student by ID             |
| DELETE | `/students/{id}`           | Delete a student by ID             |
| POST    | `/students/{id}/summary`   | Get AI-generated summary via Ollama|

---

## 🔧 How to Run

1. Make sure java is installed.

2. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/fealtyx-student-api.git
   cd fealtyx-student-api

