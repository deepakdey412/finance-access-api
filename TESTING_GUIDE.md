# Finance System - Complete Testing Guide

## 📋 Table of Contents
1. [Quick Start](#quick-start)
2. [Testing with Swagger UI](#testing-with-swagger-ui)
3. [Testing Scenarios](#testing-scenarios)
4. [Database Console Access](#database-console-access)

---

## 🚀 Quick Start

**Prerequisites:**
- Application is running on `http://localhost:8080`
- Default users are available:
  - **admin** / admin123 (Full access)
  - **analyst** / analyst123 (Read + Dashboard)
  - **viewer** / viewer123 (Read only)

**Authentication Method:**
- This application uses **JWT (JSON Web Token)** authentication
- You must first login to get a token, then use that token for all API requests

---

## 🎯 Testing with Swagger UI

Swagger UI provides an interactive interface to test all APIs without writing any code or commands.

### Step 1: Start the Application

1. Open terminal/command prompt
2. Navigate to project directory
3. Run: `mvnw.cmd spring-boot:run` (Windows) or `./mvnw spring-boot:run` (Linux/Mac)
4. Wait for the message: "Started FinanceSystemApplication"

### Step 2: Open Swagger UI

1. Open your web browser
2. Go to: **http://localhost:8080/swagger-ui.html**
3. You'll see the complete API documentation with all endpoints

### Step 3: Login to Get JWT Token

1. Scroll down to find the **"Authentication"** section
2. Click on **POST /api/auth/login** to expand it
3. Click the **"Try it out"** button (top right of the endpoint)
4. You'll see a request body editor with example JSON
5. Replace it with:
```json
{
  "username": "admin",
  "password": "admin123"
}
```
6. Click the blue **"Execute"** button
7. Scroll down to see the response
8. In the response body, you'll see something like:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0...",
  "userId": 1,
  "username": "admin",
  "role": "ADMIN"
}
```
9. **Copy the entire token value** (the long string after "token":)

### Step 4: Authorize with JWT Token

1. Look at the top right of the page
2. Click the **"Authorize"** button (it has a lock icon 🔒)
3. A popup window will appear
4. In the **"Value"** field, type: `Bearer ` (with a space after Bearer)
5. Paste your token after the space
   - Example: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0...`
6. Click **"Authorize"**
7. Click **"Close"**
8. You'll see the lock icon is now closed 🔒 - you're authenticated!

---

## 📝 Testing Scenarios

### Scenario 1: Create Financial Records (ADMIN)

#### Create an Income Record

1. Find the **"Financial Records"** section
2. Click on **POST /api/records**
3. Click **"Try it out"**
4. Enter this JSON:
```json
{
  "amount": 5000.00,
  "type": "INCOME",
  "category": "Salary",
  "date": "2024-01-15",
  "description": "January salary"
}
```
5. Click **"Execute"**
6. Check the response:
   - **Response Code: 201** = Success! Record created
   - You'll see the created record with an ID in the response body

#### Create an Expense Record

1. Same endpoint: **POST /api/records**
2. Click **"Try it out"**
3. Enter this JSON:
```json
{
  "amount": 1200.00,
  "type": "EXPENSE",
  "category": "Rent",
  "date": "2024-01-05",
  "description": "Monthly rent payment"
}
```
4. Click **"Execute"**
5. Check response code: **201 Created**

#### Create More Sample Records

Create a few more records to test filtering and analytics:

**Freelance Income:**
```json
{
  "amount": 3000.00,
  "type": "INCOME",
  "category": "Freelance",
  "date": "2024-01-20",
  "description": "Project payment"
}
```

**Groceries Expense:**
```json
{
  "amount": 500.00,
  "type": "EXPENSE",
  "category": "Groceries",
  "date": "2024-01-10",
  "description": "Monthly groceries"
}
```

**Utilities Expense:**
```json
{
  "amount": 200.00,
  "type": "EXPENSE",
  "category": "Utilities",
  "date": "2024-01-12",
  "description": "Electricity bill"
}
```

### Scenario 2: View All Records (Any Role)

1. Find **GET /api/records/all**
2. Click **"Try it out"**
3. Click **"Execute"**
4. You'll see an array of all financial records
5. Response code: **200 OK**

### Scenario 3: View Records with Pagination

1. Find **GET /api/records** (with query parameters)
2. Click **"Try it out"**
3. You'll see optional parameters:
   - **page**: Enter `0` (first page)
   - **size**: Enter `5` (5 records per page)
   - Leave other fields empty for now
4. Click **"Execute"**
5. Response shows paginated results with metadata:
   - `content`: Array of records
   - `totalPages`: Total number of pages
   - `totalElements`: Total number of records
   - `size`: Records per page
   - `number`: Current page number

### Scenario 4: Filter Records

#### Filter by Type (INCOME only)

1. **GET /api/records**
2. Click **"Try it out"**
3. In the **type** field, select or enter: `INCOME`
4. Click **"Execute"**
5. You'll see only income records

#### Filter by Date Range

1. **GET /api/records**
2. Click **"Try it out"**
3. Enter:
   - **startDate**: `2024-01-01`
   - **endDate**: `2024-01-31`
4. Click **"Execute"**
5. You'll see records from January 2024 only

#### Filter by Category

1. **GET /api/records**
2. Click **"Try it out"**
3. In the **category** field, enter: `Salary`
4. Click **"Execute"**
5. You'll see only salary records

#### Combined Filters

1. **GET /api/records**
2. Click **"Try it out"**
3. Enter:
   - **startDate**: `2024-01-01`
   - **endDate**: `2024-12-31`
   - **type**: `EXPENSE`
   - **page**: `0`
   - **size**: `10`
4. Click **"Execute"**
5. You'll see paginated expense records for 2024

### Scenario 5: View Single Record

1. Find **GET /api/records/{id}**
2. Click **"Try it out"**
3. In the **id** field, enter: `1`
4. Click **"Execute"**
5. You'll see the details of record with ID 1
6. Response code: **200 OK**

### Scenario 6: Update a Record (ADMIN only)

1. Find **PUT /api/records/{id}**
2. Click **"Try it out"**
3. In the **id** field, enter: `1`
4. In the request body, enter:
```json
{
  "amount": 5500.00,
  "description": "Updated salary amount"
}
```
5. Click **"Execute"**
6. Response code: **200 OK**
7. You'll see the updated record

### Scenario 7: Dashboard Analytics (ANALYST/ADMIN)

#### Get Complete Dashboard Summary

1. Find the **"Dashboard Analytics"** section
2. Click on **GET /api/dashboard/summary**
3. Click **"Try it out"**
4. Click **"Execute"**
5. You'll see complete financial summary:
```json
{
  "totalIncome": 8000.00,
  "totalExpense": 1900.00,
  "netBalance": 6100.00,
  "categoryTotals": [...],
  "monthlyTrends": [...],
  "recentTransactions": [...]
}
```

#### Get Total Income

1. **GET /api/dashboard/income**
2. Click **"Try it out"**
3. Click **"Execute"**
4. Response: `8000.00`

#### Get Total Expense

1. **GET /api/dashboard/expense**
2. Click **"Try it out"**
3. Click **"Execute"**
4. Response: `1900.00`

#### Get Net Balance

1. **GET /api/dashboard/balance**
2. Click **"Try it out"**
3. Click **"Execute"**
4. Response: `6100.00`

#### Get Category-wise Totals

1. **GET /api/dashboard/category-totals**
2. Click **"Try it out"**
3. Click **"Execute"**
4. You'll see breakdown by category:
```json
[
  {
    "category": "Salary",
    "type": "INCOME",
    "total": 5000.00
  },
  {
    "category": "Rent",
    "type": "EXPENSE",
    "total": 1200.00
  }
]
```

#### Get Monthly Trends

1. **GET /api/dashboard/monthly-trends**
2. Click **"Try it out"**
3. Click **"Execute"**
4. You'll see month-by-month breakdown:
```json
[
  {
    "year": 2024,
    "month": 1,
    "income": 8000.00,
    "expense": 1900.00,
    "net": 6100.00
  }
]
```

#### Get Recent Transactions

1. **GET /api/dashboard/recent**
2. Click **"Try it out"**
3. Click **"Execute"**
4. You'll see the 10 most recent transactions

### Scenario 8: User Management (ADMIN only)

#### Create a New User

1. Find the **"User Management"** section
2. Click on **POST /api/users**
3. Click **"Try it out"**
4. Enter:
```json
{
  "username": "john",
  "password": "john12345",
  "role": "ANALYST"
}
```
5. Click **"Execute"**
6. Response code: **201 Created**
7. You'll see the new user details (password will be hashed)

#### Get All Users

1. **GET /api/users**
2. Click **"Try it out"**
3. Click **"Execute"**
4. You'll see list of all users

#### Update User Role

1. **PUT /api/users/{id}**
2. Click **"Try it out"**
3. In the **id** field, enter the user ID (e.g., `4`)
4. Enter:
```json
{
  "role": "ADMIN",
  "active": true
}
```
5. Click **"Execute"**
6. User role is updated

#### Delete a User

1. **DELETE /api/users/{id}**
2. Click **"Try it out"**
3. In the **id** field, enter the user ID
4. Click **"Execute"**
5. Response code: **204 No Content** (success)

### Scenario 9: Test Different User Roles

#### Test as VIEWER (Read-only)

1. Click **"Authorize"** button again
2. Click **"Logout"** to clear current token
3. Go back to **POST /api/auth/login**
4. Login with:
```json
{
  "username": "viewer",
  "password": "viewer123"
}
```
5. Copy the new token
6. Click **"Authorize"** and enter: `Bearer <new-token>`
7. Now try these:
   - ✅ **GET /api/records/all** - Should work (200 OK)
   - ✅ **GET /api/records/{id}** - Should work (200 OK)
   - ❌ **POST /api/records** - Should fail (403 Forbidden)
   - ❌ **GET /api/dashboard/summary** - Should fail (403 Forbidden)

#### Test as ANALYST (Read + Dashboard)

1. Logout and login with:
```json
{
  "username": "analyst",
  "password": "analyst123"
}
```
2. Authorize with the new token
3. Now try these:
   - ✅ **GET /api/records/all** - Should work (200 OK)
   - ✅ **GET /api/dashboard/summary** - Should work (200 OK)
   - ❌ **POST /api/records** - Should fail (403 Forbidden)
   - ❌ **POST /api/users** - Should fail (403 Forbidden)

### Scenario 10: Test Validation

#### Missing Required Field

1. Login as admin
2. **POST /api/records**
3. Try to create with missing amount:
```json
{
  "type": "INCOME",
  "category": "Salary",
  "date": "2024-01-15"
}
```
4. Response code: **400 Bad Request**
5. Error message shows: "amount: Amount is required"

#### Negative Amount

1. **POST /api/records**
2. Try with negative amount:
```json
{
  "amount": -100.00,
  "type": "INCOME",
  "category": "Salary",
  "date": "2024-01-15"
}
```
3. Response code: **400 Bad Request**
4. Error message shows validation error

#### Short Password

1. **POST /api/users**
2. Try with short password:
```json
{
  "username": "test",
  "password": "123",
  "role": "VIEWER"
}
```
3. Response code: **400 Bad Request**
4. Error message shows: "password: Password must be at least 8 characters"

#### Duplicate Username

1. **POST /api/users**
2. Try to create user with existing username:
```json
{
  "username": "admin",
  "password": "password123",
  "role": "VIEWER"
}
```
3. Response code: **400 Bad Request**
4. Error message shows: "Username already exists"

### Scenario 11: Test Error Handling

#### Non-existent Record

1. **GET /api/records/{id}**
2. Enter ID: `9999`
3. Click **"Execute"**
4. Response code: **404 Not Found**
5. Error message: "Financial record not found"

#### Invalid Token

1. Click **"Authorize"**
2. Enter: `Bearer invalid-token-12345`
3. Try any protected endpoint
4. Response code: **401 Unauthorized**

#### No Token

1. Click **"Authorize"**
2. Click **"Logout"**
3. Try any protected endpoint (without authorizing)
4. Response code: **401 Unauthorized**

---

## 🗄️ Database Console Access

### Step 1: Open H2 Console

1. Open browser
2. Go to: **http://localhost:8080/h2-console**
3. You'll see the H2 login page

### Step 2: Login to Database

1. Enter these details:
   - **JDBC URL**: `jdbc:h2:mem:financedb`
   - **User Name**: `sa`
   - **Password**: (leave empty)
2. Click **"Connect"**

### Step 3: Run SQL Queries

#### View All Users

```sql
SELECT * FROM USERS;
```

Click **"Run"** - You'll see all users with their hashed passwords

#### View All Financial Records

```sql
SELECT * FROM FINANCIAL_RECORDS;
```

#### Get Income Summary

```sql
SELECT SUM(AMOUNT) AS TOTAL_INCOME 
FROM FINANCIAL_RECORDS 
WHERE TYPE = 'INCOME' AND IS_DELETED = FALSE;
```

#### Get Expense Summary

```sql
SELECT SUM(AMOUNT) AS TOTAL_EXPENSE 
FROM FINANCIAL_RECORDS 
WHERE TYPE = 'EXPENSE' AND IS_DELETED = FALSE;
```

#### Get Category Breakdown

```sql
SELECT CATEGORY, TYPE, SUM(AMOUNT) AS TOTAL 
FROM FINANCIAL_RECORDS 
WHERE IS_DELETED = FALSE 
GROUP BY CATEGORY, TYPE 
ORDER BY TOTAL DESC;
```

#### Get Monthly Summary

```sql
SELECT 
    YEAR(DATE) AS YEAR, 
    MONTH(DATE) AS MONTH, 
    TYPE,
    SUM(AMOUNT) AS TOTAL 
FROM FINANCIAL_RECORDS 
WHERE IS_DELETED = FALSE 
GROUP BY YEAR(DATE), MONTH(DATE), TYPE 
ORDER BY YEAR, MONTH;
```

---

## 📊 Understanding Response Codes

- **200 OK**: Request successful, data returned
- **201 Created**: Resource created successfully
- **204 No Content**: Request successful, no data to return (e.g., delete)
- **400 Bad Request**: Validation error or invalid input
- **401 Unauthorized**: Missing or invalid authentication token
- **403 Forbidden**: User doesn't have permission for this action
- **404 Not Found**: Resource doesn't exist
- **500 Internal Server Error**: Server error (check logs)

---

## 📝 Tips for Testing

1. **Always login first** to get your JWT token
2. **JWT tokens expire after 24 hours** - login again if you get 401 errors
3. **Copy the complete token** when authorizing
4. **Include "Bearer "** (with space) before your token
5. **Test with different roles** to verify access control
6. **Create multiple records** before testing analytics
7. **Use the H2 console** to verify data is saved correctly
8. **Check response codes** to understand what happened
9. **Read error messages** - they tell you exactly what's wrong
10. **Use Swagger's "Try it out"** feature - it's the easiest way to test

---

## ✅ Complete Testing Checklist

- [ ] Application is running on port 8080
- [ ] Can access Swagger UI at http://localhost:8080/swagger-ui.html
- [ ] Can login and get JWT token from POST /api/auth/login
- [ ] Can authorize in Swagger with Bearer token
- [ ] Can create financial records as admin
- [ ] Can view all records
- [ ] Can filter records by date, type, category
- [ ] Can view single record by ID
- [ ] Can update records as admin
- [ ] Can delete records as admin
- [ ] Can access dashboard summary as analyst
- [ ] Can view all dashboard endpoints
- [ ] Can create users as admin
- [ ] Can update user roles as admin
- [ ] VIEWER cannot create records (403)
- [ ] VIEWER cannot access dashboard (403)
- [ ] ANALYST cannot create records (403)
- [ ] ANALYST can access dashboard (200)
- [ ] Validation errors return 400
- [ ] Invalid/missing token returns 401
- [ ] Can access H2 console
- [ ] Can run SQL queries in H2 console

---

## 🎉 You're All Set!

You now know how to test the entire Finance System using Swagger UI. The interactive interface makes it easy to test all features without writing any code.

**Happy Testing!**
