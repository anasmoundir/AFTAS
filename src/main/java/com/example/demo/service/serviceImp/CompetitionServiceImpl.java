    package com.example.demo.service.serviceImp;

    import com.example.demo.model.entities.Competition;
    import com.example.demo.model.entities.dto.CompetitionDto;
    import com.example.demo.model.entities.mapper.CompetitionMapper;
    import com.example.demo.repository.IcompetitionRepo;
    import com.example.demo.service.CompetitionService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;

    import java.sql.Date;
    import java.util.List;

    @Component
    public class CompetitionServiceImpl implements CompetitionService {
        private final IcompetitionRepo icompetitionRepo;
        private final CompetitionMapper competitionMapper;

        @Autowired
        public CompetitionServiceImpl(IcompetitionRepo icompetitionRepo, CompetitionMapper competitionMapper) {
            this.icompetitionRepo = icompetitionRepo;
            this.competitionMapper = competitionMapper;
        }

        @Override
        public CompetitionDto getCompetitionById(Long id) {
            Competition competition = icompetitionRepo.findById(id).orElseThrow(() -> new RuntimeException("Competition not found"));
            return competitionMapper.competitionToCompetitionDto(competition);
        }

        @Override
        public List<CompetitionDto> getAllCompetitions() {
            List<Competition> competitions = icompetitionRepo.findAll();
            return competitionMapper.competitionsToCompetitionDtos(competitions);
        }

        @Override
        public CompetitionDto addCompetition(CompetitionDto competitionDto) {
            Competition competition = competitionMapper.competitionDtoToCompetition(competitionDto);
            icompetitionRepo.save(competition);
            return competitionMapper.competitionToCompetitionDto(competition);
        }

        @Override
        public void updateCompetition(Long id, CompetitionDto competitionDto) {
            Competition existingCompetition = icompetitionRepo.findById(id).orElseThrow(() -> new RuntimeException("Competition not found"));
            existingCompetition.setCode(competitionDto.getCode());
            existingCompetition.setTheDate((Date)competitionDto.getTheDate());

            icompetitionRepo.save(existingCompetition);
        }

        @Override
        public void deleteCompetition(Long id) {
            icompetitionRepo.deleteById(id);
        }
    }
