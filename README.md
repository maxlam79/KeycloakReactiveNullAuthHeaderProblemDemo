# Keycloak Reactive Null authHeader Problem Demo

This is a small project to demo the problem with:

error id: 9009f9b4-1d58-4011-9ff2-49b87bb59ddd-1: java.lang.NullPointerException: Cannot invoke "String.startsWith(String)" because "authHeader" is null

** With the use of quarkus-keycloak-admin-client-reactive. The application.properties is populated with the config fields for keycloak, please kindly change them accordingly.  