/*
 * Copyright 2023 Oliver Geisel
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.olivergeisel.teddjbrary.rooms;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("rooms")
public class RaumController {

    private static final String TEMPLATE_DIR = "rooms/";
    private final RaumReposititory repo;

    public RaumController(RaumReposititory repo) {
        this.repo = repo;
    }

    @GetMapping({"", "/"})
    String overview(Model model) {
        model.addAttribute("rooms", repo.findAll());
        return TEMPLATE_DIR + "overview";
    }

    @GetMapping("/{id}")
    String detail(@PathVariable UUID id, Model model) {
        model.addAttribute("room", repo.findById(id).get());
        return TEMPLATE_DIR + "room";
    }

}
