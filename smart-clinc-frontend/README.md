# Smart Clinic Management System - Frontend

A complete Angular 18 frontend application for the Smart Clinic Management System with user authentication, JWT-based security, and role-based access control.

## 🎯 Overview

This is a production-ready Angular 18 frontend designed to work with the Spring Boot backend. It includes:
- User registration with comprehensive form validation
- Secure login with JWT token authentication
- Role-based access control (ADMIN, DOCTOR, PATIENT)
- Protected routes with auth guards
- Modern, responsive UI with gradient design
- Real-time form validation
- Automatic token injection via HTTP interceptors

## ✨ Features

### Authentication & Security
- ✅ User registration with email, phone, and role selection
- ✅ Secure login with JWT tokens
- ✅ Automatic token storage in localStorage
- ✅ JWT token interceptor for API requests
- ✅ Protected routes with auth guards
- ✅ Automatic logout on token expiration

### User Interface
- ✅ Modern gradient design
- ✅ Responsive layout for all devices
- ✅ Real-time form validation
- ✅ Error handling and user feedback
- ✅ Loading states for async operations
- ✅ Role-based dashboard views

### Form Validation
- ✅ Email format validation
- ✅ Phone number pattern validation
- ✅ Password strength requirements
- ✅ Password confirmation matching
- ✅ Required field validation
- ✅ Character length limits

## 🏗️ Architecture

```
smart-clinc-frontend/
├── src/
│   ├── app/
│   │   ├── components/          # UI components
│   │   │   ├── login/          # Login component
│   │   │   ├── register/       # Registration component
│   │   │   └── dashboard/      # Dashboard component
│   │   ├── services/           # Business logic
│   │   │   └── auth.service.ts # Authentication service
│   │   ├── guards/             # Route guards
│   │   │   └── auth.guard.ts   # Authentication guard
│   │   ├── interceptors/       # HTTP interceptors
│   │   │   └── auth.interceptor.ts # JWT token interceptor
│   │   ├── models/             # Data models
│   │   │   └── user.model.ts   # User and auth models
│   │   ├── app.module.ts       # Root module
│   │   ├── app-routing.module.ts # Routing configuration
│   │   └── app.component.ts    # Root component
│   ├── styles.css              # Global styles
│   ├── index.html              # HTML template
│   ├── main.ts                 # Application entry point
│   └── polyfills.ts            # Polyfills
├── package.json                # Dependencies
├── tsconfig.json               # TypeScript config
├── angular.json                # Angular CLI config
└── README.md                   # This file
```

## 🚀 Quick Start

### Prerequisites
- Node.js v25.8.2 or higher
- npm (comes with Node.js)
- Angular CLI 18.2.0

### Installation

1. **Navigate to the frontend directory**
   ```bash
   cd D:\Projectes\smart-clinic-management-sys\smart-clinc-frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start the development server**
   ```bash
   npm start
   ```

4. **Access the application**
   Open your browser and navigate to: `http://localhost:4200`

## 📚 API Integration

### Base URL
The frontend is configured to connect to the backend at:
```
http://localhost:8080/api
```

### Authentication Endpoints

#### Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "fullName": "John Doe",
  "email": "john@example.com",
  "phone": "+1234567890",
  "password": "SecurePass123",
  "confirmPassword": "SecurePass123",
  "role": "PATIENT"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "SecurePass123"
}
```

## 🔐 Security Features

- **JWT Token Storage**: Tokens are stored in localStorage
- **Automatic Token Injection**: HTTP interceptor adds Authorization header
- **Protected Routes**: Auth guard prevents unauthorized access
- **Form Validation**: Client-side validation before API calls
- **Error Handling**: Comprehensive error messages for users

## 📝 Available Routes

| Route | Component | Access | Description |
|-------|-----------|--------|-------------|
| `/` | Redirect | Public | Redirects to login |
| `/login` | LoginComponent | Public | User login page |
| `/register` | RegisterComponent | Public | User registration page |
| `/dashboard` | DashboardComponent | Protected | User dashboard (requires auth) |

## 👥 User Roles

| Role | Dashboard Features |
|------|-------------------|
| ADMIN | Full system administration |
| DOCTOR | Patient appointments and medical records |
| PATIENT | View appointments and personal records |

## 🛠️ Technology Stack

| Component | Version |
|-----------|---------|
| Angular | 18.2.0 |
| TypeScript | 5.5.4 |
| Node.js | 25.8.2 |
| RxJS | 7.8.1 |
| Zone.js | 0.14.10 |

## 🎨 UI Features

- **Gradient Background**: Modern purple gradient design
- **Card-based Layout**: Clean, centered authentication forms
- **Responsive Design**: Works on desktop, tablet, and mobile
- **Form Validation**: Real-time feedback with error messages
- **Loading States**: Visual feedback during API calls
- **Role Badges**: Color-coded role indicators
- **Alert Messages**: Success and error notifications

## 🔧 Configuration

### Update Backend URL
To change the backend API URL, edit `src/app/services/auth.service.ts`:
```typescript
private apiUrl = 'http://localhost:8080/api/auth';
```

### CORS Configuration
Ensure your backend CORS configuration allows requests from `http://localhost:4200`

## 🧪 Testing the Application

1. **Start the backend server** (Spring Boot)
   ```bash
   cd D:\Projectes\smart-clinic-management-sys\smart-clinc-backend
   mvn spring-boot:run
   ```

2. **Start the frontend server** (Angular)
   ```bash
   cd D:\Projectes\smart-clinic-management-sys\smart-clinc-frontend
   npm start
   ```

3. **Test Registration**
   - Navigate to `http://localhost:4200/register`
   - Fill in the registration form
   - Select a role (PATIENT, DOCTOR, or ADMIN)
   - Submit the form

4. **Test Login**
   - Navigate to `http://localhost:4200/login`
   - Enter your credentials
   - Submit the form

5. **Test Dashboard**
   - After successful login, you'll be redirected to the dashboard
   - Verify your user information and role are displayed
   - Test the logout functionality

## 📊 Form Validation Rules

### Registration
- **Full Name**: 2-100 characters, required
- **Email**: Valid email format, required
- **Phone**: Valid phone number (10+ digits, optional + prefix), required
- **Password**: 6-50 characters, required
- **Confirm Password**: Must match password, required
- **Role**: Must select PATIENT, DOCTOR, or ADMIN

### Login
- **Email**: Valid email format, required
- **Password**: Required

## 🐛 Troubleshooting

### Port 4200 Already in Use
```bash
# Windows
netstat -ano | findstr :4200
taskkill /PID <PID> /F

# Linux/macOS
lsof -ti:4200 | xargs kill -9
```

### Backend Connection Error
- Ensure the backend is running on port 8080
- Check CORS configuration in the backend
- Verify the API URL in auth.service.ts

### Build Errors
```bash
# Clear node_modules and reinstall
rm -rf node_modules
npm install

# Clear Angular cache
ng cache clean
```

## 🚀 Build for Production

```bash
# Build the application
npm run build

# The build artifacts will be stored in the dist/ directory
```

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Development Team

Smart Clinic Development Team

## 📧 Contact & Support

For issues or questions:
- Open an issue on GitHub
- Contact: support@smartclinic.com

---

**Status**: ✅ Production Ready  
**Last Updated**: May 2026  
**Version**: 1.0.0
