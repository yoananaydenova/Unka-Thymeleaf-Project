package com.yoanan.unka.service.impl;

import com.yoanan.unka.config.IAuthenticationFacade;
import com.yoanan.unka.model.entity.ProfileInformationEntity;
import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.service.ProfileInformationAddServiceModel;
import com.yoanan.unka.model.service.ProfileInformationChangeServiceModel;
import com.yoanan.unka.model.service.ProfileInformationServiceModel;
import com.yoanan.unka.model.service.UserServiceModel;
import com.yoanan.unka.repository.ProfileInformationRepository;
import com.yoanan.unka.service.CloudinaryService;
import com.yoanan.unka.service.ProfileInformationService;
import com.yoanan.unka.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

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

        ProfileInformationEntity newProfileInformationEntity = modelMapper.map(profileInformationAddServiceModel, ProfileInformationEntity.class);


        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        Optional<ProfileInformationEntity> profileInfoFromDB = profileInformationRepository.findByUser_Username(username);

        if (profileInfoFromDB.isPresent()) {
            profileInfoFromDB.get().setProfession(profileInformationAddServiceModel.getProfession());
            profileInfoFromDB.get().setYearsExperience(profileInformationAddServiceModel.getYearsExperience());
            profileInfoFromDB.get().setPresentation(profileInformationAddServiceModel.getPresentation());
            profileInformationRepository.save(profileInfoFromDB.get());
        } else {
            UserEntity userEntity = userService.findByUsername(username);
            newProfileInformationEntity.setUser(userEntity);
            newProfileInformationEntity.setId(null);
            profileInformationRepository.save(newProfileInformationEntity);
        }

    }


    // Find profile information about current logged user
    @Override
    public ProfileInformationServiceModel getProfileInformation() {
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        Optional<ProfileInformationEntity> profileInformationEntityOpt = profileInformationRepository.findByUser_Username(username);
        // Save empty profileInformation entity when user see page for first time
        ProfileInformationEntity returnProfileInfoEntity;
        if (profileInformationEntityOpt.isEmpty()) {
            ProfileInformationEntity newProfileInformationEntity = new ProfileInformationEntity();
            UserEntity currentUser = userService.findByUsername(username);
            newProfileInformationEntity.setUser(currentUser);

            returnProfileInfoEntity = profileInformationRepository.save(newProfileInformationEntity);
        } else {
            // If info is in DB
            returnProfileInfoEntity = profileInformationEntityOpt.get();
        }

        return modelMapper.map(returnProfileInfoEntity, ProfileInformationServiceModel.class);

    }

    // See edit profile page for first time
    @Override
    public ProfileInformationServiceModel getEditProfileInformationByUserId(Long userId) {
        // Check is it current user or admin want to edit profile
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        UserEntity currentUser = userService.findByUsername(username);
        boolean idEqual = currentUser.getId().equals(userId);
        boolean currentUserIsAdmin = userService.currentUserIsAdmin();
        if (!(idEqual || currentUserIsAdmin)) {
            throw new IllegalStateException("User with username" + username + " do not have permission to edit profile information with id " + userId + " !!!");
        }
        return getProfileInformationByUserId(userId);
    }
    @Override
    public ProfileInformationServiceModel getProfileInformationByUserId(Long userId) {

        // Find profile info by ID
        Optional<ProfileInformationEntity> profileInformationEntityOpt = profileInformationRepository.findByUser_Id(userId);
        // Save empty profileInformation entity when ADMIN see page for first time
        ProfileInformationEntity returnProfileInfoEntity;
        if (profileInformationEntityOpt.isEmpty()) {
            ProfileInformationEntity newProfileInformationEntity = new ProfileInformationEntity();
            // UserService will throw if user with thaat id dont exist
            UserServiceModel userById = userService.findUserById(userId);
            newProfileInformationEntity.setUser(modelMapper.map(userById, UserEntity.class));

            returnProfileInfoEntity = profileInformationRepository.save(newProfileInformationEntity);
        } else {
            // If info is in DB
            returnProfileInfoEntity = profileInformationEntityOpt.get();
        }
        return modelMapper.map(returnProfileInfoEntity, ProfileInformationServiceModel.class);
    }

    @Override
    public void saveChangedInfo(ProfileInformationChangeServiceModel profileInformationChangeServiceModel) throws IOException {

        // Check is it current user or admin want to edit profile
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        UserEntity currentUser = userService.findByUsername(username);
        boolean idEqual = currentUser.getId().equals(profileInformationChangeServiceModel.getUserId());
        boolean currentUserIsAdmin = userService.currentUserIsAdmin();
        if (!(idEqual || currentUserIsAdmin)) {
            throw new IllegalStateException("User with username" + username + " do not have permission to edit profile information with id " + profileInformationChangeServiceModel.getUserId() + " !!!");
        }

        // Find profile info by ID
        ProfileInformationEntity profileInformationEntity =
                profileInformationRepository.findByUser_Id(profileInformationChangeServiceModel.getUserId())
                        .orElseThrow(() -> new IllegalStateException("Profile information for user with id " + profileInformationChangeServiceModel.getUserId() + " not found!"));


        ProfileInformationEntity profileInformationEntityWithChange = modelMapper.map(profileInformationChangeServiceModel, ProfileInformationEntity.class);
        profileInformationEntityWithChange.setId(profileInformationEntity.getId());

        if (profileInformationChangeServiceModel.getImg() != null) {
            String imageUrl = cloudinaryService.uploadImage(profileInformationChangeServiceModel.getImg());
            profileInformationEntityWithChange.setImgUrl(imageUrl);
        }
        profileInformationRepository.save(profileInformationEntityWithChange);

    }




    @Override
    public void saveProfileImage(MultipartFile profileImage) throws IOException {

        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        ProfileInformationEntity profileInformationEntity = profileInformationRepository.findByUser_Username(username)
                .orElseThrow(() -> new IllegalStateException("Profile information for user with username" + username + " not found!"));

        String imageUrl = cloudinaryService.uploadImage(profileImage);

        profileInformationEntity.setImgUrl(imageUrl);
        profileInformationRepository.save(profileInformationEntity);
    }


}
