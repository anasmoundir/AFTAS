package com.example.demo.service.serviceImp;

import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.Hunting;
import com.example.demo.model.entities.Rankin;
import com.example.demo.repository.IrankinRepo;
import com.example.demo.service.RankinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
public class RankinServiceImpl implements RankinService {
    private final IrankinRepo irankinRepo;


    @Autowired
    public RankinServiceImpl(IrankinRepo irankinRepo) {
        this.irankinRepo = irankinRepo;
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
        irankinRepo.save(ranking);
    }
        public void calculateRank(Rankin ranking) {
        Competition competition = ranking.getCompetition();
        List<Hunting> huntings = competition.getHuntings();
        List<Integer> scores = huntings.stream().map(Hunting::getPoints).sorted(Collections.reverseOrder()).toList();
        int rank = scores.indexOf(ranking.getScore()) + 1;
        ranking.setRank(rank);
    }

}
