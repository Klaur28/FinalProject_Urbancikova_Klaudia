package projekt.poistenie.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projekt.poistenie.dtos.PoistnaZmluvaDTO;
import projekt.poistenie.entities.Poistenec;
import projekt.poistenie.entities.PoistnaZmluva;
import projekt.poistenie.dtos.mappers.PoistnaZmluvaMapper;
import projekt.poistenie.repository.PoistenecRepository;
import projekt.poistenie.repository.PoistnaZmluvaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PoistnaZmluvaServiceImplTest {

    @Mock PoistnaZmluvaRepository zmluvaRepo;
    @Mock PoistenecRepository poistenecRepo;
    @Mock PoistnaZmluvaMapper mapper;
    @InjectMocks PoistnaZmluvaServiceImpl service;

    @Test
    void create_success() {
        // Arrange
        PoistnaZmluvaDTO dto = new PoistnaZmluvaDTO();
        dto.setPoistenecId(1L);

        Poistenec p = new Poistenec(); p.setId(1L);
        when(poistenecRepo.findById(1L)).thenReturn(Optional.of(p));

        PoistnaZmluva entity = new PoistnaZmluva();
        when(mapper.toEntity(dto)).thenReturn(entity);

        PoistnaZmluva saved = new PoistnaZmluva(); saved.setId(2L);
        when(zmluvaRepo.save(entity)).thenReturn(saved);

        // Act
        service.create(dto);

        // Assert: verify flow without unnecessary stubs
        verify(mapper).toEntity(dto);
        verify(poistenecRepo).findById(1L);
        verify(zmluvaRepo).save(entity);
    }

    @Test
    void create_nullPoistenecId_throws() {
        PoistnaZmluvaDTO dto = new PoistnaZmluvaDTO();
        dto.setPoistenecId(null);
        assertThrows(IllegalArgumentException.class, () -> service.create(dto));
    }

    @Test
    void create_poistenecNotFound_throws() {
        PoistnaZmluvaDTO dto = new PoistnaZmluvaDTO();
        dto.setPoistenecId(5L);
        when(poistenecRepo.findById(5L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.create(dto));
    }

    @Test
    void findAll_returnsMappedList() {
        PoistnaZmluva e1 = new PoistnaZmluva(), e2 = new PoistnaZmluva();
        when(zmluvaRepo.findAll()).thenReturn(List.of(e1, e2));
        PoistnaZmluvaDTO d1 = new PoistnaZmluvaDTO(), d2 = new PoistnaZmluvaDTO();
        when(mapper.toDto(e1)).thenReturn(d1);
        when(mapper.toDto(e2)).thenReturn(d2);

        List<PoistnaZmluvaDTO> result = service.findAll();

        assertEquals(2, result.size());
        assertSame(d1, result.get(0));
        assertSame(d2, result.get(1));
        verify(zmluvaRepo).findAll();
    }

    @Test
    void findById_success() {
        PoistnaZmluva e = new PoistnaZmluva(); e.setId(10L);
        when(zmluvaRepo.findById(10L)).thenReturn(Optional.of(e));
        PoistnaZmluvaDTO dto = new PoistnaZmluvaDTO(); dto.setId(10L);
        when(mapper.toDto(e)).thenReturn(dto);

        PoistnaZmluvaDTO result = service.findById(10L);

        assertEquals(10L, result.getId());
        verify(zmluvaRepo).findById(10L);
        verify(mapper).toDto(e);
    }

    @Test
    void findById_notFound_throws() {
        when(zmluvaRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void update_success() {
        PoistnaZmluvaDTO dto = new PoistnaZmluvaDTO(); dto.setId(20L);
        PoistnaZmluva existing = new PoistnaZmluva(); existing.setId(20L);
        when(zmluvaRepo.findById(20L)).thenReturn(Optional.of(existing));

        doNothing().when(mapper).updatePoistnaZmluva(dto, existing);
        when(zmluvaRepo.save(existing)).thenReturn(existing);

        service.update(dto);

        verify(zmluvaRepo).findById(20L);
        verify(mapper).updatePoistnaZmluva(dto, existing);
        verify(zmluvaRepo).save(existing);
    }

    @Test
    void update_notFound_throws() {
        PoistnaZmluvaDTO dto = new PoistnaZmluvaDTO(); dto.setId(30L);
        when(zmluvaRepo.findById(30L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.update(dto));
    }

    @Test
    void delete_success() {
        when(zmluvaRepo.existsById(40L)).thenReturn(true);
        service.delete(40L);
        verify(zmluvaRepo).deleteById(40L);
    }

    @Test
    void delete_notFound_throws() {
        when(zmluvaRepo.existsById(50L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.delete(50L));
    }

    @Test
    void findByPoistenecEmail_success() {
        Poistenec p = new Poistenec(); p.setId(7L);
        when(poistenecRepo.findByEmail("x@y")).thenReturn(Optional.of(p));
        PoistnaZmluva z = new PoistnaZmluva(); z.setId(8L);
        when(zmluvaRepo.findByPoistenec(p)).thenReturn(List.of(z));
        PoistnaZmluvaDTO dto = new PoistnaZmluvaDTO(); dto.setId(8L);
        when(mapper.toDto(z)).thenReturn(dto);

        List<PoistnaZmluvaDTO> list = service.findByPoistenecEmail("x@y");

        assertEquals(1, list.size());
        assertEquals(8L, list.get(0).getId());
    }

    @Test
    void findByPoistenecEmail_notFound_throws() {
        when(poistenecRepo.findByEmail("no")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findByPoistenecEmail("no"));
    }

    @Test
    void belongsToEmail() {
        when(zmluvaRepo.existsByIdAndPoistenecEmail(60L, "e@e")).thenReturn(true);
        assertTrue(service.belongsToEmail(60L, "e@e"));
        when(zmluvaRepo.existsByIdAndPoistenecEmail(70L, "x@x")).thenReturn(false);
        assertFalse(service.belongsToEmail(70L, "x@x"));
    }
}