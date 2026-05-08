# Expense Tracker API

## Project Objective
Build a secure, role-based expense tracking REST API that lets users record, categorize, and summarize their expenses while ensuring users can only access their own data.

## Key Features
- Users, Expenses, Categories with proper relationships
- CRUD operations for expenses and categories
- Role-based security with encoded passwords
- Ownership checks so users only modify their own data
- Layered architecture: Controller, Service, Repository

## Roles
- `ROLE_USER`: can manage only their own expenses and categories
- `ROLE_ADMIN`: can access any user’s data
