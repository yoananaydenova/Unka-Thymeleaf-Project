package com.yoanan.unka.service.impl;

import com.yoanan.unka.config.IAuthenticationFacade;
import com.yoanan.unka.model.entity.ProfileInformationEntity;
import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.service.ProfileInformationAddServiceModel;
import com.yoanan.unka.model.service.ProfileInformationServiceModel;
import com.yoanan.unka.repository.ProfileInformationRepository;
import com.yoanan.unka.service.CloudinaryService;
import com.yoanan.unka.service.ProfileInformationService;
import com.yoanan.unka.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProfileInformationServiceImpl implements ProfileInformationService {

    private final ModelMapper modelMapper;
    private final ProfileInformationRepository profileInformationRepository;
    private final UserService userService;
    private final IAuthenticationFacade authenticationFacade;
    private final CloudinaryService cloudinaryService;

    public ProfileInformationServiceImpl(ModelMapper modelMapper, ProfileInformationRepository profileInformationRepository, UserService userService, IAuthenticationFacade authenticationFacade, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.profileInformationRepository = profileInformationRepository;
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public void addInformation(ProfileInformationAddServiceModel profileInformationAddServiceModel) {

        ProfileInformationEntity profileInformationEntity = modelMapper.map(profileInformationAddServiceModel, ProfileInformationEntity.class);


        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        if (profileInformationRepository.findByUser_Username(username).isPresent()) {
            throw new IllegalStateException("Already have profile information for user with username" + username + " !");
        }

        UserEntity userEntity = userService.findByUsername(username);
        profileInformationEntity.setUser(userEntity);
        profileInformationEntity.setId(null);
        profileInformationRepository.save(profileInformationEntity);

    }

    @Override
    public boolean hasProfileInformation() {
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        return profileInformationRepository.existsByUser_Username(username);
    }

    @Override
    public ProfileInformationServiceModel getProfileInformation() {
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        ProfileInformationEntity profileInformationEntity;
        // Save empty profileInformation entity
        if (profileInformationRepository.findByUser_Username(username).isEmpty()) {
            ProfileInformationEntity newProfileInformationEntity = new ProfileInformationEntity();
            UserEntity currentUser = userService.findByUsername(username);
            newProfileInformationEntity.setUser(currentUser);
            profileInformationEntity = profileInformationRepository.save(newProfileInformationEntity);
        } else {
            profileInformationEntity = profileInformationRepository.findByUser_Username(username)
                    .orElseThrow(() -> new IllegalStateException("Already have profile information for user with username" + username + " !"));
        }

        return modelMapper.map(profileInformationEntity, ProfileInformationServiceModel.class);

    }

    @Override
    public void saveProfileImage(MultipartFile profileImage) throws IOException {
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        ProfileInformationEntity profileInformationEntity = profileInformationRepository.findByUser_Username(username)
                .orElseThrow(() -> new IllegalStateException("Already have profile information for user with username" + username + " !"));

        String imageUrl = cloudinaryService.uploadImage(profileImage);

        profileInformationEntity.setImgUrl(imageUrl);
        profileInformationRepository.save(profileInformationEntity);
    }
}
