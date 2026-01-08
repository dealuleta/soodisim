package com.soodisim.demosoodisim.service;

import com.soodisim.demosoodisim.model.City;
import com.soodisim.demosoodisim.model.Post;
import com.soodisim.demosoodisim.model.User;
import com.soodisim.demosoodisim.repository.CityRepository;
import com.soodisim.demosoodisim.repository.PostRepository;
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

class PostServiceTest {

    @Mock
    private PostRepository postRepo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private CityRepository cityRepo;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePostSuccess() {
        Long userId = 1L;
        Long cityId = 2L;

        User user = new User();
        user.setUserId(userId);

        City city = new City();
        city.setCityId(cityId);

        Post post = new Post();
        post.setTitle("Beautiful View");

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(cityRepo.findById(cityId)).thenReturn(Optional.of(city));
        when(postRepo.save(post)).thenReturn(post);

        Post result = postService.createPost(post, userId, cityId);

        assertNotNull(result);
        assertEquals("Beautiful View", result.getTitle());
        assertEquals(user, post.getUser());
        assertEquals(city, post.getCity());
        verify(postRepo).save(post);
    }

    @Test
    void testCreatePostUserNotFound() {
        Post post = new Post();

        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> postService.createPost(post, 1L, 2L)
        );

        assertEquals("User not found", ex.getMessage());
        verify(postRepo, never()).save(any());
    }

    @Test
    void testCreatePostCityNotFound() {
        Post post = new Post();
        User user = new User();
        user.setUserId(1L);

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(cityRepo.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> postService.createPost(post, 1L, 2L)
        );

        assertEquals("City not found", ex.getMessage());
        verify(postRepo, never()).save(any());
    }

    @Test
    void testGetPostsByCity() {
        Post p1 = new Post();
        Post p2 = new Post();

        when(postRepo.findByCity_CityId(2L)).thenReturn(Arrays.asList(p1, p2));

        List<Post> result = postService.getPostsByCity(2L);

        assertEquals(2, result.size());
    }

    @Test
    void testGetPostsByUser() {
        Post p1 = new Post();
        Post p2 = new Post();

        when(postRepo.findByUser_UserId(1L)).thenReturn(Arrays.asList(p1, p2));

        List<Post> result = postService.getPostsByUser(1L);

        assertEquals(2, result.size());
    }
}
