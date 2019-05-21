package com.adidas.masterservice.app;


import static com.adidas.masterservice.app.util.JsonUtil.objectToJson;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.adidas.masterservice.app.dto.Brand;
import com.adidas.masterservice.app.dto.MigrationFlow;
import com.adidas.masterservice.app.dto.MigrationType;
import com.adidas.masterservice.app.dto.WorkerStarterDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class OperationControllerTest{


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        WorkerStarterDto workerStarterDto = new WorkerStarterDto();
        workerStarterDto.setBrand(Brand.ADIDAS);
        workerStarterDto.setFlow(MigrationFlow.FULL);
        workerStarterDto.setMigrationType(MigrationType.BAZAAR_VOICE);
        this.mockMvc.perform(post("/run")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectToJson(workerStarterDto)
                ))
                .andDo(print()).andExpect(status().isAccepted())
                .andDo(document("index"));
    }

}
