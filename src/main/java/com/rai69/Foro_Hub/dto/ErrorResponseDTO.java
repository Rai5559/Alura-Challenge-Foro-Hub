package com.rai69.Foro_Hub.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta estándar de error de la API")
public class ErrorResponseDTO {
    
    @Schema(description = "Tipo de error HTTP", example = "Bad Request")
    private String error;
    
    @Schema(description = "Mensaje descriptivo del error", example = "Los datos enviados no son válidos")
    private String message;
    
    @Schema(description = "Timestamp del error en formato ISO", example = "2024-12-23T10:30:00Z")
    private String timestamp;
    
    @Schema(description = "Código de estado HTTP", example = "400")
    private int status;
    
    @Schema(description = "Ruta del endpoint que generó el error", example = "/auth/login")
    private String path;

    public ErrorResponseDTO() {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "Z";
    }

    public ErrorResponseDTO(String error, String message, int status, String path) {
        this();
        this.error = error;
        this.message = message;
        this.status = status;
        this.path = path;
    }

    // Getters y Setters
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
