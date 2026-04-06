package com.finance.system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Finance Data Processing and Access Control System API",
        version = "1.0.0",
        description = """
            A fintech-grade backend system for managing financial records with JWT-based authentication and role-based access control.
            
            **Features:**
            - JWT Token-based authentication
            - User management with role-based permissions (VIEWER, ANALYST, ADMIN)
            - Financial records CRUD operations with filtering and pagination
            - Dashboard analytics with aggregated financial data
            - Secure password hashing using BCrypt
            - Comprehensive validation and error handling
            
            **Role-Based Access:**
            - **VIEWER**: Read-only access to financial records
            - **ANALYST**: Read access + dashboard analytics
            - **ADMIN**: Full access including CRUD operations and user management
            
            **How to Use:**
            1. Login using POST /api/auth/login with username and password
            2. Copy the JWT token from the response
            3. Click "Authorize" button and enter: Bearer <your-token>
            4. Now you can access protected endpoints
            
            **Default Users:**
            - Admin: username=admin, password=admin123
            - Viewer: username=viewer, password=viewer123
            - Analyst: username=analyst, password=analyst123
            """,
        contact = @Contact(
            name = "Finance System Support",
            email = "support@financesystem.com"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Local Development Server")
    }
)
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT Bearer Token Authentication. Login first to get your token, then use it here.",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
