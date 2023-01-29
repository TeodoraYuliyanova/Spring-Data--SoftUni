package exam.service.impl;

import exam.model.Shop;
import exam.model.Town;
import exam.model.dtos.shopDTO.ImportShopDTO;
import exam.model.dtos.shopDTO.ImportShopsWrapperDTO;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
import exam.util.ValidationUtils;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static exam.constants.Messages.INVALID_SHOP;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED_SHOP;
import static exam.constants.Paths.SHOPS_IMPORT_XML;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;
    private final TownRepository townRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, ModelMapper modelMapper, ValidationUtils validationUtils, XmlParser xmlParser, TownRepository townRepository) {
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(SHOPS_IMPORT_XML);
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        File file = SHOPS_IMPORT_XML.toFile();
        ImportShopsWrapperDTO importShopsWrapperDTO = xmlParser.fromFile(file, ImportShopsWrapperDTO.class);
        List<ImportShopDTO> importShopDTO = importShopsWrapperDTO.getShop().stream().toList();

        for (ImportShopDTO shopDTO : importShopDTO) {
            boolean isValid = validationUtils.isValid(shopDTO);

            if (this.shopRepository.findFirstByName(shopDTO.getName()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                if (this.townRepository.findFirstByName(shopDTO.getTown().getName()).isPresent()) {
                    Shop shop = this.modelMapper.map(shopDTO, Shop.class);
                    Town townToSet = this.townRepository.findFirstByName(shopDTO.getTown().getName()).get();
                    shop.setTown(townToSet);
                    this.shopRepository.saveAndFlush(shop);
                    sb.append(String.format(SUCCESSFULLY_IMPORTED_SHOP, shop.getName(), shop.getIncome()));
                }
            } else {
                sb.append(INVALID_SHOP).append(System.lineSeparator());
            }
        }


        return sb.toString();
    }
}
