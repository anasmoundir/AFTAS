    package com.example.demo.service.serviceImp;

    import com.example.demo.model.entities.Competition;
    import com.example.demo.model.entities.dto.CompetitionDto;
    import com.example.demo.model.entities.mapper.CompetitionMapper;
    import com.example.demo.model.entities.mapper.mapperImplementation.MyMapperImp;
    import com.example.demo.repository.IcompetitionRepo;
    import com.example.demo.service.CompetitionService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;

    import java.sql.Date;
    import java.sql.Timestamp;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.time.LocalTime;
    import java.time.format.DateTimeFormatter;
    import java.util.List;

    @Component
    public class CompetitionServiceImpl implements CompetitionService {
        private final IcompetitionRepo icompetitionRepo;
        private final MyMapperImp competitionMapper;

        @Autowired
        public CompetitionServiceImpl(IcompetitionRepo icompetitionRepo, MyMapperImp competitionMapper) {
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
            String locationCode = extractLocationCode(competitionDto.getLocation());
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yy");
            String datePart = competitionDto.getTheDate().format(dateFormatter);
            String generatedCode = locationCode + "-" + datePart;
            competition.setCode(generatedCode);
            icompetitionRepo.save(competition);
            return competitionMapper.competitionToCompetitionDto(competition);
        }
        private String extractLocationCode(String location) {
            if (location.length() >= 3) {
                return location.substring(0, 3).toUpperCase();
            } else {return location.toUpperCase();
            }
        }

        @Override
        public void updateCompetition(Long id, CompetitionDto competitionDto) {
            Competition existingCompetition = icompetitionRepo.findById(id).orElseThrow(() -> new RuntimeException("Competition not found"));
            existingCompetition.setTheDate(competitionDto.getTheDate());
            existingCompetition.setAmount(competitionDto.getAmount());
            existingCompetition.setNumberOfParticipant(competitionDto.getNumberOfParticipant());
            existingCompetition.setLocation(competitionDto.getLocation());
            existingCompetition.setStartTime(competitionDto.getStartTime());
            existingCompetition.setEndTime(competitionDto.getEndTime());
            icompetitionRepo.save(existingCompetition);
        }


        @Override
        public void deleteCompetition(Long id) {
            icompetitionRepo.deleteById(id);
        }
        @Override
        public List<CompetitionDto> getOpenCompetitions() {
            LocalDate yesterday = LocalDate.now().minusDays(1);
            LocalTime endTimeOfDay = LocalTime.of(23, 59, 59); // End of the day
            List<Competition> openCompetitions = icompetitionRepo.findOpenCompetitions(yesterday, endTimeOfDay);
            return competitionMapper.competitionsToCompetitionDtos(openCompetitions);
        }

        @Override
        public List<CompetitionDto> getClosedCompetitions() {
            LocalDate currentDate = LocalDate.now();
            LocalTime endTimeOfDay = LocalTime.MAX;
            LocalDateTime currentTimestamp = LocalDateTime.now();
            List<Competition> closedCompetitions = icompetitionRepo.findClosedCompetitions(currentDate, endTimeOfDay, currentTimestamp);
            return competitionMapper.competitionsToCompetitionDtos(closedCompetitions);
        }

    }
