package com.soodisim.demosoodisim.service;

import com.soodisim.demosoodisim.model.PointsHistory;
import com.soodisim.demosoodisim.model.User;
import com.soodisim.demosoodisim.repository.PointsHistoryRepository;
import com.soodisim.demosoodisim.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PointsHistoryServiceTest {

    @Mock
    private PointsHistoryRepository repo;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private PointsHistoryService pointsHistoryService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPointsSuccess() {
        Long userId = 1L;

        User user = new User();
        user.setUserId(userId);
        user.setPoints(10);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(repo.save(any(PointsHistory.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // return same object

        PointsHistory result = pointsHistoryService.addPoints(userId, 20, "Test Reason");

        assertNotNull(result);
        assertEquals(30, user.getPoints());
        assertEquals("Test Reason", result.getReason());
        assertEquals(20, result.getPoints());
        verify(repo).save(any(PointsHistory.class));
    }

    @Test
    void testAddPointsUserNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> pointsHistoryService.addPoints(1L, 10, "Test")
        );

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testGetHistory() {
        PointsHistory h1 = new PointsHistory();
        PointsHistory h2 = new PointsHistory();

        when(repo.findByUser_UserId(1L)).thenReturn(Arrays.asList(h1, h2));

        List<PointsHistory> result = pointsHistoryService.getHistory(1L);

        assertEquals(2, result.size());
    }
}
