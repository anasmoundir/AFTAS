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

import java.util.List;
import java.util.stream.Collectors;


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
        if (ranking == null) {
            ranking = new Rankin(hunting.getMember(), competition);
        }
        ranking.setScore(ranking.getScore() + hunting.getPoints());
        calculateRank(ranking);
        irankinRepo.updateRank(ranking.getId(),ranking.getRank());
    }
    public void calculateRank(Rankin ranking) {


        Competition competition = ranking.getCompetition();
        List<Hunting> huntings = competition.getHuntings();
        ranking.getId();
        int rank = 1;

        for (Hunting h : huntings) {
            if (h.getPoints() > ranking.getScore()) {
                rank++;
            }
        }

        this.updateRankOnly(ranking.getId(),rank);
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
