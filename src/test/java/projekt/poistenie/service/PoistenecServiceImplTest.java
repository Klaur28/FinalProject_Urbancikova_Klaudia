package projekt.poistenie.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import projekt.poistenie.dtos.PoistenecDTO;
import projekt.poistenie.entities.Poistenec;
import projekt.poistenie.exceptions.PoistenecNotFoundException;
import projekt.poistenie.dtos.mappers.PoistenecMapper;
import projekt.poistenie.repository.PoistenecRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PoistenecServiceImplTest {

    @Mock PoistenecRepository repo;
    @Mock PoistenecMapper mapper;
    @InjectMocks PoistenecServiceImpl service;

    @Test
    void create_success() {
        // Arrange
        PoistenecDTO dto = new PoistenecDTO();
        dto.setEmail("test@example.com");
        Poistenec entity = new Poistenec();
        when(repo.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(entity);

        // Act
        service.create(dto);

        // Assert
        verify(repo).findByEmail("test@example.com");
        verify(mapper).toEntity(dto);
        verify(repo).save(entity);
    }

    @Test
    void create_duplicateEmail_throws() {
        PoistenecDTO dto = new PoistenecDTO();
        dto.setEmail("dup@example.com");
        when(repo.findByEmail("dup@example.com")).thenReturn(Optional.of(new Poistenec()));

        assertThrows(IllegalArgumentException.class, () -> service.create(dto));
        verify(repo).findByEmail("dup@example.com");
        verifyNoMoreInteractions(repo);
    }

    @Test
    void findById_success() {
        Poistenec e = new Poistenec(); e.setId(2L);
        when(repo.findById(2L)).thenReturn(Optional.of(e));
        PoistenecDTO dto = new PoistenecDTO(); dto.setId(2L);
        when(mapper.toDto(e)).thenReturn(dto);

        PoistenecDTO result = service.findById(2L);
        assertEquals(2L, result.getId());
        verify(repo).findById(2L);
        verify(mapper).toDto(e);
    }

    @Test
    void findById_notFound_throws() {
        when(repo.findById(3L)).thenReturn(Optional.empty());
        assertThrows(PoistenecNotFoundException.class, () -> service.findById(3L));
    }

    @Test
    void update_success() {
        PoistenecDTO dto = new PoistenecDTO(); dto.setId(4L); dto.setEmail("new@mail");
        Poistenec existing = new Poistenec(); existing.setId(4L);
        when(repo.findById(4L)).thenReturn(Optional.of(existing));

        // simulate mapper copying fields
        doAnswer(inv -> {
            existing.setEmail(dto.getEmail());
            return null;
        }).when(mapper).updatePoistenec(dto, existing);

        when(repo.save(existing)).thenReturn(existing);

        // Act
        service.update(dto);

        // Assert
        verify(repo).findById(4L);
        verify(mapper).updatePoistenec(dto, existing);
        verify(repo).save(existing);
    }

    @Test
    void update_notFound_throws() {
        PoistenecDTO dto = new PoistenecDTO(); dto.setId(5L);
        when(repo.findById(5L)).thenReturn(Optional.empty());
        assertThrows(PoistenecNotFoundException.class, () -> service.update(dto));
    }

    @Test
    void delete_existing_success() {
        when(repo.existsById(6L)).thenReturn(true);
        service.delete(6L);
        verify(repo).deleteById(6L);
    }

    @Test
    void delete_notFound_throws() {
        when(repo.existsById(7L)).thenReturn(false);
        assertThrows(PoistenecNotFoundException.class, () -> service.delete(7L));
    }

    @Test
    void findByEmail_success() {
        Poistenec e = new Poistenec(); e.setEmail("e@mail");
        when(repo.findByEmail("e@mail")).thenReturn(Optional.of(e));
        PoistenecDTO dto = new PoistenecDTO(); dto.setEmail("e@mail");
        when(mapper.toDto(e)).thenReturn(dto);

        PoistenecDTO result = service.findByEmail("e@mail");
        assertEquals("e@mail", result.getEmail());
        verify(repo).findByEmail("e@mail");
        verify(mapper).toDto(e);
    }

    @Test
    void findByEmail_notFound_throws() {
        when(repo.findByEmail("x@mail")).thenReturn(Optional.empty());
        assertThrows(PoistenecNotFoundException.class, () -> service.findByEmail("x@mail"));
    }

    @Test
    void isOwnedByEmail_true() {
        when(repo.existsByIdAndEmail(8L, "o@mail")).thenReturn(true);
        assertTrue(service.isOwnedByEmail(8L, "o@mail"));
    }

    @Test
    void isOwnedByEmail_false() {
        when(repo.existsByIdAndEmail(9L, "no@mail")).thenReturn(false);
        assertFalse(service.isOwnedByEmail(9L, "no@mail"));
    }
}
