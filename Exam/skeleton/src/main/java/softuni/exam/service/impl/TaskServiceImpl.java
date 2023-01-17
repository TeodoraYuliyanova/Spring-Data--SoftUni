package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.task.ImportTaskDTO;
import softuni.exam.models.dto.task.ImportTasksWrapperDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.models.entity.Task;
import softuni.exam.models.enums.CarType;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.repository.PartRepository;
import softuni.exam.repository.TaskRepository;
import softuni.exam.service.TaskService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.swing.text.DateFormatter;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.TASKS_IMPORT_XML;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final CarRepository carRepository;
    private final MechanicRepository mechanicRepository;
    private final PartRepository partRepository;
    private final ValidationUtils validationUtils;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper, XmlParser xmlParser, CarRepository carRepository, MechanicRepository mechanicRepository, PartRepository partRepository, ValidationUtils validationUtils) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.carRepository = carRepository;
        this.mechanicRepository = mechanicRepository;
        this.partRepository = partRepository;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(TASKS_IMPORT_XML);
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        File file = TASKS_IMPORT_XML.toFile();

        ImportTasksWrapperDTO importTasksWrapperDTO = this.xmlParser.fromFile(file, ImportTasksWrapperDTO.class);
        List<ImportTaskDTO> importTaskDTO = importTasksWrapperDTO.getTasks().stream().toList();

        for (ImportTaskDTO taskDTO : importTaskDTO) {
            boolean isValid = this.validationUtils.isValid(taskDTO);

            if (this.mechanicRepository.findFirstByFirstName(taskDTO.getMechanic().getFirstName()).isEmpty()) {
                isValid = false;
            }

            if (isValid) {
                if (this.carRepository.findById(taskDTO.getCar().getId()).isPresent()) {
                    if (this.partRepository.findById(taskDTO.getPart().getId()).isPresent()) {
                        Task taskToSave = this.modelMapper.map(taskDTO, Task.class);

                        Car carToBeSet = this.carRepository.findById(taskDTO.getCar().getId()).get();
                        Mechanic mechanicToBeSet = this.mechanicRepository.findFirstByFirstName(taskDTO.getMechanic().getFirstName()).get();
                        Part partToBeSet = this.partRepository.findById(taskDTO.getPart().getId()).get();

                        taskToSave.setCar(carToBeSet);
                        taskToSave.setMechanic(mechanicToBeSet);
                        taskToSave.setPart(partToBeSet);
                        taskToSave.setDate(LocalDateTime.parse(taskDTO.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                        this.taskRepository.saveAndFlush(taskToSave);
                        sb.append(String.format(SUCCESSFULLY_IMPORTED_TASK, taskToSave.getPrice()));
                    }
                }
            } else {
                sb.append(INVALID_TASK).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        List<Task> tasks = this.taskRepository.findAllByCar_CarTypeOrderByPriceDesc(CarType.coupe)
                .orElseThrow(NoSuchElementException::new).stream().toList();
        return tasks.stream().map(task -> String.format(OUTPUT,
                task.getCar().getCarMake(),
                task.getCar().getCarModel(),
                task.getCar().getKilometers(),
                task.getMechanic().getFirstName(),
                task.getMechanic().getLastName(),
                task.getId(),
                task.getCar().getEngine(),
                task.getPrice())).collect(Collectors.joining(System.lineSeparator()));
    }
}
