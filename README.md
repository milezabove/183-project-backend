# General Info

- Use branch "bad-example"
- Good examples (e.g. use of JWT, using JPA for database queries to prevent SQL injection) are commented out

# HOW TO USE

1. Clone the project
2. Create a database in Docker with the command: 
   ```bash
   docker run --name m183 -e POSTGRES_PASSWORD=m183 -e POSTGRES_USER=postgres -p 5432:5432 -d postgres
