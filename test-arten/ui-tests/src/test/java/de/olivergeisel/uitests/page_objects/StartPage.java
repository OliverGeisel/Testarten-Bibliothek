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

package de.olivergeisel.uitests.page_objects;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

// page_url = https://localhost:8080/  - (index)
public class StartPage {
    public SelenideElement navBar        = $("nav[class*='visible']");
    public SelenideElement katalogButton = $("html > body > nav > div > div > ul > li:nth-of-type(2)");
    public SelenideElement searchInput   = $("input[type='search']");
}
