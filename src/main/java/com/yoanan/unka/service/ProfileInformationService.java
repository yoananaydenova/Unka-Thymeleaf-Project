package com.yoanan.unka.service;

import com.yoanan.unka.model.service.ProfileInformationAddServiceModel;
import com.yoanan.unka.model.service.ProfileInformationChangeServiceModel;
import com.yoanan.unka.model.service.ProfileInformationServiceModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfileInformationService {
    void addInformation(ProfileInformationAddServiceModel profileInformationAddServiceModel);

    ProfileInformationServiceModel getProfileInformation();

    void saveProfileImage(MultipartFile profileImage) throws IOException;

    ProfileInformationServiceModel getProfileInformationByUserId(Long userId);

    void saveChangedInfo(ProfileInformationChangeServiceModel profileInformationChangeServiceModel) throws IOException;

    ProfileInformationServiceModel getEditProfileInformationByUserId(Long userId);
}
