Technical Debit Tracker
===================================

A Java-based application for Tracking Technical Debits using MongoDB.

Prerequisites:
- Java JDK 8 or higher
- MongoDB installed and running on localhost:27017

Setup Instructions:
1. Ensure MongoDB is running
2. Compile the project using the provided commands
3. Run Main.java to start the application

Compilation:
javac -cp "lib/*" -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })

Execution:
java -cp "out;lib/*" com.example.tdt.Main

Features:
1. Allows logging and categorizing technical debt items by type and severity.
2. Manages priority levels and tracks debt status from open to resolved.
3. Supports assignment of debt items to developers and project modules.
4. Uses MongoDB for flexible and scalable storage of debt records.
5. Provides search and filter options based on priority, status, and module.
