package com.yoanan.unka.service;

import com.yoanan.unka.model.service.ProfileInformationAddServiceModel;
import com.yoanan.unka.model.service.ProfileInformationServiceModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfileInformationService {
    void addInformation(ProfileInformationAddServiceModel profileInformationAddServiceModel);

    boolean hasProfileInformation();

    ProfileInformationServiceModel getProfileInformation();

    void saveProfileImage(MultipartFile profileImage) throws IOException;
}
