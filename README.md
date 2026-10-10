# B2B Vendor Quote Matcher

A full-stack application that helps businesses process vendor quotations, match similar products, and compare prices to identify potential cost savings.

## Key Features

- **Secure Authentication:** User registration and login using Spring Security and JWT.
- **Quote Upload:** Upload vendor quotation PDFs for processing.
- **Asynchronous Processing:** Apache Kafka supports event-driven quote processing.
- **AI-Powered Extraction:** Spring AI with Ollama extracts structured information from unstructured quote documents.
- **Semantic Product Matching:** PostgreSQL with pgvector helps identify similar products even when descriptions differ.
- **Price Comparison:** Compare quotations and identify price differences between vendors.
- **Full-Stack Dashboard:** React frontend connected to a Spring Boot REST API.

## Tech Stack

**Backend:** Java, Spring Boot, Spring Security, Spring AI, Apache Kafka, Maven

**Frontend:** React, JavaScript, Vite, CSS

**Database:** PostgreSQL, pgvector

**AI/LLM:** Ollama, Llama 3.2, Nomic Embeddings

**Tools:** Docker, Git, GitHub, IntelliJ IDEA

## Application Workflow

1. A user logs in and uploads a vendor quotation.
2. The backend records the quote and sends an event to Kafka.
3. The processing pipeline extracts text from the PDF.
4. An AI model converts the extracted information into structured data.
5. Product descriptions are converted into embeddings and matched semantically.
6. Users compare quotations to review price differences and potential savings.

## Getting Started

### Prerequisites

- Java and Maven
- Node.js and npm
- PostgreSQL with pgvector
- Docker
- Ollama with the required models downloaded

### Configuration

Configure the database connection, database password, JWT secret, Ollama URL, and model settings in `src/main/resources/application.properties`.

Keep passwords and secrets in environment variables. Never commit real credentials to GitHub.

### Run the Backend

From the project root:

```bash
./mvnw spring-boot:run
```

On Windows, use:

```bash
mvnw.cmd spring-boot:run
```

### Run the Frontend

Open a separate terminal:

```bash
cd vendor-quote-frontend
npm install
npm run dev
```

Open the local URL displayed by Vite in your terminal.

> Ensure PostgreSQL, Kafka, and Ollama are running and correctly configured before starting the application.

## What I Learned

- Building REST APIs with Spring Boot
- Implementing JWT-based authentication
- Designing asynchronous processing with Apache Kafka
- Integrating LLM-based structured data extraction
- Using vector embeddings for semantic similarity search
- Connecting a React frontend to a Java backend
- Managing application infrastructure with Docker

## Future Improvements

- Add comprehensive unit and integration tests
- Improve extraction accuracy and error handling
- Add more detailed quote analytics and reporting
- Document API endpoints and deployment configuration
