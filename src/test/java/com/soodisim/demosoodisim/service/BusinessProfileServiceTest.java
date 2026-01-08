package com.soodisim.demosoodisim.service;

import com.soodisim.demosoodisim.model.BusinessProfile;
import com.soodisim.demosoodisim.model.User;
import com.soodisim.demosoodisim.repository.BusinessProfileRepository;
import com.soodisim.demosoodisim.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BusinessProfileServiceTest {

    @Mock
    private BusinessProfileRepository businessRepo;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private BusinessProfileService businessProfileService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBusinessProfileSuccess() {
        Long userId = 1L;

        User user = new User();
        user.setUserId(1L);
        user.setName("Dea");

        BusinessProfile profile = new BusinessProfile();
        profile.setBusinessName("Tirana Coffee");

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(businessRepo.save(profile)).thenReturn(profile);

        BusinessProfile result = businessProfileService.createBusinessProfile(userId, profile);

        assertNotNull(result);
        assertEquals("Tirana Coffee", result.getBusinessName());
        assertEquals(user, profile.getUser());

        verify(userRepo, times(1)).findById(userId);
        verify(businessRepo, times(1)).save(profile);
    }

    @Test
    void testCreateBusinessProfileUserNotFound() {
        Long userId = 2L;

        BusinessProfile profile = new BusinessProfile();

        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> businessProfileService.createBusinessProfile(userId, profile)
        );

        assertEquals("User not found", exception.getMessage());
        verify(userRepo, times(1)).findById(userId);
        verify(businessRepo, never()).save(any());
    }
}
