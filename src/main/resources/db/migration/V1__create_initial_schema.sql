-- V1__create_initial_schema.sql
-- Migración inicial para el esquema del Foro Hub

-- Tabla de Perfil (debe crearse primero por las FK)
CREATE TABLE Perfil (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla de Usuario
CREATE TABLE Usuario (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correoElectronico VARCHAR(150) UNIQUE NOT NULL,
    contrasena TEXT NOT NULL,
    perfiles INTEGER REFERENCES Perfil(id)
);

-- Tabla de Curso
CREATE TABLE Curso (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(100)
);

-- Tabla de Topico
CREATE TABLE Topico (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    mensaje TEXT NOT NULL,
    fechaCreacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50),
    autor INTEGER REFERENCES Usuario(id),
    curso INTEGER REFERENCES Curso(id)
);

-- Tabla de Respuesta
CREATE TABLE Respuesta (
    id SERIAL PRIMARY KEY,
    mensaje TEXT NOT NULL,
    topico INTEGER REFERENCES Topico(id),
    fechaCreacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    autor INTEGER REFERENCES Usuario(id),
    solucion BOOLEAN DEFAULT FALSE
);

-- Insertar perfiles por defecto
INSERT INTO Perfil (nombre) VALUES ('ADMIN'), ('USER');

-- Crear índices para mejorar performance
CREATE INDEX idx_topico_autor ON Topico(autor);
CREATE INDEX idx_topico_curso ON Topico(curso);
CREATE INDEX idx_topico_fecha ON Topico(fechaCreacion);
CREATE INDEX idx_respuesta_topico ON Respuesta(topico);
CREATE INDEX idx_respuesta_autor ON Respuesta(autor);
CREATE INDEX idx_usuario_correo ON Usuario(correoElectronico);
