package com.yoanan.unka.service;

import com.yoanan.unka.model.service.ChartServiceModel;

import java.util.List;

public interface ChartService {

    void seedChars();

    ChartServiceModel findByIdOfChart(Long chartId);

    List<ChartServiceModel> findAllChartsWithGroupsName();
}
