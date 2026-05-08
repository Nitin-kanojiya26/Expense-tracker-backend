package com.nitin.expense.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpenseTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerApplication.class, args);
        String banner = """
BASE URL: http://localhost:8080
HEADER: Content-Type: application/json
AUTH (for all endpoints except register/login):
Postman -> Authorization -> Basic Auth
Username: demo
Password: demo123

REGISTER USER (POST /api/users/register)  OR  (POST /api/auth/register)
{
  "username": "demo",
  "password": "demo123",
  "email": "demo@test.com",
  "fullName": "Demo User"
}

LOGIN (POST /api/users/login)  OR  (POST /api/auth/login)
{
  "username": "demo",
  "password": "demo123"
}

NOTE:
- Use the authenticated user's id in {userId} unless you are ROLE_ADMIN.
- Admin-only endpoints require a user with ROLE_ADMIN.

CREATE CATEGORY (POST /api/categories/user/{userId})
{
  "name": "Food",
  "description": "All food expenses"
}

UPDATE CATEGORY (PUT /api/categories/{categoryId})
{
  "name": "Groceries",
  "description": "Supermarket only"
}

CREATE EXPENSE (POST /api/expenses/user/{userId}?categoryId={categoryId})
{
  "description": "Lunch",
  "amount": 120,
  "date": "2026-03-22",
  "notes": "Office canteen"
}

UPDATE EXPENSE (PUT /api/expenses/{expenseId}?categoryId={categoryId})
{
  "description": "Dinner",
  "amount": 250.00,
  "notes": "Client meeting"
}

(NO BODY) GET /api/categories/user/{userId}
(NO BODY) GET /api/expenses/user/{userId}
(NO BODY) GET /api/expenses/user/{userId}/summary?startDate=2026-03-01&endDate=2026-03-22
(NO BODY) DELETE /api/categories/{categoryId}
(NO BODY) DELETE /api/expenses/{expenseId}

  for adding ADMIN : INSERT INTO user_roles (user_id, role) VALUES (YOUR_USER_ID, 'ROLE_ADMIN');

ADMIN: LOCK USER (PUT /api/admin/users/{userId}/lock)
ADMIN: UNLOCK USER (PUT /api/admin/users/{userId}/unlock)

END
""";
        System.out.println("\n" + banner);
    }
}
