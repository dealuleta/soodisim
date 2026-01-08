package com.soodisim.demosoodisim.service;

import com.soodisim.demosoodisim.model.Interaction;
import com.soodisim.demosoodisim.model.Post;
import com.soodisim.demosoodisim.model.User;
import com.soodisim.demosoodisim.repository.InteractionRepository;
import com.soodisim.demosoodisim.repository.PostRepository;
import com.soodisim.demosoodisim.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class
InteractionServiceTest {

    @Mock
    private InteractionRepository interactionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private InteractionService interactionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddInteractionSuccessWithViewIncrement() {
        Long userId = 1L;
        Long postId = 10L;

        User user = new User();
        user.setUserId(userId);

        Post post = new Post();
        post.setPostId(postId);
        post.setViews(5);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Interaction savedInteraction = new Interaction();
        savedInteraction.setType("VIEW");
        savedInteraction.setUser(user);
        savedInteraction.setPost(post);

        when(interactionRepository.save(any(Interaction.class))).thenReturn(savedInteraction);
        when(postRepository.save(post)).thenReturn(post);

        Interaction result = interactionService.addInteraction(userId, postId, "VIEW");

        assertNotNull(result);
        assertEquals("VIEW", result.getType());
        assertEquals(6, post.getViews()); // should increment
        verify(postRepository, times(1)).save(post);
        verify(interactionRepository, times(1)).save(any(Interaction.class));
    }

    @Test
    void testAddInteractionUserNotFound() {
        Long userId = 1L;
        Long postId = 10L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> interactionService.addInteraction(userId, postId, "LIKE")
        );

        assertEquals("User not found", ex.getMessage());
        verify(userRepository).findById(userId);
        verify(postRepository, never()).findById(any());
        verify(interactionRepository, never()).save(any());
    }

    @Test
    void testAddInteractionPostNotFound() {
        Long userId = 1L;
        Long postId = 10L;

        User user = new User();
        user.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> interactionService.addInteraction(userId, postId, "LIKE")
        );

        assertEquals("Post not found", ex.getMessage());
        verify(postRepository).findById(postId);
        verify(interactionRepository, never()).save(any());
    }
}
