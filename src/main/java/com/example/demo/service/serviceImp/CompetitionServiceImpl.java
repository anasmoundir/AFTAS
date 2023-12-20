    package com.example.demo.service.serviceImp;

    import com.example.demo.Exeption.CompetitionNotFoundException;
    import com.example.demo.model.entities.Competition;
    import com.example.demo.model.entities.dto.CompetitionDto;
    import com.example.demo.model.entities.mapper.MyMapperImp;
    import com.example.demo.repository.IcompetitionRepo;
    import com.example.demo.service.CompetitionService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.stereotype.Component;

    import org.springframework.data.domain.Pageable;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.time.LocalTime;
    import java.time.format.DateTimeFormatter;
    import java.util.List;
    import java.util.Optional;

    @Component
    public class CompetitionServiceImpl implements CompetitionService {
        private final IcompetitionRepo icompetitionRepo;
        private final  MyMapperImp competitionMapper;

        @Autowired
        public CompetitionServiceImpl(IcompetitionRepo icompetitionRepo, MyMapperImp competitionMapper) {
            this.icompetitionRepo = icompetitionRepo;
            this.competitionMapper = competitionMapper;
        }

        @Override
        public CompetitionDto getCompetitionById(Long id) {

                Optional competition = icompetitionRepo.findById(id);
                if(competition.isPresent())
                {
                    Competition competition1 = (Competition) competition.get();
                    return competitionMapper.competitionToCompetitionDto(competition1);

                }else
                {
                    throw new CompetitionNotFoundException("competition not found ");
                }
        }

        @Override
        public List<CompetitionDto> getAllCompetitions() {
            Page<Competition> competitions = icompetitionRepo.findAll();
            return competitionMapper.competitionsToCompetitionDtos((List<Competition>) competitions);
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
            CompetitionDto OriginalCompetition = getCompetitionById(id);
            OriginalCompetition.setTheDate(competitionDto.getTheDate());
            OriginalCompetition.setAmount(competitionDto.getAmount());
            OriginalCompetition.setNumberOfParticipant(competitionDto.getNumberOfParticipant());
            OriginalCompetition.setLocation(competitionDto.getLocation());
            OriginalCompetition.setStartTime(competitionDto.getStartTime());
            OriginalCompetition.setEndTime(competitionDto.getEndTime());
            Competition comp  = competitionMapper.competitionDtoToCompetition(OriginalCompetition);
            icompetitionRepo.save(comp);
        }


        @Override
        public void deleteCompetition(Long id) {
            icompetitionRepo.deleteById(id);
        }
        @Override
        public Page<CompetitionDto> getOpenCompetitions(Pageable pageable) {
            LocalDate yesterday = LocalDate.now().minusDays(1);
            LocalTime endTimeOfDay = LocalTime.of(23, 59, 59);
            Page<Competition> openCompetitions = icompetitionRepo.findOpenCompetitions(yesterday, endTimeOfDay, pageable);
            return openCompetitions.map(competitionMapper::competitionToCompetitionDto);
        }

        @Override
        public Page<CompetitionDto> getClosedCompetitions(Pageable pageable) {
            LocalDate currentDate = LocalDate.now();
            LocalTime endTimeOfDay = LocalTime.MAX;
            LocalDateTime currentTimestamp = LocalDateTime.now();
            Page<Competition> closedCompetitions = icompetitionRepo.findClosedCompetitions(currentDate, endTimeOfDay, currentTimestamp, pageable);
            return closedCompetitions.map(competitionMapper::competitionToCompetitionDto);
        }

    }
