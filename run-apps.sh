#!/bin/bash

run_rest() {
  echo "Starting restservice (Spring Boot) app..."
  cd restservice || { echo "restservice directory not found"; exit 1; }
  
  # Install dependencies and skip tests
  mvn clean install -Dmaven.test.skip=true
  
  # Run the Spring Boot application
  mvn spring-boot:run &
  
  # Return to the root directory
  cd ..
}

run_soap() {
  echo "Starting soapservice (Spring Boot) app..."
  cd soapservice || { echo "soapservice directory not found"; exit 1; }
  
  # Install dependencies and skip tests
  mvn clean install -Dmaven.test.skip=true
  
  # Run the Spring Boot application
  mvn spring-boot:run &
  
  # Return to the root directory
  cd ..
}

# Main script starts here
echo "Running apps..."

# Run rest service
run_rest

# Run soap service
run_soap

echo "Both apps are running."

# Prevent the script from exiting (so application processes continue running)
wait
