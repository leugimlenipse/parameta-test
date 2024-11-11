#!/bin/bash

run_rest() {
  echo "Building restservice (Spring Boot) app..."
  cd restservice || { echo "restservice directory not found"; exit 1; }
  
  # Install dependencies and skip tests
  mvn clean install -Dmaven.test.skip=true

  # Return to the root directory
  cd ..
}

run_soap() {
  echo "Building soapservice (Spring Boot) app..."
  cd soapservice || { echo "soapservice directory not found"; exit 1; }
  
  # Install dependencies and skip tests
  mvn clean install -Dmaven.test.skip=true
  
  # Return to the root directory
  cd ..
}

# Main script starts here
echo "Building apps..."

# Run rest service
run_rest

# Run soap service
run_soap

echo "Both apps are built and ready to be run."

# Prevent the script from exiting (so application processes continue running)
wait
