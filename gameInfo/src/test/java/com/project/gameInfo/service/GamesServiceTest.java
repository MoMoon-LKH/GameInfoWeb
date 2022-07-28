package com.project.gameInfo.service;

import com.project.gameInfo.repository.GamesRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GamesServiceTest {

    @Mock
    private GamesRepository gamesRepository;

    @InjectMocks
    private GamesService gamesService;

}