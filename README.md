# Shopping Application

This is a simple RESTful API for managing products and cart.

## How to Run the Application

1. **Prerequisites**:
    - Java Development Kit (JDK) installed
    - Maven installed
    - Git installed (optional)

2. **Clone the Repository**


To run the application locally using Docker Compose, follow these steps:

1. Make sure you have Docker and Docker Compose installed on your system.
2. Run Docker Compose to build and start the application containers:
   ```bash
   docker-compose up --build

5. **Access the Application**:
- The application will be running at `http://localhost:8080`.

## Examples of Endpoint Usage

### 1. Get All Products

- **Endpoint**: `GET /api/v1/products`
- **Description**: Retrieves a list of all products.


### 2. Get Product by ID

- **Endpoint**: `GET /api/v1/products/{id}`
- **Description**: Retrieves a product by its ID.


### 3. Create a New Product

- **Endpoint**: `POST /api/v1/products`
- **Description**: Creates a new product.


### 5. Delete Product

- **Endpoint**: `DELETE /api/v1/products/{id}`
- **Description**: Deletes a product by its ID.
- **Example**:

