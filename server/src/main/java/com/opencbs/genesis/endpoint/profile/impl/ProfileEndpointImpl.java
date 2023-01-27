package com.opencbs.genesis.endpoint.profile.impl;

import com.opencbs.genesis.domain.Profile;
import com.opencbs.genesis.domain.ProfileField;
import com.opencbs.genesis.domain.ProfileFieldValue;
import com.opencbs.genesis.dto.profile.ProfileCreateDto;
import com.opencbs.genesis.dto.profile.ProfileDto;
import com.opencbs.genesis.dto.profile.ProfileUpdateDto;
import com.opencbs.genesis.endpoint.profile.ProfileEndpoint;
import com.opencbs.genesis.helpers.FileHelper;
import com.opencbs.genesis.mappers.profile.ProfileFieldValueMapper;
import com.opencbs.genesis.mappers.profile.ProfileMapper;
import com.opencbs.genesis.properties.AttachmentProperties;
import com.opencbs.genesis.request.profilefieldrequest.ProfileFieldValueCreateRequest;
import com.opencbs.genesis.services.ProfileFieldService;
import com.opencbs.genesis.services.ProfileFieldValueService;
import com.opencbs.genesis.services.ProfileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProfileEndpointImpl implements ProfileEndpoint {

    private final ProfileService profileService;
    private final ProfileFieldValueService profileFieldValueService;
    private final ProfileMapper profileMapper;
    private final ProfileFieldValueMapper profileFieldValueMapper;
    private final Path rootLocation;
    private final ProfileFieldService profileFieldService;
    private final AttachmentProperties attachmentProperties;

    @Autowired
    public ProfileEndpointImpl(ProfileService profileService,
                               ProfileFieldValueService profileFieldValueService,
                               ProfileMapper profileMapper,
                               ProfileFieldValueMapper profileFieldValueMapper,
                               ProfileFieldService profileFieldService,
                               AttachmentProperties attachmentProperties) {
        this.profileService = profileService;
        this.profileFieldValueService = profileFieldValueService;
        this.profileMapper = profileMapper;
        this.profileFieldValueMapper = profileFieldValueMapper;
        this.profileFieldService = profileFieldService;
        this.attachmentProperties = attachmentProperties;
        this.rootLocation = Paths.get(attachmentProperties.getLocation());
    }

    @SneakyThrows
    @Override
    public ProfileDto create(ProfileCreateDto createDto) {
        final Profile profile = profileService.create();
        List<ProfileFieldValueCreateRequest> profileFieldValueCreateRequests = createDto.getProfileSections().stream()
                .map(it -> profileFieldValueMapper.toProfileFieldValueCreateRequest(profile, it))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        profileFieldValueCreateRequests.forEach(profileFieldValueService::create);
        return profileMapper.toProfileDto(profile);
    }

    @Override
    public Page<ProfileDto> findAll(Pageable pageable) {
        Page<Profile> profiles = profileService.findAll(pageable);
        return profiles.map(profileMapper::toProfileDto);
    }

    @Override
    public ProfileDto findById(Long id) {
        Profile profile = profileService.findById(id).orElseThrow(()
                -> new EntityNotFoundException(String.format("There is no Profile with ID %d", id)));
        return profileMapper.toProfileDto(profile);
    }

    @Override
    @Transactional
    public ProfileDto update(Long id, ProfileUpdateDto profileUpdateDto) {
        Profile profile = profileService.findById(id).orElseThrow(()
                -> new EntityNotFoundException(String.format("There is no Profile with ID %d", id)));
        profileFieldValueService.delete(profile);
        List<ProfileFieldValueCreateRequest> profileFieldValueCreateRequests = profileUpdateDto.getProfileSections().stream()
                .map(it -> profileFieldValueMapper.toProfileFieldValueCreateRequest(profile, it))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        profileFieldValueCreateRequests.forEach(profileFieldValueService::create);
        return profileMapper.toProfileDto(profile);
    }

    @Override
    public void delete(Long id) {
        profileService.delete(id);
    }

    @SneakyThrows
    @Override
    public void createAttachment(MultipartFile file, Long profileId, String sectionCode, String profileFieldCode) {

        Profile profile = profileService.findById(profileId).orElseThrow(()
                -> new EntityNotFoundException(String.format("There is no Profile with ID %d", profileId)));

        ProfileField photoField = profileFieldService.findByCodeAndSectionCode(profileFieldCode, sectionCode)
                .orElseThrow(()
                        -> new EntityNotFoundException(String.format("There is no Field with ID %d", profileId)));

        Optional<ProfileFieldValue> profileFieldValue = profileFieldValueService.getByOwner(profile, photoField);

        if (!profileFieldValue.isPresent()) {
            Path savedPath = rootLocation.resolve(String.format("%s_%s", UUID.randomUUID(), file.getOriginalFilename()));
            FileHelper.save(file, savedPath);

            ProfileFieldValueCreateRequest profileFieldValueCreateRequest = profileFieldValueMapper
                    .getProfileFieldCreateRequestOne(profile, photoField, savedPath);
            profileFieldValueService.create(profileFieldValueCreateRequest);
        } else {
            profileFieldValueService.deleteByField(profile,photoField);
            FileHelper.delete(Paths.get(profileFieldValue.get().getValue()));

            Path savedPath = rootLocation.resolve(String.format("%s_%s", UUID.randomUUID(), file.getOriginalFilename()));
            FileHelper.save(file, savedPath);

            ProfileFieldValueCreateRequest profileFieldValueCreateRequest = profileFieldValueMapper
                    .getProfileFieldCreateRequestOne(profile, photoField, savedPath);
            profileFieldValueService.create(profileFieldValueCreateRequest);
        }

    }

    @Override
    public ResponseEntity getResponseEntity(Long profileId, String sectionCode, String profileFieldCode, Integer size) throws Exception {

        Profile profile = profileService.findById(profileId).orElseThrow(()
                -> new EntityNotFoundException(String.format("There is no Profile with ID %d", profileId)));
        ProfileField photoField = profileFieldService.findByCodeAndSectionCode(profileFieldCode, sectionCode)
                .orElseThrow(()
                        -> new EntityNotFoundException(String.format("There is no Profile with ID %d", profileId)));
        ProfileFieldValue profileFieldValue = profileFieldValueService.getByOwner(profile, photoField).orElseThrow(()
                -> new EntityNotFoundException(String.format("There is no ProfileFieldValue with ID %d", profileId)));


        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profileFieldValue.getValue() + "\"");

        return bodyBuilder.body(
                size == null
                        ? this.getPictureContent(profileFieldValue)
                        : this.getResizedPictureContent(profileFieldValue, size)
        );
    }

    private byte[] getPictureContent(ProfileFieldValue profileFieldValue) throws Exception {
        BufferedImage image = ImageIO.read((Paths.get(profileFieldValue.getValue())).toFile());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        ImageIO.write(image, "jpg", bos);

        return bos.toByteArray();
    }

    private byte[] getResizedPictureContent(ProfileFieldValue profileFieldValue, int size) throws Exception {
        BufferedImage image = ImageIO.read((Paths.get(profileFieldValue.getValue())).toFile());

        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();

        image = this.resize(image, type, size);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", bos);
        return bos.toByteArray();
    }

    private BufferedImage resize(BufferedImage image, int type, int size) {
        int width = image.getWidth();
        int height = image.getHeight();

        if (width > height) {
            height = size * height / width;
            width = size;
        } else {
            width = size * width / height;
            height = size;
        }

        BufferedImage resizedImage = new BufferedImage(size, size, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }
}
