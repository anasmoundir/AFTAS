package com.example.demo.service.serviceImp;

import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.Hunting;
import com.example.demo.model.entities.Rankin;
import com.example.demo.model.entities.RankingId;
import com.example.demo.model.entities.dto.RankinDto;
import com.example.demo.model.entities.mapper.MyMapperImp;
import com.example.demo.repository.IrankinRepo;
import com.example.demo.service.RankinService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Component
public class RankinServiceImpl implements RankinService {
    private final IrankinRepo irankinRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public RankinServiceImpl(IrankinRepo irankinRepo,ModelMapper modelMapper) {
        this.irankinRepo = irankinRepo;
        this.modelMapper =modelMapper;
    }



    @Override
    public void updateRankingAndScore(Hunting hunting) {
        Competition competition = hunting.getCompetition();
        Rankin ranking = irankinRepo.findByMemberAndCompetition(hunting.getMember(), competition);
        Set<Rankin>  rankins= competition.getRankings();
        if (ranking == null) {
            ranking = new Rankin(hunting.getMember(), competition);
        }
        ranking.setScore(ranking.getScore() + hunting.getPoints());
        calculateRank(rankins);
        irankinRepo.updateRank(ranking.getId(),ranking.getRank());
    }
    public void calculateRank(Set<Rankin> rankings) {

        List<Rankin> rankingList = new ArrayList<>(rankings);
        Collections.sort(rankingList, Comparator.comparingDouble(Rankin::getScore).reversed());
        IntStream.range(0, rankingList.size()).forEach(index-> {
            Rankin rankin = rankingList.get(index);
            int newRank = index + 1;
            rankin.setRank(newRank);
            this.updateRankOnly(rankin.getId(), newRank);
        });

    }


    public void updateRankOnly(RankingId rankingId, int newRank) {
        irankinRepo.updateRank(rankingId, newRank);
    }

    @Override
    public List<RankinDto> getPodiumForCompetition(Competition competition) {

        List<Rankin> topThreeByCompetition = irankinRepo.findTopThreeByCompetition(competition);
        return topThreeByCompetition.stream().map(rankin -> modelMapper.map(rankin, RankinDto.class)).collect(Collectors.toList());
    }


}
