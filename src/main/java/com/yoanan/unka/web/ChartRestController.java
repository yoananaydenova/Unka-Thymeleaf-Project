package com.yoanan.unka.web;

import com.yoanan.unka.model.view.ChartViewRestModel;
import com.yoanan.unka.service.ChartService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/charts")
@RestController
public class ChartRestController {

    private final ModelMapper modelMapper;
    private final ChartService chartService;

    public ChartRestController(ModelMapper modelMapper, ChartService chartService) {
        this.modelMapper = modelMapper;
        this.chartService = chartService;
    }

    @GetMapping("/api")
    public ResponseEntity<List<ChartViewRestModel>> findAll() {
        List<ChartViewRestModel> chartsList = chartService
                .findAllChartsWithGroupsName()
                .stream()
                .map(chart -> {
                    ChartViewRestModel chartViewRestModel = modelMapper.map(chart, ChartViewRestModel.class);
                    chartViewRestModel.setValue(chart.getId());
                    chartViewRestModel.setGroup(chart.getGroupName());
                    chartViewRestModel.setName(chart.getNumber() + " " + chart.getName());
                    return chartViewRestModel;
                })
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(chartsList);
    }
}
