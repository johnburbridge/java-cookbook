/*
 * Copyright (C) 2024 John Burbridge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package com.github.johnburbridge.javacookbook.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PersonController.class)
@Import(TestConfig.class)
public class PersonControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private PersonRepository personRepository;

    @Autowired private ObjectMapper objectMapper;

    private Person person1;
    private Person person2;
    private List<Person> allPersons;

    @BeforeEach
    void setUp() {
        // Create test addresses
        Address homeAddress1 = new Address("123 Main St", "Springfield", "IL", 62701);
        Address workAddress1 = new Address("100 Business Blvd", "Springfield", "IL", 62704);

        Address homeAddress2 = new Address("456 Oak Ave", "Shelbyville", "IL", 62565);
        Address workAddress2 = new Address("200 Commerce St", "Shelbyville", "IL", 62566);

        // Create test persons
        person1 =
                new Person(
                        "John",
                        "Doe",
                        30,
                        "john.doe@example.com",
                        "555-123-4567",
                        homeAddress1,
                        workAddress1);
        person2 =
                new Person(
                        "Jane",
                        "Smith",
                        25,
                        "jane.smith@example.com",
                        "555-987-6543",
                        homeAddress2,
                        workAddress2);

        allPersons = Arrays.asList(person1, person2);
    }

    @Test
    void getAllPersons_ShouldReturnAllPersons() throws Exception {
        // Given
        when(personRepository.findAll()).thenReturn(allPersons);

        // When & Then
        mockMvc.perform(get("/api/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Doe")))
                .andExpect(jsonPath("$[0].age", is(30)))
                .andExpect(jsonPath("$[0].email", is("john.doe@example.com")))
                .andExpect(jsonPath("$[0].phoneNumber", is("555-123-4567")))
                .andExpect(jsonPath("$[0].homeAddress.street", is("123 Main St")))
                .andExpect(jsonPath("$[0].homeAddress.city", is("Springfield")))
                .andExpect(jsonPath("$[0].workAddress.street", is("100 Business Blvd")))
                .andExpect(jsonPath("$[1].firstName", is("Jane")))
                .andExpect(jsonPath("$[1].lastName", is("Smith")));
    }

    // Additional tests can be added as we gain more understanding of the PersonController
    // implementation
    // For now, we'll focus on the known functionality - getAllPersons()
}
