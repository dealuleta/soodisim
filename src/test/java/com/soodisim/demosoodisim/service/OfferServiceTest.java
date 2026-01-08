package com.soodisim.demosoodisim.service;

import com.soodisim.demosoodisim.model.BusinessProfile;
import com.soodisim.demosoodisim.model.Offer;
import com.soodisim.demosoodisim.repository.BusinessProfileRepository;
import com.soodisim.demosoodisim.repository.OfferRepository;

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

class OfferServiceTest {

    @Mock
    private OfferRepository offerRepo;

    @Mock
    private BusinessProfileRepository businessRepo;

    @InjectMocks
    private OfferService offerService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOfferSuccess() {
        Long businessId = 3L;

        BusinessProfile business = new BusinessProfile();
        business.setBusinessId(businessId);

        Offer offer = new Offer();
        offer.setTitle("50% Discount");

        when(businessRepo.findById(businessId)).thenReturn(Optional.of(business));
        when(offerRepo.save(offer)).thenReturn(offer);

        Offer result = offerService.createOffer(businessId, offer);

        assertNotNull(result);
        assertEquals("50% Discount", result.getTitle());
        assertEquals(business, offer.getBusiness());
        verify(offerRepo).save(offer);
    }

    @Test
    void testCreateOfferBusinessNotFound() {
        Long businessId = 5L;
        Offer offer = new Offer();

        when(businessRepo.findById(businessId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> offerService.createOffer(businessId, offer)
        );

        assertEquals("Business not found", ex.getMessage());
        verify(offerRepo, never()).save(any());
    }

    @Test
    void testGetOffersByBusiness() {
        Offer o1 = new Offer();
        Offer o2 = new Offer();

        when(offerRepo.findByBusiness_BusinessId(2L)).thenReturn(Arrays.asList(o1, o2));

        List<Offer> result = offerService.getOffersByBusiness(2L);

        assertEquals(2, result.size());
    }
}
