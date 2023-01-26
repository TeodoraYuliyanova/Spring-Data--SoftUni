package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.ApartmentType;
import softuni.exam.models.dto.offer.ImportOfferDTO;
import softuni.exam.models.dto.offer.ImportOffersWrapperDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.OFFERS_PATH_XML;
import static softuni.exam.models.ApartmentType.three_rooms;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final AgentRepository agentRepository;
    private final ApartmentRepository apartmentRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtils validationUtils, AgentRepository agentRepository, ApartmentRepository apartmentRepository) {
        this.offerRepository = offerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(OFFERS_PATH_XML);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        File file = OFFERS_PATH_XML.toFile();

        List<ImportOfferDTO> importOffersDTO = xmlParser.fromFile(file, ImportOffersWrapperDTO.class).getOffer();

        for (ImportOfferDTO importOfferDTO : importOffersDTO) {
            boolean isValid = validationUtils.isValid(importOfferDTO);

            if (this.agentRepository.findFirstByFirstName(importOfferDTO.getAgent().getName()).isEmpty()) {
                isValid = false;
            }

            if (this.apartmentRepository.findFirstById(importOfferDTO.getApartment().getId()).isEmpty()) {
                isValid = false;
            }

            if (isValid) {
                Agent agentToSet = this.agentRepository.findFirstByFirstName(importOfferDTO.getAgent().getName()).get();
                Apartment apartmentToSet = this.apartmentRepository.findFirstById(importOfferDTO.getApartment().getId()).get();

                Offer offer = this.modelMapper.map(importOfferDTO, Offer.class);
                offer.setAgent(agentToSet);
                offer.setApartment(apartmentToSet);
                offer.setPublishedOn(LocalDate.parse(importOfferDTO.getPublishedOn(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                this.offerRepository.saveAndFlush(offer);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_OFFER, offer.getPrice()));
            } else {

                sb.append(INVALID_OFFER).append(System.lineSeparator());
            }

        }

        return sb.toString();
    }

    @Override
    public String exportOffers() {

       List<Offer> offers = this.offerRepository.findAllByApartment_ApartmentTypeIsOrderByApartmentAreaDescPriceAsc(ApartmentType.three_rooms).orElseThrow(NoSuchElementException::new);
        return offers.stream().
                map(offer -> String.format(EXPORT_OFFERS,
                        offer.getAgent().getFirstName(),
                        offer.getAgent().getLastName(),
                        offer.getId(),
                        offer.getApartment().getArea(),
                        offer.getApartment().getTown().getTownName(),
                        offer.getPrice())).collect(Collectors.joining(System.lineSeparator()));
    }
}
