package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.picture.ImportPicturesDTOJson;
import softuni.exam.instagraphlite.models.enitites.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.instagraphlite.constants.Messages.INVALID_PICTURES;
import static softuni.exam.instagraphlite.constants.Messages.SUCCESSFULLY_IMPORTED_PICTURES;
import static softuni.exam.instagraphlite.constants.Paths.PICTURES_IMPORT_JSON;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(PICTURES_IMPORT_JSON);
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<ImportPicturesDTOJson> importPicturesDTOJson = Arrays.stream(this.gson.fromJson(readFromFileContent(), ImportPicturesDTOJson[].class)).toList();

        for (ImportPicturesDTOJson importPicture : importPicturesDTOJson) {
            boolean isValid = validationUtils.isValid(importPicture);

            if (this.pictureRepository.findFirstByPath(importPicture.getPath()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Picture picture = this.modelMapper.map(importPicture, Picture.class);
                this.pictureRepository.saveAndFlush(picture);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_PICTURES, picture.getSize()));
            }else{
                sb.append(INVALID_PICTURES).append(System.lineSeparator());
            }
        }


        return sb.toString();
    }

    @Override
    public String exportPictures() {
        return null;
    }
}
