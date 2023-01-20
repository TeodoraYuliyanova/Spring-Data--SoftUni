package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.post.ImportPostDTO;
import softuni.exam.instagraphlite.models.dtos.post.ImportPostsWrapperDTO;
import softuni.exam.instagraphlite.models.enitites.Picture;
import softuni.exam.instagraphlite.models.enitites.Post;
import softuni.exam.instagraphlite.models.enitites.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.ValidationUtils;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static softuni.exam.instagraphlite.constants.Messages.INVALID_POST;
import static softuni.exam.instagraphlite.constants.Messages.SUCCESSFULLY_IMPORTED_POST;
import static softuni.exam.instagraphlite.constants.Paths.POSTS_IMPORT_XML;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, PictureRepository pictureRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }


    @Override
    public boolean areImported() {
        return this.postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(POSTS_IMPORT_XML);
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        File file = POSTS_IMPORT_XML.toFile();

        ImportPostsWrapperDTO importPostsWrapperDTO = this.xmlParser.fromFile(file, ImportPostsWrapperDTO.class);
        List<ImportPostDTO> importPostDTO = importPostsWrapperDTO.getPosts().stream().toList();

        for (ImportPostDTO postDTO : importPostDTO) {
            boolean isValid = validationUtils.isValid(postDTO);

            if (isValid) {
                if (this.userRepository.findFirstByUsername(postDTO.getUser().getUsername()).isPresent()) {
                    if (this.pictureRepository.findFirstByPath(postDTO.getPicture().getPath()).isPresent()) {
                        Post post = this.modelMapper.map(postDTO, Post.class);
                        User user = this.userRepository.findFirstByUsername(postDTO.getUser().getUsername()).get();
                        Picture picture = this.pictureRepository.findFirstByPath(postDTO.getPicture().getPath()).get();
                        post.setUser(user);
                        post.setPicture(picture);
                        this.postRepository.saveAndFlush(post);
                        sb.append(String.format(SUCCESSFULLY_IMPORTED_POST, post.getUser().getUsername()));
                    } else {
                        sb.append(INVALID_POST).append(System.lineSeparator());
                    }
                } else {
                    sb.append(INVALID_POST).append(System.lineSeparator());
                }
            } else {
                sb.append(INVALID_POST).append(System.lineSeparator());
            }
        }


        return sb.toString();
    }
}
