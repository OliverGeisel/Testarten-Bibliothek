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

package de.olivergeisel.teddjbrary.inventory;

import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.user.visitor.BesucherRepository;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("inventory")
public class BestandController {
    private final BestandsVerwaltung verwaltung;
    private final BesucherRepository besucherRepository;


    public BestandController(BestandsVerwaltung verwaltung, BesucherRepository besucherRepository) {
        this.verwaltung = verwaltung;
        this.besucherRepository = besucherRepository;
    }

    @GetMapping({"", "/"})
    public String overview(Model model) {
        model.addAttribute("items", verwaltung.findAll());
        return "inventory";
    }

    @GetMapping("{id}")
    public String detail(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("item", verwaltung.findById(id));
        return "inventory-detail";
    }

    @PostMapping("ausleihen/{id}")
    public String ausleihen(@PathVariable("id") Buch buch, @LoggedIn UserAccount account) {
        besucherRepository.findByUserAccount(account).ifPresent(besucher -> verwaltung.ausleihen(buch, besucher));
        return "redirect:/inventory";
    }
}
