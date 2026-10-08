# Calculator Web Application

A simple four-operation calculator built with **Java 17** and **Jakarta Servlets**, packaged as a WAR for deployment on **Apache Tomcat 10+**.

---

## What the app does

| Feature | Detail |
|---------|--------|
| Operations | Add, Subtract, Multiply, Divide |
| Input | Two numbers + operation chosen from a dropdown |
| Output | Result shown on a dedicated result page |
| Error handling | HTTP 400 for missing/non-numeric parameters and divide-by-zero |

---

## Prerequisites

| Tool | Minimum version |
|------|----------------|
| Java | 17 |
| Maven | 3.8+ |
| Tomcat | 10.1+ (Jakarta EE 9+) |

> **Java 17 is required.** The POM sets `maven.compiler.release=17`.

---

## Build

```bash
mvn clean verify
```

A successful build prints **BUILD SUCCESS** and produces:

```
target/calculator.war
```

---

## Run tests only

```bash
mvn test
```

---

## Deploy to Tomcat

1. Copy the WAR to Tomcat's webapps directory:

   ```bash
   cp target/calculator.war /path/to/tomcat/webapps/
   ```

2. Start (or restart) Tomcat:

   ```bash
   /path/to/tomcat/bin/startup.sh   # Linux/macOS
   /path/to/tomcat/bin/startup.bat  # Windows
   ```

3. Open your browser:

   ```
   http://localhost:8080/calculator/index.html
   ```

---

## Analyse with SonarQube

Make sure a SonarQube instance is running on `http://localhost:9000`, then run:

```bash
mvn clean verify sonar:sonar -Dsonar.token=YOUR_TOKEN
```

> Replace `YOUR_TOKEN` with a **User Token** generated in SonarQube → My Account → Security.  
> Never commit a real token to version control.

---

## Push to GitHub

```bash
git init
git add .
git commit -m "Initial commit: calculator web app"
git remote add origin https://github.com/YOUR_USERNAME/calculator.git
git push -u origin main
```

---

## Project structure

```
calculator/
├── pom.xml
├── .gitignore
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── Calculator.java        # Pure-Java arithmetic logic
│   │   │   └── CalculatorServlet.java # Jakarta Servlet (@WebServlet)
│   │   └── webapp/
│   │       └── index.html             # Calculator HTML form
│   └── test/
│       └── java/com/example/
│           └── CalculatorTest.java    # JUnit 5 unit tests
└── target/
    └── calculator.war                 # Generated after mvn clean verify
```
