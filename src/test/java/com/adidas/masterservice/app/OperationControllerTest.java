package com.adidas.masterservice.app;

import com.adidas.masterservice.app.controllers.OperationController;
import io.micrometer.core.annotation.Timed;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cloud.deployer.resource.support.DelegatingResourceLoader;
import org.springframework.cloud.deployer.spi.local.LocalDeployerProperties;
import org.springframework.cloud.deployer.spi.local.LocalTaskLauncher;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import({LocalTaskLauncher.class,LocalDeployerProperties.class, DelegatingResourceLoader.class})
@RunWith(SpringRunner.class)
@WebMvcTest(OperationController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class OperationControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/workerStart")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("started")))
                .andDo(document("home"));
    }

    @Test
    public void shouldReturnHealthpage() throws Exception {
        this.mockMvc.perform(get("/msg")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test-app-msg")))
                .andDo(document("msg"));
    }


}
