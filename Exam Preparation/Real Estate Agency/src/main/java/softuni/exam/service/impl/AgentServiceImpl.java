package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.agent.ImportAgentDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_AGENT;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_AGENT;
import static softuni.exam.constants.Paths.AGENTS_PATH_JSON;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository, Gson gson, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(AGENTS_PATH_JSON);
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<ImportAgentDTO> importAgentDTO = Arrays.stream(this.gson.fromJson(readAgentsFromFile(), ImportAgentDTO[].class)).toList();

        for (ImportAgentDTO agentDTO : importAgentDTO) {

            boolean isValid = validationUtils.isValid(agentDTO);

            if (this.agentRepository.findFirstByFirstName(agentDTO.getFirstName()).isPresent()) {
                sb.append(INVALID_AGENT).append(System.lineSeparator());
                continue;
            }

            if (isValid) {
                if (this.townRepository.findFirstByTownName(agentDTO.getTown()).isPresent()) {
                    Agent agentToSave = this.modelMapper.map(agentDTO, Agent.class);
                    agentToSave.setTown(this.townRepository.findFirstByTownName(agentDTO.getTown()).get());
                    this.agentRepository.saveAndFlush(agentToSave);
                    sb.append(String.format(SUCCESSFULLY_IMPORTED_AGENT,agentToSave.getFirstName(),agentToSave.getLastName()));
                }else {
                    sb.append("Error").append(System.lineSeparator());
                }
            }else {
                sb.append(INVALID_AGENT).append(System.lineSeparator());
            }

        }
        return sb.toString();
    }
}
