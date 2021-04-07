package com.yoanan.unka.web;

import com.yoanan.unka.model.entity.ChartEntity;
import com.yoanan.unka.model.entity.GroupEntity;
import com.yoanan.unka.model.entity.SectionEntity;
import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.repository.ChartRepository;
import com.yoanan.unka.repository.GroupRepository;
import com.yoanan.unka.repository.SectionRepository;
import com.yoanan.unka.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.toIntExact;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ChartRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChartRepository chartRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private UserRepository userRepository;


    private Long CHART1_ID, CHART2_ID;

    @BeforeEach
    public void setUp() {
        chartRepository.deleteAll();
        groupRepository.deleteAll();
        sectionRepository.deleteAll();
        userRepository.deleteAll();

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("pesho").setPassword("xyz123").setFullName("petar petrov");
        userEntity = userRepository.save(userEntity);

        GroupEntity groupEntity1 = new GroupEntity();
        groupEntity1.setNumber(10);
        groupEntity1.setName("Капитал");
        groupEntity1 = groupRepository.save(groupEntity1);


        SectionEntity sectionEntity1 = new SectionEntity();
        sectionEntity1.setNumber(1);
        sectionEntity1.setName("Сметки за капитал");
        sectionEntity1 = sectionRepository.save(sectionEntity1);

        ChartEntity chartEntity1 = new ChartEntity();
        chartEntity1.setNumber(100);
        chartEntity1.setName("Основен капитал");
        chartEntity1.setGroup(groupEntity1);
        chartEntity1.setSection(sectionEntity1);
        chartEntity1 = chartRepository.save(chartEntity1);
        CHART1_ID = chartEntity1.getId();


        GroupEntity groupEntity2 = new GroupEntity();
        groupEntity2.setNumber(20);
        groupEntity2.setName("Дълготрайни материални активи");
        groupEntity2 = groupRepository.save(groupEntity2);

        SectionEntity sectionEntity2 = new SectionEntity();
        sectionEntity2.setNumber(2);
        sectionEntity2.setName("Сметки за дълготрайни активи");
        sectionEntity2 = sectionRepository.save(sectionEntity2);

        ChartEntity chartEntity2 = new ChartEntity();
        chartEntity2.setNumber(200);
        chartEntity2.setName("Земи/терени");
        chartEntity2.setGroup(groupEntity2);
        chartEntity2.setSection(sectionEntity2);
        chartEntity2= chartRepository.save(chartEntity2);
        CHART2_ID = chartEntity2.getId();

    }

    @AfterEach
    public void tearDown(){
        chartRepository.deleteAll();
        groupRepository.deleteAll();
        sectionRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    @WithMockUser(value = "pesho", roles = {"STUDENT"})
    void testFetchCharts() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/charts/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].value").value(toIntExact(CHART1_ID)))
                .andExpect(jsonPath("[0].group").value("Капитал"))
                .andExpect(jsonPath("[0].name").value("100 Основен капитал"))
                .andExpect(jsonPath("[1].value").value(toIntExact(CHART2_ID)))
                .andExpect(jsonPath("[1].group").value("Дълготрайни материални активи"))
                .andExpect(jsonPath("[1].name").value("200 Земи/терени"));
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"STUDENT"})
    public void testFetchChartsSize() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/charts/api"))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testFetchChartsWithoutRoleRedirectToLoginPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/charts/api"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/login"));
    }
}
