package com.yoanan.unka.service.impl;

import com.yoanan.unka.model.entity.SolutionEntity;
import com.yoanan.unka.model.service.ChartServiceModel;
import com.yoanan.unka.model.service.ExerciseServiceModel;
import com.yoanan.unka.model.service.SolutionServiceModel;
import com.yoanan.unka.repository.SolutionRepository;
import com.yoanan.unka.service.ChartService;
import com.yoanan.unka.service.SolutionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SolutionServiceTest {

    SolutionService solutionServiceToTest;

    @Mock
    SolutionRepository solutionRepository;
    @Mock
    ChartService chartService;

    ModelMapper modelMapper;


    @BeforeEach
    public void SetUp() {
        modelMapper = new ModelMapper();
        solutionServiceToTest = new SolutionServiceImpl(
                solutionRepository,
                chartService,
                modelMapper
        );
    }


//    @Test
//    public void addSolution(){
//
//        SolutionServiceModel solutionServiceModel = new SolutionServiceModel();
//        solutionServiceModel.setCreditChartId(100L);
//        solutionServiceModel.setDebitChartId(200L);
//        solutionServiceModel.setCreditValue(10.99);
//        solutionServiceModel.setDebitValue(20.99);
//        solutionServiceModel.setExercise(new ExerciseServiceModel());
//
//        SolutionEntity solutionEntity = modelMapper.map(solutionServiceModel, SolutionEntity.class);
//
//        when(solutionRepository.save(solutionEntity))
//                .thenReturn(solutionEntity);
//
//        SolutionServiceModel result = solutionServiceToTest.addSolution(solutionServiceModel);
//        Assertions.assertEquals(100L, result.getCreditChartId());
//    }


    @Test
    public void verificationSolution_whenSolutionNotExist_shouldThrow() {

        Assertions.assertThrows(
                NullPointerException.class, () -> {
                    solutionServiceToTest.verificationSolution(new SolutionServiceModel());
                }
        );
    }


    @Test
    public void verificationSolution_whenSolutionExist_shouldReturnTrue() {

        ChartServiceModel chart = new ChartServiceModel();
        chart.setId(100L);


        ExerciseServiceModel exercise = new ExerciseServiceModel();
        exercise.setId(5L);

        SolutionServiceModel solutionServiceModel = new SolutionServiceModel();
        solutionServiceModel.setCreditChartId(chart.getId());
        solutionServiceModel.setDebitChartId(chart.getId());
        solutionServiceModel.setCreditValue(10.99);
        solutionServiceModel.setDebitValue(20.99);
        solutionServiceModel.setExercise(exercise);

        SolutionEntity solution = modelMapper.map(solutionServiceModel, SolutionEntity.class);

        when(solutionRepository.findByExercise_Id(5L))
                .thenReturn(Optional.ofNullable(solution));

        when(chartService.findByIdOfChart(chart.getId()))
                .thenReturn(chart);

        boolean result = solutionServiceToTest.verificationSolution(solutionServiceModel);
        Assertions.assertTrue(result);

    }

}
