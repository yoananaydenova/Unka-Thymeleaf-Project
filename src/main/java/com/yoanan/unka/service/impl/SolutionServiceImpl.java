package com.yoanan.unka.service.impl;

import com.yoanan.unka.model.entity.SolutionEntity;
import com.yoanan.unka.model.service.ChartServiceModel;
import com.yoanan.unka.model.service.SolutionServiceModel;
import com.yoanan.unka.repository.SolutionRepository;
import com.yoanan.unka.service.ChartService;
import com.yoanan.unka.service.SolutionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SolutionServiceImpl implements SolutionService {

    private final SolutionRepository solutionRepository;
    private final ChartService chartService;
    private final ModelMapper modelMapper;

    public SolutionServiceImpl(SolutionRepository solutionRepository, ChartService chartService, ModelMapper modelMapper) {
        this.solutionRepository = solutionRepository;
        this.chartService = chartService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void addSolution(SolutionServiceModel solutionServiceModel) {
        SolutionEntity solutionEntity = modelMapper.map(solutionServiceModel, SolutionEntity.class);
        solutionEntity.setId(null);
        solutionRepository.save(solutionEntity);
    }


    @Override
    public boolean verificationSolution(SolutionServiceModel solutionServiceModel) {
        // Find exercise with solution from teacher
        SolutionEntity solutionEntityFromTeacher = solutionRepository.findByExercise_Id(solutionServiceModel.getExercise().getId())
                .orElseThrow(() ->
                        new IllegalStateException("Solution with id " + solutionServiceModel.getExercise().getId() + " not found!"));

        // Input charts from user
        ChartServiceModel debitChartByIdOfStudent = chartService.findByIdOfChart(solutionServiceModel.getDebitChartId());
        ChartServiceModel creditChartByIdOfStudent = chartService.findByIdOfChart(solutionServiceModel.getCreditChartId());

        // Check if teacher values == student values
        boolean isEqualDebitChartId = debitChartByIdOfStudent.getId().equals(solutionEntityFromTeacher.getDebitChartId());
        boolean isEqualCreditChartId = creditChartByIdOfStudent.getId().equals(solutionEntityFromTeacher.getCreditChartId());
        boolean isEqualDebitValue = solutionServiceModel.getDebitValue().equals(solutionEntityFromTeacher.getDebitValue());
        boolean isEqualCreditValue = solutionServiceModel.getCreditValue().equals(solutionEntityFromTeacher.getCreditValue());

        return isEqualDebitChartId && isEqualCreditChartId && isEqualDebitValue && isEqualCreditValue;
    }
}
