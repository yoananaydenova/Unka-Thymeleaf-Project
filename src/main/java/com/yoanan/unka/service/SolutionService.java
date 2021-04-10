package com.yoanan.unka.service;

import com.yoanan.unka.model.service.SolutionServiceModel;

public interface SolutionService {

    SolutionServiceModel addSolution(SolutionServiceModel solutionServiceModel);

    boolean verificationSolution(SolutionServiceModel solutionServiceModel);
}
