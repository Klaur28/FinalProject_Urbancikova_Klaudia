package projekt.poistenie.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import projekt.poistenie.dtos.StatistikyDTO;
import projekt.poistenie.entities.PoistnaZmluva;
import projekt.poistenie.repository.PoistnaZmluvaRepository;
import projekt.poistenie.repository.PoistenecRepository;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatistikyServiceImplTest {

    @Mock PoistenecRepository poRepo;
    @Mock PoistnaZmluvaRepository zRepo;
    @InjectMocks StatistikyServiceImpl service;

    @Test
    void getGlobalStatistics_nonEmpty() {
        when(poRepo.count()).thenReturn(5L);
        when(zRepo.count()).thenReturn(3L);

        PoistnaZmluva z1 = new PoistnaZmluva();
        z1.setDruhPoistenia("A");
        z1.setUdalosti(List.of(new Udalost(), new Udalost()));
        PoistnaZmluva z2 = new PoistnaZmluva();
        z2.setDruhPoistenia("B");
        z2.setUdalosti(List.of(new Udalost()));
        PoistnaZmluva z3 = new PoistnaZmluva();
        z3.setDruhPoistenia("A");
        z3.setUdalosti(Collections.emptyList());
        when(zRepo.findAll()).thenReturn(List.of(z1, z2, z3));

        StatistikyDTO dto = service.getGlobalStatistics();

        assertEquals(5, dto.getPocetPoistenych());
        assertEquals(3, dto.getPocetPoisteni());
        assertEquals(3, dto.getPocetUdalosti());
        Map<String, Long> map = dto.getRozdeleniePodlaTypu();
        assertEquals(2, map.size());
        assertEquals(2L, map.get("A"));
        assertEquals(1L, map.get("B"));
    }

    @Test
    void getGlobalStatistics_empty() {
        when(poRepo.count()).thenReturn(0L);
        when(zRepo.count()).thenReturn(0L);
        when(zRepo.findAll()).thenReturn(Collections.emptyList());

        StatistikyDTO dto = service.getGlobalStatistics();

        assertEquals(0, dto.getPocetPoistenych());
        assertEquals(0, dto.getPocetPoisteni());
        assertEquals(0, dto.getPocetUdalosti());
        assertTrue(dto.getRozdeleniePodlaTypu().isEmpty());
    }
}
