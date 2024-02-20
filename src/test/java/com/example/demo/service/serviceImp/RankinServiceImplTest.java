package com.example.demo.service.serviceImp;

import com.example.demo.model.entities.Hunting;
import com.example.demo.model.entities.Rankin;
import com.example.demo.model.entities.RankingId;
import com.example.demo.model.entities.mapper.MyMapperImp;
import com.example.demo.repository.IhuntingRepo;
import com.example.demo.repository.IrankinRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class RankinServiceImplTest {
    @Mock
    private IrankinRepo irankinRepo;

    @Mock
    private HuntingServiceImpl huntingService;

    @InjectMocks
    private RankinServiceImpl rankinService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testCalculateRank() {
//        Set<Rankin> rankings = new HashSet<>();
//        Rankin rankin1 = new Rankin();
//        rankin1.setScore(Double.POSITIVE_INFINITY);
//        rankings.add(rankin1);
//
//        Rankin rankin2 = new Rankin();
//        rankin2.setScore(Double.POSITIVE_INFINITY);
//        rankings.add(rankin2);
//
//        Rankin rankin3 = new Rankin();
//        rankin3.setScore(Double.POSITIVE_INFINITY);
//        rankings.add(rankin3);
//
//        Hunting mockedHunting = new Hunting();
//        when(huntingService.createHunting(any())).thenReturn(mockedHunting);
//
//        doAnswer(invocation -> {
//            Object[] args = invocation.getArguments();
//            RankingId rankingId = (RankingId) args[0];
//            int newRank = (int) args[1];
//
//
//            return null;
//        }).when(irankinRepo).updateRank(any(), anyInt());
//
//        rankinService.calculateRank(rankings);
//
//        verify(irankinRepo, times(3)).updateRank(any(), anyInt());
//        verify(huntingService, times(3)).createHunting(any());
//        assertEquals(1, rankin1.getRank());
//        assertEquals(2, rankin2.getRank());
//        assertEquals(3, rankin3.getRank());
//    }huntingService.createHunting(<any>);


}