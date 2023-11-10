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

package de.olivergeisel.teddjbrary.auxiliary;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("jobs")
public class DefectController {

	private static final String BIBLITHEKAR_JOB_HTML = """
				<h1>Stellenbeschreibung: Bibliothekar/in</h1>
			    <p><strong>Position:</strong> Bibliothekar/in</p>
						    <p><strong>Abteilung:</strong> Bibliothekswesen</p>
						    <p><strong>Standort:</strong> [Ort der Bibliothek]</p>
						    <p><strong>Arbeitszeit:</strong> Vollzeit</p>

						    <h2>Über uns:</h2>
						    <p>Die [Name der Bibliothek] ist eine renommierte öffentliche Bibliothek, die sich der Förderung des Wissens und der Kultur in unserer Gemeinschaft verschrieben hat. Wir bieten eine breite Palette von Ressourcen und Dienstleistungen an, um die Bildung und das lebenslange Lernen in unserer Region zu unterstützen.</p>

						    <h2>Aufgaben:</h2>
						    <ol>
						        <li>
						            <h3>Kundenbetreuung:</h3>
						            <ul>
						                <li>Begrüßung und Unterstützung der Bibliotheksbesucher.</li>
						                <li>Beantwortung von Fragen und Bereitstellung von Informationen zu Bibliotheksressourcen und -dienstleistungen.</li>
						                <li>Unterstützung bei der Nutzung von Computern und elektronischen Ressourcen.</li>
						            </ul>
						        </li>
						        <li>
						            <h3>Bestandsmanagement:</h3>
						            <ul>
						                <li>Anschaffung neuer Bücher, Medien und Ressourcen gemäß den Bedürfnissen der Nutzer.</li>
						                <li>Pflege und Aktualisierung des Bibliotheksbestands, einschließlich Katalogisierung und Klassifizierung von Materialien.</li>
						                <li>Überwachung von Ausleihen, Rückgaben und Reservierungen.</li>
						            </ul>
						        </li>
						        <li>
						            <h3>Veranstaltungsplanung:</h3>
						            <ul>
						                <li>Organisation und Durchführung von bibliothekarischen Veranstaltungen, Workshops und Lesungen.</li>
						                <li>Förderung von Leseaktivitäten und Bildungsprogrammen.</li>
						            </ul>
						        </li>
						        <li>
						            <h3>Technische Unterstützung:</h3>
						            <ul>
						                <li>Unterstützung bei technischen Anliegen der Bibliotheksbesucher, einschließlich der Nutzung von Computern und Druckern.</li>
						                <li>Sicherstellung der Funktionalität von Bibliothekssoftware und -geräten.</li>
						            </ul>
						        </li>
						        <li>
						            <h3>Ordnung und Sauberkeit:</h3>
						            <ul>
						                <li>Aufrechterhaltung der Ordnung und Sauberkeit in den Bibliotheksräumen.</li>
						                <li>Überwachung der Einhaltung der Bibliotheksrichtlinien und -verfahren.</li>
						            </ul>
						        </li>
						    </ol>

						    <h2>Qualifikationen:</h2>
						    <ul>
						        <li>Abgeschlossene Ausbildung im Bibliotheks- oder Informationswissenschaftsbereich ist wünschenswert.</li>
						        <li>Starke Kommunikationsfähigkeiten und Kundenorientierung.</li>
						        <li>Erfahrung in der Verwaltung von Bibliotheksressourcen und -dienstleistungen.</li>
						        <li>Kenntnisse in Bibliothekssoftware und Informationstechnologie.</li>
						        <li>Teamfähigkeit und die Fähigkeit, selbstständig zu arbeiten.</li>
						        <li>Interesse an Literatur und kultureller Bildung.</li>
						    </ul>

						    <p>Wir bieten eine wettbewerbsfähige Vergütung und ein inspirierendes Arbeitsumfeld, in dem Sie Ihr Wissen und Ihre Leidenschaft für Bibliotheken einbringen können.</p>

						    <p>Wenn Sie sich für diese Position interessieren und die Anforderungen erfüllen, senden Sie bitte Ihren Lebenslauf und ein Anschreiben an [Kontaktinformationen]. Wir freuen uns auf Ihre Bewerbung!</p>
			""";

	@GetMapping("/bewerbung")
	public String newBewerbung(@RequestParam String name,
			@RequestParam String email,
			@RequestParam String message,
			@RequestParam String job, Model model) {
		model.addAttribute("name", name);
		model.addAttribute("email", email);
		model.addAttribute("message", message);
		model.addAttribute("job", job);
		model.addAttribute("description", BIBLITHEKAR_JOB_HTML);
		return "extra/bewerbung";
	}

	//region setter/getter
	@GetMapping({"", "/"})
	public String getJobs() {
		return "extra/jobs";
	}
//endregion

}
