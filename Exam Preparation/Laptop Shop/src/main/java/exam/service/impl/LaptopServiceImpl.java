package exam.service.impl;

import com.google.gson.Gson;
import exam.model.Laptop;
import exam.model.Shop;
import exam.model.dtos.laptopDTO.ImportLaptopsDTO;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import exam.util.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static exam.constants.Messages.*;
import static exam.constants.Paths.LAPTOPS_IMPORT_JSON;

@Service
public class LaptopServiceImpl implements LaptopService {

    private final LaptopRepository laptopRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ShopRepository shopRepository;

    @Autowired
    public LaptopServiceImpl(LaptopRepository laptopRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils, ShopRepository shopRepository) {
        this.laptopRepository = laptopRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.shopRepository = shopRepository;
    }


    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(LAPTOPS_IMPORT_JSON);
    }

    @Override
    public String importLaptops() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<ImportLaptopsDTO> importLaptopsDTO = Arrays.stream(this.gson.fromJson(readLaptopsFileContent(), ImportLaptopsDTO[].class)).toList();
        for (ImportLaptopsDTO laptopDTO : importLaptopsDTO) {

            boolean isValid = validationUtils.isValid(laptopDTO);

            if (this.laptopRepository.findFirstByMacAddress(laptopDTO.getMacAddress()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                if (this.shopRepository.findFirstByName(laptopDTO.getShop().getName()).isPresent()) {

                    Laptop laptopToSave = this.modelMapper.map(laptopDTO, Laptop.class);
                    Shop shopToSet = this.shopRepository.findFirstByName(laptopDTO.getShop().getName()).get();
                    laptopToSave.setShop(shopToSet);
                    this.laptopRepository.saveAndFlush(laptopToSave);
                    sb.append(String.format(SUCCESSFULLY_IMPORTED_LAPTOP,
                            laptopToSave.getMacAddress(), laptopToSave.getCpuSpeed(), laptopToSave.getRam(), laptopToSave.getStorage()));
                }
            } else {
                sb.append(INVALID_LAPTOP).append(System.lineSeparator());
            }
        }


        return sb.toString();
    }

    @Override
    public String exportBestLaptops() {

        List<Laptop> laptops = this.laptopRepository.getAllByOrderByCpuSpeedDescRamDescStorageDescMacAddress()
                .orElseThrow(NoSuchElementException::new).stream().toList();

        return laptops.stream().map(laptop -> String.format(LAPTOPS_OUTPUT,
                laptop.getMacAddress(),
                laptop.getCpuSpeed(),
                laptop.getRam(),
                laptop.getStorage(),
                laptop.getPrice(),
                laptop.getShop().getName(),
                laptop.getShop().getTown().getName())).collect(Collectors.joining(System.lineSeparator()));

    }
}
