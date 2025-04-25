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

import java.util.List;

public class PersonRepository {
    public List<Person> findAll() {
        Person john =
                new Person(
                        "John",
                        "Burbridge",
                        51,
                        "fake@emailaddress.com",
                        "555-516-4620",
                        new Address("1234 Elm Street", "Oakland", "CA", 94619),
                        null);
        return List.of(john);
    }
}
