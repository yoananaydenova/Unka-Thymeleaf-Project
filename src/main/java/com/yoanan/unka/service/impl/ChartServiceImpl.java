package com.yoanan.unka.service.impl;

import com.google.gson.Gson;
import com.yoanan.unka.model.binding.ChartBindingModel;
import com.yoanan.unka.model.entity.ChartEntity;
import com.yoanan.unka.model.entity.GroupEntity;
import com.yoanan.unka.model.entity.SectionEntity;
import com.yoanan.unka.repository.ChartRepository;
import com.yoanan.unka.repository.GroupRepository;
import com.yoanan.unka.repository.SectionRepository;
import com.yoanan.unka.service.ChartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class ChartServiceImpl implements ChartService {

    private final Resource groupsFile;
    private final Resource sectionsFile;
    private final Resource chartsFile;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ChartRepository chartRepository;
    private final GroupRepository groupRepository;
    private final SectionRepository sectionRepository;

    public ChartServiceImpl(
            @Value("classpath:init/groups.json") Resource groupsFile,
            @Value("classpath:init/sections.json") Resource sectionsFile,
            @Value("classpath:init/charts.json") Resource chartsFile,
            Gson gson,
            ModelMapper modelMapper, ChartRepository chartRepository,
            GroupRepository groupRepository,
            SectionRepository sectionRepository) {
        this.groupsFile = groupsFile;
        this.sectionsFile = sectionsFile;
        this.chartsFile = chartsFile;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.chartRepository = chartRepository;
        this.groupRepository = groupRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    public void seedChars() {

        // Seed Sections
        if (sectionRepository.count() == 0) {
            try {
                SectionEntity[] sectionEntities =
                        gson.fromJson(Files.readString(Path.of(sectionsFile.getURI())), SectionEntity[].class);
                //TODO validation of json file!!!!!
                Arrays.stream(sectionEntities).
                        forEach(sectionRepository::save);
            } catch (IOException e) {
                throw new IllegalStateException("Can not seed sections!");
            }
        }

        // Seed Groups
        if (groupRepository.count() == 0) {
            try {
                GroupEntity[] groupEntities =
                        gson.fromJson(Files.readString(Path.of(groupsFile.getURI())), GroupEntity[].class);
                //TODO validation of json file!!!!!
                Arrays.stream(groupEntities).
                        forEach(groupRepository::save);
            } catch (IOException e) {
                throw new IllegalStateException("Can not seed groups!");
            }
        }

        // Seed Charts
        if (chartRepository.count() == 0) {
            try {
                ChartBindingModel[] chartBindingModels =
                        gson.fromJson(Files.readString(Path.of(chartsFile.getURI())), ChartBindingModel[].class);

                //TODO validation of json file!!!!!
                Arrays.stream(chartBindingModels)
                        .forEach(cbm -> {
                            ChartEntity chartEntity = modelMapper.map(cbm, ChartEntity.class);
                            chartEntity.setGroup(groupRepository.findByNumber(cbm.getGroup()));
                            chartEntity.setSection(sectionRepository.findByNumber(cbm.getSection()));
                            chartRepository.save(chartEntity);
                        });
            } catch (IOException e) {
                throw new IllegalStateException("Can not seed charts!");
            }
        }


    }
}
