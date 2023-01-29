package exam.service.impl;

import com.google.gson.Gson;
import exam.model.Customer;
import exam.model.Town;
import exam.model.dtos.customerDTO.ImportCustomersDTO;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import exam.util.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static exam.constants.Messages.INVALID_CUSTOMER;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED_CUSTOMER;
import static exam.constants.Paths.CUSTOMERS_IMPORT_JSON;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private Gson gson;
    private ModelMapper modelMapper;
    private ValidationUtils validationUtils;
    private final TownRepository townRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, Gson gson, ModelMapper modelMapper, ValidationUtils validationUtils, TownRepository townRepository) {
        this.customerRepository = customerRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(CUSTOMERS_IMPORT_JSON);
    }

    @Override
    public String importCustomers() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<ImportCustomersDTO> importCustomersDTO = Arrays.stream(this.gson.fromJson(readCustomersFileContent(), ImportCustomersDTO[].class)).toList();

        for (ImportCustomersDTO customerDTO : importCustomersDTO) {
            boolean isValid = validationUtils.isValid(customerDTO);

            if (this.customerRepository.findFirstByEmail(customerDTO.getEmail()).isPresent()) {
                isValid = false;
            }

            if (isValid) {

                if (this.townRepository.findFirstByName(customerDTO.getTown().getName()).isPresent()) {
                    Town townToSet = this.townRepository.findFirstByName(customerDTO.getTown().getName()).get();
                    Customer customerToSave = this.modelMapper.map(customerDTO, Customer.class);
                    customerToSave.setRegisteredOn(LocalDate.parse(customerDTO.getRegisteredOn(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    customerToSave.setTown(townToSet);
                    this.customerRepository.saveAndFlush(customerToSave);
                    sb.append(String.format(SUCCESSFULLY_IMPORTED_CUSTOMER,
                            customerToSave.getFirstName(), customerToSave.getLastName(), customerToSave.getEmail()));
                }
            } else {
                sb.append(INVALID_CUSTOMER).append(System.lineSeparator());
            }
        }


        return sb.toString();
    }
}
