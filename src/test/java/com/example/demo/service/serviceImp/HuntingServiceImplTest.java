package com.example.demo.service.serviceImp;

import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.Fish;
import com.example.demo.model.entities.Hunting;
import com.example.demo.model.entities.Member;
import com.example.demo.model.entities.dto.HuntingDto;
import com.example.demo.model.entities.dto.MemberDto;
import com.example.demo.model.entities.mapper.HuntingMapper;
import com.example.demo.repository.IhuntingRepo;
import com.example.demo.service.HuntingService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HuntingServiceImplTest {

    @Mock
    private IhuntingRepo huntingRepo;

    @Mock
    private MemberServiceImpl memberService;

    @Mock
    private RankinServiceImpl rankingService;

    @Mock
    private HuntingMapper huntingMapper;

    @InjectMocks
    private HuntingService huntingService;

    @Test
    void testAddHuntingAndCalculateScore() {
        // Create a sample HuntingDto
        HuntingDto huntingDto = new HuntingDto();
        huntingDto.setMemberId(1L);

        // Mock the behavior of your services
        MemberDto mockedMember = new MemberDto();
        when(memberService.getMemberById(1L)).thenReturn(mockedMember);

        Hunting createdHunting = new Hunting();
        when(huntingMapper.huntingToHuntingDto(any())).thenReturn(huntingDto);
        when(huntingMapper.huntingToHuntingDto(createdHunting)).thenReturn(huntingDto);

        HuntingDto result = huntingService.addHuntingAndCalculateScore(huntingDto);

        verify(rankingService).updateRankingAndScore(any());
        verify(memberService).updateMember(eq(1L), any());

        assertNotNull(result);

    }

    @Test
    void testUpdateHunting() {
        HuntingDto huntingDto = new HuntingDto();
        huntingDto.setNombreOffish(1);

        Hunting existingHunting = new Hunting();
        when(huntingRepo.findById(1L)).thenReturn(Optional.of(existingHunting));
        when(huntingRepo.save(any())).thenReturn(existingHunting);

        MemberDto mockedMember = new MemberDto();
        when(memberService.getMemberById(1L)).thenReturn(mockedMember);

        Hunting updatedHunting = new Hunting();
        when(huntingMapper.huntingToHuntingDto(updatedHunting)).thenReturn(huntingDto);

        HuntingDto result = huntingService.updateHunting(1L, huntingDto);

        verify(rankingService).updateRankingAndScore(any());
        verify(memberService).updateMember(eq(1L), any());

        assertNotNull(result);
    }
    @Test
    public void test_createHuntingWithValidInput() {
        HuntingDto huntingDto = new HuntingDto();
        huntingDto.setNombreOffish(5);
        huntingDto.setCompetitionId(1L);
        huntingDto.setFishId(1L);
        huntingDto.setMemberId(1L);

        Hunting createdHunting = new Hunting();
        createdHunting.setId(1L);
        createdHunting.setNombreOffish(huntingDto.getNombreOffish());
        createdHunting.setCompetition(new Competition());
        createdHunting.setFish(new Fish());
        createdHunting.setMember(new Member());

        when(huntingService.createHunting(huntingDto)).thenReturn(createdHunting);

        HuntingDto result = huntingService.addHuntingAndCalculateScore(huntingDto);


        assertEquals(createdHunting.getId(), result.getClass());
        assertEquals(createdHunting.getNombreOffish(), result.getNombreOffish());
        assertEquals(createdHunting.getCompetition().getId(), result.getCompetitionId());
        assertEquals(createdHunting.getFish().getId(), result.getFishId());
        assertEquals(createdHunting.getMember().getId(), result.getMemberId());
    }
}
