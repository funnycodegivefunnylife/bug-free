package bugfree.challenge.client_api.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for User responses
 */
public class UserDto {
    
    @JsonProperty("id")
    private final String id;
    
    @JsonProperty("email")
    private final String email;
    
    @JsonProperty("firstName")
    private final String firstName;
    
    @JsonProperty("lastName")
    private final String lastName;
    
    @JsonProperty("fullName")
    private final String fullName;
    
    @JsonProperty("status")
    private final String status;
    
    @JsonProperty("createdAt")
    private final LocalDateTime createdAt;
    
    @JsonProperty("updatedAt")
    private final LocalDateTime updatedAt;
    
    public UserDto(String id, String email, String firstName, String lastName, 
                   String fullName, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters
    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return fullName; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
