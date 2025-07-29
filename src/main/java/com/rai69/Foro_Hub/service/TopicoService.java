package com.rai69.Foro_Hub.service;

import com.rai69.Foro_Hub.dto.TopicoRequestDTO;
import com.rai69.Foro_Hub.dto.TopicoResponseDTO;
import com.rai69.Foro_Hub.exception.DuplicatedTopicException;
import com.rai69.Foro_Hub.exception.EntityNotFoundException;
import com.rai69.Foro_Hub.model.TopicoModel;
import com.rai69.Foro_Hub.model.UsuarioModel;
import com.rai69.Foro_Hub.model.CursoModel;
import com.rai69.Foro_Hub.repository.TopicoRepository;
import com.rai69.Foro_Hub.repository.UsuarioRepository;
import com.rai69.Foro_Hub.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private CursoService cursoService;

    @Transactional
    public TopicoResponseDTO crearTopico(TopicoRequestDTO requestDTO) {
        if (topicoRepository.existsByTituloAndMensaje(requestDTO.getTitulo(), requestDTO.getMensaje())) {
            throw new DuplicatedTopicException(
                "Ya existe un tópico con el mismo título y mensaje");
        }
        
        UsuarioModel autor = usuarioRepository.findById(requestDTO.getAutorId())
            .orElseThrow(() -> new EntityNotFoundException(
                "Usuario no encontrado con ID: " + requestDTO.getAutorId()));

        CursoModel curso = cursoService.obtenerOCrearCursoModel(requestDTO.getCursoNombre(), requestDTO.getCategoria());

        TopicoModel topico = new TopicoModel();
        topico.setTitulo(requestDTO.getTitulo());
        topico.setMensaje(requestDTO.getMensaje());
        topico.setFechaCreacion(LocalDateTime.now());
        topico.setStatus(requestDTO.getStatus() != null ? requestDTO.getStatus() : "ABIERTO");
        topico.setAutor(autor);
        topico.setCurso(curso);
        
        TopicoModel topicoGuardado = topicoRepository.save(topico);
        
        return convertirAResponseDTO(topicoGuardado);
    }
    
    public TopicoResponseDTO obtenerTopico(Integer id) {
        TopicoModel topico = topicoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tópico no encontrado con ID: " + id));
        
        return convertirAResponseDTO(topico);
    }
    
    public Page<TopicoResponseDTO> listarTopicos(Pageable pageable) {
        Page<TopicoModel> topicos = topicoRepository.findAll(pageable);
        return topicos.map(this::convertirAResponseDTO);
    }
   
    @Transactional
    public TopicoResponseDTO actualizarTopico(Integer id, TopicoRequestDTO requestDTO) {
        TopicoModel topico = topicoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tópico no encontrado con ID: " + id));
        
        topico.setTitulo(requestDTO.getTitulo());
        topico.setMensaje(requestDTO.getMensaje());
        if (requestDTO.getStatus() != null) {
            topico.setStatus(requestDTO.getStatus());
        }
        
        TopicoModel topicoActualizado = topicoRepository.save(topico);
        return convertirAResponseDTO(topicoActualizado);
    }
    
    @Transactional
    public void eliminarTopico(Integer id) {
        if (!topicoRepository.existsById(id)) {
            throw new RuntimeException("Tópico no encontrado con ID: " + id);
        }
        topicoRepository.deleteById(id);
    }

    private TopicoResponseDTO convertirAResponseDTO(TopicoModel topico) {
        TopicoResponseDTO dto = new TopicoResponseDTO();
        dto.setId(topico.getId());
        dto.setTitulo(topico.getTitulo());
        dto.setMensaje(topico.getMensaje());
        dto.setFechaCreacion(topico.getFechaCreacion());
        dto.setStatus(topico.getStatus());
        
        if (topico.getAutor() != null) {
            TopicoResponseDTO.AutorDTO autorDTO = new TopicoResponseDTO.AutorDTO();
            autorDTO.setId(topico.getAutor().getId());
            autorDTO.setNombre(topico.getAutor().getNombre());
            dto.setAutor(autorDTO);
        }
        
        if (topico.getCurso() != null) {
            TopicoResponseDTO.CursoDTO cursoDTO = new TopicoResponseDTO.CursoDTO();
            cursoDTO.setId(topico.getCurso().getId());
            cursoDTO.setNombre(topico.getCurso().getNombre());
            cursoDTO.setCategoria(topico.getCurso().getCategoria());
            dto.setCurso(cursoDTO);
        }
        
        dto.setTotalRespuestas(topico.getRespuestas() != null ? topico.getRespuestas().size() : 0);
        
        return dto;
    }
}
