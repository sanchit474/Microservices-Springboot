# Microservices Configuration - Fixed and Aligned

## Summary of Changes

### 1. API Gateway (ApiGateway)
**File: `ApiGateway/pom.xml`**
- ✅ Updated Spring Boot version: `4.0.5` → `4.0.3` (aligned with Hotel & User services)
- ✅ Confirmed Spring Cloud version: `2025.1.1` (consistent)
- ✅ Confirmed Java version: `17` (consistent)
- ✅ Updated groupId: `com.sb.APIGateway.microservices` → `com.sb.microservice.gateway`
- ✅ Added meaningful name: "API Gateway"
- ✅ Updated test dependency: `spring-boot-starter-webflux-test` → `spring-boot-starter-test`

**File: `ApiGateway/src/main/java/com/sb/microservice/ApiGateway/ApiGatewayApplication.java`**
- ✅ Added `@EnableDiscoveryClient` annotation for Eureka service registration
- ✅ Added import: `org.springframework.cloud.client.discovery.EnableDiscoveryClient`

**File: `ApiGateway/src/main/resources/application.yaml`**
- ✅ Added route filters: `StripPrefix=1` for proper path rewriting
- ✅ Enhanced Eureka instance configuration:
  - Added hostname configuration
  - Added instance-id pattern for better identification
  - Added registry fetch and replication intervals for faster updates
  - Fixed property name: `serviceUrl` → `service-url`
- ✅ Added DEBUG logging for Spring Cloud components
- ✅ Improved gateway route configuration for all three services (USER, HOTEL, RATING)

### 2. Rating Service (rating)
**File: `rating/pom.xml`**
- ✅ Updated Spring Boot version: `3.4.5` → `4.0.3` (aligned with other services)
- ✅ Updated Java version: `21` → `17` (aligned with other services)
- ✅ Updated Spring Cloud version: `2024.0.1` → `2025.1.1` (consistent)
- ✅ Updated groupId: `com.sb.rating` → `com.sb.microservice.rating`
- ✅ Added meaningful name: "Rating Service"

### 3. Service Consistency
All microservices now have:
- ✅ **Spring Boot**: `4.0.3`
- ✅ **Spring Cloud**: `2025.1.1`
- ✅ **Java**: `17`
- ✅ **Eureka Client**: Properly configured

### 4. Service Registry (Eureka)
The Service Registry (serviceregistry) remains on port `8761` with:
- `register-with-eureka: false`
- `fetch-registry: false`
- This is correct for a Eureka server

## API Gateway Routes Configuration

The API Gateway now properly routes requests to:

| Service | Route | Port | Database |
|---------|-------|------|----------|
| USER-SERVICE | `/users/**` | 8081 | MySQL (localhost:3306) |
| HOTEL-SERVICE | `/hotels/**` | 8082 | PostgreSQL (Supabase) |
| RATING-SERVICE | `/ratings/**`, `/rating/**` | 8083 | MongoDB (MongoDB Atlas) |

## Startup Order (Recommended)

1. Start **Service Registry** (Eureka) - Port 8761
2. Start **User Service** - Port 8081
3. Start **Hotel Service** - Port 8082
4. Start **Rating Service** - Port 8083
5. Start **API Gateway** - Port 8084

## Verification Steps

To verify everything is working:

1. Check Eureka Dashboard: `http://localhost:8761/`
   - Should see all 4 services registered (USER-SERVICE, HOTEL-SERVICE, RATING-SERVICE, API-GATEWAY)

2. Test API Gateway Routes:
   - `http://localhost:8084/users/...` → routes to USER-SERVICE
   - `http://localhost:8084/hotels/...` → routes to HOTEL-SERVICE
   - `http://localhost:8084/ratings/...` → routes to RATING-SERVICE

3. Check logs for:
   - Successful service registration with Eureka
   - Gateway route mapping initialization
   - Load balancing working correctly

## Notes

- All services use instance preferences with `prefer-ip-address: true` for better internal communication
- The API Gateway uses reactive webflux: `spring-cloud-starter-gateway-server-webflux`
- Path prefix stripping is enabled to clean URLs for backend services
- Service discovery is enabled with reduced registry fetch intervals for faster service discovery

